package com.JasonILTG.ScienceMod.tileentity;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

public abstract class TEMachine extends TEScience implements IInventory
{
	// A wrapper class for all the machines in the mod.
	
	private ItemStack[] inventory;
	
	@Override
	public int getSizeInventory()
	{
		return inventory.length;
	}
	
	@Override
	public ItemStack getStackInSlot(int index)
	{
		return inventory[index];
	}
	
	@Override
	public ItemStack decrStackSize(int index, int amount)
	{
		ItemStack stack = inventory[index];
		
		if (stack != null) {
			if (stack.stackSize <= amount) {
				setInventorySlotContents(index, null);
			}
			else {
				stack = stack.splitStack(amount);
				if (stack.stackSize == 0) {
					setInventorySlotContents(index, null);
				}
			}
		}
		
		return stack;
	}
}
