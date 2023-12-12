package net.Pandarix.betterarcheology.block.entity;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;

import java.util.List;

public class GuardianFossilBlockEntity extends BlockEntity {
    public GuardianFossilBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.GUARDIAN_FOSSIl, pos, state);
    }

    public static void tick(World world, BlockPos pos, BlockState state, GuardianFossilBlockEntity blockEntity) {
        //get players in bounding box of 10 blocks
        List<PlayerEntity> playersInRange = world.getNonSpectatingEntities(PlayerEntity.class, (new Box(pos).expand(10)));
        //give every player in range waterbreathing for 10 seconds, particles are not being displayed for ux
        for (PlayerEntity player : playersInRange) {
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.WATER_BREATHING, 200, 0, false, false));
        }
    }
}
