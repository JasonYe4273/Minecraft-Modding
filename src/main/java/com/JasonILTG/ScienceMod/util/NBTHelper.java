package com.JasonILTG.ScienceMod.util;

import java.util.ArrayList;

import com.JasonILTG.ScienceMod.init.ScienceModItems;
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
	
	public static void checkPrecipitates(ItemStack stack)
	{
		ItemStack s = stack.copy();
		
		//Check that it is a solution
		if(stack.isItemEqual(new ItemStack(ScienceModItems.solution))) return;
		
		//Split it into a cation or anion
		NBTTagList ionList = s.getTagCompound().getTagList(NBTKeys.IONS, NBTTypes.COMPOUND);
		ArrayList<String> cations = new ArrayList<String>();
		ArrayList<Integer> cationIndices = new ArrayList<Integer>();
		ArrayList<String> anions = new ArrayList<String>();
		ArrayList<Integer> anionIndices = new ArrayList<Integer>();
		for( int i = 0; i < ionList.tagCount(); i++ )
		{
			NBTTagCompound tag = ionList.getCompoundTagAt(i);
			String ion = tag.getString(NBTKeys.ION);
			if(tag.getInteger(NBTKeys.CHARGE) > 0)
			{
				//These cations are always soluble
				if(!(ion.equals("NH4") || ion.equals("Li") || ion.equals("Na") || ion.equals("K") || ion.equals("Cs") || ion.equals("Rb")))
				{
					cations.add(ion);
					cationIndices.add(i);
				}
			}
			else
			{
				//These anions are always soluble
				if(!(ion.equals("NO3")))
				{
					anions.add(ion);
					anionIndices.add(i);
				}
			}
		}
		
		ArrayList<String> cationsRemoved = new ArrayList<String>();
		ArrayList<Double> cationMoles = new ArrayList<Double>();
		ArrayList<String> anionsRemoved = new ArrayList<String>();
		ArrayList<Double> anionMoles = new ArrayList<Double>();
		ArrayList<String> precipitates = new ArrayList<String>();
		ArrayList<Double> precipitateMoles = new ArrayList<Double>();
		ArrayList<String> precipitateStates = new ArrayList<String>();
		for( int i = 0; i < cations.size(); i++ )
		{
			if(cations.get(i).equals("H"))
			{
				int index = anions.indexOf("OH");
				if(index > 0)
				{
					cationsRemoved.add("H");
					anionsRemoved.add("OH");
					precipitates.add("H2O");
					precipitateStates.add("l");
					double baseMols = Math.min(ionList.getCompoundTagAt(i).getDouble(NBTKeys.MOLS), ionList.getCompoundTagAt(index).getDouble(NBTKeys.MOLS));
					cationMoles.add(baseMols);
					anionMoles.add(baseMols);
					precipitateMoles.add(baseMols);
				}
			}
		}
	}
	
	public enum PrecipitateRecipe
	{
		Water("H", "OH", "H2O", 1, 1, 1, "l");
		
		private PrecipitateRecipe(String cation, String anion, String result, int cationMol, int anionMol, int resultMol, String resultState)
		{
			
		}
	}
}
