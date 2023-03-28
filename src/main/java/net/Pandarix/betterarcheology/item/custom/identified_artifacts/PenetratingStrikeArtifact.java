package net.Pandarix.betterarcheology.item.custom.identified_artifacts;

import net.Pandarix.betterarcheology.BetterArcheology;
import net.Pandarix.betterarcheology.enchantment.ModEnchantments;
import net.Pandarix.betterarcheology.item.ModItems;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentLevelEntry;
import net.minecraft.entity.Entity;
import net.minecraft.item.EnchantedBookItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class PenetratingStrikeArtifact extends EnchantedBookItem {

    public PenetratingStrikeArtifact(Settings settings) {
        super(settings);
    }

    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(Text.translatable(Util.createTranslationKey("item", new Identifier(BetterArcheology.MOD_ID, "penetrating_tooltip"))).formatted(Formatting.GRAY));
        super.appendTooltip(stack, world, tooltip, context);
    }

    public static ItemStack withEnchantment() {
        ItemStack itemStack = new ItemStack(ModItems.PENETRATING_STRIKE_ARTIFACT);
        addEnchantment(itemStack);
        return itemStack;
    }

    public static void addEnchantment(ItemStack stack) {
        EnchantmentLevelEntry entry = new EnchantmentLevelEntry(ModEnchantments.PENETRATING_STRIKE, 1);
        NbtList nbtList = getEnchantmentNbt(stack);
        boolean bl = true;
        Identifier identifier = EnchantmentHelper.getEnchantmentId(entry.enchantment);

        for(int i = 0; i < nbtList.size(); ++i) {
            NbtCompound nbtCompound = nbtList.getCompound(i);
            Identifier identifier2 = EnchantmentHelper.getIdFromNbt(nbtCompound);
            if (identifier2 != null && identifier2.equals(identifier)) {
                if (EnchantmentHelper.getLevelFromNbt(nbtCompound) < entry.level) {
                    EnchantmentHelper.writeLevelToNbt(nbtCompound, entry.level);
                }

                bl = false;
                break;
            }
        }

        if (bl) {
            nbtList.add(EnchantmentHelper.createNbt(identifier, entry.level));
        }

        stack.getOrCreateNbt().put("StoredEnchantments", nbtList);
    }
}
