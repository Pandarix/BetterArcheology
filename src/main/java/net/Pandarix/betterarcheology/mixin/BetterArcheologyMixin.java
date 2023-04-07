package net.Pandarix.betterarcheology.mixin;

import net.Pandarix.betterarcheology.BetterArcheology;
import net.Pandarix.betterarcheology.util.ModConfigs;
import net.minecraft.client.gui.screen.TitleScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TitleScreen.class)
public class BetterArcheologyMixin {
	@Inject(at = @At("HEAD"), method = "init()V")
	private void init(CallbackInfo info) {
		BetterArcheology.LOGGER.info("BetterArcheology Mixin initialized, prepare for impact");
		BetterArcheology.LOGGER.info("BetterArcheology Artifact Enchantments are " + (ModConfigs.ARTIFACT_ENCHANTMENTS_ENABLED ? "enabled" : "disabled"));
	}
}
