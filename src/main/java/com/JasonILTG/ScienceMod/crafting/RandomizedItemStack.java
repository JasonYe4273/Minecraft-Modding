package com.JasonILTG.ScienceMod.crafting;

import java.util.Random;

import net.minecraft.item.ItemStack;

public class RandomizedItemStack
{
	private static Random generator = new Random();
	
	ItemStack baseItemStack;
	double weight;
	
	public RandomizedItemStack(ItemStack stackToUse, double weight)
	{
		baseItemStack = stackToUse;
		if (weight < 0) weight = 0;
		this.weight = weight;
	}
	
}
