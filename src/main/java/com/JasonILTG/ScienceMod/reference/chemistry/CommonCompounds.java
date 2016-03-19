package com.JasonILTG.ScienceMod.reference.chemistry;

import com.JasonILTG.ScienceMod.item.chemistry.CompoundItem;

import net.minecraft.item.ItemStack;

/**
 * 
 * @author JasonILTG and syy1125
 */
public class CommonCompounds
{
	public static ItemStack water;
	public static ItemStack carbonDioxide;
	
	public static void init()
	{
		water = CompoundItem.getCompoundStack("H2O", 1);
		carbonDioxide = CompoundItem.getCompoundStack("CO2", 1);
	}
	
	public static ItemStack getWater(int stackSize)
	{
		return new ItemStack(water.getItem(), stackSize, water.getMetadata());
	}
	
	public static ItemStack getCO2(int stackSize)
	{
		return new ItemStack(carbonDioxide.getItem(), stackSize, carbonDioxide.getMetadata());
	}
}
