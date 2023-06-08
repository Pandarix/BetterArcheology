package net.Pandarix.betterarcheology.block.custom;

import com.google.common.collect.ImmutableMap;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;

import java.util.Map;
import java.util.stream.Stream;

public class CreeperFossilBodyBlock extends FossilBaseBodyBlock {
    //Map of hitboxes for every direction the model can be facing
    private static final Map<Direction, VoxelShape> SHAPES_FOR_DIRECTION = ImmutableMap.of(
            Direction.NORTH, Stream.of(
                    Block.createCuboidShape(3.5, 5.25, 5.5, 12.5, 17.25, 10.5),
                    Block.createCuboidShape(3, 0, 9.5, 13, 6.5, 14.5),
                    Block.createCuboidShape(3, 0, 1.5, 13, 6.5, 6.5)).reduce(VoxelShapes::union).get(),
            Direction.SOUTH, Stream.of(
                    Block.createCuboidShape(3.5, 5.25, 5.5, 12.5, 17.25, 10.5),
                    Block.createCuboidShape(3, 0, 9.5, 13, 6.5, 14.5),
                    Block.createCuboidShape(3, 0, 1.5, 13, 6.5, 6.5)).reduce(VoxelShapes::union).get(),
            Direction.WEST, Stream.of(
                    Block.createCuboidShape(5.5, 5.25, 3.5, 10.5, 17.25, 12.5),
                    Block.createCuboidShape(1.5, 0, 3, 6.5, 6.5, 13),
                    Block.createCuboidShape(9.5, 0, 3, 14.5, 6.5, 13)).reduce(VoxelShapes::union).get(),
            Direction.EAST, Stream.of(
                    Block.createCuboidShape(5.5, 5.25, 3.5, 10.5, 17.25, 12.5),
                    Block.createCuboidShape(1.5, 0, 3, 6.5, 6.5, 13),
                    Block.createCuboidShape(9.5, 0, 3, 14.5, 6.5, 13)).reduce(VoxelShapes::union).get());

    public CreeperFossilBodyBlock(Settings settings) {
        super(settings);
    }

    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPES_FOR_DIRECTION.get(state.get(FACING));
    }
}
