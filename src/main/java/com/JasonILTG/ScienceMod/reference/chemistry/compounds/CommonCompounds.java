package com.JasonILTG.ScienceMod.reference.chemistry.compounds;

import com.JasonILTG.ScienceMod.item.chemistry.CompoundItem;

import net.minecraft.item.ItemStack;

/**
 * Reference class for common compounds.
 * 
 * @author JasonILTG and syy1125
 */
public class CommonCompounds
{
	public static ItemStack water;
	public static ItemStack carbonDioxide;
	
	/**
	 * Initializes the common compounds.
	 */
	public static void init()
	{
		water = CompoundItem.getCompoundStack("H2O", 1);
		carbonDioxide = CompoundItem.getCompoundStack("CO2", 1);
	}
	
	/**
	 * Returns an <code>ItemStack</code> of water with the given size.
	 * 
	 * @param stackSize The size
	 * @return The <code>ItemStack</code>
	 */
	public static ItemStack getWater(int stackSize)
	{
		return new ItemStack(water.getItem(), stackSize, water.getMetadata());
	}
	
	/**
	 * Returns an <code>ItemStack</code> of carbon dioxide with the given size.
	 * 
	 * @param stackSize The size
	 * @return The <code>ItemStack</code>
	 */
	public static ItemStack getCO2(int stackSize)
	{
		return new ItemStack(carbonDioxide.getItem(), stackSize, carbonDioxide.getMetadata());
	}
}
