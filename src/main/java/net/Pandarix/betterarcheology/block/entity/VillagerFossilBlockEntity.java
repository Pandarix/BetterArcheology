package net.Pandarix.betterarcheology.block.entity;

import net.Pandarix.betterarcheology.BetterArcheology;
import net.Pandarix.betterarcheology.networking.ModMessages;
import net.Pandarix.betterarcheology.screen.FossilInventoryScreenHandler;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;

public class VillagerFossilBlockEntity extends BlockEntity implements NamedScreenHandlerFactory, ImplementedInventory {
    private static final Identifier identifier = new Identifier(BetterArcheology.MOD_ID, "villager_fossil_block_entity");

    private final DefaultedList<ItemStack> inventory = DefaultedList.ofSize(1, ItemStack.EMPTY);

    public VillagerFossilBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.VILLAGER_FOSSIL, pos, state);
    }


    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        Inventories.writeNbt(nbt, inventory);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        Inventories.readNbt(nbt, inventory);
        super.readNbt(nbt);
    }

    @Override
    public DefaultedList<ItemStack> getItems(){
        return this.inventory;
    }

    @Override
    public Text getDisplayName(){
        return Text.translatable(getCachedState().getBlock().getTranslationKey());
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory inv, PlayerEntity player){
        return new FossilInventoryScreenHandler(syncId, inv, this);
    }

    public ItemStack getInventoryContents() {
        return this.getStack(0);
    }

    public void setInventory(DefaultedList<ItemStack> inventory) {
        for (int i = 0; i < inventory.size(); i++) {
            this.inventory.set(i, inventory.get(i));
        }
    }

    @Override
    public void markDirty() {
        assert world != null;
        if(!world.isClient()) {
            PacketByteBuf data = PacketByteBufs.create();
            data.writeInt(inventory.size());
            for (ItemStack itemStack : inventory) {
                data.writeItemStack(itemStack);
            }
            data.writeBlockPos(getPos());

            for (ServerPlayerEntity player : PlayerLookup.tracking((ServerWorld) world, getPos())) {
                ServerPlayNetworking.send(player, ModMessages.ITEM_SYNC, data);
            }
        }

        super.markDirty();
    }
}
