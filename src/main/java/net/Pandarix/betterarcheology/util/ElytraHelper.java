package net.Pandarix.betterarcheology.util;

import dev.emi.trinkets.api.TrinketsApi;
import net.Pandarix.betterarcheology.enchantment.ModEnchantments;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Vec3d;

public class ElytraHelper {
    /**
     * Checks if the given player has an Item with soaring winds equipped and returns the enchantment level
     * @param player the player to check
     * @return int the enchantment level of the item
     */
    public static int getSoaringWindsLevel(PlayerEntity player){
        //check the enchantment level of the chestplate
        int enchantmentLevel = getSafeEnchantLevel(ModEnchantments.SOARING_WINDS, player.getEquippedStack(EquipmentSlot.CHEST));
        if(enchantmentLevel > 0){
            return enchantmentLevel;
        }

        //if trinkets is not loaded skip it
        if (FabricLoader.getInstance().isModLoaded("trinkets")){
            var Trinkets = TrinketsApi.getTrinketComponent(player);
            if (Trinkets.isPresent()) {
                //check if the player has a trinket equipped with soaring winds and return the level
                var trinkets = Trinkets.get().getAllEquipped();
                for (var slot : trinkets) {
                    enchantmentLevel = getSafeEnchantLevel(ModEnchantments.SOARING_WINDS, slot.getRight());
                    if(enchantmentLevel > 0){
                        return enchantmentLevel;
                    }
                }
            }
        }

        return 0;
    }

    /**
     * Returns the enchantment level of an item, or 0 if the item has no enchantments
     * @param enchantment the enchantment to check
     * @param item the item to check
     * @return int the enchantment level of the item
     */
    public static int getSafeEnchantLevel(Enchantment enchantment, ItemStack item){
        if (!item.hasEnchantments()) {
            return 0;
        }
        return EnchantmentHelper.getLevel(enchantment, item);
    }

    /**
     * Applies a boost to a given flying player.
     * The boost vector is first multiplied with the rotation vector component wise
     * and then added to the velocity vector. This boosts the current velocity
     * in the direction of the boost vector.
     *
     * @param player the player to boost
     * @param vec_boost the boost vector
     */
    public static void applyElytraBoost(PlayerEntity player, Vec3d vec_boost){
        if (!player.isFallFlying()){
            return;
        }

        Vec3d vec_rot = player.getRotationVector();
        Vec3d vec_vel = player.getVelocity();

        //first multiply the boost vector with the rotation vector component wise
        //then add the result to the velocity vector to boost the current velocity by the boost vector
        Vec3d vec_res = vec_vel.add(vec_rot.multiply(vec_boost));

        //set the new velocity
        player.setVelocity(vec_res);
    }
}
