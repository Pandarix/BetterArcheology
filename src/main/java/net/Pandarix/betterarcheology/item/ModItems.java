package net.Pandarix.betterarcheology.item;

import net.Pandarix.betterarcheology.BetterArcheology;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModItems {
    //ITEM ENTRIES-------------------------------------------------------------------------//
    //TODO: Add Items like this: public static final Item NAME = registerItem("name", new Item(new FabricItemSettings()), ModItemGroup.BETTER_ARCHEOLOGY_ITEMGROUP);

    //REGISTERING--------------------------------------------------------------------------//
    /**
     * Registers given Item into Registry with BetterArcheology identifier
     *
     * @param name Item registry name entry
     * @param item Instance of Item
     * @return Registry entry of given Item
     */
    public static Item registerItem(String name, Item item, ItemGroup group){
        Item registeredItem = Registry.register(Registries.ITEM, new Identifier(BetterArcheology.MOD_ID, name), item);

        ItemGroupEvents.modifyEntriesEvent(group).register(entries -> entries.add(registeredItem));
        return registeredItem;
    }

    //LOGGER-----------------------------------------------------------------------------//
    public static void registerModItems(){
        //status message
        BetterArcheology.LOGGER.info("Registering Items from " + BetterArcheology.MOD_ID);
    }
}
