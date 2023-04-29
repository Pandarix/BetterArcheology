package net.Pandarix.betterarcheology.util;

import net.Pandarix.betterarcheology.block.entity.FleeFromBlockEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.NoPenaltyTargeting;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.ai.pathing.Path;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.chunk.Chunk;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

public class FleeBlockGoal<T extends BlockEntity> extends Goal {
    protected final PathAwareEntity mob;
    private final double slowSpeed;
    private final double fastSpeed;
    @Nullable
    protected BlockEntity targetBlock;
    @Nullable
    protected Path fleePath;
    protected final EntityNavigation fleeingEntityNavigation;
    protected final Class<T> classToFleeFrom;

    public FleeBlockGoal(PathAwareEntity mob, Class<T> fleeFromType, double slowSpeed, double fastSpeed) {
        this.mob = mob;
        this.classToFleeFrom = fleeFromType;
        this.slowSpeed = slowSpeed;
        this.fastSpeed = fastSpeed;
        this.fleeingEntityNavigation = mob.getNavigation();
        this.setControls(EnumSet.of(Control.MOVE));
    }

    public boolean canStart() {
        this.targetBlock = this.getClosestBlockEntity(this.mob);
        if (this.targetBlock == null) {
            return false;
        } else {
            Vec3d vec3d = NoPenaltyTargeting.findFrom(this.mob, 16, 7, this.targetBlock.getPos().toCenterPos());
            if (vec3d == null) {
                return false;
            } else if (vec3d.squaredDistanceTo(targetBlock.getPos().getX(), targetBlock.getPos().getY(), targetBlock.getPos().getZ()) < this.mob.squaredDistanceTo(this.targetBlock.getPos().toCenterPos())) {
                return false;
            } else {
                this.fleePath = this.fleeingEntityNavigation.findPathTo(vec3d.x, vec3d.y, vec3d.z, 0);
                return this.fleePath != null;
            }
        }
    }

    private BlockEntity getClosestBlockEntity(PathAwareEntity fleeingEntity) {
        BlockEntity closestBlockEntity = null; //set to no Entity for now
        double closestDistanceSq = Double.MAX_VALUE; //initially the biggest value possible

        //searches every blockEntity from the list
        for (BlockEntity blockEntity : getBlockEntitiesInRange(fleeingEntity)) {
            double distanceSq = blockEntity.getPos().getSquaredDistance(fleeingEntity.getPos()); //calculate distance
            //if this distance is closer than the previous, set the closest Entity to this one
            if (distanceSq < closestDistanceSq) {
                closestBlockEntity = blockEntity;
                closestDistanceSq = distanceSq;
            }
        }
        return closestBlockEntity;
    }

    private List<BlockEntity> getBlockEntitiesInRange(PathAwareEntity fleeingEntity) {
        List<BlockEntity> blockEntities = new ArrayList<>();

        int chunkX = MathHelper.floor(fleeingEntity.getPos().getX()) >> 4; // Divide by 16 to get chunk coordinates
        int chunkZ = MathHelper.floor(fleeingEntity.getPos().getZ()) >> 4;

        Chunk chunk = fleeingEntity.getEntityWorld().getChunk(chunkX, chunkZ);

        for (BlockPos blockPos : chunk.getBlockEntityPositions()) {
            BlockEntity blockEntity = fleeingEntity.getEntityWorld().getBlockEntity(blockPos);
            if (blockEntity != null && blockEntity.getPos().isWithinDistance(fleeingEntity.getPos(), ModConfigs.OCELOT_FOSSIL_FLEE_RANGE)) { // Check if within distance
                blockEntities.add(blockEntity);
            }
        }

        return blockEntities;
    }

    public boolean shouldContinue() {
        return !this.fleeingEntityNavigation.isIdle();
    }

    public void start() {
        this.fleeingEntityNavigation.startMovingAlong(this.fleePath, this.slowSpeed);
    }

    public void stop() {
        this.targetBlock = null;
    }

    public void tick() {
        if (this.mob.squaredDistanceTo(this.targetBlock.getPos().toCenterPos()) < 49.0) {
            this.mob.getNavigation().setSpeed(this.fastSpeed);
        } else {
            this.mob.getNavigation().setSpeed(this.slowSpeed);
        }

    }

}
