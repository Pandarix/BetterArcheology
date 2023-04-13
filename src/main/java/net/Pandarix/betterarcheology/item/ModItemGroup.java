package net.Pandarix.betterarcheology.item;

import net.Pandarix.betterarcheology.BetterArcheology;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ModItemGroup {
    public static ItemGroup BETTER_ARCHEOLOGY_ITEMGROUP;    //CreativeTab for Better Archeology

    /*
    Gets called in onInitialise before the Item-Registration
    Creates FabricItemGroup with an ItemStack as a Symbol
    The Name of the Tab is located in the .lang file under "itemGroup.betterarcheology"
     */
    public static void registerTab() {
        BETTER_ARCHEOLOGY_ITEMGROUP = FabricItemGroup.builder(
                        new Identifier(BetterArcheology.MOD_ID))
                .displayName(Text.translatable("itemGroup.betterarcheology"))
                .icon(() -> new ItemStack(ModItems.UNIDENTIFIED_ARTIFACT))
                .build();
    }
}

