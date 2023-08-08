package net.Pandarix.betterarcheology.util;

import com.mojang.datafixers.util.Pair;
import net.Pandarix.betterarcheology.BetterArcheology;

public class ModConfigs {
    public static SimpleConfig CONFIG;
    private static ModConfigProvider configs;
    public static boolean ARTIFACT_ENCHANTMENTS_ENABLED;
    public static float PENETRATING_STRIKE_PROTECTION_IGNORANCE;
    public static float SOARING_WINDS_BOOST;
    public static int SOARING_WINDS_MAXLEVEL;
    public static int OCELOT_FOSSIL_FLEE_RANGE;

    public static void registerConfigs() {
        configs = new ModConfigProvider();
        createConfigs();

        CONFIG = SimpleConfig.of(BetterArcheology.MOD_ID + "config").provider(configs).request();

        assignConfigs();
    }

    private static void createConfigs() {
        configs.addKeyValuePair(new Pair<>("artifact.enchantments.enabled", true), "Set to true or false to enable or disable effects");
        configs.addKeyValuePair(new Pair<>("penetrating.strike.protection.ignorance", 0.33f), "Set to % of damage-reduction from Protection Enchantments that should be ignored, keep in range of 0-1.00");
        configs.addKeyValuePair(new Pair<>("soaring.winds.boost", 0.5f), "Set to movement speed multiplier, that should be applied when starting to fly");
        configs.addKeyValuePair(new Pair<>("soaring.winds.maxlevel", 1), "Set the maximum level for soaring winds");
        configs.addKeyValuePair(new Pair<>("ocelot.fossil.flee.range", 20), "Range in Block that the Fossil scares Creepers away");
    }

    private static void assignConfigs() {
        ARTIFACT_ENCHANTMENTS_ENABLED = CONFIG.getOrDefault("artifact.enchantments.enabled", true);
        PENETRATING_STRIKE_PROTECTION_IGNORANCE = (float) CONFIG.getOrDefault("penetrating.strike.protection.ignorance", 0.33f);
        SOARING_WINDS_BOOST = (float) CONFIG.getOrDefault("soaring.winds.boost", 0.3f);
        SOARING_WINDS_MAXLEVEL = CONFIG.getOrDefault("soaring.winds.maxlevel", 1);
        OCELOT_FOSSIL_FLEE_RANGE = CONFIG.getOrDefault("ocelot.fossil.flee.range", 20);

        System.out.println("All " + configs.getConfigsList().size() + " have been set properly");
    }
}