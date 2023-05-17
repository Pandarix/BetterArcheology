package net.Pandarix.betterarcheology.mixin;

import net.Pandarix.betterarcheology.BetterArcheology;
import net.Pandarix.betterarcheology.block.custom.DiggableBlockEntity;
import net.Pandarix.betterarcheology.block.custom.FossiliferousBlock;
import net.Pandarix.betterarcheology.item.ShovelUsageInterface;
import net.Pandarix.betterarcheology.util.ServerPlayerHelper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.item.*;
import net.minecraft.particle.BlockStateParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Identifier;
import net.minecraft.util.UseAction;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.Objects;
import java.util.function.Predicate;

@Mixin(ShovelItem.class)
public class ShovelDiggingMixin implements ShovelUsageInterface {
    Identifier ADVANCEMENT_ID = new Identifier(BetterArcheology.MOD_ID, "fossiliferous_block_dug");
    @Inject(method = "useOnBlock", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemUsageContext;getSide()Lnet/minecraft/util/math/Direction;", shift = At.Shift.BEFORE), locals = LocalCapture.CAPTURE_FAILSOFT)
    private void injectMethod(ItemUsageContext context, CallbackInfoReturnable<ActionResult> cir, World world, BlockPos blockPos, BlockState blockState) {
        if (blockState.getBlock() instanceof FossiliferousBlock block) {
            PlayerEntity betterarcheology$player = context.getPlayer();
            Objects.requireNonNull(betterarcheology$player).playSound(block.getDiggingSound(), 0.35f, 0.25f);

            ItemUsage.consumeHeldItem(context.getWorld(), betterarcheology$player, betterarcheology$player.getActiveHand());
            if(!world.isClient()){
                ServerPlayerHelper.getServerPlayer(betterarcheology$player).getAdvancementTracker().grantCriterion(world.getServer().getAdvancementLoader().get(ADVANCEMENT_ID), "criteria");
            }
        }
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.BRUSH;
    }

    @Override
    public @NotNull HitResult getHitResult(LivingEntity user) {
        return ProjectileUtil.getCollision(user, Predicate.not(Entity::isSpectator), Math.sqrt(ServerPlayNetworkHandler.MAX_BREAK_SQUARED_DISTANCE) - 1.0);
    }

    @Override
    public int getMaxUseTime(ItemStack stack) {
        return 200;
    }

    @Override
    public void usageTick(World world, LivingEntity user, ItemStack stack, int remainingUseTicks) {
        if (remainingUseTicks >= 0 && user instanceof PlayerEntity playerEntity) {
            HitResult betterarcheology$hitResult = this.getHitResult(user);
            if (betterarcheology$hitResult instanceof BlockHitResult betterarcheology$blockHitResult) {
                if (betterarcheology$hitResult.getType() == HitResult.Type.BLOCK) {
                    int i = this.getMaxUseTime(stack) - remainingUseTicks + 1;

                    boolean bl = i % 10 == 5;
                    if (bl) {
                        BlockPos betterarcheology$blockPos = betterarcheology$blockHitResult.getBlockPos();
                        BlockState betterarcheology$blockState = world.getBlockState(betterarcheology$blockPos);
                        this.addDigParticles(world, betterarcheology$blockHitResult, betterarcheology$blockState);
                        Block betterarcheology$block = betterarcheology$blockState.getBlock();

                        SoundEvent betterarcheology$soundEvent;
                        if (betterarcheology$block instanceof FossiliferousBlock betterarcheology$fossiliferousBlock) {
                            betterarcheology$soundEvent = betterarcheology$fossiliferousBlock.getDiggingSound();
                        } else {
                            betterarcheology$soundEvent = SoundEvents.BLOCK_GRAVEL_BREAK;
                        }
                        world.playSound(playerEntity, betterarcheology$blockPos, betterarcheology$soundEvent, SoundCategory.BLOCKS);

                        if (!world.isClient()) {

                            BlockEntity betterarcheology$blockEntity = world.getBlockEntity(betterarcheology$blockPos);

                            if (betterarcheology$blockEntity instanceof DiggableBlockEntity betterarcheology$diggableBlockEntity) {
                                boolean bl2 = betterarcheology$diggableBlockEntity.dig(world.getTime(), playerEntity, betterarcheology$blockHitResult.getSide());
                                if (bl2) {
                                    EquipmentSlot betterarcheology$equipmentSlot = stack.equals(playerEntity.getEquippedStack(EquipmentSlot.OFFHAND)) ? EquipmentSlot.OFFHAND : EquipmentSlot.MAINHAND;
                                    stack.damage(1, user, (userx) -> {
                                        userx.sendEquipmentBreakStatus(betterarcheology$equipmentSlot);
                                    });
                                }
                            }
                        }
                    }

                    return;
                }
            }

            user.stopUsingItem();
        } else {
            user.stopUsingItem();
        }
    }

    @Override
    public void addDigParticles(World world, BlockHitResult hitResult, BlockState state) {
        Random betterarcheology$random = world.getRandom();

        int i = betterarcheology$random.nextBetweenExclusive(1, 3);
        BlockStateParticleEffect betterarcheology$blockStateParticleEffect = new BlockStateParticleEffect(ParticleTypes.BLOCK, state);

        BlockPos betterarcheology$pos = hitResult.getBlockPos();

        for (int j = 0; j < i; ++j) {
            world.addParticle(betterarcheology$blockStateParticleEffect, betterarcheology$pos.getX() + 0.5, betterarcheology$pos.getY() + 1, betterarcheology$pos.getZ() + 0.5, 3.0 * betterarcheology$random.nextDouble() * (betterarcheology$random.nextBoolean() ? 1:-1), 0.0, 3.0 * betterarcheology$random.nextDouble() * (betterarcheology$random.nextBoolean() ? 1:-1));
        }
    }
}
