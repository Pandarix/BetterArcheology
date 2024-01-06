package net.Pandarix.betterarcheology.block.custom;

import com.google.common.collect.ImmutableMap;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

import java.util.Map;
import java.util.stream.Stream;

public class SheepFossilBlock extends FossilBaseBlock {
    public static final BooleanProperty PLAYING = BooleanProperty.of("playing"); //true while sound is played and for the duration of "playCooldown"
    private static final int playCooldown = 80; //used to prevent sound-spamming
    public static final IntProperty HORN_SOUND = IntProperty.of("horn_sound", 0, 7); //index of the goat horn sound currently used

    //Map of hitboxes for every direction the model can be facing
    private static final Map<Direction, VoxelShape> SHEEP_SHAPES_FOR_DIRECTION = ImmutableMap.of(
            Direction.NORTH, Stream.of(
                    Block.createCuboidShape(4, 0, 4, 12, 17.75, 19),
                    Block.createCuboidShape(4, 9, 0, 12, 17.75, 4),
                    Block.createCuboidShape(3.75, 14, -7.5, 12, 25, 5)).reduce(VoxelShapes::union).get(),
            Direction.SOUTH, Stream.of(
                    Block.createCuboidShape(4, 0, -3, 12, 17.75, 12),
                    Block.createCuboidShape(4, 9, 12, 12, 17.75, 16),
                    Block.createCuboidShape(4, 14, 11, 12.25, 25, 23.5)).reduce(VoxelShapes::union).get(),
            Direction.EAST, Stream.of(
                    Block.createCuboidShape(-3, 0, 4, 12, 17.75, 12),
                    Block.createCuboidShape(12, 9, 4, 16, 17.75, 12),
                    Block.createCuboidShape(11, 14, 3.75, 23.5, 25, 12)).reduce(VoxelShapes::union).get(),
            Direction.WEST, Stream.of(
                    Block.createCuboidShape(4, 0, 4, 19, 17.75, 12),
                    Block.createCuboidShape(0, 9, 4, 4, 17.75, 12),
                    Block.createCuboidShape(-7.5, 14, 4, 5, 25, 12.25)).reduce(VoxelShapes::union).get());

    public SheepFossilBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(HORN_SOUND, 0).with(PLAYING, false));
    }

    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHEEP_SHAPES_FOR_DIRECTION.get(state.get(FACING));
    }

    public void neighborUpdate(BlockState state, World world, BlockPos pos, Block sourceBlock, BlockPos sourcePos, boolean notify) {
        boolean powered = world.isReceivingRedstonePower(pos) || world.isReceivingRedstonePower(pos.up());
        boolean playing = (Boolean) state.get(PLAYING);

        if (powered && !playing) {
            //play sound and set state to playing
            if(!world.isClient()){
                world.playSound(null, pos, SoundEvents.GOAT_HORN_SOUNDS.get(state.get(HORN_SOUND)).value(), SoundCategory.BLOCKS);
            }
            world.setBlockState(pos, state.with(PLAYING, true));
            //set cooldown for playing to be reset
            world.scheduleBlockTick(pos, this, playCooldown);
        }
    }

    //used to tune the SheepFossilBlock to an Index of the GoatHornsSounds
    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        //if sound is already being played, abort
        if(state.get(PLAYING)){return ActionResult.FAIL;}

        if (!world.isClient()) {
            //increase index or set to 0 if at max
            if (state.get(HORN_SOUND) + 1 <= 7) {
                world.setBlockState(pos, state.with(HORN_SOUND, state.get(HORN_SOUND) + 1).with(PLAYING, true));
            } else {
                world.setBlockState(pos, state.with(HORN_SOUND, 0).with(PLAYING, true));
            }

            //play sound and set cooldown to reset "playing" property
            world.playSound(null, pos, SoundEvents.GOAT_HORN_SOUNDS.get(world.getBlockState(pos).get(HORN_SOUND)).value(), SoundCategory.BLOCKS);
            world.scheduleBlockTick(pos, this, playCooldown);
        } else {
            //if on clientside, display a note particle
            world.addParticle(ParticleTypes.NOTE, pos.getX() + 0.5, pos.getY() + 1.5, pos.getZ() + 0.5, 0, 0.2, 0);
        }
        return ActionResult.SUCCESS;
    }

    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        world.setBlockState(pos, state.with(PLAYING, false));
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING, HORN_SOUND, PLAYING);
    }
}
