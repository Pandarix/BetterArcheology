package net.Pandarix.betterarcheology.world.processor;

import com.yungnickyoung.minecraft.yungsapi.api.autoregister.AutoRegister;
import net.Pandarix.betterarcheology.BetterArcheology;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.structure.processor.StructureProcessorType;
import net.minecraft.util.Identifier;

@AutoRegister(BetterArcheology.MOD_ID)
public class ModProcessorTypes
{
    public static StructureProcessorType<WaterlogFixProcessor> WATERLOGFIX_PROCESSOR;

    public static void registerProcessorTypes() {
        WATERLOGFIX_PROCESSOR = Registry.register(Registries.STRUCTURE_PROCESSOR, new Identifier(BetterArcheology.MOD_ID, "waterlogfix_processor"), () -> WaterlogFixProcessor.CODEC);
    }
}