package net.Pandarix.betterarcheology.block.custom;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;

public class SheepFossilHeadBlock extends FossilBaseHeadBlock {
    private static final VoxelShape SHEEP_HEAD_SHAPE = Block.createCuboidShape(3, 0, 3, 13, 8, 13);
    public SheepFossilHeadBlock(Settings settings) {
        super(settings);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHEEP_HEAD_SHAPE;
    }
}
