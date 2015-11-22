package com.JasonILTG.ScienceMod.tileentity;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

public abstract class TEInventory extends TEScience implements IInventory
{
	//A wrapper class for all of the tile entities with inventory
	
	protected String customName;
	protected ItemStack[] inventory;
	protected int inventorySize;
	
	public TEInventory(String name, int inventorySize)
	{
		customName = name;
		inventory = new ItemStack[inventorySize];
		this.inventorySize = inventorySize;
	}
	
	public String getCustomName()
	{
		return customName;
	}
	
	public void setCustomName(String name)
	{
		customName = name;
	}
	
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
		ItemStack stack = getStackInSlot(index);
		
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
