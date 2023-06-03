package net.Pandarix.betterarcheology.block.custom;

import com.google.common.collect.ImmutableMap;
import net.Pandarix.betterarcheology.block.entity.VillagerFossilBlockEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
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
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.stream.Stream;

public class CreeperFossilBlock extends FossilBaseWithEntityBlock {
    public static final BooleanProperty ON = BooleanProperty.of("on");
    private static final Map<Direction, VoxelShape> CHICKEN_SHAPES_FOR_DIRECTION = ImmutableMap.of(
            Direction.NORTH, Stream.of(
                    Block.createCuboidShape(3.5, 17.25, 3.5, 12.5, 26.25, 12.5),
                    Block.createCuboidShape(3.5, 5.25, 5.5, 12.5, 17.25, 10.5),
                    Block.createCuboidShape(3, 0, 9.5, 13, 6.5, 14.5),
                    Block.createCuboidShape(3, 0, 1.5, 13, 6.5, 6.5)).reduce(VoxelShapes::union).get(),
            Direction.SOUTH, Stream.of(
                    Block.createCuboidShape(3.5, 17.25, 3.5, 12.5, 26.25, 12.5),
                    Block.createCuboidShape(3.5, 5.25, 5.5, 12.5, 17.25, 10.5),
                    Block.createCuboidShape(3, 0, 9.5, 13, 6.5, 14.5),
                    Block.createCuboidShape(3, 0, 1.5, 13, 6.5, 6.5)).reduce(VoxelShapes::union).get(),
            Direction.WEST, Stream.of(
                    Block.createCuboidShape(3.5, 17.25, 3.5, 12.5, 26.25, 12.5),
                    Block.createCuboidShape(5.5, 5.25, 3.5, 10.5, 17.25, 12.5),
                    Block.createCuboidShape(1.5, 0, 3, 6.5, 6.5, 13),
                    Block.createCuboidShape(9.5, 0, 3, 14.5, 6.5, 13)).reduce(VoxelShapes::union).get(),
            Direction.EAST, Stream.of(
                    Block.createCuboidShape(3.5, 17.25, 3.5, 12.5, 26.25, 12.5),
                    Block.createCuboidShape(5.5, 5.25, 3.5, 10.5, 17.25, 12.5),
                    Block.createCuboidShape(1.5, 0, 3, 6.5, 6.5, 13),
                    Block.createCuboidShape(9.5, 0, 3, 14.5, 6.5, 13)).reduce(VoxelShapes::union).get());

    public CreeperFossilBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(ON, false));
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if(!world.isClient()){
            world.setBlockState(pos, state.cycle(ON));
        }
        world.playSoundAtBlockCenter(pos, SoundEvents.ENTITY_CREEPER_HURT, SoundCategory.BLOCKS, 0.5f, 1f, true);
        return ActionResult.SUCCESS;
    }

    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return CHICKEN_SHAPES_FOR_DIRECTION.get(state.get(FACING));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(ON);
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new VillagerFossilBlockEntity(pos, state);
    }
}
