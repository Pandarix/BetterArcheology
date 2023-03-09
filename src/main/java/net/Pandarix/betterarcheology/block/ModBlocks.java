package net.Pandarix.betterarcheology.block;

import net.Pandarix.betterarcheology.BetterArcheology;
import net.Pandarix.betterarcheology.item.ModItemGroup;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.Material;
import net.minecraft.block.PillarBlock;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;

public class ModBlocks {
    //ITEM ENTRIES-------------------------------------------------------------------------//
    /*
    TODO: Add Items like this:
    public static final Block NAME = registerBlock("name", new Block(FabricBlockSettings.copy(BLocks.BLOCK)), ModItemGroup.BETTER_ARCHEOLOGY_ITEMGROUP);
    oder
    public static final Block NAME = registerBlock("name", new Block(FabricBlockSettings.of(Material.MATERIAL).weitereSettings()), ModItemGroup.BETTER_ARCHEOLOGY_ITEMGROUP);
     */

    public static final Block ROTTEN_LOG = registerBlock("rotten_log",new PillarBlock(FabricBlockSettings.copy(Blocks.OAK_LOG).sounds(BlockSoundGroup.NETHER_STEM)), ModItemGroup.BETTER_ARCHEOLOGY_ITEMGROUP);

    //REGISTERING--------------------------------------------------------------------------//
    //Registers Block and calls registerBlockItem to add it as an Item as well
    private static Block registerBlock(String name, Block block, ItemGroup group){
        registerBlockItem(name, block, group);
        return Registry.register(Registries.BLOCK, new Identifier(BetterArcheology.MOD_ID, name), block);
    }

    //Registers given Block as an BlockItem and adds it to an ItemGroup
    private static Item registerBlockItem(String name, Block block, ItemGroup group){
        Item item = Registry.register(Registries.ITEM, new Identifier(BetterArcheology.MOD_ID, name), new BlockItem(block, new FabricItemSettings()));

        ItemGroupEvents.modifyEntriesEvent(group).register(entries -> entries.add(item));
        return item;
    }

    //LOGGER-----------------------------------------------------------------------------//
    public static void registerModBlocks(){
        BetterArcheology.LOGGER.info("Registering Blocks from " + BetterArcheology.MOD_ID);
    }
}