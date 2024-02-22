package net.Pandarix.betterarcheology.util;

import dev.emi.trinkets.api.TrinketComponent;
import dev.emi.trinkets.api.TrinketsApi;
import net.Pandarix.betterarcheology.enchantment.ModEnchantments;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ElytraItem;

import java.util.Objects;
import java.util.Optional;

public class ArtifactEnchantmentHelper
{
    public static boolean hasSoaringWinds(PlayerEntity player)
    {
        if (player == null)
        {
            return false;
        }

        //  if the Item is an Elytra and has Soaring winds, return true
        if (player.getEquippedStack(EquipmentSlot.CHEST).getItem() instanceof ElytraItem
                && EnchantmentHelper.getLevel(ModEnchantments.SOARING_WINDS, player.getEquippedStack(EquipmentSlot.CHEST)) >= 1)
        {
            return true;
        }

        // IF TRINKETS is installed, check for back-slot (used by ElytraSlot mod)
        if (FabricLoader.getInstance().isModLoaded("elytraslot"))
        {
            // if there is a cape-slot
            if (TrinketsApi.getPlayerSlots(player).get("chest").getSlots().containsKey("cape"))
            {
                // if the player has trinkets Data
                Optional<TrinketComponent> trinketData = TrinketsApi.getTrinketComponent(player);
                if (trinketData.isPresent())
                {
                    // check for a trinkets slot named "cape" with an ElytraItem with Soaring winds on it
                    return trinketData.get().getAllEquipped().stream().anyMatch((pair) ->
                            Objects.equals(pair.getLeft().inventory().getSlotType().getName(), "cape")
                                    && pair.getRight().getItem() instanceof ElytraItem
                                    && EnchantmentHelper.getLevel(ModEnchantments.SOARING_WINDS, pair.getRight()) >= 1
                    );
                }
            }
        }
        // if nothing succeeded, false
        return false;
    }
}