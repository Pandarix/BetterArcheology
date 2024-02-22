package net.Pandarix.betterarcheology.screen;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;

public class FossilInventoryScreenHandler extends ScreenHandler
{
    private final Inventory inventory;

    public FossilInventoryScreenHandler(int syncId, PlayerInventory inventory)
    {
        this(syncId, inventory, new SimpleInventory(1));
    }

    public FossilInventoryScreenHandler(int syncId, PlayerInventory playerInventory, Inventory inventory)
    {
        super(ModScreenHandlers.FOSSIL_SCREEN_HANDLER, syncId);
        checkSize(inventory, 1);
        inventory.onOpen(playerInventory.player);
        this.inventory = inventory;

        this.addSlot(new Slot(inventory, 0, 80, 22));

        addPlayerInventory(playerInventory);
        addPlayerHotbar(playerInventory);
    }

    @Override
    public ItemStack quickMove(PlayerEntity player, int invSlot)
    {
        ItemStack newStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(invSlot);
        if (slot != null && slot.hasStack())
        {
            ItemStack originalStack = slot.getStack();
            newStack = originalStack.copy();
            if (invSlot < this.inventory.size())
            {
                if (!this.insertItem(originalStack, this.inventory.size(), this.slots.size(), true))
                {
                    return ItemStack.EMPTY;
                }
            } else if (!this.insertItem(originalStack, 0, this.inventory.size(), false))
            {
                return ItemStack.EMPTY;
            }

            if (originalStack.isEmpty())
            {
                slot.setStack(ItemStack.EMPTY);
            } else
            {
                slot.markDirty();
            }
        }

        return newStack;
    }

    @Override
    public boolean canUse(PlayerEntity player)
    {
        return this.inventory.canPlayerUse(player);
    }

    //Helper Method to add Players Inventoryslots to Screen
    private void addPlayerInventory(PlayerInventory playerInventory)
    {
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
        for (int i = 0; i < inventoryRows; ++i)
        {
            //Add a slot for every Column in the Row
            for (int l = 0; l < inventoryColumns; ++l)
            {
                //Numbers are Minecrafts pre-defined offsets due to the textures
                this.addSlot(new Slot(playerInventory, l + i * 9 + 9, 8 + l * 18, 86 + i * 18));
            }
        }
    }

    //Helper Method to add Players HotBarSlots to Screen
    private void addPlayerHotbar(PlayerInventory playerInventory)
    {
        //Adds a new Slot to the Screen for every Slot in the Players Hotbar
        for (int i = 0; i < PlayerInventory.getHotbarSize(); ++i)
        {
            //Numbers are Minecrafts pre-defined offsets due to the textures
            this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 144));
        }
    }
}
