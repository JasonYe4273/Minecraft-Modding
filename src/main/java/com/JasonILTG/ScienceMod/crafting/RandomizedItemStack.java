package com.JasonILTG.ScienceMod.crafting;

import java.util.Random;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

/**
 * A randomized <code>ItemStack</code> used for randomized outputs.
 * 
 * @author JasonILTG and syy1125
 */
public class RandomizedItemStack
{
	/** The random number generator */
	private static Random generator = new Random();
	
	/** The base <code>ItemStack</code> */
	private final ItemStack baseItemStack;
	/** The chance this <code>RandomizedItemStack</code> has of being selected */
	private double weight;
	
	/**
	 * Constructor.
	 * 
	 * @param stackToUse The <code>ItemStack</code>
	 * @param weight The weight
	 */
	public RandomizedItemStack(ItemStack stackToUse, double weight)
	{
		baseItemStack = stackToUse;
		if (weight < 0) weight = 0;
		this.weight = weight;
	}
	
	/**
	 * @return The weight
	 */
	public double getWeight()
	{
		return weight;
	}
	
	/**
	 * Sets the weight.
	 * 
	 * @param newWeight The weight
	 */
	public void setWeight(double newWeight)
	{
		if (newWeight < 0) newWeight = 0;
		weight = newWeight;
	}
	
	/**
	 * Gets the base for the object. Useful for accessing stored raw data of ItemStack.
	 * 
	 * @return The base <code>ItemStack</code>
	 */
	public ItemStack copyBaseItemStack()
	{
		return baseItemStack;
	}
	
	/**
	 * Generates the randomized output <code>ItemStack</code>.
	 * 
	 * @return The randomized output
	 */
	public ItemStack generateOutput()
	{
		// The integer part of weight is guaranteed.
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
