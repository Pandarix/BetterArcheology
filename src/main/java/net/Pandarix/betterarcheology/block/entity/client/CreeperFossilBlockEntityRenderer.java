package net.Pandarix.betterarcheology.block.entity.client;

import net.Pandarix.betterarcheology.BetterArcheology;
import net.Pandarix.betterarcheology.block.custom.CreeperFossilBlock;
import net.Pandarix.betterarcheology.block.entity.CreeperFossilBlockEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.entity.model.CreeperEntityModel;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.render.entity.model.EntityModelLoader;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

@Environment(EnvType.CLIENT)
public class CreeperFossilBlockEntityRenderer implements BlockEntityRenderer<CreeperFossilBlockEntity> {
    private static final Identifier TEXTURE = new Identifier(BetterArcheology.MOD_ID, "/textures/block/fossils/creeper_overlay.png");
    private static final Identifier SKIN = new Identifier("textures/entity/creeper/creeper_armor.png");
    public CreeperFossilBlockEntityRenderer(BlockEntityRendererFactory.Context context) {
    }

    @Override
    public void render(CreeperFossilBlockEntity entity, float tickDelta, MatrixStack matrices,
                       VertexConsumerProvider vertexConsumers, int light, int overlay) {
        World world = entity.getWorld();
        BlockPos pos = entity.getPos();
        BlockState state = world.getBlockState(pos);
        BakedModel model = MinecraftClient.getInstance().getBlockRenderManager().getModel(state.with(CreeperFossilBlock.OVERLAY, true));

        float f = (float) Math.sin(tickDelta * 0.01f);
        BetterArcheology.LOGGER.info("f: " + tickDelta);

        VertexConsumer vertexConsumer = vertexConsumers.getBuffer(RenderLayer.getEnergySwirl(SKIN, f, f));
        MinecraftClient.getInstance().getBlockRenderManager().getModelRenderer().render(world, model, state, pos, matrices, vertexConsumer, false, world.random, light, overlay);
    }

    protected float getEnergySwirlX(float partialAge) {
        return partialAge * 0.01F;
    }
}
