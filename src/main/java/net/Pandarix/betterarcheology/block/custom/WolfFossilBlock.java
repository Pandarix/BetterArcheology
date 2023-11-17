package net.Pandarix.betterarcheology.block.custom;

import com.google.common.collect.ImmutableMap;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class WolfFossilBlock extends FossilBaseWithEntityBlock {
    //Map of hitboxes for every direction the model can be facing
    private static final Map<Direction, VoxelShape> WOLF_SHAPES_FOR_DIRECTION = ImmutableMap.of(
            Direction.NORTH, Stream.of(
                    Block.createCuboidShape(4, 0, 2, 12, 16, 17),
                    Block.createCuboidShape(4, 9, -6, 12, 16, 2)).reduce(VoxelShapes::union).get(),
            Direction.SOUTH, Stream.of(
                    Block.createCuboidShape(4, 0, -1, 12, 16, 14),
                    Block.createCuboidShape(4, 9, 14, 12, 16, 22)).reduce(VoxelShapes::union).get(),
            Direction.EAST, Stream.of(
                    Block.createCuboidShape(-1, 0, 4, 14, 16, 12),
                    Block.createCuboidShape(14, 9, 4, 22, 16, 12)).reduce(VoxelShapes::union).get(),
            Direction.WEST, Stream.of(
                    Block.createCuboidShape(2, 0, 4, 17, 16, 12),
                    Block.createCuboidShape(-6, 9, 4, 2, 16, 12)).reduce(VoxelShapes::union).get());
    public WolfFossilBlock(Settings settings) {
        super(settings);
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new SkeletonFleeFromBlockEntity(pos, state);
    }

    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return WOLF_SHAPES_FOR_DIRECTION.get(state.get(FACING));
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable BlockView world, List<Text> tooltip, TooltipContext options) {
        tooltip.add(Text.translatable("block.betterarcheology.wolf_fossil_tooltip").formatted(Formatting.GRAY));
        super.appendTooltip(stack, world, tooltip, options);
    }
}
