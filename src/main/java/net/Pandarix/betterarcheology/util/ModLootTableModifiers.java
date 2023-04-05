package net.Pandarix.betterarcheology.util;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.Pandarix.betterarcheology.BetterArcheology;
import net.Pandarix.betterarcheology.item.ModItems;
import net.fabricmc.fabric.api.loot.v2.FabricLootPoolBuilder;
import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.condition.RandomChanceLootCondition;
import net.minecraft.loot.condition.SurvivesExplosionLootCondition;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;
import net.minecraft.util.Identifier;

public class ModLootTableModifiers {
    private static final Identifier DESERT_PYRAMID_ARCHAEOLOGY_ID = new Identifier("minecraft", "archaeology/desert_pyramid");

    public static void modifyLootTables() {
        /*
        TODO: Enable when the loottable is moved to the right location (1.20 release)

        LootTableEvents.MODIFY.register((resourceManager, lootManager, id, tableBuilder, source) -> {
            // If the loot table is for the cobblestone block and it is not overridden by a user:
            if (DESERT_PYRAMID_ARCHAEOLOGY_ID.equals(id) && source.isBuiltin()) {
                BetterArcheology.LOGGER.info("Injecting custom loot to Pyramid");
                // Create a new loot pool that will hold the diamonds.
                LootPool.Builder pool = LootPool.builder()
                        // Add diamonds...
                        .with(ItemEntry.builder(Items.DIAMOND));
                // Add the loot pool to the loot table
                tableBuilder.pool(pool);
            }
        });
        */
    }
}
