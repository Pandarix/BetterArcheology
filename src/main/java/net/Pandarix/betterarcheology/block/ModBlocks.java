package net.Pandarix.betterarcheology.block;

import net.Pandarix.betterarcheology.BetterArcheology;
import net.Pandarix.betterarcheology.block.custom.ArchelogyTable;
import net.Pandarix.betterarcheology.block.custom.InfestedMudBricks;
import net.Pandarix.betterarcheology.item.ModItemGroup;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
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
    public static final Block NAME = registerBlock("name", new Block(FabricBlockSettings.of(Material.MATERIAL).otherSettings()), ModItemGroup.BETTER_ARCHEOLOGY_ITEMGROUP);
     */

    //-----------ROTTEN WOOD-------------//
    public static final Block ROTTEN_LOG = registerBlock("rotten_log",new PillarBlock(FabricBlockSettings.copy(Blocks.OAK_LOG).sounds(BlockSoundGroup.NETHER_STEM)), ModItemGroup.BETTER_ARCHEOLOGY_ITEMGROUP);

    public static final Block ROTTEN_PLANKS = registerBlock("rotten_planks",new Block(FabricBlockSettings.copy(Blocks.OAK_PLANKS).sounds(BlockSoundGroup.NETHER_STEM)), ModItemGroup.BETTER_ARCHEOLOGY_ITEMGROUP);

    public static final Block ROTTEN_SLAB = registerBlock("rotten_slab",new SlabBlock(FabricBlockSettings.copy(Blocks.OAK_SLAB).sounds(BlockSoundGroup.NETHER_STEM)), ModItemGroup.BETTER_ARCHEOLOGY_ITEMGROUP);

    public static final Block ROTTEN_STAIRS = registerBlock("rotten_stairs",new StairsBlock(ROTTEN_PLANKS.getDefaultState(),FabricBlockSettings.copy(Blocks.OAK_STAIRS).sounds(BlockSoundGroup.NETHER_STEM)), ModItemGroup.BETTER_ARCHEOLOGY_ITEMGROUP);


    //-------------MUD Brick Stuff----------------//
    public static final Block INFESTED_MUD_BRICKS = registerBlock("infested_mud_bricks", new InfestedMudBricks(Blocks.MUD_BRICKS, FabricBlockSettings.copy(Blocks.INFESTED_STONE_BRICKS)), ModItemGroup.BETTER_ARCHEOLOGY_ITEMGROUP);

    public static final Block CRACKED_MUD_BRICKS = registerBlock("cracked_mud_bricks", new Block(FabricBlockSettings.copy(Blocks.MUD_BRICKS)), ModItemGroup.BETTER_ARCHEOLOGY_ITEMGROUP);

    public static final Block CRACKED_MUD_BRICK_SLAB = registerBlock("cracked_mud_brick_slab",new SlabBlock(FabricBlockSettings.copy(Blocks.MUD_BRICK_SLAB)), ModItemGroup.BETTER_ARCHEOLOGY_ITEMGROUP);


    public static final Block CRACKED_MUD_BRICK_STAIRS = registerBlock("cracked_mud_brick_stairs",new StairsBlock(CRACKED_MUD_BRICKS.getDefaultState(),FabricBlockSettings.copy(Blocks.MUD_BRICK_STAIRS)), ModItemGroup.BETTER_ARCHEOLOGY_ITEMGROUP);
    
    public static final Block ARCHEOLOGY_TABLE = registerBlock("archeology_table", new ArchelogyTable(FabricBlockSettings.of(Material.WOOD).nonOpaque()), ModItemGroup.BETTER_ARCHEOLOGY_ITEMGROUP);

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