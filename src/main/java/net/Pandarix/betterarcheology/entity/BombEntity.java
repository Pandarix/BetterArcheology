package net.Pandarix.betterarcheology.entity;

import net.Pandarix.betterarcheology.item.ModItems;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;

public class BombEntity extends ThrownItemEntity {
    public BombEntity(EntityType<? extends ThrownItemEntity> entityType, World world) {
        super(entityType, world);
    }

    public BombEntity(World world, LivingEntity owner) {
        super(ModEntityTypes.BOMB_ENTITY, owner, world);
    }

    public BombEntity(World world, double x, double y, double z) {
        super(ModEntityTypes.BOMB_ENTITY, x, y, z, world);
    }
    @Override
    protected Item getDefaultItem() {
        return ModItems.BOMB_ITEM;
    }

    @Environment(EnvType.CLIENT)
    public void handleStatus(byte status) {
        //explosion effect addons
        if (status == 3) {
            Random random = this.getWorld().getRandom();

            for (int i = 0; i < 25; ++i) {
                this.getWorld().addParticle(ParticleTypes.LARGE_SMOKE, this.getX(), this.getY(), this.getZ(), random.nextDouble() / 5f * random.nextBetween(-1, 1), random.nextDouble() / 2f, random.nextDouble() / 5f * random.nextBetween(-1, 1));
            }
        }
    }

    @Override
    public void tick() {
        Random random = this.getWorld().getRandom();
        if (random.nextBoolean()) {
            //trail particles for when the bomb is thrown
            this.getWorld().addParticle(random.nextBoolean() ? ParticleTypes.FLAME : ParticleTypes.SMOKE, this.getX(), this.getY(), this.getZ(), 0, 0, 0);
        }
        super.tick();
    }

    protected void onCollision(HitResult hitResult) {
        super.onCollision(hitResult);
        if (!this.getWorld().isClient) {
            this.getWorld().sendEntityStatus(this, (byte) 3);
            this.discard();
        }
        //explode on collision
        this.getWorld().createExplosion(this, this.getX(), this.getY(), this.getZ(), 2.5f, World.ExplosionSourceType.TNT);
    }
}
