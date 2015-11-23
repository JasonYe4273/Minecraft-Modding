package com.JasonILTG.ScienceMod.util;

import net.minecraft.item.ItemStack;

public class ItemStackHelper
{
	public static ItemStack[] findInsertPattern(ItemStack stackToInsert, ItemStack[] insertTarget)
	{
		// null check
		if (insertTarget == null) return null;
		if (stackToInsert == null) return new ItemStack[insertTarget.length];
		
		// Generate local copies to prevent modification of the parameters
		ItemStack stack = stackToInsert.copy();
		// insertTarget should not be modified in the method
		
		// Initialize the output array
		ItemStack[] insertPattern = new ItemStack[insertTarget.length];
		
		// First pass through the array to look for already existing stacks of the same item
		for (int i = 0; i < insertPattern.length; i++)
		{
			if (insertTarget[i] != null && insertTarget[i].isItemEqual(stackToInsert)
					&& insertTarget[i].stackSize < insertTarget[i].getMaxStackSize())
			{
				// The target slot has a matching ItemStack and can store more
				if (insertTarget[i].getMaxStackSize() - insertTarget[i].stackSize > stack.stackSize) {
					// The stack has more than enough space
					insertPattern[i] = stack;
					// Insertion complete, return the pattern
					return insertPattern;
				}
				
				// The target slot does not have enough space
				insertPattern[i] = stack.splitStack(insertTarget[i].getMaxStackSize() - insertTarget[i].stackSize);
			}
		}
		
		// Second pass through the array to look for any empty output slot
		for (int i = 0; i < insertPattern.length; i++)
		{
			if (insertTarget[i] == null) {
				insertPattern[i] = stack;
				return insertPattern;
			}
		}
		
		// If it comes to this, we can't insert output into the output slots.
		return null;
	}
	
	public static ItemStack[] findInsertPattern(ItemStack[] stacksToInsert, ItemStack[] insertTarget)
	{
		// null check
		if (insertTarget == null) return null;
		if (stacksToInsert == null || stacksToInsert.length == 0) return new ItemStack[insertTarget.length];
		
		ItemStack[] outputToAdd = new ItemStack[insertTarget.length];
		// Use a copied version of the output inventory to prevent modification of the inventory
		ItemStack[] predictedOutput = new ItemStack[insertTarget.length];
		System.arraycopy(insertTarget, 0, predictedOutput, 0, insertTarget.length);
		
		for (ItemStack stack : stacksToInsert)
		{
			// Find out how to insert the stack
			ItemStack[] pattern = ItemStackHelper.findInsertPattern(stack, predictedOutput);
			
			// If the return is null, that means we can't insert the stack.
			if (pattern == null) return null;
			
			// Add the pattern to the output and to the predicted pattern
			outputToAdd = ItemStackHelper.mergeStackArrays(outputToAdd, pattern);
			predictedOutput = ItemStackHelper.mergeStackArrays(predictedOutput, pattern);
			if (outputToAdd == null) return null;
		}
		
		return outputToAdd;
	}
	
	public static ItemStack[] mergeStackArrays(ItemStack[] stackArray1, ItemStack[] stackArray2)
	{
		// null and length mismatch check
		if (stackArray1 == null) return stackArray2;
		if (stackArray2 == null) return stackArray1;
		if (stackArray1.length != stackArray2.length) return null;
		
		// Generate output
		ItemStack[] outStack = new ItemStack[stackArray1.length];
		for (int i = 0; i < outStack.length; i++) {
			// For each ItemStack in the array
			if (stackArray1[i] == null) {
				outStack[i] = stackArray2[i];
			}
			else if (stackArray2[i] == null) {
				outStack[i] = stackArray1[i];
			}
			else {
				if (stackArray1[i].isItemEqual(stackArray2[i]))
				{
					outStack[i] = new ItemStack(stackArray1[i].getItem(), stackArray1[i].stackSize + stackArray2[i].stackSize,
							stackArray1[i].getMetadata());
				}
				else {
					outStack[i] = null;
				}
			}
		}
		
		return outStack;
	}
}
