package net.Pandarix.betterarcheology.block.custom;

import net.Pandarix.betterarcheology.BetterArcheology;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ExperienceOrbEntity;
import net.minecraft.entity.mob.SilverfishEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;

public class LootVaseBlock extends Block {
    private static final VoxelShape SHAPE = Block.createCuboidShape(3, 0, 3, 13, 14, 13);
    Identifier ADVANCEMENT_ID = new Identifier(BetterArcheology.MOD_ID, "loot_vase_broken");
    public LootVaseBlock(Settings settings) {
        super(settings);
    }

    @Override
    public void onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        if (!world.isClient()) {
            if (!player.isCreative() && EnchantmentHelper.getLevel(Enchantments.SILK_TOUCH, player.getMainHandStack()) <= 0) {
                Entity xpOrb = new ExperienceOrbEntity(world, pos.getX(), pos.getY(), pos.getZ(), 4);
                world.spawnEntity(xpOrb);
            }
            getServerPlayer(player).getAdvancementTracker().grantCriterion(world.getServer().getAdvancementLoader().get(ADVANCEMENT_ID), "criteria");
        }
        super.onBreak(world, pos, state, player);
    }

    private static ServerPlayerEntity getServerPlayer(PlayerEntity player) {
        if (player instanceof ServerPlayerEntity) {
            return (ServerPlayerEntity) player;
        } else {
            return null;
        }
    }

    @Override
    public void onStacksDropped(BlockState state, ServerWorld world, BlockPos pos, ItemStack tool, boolean dropExperience) {
        super.onStacksDropped(state, world, pos, tool, dropExperience);
        if (world.getGameRules().getBoolean(GameRules.DO_TILE_DROPS) && EnchantmentHelper.getLevel(Enchantments.SILK_TOUCH, tool) == 0) {
            if(world.getRandom().nextInt(25) == 1){
                spawnSilverFish(world, pos);
            }
        }
    }

    private static void spawnSilverFish(ServerWorld world, BlockPos pos){
        SilverfishEntity silverfishEntity = (SilverfishEntity)EntityType.SILVERFISH.create(world);
        if (silverfishEntity != null) {
            silverfishEntity.refreshPositionAndAngles((double)pos.getX() + 0.5, (double)pos.getY(), (double)pos.getZ() + 0.5, 0.0F, 0.0F);
            world.spawnEntity(silverfishEntity);
            silverfishEntity.playSpawnEffects();
        }
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }
}
