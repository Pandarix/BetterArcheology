package net.Pandarix.betterarcheology.item;

import net.Pandarix.betterarcheology.BetterArcheology;
import net.Pandarix.betterarcheology.entity.BombEntity;
import net.Pandarix.betterarcheology.util.ServerPlayerHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class BombItem extends Item {
    //gets id of advancement for having thrown a bomb which has the condition "impossible" because it needs to be triggered here
    Identifier ADVANCEMENT_ID = new Identifier(BetterArcheology.MOD_ID, "used_bomb_item");
    public BombItem(Settings settings) {
        super(settings);
    }

    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);

        //plays sound for throwing the bomb
        world.playSound((PlayerEntity)null, user.getX(), user.getY(), user.getZ(), SoundEvents.ENTITY_SNOWBALL_THROW, SoundCategory.NEUTRAL, 0.5F, 0.4F / (world.getRandom().nextFloat() * 0.4F + 0.8F));

        BombEntity bombEntity = new BombEntity(world, user);

        user.getItemCooldownManager().set(this, 10);

        //on server, set velocity of thrown item and sets the item to the bomb
        //grants player the advancement
        if (!world.isClient) {
            bombEntity.setItem(itemStack);
            bombEntity.setVelocity(user, user.getPitch(), user.getYaw(), 0.0F, 0.75F, 1.0F);
            world.spawnEntity(bombEntity);
            ServerPlayerHelper.getServerPlayer(user).getAdvancementTracker().grantCriterion(world.getServer().getAdvancementLoader().get(ADVANCEMENT_ID), "criteria");
        }

        //play fuse sound at bombEntity
        world.playSoundFromEntity(null, bombEntity, SoundEvents.ENTITY_CREEPER_PRIMED,  SoundCategory.NEUTRAL, 1f, (float) world.getRandom().nextDouble()* 0.5f + 0.5f);

        //decrease stack size
        itemStack.decrement(1);

        return TypedActionResult.consume(itemStack);
    }
}
