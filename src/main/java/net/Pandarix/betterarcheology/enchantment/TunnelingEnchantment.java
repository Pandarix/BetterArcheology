package net.Pandarix.betterarcheology.enchantment;

import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.PickaxeItem;
import net.minecraft.item.ShovelItem;

public class TunnelingEnchantment extends ArtifactEnchantment
{
    public TunnelingEnchantment(Rarity weight, EnchantmentTarget target, EquipmentSlot... slotTypes)
    {
        super(weight, target, slotTypes);
    }

    @Override
    public boolean isAcceptableItem(ItemStack stack)
    {
        return stack.getItem() instanceof PickaxeItem || stack.getItem() instanceof ShovelItem;
    }
}
