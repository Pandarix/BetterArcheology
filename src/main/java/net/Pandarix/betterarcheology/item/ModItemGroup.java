package net.Pandarix.betterarcheology.item;

import net.Pandarix.betterarcheology.BetterArcheology;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ModItemGroup {
    /*
    Gets called in onInitialize before the Item-Registration
    Creates FabricItemGroup with an ItemStack as a Symbol
    The Name of the Tab is located in the .lang file under "itemGroup.betterarcheology"
     */
    public static final RegistryKey<ItemGroup> BETTER_ARCHEOLOGY_ITEMGROUP = RegistryKey.of(RegistryKeys.ITEM_GROUP, new Identifier(BetterArcheology.MOD_ID, "betterarcheology"));

    public static void registerTab() {
        Registry.register(Registries.ITEM_GROUP, BETTER_ARCHEOLOGY_ITEMGROUP, FabricItemGroup.builder().displayName(Text.translatable("itemGroup." + BetterArcheology.MOD_ID)).icon(() -> new ItemStack(ModItems.UNIDENTIFIED_ARTIFACT)).entries((context, entries) -> {
        }).build());
    }
}

