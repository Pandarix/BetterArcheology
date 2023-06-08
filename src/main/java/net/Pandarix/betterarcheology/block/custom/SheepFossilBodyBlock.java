package net.Pandarix.betterarcheology.block.custom;

import com.google.common.collect.ImmutableMap;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;

import java.util.Map;

public class SheepFossilBodyBlock extends FossilBaseBodyBlock {
    //Map of hitboxes for every direction the model can be facing
    private static final Map<Direction, VoxelShape> SHAPES_FOR_DIRECTION = ImmutableMap.of(
            Direction.NORTH, Block.createCuboidShape(0, 0, 2, 16, 8, 16),
            Direction.SOUTH, Block.createCuboidShape(0, 0, 0, 16, 8, 14),
            Direction.EAST, Block.createCuboidShape(2, 0, 0, 16, 8, 16),
            Direction.WEST, Block.createCuboidShape(0, 0, 0, 14, 8, 16));
    public SheepFossilBodyBlock(Settings settings) {
        super(settings);
    }

    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPES_FOR_DIRECTION.get(state.get(FACING));
    }
}
