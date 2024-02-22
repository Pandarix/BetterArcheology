package net.Pandarix.betterarcheology.mixin;

import net.Pandarix.betterarcheology.block.entity.SkeletonFleeFromBlockEntity;
import net.Pandarix.betterarcheology.util.FleeBlockGoal;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.GoalSelector;
import net.minecraft.entity.mob.AbstractSkeletonEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(AbstractSkeletonEntity.class)
public class AddSkeletonGoalsMixin
{

    @Redirect(method = "initGoals", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/ai/goal/GoalSelector;add(ILnet/minecraft/entity/ai/goal/Goal;)V", ordinal = 3))
    private void injectMethod(GoalSelector instance, int priority, Goal goal)
    {
        instance.add(priority, goal); //add what would've been added anyway
        instance.add(priority, new FleeBlockGoal<>((AbstractSkeletonEntity) (Object) this, SkeletonFleeFromBlockEntity.class, 1.0, 1.2));
    }
}
