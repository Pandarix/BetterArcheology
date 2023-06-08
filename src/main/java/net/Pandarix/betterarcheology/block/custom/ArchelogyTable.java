package net.Pandarix.betterarcheology.block.custom;

import net.Pandarix.betterarcheology.block.entity.ArcheologyTableBlockEntity;
import net.Pandarix.betterarcheology.block.entity.ModBlockEntities;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.BlockStateParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class ArchelogyTable extends BlockWithEntity implements BlockEntityProvider {
    //indicates if the table is currently "crafting" the identified artifact
    //triggers particle creation
    public static final BooleanProperty DUSTING = BooleanProperty.of("dusting");

    public ArchelogyTable(Settings settings) {
        super(settings);
        this.setDefaultState((BlockState) this.stateManager.getDefaultState().with(DUSTING, false));
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(DUSTING);
    }

    /* BLOCK ENTITY STUFF */

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }


    //Drops Items present in the table at the time of destruction
    @Override
    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        if (state.getBlock() != newState.getBlock()) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof ArcheologyTableBlockEntity) {
                ItemScatterer.spawn(world, pos, (ArcheologyTableBlockEntity) blockEntity);
                world.updateComparators(pos, this);
            }
        }
        super.onStateReplaced(state, world, pos, newState, moved);
    }

    //Creates the Screen-Handler belonging to the BlockEntity
    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (!world.isClient) {
            NamedScreenHandlerFactory handledScreen = state.createScreenHandlerFactory(world, pos);

            if (handledScreen != null) {
                player.openHandledScreen(handledScreen);
            }
        }

        return ActionResult.SUCCESS;

    }

    // creates ArcheologyTableBlockEntity for each ArcheologyTable
    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new ArcheologyTableBlockEntity(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return checkType(type, ModBlockEntities.ARCHEOLOGY_TABLE, ArcheologyTableBlockEntity::tick);
    }

    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        if (world.isClient() && state.get(DUSTING)) {
            addDustParticles(world, pos, random);
        }
        super.randomDisplayTick(state, world, pos, random);
    }

    public void addDustParticles(World world, BlockPos pos, Random random) {
        if (random.nextBoolean()) {
            return;
        } //create particles half of the time
        int i = random.nextBetweenExclusive(1, 3); //number of particles to be created

        BlockStateParticleEffect blockStateParticleEffect = new BlockStateParticleEffect(ParticleTypes.BLOCK, Blocks.SAND.getDefaultState());

        for (int j = 0; j < i; ++j) {
            //centering Block position
            //setting base velocity to 3 and multiplying it with rand double with random sign
            //that way particles can spread in every direction by chance
            world.addParticle(blockStateParticleEffect,
                    pos.getX() + 0.5, pos.getY() + 1, pos.getZ() + 0.5,
                    3.0 * random.nextDouble() * (random.nextBoolean() ? 1 : -1),
                    0.0,
                    3.0 * random.nextDouble() * (random.nextBoolean() ? 1 : -1));
        }
    }
};

