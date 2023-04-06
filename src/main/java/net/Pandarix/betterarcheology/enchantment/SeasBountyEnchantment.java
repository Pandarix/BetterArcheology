package net.Pandarix.betterarcheology.enchantment;

import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.FishingRodItem;
import net.minecraft.item.ItemStack;

public class SeasBountyEnchantment extends ArtifactEnchantment {
    protected SeasBountyEnchantment(Rarity weight, EnchantmentTarget target, EquipmentSlot... slotTypes) {
        super(weight, target, slotTypes);
    }

    @Override
    public int getMaxLevel() {
        return 1;
    }

    @Override
    public boolean isAcceptableItem(ItemStack stack) {
        return stack.getItem() instanceof FishingRodItem;
    }

}
