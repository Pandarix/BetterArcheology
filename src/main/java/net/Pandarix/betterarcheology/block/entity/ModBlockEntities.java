package net.Pandarix.betterarcheology.block.entity;

import net.Pandarix.betterarcheology.BetterArcheology;
import net.Pandarix.betterarcheology.block.ModBlocks;
import net.Pandarix.betterarcheology.block.entity.suspicious_blocks.SuspiciousDirtBlockEntity;
import net.Pandarix.betterarcheology.block.entity.suspicious_blocks.SuspiciousGravelBlockEntity;
import net.Pandarix.betterarcheology.block.entity.suspicious_blocks.SuspiciousRedSandBlockEntity;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModBlockEntities {
    public static BlockEntityType<ArcheologyTableBlockEntity> ARCHEOLOGY_TABLE;
    public static BlockEntityType<SuspiciousGravelBlockEntity> SUSPICIOUS_GRAVEL;
    public static BlockEntityType<SuspiciousDirtBlockEntity> SUSPICIOUS_DIRT;
    public static BlockEntityType<SuspiciousRedSandBlockEntity> SUSPICIOUS_RED_SAND;

    public static void registerBlockEntities() {
        ARCHEOLOGY_TABLE = Registry.register(Registries.BLOCK_ENTITY_TYPE,
                new Identifier(BetterArcheology.MOD_ID, "archeology_table"),
                FabricBlockEntityTypeBuilder.create(ArcheologyTableBlockEntity::new,
                        ModBlocks.ARCHEOLOGY_TABLE).build(null));

        SUSPICIOUS_DIRT = Registry.register(Registries.BLOCK_ENTITY_TYPE,
                new Identifier(BetterArcheology.MOD_ID, "suspicious_dirt"),
                FabricBlockEntityTypeBuilder.create(SuspiciousDirtBlockEntity::new,
                        ModBlocks.SUSPICIOUS_DIRT).build(null));

        SUSPICIOUS_RED_SAND = Registry.register(Registries.BLOCK_ENTITY_TYPE,
                new Identifier(BetterArcheology.MOD_ID, "suspicious_red_sand"),
                FabricBlockEntityTypeBuilder.create(SuspiciousRedSandBlockEntity::new,
                        ModBlocks.SUSPICIOUS_RED_SAND).build(null));
    }
}
