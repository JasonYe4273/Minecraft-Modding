package com.JasonILTG.ScienceMod.crafting;

import java.util.Random;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class RandomizedItemStack
{
	private static Random generator = new Random();
	
	private final ItemStack baseItemStack;
	private double weight;
	
	public RandomizedItemStack(ItemStack stackToUse, double weight)
	{
		baseItemStack = stackToUse;
		if (weight < 0) weight = 0;
		this.weight = weight;
	}
	
	public double getWeight()
	{
		return weight;
	}
	
	public void setWeight(double newWeight)
	{
		if (newWeight < 0) newWeight = 0;
		weight = newWeight;
	}
	
	/**
	 * Gets the base for the object. Useful for
	 * 
	 * @return
	 */
	public ItemStack copyBaseItemStack()
	{
		return baseItemStack;
	}
	
	public ItemStack generateOutput()
	{
		// The integer part of weight is guarenteed.
		int outputCount = (int) weight;
		// The decimal part of weight is the probability that there will be one more output.
		if (generator.nextDouble() < weight - outputCount) outputCount++;
		
		Item outputItem = baseItemStack.getItem();
		
		if (outputItem.getHasSubtypes()) {
			// There are subtypes
			return new ItemStack(outputItem, outputCount, baseItemStack.getMetadata());
		}
		else {
			// There are no subtypes
			return new ItemStack(outputItem, outputCount);
		}
	}
}
