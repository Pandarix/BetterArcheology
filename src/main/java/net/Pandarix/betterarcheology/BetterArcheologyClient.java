package net.Pandarix.betterarcheology;

import net.Pandarix.betterarcheology.block.ModBlocks;
import net.Pandarix.betterarcheology.block.entity.ModBlockEntities;
import net.Pandarix.betterarcheology.block.entity.client.*;
import net.Pandarix.betterarcheology.entity.BombEntity;
import net.Pandarix.betterarcheology.entity.ModEntityTypes;
import net.Pandarix.betterarcheology.networking.ModMessages;
import net.Pandarix.betterarcheology.screen.FossilInventoryScreen;
import net.Pandarix.betterarcheology.screen.IdentifyingScreen;
import net.Pandarix.betterarcheology.screen.ModScreenHandlers;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.BlockEntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.BrushableBlockEntity;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.block.entity.BrushableBlockEntityRenderer;
import net.minecraft.client.render.entity.*;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.item.Item;

public class BetterArcheologyClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ModMessages.registerS2CPackets();

        HandledScreens.register(ModScreenHandlers.IDENTIFYING_SCREEN_HANDLER, IdentifyingScreen::new);
        HandledScreens.register(ModScreenHandlers.FOSSIL_SCREEN_HANDLER, FossilInventoryScreen::new);

        BlockEntityRendererRegistry.register(ModBlockEntities.ARCHEOLOGY_TABLE, ArcheologyTableBlockEntityRenderer::new);
        BlockEntityRendererRegistry.register(ModBlockEntities.VILLAGER_FOSSIL, VillagerFossilBlockEntityRenderer::new);
        BlockEntityRendererRegistry.register(ModBlockEntities.SUSBLOCK, SusBlockEntityRenderer::new);

        EntityRendererRegistry.register(ModEntityTypes.BOMB_ENTITY, FlyingItemEntityRenderer::new);

        //RENDERING
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.ROTTEN_DOOR, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.ROTTEN_TRAPDOOR, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.VILLAGER_FOSSIL, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.VILLAGER_FOSSIL_BODY, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.OCELOT_FOSSIL, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.OCELOT_FOSSIL_BODY, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.OCELOT_FOSSIL_HEAD, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.SHEEP_FOSSIL, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.SHEEP_FOSSIL_BODY, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.SHEEP_FOSSIL_HEAD, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.CHICKEN_FOSSIL, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.CHICKEN_FOSSIL_HEAD, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.CHICKEN_FOSSIL_BODY, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.CREEPER_FOSSIL, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.CREEPER_FOSSIL_BODY, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.CREEPER_FOSSIL_HEAD, RenderLayer.getCutout());
    }
}
