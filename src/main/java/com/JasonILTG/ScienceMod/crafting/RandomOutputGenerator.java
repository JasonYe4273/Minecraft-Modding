package com.JasonILTG.ScienceMod.crafting;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.item.ItemStack;

/**
 * Generator to randomize outputs.
 * 
 * @author JasonILTG and syy1125
 */
public abstract class RandomOutputGenerator
{
	/** The <code>RandomizedItemStack</code>s possible */
	protected List<RandomizedItemStack> storedStacks;
	/** The random number generator */
	private static Random generator = new Random();
	
	/**
	 * Default constructor.
	 */
	public RandomOutputGenerator()
	{
		storedStacks = new ArrayList<RandomizedItemStack>();
	}
	
	/**
	 * Adds possible <code>RandomizedItemStack</code>s to this generator
	 * 
	 * @param possibleStacks The <codE>RandomizedItemStack</code>s to be added
	 */
	public abstract void addPossibility(RandomizedItemStack... possibleStacks);
	
	/**
	 * Generates the output for one time.
	 * 
	 * @return a randomized output based on stored possible outputs
	 */
	public abstract ItemStack[] generateOutputs();
	
	/**
	 * Output possibilities do not interfere with each other.
	 */
	public static class Inclusive extends RandomOutputGenerator
	{
		/**
		 * Constructor.
		 * 
		 * @param possibleStacks The <code>RandomizedItemStack</code>s
		 */
		public Inclusive(RandomizedItemStack... possibleStacks)
		{
			super();
			addPossibility(possibleStacks);
		}
		
		@Override
		public void addPossibility(RandomizedItemStack... possibleStacks)
		{
			// Add all the RandomizedItemStacks to the list.
			for (RandomizedItemStack aStack : possibleStacks)
			{
				storedStacks.add(aStack);
			}
		}
		
		@Override
		public ItemStack[] generateOutputs()
		{
			List<ItemStack> outStack = new ArrayList<ItemStack>();
			
			// For all RandomizedItemStacks in storage
			for (RandomizedItemStack randomStack : storedStacks)
			{
				ItemStack stackToAdd = randomStack.generateOutput();
				if (stackToAdd.stackSize <= 0) continue;
				outStack.add(stackToAdd);
			}
			
			return outStack.toArray(new ItemStack[outStack.size()]);
		}
	}
	
	/**
	 * Outputs are mutually exclusive.
	 */
	public static class Exclusive extends RandomOutputGenerator
	{
		/** The total weight of all possible <code>RandomizedItemStack</code>s */
		private double totalWeight = 0;
		
		/**
		 * Constructor.
		 * 
		 * @param possibleStacks The <code>RandomizedItemStack</code>s
		 */
		public Exclusive(RandomizedItemStack... possibleStacks)
		{
			super();
			addPossibility(possibleStacks);
		}
		
		@Override
		public void addPossibility(RandomizedItemStack... possibleStacks)
		{
			// Add all the RandomizedItemStacks to the list.
			for (RandomizedItemStack aStack : possibleStacks)
			{
				storedStacks.add(aStack);
				totalWeight += aStack.getWeight();
			}
			
		}
		
		@Override
		public ItemStack[] generateOutputs()
		{
			// Generates a random double between 0 and totalWeight, and then sweeps through the stored outputs to determine which
			// output this number corresponds to.
			double outputIndicator = generator.nextDouble() * totalWeight;
			double currentIndicator = 0;
			
			for (RandomizedItemStack currentStack : storedStacks)
			{
				currentIndicator += currentStack.getWeight();
				if (currentIndicator > outputIndicator) return new ItemStack[] { currentStack.copyBaseItemStack() };
			}
			
			// This should not trigger. Ever.
			return null;
		}
	}
}
