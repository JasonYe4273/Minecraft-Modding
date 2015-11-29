package com.JasonILTG.ScienceMod.inventory.tool;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

import com.JasonILTG.ScienceMod.inventory.general.ItemInventory;
import com.JasonILTG.ScienceMod.item.general.ItemJarred;
import com.JasonILTG.ScienceMod.reference.NBTKeys;
import com.JasonILTG.ScienceMod.reference.NBTTypes;
import com.JasonILTG.ScienceMod.reference.Reference;
import com.JasonILTG.ScienceMod.util.LogHelper;

public class LauncherInventory extends ItemInventory
{
	private ItemStack[] inventory;
	
	public static final String NAME = "Jar Launcher";
	
	public LauncherInventory(ItemStack launcher)
	{
		super(NAME);
		if (!launcher.hasTagCompound()) launcher.setTagCompound(new NBTTagCompound());
		inventory = new ItemStack[8];
		containerItem = launcher;
		readFromNBT(launcher.getTagCompound());
	}
	
	@Override
	public ItemStack getStackInSlotOnClosing(int index)
	{
		// Does not throw out anything.
		return null;
	}
	
	@Override
	public int getInventoryStackLimit()
	{
		return Reference.DEFUALT_STACK_LIMIT;
	}
	
	@Override
	public void markDirty()
	{
		return;
	}
	
	@Override
	public void openInventory(EntityPlayer player)
	{
		// Does nothing?
	}
	
	@Override
	public void closeInventory(EntityPlayer player)
	{
		// Does nothing?
	}
	
	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack)
	{
		return stack.getItem() instanceof ItemJarred;
	}
	
	@Override
	public int getField(int id)
	{
		return 0;
	}
	
	@Override
	public void setField(int id, int value)
	{
		// Does nothing?
	}
	
	@Override
	public int getFieldCount()
	{
		return 0;
	}
	
	@Override
	public void clear()
	{
		for (int i = 0; i < this.getSizeInventory(); i ++)
			this.setInventorySlotContents(i, null);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound tag)
	{
		LogHelper.info("Loading launcher inventory.");
		NBTTagList tagList = tag.getTagList(NBTKeys.Inventory.INVENTORY, NBTTypes.COMPOUND);
		LogHelper.info("Loaded " + tagList.tagCount() + " tags on inventory.");
		
		for (int i = 0; i < tagList.tagCount(); i ++)
		{
			NBTTagCompound tagCompound = tagList.getCompoundTagAt(i);
			LogHelper.info("Loading stack at " + tagCompound.getByte(NBTKeys.Inventory.SLOT));
			inventory[tagCompound.getByte(NBTKeys.Inventory.SLOT)] = ItemStack.loadItemStackFromNBT(tagCompound);
		}
		
	}
	
	@Override
	public void writeToNBT(NBTTagCompound tag)
	{
		NBTTagList tagList = new NBTTagList();
		
		for (int index = 0; index < inventory.length; index ++)
		{
			if (inventory[index] == null) continue;
			
			LogHelper.info("Launcher inventory at index " + index + " is not null. Saving...");
			NBTTagCompound tagCompound = new NBTTagCompound();
			tagCompound.setByte(NBTKeys.Inventory.SLOT, (byte) index);
			tagCompound = inventory[index].writeToNBT(tagCompound);
			tagList.appendTag(tagCompound);
		}
		
		tag.setTag(NBTKeys.Inventory.INVENTORY, tagList);
	}
	
	@Override
	public int getSizeInventory()
	{
		return inventory.length;
	}
	
	@Override
	public ItemStack getStackInSlot(int index)
	{
		if (index < 0 || index >= inventory.length) return null;
		return inventory[index];
	}
	
	@Override
	public void setInventorySlotContents(int index, ItemStack stack)
	{
		if (index < 0 || index >= inventory.length) return;
		inventory[index] = stack;
	}
	
	@Override
	public int getNextNonemptyIndex()
	{
		for (int i = 0; i < inventory.length; i ++)
		{
			if (inventory[i] != null && inventory[i].stackSize > 0)
			{
				return i;
			}
		}
		
		return -1;
	}
	
}
