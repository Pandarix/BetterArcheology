package net.Pandarix.betterarcheology.world.processor;

import com.yungnickyoung.minecraft.yungsapi.api.autoregister.AutoRegister;
import net.Pandarix.betterarcheology.BetterArcheology;
import net.minecraft.structure.processor.StructureProcessorType;

@AutoRegister(BetterArcheology.MOD_ID)
public class ModProcessorTypes
{
    @AutoRegister("waterlogfix_processor")
    public static StructureProcessorType<WaterlogFixProcessor> WATERLOGFIX_PROCESSOR = () -> WaterlogFixProcessor.CODEC;
}
