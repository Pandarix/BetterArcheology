package net.Pandarix.betterarcheology.item;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class ShadowPearlItem extends Item {
    public ShadowPearlItem(Settings settings) {
        super(settings);
    }

    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);

        world.playSound((PlayerEntity)null, user.getX(), user.getY(), user.getZ(), SoundEvents.ENTITY_ENDERMAN_TELEPORT, SoundCategory.NEUTRAL, 0.5F, 0.1F / (world.getRandom().nextFloat() * 0.4F + 0.8F));
        world.playSoundFromEntity(null, user, SoundEvents.ENTITY_WITHER_SHOOT,  SoundCategory.NEUTRAL, 0.25f, (float) world.getRandom().nextDouble()* 0.5f + 0.5f);

        user.getItemCooldownManager().set(this, 80);

        if (!world.isClient) {
            Vec3d rotationVector = user.getRotationVector();
            Vec3d velocity = user.getVelocity();
            user.setVelocity(velocity.add(rotationVector.x * 0.1 + (rotationVector.x * 1.5 - velocity.x), rotationVector.y * 0.1 + (rotationVector.y * 1.5 - velocity.y), rotationVector.z * 0.1 + (rotationVector.z * 1.5 - velocity.z)));
            user.getActiveItem().damage(1, user.getRandom(), (ServerPlayerEntity) user);
        }

        return TypedActionResult.success(itemStack, world.isClient());
    }
}
