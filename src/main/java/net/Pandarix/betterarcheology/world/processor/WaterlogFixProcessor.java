package net.Pandarix.betterarcheology.world.processor;

import com.mojang.serialization.Codec;
import com.yungnickyoung.minecraft.yungsapi.world.structure.processor.ISafeWorldModifier;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.state.property.Properties;
import net.minecraft.structure.StructurePlacementData;
import net.minecraft.structure.StructureTemplate;
import net.minecraft.structure.processor.StructureProcessor;
import net.minecraft.structure.processor.StructureProcessorType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.WorldView;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkSection;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

/**
 * A patchwork fix for https://bugs.mojang.com/browse/MC-130584.
 */

/**
 * A patchwork fix for https://bugs.mojang.com/browse/MC-130584.
 */
public class WaterlogFixProcessor extends StructureProcessor implements ISafeWorldModifier {
    public static final WaterlogFixProcessor INSTANCE = new WaterlogFixProcessor();
    public static final Codec<WaterlogFixProcessor> CODEC = Codec.unit(() -> INSTANCE);

    /**
     * Workaround for https://bugs.mojang.com/browse/MC-130584
     * Due to a hardcoded field in Templates, any waterloggable blocks in structures replacing water in the world will become waterlogged.
     * Idea of workaround is detect if we are placing a waterloggable block and if so, remove the water in the world instead.
     */
    @Nullable
    @Override
    public StructureTemplate.StructureBlockInfo process(WorldView world,
                                                             BlockPos pos,
                                                             BlockPos pivot,
                                                             StructureTemplate.StructureBlockInfo originalBlockInfo,
                                                             StructureTemplate.StructureBlockInfo currentBlockInfo,
                                                             StructurePlacementData data) {
        // Check if block is waterloggable and not intended to be waterlogged
        if (currentBlockInfo.state().contains(Properties.WATERLOGGED) && !currentBlockInfo.state().get(Properties.WATERLOGGED)) {
            ChunkPos currentChunkPos = new ChunkPos(currentBlockInfo.pos());
            Chunk currentChunk = world.getChunk(currentChunkPos.x, currentChunkPos.z);
            int sectionYIndex = currentChunk.getSectionIndex(currentBlockInfo.pos().getY());

            // Validate chunk section index. Sometimes the index is -1. Not sure why, but this will
            // at least prevent the game from crashing.
            if (sectionYIndex < 0) {
                return currentBlockInfo;
            }

            ChunkSection currChunkSection = currentChunk.getSection(sectionYIndex);

            if (getFluidStateSafe(world, currentBlockInfo.pos()).isIn(FluidTags.WATER)) {
                setBlockStateSafe(world, currentBlockInfo.pos(), Blocks.STONE_BRICKS.getDefaultState());
            }

            // Remove water in adjacent blocks
            BlockPos.Mutable mutable = new BlockPos.Mutable();
            for (Direction direction : Direction.values()) {
                mutable.set(currentBlockInfo.pos()).move(direction);
                if (currentChunkPos.x != mutable.getX() >> 4 || currentChunkPos.z != mutable.getZ() >> 4) {
                    currentChunkPos = new ChunkPos(mutable);
                    currentChunk = world.getChunk(currentChunkPos.x, currentChunkPos.z);
                    sectionYIndex = currentChunk.getSectionIndex(mutable.getY());
                    if (sectionYIndex < 0) {
                        return currentBlockInfo;
                    }
                    currChunkSection = currentChunk.getSection(sectionYIndex);
                }

                if (getFluidStateSafe(currChunkSection, mutable).isIn(FluidTags.WATER)) {
                    Optional<BlockState> blockState = getBlockStateSafe(currChunkSection, mutable);
                    if (blockState.isPresent() && !(blockState.get().contains(Properties.WATERLOGGED) && blockState.get().get(Properties.WATERLOGGED))) {
                        setBlockStateSafe(currChunkSection, mutable, Blocks.STONE_BRICKS.getDefaultState());
                    }
                }
            }
        }

        return currentBlockInfo;
    }

    protected StructureProcessorType<?> getType() {
        return ModProcessorTypes.WATERLOGFIX_PROCESSOR;
    }
}