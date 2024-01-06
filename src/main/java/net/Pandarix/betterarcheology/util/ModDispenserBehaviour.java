package net.Pandarix.betterarcheology.util;

import net.Pandarix.betterarcheology.entity.BombEntity;
import net.Pandarix.betterarcheology.item.ModItems;
import net.minecraft.block.DispenserBlock;
import net.minecraft.block.dispenser.ProjectileDispenserBehavior;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Position;
import net.minecraft.world.World;

public class ModDispenserBehaviour {
    public static void registerDispenserBehaviour() {
        if(ModConfigs.BOMB_DISPENSER_SHOOTING) {
            DispenserBlock.registerBehavior(ModItems.BOMB_ITEM, new ProjectileDispenserBehavior() {
                protected ProjectileEntity createProjectile(World world, Position position, ItemStack stack) {
                    return new BombEntity(world, position.getX(), position.getY(), position.getZ());
                }
            });
        }
    }
}