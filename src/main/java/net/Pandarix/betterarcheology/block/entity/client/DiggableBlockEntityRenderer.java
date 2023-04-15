package net.Pandarix.betterarcheology.block.entity.client;

import net.Pandarix.betterarcheology.block.custom.DiggableBlockEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.RotationAxis;

@Environment(EnvType.CLIENT)
public class DiggableBlockEntityRenderer implements BlockEntityRenderer<DiggableBlockEntity> {
    private final ItemRenderer itemRenderer;

    public DiggableBlockEntityRenderer(BlockEntityRendererFactory.Context context) {
        itemRenderer = context.getItemRenderer();
    }

    public void render(DiggableBlockEntity diggableBlockEntity, float f, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, int j) {
        if (diggableBlockEntity.getWorld() != null) {
            int k = (Integer)diggableBlockEntity.getCachedState().get(IntProperty.of("dug", 0, 3));
            if (k > 0) {
                Direction direction = diggableBlockEntity.getHitDirection();
                if (direction != null) {
                    ItemStack itemStack = diggableBlockEntity.getItem();
                    if (!itemStack.isEmpty()) {
                        matrixStack.push();
                        matrixStack.translate(0.0F, 0.5F, 0.0F);
                        float[] fs = this.getTranslation(direction, k);
                        matrixStack.translate(fs[0], fs[1], fs[2]);
                        matrixStack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(75.0F));
                        boolean bl = direction == Direction.EAST || direction == Direction.WEST;
                        matrixStack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees((float)((bl ? 90 : 0) + 11)));
                        matrixStack.scale(0.5F, 0.5F, 0.5F);
                        int l = WorldRenderer.getLightmapCoordinates(diggableBlockEntity.getWorld(), diggableBlockEntity.getCachedState(), diggableBlockEntity.getPos().offset(direction));
                        this.itemRenderer.renderItem(itemStack, ModelTransformationMode.FIXED, l, OverlayTexture.DEFAULT_UV, matrixStack, vertexConsumerProvider, diggableBlockEntity.getWorld(), 0);
                        matrixStack.pop();
                    }
                }
            }
        }
    }

    private float[] getTranslation(Direction direction, int dugLevel) {
        float[] fs = new float[]{0.5F, 0.0F, 0.5F};
        float f = (float)dugLevel / 10.0F * 0.75F;
        switch (direction) {
            case EAST -> fs[0] = 0.73F + f;
            case WEST -> fs[0] = 0.25F - f;
            case UP -> fs[1] = 0.25F + f;
            case DOWN -> fs[1] = -0.23F - f;
            case NORTH -> fs[2] = 0.25F - f;
            case SOUTH -> fs[2] = 0.73F + f;
        }

        return fs;
    }
}
