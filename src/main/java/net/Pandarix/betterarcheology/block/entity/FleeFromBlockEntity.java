package net.Pandarix.betterarcheology.block.entity;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;

public class FleeFromBlockEntity extends BlockEntity
{
    //This is used to add a FleeGoal for Mobs, Blocks extending this class can be fled from
    public FleeFromBlockEntity(BlockPos pos, BlockState state)
    {
        super(ModBlockEntities.FLEE_FROM, pos, state);
    }
}
