package net.Pandarix.betterarcheology.block.entity;

import com.mojang.logging.LogUtils;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.Pandarix.betterarcheology.BetterArcheology;
import net.Pandarix.betterarcheology.block.custom.FossiliferousBlock;
import net.Pandarix.betterarcheology.block.entity.ModBlockEntities;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.context.*;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.state.property.Property;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;

import java.util.Objects;

public class DiggableBlockEntity extends BlockEntity {
    private static final Logger field_42801 = LogUtils.getLogger();
    private static final String LOOT_TABLE_NBT_KEY = "LootTable";
    private static final String LOOT_TABLE_SEED_NBT_KEY = "LootTableSeed";
    private static final String HIT_DIRECTION_NBT_KEY = "hit_direction";
    private static final String ITEM_NBT_KEY = "item";
    private static final int field_42806 = 10;
    private static final int field_42807 = 40;
    private static final int field_42808 = 10;
    private int digsCount;
    private long nextDigTime;
    private long nextShovelTime;
    private ItemStack item;
    @Nullable
    private Direction hitDirection;
    @Nullable
    private Identifier lootTable = new Identifier(BetterArcheology.MOD_ID, "diggable_block");
    private long lootTableSeed;

    public DiggableBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.DIGGABLE_BLOCK, pos, state);
        this.item = ItemStack.EMPTY;
    }

    public boolean dig(long worldTime, PlayerEntity player, Direction hitDirection) {
        if (this.hitDirection == null) {
            this.hitDirection = hitDirection;
        }

        this.nextDigTime = worldTime + 40L;
        if (worldTime >= this.nextShovelTime && this.world instanceof ServerWorld) {
            this.nextShovelTime = worldTime + 10L;
            this.generateItem(player);
            int i = this.getDugLevel();
            if (++this.digsCount >= 10) {
                this.finishDigging(player);
                return true;
            } else {
                this.world.scheduleBlockTick(this.getPos(), this.getCachedState().getBlock(), 40);
                int j = this.getDugLevel();
                if (i != j) {
                    BlockState blockState = this.getCachedState();
                    BlockState blockState2 = (BlockState)blockState.with(FossiliferousBlock.DUG, j);
                    this.world.setBlockState(this.getPos(), blockState2, 3);
                }

                return false;
            }
        } else {
            return false;
        }
    }

    public void generateItem(PlayerEntity player) {
        if (this.lootTable != null && this.world != null && !this.world.isClient() && this.world.getServer() != null) {
            LootTable lootTable = this.world.getServer().getLootManager().getLootTable(this.lootTable);
            if (player instanceof ServerPlayerEntity) {
                ServerPlayerEntity serverPlayerEntity = (ServerPlayerEntity)player;
                Criteria.PLAYER_GENERATES_CONTAINER_LOOT.trigger(serverPlayerEntity, this.lootTable);
            }

            LootContextParameterSet lootContextParameterSet = (new LootContextParameterSet.Builder((ServerWorld)this.world)).add(LootContextParameters.ORIGIN, Vec3d.ofCenter(this.pos)).luck(player.getLuck()).add(LootContextParameters.THIS_ENTITY, player).build(LootContextTypes.CHEST);
            ObjectArrayList<ItemStack> objectArrayList = lootTable.generateLoot(lootContextParameterSet, this.lootTableSeed);

            ItemStack itemStack;
            switch (objectArrayList.size()) {
                case 0:
                    itemStack = ItemStack.EMPTY;
                    break;
                case 1:
                    itemStack = (ItemStack)objectArrayList.get(0);
                    break;
                default:
                    Identifier table = this.lootTable;
                    field_42801.warn("Expected max 1 loot from loot table " + table + " got " + objectArrayList.size());
                    itemStack = (ItemStack)objectArrayList.get(0);
            }

            this.item = itemStack;
            this.lootTable = null;
            this.markDirty();
        }
    }

    private void finishDigging(PlayerEntity player) {
        if (this.world != null && this.world.getServer() != null) {
            this.spawnItem(player);
            BlockState blockState = this.getCachedState();
            this.world.syncWorldEvent(3008, this.getPos(), Block.getRawIdFromState(blockState));
            Block block = this.getCachedState().getBlock();
            Block block2;
            if (block instanceof FossiliferousBlock fossiliferousBlock) {
                block2 = fossiliferousBlock.getBaseBlock();
            } else {
                block2 = Blocks.AIR;
            }

            this.world.setBlockState(this.pos, block2.getDefaultState(), 3);
        }
    }

    private void spawnItem(PlayerEntity player) {
        if (this.world != null && this.world.getServer() != null) {
            this.generateItem(player);
            if (!this.item.isEmpty()) {
                double d = (double) EntityType.ITEM.getWidth();
                double e = 1.0 - d;
                double f = d / 2.0;
                Direction direction = (Direction) Objects.requireNonNullElse(this.hitDirection, Direction.UP);
                BlockPos blockPos = this.pos.offset(direction, 1);
                double g = (double)blockPos.getX() + 0.5 * e + f;
                double h = (double)blockPos.getY() + 0.5 + (double)(EntityType.ITEM.getHeight() / 2.0F);
                double i = (double)blockPos.getZ() + 0.5 * e + f;
                ItemEntity itemEntity = new ItemEntity(this.world, g, h, i, this.item.split(this.world.random.nextInt(21) + 10));
                itemEntity.setVelocity(Vec3d.ZERO);
                this.world.spawnEntity(itemEntity);
                this.item = ItemStack.EMPTY;
            }

        }
    }

    public void scheduledTick() {
        if (this.world != null) {
            if (this.digsCount != 0 && this.world.getTime() >= this.nextDigTime) {
                int i = this.getDugLevel();
                this.digsCount = Math.max(0, this.digsCount - 2);
                int j = this.getDugLevel();
                if (i != j) {
                    this.world.setBlockState(this.getPos(), (BlockState)this.getCachedState().with(FossiliferousBlock.DUG, j), 3);
                }

                this.nextDigTime = this.world.getTime() + 4L;
            }

            if (this.digsCount == 0) {
                this.hitDirection = null;
                this.nextDigTime = 0L;
                this.nextShovelTime = 0L;
            } else {
                this.world.scheduleBlockTick(this.getPos(), this.getCachedState().getBlock(), (int)(this.nextDigTime - this.world.getTime()));
            }

        }
    }

    private boolean readLootTableFromNbt(NbtCompound nbt) {
        if (nbt.contains("LootTable", 8)) {
            this.lootTable = new Identifier(nbt.getString("LootTable"));
            this.lootTableSeed = nbt.getLong("LootTableSeed");
            return true;
        } else {
            return false;
        }
    }

    private boolean writeLootTableToNbt(NbtCompound nbt) {
        if (this.lootTable == null) {
            return false;
        } else {
            nbt.putString("LootTable", this.lootTable.toString());
            if (this.lootTableSeed != 0L) {
                nbt.putLong("LootTableSeed", this.lootTableSeed);
            }

            return true;
        }
    }

    public NbtCompound toInitialChunkDataNbt() {
        NbtCompound nbtCompound = super.toInitialChunkDataNbt();
        if (this.hitDirection != null) {
            nbtCompound.putInt("hit_direction", this.hitDirection.ordinal());
        }

        nbtCompound.put("item", this.item.writeNbt(new NbtCompound()));
        return nbtCompound;
    }

    public BlockEntityUpdateS2CPacket toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    public void readNbt(NbtCompound nbt) {
        if (!this.readLootTableFromNbt(nbt) && nbt.contains("item")) {
            this.item = ItemStack.fromNbt(nbt.getCompound("item"));
        }

        if (nbt.contains("hit_direction")) {
            this.hitDirection = Direction.values()[nbt.getInt("hit_direction")];
        }

    }

    protected void writeNbt(NbtCompound nbt) {
        if (!this.writeLootTableToNbt(nbt)) {
            nbt.put("item", this.item.writeNbt(new NbtCompound()));
        }

    }

    public void setLootTable(Identifier lootTable, long seed) {
        this.lootTableSeed = seed;
    }

    private int getDugLevel() {
        if (this.digsCount == 0) {
            return 0;
        } else if (this.digsCount < 3) {
            return 1;
        } else {
            return this.digsCount < 6 ? 2 : 3;
        }
    }

    @Nullable
    public Direction getHitDirection() {
        return this.hitDirection;
    }

    public ItemStack getItem() {
        return this.item;
    }
}
