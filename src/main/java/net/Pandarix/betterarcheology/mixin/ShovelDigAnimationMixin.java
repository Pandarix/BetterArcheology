package net.Pandarix.betterarcheology.mixin;

import net.Pandarix.betterarcheology.BetterArcheology;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.item.HeldItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ShovelItem;
import net.minecraft.util.Arm;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RotationAxis;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(HeldItemRenderer.class)
public abstract class ShovelDigAnimationMixin {
    @Shadow
    protected abstract void applyBrushTransformation(MatrixStack matrices, float tickDelta, Arm arm, ItemStack stack, float equipProgress);

    @Shadow
    protected abstract void applyEquipOffset(MatrixStack matrices, Arm arm, float equipProgress);

    @Shadow
    @Final
    private MinecraftClient client;

    @Redirect(method = "renderFirstPersonItem", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/item/HeldItemRenderer;applyBrushTransformation(Lnet/minecraft/client/util/math/MatrixStack;FLnet/minecraft/util/Arm;Lnet/minecraft/item/ItemStack;F)V"))
    private void injectMethod(HeldItemRenderer instance, MatrixStack matrices, float tickDelta, Arm arm, ItemStack stack, float equipProgress) {
        if (stack.getItem() instanceof ShovelItem) {
            applyDigTransformation(matrices, tickDelta, arm, stack, equipProgress);
        } else {
            this.applyBrushTransformation(matrices, tickDelta, arm, stack, equipProgress);
        }
    }


    private void applyDigTransformation(MatrixStack matrices, float tickDelta, Arm arm, ItemStack stack, float equipProgress) {
        this.applyEquipOffset(matrices, arm, equipProgress);

        float betterarcheology$n = MathHelper.sin(this.client.player.getItemUseTimeLeft() / 5F * (float) Math.PI) * 45 + 45;

        if (arm != Arm.RIGHT) {
            //matrices.translate(0.1, 0.83, 0.35);
            matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(-145.0F));
            matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(115.0F));
            //matrices.translate(-0.1, -0.5, -0.3);
            matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(betterarcheology$n));
        } else {
            matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(-145.0F));
            matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(35.0F));
            matrices.translate(-0.1, -0.5, -0.3);
            matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(betterarcheology$n));
        }

    }
}