package net.Pandarix.betterarcheology.enchantment;

import net.Pandarix.betterarcheology.BetterArcheology;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModEnchantments {
    public static Enchantment PENETRATING_STRIKE = register("penetrating_strike", new PenetratingStrikeEnchantment(Enchantment.Rarity.VERY_RARE, EquipmentSlot.MAINHAND));

    private static Enchantment register(String name, Enchantment enchantment) {
        return Registry.register(Registries.ENCHANTMENT, new Identifier(BetterArcheology.MOD_ID, name), enchantment);
    }

    public static void registerModEnchantments() {
        BetterArcheology.LOGGER.info("Registering Enchantments for " + BetterArcheology.MOD_ID);
    }
}
