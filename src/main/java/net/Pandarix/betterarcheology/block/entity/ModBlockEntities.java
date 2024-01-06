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
    public static BlockEntityType<ChickenFossilBlockEntity> CHICKEN_FOSSIL;
    public static BlockEntityType<GuardianFossilBlockEntity> GUARDIAN_FOSSIl;
    public static BlockEntityType<FleeFromBlockEntity> FLEE_FROM;
    public static BlockEntityType<SkeletonFleeFromBlockEntity> SKELETON_FLEE_FROM;
    public static BlockEntityType<SusBlockEntity> SUSBLOCK;

    public static BlockEntityType<RadianceTotemBlockEntity> RADIANCE_TOTEM;

    public static void registerBlockEntities() {
        ARCHEOLOGY_TABLE = Registry.register(Registries.BLOCK_ENTITY_TYPE,
                new Identifier(BetterArcheology.MOD_ID, "archeology_table"),
                FabricBlockEntityTypeBuilder.create(ArcheologyTableBlockEntity::new,
                        ModBlocks.ARCHEOLOGY_TABLE).build(null));

        VILLAGER_FOSSIL = Registry.register(Registries.BLOCK_ENTITY_TYPE,
                new Identifier(BetterArcheology.MOD_ID, "villager_fossil"),
                FabricBlockEntityTypeBuilder.create(VillagerFossilBlockEntity::new,
                        ModBlocks.VILLAGER_FOSSIL).build(null));

        CHICKEN_FOSSIL = Registry.register(Registries.BLOCK_ENTITY_TYPE,
                new Identifier(BetterArcheology.MOD_ID, "chicken_fossil"),
                FabricBlockEntityTypeBuilder.create(ChickenFossilBlockEntity::new,
                        ModBlocks.CHICKEN_FOSSIL).build(null));

        GUARDIAN_FOSSIl = Registry.register(Registries.BLOCK_ENTITY_TYPE,
                new Identifier(BetterArcheology.MOD_ID, "guardian_fossil"),
                FabricBlockEntityTypeBuilder.create(GuardianFossilBlockEntity::new,
                        ModBlocks.GUARDIAN_FOSSIL).build(null));

        SUSBLOCK = Registry.register(Registries.BLOCK_ENTITY_TYPE,
                new Identifier(BetterArcheology.MOD_ID, "sus_block"),
                FabricBlockEntityTypeBuilder.create(SusBlockEntity::new,
                        ModBlocks.SUSPICIOUS_DIRT, ModBlocks.SUSPICIOUS_RED_SAND, ModBlocks.FOSSILIFEROUS_DIRT).build(null));

        FLEE_FROM = Registry.register(Registries.BLOCK_ENTITY_TYPE,
                new Identifier(BetterArcheology.MOD_ID, "flee_from"),
                FabricBlockEntityTypeBuilder.create(FleeFromBlockEntity::new,
                        ModBlocks.OCELOT_FOSSIL).build(null));

        SKELETON_FLEE_FROM = Registry.register(Registries.BLOCK_ENTITY_TYPE,
                new Identifier(BetterArcheology.MOD_ID, "skeleton_flee_from"),
                FabricBlockEntityTypeBuilder.create(SkeletonFleeFromBlockEntity::new,
                        ModBlocks.WOLF_FOSSIL).build(null));

        RADIANCE_TOTEM = Registry.register(Registries.BLOCK_ENTITY_TYPE,
                new Identifier(BetterArcheology.MOD_ID, "radiance_totem"),
                FabricBlockEntityTypeBuilder.create(RadianceTotemBlockEntity::new,
                        ModBlocks.RADIANCE_TOTEM).build(null));
    }
}
