package net.Pandarix.betterarcheology.util;

import net.Pandarix.betterarcheology.BetterArcheology;
import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.minecraft.item.Items;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.util.Identifier;

public class ModLootTableModifiers {
    private static final Identifier DESERT_PYRAMID_ARCHAEOLOGY_ID = new Identifier("minecraft", "archaeology/desert_pyramid");

    public static void modifyLootTables() {
        LootTableEvents.MODIFY.register((resourceManager, lootManager, id, tableBuilder, source) -> {
            // If the loot table is for the cobblestone block and it is not overridden by a user:
            if (DESERT_PYRAMID_ARCHAEOLOGY_ID.equals(id) && source.isBuiltin()) {
                BetterArcheology.LOGGER.info("Injecting custom loot to Pyramid");
                // Create a new loot pool that will hold the diamonds.
                LootPool.Builder pool = LootPool.builder()
                        // Add diamonds...
                        .with(ItemEntry.builder(Items.ALLIUM));
                // Add the loot pool to the loot table
                tableBuilder.pool(pool);
            }
        });
    }
}
