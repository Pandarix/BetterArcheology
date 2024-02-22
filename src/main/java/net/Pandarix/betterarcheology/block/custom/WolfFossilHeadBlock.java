package net.Pandarix.betterarcheology.block.custom;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;


public class WolfFossilHeadBlock extends FossilBaseHeadBlock
{
    private static final VoxelShape WOLF_HEAD_SHAPE = Block.createCuboidShape(4, 0, 4, 12, 6, 12);

    public WolfFossilHeadBlock(Settings settings)
    {
        super(settings);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context)
    {
        return WOLF_HEAD_SHAPE;
    }
}