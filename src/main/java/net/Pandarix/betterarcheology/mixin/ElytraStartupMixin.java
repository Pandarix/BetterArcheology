package net.Pandarix.betterarcheology.mixin;

import net.Pandarix.betterarcheology.enchantment.ModEnchantments;
import net.Pandarix.betterarcheology.util.ModConfigs;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public abstract class ElytraStartupMixin {
    @Shadow public abstract ItemStack getEquippedStack(EquipmentSlot slot);

    @Inject(method = "startFallFlying", at = @At(value = "TAIL"))
    private void injectMethod(CallbackInfo ci){
        //if it is enabled in the config and the chestslot is enchanted
        if(ModConfigs.ARTIFACT_ENCHANTMENTS_ENABLED && this.getEquippedStack(EquipmentSlot.CHEST).hasEnchantments()){
            //if the enchantment in the chestslot is soaring winds
            if(EnchantmentHelper.getLevel(ModEnchantments.SOARING_WINDS, this.getEquippedStack(EquipmentSlot.CHEST)) == 1){

                PlayerEntity betterarcheology$player = (PlayerEntity) (Object) this;
                float betterarcheology$boost = ModConfigs.SOARING_WINDS_BOOST * 0.5f;
                Vec3d betterarcheology$vec3d = betterarcheology$player.getRotationVector();
                Vec3d betterarcheology$vec3d2 = betterarcheology$player.getVelocity();

                //add player velocity when starting to fall-fly
                betterarcheology$player.setVelocity(betterarcheology$vec3d2.add(
                        betterarcheology$vec3d.x * 0.1 + (betterarcheology$vec3d.x * 1.5 - betterarcheology$vec3d2.x) * betterarcheology$boost,
                        betterarcheology$vec3d.y * 0.1 + (betterarcheology$vec3d.y * 1.5 - betterarcheology$vec3d2.y) * betterarcheology$boost/2,
                        betterarcheology$vec3d.z * 0.1 + (betterarcheology$vec3d.z * 1.5 - betterarcheology$vec3d2.z) * betterarcheology$boost));
            }
        }
    }
}
