package net.Pandarix.betterarcheology.enchantment;

import net.Pandarix.betterarcheology.BetterArcheology;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.AxeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;

public class PenetratingStrikeEnchantment extends Enchantment {

    public PenetratingStrikeEnchantment(Rarity weight, EquipmentSlot... slotTypes) {
        super(weight, EnchantmentTarget.WEAPON, slotTypes);
    }

    //also allowing axes
    public boolean isAcceptableItem(ItemStack stack) {
        return stack.getItem() instanceof AxeItem ? true : super.isAcceptableItem(stack);
    }

    //Enchantment Functionality-------------------------------------------------------------------------//
    @Override
    public int getMaxLevel() {
        return 1;
    }

    /*
    Decreases the damage reduction caused from the target's armor
    by dealing additional damage relative to the armor and level
    for reference, see: https://minecraft.fandom.com/wiki/Armor#Enchantments
     */
    @Override
    public void onTargetDamaged(LivingEntity user, Entity target, int level) {
        //calculate total Protection of Armor
        int enchantmentProtectionFactor = 0;
        for (ItemStack itemStack : target.getArmorItems()) {
            enchantmentProtectionFactor += EnchantmentHelper.getLevel(Enchantments.PROTECTION, itemStack);
        }
        //capping the protection at 20, as minecraft does it
        enchantmentProtectionFactor = Math.min(enchantmentProtectionFactor, 20);

        //damage in % that was subtracted due to the Enchantments' protections
        double damagePercentageProtected = enchantmentProtectionFactor / 25f;

        //damagevalue of the current weapon
        float damageInflicted = 0;

        //set to value of getAttackDamage
        //method is not inherited, therefore a hard if-check is needed
        if (user.getMainHandStack().getItem() instanceof SwordItem) {
            damageInflicted = ((SwordItem) user.getMainHandStack().getItem()).getAttackDamage() + 1;
        } else if (user.getMainHandStack().getItem() instanceof AxeItem) {
            damageInflicted = ((AxeItem) user.getMainHandStack().getItem()).getAttackDamage() + 1;
        }

        //calculates total damage that was reduced
        float totalProtectedDamage = (float) (damageInflicted * damagePercentageProtected);

        BetterArcheology.LOGGER.info("Total Armor of Target: " + enchantmentProtectionFactor);
        BetterArcheology.LOGGER.info("Total Damage Reduction: " + totalProtectedDamage + " bzw. " + damagePercentageProtected + "%");
        BetterArcheology.LOGGER.info("Total Damage Weapon would inflict: " + damageInflicted);

        //applies damage based on enchantment level
        if (level == 1) {
            target.damage(user.getDamageSources().mobAttack(user), (float) (totalProtectedDamage * 0.15));
            BetterArcheology.LOGGER.info("Damage dealt back by Enchantment:" + totalProtectedDamage * 0.15);
        }

        //Audio Feedback
        if (!user.getWorld().isClient()) {
            user.getWorld().playSound(null, target.getBlockPos(), SoundEvents.ITEM_ARMOR_EQUIP_CHAIN, SoundCategory.BLOCKS);
        }
    }

    //Restricting any other access to the Enchantment than Archeology-----------------------------------//
    @Override
    public boolean isAvailableForEnchantedBookOffer() {
        return false;
    }

    @Override
    public boolean isTreasure() {
        return false;
    }

    @Override
    public boolean isAvailableForRandomSelection() {
        return false;
    }

    @Override
    public Rarity getRarity() {
        return Rarity.VERY_RARE;
    }
}

