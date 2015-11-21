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
	public static void readInventoryFromNBT(NBTTagCompound tag, ItemStack[] inventory)
	{
		// A list of tags that contains all the items in the inventory
		NBTTagList tagList = tag.getTagList(NBTKeys.ITEMS, 10);
		
		// For each tag
		for (int i = 0; i < tagList.tagCount(); i++)
		{
			// Get the ItenStack and index
			NBTTagCompound tagCompound = tagList.getCompoundTagAt(i);
			byte slotIndex = tagCompound.getByte("Slot");
			
			// Load the ItemStack into the inventory
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
	public static void addInventoryToTag(ItemStack[] inventory, NBTTagCompound tag)
	{
		// Generate a new tag list to store item tags
		NBTTagList tagList = new NBTTagList();
		
		// For each inventory ItemStack
		for (int currentIndex = 0; currentIndex < inventory.length; currentIndex++)
		{
			// If it is not null
			if (inventory[currentIndex] != null)
			{
				// Add the ItemStack to the tag list
				NBTTagCompound tagCompound = new NBTTagCompound();
				tagCompound.setByte("Slot", (byte) currentIndex);
				inventory[currentIndex].writeToNBT(tagCompound);
				tagList.appendTag(tagCompound);
			}
		}
		
		// Stores the tag list
		tag.setTag(NBTKeys.ITEMS, tagList);
	}
}
