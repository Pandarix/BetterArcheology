package net.Pandarix.betterarcheology.block.custom;

import net.Pandarix.betterarcheology.BetterArcheology;
import net.minecraft.block.BlockState;
import net.minecraft.block.CropBlock;
import net.minecraft.block.FlowerBlock;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class GrowthTotemBlock extends FlowerBlock {

    public GrowthTotemBlock(StatusEffect suspiciousStewEffect, int effectDuration, Settings settings) {
        super(suspiciousStewEffect, effectDuration, settings);
    }

    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        if (world.isClient() && random.nextBetween(0, 15) == 0) {
            for (int i = -5; i <= 5; i++) {
                for (int j = -5; j <= 5; j++) {
                    if (random.nextBetween(0, 3) == 3) {
                        Vec3d center = pos.add(i, 0, j).toCenterPos();
                        world.addParticle(ParticleTypes.INSTANT_EFFECT,
                                center.x + randomDirectionModifier(random, 3),
                                pos.getY(),
                                center.z + randomDirectionModifier(random, 3),
                                0, -5, 0
                        );
                    }
                }
            }
        }
        super.randomDisplayTick(state, world, pos, random);
    }

    @Override
    public boolean hasRandomTicks(BlockState state) {
        return true;
    }

    @Override
    public void randomTick(BlockState pState, ServerWorld world, BlockPos pPos, Random random) {
        if(random.nextBoolean()){return;}
        for (int i = -5; i <= 5; i++) {
            for (int j = -5; j <= 5; j++) {
                BlockPos pos = pPos.add(i, 0, j);
                BlockState state = world.getBlockState(pos);
                if (state.getBlock() instanceof CropBlock cropBlock) {
                    if (cropBlock.isFertilizable(world, pos, state)) {
                        if (cropBlock.canGrow(world, world.random, pos, state)) {
                            cropBlock.grow(world, world.random, pos, state);
                            if (random.nextBoolean()) {
                                world.playSound(null, pos, SoundEvents.BLOCK_AMETHYST_BLOCK_CHIME, SoundCategory.BLOCKS);
                            }
                        }
                    }
                }
            }
        }
        super.randomTick(pState, world, pPos, random);
    }

    @Override
    public void onSteppedOn(World world, BlockPos pos, BlockState state, Entity entity) {
        super.onSteppedOn(world, pos, state, entity);
        if (entity instanceof LivingEntity livingEntity) {
            livingEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.GLOWING, 100));
        }
    }

    private static float randomDirectionModifier(Random pRandom, int pReduce) {
        return ((pRandom.nextFloat() / pReduce) * pRandom.nextBetween(-1, 1));
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable BlockView world, List<Text> tooltip, TooltipContext options) {
        super.appendTooltip(stack, world, tooltip, options);
        tooltip.add(Text.translatable("block.betterarcheology.growth_totem_tooltip").formatted(Formatting.DARK_GREEN));
    }
}
