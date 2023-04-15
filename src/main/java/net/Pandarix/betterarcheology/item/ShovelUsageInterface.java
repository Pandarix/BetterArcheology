package net.Pandarix.betterarcheology.item;

import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.BrushItem;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.BlockStateParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.UseAction;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

public interface ShovelUsageInterface {
    UseAction getUseAction(ItemStack stack);

    void usageTick(World world, LivingEntity user, ItemStack stack, int remainingUseTicks);

    @NotNull HitResult getHitResult(LivingEntity user);

    int getMaxUseTime(ItemStack stack);

    public void addDigParticles(World world, BlockHitResult hitResult, BlockState state);

}
