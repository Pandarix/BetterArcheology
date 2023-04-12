package net.Pandarix.betterarcheology.screen;

import net.Pandarix.betterarcheology.BetterArcheology;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.resource.featuretoggle.FeatureFlags;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;

public class ModScreenHandlers {
    public static ScreenHandlerType<IdentifyingScreenHandler> IDENTIFYING_SCREEN_HANDLER = new ScreenHandlerType<>(IdentifyingScreenHandler::new, FeatureFlags.VANILLA_FEATURES);
    public static ScreenHandlerType<FossilInventoryScreenHandler> FOSSIL_SCREEN_HANDLER = new ScreenHandlerType<>(FossilInventoryScreenHandler::new, FeatureFlags.VANILLA_FEATURES);

    public static void registerAllScreenHandlers(){
        Registry.register(Registries.SCREEN_HANDLER, new Identifier(BetterArcheology.MOD_ID, "archeology_table"), IDENTIFYING_SCREEN_HANDLER);
        Registry.register(Registries.SCREEN_HANDLER, new Identifier(BetterArcheology.MOD_ID, "fossil"), FOSSIL_SCREEN_HANDLER);
    }
}
