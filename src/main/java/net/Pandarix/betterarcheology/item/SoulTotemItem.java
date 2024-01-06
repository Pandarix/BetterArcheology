package net.Pandarix.betterarcheology.item;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Predicate;

public class SoulTotemItem extends Item {
    public SoulTotemItem(Settings settings) {
        super(settings);
    }

    @Override
    public int getMaxUseTime(ItemStack stack) {
        return 6;
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.BLOCK;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        user.setCurrentHand(hand);
        return TypedActionResult.consume(itemStack);
    }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        if (!(user instanceof PlayerEntity player)) {
            return stack;
        }

        HitResult hitresult = ProjectileUtil.getCollision(player, Predicate.not(Entity::isSpectator), Math.sqrt(ServerPlayNetworkHandler.MAX_BREAK_SQUARED_DISTANCE) - 1.0);

        //if an entity is being targeted
        if (hitresult instanceof EntityHitResult entityHitResult) {
            if (entityHitResult.getType() == HitResult.Type.ENTITY) {

                Entity entity = entityHitResult.getEntity();
                //if the entity could be attacked
                if (entity.isAttackable()) {
                    //spawn particles from player to target on client
                    //particles move towards the player
                    if (world.isClient()) {
                        Vec3d playerPos = player.getPos();
                        Vec3d targetPos = entity.getPos();
                        Vec3d toPlayerPos = playerPos.subtract(targetPos);
                        for (float f = 0; f <= 1; f += 0.05) {
                            world.addParticle(ParticleTypes.SCULK_SOUL,
                                    lerp(playerPos.x, targetPos.x, f),
                                    lerp(playerPos.y, targetPos.y, f) + 1,
                                    lerp(playerPos.z, targetPos.z, f),
                                    toPlayerPos.x * f / 15, toPlayerPos.y * f / 15, toPlayerPos.z * f / 15);
                        }
                    } else {
                        //play sound effects
                        world.playSoundFromEntity(null, player, SoundEvents.ENTITY_MULE_EAT, SoundCategory.PLAYERS, 0.5f, 1f);
                        world.playSoundFromEntity(null, player, SoundEvents.ENTITY_WITHER_SHOOT, SoundCategory.PLAYERS, 0.1f, 0.25f);
                        //damage target and heal user
                        entity.damage(entity.getDamageSources().playerAttack(player), 4);
                        player.heal(4);
                        //set cooldown and damage stack
                        player.getItemCooldownManager().set(this, 180);
                        stack.damage(1, user, (p) -> {
                            p.sendToolBreakStatus(player.getActiveHand());
                        });
                    }
                }

            }
        }
        return super.finishUsing(stack, world, user);
    }

    private double lerp(double a, double b, float f) {
        return a + f * (b - a);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        super.appendTooltip(stack, world, tooltip, context);
        tooltip.add(Text.translatable(this.getTranslationKey() + "_description").formatted(Formatting.DARK_AQUA));
    }
}
