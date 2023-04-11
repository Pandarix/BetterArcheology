package net.Pandarix.betterarcheology.mixin;

import net.Pandarix.betterarcheology.enchantment.ModEnchantments;
import net.Pandarix.betterarcheology.util.ModConfigs;
import net.minecraft.block.BlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.MiningToolItem;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MiningToolItem.class)
public class TunnelingEnchantmentMixin {
    @Inject(method = "postMine", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;damage(ILnet/minecraft/entity/LivingEntity;Ljava/util/function/Consumer;)V", shift = At.Shift.AFTER))
    private void injectMethod(ItemStack stack, World world, BlockState state, BlockPos pos, LivingEntity miner, CallbackInfoReturnable<Boolean> cir) {
        if (ModConfigs.ARTIFACT_ENCHANTMENTS_ENABLED && !stack.isEmpty() && stack.hasEnchantments() && EnchantmentHelper.getLevel(ModEnchantments.TUNNELING, stack) == 1) {
            if (stack.isSuitableFor(state) && stack.isSuitableFor(world.getBlockState(pos.down())) && Math.abs((world.getBlockState(pos.down()).getHardness(world, pos.down()) - world.getBlockState(pos).getHardness(world, pos))) <= 3.75) {
                world.breakBlock(pos.down(), true, miner);
                stack.damage(1, miner, e -> e.sendEquipmentBreakStatus(EquipmentSlot.MAINHAND));
            }
        }
    }
}
