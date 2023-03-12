package net.Pandarix.betterarcheology.block.custom;

import net.Pandarix.betterarcheology.block.entity.ArcheologyTableBlockEntity;
import net.Pandarix.betterarcheology.block.entity.ModBlockEntities;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("ALL")
public class ArchelogyTable extends BlockWithEntity implements BlockEntityProvider {
    public ArchelogyTable(Settings settings) {
        super(settings);
    }


    /* BLOCK ENTITY STUFF */

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }


    //Drops Items present in the table at the time of destruction//
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

    //Creates the Screen-Handler belonging to the BlockEntity//
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

    // creates ArcheologyTableBlockEntity //
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
};

