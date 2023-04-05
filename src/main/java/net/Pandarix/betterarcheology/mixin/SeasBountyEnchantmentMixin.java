package net.Pandarix.betterarcheology.mixin;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.Pandarix.betterarcheology.BetterArcheology;
import net.Pandarix.betterarcheology.enchantment.ModEnchantments;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.projectile.FishingBobberEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.context.LootContext;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(FishingBobberEntity.class)
public abstract class SeasBountyEnchantmentMixin {
    private static final Identifier enchantedLootTable = new Identifier(BetterArcheology.MOD_ID, "seas_bounty");

    @Redirect(method = "use", at = @At(value = "INVOKE", target = "Lnet/minecraft/loot/LootTable;generateLoot(Lnet/minecraft/loot/context/LootContext;)Lit/unimi/dsi/fastutil/objects/ObjectArrayList;"))
    //INJECTED CODE-------------------------------------------------------------------------//
    private ObjectArrayList<ItemStack> injectMethod(LootTable lootTable, LootContext context, ItemStack usedItem) {
        //if there are no enchantments, we can save ourselves the more complex check
        if (usedItem.hasEnchantments()) {
            //if the used Item has the SeasBounty Enchantment at Level 1
            if (EnchantmentHelper.getLevel(ModEnchantments.SEAS_BOUNTY, usedItem) == 1) {
                return context.getWorld().getServer().getLootManager().getTable(enchantedLootTable).generateLoot(context);
            }
        }
        return lootTable.generateLoot(context);
    }

}
