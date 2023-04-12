package net.Pandarix.betterarcheology.block.entity;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import joptsimple.util.KeyValuePair;
import net.Pandarix.betterarcheology.BetterArcheology;
import net.Pandarix.betterarcheology.block.custom.ArchelogyTable;
import net.Pandarix.betterarcheology.enchantment.ModEnchantments;
import net.Pandarix.betterarcheology.item.ModItems;
import net.Pandarix.betterarcheology.screen.IdentifyingScreenHandler;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.fabricmc.fabric.api.loot.v2.FabricLootPoolBuilder;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.BrushItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.loot.entry.LootPoolEntry;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.Pair;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.*;

import static net.Pandarix.betterarcheology.block.custom.ArchelogyTable.DUSTING;

public class ArcheologyTableBlockEntity extends BlockEntity implements NamedScreenHandlerFactory, ImplementedInventory {

    //default inventory size of the archeology table, TODO: In-/Decrease this when adding/ removing Slots!
    public static final int INV_SIZE = 3;
    //default number of Properties of ArcheologyTableBlockEntity, TODO: In-/Decrease this when adding/ removing Properties!
    public static final int PROPERTY_DELEGATES = 2;

    //count of custom slots inside the table
    private final DefaultedList<ItemStack> inventory = DefaultedList.ofSize(INV_SIZE, ItemStack.EMPTY);

    private final String translationKey = "archeology_table";   //used in getDisplayName using getTranslationKey

    //synchronises Ints between server and client
    protected final PropertyDelegate propertyDelegate;
    private int progress = 0;
    private int maxProgress = 72;

    private static final Identifier craftingLoot = new Identifier(BetterArcheology.MOD_ID, "identifying_loot");

    public ArcheologyTableBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.ARCHEOLOGY_TABLE, pos, state);
        this.propertyDelegate = new PropertyDelegate() {
            public int get(int index) {
                switch (index) {
                    case 0:
                        return ArcheologyTableBlockEntity.this.progress;
                    case 1:
                        return ArcheologyTableBlockEntity.this.maxProgress;
                    default:
                        return 0;
                }
            }

            public void set(int index, int value) {
                switch (index) {
                    case 0:
                        ArcheologyTableBlockEntity.this.progress = value;
                        break;
                    case 1:
                        ArcheologyTableBlockEntity.this.maxProgress = value;
                        break;
                }
            }

            public int size() {
                return PROPERTY_DELEGATES;
            }
        };
    }

    @Override
    public DefaultedList<ItemStack> getItems() {
        return this.inventory;
    }

    @Override
    public Text getDisplayName() {
        return Text.translatable(getCachedState().getBlock().getTranslationKey());
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return new IdentifyingScreenHandler(syncId, playerInventory, this, this.propertyDelegate);
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        Inventories.writeNbt(nbt, inventory);
        nbt.putInt("archeology_table.progress", progress);          //saves the block inventory upon closing the world
    }

    private void resetProgress() {
        this.progress = 0;
    }

    @Override
    public void readNbt(NbtCompound nbt) {                          //reads saved inventory upon opening the world
        Inventories.readNbt(nbt, inventory);
        super.readNbt(nbt);
        progress = nbt.getInt("archeology_table");
    }

    public static void tick(World world, BlockPos blockPos, BlockState blockState, ArcheologyTableBlockEntity entity) {
        if (world.isClient()) {
            return;
        }

        if (hasRecipe(entity)) {                                      //if the entity has a recipe inside:
            if (entity.progress % 10 == 0) {
                world.playSound(null, entity.pos, SoundEvents.ITEM_BRUSH_BRUSHING, SoundCategory.BLOCKS, 0.25f, 1f);
            }
            world.setBlockState(blockPos, blockState.with(DUSTING, true));
            entity.progress++;
            markDirty(world, blockPos, blockState);
            if (entity.progress >= entity.maxProgress) {              //if crafting progress is bigger or as big as the maxProgress, then craft the Item, else reset the timer
                craftItem(entity);
            }
        } else {
            world.setBlockState(blockPos, blockState.with(DUSTING, false));
            entity.resetProgress();
            markDirty(world, blockPos, blockState);
        }
    }

    private static void craftItem(ArcheologyTableBlockEntity entity) {
        SimpleInventory inventory = new SimpleInventory(entity.size());
        for (int i = 0; i < entity.size(); i++) {
            inventory.setStack(i, entity.getStack(i));
        }

        if (hasRecipe(entity) && entity.getStack(2).isEmpty()) {
            entity.removeStack(1, 1);
            ItemStack brush = entity.getStack(0);

            int newDamage = brush.getDamage() + 1;//calculate new Damage Value the item would have
            //if the item is supposed to break or the durability is smaller than zero
            if (newDamage > brush.getMaxDamage()) {
                entity.removeStack(0, 1);   //remove the Item
                assert entity.world != null;
                if (!entity.world.isClient()) {
                    //play break sound
                    entity.world.playSound(null, entity.pos, SoundEvents.ENTITY_ITEM_BREAK, SoundCategory.BLOCKS, 0.25f, 1f);
                }
            } else {
                //if not, set the damage to the calculated damage above
                brush.setDamage(newDamage);    //TODO: Balance Damage taken
            }

            if (!entity.world.isClient()) {
                //play sound after crafting
                entity.world.playSound(null, entity.pos, SoundEvents.ITEM_BRUSH_BRUSH_SAND_COMPLETED, SoundCategory.BLOCKS, 0.5f, 1f);
            }
            entity.setStack(2, generateCraftingLoot(entity, entity.world));    //set crafted output in the output slot, TODO: Replace Output
            entity.resetProgress(); //resets crafting progress
        }

    }

    private static ItemStack generateCraftingLoot(BlockEntity entity, World world) {
        LootTable lootTable = Objects.requireNonNull(world.getServer()).getLootManager().getTable(craftingLoot);

        LootContext.Builder builder = new LootContext.Builder((ServerWorld) world).parameter(LootContextParameters.ORIGIN, Vec3d.ofCenter(entity.getPos())).random(world.getRandom()).luck(0);

        ObjectArrayList<ItemStack> objectArrayList = lootTable.generateLoot(builder.build(LootContextTypes.CHEST));

        if (objectArrayList.size() == 0) {
            return ItemStack.EMPTY;
        }
        if (objectArrayList.size() == 1) {
            return objectArrayList.get(0);
        }
        return ItemStack.EMPTY;
    }

    private static boolean hasRecipe(ArcheologyTableBlockEntity entity) {           //recipe type can be configured here
        SimpleInventory inventory = new SimpleInventory(entity.size());
        for (int i = 0; i < entity.size(); i++) {
            inventory.setStack(i, entity.getStack(i));
        }

        boolean hasShardInFirstSlot = entity.getStack(1).getItem() == ModItems.UNIDENTIFIED_ARTIFACT;                     //Input
        boolean hasBrushInSlot = entity.getStack(0).getItem() == ModItems.IRON_BRUSH ||
                entity.getStack(0).getItem() == ModItems.DIAMOND_BRUSH ||
                entity.getStack(0).getItem() == Items.BRUSH;
        return hasShardInFirstSlot && hasBrushInSlot && canInsertAmountIntoOutputSlot(inventory) && canInsertItemIntoOutputSlot(inventory, Items.ACACIA_FENCE);                                  //TODO: REPLACE OUTPUT
    }

    @Override
    public boolean canExtract(int slot, ItemStack stack, Direction side) {
        //only extract on the bottom
        return side == Direction.DOWN && slot == 2;
    }

    @Override
    public boolean canInsert(int slot, ItemStack stack, @Nullable Direction side) {
        //no insertion into the output slot
        if (side == Direction.DOWN) {return false;}
        //if the top is targeted and the item is a Brush, insert
        if (side == Direction.UP) {return slot == 0 && stack.getItem() instanceof BrushItem;}
        //for the sides: if it is an unidentified artifact
        return slot == 1 && stack.isOf(ModItems.UNIDENTIFIED_ARTIFACT);
    }

    private static boolean canInsertItemIntoOutputSlot(SimpleInventory inventory, Item output) {
        return inventory.getStack(2).getItem() == output || inventory.getStack(2).isEmpty();
    }

    private static boolean canInsertAmountIntoOutputSlot(SimpleInventory inventory) {
        return inventory.getStack(2).getMaxCount() > inventory.getStack(2).getCount();
    }
}
