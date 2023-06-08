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

public class VillagerFossilBodyBlock extends FossilBaseBodyBlock {
    //Map of hitboxes for every direction the model can be facing
    private static final Map<Direction, VoxelShape> SHAPES_FOR_DIRECTION = ImmutableMap.of(
            Direction.NORTH, Block.createCuboidShape(0, 0, 8, 16, 12, 15),
            Direction.SOUTH, Block.createCuboidShape(0, 0, 1, 16, 12, 8),
            Direction.EAST, Block.createCuboidShape(1, 0, 0, 8, 12, 16),
            Direction.WEST, Block.createCuboidShape(8, 0, 0, 15, 12, 16));

    public VillagerFossilBodyBlock(Settings settings) {
        super(settings);
    }

    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPES_FOR_DIRECTION.get(state.get(FACING));
    }
}
