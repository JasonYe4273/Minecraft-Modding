package com.JasonILTG.ScienceMod.util;

import java.util.ArrayList;

import com.JasonILTG.ScienceMod.reference.NBTKeys;
import com.JasonILTG.ScienceMod.reference.NBTTypes;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.fluids.FluidTank;

public class NBTHelper
{
	/**
	 * Experimental method for reading an array of ItemStack from a compound tag and putting them into an inventory.
	 * 
	 * @param tag the tag to read from
	 * @param inventory the inventory to place items into
	 */
	public static void readInventoryFromNBT(NBTTagCompound tag, ItemStack[] inventory)
	{
		// A list of tags that contains all the items in the inventory
		NBTTagList tagList = tag.getTagList(NBTKeys.ITEMS, NBTTypes.COMPOUND);
		
		// For each tag
		for (int i = 0; i < tagList.tagCount(); i++)
		{
			// Get the ItemStack and index
			NBTTagCompound tagCompound = tagList.getCompoundTagAt(i);
			byte slotIndex = tagCompound.getByte(NBTKeys.SLOT);
			
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
	public static void writeInventoryToTag(ItemStack[] inventory, NBTTagCompound tag)
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
				tagCompound.setByte(NBTKeys.SLOT, (byte) currentIndex);
				inventory[currentIndex].writeToNBT(tagCompound);
				tagList.appendTag(tagCompound);
			}
		}
		
		// Stores the tag list
		tag.setTag(NBTKeys.ITEMS, tagList);
	}
	
	public static void readTanksFromNBT(FluidTank[] tanks, NBTTagCompound tag)
	{
		// A list of tags that contains all the items in the inventory
		NBTTagList tagList = tag.getTagList(NBTKeys.TANKS, NBTTypes.COMPOUND);
		
		// For each tag
		for (int i = 0; i < tagList.tagCount(); i++)
		{
			NBTTagCompound tagCompound = tagList.getCompoundTagAt(i);
			byte slotIndex = tagCompound.getByte(NBTKeys.SLOT);
			
			// Load the tank
			if (slotIndex >= 0 && slotIndex < tanks.length) {
				tanks[slotIndex].readFromNBT(tagCompound.getCompoundTag(NBTKeys.TANKS));
			}
		}
	}
	
	public static void writeTanksToNBT(FluidTank[] tanks, NBTTagCompound tag)
	{
		// Generate a new tag list to store tank tags
		NBTTagList tagList = new NBTTagList();
		
		// For each tank
		for (int currentIndex = 0; currentIndex < tanks.length; currentIndex++)
		{
			// If not null
			if (tanks[currentIndex] != null)
			{
				// Add a tag to the tag list
				NBTTagCompound tagCompound = new NBTTagCompound();
				tagCompound.setByte("Slot", (byte) currentIndex);
				tanks[currentIndex].writeToNBT(tagCompound);
				tagList.appendTag(tagCompound);
			}
		}
		
		// Add the tag list to the tag
		tag.setTag(NBTKeys.TANKS, tagList);
	}
	
	/*
	 * Method to combine two tagLists together, adding the values of tags that have matching compKeys values
	 */
	public static NBTTagList combineTagLists(NBTTagList tagList1, NBTTagList tagList2, String stringCompKey, String intCompKey, String intAddKey, String doubleAddKey)
	{
		NBTTagList newTagList = new NBTTagList();
		
		ArrayList<String> stringsIn1 = new ArrayList<String>();
		for( int i = 0; i < tagList1.tagCount(); i++ )
		{
			NBTTagCompound tag1 = tagList1.getCompoundTagAt(i);
			newTagList.appendTag(tag1);
			stringsIn1.add(tag1.getString(stringCompKey));
		}
		
		for( int i = 0; i < tagList2.tagCount(); i++ )
		{
			NBTTagCompound tag2 = tagList2.getCompoundTagAt(i);
			int indexIn1 = stringsIn1.indexOf(tag2.getString(stringCompKey));
			if( indexIn1 > -1 )
			{
				NBTTagCompound tag1 = tagList1.getCompoundTagAt(indexIn1);
				if( intCompKey == null || (intCompKey != null && tag1.getInteger(intCompKey) == tag2.getInteger(intCompKey)) )
				{
					//If the same tag exists in tagList1, combine the tags
					
					//Null check
					if(doubleAddKey != null )
					{
						newTagList.getCompoundTagAt(indexIn1).setDouble(doubleAddKey, tag1.getDouble(doubleAddKey) + tag2.getDouble(doubleAddKey));
					}
					
					//Null check
					if(intAddKey != null )
					{
						newTagList.getCompoundTagAt(indexIn1).setInteger(intAddKey, tag1.getInteger(intAddKey) + tag2.getInteger(intAddKey));
					}
				}
				
			}
			else
			{
				//Otherwise just add a new tag
				newTagList.appendTag(tagList2.getCompoundTagAt(i));
			}
		}
		
		return newTagList;
	}
	
	/*
	 * A method to check double values in a tag list and adjust them to fractions of integers
	 */
	public static void checkDoubleFrac(ItemStack stack, String[] tagListKeys, String doubleKey)
	{
		for( String key : tagListKeys )
		{
			NBTTagList tagList = stack.getTagCompound().getTagList(key, NBTTypes.COMPOUND);
			//Null check
			if( tagList == null ) return;
			
			double tolerance = 0.000001;
			for( int i = 0; i < tagList.tagCount(); i++ )
			{
				double current = tagList.getCompoundTagAt(i).getDouble(doubleKey);
				if( current < tolerance ) tagList.removeTag(i);
				else tagList.getCompoundTagAt(i).setDouble(doubleKey, checkDoubleFrac(current));
			}
		}
	}
	
	/*
	 * A method to turn a double value into a fraction of integers up to maxIntInRatio
	 */
	private static double checkDoubleFrac(double current)
	{
		double tolerance = 0.0001;
		double maxDenom = 100.0;
		for( double denom = 1.0; denom < maxDenom; denom++ )
		{
			double numer = current * denom;
			if( Math.abs(Math.floor(numer) - numer) > 0.0 ) LogHelper.info(current);
			if( Math.abs(Math.floor(numer) - numer) < tolerance )
			{
				LogHelper.info(numer / denom);
				return numer / denom;
			}
		}
		return current;
	}
}
