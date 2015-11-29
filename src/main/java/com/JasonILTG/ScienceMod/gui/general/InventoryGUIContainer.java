package com.JasonILTG.ScienceMod.gui.general;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

public class InventoryGUIContainer extends Container
{
	
	protected IInventory inventory;
	protected int playerInvY;
	protected int playerInvStartID;
	
	public InventoryGUIContainer(IInventory te, int playerInvStartID, int playerInvY)
	{
		this.inventory = te;
		this.playerInvY = playerInvY;
		this.playerInvStartID = playerInvStartID;
	}
	
	public IInventory getInv()
	{
		return inventory;
	}
	
	public void addPlayerInventorySlots(IInventory playerInv)
	{
		// Player Inventory, Slot 9-35
		for (int y = 0; y < 3; ++y)
		{
			for (int x = 0; x < 9; ++x)
			{
				this.addSlotToContainer(new ScienceSlot(playerInv, x + y * 9 + 9, 8 + x * 18, playerInvY + y * 18));
			}
		}
		
		// Player Inventory, Slot 0-8
		for (int x = 0; x < 9; ++x)
		{
			this.addSlotToContainer(new ScienceSlot(playerInv, x, 8 + x * 18, playerInvY + 58));
		}
	}
	
	@Override
	public boolean canInteractWith(EntityPlayer playerIn)
	{
		return inventory.isUseableByPlayer(playerIn);
	}
	
	@Override
	public ItemStack transferStackInSlot(EntityPlayer playerIn, int fromSlot)
	{
		ItemStack previous = null;
		ScienceSlot slot = (ScienceSlot) this.inventorySlots.get(fromSlot);
		
		if (slot != null && slot.getHasStack())
		{
			ItemStack current = slot.getStack();
			previous = current.copy();
			
			if (fromSlot > playerInvStartID)
			{
				// From Player Inventory to TE Inventory
				if (!this.mergeItemStack(current, 0, playerInvStartID, false)) return null;
			}
			else
			{
				// From TE Inventory to Player Inventory
				if (!this.mergeItemStack(current, playerInvStartID, this.getInventory().size(), true)) return null;
			}
			
			if (current.stackSize == 0)
				slot.putStack((ItemStack) null);
			else
				slot.onSlotChanged();
			
			if (current.stackSize == previous.stackSize) return null;
			
			slot.onPickupFromSlot(playerIn, current);
		}
		return previous;
	}
	
	@Override
	protected boolean mergeItemStack(ItemStack stack, int startIndex, int endIndex, boolean useEndIndex)
	{
		boolean success = false;
		int index = startIndex;
		
		if (useEndIndex)
			index = endIndex - 1;
		
		ScienceSlot slot;
		ItemStack stackinslot;
		
		if (stack.isStackable())
		{
			// For each inventory slot in the target slot
			while (stack.stackSize > 0 && (!useEndIndex && index < endIndex || useEndIndex && index >= startIndex))
			{
				slot = (ScienceSlot) this.inventorySlots.get(index);
				stackinslot = slot.getStack();
				
				// If the two stacks can be merged
				if (stackinslot != null && stackinslot.isItemEqual(stack) && ItemStack.areItemStackTagsEqual(stack, stackinslot))
				{
					int l = stackinslot.stackSize + stack.stackSize;
					int maxsize = slot.getItemStackLimit(stack);
					
					if (l <= maxsize)
					{
						// The final amount does not exceed the stack size limit.
						stack.stackSize = 0;
						stackinslot.stackSize = l;
						slot.onSlotChanged();
						success = true;
						
						// End merge, break loop.
						break;
					}
					else if (stackinslot.stackSize < maxsize)
					{
						// The final amount exceeds max stack size, keep overflow for next cycles.
						stack.stackSize -= maxsize - stackinslot.stackSize;
						stackinslot.stackSize = maxsize;
						slot.onSlotChanged();
						success = true;
					}
				}
				
				// Index update
				if (useEndIndex)
					index --;
				else
					index ++;
			}
		}
		
		if (stack.stackSize > 0)
		{
			if (useEndIndex)
				index = endIndex - 1;
			else
				index = startIndex;
			
			while (!useEndIndex && index < endIndex || useEndIndex && index >= startIndex && stack.stackSize > 0)
			{
				slot = (ScienceSlot) this.inventorySlots.get(index);
				stackinslot = slot.getStack();
				
				// Forge: Make sure to respect isItemValid in the slot.
				if (stackinslot == null && slot.isItemValid(stack))
				{
					if (stack.stackSize < slot.getItemStackLimit(stack))
					{
						// All of the input stack can be inserted.
						slot.putStack(stack.copy());
						stack.stackSize = 0;
						slot.onSlotChanged();
						success = true;
						
						// Break the loop to stop trying.
						break;
					}
					else
					{
						ItemStack newstack = stack.copy();
						newstack.stackSize = slot.getItemStackLimit(stack);
						slot.putStack(newstack);
						stack.stackSize -= slot.getItemStackLimit(stack);
						slot.onSlotChanged();
						success = true;
					}
				}
				
				if (useEndIndex)
					--index;
				else
					++index;
			}
		}
		
		return success;
	}
}
