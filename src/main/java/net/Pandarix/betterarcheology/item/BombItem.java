package net.Pandarix.betterarcheology.item;

import net.Pandarix.betterarcheology.entity.BombEntity;
import net.Pandarix.betterarcheology.entity.ModEntityTypes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class BombItem extends Item {
    public BombItem(Settings settings) {
        super(settings);
    }

    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);

        world.playSound((PlayerEntity)null, user.getX(), user.getY(), user.getZ(), SoundEvents.ENTITY_SNOWBALL_THROW, SoundCategory.NEUTRAL, 0.5F, 0.4F / (world.getRandom().nextFloat() * 0.4F + 0.8F));

        BombEntity bombEntity = new BombEntity(world, user);
        world.playSoundFromEntity(null, bombEntity, SoundEvents.ENTITY_CREEPER_PRIMED,  SoundCategory.NEUTRAL, 1f, (float) world.getRandom().nextDouble()* 0.5f + 0.5f);

        user.getItemCooldownManager().set(this, 10);

        if (!world.isClient) {
            bombEntity.setItem(itemStack);
            bombEntity.setVelocity(user, user.getPitch(), user.getYaw(), 0.0F, 0.75F, 1.0F);
            world.spawnEntity(bombEntity);
        }

        itemStack.decrement(1);

        return TypedActionResult.success(itemStack, world.isClient());
    }
}
