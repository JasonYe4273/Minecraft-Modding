package com.JasonILTG.ScienceMod.tileentity;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import com.JasonILTG.ScienceMod.util.NBTHelper;

/**
 * A wrapper class for all machines that have an inventory and a progress bar in the mod.
 */
public abstract class TEMachine extends TEScience implements IInventory
{
	// A wrapper class for all the machines in the mod.
	
	protected ItemStack[] inventory;
	
	protected int maxProgress;
	protected int currentProgress;
	
	public TEMachine()
	{
		super();
		currentProgress = 0;
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
	
	public void setInventorySlotContents(int index, ItemStack items)
	{	
		
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
	
	@Override
	public void readFromNBT(NBTTagCompound tag)
	{
		super.readFromNBT(tag);
		NBTHelper.readInventoryFromNBT(tag, inventory);
	}
	
	@Override
	public void writeToNBT(NBTTagCompound tag)
	{
		super.writeToNBT(tag);
		NBTHelper.writeInventoryToTag(inventory, tag);
	}
}
