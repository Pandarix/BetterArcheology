package net.Pandarix.betterarcheology.screen;

import net.minecraft.resource.featuretoggle.FeatureFlags;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;

public class ModScreenHandlers {
    public static ScreenHandlerType<IdentifyingScreenHandler> IDENTIFYING_SCREEN_HANDLER;

    public static void registerAllScreenHandlers() {
        IDENTIFYING_SCREEN_HANDLER = new ScreenHandlerType<>(IdentifyingScreenHandler::new, FeatureFlags.VANILLA_FEATURES);            //Feature Flag declares screen to be accessible by default
    }
}
