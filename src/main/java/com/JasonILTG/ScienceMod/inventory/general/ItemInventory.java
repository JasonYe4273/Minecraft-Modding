package com.JasonILTG.ScienceMod.inventory.general;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.IChatComponent;

public abstract class ItemInventory implements IInventory
{
	protected ItemStack[][] allInventories;
	
	protected String customName;
	
	public ItemInventory(String name)
	{
		customName = name;
	}
	
	public int getNextNonemptyIndex()
	{
		int currentIndex = 0;
		for (ItemStack[] inv : allInventories) {
			for (ItemStack stack : inv)
			{
				if (stack != null) return currentIndex;
				currentIndex ++;
			}
		}
		return -1;
	}
	
	@Override
	public int getSizeInventory()
	{
		int inventorySize = 0;
		for (ItemStack[] inv : allInventories)
			inventorySize += inv.length;
		
		return inventorySize;
	}
	
	@Override
	public ItemStack getStackInSlot(int index)
	{
		for (ItemStack[] inventory : allInventories)
		{
			if (index >= inventory.length) {
				index -= inventory.length;
			}
			else {
				return inventory[index];
			}
		}
		
		// Default return.
		return null;
	}
	
	@Override
	public ItemStack decrStackSize(int index, int count)
	{
		ItemStack stack = getStackInSlot(index);
		
		if (stack != null)
		{
			if (count >= stack.stackSize) {
				// The action will deplete the stack.
				setInventorySlotContents(index, null);
			}
			else {
				// The action should not deplete the stack
				stack = stack.splitStack(count);
			}
		}
		
		return stack;
	}
	
	@Override
	public ItemStack getStackInSlotOnClosing(int index)
	{
		ItemStack stack = getStackInSlot(index);
		
		if (stack != null)
		{
			setInventorySlotContents(index, null);
			return stack;
		}
		
		return null;
	}
	
	@Override
	public void setInventorySlotContents(int index, ItemStack stack)
	{
		for (ItemStack[] inventory : allInventories)
		{
			if (index >= inventory.length) {
				index -= inventory.length;
			}
			else {
				inventory[index] = stack;
				return;
			}
		}
	}
	
	public int getInvIndexBySlotIndex(int index)
	{
		for (int i = 0; i < allInventories.length; i ++)
		{
			if (index >= allInventories[i].length) {
				index -= allInventories[i].length;
			}
			else {
				return i;
			}
		}
		
		return allInventories.length;
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
	public String getName()
	{
		return this.hasCustomName() ? this.customName : "container.inventory_tile_entity";
	}
	
	@Override
	public boolean hasCustomName()
	{
		return this.customName != null && !this.customName.equals("");
	}
	
	@Override
	public IChatComponent getDisplayName()
	{
		return this.hasCustomName() ? new ChatComponentText(this.getName()) : new ChatComponentTranslation(this.getName());
	}
	
	@Override
	public boolean isUseableByPlayer(EntityPlayer player)
	{
		// Always usable by player
		return true;
	}
	
	public abstract void readFromNBT(NBTTagCompound tag);
	
	public abstract void writeToNBT(NBTTagCompound tag);
}
