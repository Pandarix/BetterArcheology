package net.Pandarix.betterarcheology;

import net.Pandarix.betterarcheology.block.ModBlocks;
import net.Pandarix.betterarcheology.screen.IdentifyingScreen;
import net.Pandarix.betterarcheology.screen.ModScreenHandlers;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.client.render.RenderLayer;

public class BetterArcheologyClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        HandledScreens.register(ModScreenHandlers.IDENTIFYING_SCREEN_HANDLER, IdentifyingScreen::new);

        //RENDERING
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.ROTTEN_DOOR, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.ROTTEN_TRAPDOOR, RenderLayer.getCutout());
    }

}
