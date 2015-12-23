package com.JasonILTG.ScienceMod.inventory.tool;

import com.JasonILTG.ScienceMod.inventory.general.ItemInventory;
import com.JasonILTG.ScienceMod.item.general.ItemJarred;
import com.JasonILTG.ScienceMod.reference.NBTKeys;
import com.JasonILTG.ScienceMod.reference.NBTTypes;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

/**
 * Inventory class for jar launchers.
 * 
 * @author JasonILTG and syy1125
 */
public class LauncherInventory extends ItemInventory
{
	/** The inventory ItemStack array */
	private ItemStack[] inventory;
	
	public static final String NAME = "Jar Launcher";
	private static final int DEFAULT_INV_SIZE = 8;
	
	/**
	 * Constructor.
	 * 
	 * @param launcher The jar launcher ItemStack
	 */
	public LauncherInventory(ItemStack launcher)
	{
		super(NAME);
		containerItem = launcher;
		inventory = new ItemStack[DEFAULT_INV_SIZE];
		
		if (!launcher.hasTagCompound()) launcher.setTagCompound(new NBTTagCompound());
		
		readFromNBT(launcher.getTagCompound());
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
	public ItemStack getStackInSlotOnClosing(int index)
	{
		// Does not throw out anything.
		return null;
	}
	
	@Override
	public void setInventorySlotContents(int index, ItemStack stack)
	{
		if (index < 0 || index >= inventory.length) return;
		inventory[index] = stack;
	}
	
	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack)
	{
		return stack.getItem() instanceof ItemJarred;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound tag)
	{
		NBTTagList tagList = tag.getTagList(NBTKeys.Inventory.INVENTORY, NBTTypes.COMPOUND);
		
		for (int i = 0; i < tagList.tagCount(); i ++)
		{
			NBTTagCompound tagCompound = tagList.getCompoundTagAt(i);
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
			
			NBTTagCompound tagCompound = new NBTTagCompound();
			tagCompound.setByte(NBTKeys.Inventory.SLOT, (byte) index);
			tagCompound = inventory[index].writeToNBT(tagCompound);
			tagList.appendTag(tagCompound);
		}
		
		tag.setTag(NBTKeys.Inventory.INVENTORY, tagList);
	}
	
	@Override
	public int getNextNonemptyIndex()
	{
		for (int i = 0; i < inventory.length; i ++) {
			if (inventory[i] != null && inventory[i].stackSize > 0)
			{
				return i;
			}
		}
		
		return -1;
	}
	
}
