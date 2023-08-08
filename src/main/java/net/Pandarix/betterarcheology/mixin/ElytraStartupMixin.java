package net.Pandarix.betterarcheology.mixin;

import net.Pandarix.betterarcheology.util.ModConfigs;
import net.Pandarix.betterarcheology.util.ElytraHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public abstract class ElytraStartupMixin {
    @Inject(method = "startFallFlying", at = @At(value = "TAIL"))
    private void injectMethod(CallbackInfo ci){
        if(ModConfigs.ARTIFACT_ENCHANTMENTS_ENABLED) {
            PlayerEntity betterarcheology$player = (PlayerEntity) (Object) this;
            int betterarcheology$enchantmentLevel = ElytraHelper.getSoaringWindsLevel(betterarcheology$player);

            //actually launch with soaring winds
            if (betterarcheology$enchantmentLevel > 0) {
                float betterarcheology$boost = betterarcheology$enchantmentLevel * ModConfigs.SOARING_WINDS_BOOST * 0.5f;

                ElytraHelper.applyElytraBoost(betterarcheology$player, new Vec3d(betterarcheology$boost, betterarcheology$boost/2, betterarcheology$boost));
            }
        }
    }
}
