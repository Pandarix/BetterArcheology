package net.Pandarix.betterarcheology.mixin;

import net.Pandarix.betterarcheology.BetterArcheology;
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
        if(ModConfigs.ARTIFACT_ENCHANTMENTS_ENABLED && this.getEquippedStack(EquipmentSlot.CHEST).hasEnchantments()){
            if(EnchantmentHelper.getLevel(ModEnchantments.SOARING_WINDS, this.getEquippedStack(EquipmentSlot.CHEST)) == 1){

                PlayerEntity player = (PlayerEntity) (Object) this;
                float boost = ModConfigs.SOARING_WINDS_BOOST * 0.5f;
                Vec3d vec3d = player.getRotationVector();
                Vec3d vec3d2 = player.getVelocity();
                player.setVelocity(vec3d2.add(vec3d.x * 0.1 + (vec3d.x * 1.5 - vec3d2.x) * boost, vec3d.y * 0.1 + (vec3d.y * 1.5 - vec3d2.y) * boost, vec3d.z * 0.1 + (vec3d.z * 1.5 - vec3d2.z) * boost));

                BetterArcheology.LOGGER.info("Enchantment is on Elytra");
            }
        }
        BetterArcheology.LOGGER.info("Player started flying");
    }
}
