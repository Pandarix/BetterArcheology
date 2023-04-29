package net.Pandarix.betterarcheology.block.entity;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.math.BlockPos;

public class FleeFromBlockEntity extends BlockEntity{
    public FleeFromBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.FLEE_FROM, pos, state);
    }
}
