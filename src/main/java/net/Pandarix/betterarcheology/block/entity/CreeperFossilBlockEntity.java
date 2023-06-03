package net.Pandarix.betterarcheology.block.entity;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;

public class CreeperFossilBlockEntity extends BlockEntity {
    public CreeperFossilBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.CREEPER_FOSSIL, pos, state);
    }
}
