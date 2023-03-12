package net.Pandarix.betterarcheology.villager;

import com.google.common.collect.ImmutableSet;
import net.Pandarix.betterarcheology.BetterArcheology;
import net.Pandarix.betterarcheology.block.ModBlocks;
import net.Pandarix.betterarcheology.item.ModItems;
import net.fabricmc.fabric.api.object.builder.v1.trade.TradeOfferHelper;
import net.fabricmc.fabric.api.object.builder.v1.villager.VillagerProfessionBuilder;
import net.fabricmc.fabric.api.object.builder.v1.world.poi.PointOfInterestHelper;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.village.TradeOffer;
import net.minecraft.village.VillagerProfession;
import net.minecraft.world.poi.PointOfInterestType;

public class ModVillagers {


    //------------------Archeologist------------------//
    public static final PointOfInterestType ARCHEOLOGY_TABLE_POI = registerPOI("archeology_table_poi", ModBlocks.ARCHEOLOGY_TABLE);
    public static final VillagerProfession ARCHEOLOGIST = registerProfession("archeologist",
            RegistryKey.of(Registries.POINT_OF_INTEREST_TYPE.getKey(), new Identifier(BetterArcheology.MOD_ID, "archeology_table_poi")));

    public static void registerTrades() {
        TradeOfferHelper.registerVillagerOffers(ARCHEOLOGIST,1,
                factories -> {
                    factories.add(((entity, random) -> new TradeOffer(
                            new ItemStack(Items.EMERALD, 16),
                            new ItemStack(ModItems.UNIDENTIFIED_SHARDS, 1),
                            6, 2, 0.02f
                    )));
                });
    }
    public static VillagerProfession registerProfession(String name, RegistryKey<PointOfInterestType> type) {
        return Registry.register(Registries.VILLAGER_PROFESSION, new Identifier(BetterArcheology.MOD_ID, name),
                VillagerProfessionBuilder.create(). id(new Identifier(BetterArcheology.MOD_ID, name)).workstation(type)
                        .workSound(SoundEvents.ENTITY_VILLAGER_WORK_LIBRARIAN).build());                                            //TODO: Sound of Librarian, change later?
    }
    public static PointOfInterestType registerPOI(String name, Block block) {
        return PointOfInterestHelper.register(new Identifier(BetterArcheology.MOD_ID, name),
                1, 1, ImmutableSet.copyOf(block.getStateManager().getStates()));
    }


    //----------------------------LOGGER---------------------------//
    public static void registerVillagers() {
        BetterArcheology.LOGGER.debug("Registering Villagers for " + BetterArcheology.MOD_ID);
    }
}
