package net.Pandarix.betterarcheology.item;

import net.Pandarix.betterarcheology.BetterArcheology;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModItems {
    //ITEM ENTRIES-------------------------------------------------------------------------//
    //TODO: Add Items like this: public static final Item NAME = registerItem("name", new Item(new FabricItemSettings()));

    //REGISTERING--------------------------------------------------------------------------//

    /**
     * Registers given Item into Registry with BetterArcheology identifier
     *
     * @param name Item registry name entry
     * @param item Instance of Item
     * @return Registry entry of given Item
     */
    public static Item registerItem(String name, Item item){
        return Registry.register(Registries.ITEM, new Identifier(BetterArcheology.MOD_ID, name), item);
    }

    //Calls addToItemGroup on every Item in Registry
    //TODO: Add new Items like this: addToItemGroup(ModItemGroup.BETTER_ARCHEOLOGY_ITEMGROUP, ITEM);
    public static void addItemsToItemGroup(){
    }

    //Adds one Item to an ItemGroup
    public static void addToItemGroup(ItemGroup group, Item item){
        ItemGroupEvents.modifyEntriesEvent(group).register(entries -> entries.add(item));
    }

    //LOGGER-----------------------------------------------------------------------------//

    public static void registerModItems(){
        //status message
        BetterArcheology.LOGGER.info("Registering Items from " + BetterArcheology.MOD_ID);
        //adding Items
        addItemsToItemGroup();
    }
}
