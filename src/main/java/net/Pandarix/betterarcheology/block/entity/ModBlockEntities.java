package net.Pandarix.betterarcheology.block.entity;


import net.Pandarix.betterarcheology.BetterArcheology;
import net.Pandarix.betterarcheology.block.ModBlocks;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModBlockEntities {
    public static BlockEntityType<ArcheologyTableBlockEntity> ARCHEOLOGY_TABLE;
    public static BlockEntityType<VillagerFossilBlockEntity> VILLAGER_FOSSIL;
    public static BlockEntityType<FleeFromBlockEntity> FLEE_FROM;
    public static BlockEntityType<DiggableBlockEntity> DIGGABLE_BLOCK;

    public static BlockEntityType<SusBlockEntity> SUSBLOCK;

    public static void registerBlockEntities() {
        ARCHEOLOGY_TABLE = Registry.register(Registries.BLOCK_ENTITY_TYPE,
                new Identifier(BetterArcheology.MOD_ID, "archeology_table"),
                FabricBlockEntityTypeBuilder.create(ArcheologyTableBlockEntity::new,
                        ModBlocks.ARCHEOLOGY_TABLE).build(null));

        VILLAGER_FOSSIL = Registry.register(Registries.BLOCK_ENTITY_TYPE,
                new Identifier(BetterArcheology.MOD_ID, "villager_fossil"),
                FabricBlockEntityTypeBuilder.create(VillagerFossilBlockEntity::new,
                        ModBlocks.VILLAGER_FOSSIL).build(null));

        DIGGABLE_BLOCK = Registry.register(Registries.BLOCK_ENTITY_TYPE,
                new Identifier(BetterArcheology.MOD_ID, "diggable_block"),
                FabricBlockEntityTypeBuilder.create(DiggableBlockEntity::new,
                        ModBlocks.FOSSILIFEROUS_DIRT).build(null));

        SUSBLOCK = Registry.register(Registries.BLOCK_ENTITY_TYPE,
                new Identifier(BetterArcheology.MOD_ID, "sus_block"),
                FabricBlockEntityTypeBuilder.create(SusBlockEntity::new,
                        ModBlocks.SUSPICIOUS_DIRT, ModBlocks.SUSPICIOUS_RED_SAND).build(null));

        FLEE_FROM = Registry.register(Registries.BLOCK_ENTITY_TYPE,
                new Identifier(BetterArcheology.MOD_ID, "flee_from"),
                FabricBlockEntityTypeBuilder.create(FleeFromBlockEntity::new,
                        ModBlocks.OCELOT_FOSSIL).build(null));
    }
}
