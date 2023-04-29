package net.Pandarix.betterarcheology.block.entity.client;

import net.Pandarix.betterarcheology.block.custom.FossilBaseWithEntityBlock;
import net.Pandarix.betterarcheology.block.entity.VillagerFossilBlockEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.LightmapTextureManager;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.world.LightType;
import net.minecraft.world.World;

import java.util.Objects;

@Environment(EnvType.CLIENT)
public class VillagerFossilBlockEntityRenderer implements BlockEntityRenderer<VillagerFossilBlockEntity> {
    public VillagerFossilBlockEntityRenderer(BlockEntityRendererFactory.Context context) {
    }

    @Override
    public void render(VillagerFossilBlockEntity entity, float tickDelta, MatrixStack matrices,
                       VertexConsumerProvider vertexConsumers, int light, int overlay) {
        ItemRenderer itemRenderer = MinecraftClient.getInstance().getItemRenderer();

        matrices.push();

        BlockState state = entity.getWorld().getBlockState(entity.getPos());
        Direction facing = state.getBlock() instanceof FossilBaseWithEntityBlock ? state.get(FossilBaseWithEntityBlock.FACING) : Direction.NORTH;

        switch (facing) {
            case EAST -> {
                matrices.translate(0.75f, 0.95f, 0.5f);
                matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(-90));
            }
            case WEST -> {
                matrices.translate(0.25f, 0.95f, 0.5f);
                matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(90));
            }
            case NORTH -> matrices.translate(0.5f, 0.95f, 0.25f);
            case SOUTH -> {
                matrices.translate(0.5f, 0.95f, 0.75f);
                matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(180));;
            }
            default -> matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(-90));
        }

        matrices.scale(0.5f, 0.5f, 0.5f);

        itemRenderer.renderItem(entity.getInventoryContents(), ModelTransformationMode.FIXED, getLightLevel(Objects.requireNonNull(entity.getWorld()), entity.getPos()), OverlayTexture.DEFAULT_UV, matrices, vertexConsumers, entity.getWorld(), 1);

        matrices.pop();
    }

    private int getLightLevel(World world, BlockPos pos) {
        int bLight = world.getLightLevel(LightType.BLOCK, pos);
        int sLight = world.getLightLevel(LightType.SKY, pos);
        return LightmapTextureManager.pack(bLight, sLight);
    }
}
