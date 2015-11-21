package com.JasonILTG.ScienceMod.util;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

import com.JasonILTG.ScienceMod.references.NBTKeys;

public class NBTHelper
{
	/**
	 * Experimental method for reading an array of ItenStack from a compound tag and putting them into an inventory.
	 * 
	 * @param tag the tag to read from
	 * @param inventory the inventory to place items into
	 */
	public static void readItemStackFromNBT(NBTTagCompound tag, ItemStack[] inventory)
	{
		NBTTagList tagList = tag.getTagList(NBTKeys.ITEMS, 10);
		
		for (int i = 0; i < tagList.tagCount(); i++)
		{
			NBTTagCompound tagCompound = tagList.getCompoundTagAt(i);
			byte slotIndex = tagCompound.getByte("Slot");
			if (slotIndex >= 0 && slotIndex < inventory.length)
			{
				inventory[slotIndex] = ItemStack.loadItemStackFromNBT(tagCompound);
			}
		}
	}
	
	/**
	 * Experimental method for saving an inventory into an NBT tag.
	 * 
	 * @param inventory the inventory of the current tile entity
	 * @param tag the tag to write the inventory into
	 */
	public static void addInventoryToCompoundTag(ItemStack[] inventory, NBTTagCompound tag)
	{
		NBTTagList tagList = new NBTTagList();
		
		for (int currentIndex = 0; currentIndex < inventory.length; currentIndex++)
		{
			if (inventory[currentIndex] != null)
			{
				NBTTagCompound tagCompound = new NBTTagCompound();
				tagCompound.setByte("Slot", (byte) currentIndex);
				inventory[currentIndex].writeToNBT(tagCompound);
				tagList.appendTag(tagCompound);
			}
		}
		
		tag.setTag(NBTKeys.ITEMS, tagList);
	}
}
