package net.Pandarix.betterarcheology.block.entity.client;

import net.Pandarix.betterarcheology.block.entity.ArcheologyTableBlockEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.LightmapTextureManager;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.world.LightType;
import net.minecraft.world.World;

import java.util.List;
import java.util.Objects;

@Environment(EnvType.CLIENT)
public class ArcheologyTableBlockEntityRenderer implements BlockEntityRenderer<ArcheologyTableBlockEntity> {
    public ArcheologyTableBlockEntityRenderer(BlockEntityRendererFactory.Context context) {
    }

    @Override
    public void render(ArcheologyTableBlockEntity entity, float tickDelta, MatrixStack matrices,
                       VertexConsumerProvider vertexConsumers, int light, int overlay) {
        ItemRenderer itemRenderer = MinecraftClient.getInstance().getItemRenderer();

        //gets List of all Items in inventory and stores corresponding indexes
        List<ItemStack> inventoryContents = entity.getInventoryContents();
        ItemStack brush = inventoryContents.get(0);
        ItemStack unidentified = inventoryContents.get(1);
        ItemStack identified = inventoryContents.get(2);

        //BRUSH
        //transform the items rotation, scale and position
        matrices.push();
        matrices.translate(0.35f, 1.025f, 0.7f);
        matrices.scale(0.65f, 0.65f, 0.65f);
        matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(90));

        //display brush on top of the table
        itemRenderer.renderItem(brush, ModelTransformationMode.GUI, getLightLevel(Objects.requireNonNull(entity.getWorld()), entity.getPos().up()),
                OverlayTexture.DEFAULT_UV, matrices, vertexConsumers, entity.getWorld(), 1);

        matrices.pop();

        //ARTIFACTS
        //transform the items rotation, scale and position
        matrices.push();
        matrices.translate(0.55f, 1.025, 0.4f);
        matrices.scale(0.55f, 0.55f, 0.55f);
        matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(90));

        //if there is no identified artifact in the output slot, render the unidentified one
        if(identified.isEmpty()){
            itemRenderer.renderItem(unidentified, ModelTransformationMode.GUI, getLightLevel(Objects.requireNonNull(entity.getWorld()), entity.getPos().up()),
                    OverlayTexture.DEFAULT_UV, matrices, vertexConsumers, entity.getWorld(), 1);
        } else {
            itemRenderer.renderItem(identified, ModelTransformationMode.GUI, getLightLevel(Objects.requireNonNull(entity.getWorld()), entity.getPos().up()),
                    OverlayTexture.DEFAULT_UV, matrices, vertexConsumers, entity.getWorld(), 1);
        }

        matrices.pop();
    }

    private int getLightLevel(World world, BlockPos pos) {
        int bLight = world.getLightLevel(LightType.BLOCK, pos);
        int sLight = world.getLightLevel(LightType.SKY, pos);
        return LightmapTextureManager.pack(bLight, sLight);
    }
}
