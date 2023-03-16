package net.Pandarix.betterarcheology.screen;

import net.Pandarix.betterarcheology.block.entity.ArcheologyTableBlockEntity;
import net.Pandarix.betterarcheology.item.ModItems;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
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
        this(syncId, inventory, new SimpleInventory(ArcheologyTableBlockEntity.INV_SIZE), new ArrayPropertyDelegate(ArcheologyTableBlockEntity.PROPERTY_DELEGATES));
    }

    public IdentifyingScreenHandler(int syncId, PlayerInventory playerInventory, Inventory inventory, PropertyDelegate delegate) {
        super(ModScreenHandlers.IDENTIFYING_SCREEN_HANDLER, syncId);    //creates a new Instance of Screenhandler
        checkSize(inventory, ArcheologyTableBlockEntity.INV_SIZE);
        this.inventory = inventory; //sets the Screens Inventory to the given Inventory

        //opens Inventory
        inventory.onOpen(playerInventory.player);

        //defines delegate field
        this.propertyDelegate = delegate;

        //SLOTS
        //TODO: Redo coordinates
        this.addSlot(new Slot(inventory, 0, 80,20));
        this.addSlot(new Slot(inventory, 1,26 ,48));
        this.addSlot(new Slot(inventory, 2, 134,48));

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
        int maxProgress = this.propertyDelegate.get(1);                         // Maximum Progress, after reaching: progress done
        int progressArrowSize = 74;                                             // This is the width in pixels of your arrow //TODO: Edit correct size

        return maxProgress != 0 && progress != 0 ? progress * progressArrowSize / maxProgress : 0;
    }

    @Override
    public ItemStack quickMove(PlayerEntity player, int invSlot) {
        ItemStack newStack = ItemStack.EMPTY;   //defines an empty ItemStack, will be used to return the changed Item in the Slot
        Slot slot = this.slots.get(invSlot);    //the given InventorySlot



        //if the slot has an Item inside
        if (slot != null && slot.hasStack()) {
            ItemStack originalStack = slot.getStack();  //stores the Item that was inside the given slot

           /* if (originalStack.isItemEqual(ModItems.IRON_BRUSH.getDefaultStack()) ||
                    originalStack.isItemEqual(ModItems.DIAMOND_BRUSH.getDefaultStack()) ||
                    originalStack.isItemEqual(Items.BRUSH.getDefaultStack())) {

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

            if (!originalStack.isItemEqual(ModItems.UNIDENTIFIED_ARTIFACT.getDefaultStack())) {
                return ItemStack.EMPTY;
            } */

            newStack = originalStack.copy();    //sets the new Stack to the given Item

            if (invSlot < this.inventory.size()) {
                if (!this.insertItem(originalStack, this.inventory.size(), this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.insertItem(originalStack, 1, this.inventory.size(), false)) {
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

    //Helper Method to add Players Inventoryslots to Screen
    private void addPlayerInventory(PlayerInventory playerInventory) {
        /*Adding Slots of Main Inventory
                COL:    COL:    COL:    ...
        ROW:    slot    slot    slot
        ROW:    slot    slot    slot
        ...
        */

        //MainSize is the number of Slots besides the Armor and Offhand
        //HotbarSize is the number of Slots in the Hotbar, which incidentally is the number of slots per Column
        int inventorySize = PlayerInventory.MAIN_SIZE - PlayerInventory.getHotbarSize();    //Because Main includes the Hotbar Slots, we have to subtract them to get the raw Inventory size
        int inventoryRows = inventorySize / PlayerInventory.getHotbarSize();    //All Slots : Slots per Column = Number of Rows to draw
        int inventoryColumns = PlayerInventory.getHotbarSize();

        //For every Row in the Inventory
        for (int i = 0; i < inventoryRows; ++i) {
            //Add a slot for every Column in the Row
            for (int l = 0; l < inventoryColumns; ++l) {
                //Numbers are Minecrafts pre-defined offsets due to the textures
                this.addSlot(new Slot(playerInventory, l + i * 9 + 9, 8 + l * 18, 86 + i * 18));
            }
        }
    }

    //Helper Method to add Players HotBarSlots to Screen
    private void addPlayerHotbar(PlayerInventory playerInventory) {
        //Adds a new Slot to the Screen for every Slot in the Players Hotbar
        for (int i = 0; i < PlayerInventory.getHotbarSize(); ++i){
            //Numbers are Minecrafts pre-defined offsets due to the textures
            this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 144));
        }
    }
}
