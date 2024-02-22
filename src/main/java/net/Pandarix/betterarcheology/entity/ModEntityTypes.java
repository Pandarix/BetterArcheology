package net.Pandarix.betterarcheology.entity;

import net.Pandarix.betterarcheology.BetterArcheology;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModEntityTypes
{
    public static final EntityType<BombEntity> BOMB_ENTITY = Registry.register(
            Registries.ENTITY_TYPE,
            new Identifier(BetterArcheology.MOD_ID, "bombentity"),
            FabricEntityTypeBuilder.<BombEntity>create(SpawnGroup.MISC, BombEntity::new).dimensions(EntityDimensions.fixed(0.75f, 0.75f)).build()
    );

    public static void registerModEntityTypes()
    {
        BetterArcheology.LOGGER.info("ModEntityTypes of " + BetterArcheology.MOD_ID + " registered.");
    }
}
