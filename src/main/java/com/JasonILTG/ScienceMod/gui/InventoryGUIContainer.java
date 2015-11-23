package com.JasonILTG.ScienceMod.gui;

import com.JasonILTG.ScienceMod.tileentity.TEInventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

public class InventoryGUIContainer extends Container {

    protected TEInventory te;
    protected int playerInvY;
    
    protected static final int PLAYER_INV_SLOTS_START = 0;
    protected static final int PLAYER_INV_SLOTS_END = 35;

    public InventoryGUIContainer(IInventory playerInv, TEInventory te, int playerInvY)
    {
        this.te = te;
        this.playerInvY = playerInvY;
        addPlayerInventorySlots(playerInv);
    }
    
    public void addPlayerInventorySlots(IInventory playerInv)
    {
        // Player Inventory, Slot 0-8, Slot IDs 0-8
        for (int x = 0; x < 9; ++x)
        {
            this.addSlotToContainer(new ScienceSlot(playerInv, x, 8 + x * 18, playerInvY + 58));
        }
        
    	// Player Inventory, Slot 9-35, Slot IDs 9-35
        for (int y = 0; y < 3; ++y)
        {
            for (int x = 0; x < 9; ++x)
            {
                this.addSlotToContainer(new ScienceSlot(playerInv, x + y * 9 + 9, 8 + x * 18, playerInvY + y * 18));
            }
        }
    }

    @Override
    public boolean canInteractWith(EntityPlayer playerIn)
    {
        return te.isUseableByPlayer(playerIn);
    }
    
    @Override
    public ItemStack transferStackInSlot(EntityPlayer playerIn, int fromSlot)
    {
        ItemStack previous = null;
        ScienceSlot slot = (ScienceSlot) this.inventorySlots.get(fromSlot);

        if( slot != null && slot.getHasStack() )
        {
            ItemStack current = slot.getStack();
            previous = current.copy();

            if( fromSlot > 35 )
            {
                // From Player Inventory to TE Inventory
                if( !this.mergeItemStack(current, 0, 35, true) ) return null;
            } 
            else
            {
                // From TE Inventory to Player Inventory
                if( !this.mergeItemStack(current, 36, this.getInventory().size(), false) ) return null;
            }

            if( current.stackSize == 0 ) slot.putStack((ItemStack) null);
            else slot.onSlotChanged();

            if( current.stackSize == previous.stackSize )return null;
            
            slot.onPickupFromSlot(playerIn, current);
        }
        return previous;
    }
}
