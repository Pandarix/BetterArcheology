package net.Pandarix.betterarcheology;

import net.Pandarix.betterarcheology.block.ModBlocks;
import net.Pandarix.betterarcheology.block.entity.ModBlockEntities;
import net.Pandarix.betterarcheology.block.entity.client.ArcheologyTableBlockEntityRenderer;
import net.Pandarix.betterarcheology.block.entity.client.DiggableBlockEntityRenderer;
import net.Pandarix.betterarcheology.block.entity.client.VillagerFossilBlockEntityRenderer;
import net.Pandarix.betterarcheology.networking.ModMessages;
import net.Pandarix.betterarcheology.screen.FossilInventoryScreen;
import net.Pandarix.betterarcheology.screen.IdentifyingScreen;
import net.Pandarix.betterarcheology.screen.ModScreenHandlers;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.BlockEntityRendererRegistry;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.client.render.RenderLayer;

public class BetterArcheologyClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ModMessages.registerS2CPackets();

        HandledScreens.register(ModScreenHandlers.IDENTIFYING_SCREEN_HANDLER, IdentifyingScreen::new);
        HandledScreens.register(ModScreenHandlers.FOSSIL_SCREEN_HANDLER, FossilInventoryScreen::new);

        BlockEntityRendererRegistry.register(ModBlockEntities.ARCHEOLOGY_TABLE, ArcheologyTableBlockEntityRenderer::new);
        BlockEntityRendererRegistry.register(ModBlockEntities.VILLAGER_FOSSIL, VillagerFossilBlockEntityRenderer::new);
        BlockEntityRendererRegistry.register(ModBlockEntities.DIGGABLE_BLOCK, DiggableBlockEntityRenderer::new);

        //RENDERING
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.ROTTEN_DOOR, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.ROTTEN_TRAPDOOR, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.VILLAGER_FOSSIL, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.VILLAGER_FOSSIL_BODY, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.OCELOT_FOSSIL, RenderLayer.getCutout());
    }

}
