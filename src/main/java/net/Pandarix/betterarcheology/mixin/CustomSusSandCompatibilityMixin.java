package net.Pandarix.betterarcheology.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.block.SuspiciousSandBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.SuspiciousSandBlockEntity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BrushItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BrushItem.class)
public class CustomSusSandCompatibilityMixin {

    //MIXIN SIGNATURE------------------------------------------------------------------------//
    //TODO: Maybe replace with a different type of mixin later, not Injecting but replacing one if()-Argument instead of this
    @Inject(method ="usageTick", at = @At(value = "INVOKE",            //Inject the following code at the use of:
            target ="Lnet/minecraft/world/World;playSound(" +          //The method world.playSound with the Parameters:
                    "Lnet/minecraft/entity/player/PlayerEntity;" +     //PlayerEntity
                    "Lnet/minecraft/util/math/BlockPos;" +             //Blockpos
                    "Lnet/minecraft/sound/SoundEvent;" +               //SoundEvent
                    "Lnet/minecraft/sound/SoundCategory;)V",           //SoundCategory, of return type void
            shift = At.Shift.AFTER))                                   //Inject code after the former method

    //INJECTED CODE-------------------------------------------------------------------------//
    private void injectMethod(World world, LivingEntity user, ItemStack stack, int remainingUseTicks, CallbackInfo ci) {
        //recreation of local variables because not all can be captured at point of execution, prevents LocalCapture.CAPTURE_FAILHARD for now
        //TODO: maybe captureable in a different way?
        //Item.raycast's access had to be widened in betteracheology.acceswidener
        PlayerEntity betterarcheology$playerEntity = (PlayerEntity) user;
        BlockHitResult betterarcheology$blockHitResult = Item.raycast(world, betterarcheology$playerEntity, RaycastContext.FluidHandling.NONE);
        BlockPos betterarcheology$blockPos = betterarcheology$blockHitResult.getBlockPos();
        BlockState betterarcheology$blockState = world.getBlockState(betterarcheology$blockPos);

        //copy of Vanilla code except the instanceof check (formerly just a 1:1 Block-Check)
        //allows for extending Blocks to also be brushed
        if (!world.isClient() && betterarcheology$blockState.getBlock() instanceof SuspiciousSandBlock) {
            BlockEntity var11 = world.getBlockEntity(betterarcheology$blockPos);
            if (var11 instanceof SuspiciousSandBlockEntity) {
                SuspiciousSandBlockEntity suspiciousSandBlockEntity = (SuspiciousSandBlockEntity)var11;
                boolean bl = suspiciousSandBlockEntity.brush(world.getTime(), betterarcheology$playerEntity, betterarcheology$blockHitResult.getSide());
                if (bl) {
                    stack.damage(1, user, (userx) -> {
                        userx.sendEquipmentBreakStatus(EquipmentSlot.MAINHAND);
                    });
                }
            }
        }
    }

}
