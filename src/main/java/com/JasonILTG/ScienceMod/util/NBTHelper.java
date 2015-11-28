package com.JasonILTG.ScienceMod.util;

import java.util.ArrayList;

import com.JasonILTG.ScienceMod.reference.NBTKeys.Inventory;
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
		NBTTagList tagList = tag.getTagList(Inventory.ITEMS, NBTTypes.COMPOUND);
		
		// For each tag
		for (int i = 0; i < tagList.tagCount(); i++)
		{
			// Get the ItemStack and index
			NBTTagCompound tagCompound = tagList.getCompoundTagAt(i);
			byte slotIndex = tagCompound.getByte(Inventory.SLOT);
			
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
				tagCompound.setByte(Inventory.SLOT, (byte) currentIndex);
				inventory[currentIndex].writeToNBT(tagCompound);
				tagList.appendTag(tagCompound);
			}
		}
		
		// Stores the tag list
		tag.setTag(Inventory.ITEMS, tagList);
	}
	
	public static void readTanksFromNBT(FluidTank[] tanks, NBTTagCompound tag)
	{
		// A list of tags that contains all the items in the inventory
		NBTTagList tagList = tag.getTagList(Inventory.TANKS, NBTTypes.COMPOUND);
		
		// For each tag
		for (int i = 0; i < tagList.tagCount(); i++)
		{
			NBTTagCompound tagCompound = tagList.getCompoundTagAt(i);
			byte slotIndex = tagCompound.getByte(Inventory.SLOT);
			
			// Load the tank
			if (slotIndex >= 0 && slotIndex < tanks.length) {
				tanks[slotIndex].readFromNBT(tagCompound.getCompoundTag(Inventory.TANKS));
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
		tag.setTag(Inventory.TANKS, tagList);
	}
	
	/*
	 * Method to combine two tagLists together, adding the values of tags that have matching compKeys values
	 */
	public static NBTTagList combineTagLists(NBTTagList tagList1, NBTTagList tagList2, String stringCompKey, String intCompKey, String intAddKey, String doubleAddKey, String fracAddKey)
	{
		NBTTagList newTagList = new NBTTagList();
		
		ArrayList<String> stringsIn1 = new ArrayList<String>();
		for( int i = 0; i < tagList1.tagCount(); i++ )
		{
			NBTTagCompound tag1 = (NBTTagCompound) tagList1.getCompoundTagAt(i).copy();
			newTagList.appendTag(tag1);
			stringsIn1.add(tag1.getString(stringCompKey));
		}
		
		for( int i = 0; i < tagList2.tagCount(); i++ )
		{
			NBTTagCompound tag2 = (NBTTagCompound) tagList2.getCompoundTagAt(i).copy();
			int indexIn1 = stringsIn1.indexOf(tag2.getString(stringCompKey));
			if( indexIn1 > -1 )
			{
				NBTTagCompound tag1 = newTagList.getCompoundTagAt(indexIn1);
				if( intCompKey == null || (intCompKey != null && tag1.getInteger(intCompKey) == tag2.getInteger(intCompKey)) )
				{
					//If the same tag exists in tagList1, combine the tags
					
					//Null check
					if(fracAddKey != null)
					{
						newTagList.getCompoundTagAt(indexIn1).setIntArray(fracAddKey, NBTHelper.addFrac(tag1.getIntArray(fracAddKey), tag2.getIntArray(fracAddKey)));
					}
					
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
				newTagList.appendTag(tag2);
			}
		}
		
		return newTagList;
	}
	
	public static void checkFracZero(ItemStack stack, String[] tagListKeys, String fracKey)
	{
		for( String key : tagListKeys )
		{
			NBTTagList tagList = stack.getTagCompound().getTagList(key, NBTTypes.COMPOUND);
			for( int i = tagList.tagCount() - 1; i >= 0; i-- )
			{
				if( tagList.getCompoundTagAt(i).getIntArray(fracKey)[0] == 0 ) tagList.removeTag(i);
			}
		}
	}
	
	public static double parseFrac(int[] numerDenom)
	{
		return (double) numerDenom[0] / (double) numerDenom[1];
	}
	
	public static int[] parseFrac(double value)
	{
		double tolerance = 0.0001;
		int denom = 1;
		while(true)
		{
			double numer = value * denom;
			if( Math.abs(Math.floor(numer) - numer) < tolerance )
			{
				return new int[]{ (int) numer, denom };
			}
			denom++;
		}
	}
	
	public static int[] addFrac(int[] frac1, int[] frac2)
	{
		int numer = frac1[0] * frac2[1] + frac1[1] * frac2[0];
		int denom = frac1[1] * frac2[1];
		int common = gcd(numer, denom);
		return new int[]{ numer / common, denom / common };
	}
	
	public static int[] multFrac(int[] frac1, int[] frac2)
	{
		int numer = frac1[0] * frac2[0];
		int denom = frac1[1] * frac2[1];
		int common = gcd(numer, denom);
		return new int[]{ numer / common, denom / common };
	}
	
	private static int gcd(int num1, int num2)
	{
		if(num1 == num2) return num1;
		if(num1 == 1) return 1;
		if(num2 == 1) return 1;
		if(num1 > num2) return gcd(num1-num2, num2);
		return gcd(num1, num2-num1);
	}
}
