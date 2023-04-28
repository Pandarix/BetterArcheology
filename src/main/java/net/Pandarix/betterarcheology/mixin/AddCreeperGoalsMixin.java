package net.Pandarix.betterarcheology.mixin;

import net.Pandarix.betterarcheology.block.entity.FleeFromBlockEntity;
import net.Pandarix.betterarcheology.util.FleeBlockGoal;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.GoalSelector;
import net.minecraft.entity.mob.CreeperEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(CreeperEntity.class)
public class AddCreeperGoalsMixin {
    @Redirect(method = "initGoals", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/ai/goal/GoalSelector;add(ILnet/minecraft/entity/ai/goal/Goal;)V", ordinal = 3))
    private void injectMethod(GoalSelector instance, int priority, Goal goal){
        instance.add(priority, goal); //add what would've been added anyway
        instance.add(priority, new FleeBlockGoal<>((CreeperEntity) (Object) this, FleeFromBlockEntity.class, 1.0, 1.2));
    }
}
