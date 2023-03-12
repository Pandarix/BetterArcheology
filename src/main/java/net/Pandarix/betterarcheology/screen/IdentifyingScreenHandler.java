package net.Pandarix.betterarcheology.screen;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ArrayPropertyDelegate;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;

public class IdentifyingScreenHandler extends ScreenHandler {
    private final Inventory inventory;
    private final PropertyDelegate propertyDelegate;
    public IdentifyingScreenHandler(int syncId, PlayerInventory inventory) {
        //size of SimpleInventory has to be same as in Defaulted List of ArcheologyTableBlockEntity;
        //size of ArrayPropertyDelegate has to be the same as the PropertyDelegate of ArcheologyTableBlockEntity (number of ints being tracked)
        this(syncId, inventory, new SimpleInventory(3), new ArrayPropertyDelegate(2));
    }
    public IdentifyingScreenHandler(int syncId, PlayerInventory playerInventory, Inventory inventory, PropertyDelegate delegate) {
        super(ModScreenHandlers.IDENTIFYING_SCREEN_HANDLER, syncId);
        checkSize(inventory, 3);
        //defines inventory field
        this.inventory = inventory;

        //opens Inventory
        inventory.onOpen(playerInventory.player);

        //defines delegate field
        this.propertyDelegate = delegate;

        //SLOTS
        this.addSlot(new Slot(inventory, 0, 1,3));
        this.addSlot(new Slot(inventory, 1, 2,4));          //TODO: Redo coordinates
        this.addSlot(new Slot(inventory, 2, 5,6));

        //Bottom screen components to render current players inventory & hotbar
        addPlayerInventory(playerInventory);
        addPlayerHotbar(playerInventory);
        addProperties(delegate);
    }

    public boolean isCrafting() {
        return propertyDelegate.get(0) > 0;
    }

    public int getScaledProgress() {
        int progress = this.propertyDelegate.get(0);
        int maxProgress = this.propertyDelegate.get(1);                         // Max Progress
        int progressArrowSize = 26;                                             // This is the width in pixels of your arrow //TODO: Edit correct size

        return maxProgress != 0 && progress != 0 ? progress * progressArrowSize / maxProgress : 0;
    }
    @Override
    public ItemStack quickMove(PlayerEntity player, int invSlot) {
        ItemStack newStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(invSlot);
        if (slot != null && slot.hasStack()) {
            ItemStack originalStack = slot.getStack();
            newStack = originalStack.copy();
            if (invSlot < this.inventory.size()) {
                if (!this.insertItem(originalStack, this.inventory.size(), this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.insertItem(originalStack, 0, this.inventory.size(), false)) {
                return ItemStack.EMPTY;
            }

            if (originalStack.isEmpty()) {
                slot.setStack(ItemStack.EMPTY);
            } else {
                slot.markDirty();
            }
        }

        return newStack;
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return this.inventory.canPlayerUse(player);
    }

    private void addPlayerInventory(PlayerInventory playerInventory) {                                          //Helper Method to define Player  Inventory
        for (int i = 0; i < 3; ++i) {
            for (int l = 0; l < 9; ++l) {
                this.addSlot(new Slot(playerInventory, l + i * 9 + 9, 8 + l * 18, 86 + i * 18));
            }
        }
    }

    private void addPlayerHotbar(PlayerInventory playerInventory) {                                             //Helper Method to define Player Slot
        for (int i = 0; i < 9; ++i) {
            this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 144));
        }
    }
}
