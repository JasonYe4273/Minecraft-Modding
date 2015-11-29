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
	protected String customName;
	protected ItemStack containerItem;
	
	public ItemInventory(String name)
	{
		customName = name;
	}
	
	/**
	 * Finds the next index that has an item. If none found, returns -1.
	 * 
	 * @return
	 */
	public abstract int getNextNonemptyIndex();
	
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
	
	public String getCustomName()
	{
		return customName;
	}
	
	public void setCustomName(String name)
	{
		customName = name;
	}
	
	public ItemStack getContainerItem()
	{
		return containerItem;
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
	
	@Override
	public void markDirty()
	{
		for (int i = 0; i < getSizeInventory(); ++i)
		{
			if (getStackInSlot(i) != null && getStackInSlot(i).stackSize == 0) {
				setInventorySlotContents(i, null);
			}
		}
		
		// This line here does the work:
		writeToNBT(containerItem.getTagCompound());
	}
	
	public abstract void readFromNBT(NBTTagCompound tag);
	
	public abstract void writeToNBT(NBTTagCompound tag);
}
