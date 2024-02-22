package net.Pandarix.betterarcheology.block.entity;

import net.Pandarix.betterarcheology.block.custom.RadianceTotemBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.Monster;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;

import java.util.List;

public class RadianceTotemBlockEntity extends BlockEntity
{
    public RadianceTotemBlockEntity(BlockPos pos, BlockState state)
    {
        super(ModBlockEntities.RADIANCE_TOTEM, pos, state);
    }

    public static void tick(World world, BlockPos pos, BlockState state, RadianceTotemBlockEntity blockEntity)
    {
        if (world.getRandom().nextBetween(0, 5) != 0)
        {
            return;
        }
        int selector = 0;
        if (state.getBlock() instanceof RadianceTotemBlock)
        {
            selector = state.get(RadianceTotemBlock.SELECTOR);
        }
        //get players in bounding box of 10 blocks
        List<LivingEntity> livingEntities = world.getNonSpectatingEntities(LivingEntity.class, (new Box(pos).expand(20)));
        if (world.getRandom().nextBetween(0, 400) == 0)
        {
            for (LivingEntity livingEntity : livingEntities)
            {
                if (livingEntity instanceof HostileEntity monster)
                {
                    monster.damage(monster.getDamageSources().magic(), 1);
                    world.playSound(null, monster.getBlockPos().getX(), monster.getBlockPos().getY(), monster.getBlockPos().getZ(), SoundEvents.BLOCK_AMETHYST_BLOCK_HIT, SoundCategory.HOSTILE, 0.5f, 0.5f);
                }
            }
            return;
        }
        //give every player in range slow-falling for 10 seconds, particles are not being displayed for ux
        for (LivingEntity livingEntity : livingEntities)
        {
            switch (selector)
            {
                case 0 -> livingEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.GLOWING, 200, 0, false, false));
                case 1 ->
                {
                    if (livingEntity instanceof Monster)
                    {
                        livingEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.GLOWING, 200, 0, false, false));
                    }
                }
                case 2 ->
                {
                    if (livingEntity instanceof AnimalEntity)
                    {
                        livingEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.GLOWING, 200, 0, false, false));
                    }
                }
                case 3 ->
                {
                    if (livingEntity instanceof PlayerEntity)
                    {
                        livingEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.GLOWING, 200, 0, false, false));
                    }
                }
            }
        }
    }
}
