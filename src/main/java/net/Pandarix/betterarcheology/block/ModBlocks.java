package net.Pandarix.betterarcheology.block;

import net.Pandarix.betterarcheology.BetterArcheology;
import net.Pandarix.betterarcheology.block.custom.*;
import net.Pandarix.betterarcheology.item.ModItemGroup;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.type.BlockSetTypeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.block.type.WoodTypeRegistry;
import net.minecraft.block.*;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;

public class ModBlocks {
    //ITEM ENTRIES-------------------------------------------------------------------------//
    /*
    TODO: Add Items like this:
    public static final Block NAME = registerBlock("name", new Block(FabricBlockSettings.copy(BLocks.BLOCK)), ModItemGroup.BETTER_ARCHEOLOGY_ITEMGROUP);
    oder
    public static final Block NAME = registerBlock("name", new Block(FabricBlockSettings.of(Material.MATERIAL).otherSettings()), ModItemGroup.BETTER_ARCHEOLOGY_ITEMGROUP);
     */

    //-----------SUS VARIANTS-------------//
    public static final Block SUSPICIOUS_RED_SAND = registerBlock("suspicious_red_sand", new SusBlock(Blocks.RED_SAND, FabricBlockSettings.copy(Blocks.SUSPICIOUS_SAND), SoundEvents.ITEM_BRUSH_BRUSHING_SAND, SoundEvents.ITEM_BRUSH_BRUSHING_SAND_COMPLETE), ModItemGroup.BETTER_ARCHEOLOGY_ITEMGROUP);

    public static final Block SUSPICIOUS_DIRT = registerBlock("suspicious_dirt", new SusBlock(Blocks.DIRT, FabricBlockSettings.copy(Blocks.SUSPICIOUS_SAND), SoundEvents.ITEM_BRUSH_BRUSHING_GRAVEL, SoundEvents.ITEM_BRUSH_BRUSHING_GRAVEL_COMPLETE), ModItemGroup.BETTER_ARCHEOLOGY_ITEMGROUP);

    //---------FOSSILIFEROUS BLOCKS-----------//
    public static final Block FOSSILIFEROUS_DIRT = registerBlock("fossiliferous_dirt", new FossiliferousBlock(Blocks.DIRT, FabricBlockSettings.copy(Blocks.DIRT), SoundEvents.BLOCK_GRAVEL_HIT, SoundEvents.BLOCK_GRAVEL_BREAK),
            ModItemGroup.BETTER_ARCHEOLOGY_ITEMGROUP);

    //-------------FOSSILS---------------//
    //Villager
    public static final Block VILLAGER_FOSSIL = registerBlockWithRarity("villager_fossil", new VillagerFossilBlock(FabricBlockSettings.copy(Blocks.BONE_BLOCK).luminance((state) -> {
        return state.get(VillagerFossilBlock.INVENTORY_LUMINANCE);
    })), ModItemGroup.BETTER_ARCHEOLOGY_ITEMGROUP, Rarity.UNCOMMON);

    public static final Block VILLAGER_FOSSIL_HEAD = registerBlockWithRarity("villager_fossil_head", new VillagerFossilHeadBlock(FabricBlockSettings.copy(Blocks.SKELETON_SKULL).sounds(BlockSoundGroup.BONE)), ModItemGroup.BETTER_ARCHEOLOGY_ITEMGROUP, Rarity.UNCOMMON);

    public static final Block VILLAGER_FOSSIL_BODY = registerBlockWithRarity("villager_fossil_body", new VillagerFossilBodyBlock(FabricBlockSettings.copy(Blocks.SKELETON_SKULL).sounds(BlockSoundGroup.BONE)), ModItemGroup.BETTER_ARCHEOLOGY_ITEMGROUP, Rarity.UNCOMMON);

    //Ocelot
    public static final Block OCELOT_FOSSIL = registerBlockWithRarity("ocelot_fossil", new OcelotFossilBlock(FabricBlockSettings.copy(Blocks.BONE_BLOCK)), ModItemGroup.BETTER_ARCHEOLOGY_ITEMGROUP, Rarity.UNCOMMON);

    public static final Block OCELOT_FOSSIL_HEAD = registerBlockWithRarity("ocelot_fossil_head", new OcelotFossilHeadBlock(FabricBlockSettings.copy(Blocks.SKELETON_SKULL).sounds(BlockSoundGroup.BONE)), ModItemGroup.BETTER_ARCHEOLOGY_ITEMGROUP, Rarity.UNCOMMON);

    public static final Block OCELOT_FOSSIL_BODY = registerBlockWithRarity("ocelot_fossil_body", new OcelotFossilBodyBlock(FabricBlockSettings.copy(Blocks.SKELETON_SKULL).sounds(BlockSoundGroup.BONE)), ModItemGroup.BETTER_ARCHEOLOGY_ITEMGROUP, Rarity.UNCOMMON);

    //Sheep
    public static final Block SHEEP_FOSSIL = registerBlockWithRarity("sheep_fossil", new SheepFossilBlock(FabricBlockSettings.copy(Blocks.BONE_BLOCK)), ModItemGroup.BETTER_ARCHEOLOGY_ITEMGROUP, Rarity.UNCOMMON);

    public static final Block SHEEP_FOSSIL_HEAD = registerBlockWithRarity("sheep_fossil_head", new SheepFossilHeadBlock(FabricBlockSettings.copy(Blocks.SKELETON_SKULL).sounds(BlockSoundGroup.BONE)), ModItemGroup.BETTER_ARCHEOLOGY_ITEMGROUP, Rarity.UNCOMMON);

    public static final Block SHEEP_FOSSIL_BODY = registerBlockWithRarity("sheep_fossil_body", new SheepFossilBodyBlock(FabricBlockSettings.copy(Blocks.SKELETON_SKULL).sounds(BlockSoundGroup.BONE)), ModItemGroup.BETTER_ARCHEOLOGY_ITEMGROUP, Rarity.UNCOMMON);

    //Sheep
    public static final Block CHICKEN_FOSSIL = registerBlockWithRarity("chicken_fossil", new ChickenFossilBlock(FabricBlockSettings.copy(Blocks.BONE_BLOCK)), ModItemGroup.BETTER_ARCHEOLOGY_ITEMGROUP, Rarity.UNCOMMON);

    public static final Block CHICKEN_FOSSIL_HEAD = registerBlockWithRarity("chicken_fossil_head", new ChickenFossilHeadBlock(FabricBlockSettings.copy(Blocks.SKELETON_SKULL).sounds(BlockSoundGroup.BONE)), ModItemGroup.BETTER_ARCHEOLOGY_ITEMGROUP, Rarity.UNCOMMON);

    public static final Block CHICKEN_FOSSIL_BODY = registerBlockWithRarity("chicken_fossil_body", new ChickenFossilBodyBlock(FabricBlockSettings.copy(Blocks.SKELETON_SKULL).sounds(BlockSoundGroup.BONE)), ModItemGroup.BETTER_ARCHEOLOGY_ITEMGROUP, Rarity.UNCOMMON);

    //Creeper
    public static final Block CREEPER_FOSSIL = registerBlockWithRarity("creeper_fossil", new CreeperFossilBlock(FabricBlockSettings.copy(Blocks.BONE_BLOCK)), ModItemGroup.BETTER_ARCHEOLOGY_ITEMGROUP, Rarity.UNCOMMON);

    public static final Block CREEPER_FOSSIL_HEAD = registerBlockWithRarity("creeper_fossil_head", new CreeperFossilHeadBlock(FabricBlockSettings.copy(Blocks.SKELETON_SKULL).sounds(BlockSoundGroup.BONE)), ModItemGroup.BETTER_ARCHEOLOGY_ITEMGROUP, Rarity.UNCOMMON);

    public static final Block CREEPER_FOSSIL_BODY = registerBlockWithRarity("creeper_fossil_body", new CreeperFossilBodyBlock(FabricBlockSettings.copy(Blocks.SKELETON_SKULL).sounds(BlockSoundGroup.BONE)), ModItemGroup.BETTER_ARCHEOLOGY_ITEMGROUP, Rarity.UNCOMMON);

    //-----------ROTTEN WOOD-------------//
    public static final WoodType ROTTEN_WOOD_TYPE = registerWoodType("rotten_wood");
    public static final BlockSetType ROTTEN_WOOD_BLOCKSET = registerBlockSetType("rotten_wood");

    public static final Block ROTTEN_LOG = registerBlock("rotten_log", new PillarBlock(FabricBlockSettings.copy(Blocks.OAK_LOG).sounds(BlockSoundGroup.NETHER_STEM)), ModItemGroup.BETTER_ARCHEOLOGY_ITEMGROUP);

    public static final Block ROTTEN_PLANKS = registerBlock("rotten_planks", new Block(FabricBlockSettings.copy(Blocks.OAK_PLANKS).sounds(BlockSoundGroup.NETHER_STEM)), ModItemGroup.BETTER_ARCHEOLOGY_ITEMGROUP);

    public static final Block ROTTEN_SLAB = registerBlock("rotten_slab", new SlabBlock(FabricBlockSettings.copy(Blocks.OAK_SLAB).sounds(BlockSoundGroup.NETHER_STEM)), ModItemGroup.BETTER_ARCHEOLOGY_ITEMGROUP);

    public static final Block ROTTEN_STAIRS = registerBlock("rotten_stairs", new StairsBlock(ROTTEN_PLANKS.getDefaultState(), FabricBlockSettings.copy(Blocks.OAK_STAIRS).sounds(BlockSoundGroup.NETHER_STEM)), ModItemGroup.BETTER_ARCHEOLOGY_ITEMGROUP);

    public static final Block ROTTEN_FENCE = registerBlock("rotten_fence", new FenceBlock(FabricBlockSettings.copy(Blocks.OAK_FENCE).sounds(BlockSoundGroup.NETHER_STEM)), ModItemGroup.BETTER_ARCHEOLOGY_ITEMGROUP);

    public static final Block ROTTEN_FENCE_GATE = registerBlock("rotten_fence_gate", new FenceGateBlock(FabricBlockSettings.copy(Blocks.OAK_FENCE_GATE).sounds(BlockSoundGroup.NETHER_STEM), ROTTEN_WOOD_TYPE), ModItemGroup.BETTER_ARCHEOLOGY_ITEMGROUP);

    public static final Block ROTTEN_TRAPDOOR = registerBlock("rotten_trapdoor", new TrapdoorBlock(FabricBlockSettings.copy(Blocks.OAK_TRAPDOOR).sounds(BlockSoundGroup.NETHER_STEM), ROTTEN_WOOD_BLOCKSET), ModItemGroup.BETTER_ARCHEOLOGY_ITEMGROUP);

    public static final Block ROTTEN_DOOR = registerBlock("rotten_door", new DoorBlock(FabricBlockSettings.copy(Blocks.OAK_DOOR).sounds(BlockSoundGroup.NETHER_STEM), ROTTEN_WOOD_BLOCKSET), ModItemGroup.BETTER_ARCHEOLOGY_ITEMGROUP);

    //-------------MUD Brick Stuff----------------//
    public static final Block INFESTED_MUD_BRICKS = registerBlock("infested_mud_bricks", new InfestedMudBricks(Blocks.MUD_BRICKS, FabricBlockSettings.copy(Blocks.INFESTED_STONE_BRICKS)), ModItemGroup.BETTER_ARCHEOLOGY_ITEMGROUP);

    public static final Block CRACKED_MUD_BRICKS = registerBlock("cracked_mud_bricks", new Block(FabricBlockSettings.copy(Blocks.MUD_BRICKS)), ModItemGroup.BETTER_ARCHEOLOGY_ITEMGROUP);

    public static final Block CRACKED_MUD_BRICK_SLAB = registerBlock("cracked_mud_brick_slab", new SlabBlock(FabricBlockSettings.copy(Blocks.MUD_BRICK_SLAB)), ModItemGroup.BETTER_ARCHEOLOGY_ITEMGROUP);

    public static final Block CRACKED_MUD_BRICK_STAIRS = registerBlock("cracked_mud_brick_stairs", new StairsBlock(CRACKED_MUD_BRICKS.getDefaultState(), FabricBlockSettings.copy(Blocks.MUD_BRICK_STAIRS)), ModItemGroup.BETTER_ARCHEOLOGY_ITEMGROUP);

    public static final Block ARCHEOLOGY_TABLE = registerBlock("archeology_table", new ArchelogyTable(FabricBlockSettings.copy(Blocks.CRAFTING_TABLE)), ModItemGroup.BETTER_ARCHEOLOGY_ITEMGROUP);

    public static final Block LOOT_VASE = registerBlockWithoutTab("loot_vase", new LootVaseBlock(FabricBlockSettings.copy(Blocks.FLOWER_POT).sounds(BlockSoundGroup.DECORATED_POT)));
    public static final Block VASE = registerBlock("vase", new VaseBlock(FabricBlockSettings.copy(Blocks.FLOWER_POT).sounds(BlockSoundGroup.DECORATED_POT)), ModItemGroup.BETTER_ARCHEOLOGY_ITEMGROUP);

    public static final Block LOOT_VASE_CREEPER = registerBlockWithoutTab("loot_vase_creeper", new LootVaseBlock(FabricBlockSettings.copy(Blocks.FLOWER_POT).sounds(BlockSoundGroup.DECORATED_POT)));
    public static final Block VASE_CREEPER = registerBlock("vase_creeper", new VaseBlock(FabricBlockSettings.copy(Blocks.FLOWER_POT).sounds(BlockSoundGroup.DECORATED_POT)), ModItemGroup.BETTER_ARCHEOLOGY_ITEMGROUP);

    public static final Block EVOKER_TRAP = registerBlockWithoutTab("evoker_trap", new EvokerTrapBlock(FabricBlockSettings.copy(Blocks.STONE).strength(25f)));

    //REGISTERING--------------------------------------------------------------------------//
    //Registers Block and calls registerBlockItem to add it as an Item as well
    private static Block registerBlock(String name, Block block, RegistryKey<ItemGroup> group) {
        registerBlockItemToGroup(name, block, group, Rarity.COMMON);
        return Registry.register(Registries.BLOCK, new Identifier(BetterArcheology.MOD_ID, name), block);
    }

    private static Block registerBlockWithoutTab(String name, Block block) {
        registerBlockItem(name, block, Rarity.COMMON);
        return Registry.register(Registries.BLOCK, new Identifier(BetterArcheology.MOD_ID, name), block);
    }

    private static Block registerBlockWithRarity(String name, Block block, RegistryKey<ItemGroup> group, Rarity rarity) {
        registerBlockItemToGroup(name, block, group, rarity);
        return Registry.register(Registries.BLOCK, new Identifier(BetterArcheology.MOD_ID, name), block);
    }

    //Registers given Block as an BlockItem and adds it to an ItemGroup
    private static Item registerBlockItemToGroup(String name, Block block, RegistryKey<ItemGroup> group, Rarity rarity) {
        Item item = Registry.register(Registries.ITEM, new Identifier(BetterArcheology.MOD_ID, name), new BlockItem(block, new FabricItemSettings().rarity(rarity)));

        ItemGroupEvents.modifyEntriesEvent(group).register(entries -> entries.add(item));
        return item;
    }

    private static Item registerBlockItem(String name, Block block, Rarity rarity) {
        return Registry.register(Registries.ITEM, new Identifier(BetterArcheology.MOD_ID, name), new BlockItem(block, new FabricItemSettings().rarity(rarity)));
    }

    private static WoodType registerWoodType(String id) {
        return WoodTypeRegistry.register(new Identifier(BetterArcheology.MOD_ID, id), new BlockSetType(id));
    }

    private static BlockSetType registerBlockSetType(String id) {
        return BlockSetTypeRegistry.registerWood(new Identifier(BetterArcheology.MOD_ID, id));
    }

    //LOGGER-----------------------------------------------------------------------------//
    public static void registerModBlocks() {
        BetterArcheology.LOGGER.info("Registering Blocks from " + BetterArcheology.MOD_ID);
    }
}