package net.Pandarix.betterarcheology.mixin;

import net.Pandarix.betterarcheology.item.ModItems;
import net.minecraft.entity.LivingEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public class TorrentTotemItemTickMixin
{
    @Inject(method = "tickRiptide", at = @At("HEAD"))
    private void injectMethod(Box a, Box b, CallbackInfo ci)
    {

        LivingEntity betterarcheology$livingEntity = (LivingEntity) (Object) this;

        if (betterarcheology$livingEntity.getActiveItem().isOf(ModItems.TORRENT_TOTEM))
        {
            World betterarcheology$world = betterarcheology$livingEntity.getWorld();
            if (betterarcheology$world.isClient())
            {
                Random betterarcheology$random = betterarcheology$livingEntity.getRandom();
                Vec3d betterarcheology$pos = betterarcheology$livingEntity.getPos();
                for (int i = 0; i <= 75; ++i)
                {
                    betterarcheology$world.addParticle(ParticleTypes.SPLASH,
                            betterarcheology$pos.x + (betterarcheology$random.nextDouble() - 0.5),
                            betterarcheology$pos.y + (betterarcheology$random.nextDouble() - 0.5),
                            betterarcheology$pos.z + (betterarcheology$random.nextDouble() - 0.5),
                            (betterarcheology$random.nextDouble() - 0.5) / 2,
                            0,
                            (betterarcheology$random.nextDouble() - 0.5) / 2);
                }
            }
        }
    }
}
