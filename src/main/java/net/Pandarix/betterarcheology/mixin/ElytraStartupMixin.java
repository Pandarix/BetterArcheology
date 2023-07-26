package net.Pandarix.betterarcheology.mixin;

import net.Pandarix.betterarcheology.BetterArcheology;
import net.Pandarix.betterarcheology.enchantment.ModEnchantments;
import net.Pandarix.betterarcheology.util.ModConfigs;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.predicate.NbtPredicate;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;

@Mixin(PlayerEntity.class)
public abstract class ElytraStartupMixin {
    @Shadow public abstract ItemStack getEquippedStack(EquipmentSlot slot);

    @Shadow public abstract void readCustomDataFromNbt(NbtCompound nbt);

    @Shadow public abstract void writeCustomDataToNbt(NbtCompound nbt);

    @Inject(method = "startFallFlying", at = @At(value = "TAIL"))
    private void injectMethod(CallbackInfo ci){
        //add a bool to specify if soaring winds should trigger
        boolean launchWithSoaringWinds = false;
        int enchantmentLevel = 0;

        if(ModConfigs.ARTIFACT_ENCHANTMENTS_ENABLED){
            //create an arraylist of all items that are checked for the enchantment
            ArrayList<ItemStack> itemsToCheck = new ArrayList<ItemStack>();
            itemsToCheck.add(this.getEquippedStack(EquipmentSlot.CHEST));

            if (ModConfigs.ELYTRA_TRINKETS_COMPATIBILITY) {
                //check if the trinktes mod is installed and there is nbt data for it on the player
                NbtCompound nbtData = NbtPredicate.entityToNbt((PlayerEntity) (Object) this);
                NbtCompound trinketsData = null;
                try{
                    trinketsData= nbtData.getCompound("cardinal_components").getCompound("trinkets:trinkets");
                }catch(Exception e){
                    BetterArcheology.LOGGER.info("No trinkets data found");
                }

                //if there is trinkets data, check chest/* slot for an elytra with soaring winds
                ArrayList<String> chestSlots = new ArrayList<String>();
                chestSlots.add("back");
                chestSlots.add("cape");

                if(trinketsData != null){
                    var chestData = trinketsData.getCompound("chest");
                    if(chestData != null){
                        for(int slotIndex = 0; slotIndex < chestSlots.size(); slotIndex++){
                            try {
                                var slotData = chestData.getCompound(chestSlots.get(slotIndex));
                                if (slotData != null) {
                                    var slotItems = slotData.getList("Items", 10);

                                    for (int itemIndex = 0; itemIndex < slotItems.size(); itemIndex++) {
                                        itemsToCheck.add(ItemStack.fromNbt(slotItems.getCompound(itemIndex)));
                                    }
                                }
                            }catch(Exception e){}
                        }
                    }
                }
            }

            //check all slots for an elytra with the soaring winds enchantment
            for(ItemStack item : itemsToCheck){
                if(!item.hasEnchantments()) {
                    continue;
                }

                //get the enchantment level of soaring winds and enable the launch if its greater than 0
                enchantmentLevel = EnchantmentHelper.getLevel(ModEnchantments.SOARING_WINDS, item);
                if(enchantmentLevel > 0){
                    launchWithSoaringWinds = true;
                    break;
                }

            }
        }

        //actually launch with soaring winds
        if(launchWithSoaringWinds){
            PlayerEntity betterarcheology$player = (PlayerEntity) (Object) this;
            float betterarcheology$boost = enchantmentLevel * ModConfigs.SOARING_WINDS_BOOST * 0.5f;
            Vec3d betterarcheology$vec3d = betterarcheology$player.getRotationVector();
            Vec3d betterarcheology$vec3d2 = betterarcheology$player.getVelocity();

            //add player velocity when starting to fall-fly
            betterarcheology$player.setVelocity(betterarcheology$vec3d2.add(
                    betterarcheology$vec3d.x * 0.1 + (betterarcheology$vec3d.x * 1.5 - betterarcheology$vec3d2.x) * betterarcheology$boost,
                    betterarcheology$vec3d.y * 0.1 + (betterarcheology$vec3d.y * 1.5 - betterarcheology$vec3d2.y) * betterarcheology$boost/2,
                    betterarcheology$vec3d.z * 0.1 + (betterarcheology$vec3d.z * 1.5 - betterarcheology$vec3d2.z) * betterarcheology$boost));

        }
    }
}
