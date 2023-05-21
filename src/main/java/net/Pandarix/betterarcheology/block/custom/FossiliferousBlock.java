package net.Pandarix.betterarcheology.block.custom;

import net.Pandarix.betterarcheology.block.entity.DiggableBlockEntity;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Property;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class FossiliferousBlock extends BlockWithEntity {
    public static final IntProperty DUG = IntProperty.of("dug", 0, 3);;
    public static final int field_42773 = 2;

    @Override
    public void appendTooltip(ItemStack stack, @Nullable BlockView world, List<Text> tooltip, TooltipContext options) {
        tooltip.add(Text.translatable(this.getTranslationKey() + "_tooltip").formatted(Formatting.GRAY));
        super.appendTooltip(stack, world, tooltip, options);
    }

    private final Block baseBlock;
    private final SoundEvent diggingSound;
    private final SoundEvent diggingCompleteSound;

    public FossiliferousBlock(Block baseBlock, AbstractBlock.Settings settings, SoundEvent diggingSound, SoundEvent diggingCompleteSound) {
        super(settings);
        this.baseBlock = baseBlock;
        this.diggingSound = diggingSound;
        this.diggingCompleteSound = diggingCompleteSound;
        this.setDefaultState((BlockState)((BlockState)this.stateManager.getDefaultState()).with(DUG, 0));
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(new Property[]{DUG});
    }

    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    public void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean notify) {
        world.scheduleBlockTick(pos, this, 2);
    }

    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        world.scheduleBlockTick(pos, this, 2);
        return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }

    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        BlockEntity var6 = world.getBlockEntity(pos);
        if (var6 instanceof DiggableBlockEntity diggableBlockEntity) {
            diggableBlockEntity.scheduledTick();
        }
    }

    public @Nullable BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new DiggableBlockEntity(pos, state);
    }

    public Block getBaseBlock() {
        return this.baseBlock;
    }

    public SoundEvent getDiggingSound() {
        return this.diggingSound;
    }

    public SoundEvent getDiggingCompleteSound() {
        return this.diggingCompleteSound;
    }
}
