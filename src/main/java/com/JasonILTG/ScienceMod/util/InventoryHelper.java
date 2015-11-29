package com.JasonILTG.ScienceMod.util;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

import com.JasonILTG.ScienceMod.reference.NBTKeys;
import com.JasonILTG.ScienceMod.reference.NBTTypes;

public class InventoryHelper
{
	/**
	 * Finds an insertion pattern of the given ItemStack into the given inventory. This method will attempt to find the stacks with
	 * matching items and fill those stacks first.
	 * 
	 * @param stackToInsert
	 * @param insertTarget
	 * @return the insertion pattern; null if the given stack cannot be inserted
	 */
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
		for (int i = 0; i < insertPattern.length; i ++)
		{
			if (insertTarget[i] != null && insertTarget[i].isItemEqual(stackToInsert)
					&& insertTarget[i].stackSize < insertTarget[i].getMaxStackSize())
			{
				// The target slot has a matching ItemStack and can store more
				if (insertTarget[i].getMaxStackSize() - insertTarget[i].stackSize >= stack.stackSize) {
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
		for (int i = 0; i < insertPattern.length; i ++)
		{
			if (insertTarget[i] == null) {
				insertPattern[i] = stack;
				return insertPattern;
			}
		}
		
		// If it comes to this, we can't insert output into the output slots.
		return null;
	}
	
	/**
	 * A variant of findInsertPattern that inserts an array of ItemStacks.
	 * 
	 * @param stacksToInsert
	 * @param insertTarget
	 * @return
	 */
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
			ItemStack[] pattern = InventoryHelper.findInsertPattern(stack, predictedOutput);
			
			// If the return is null, that means we can't insert the stack.
			if (pattern == null) return null;
			
			// Add the pattern to the output and to the predicted pattern
			outputToAdd = InventoryHelper.mergeStackArrays(outputToAdd, pattern);
			predictedOutput = InventoryHelper.mergeStackArrays(predictedOutput, pattern);
			if (outputToAdd == null) return null;
		}
		
		return outputToAdd;
	}
	
	/**
	 * Merges together two ItemStack arrays. Warning: if there are item type mismatches, the slot will be set to null. The stack size
	 * may also go over the limit if you are not careful.
	 * 
	 * @param stackArray1
	 * @param stackArray2
	 * @return the merge result of the two stacks.
	 */
	public static ItemStack[] mergeStackArrays(ItemStack[] stackArray1, ItemStack[] stackArray2)
	{
		// null and length mismatch check
		if (stackArray1 == null) return stackArray2;
		if (stackArray2 == null) return stackArray1;
		if (stackArray1.length != stackArray2.length) return null;
		
		// Generate output
		ItemStack[] outStack = new ItemStack[stackArray1.length];
		for (int i = 0; i < outStack.length; i ++) {
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
	
	/**
	 * Checks for empty stacks in an inventory and turns them to null.
	 * 
	 * @param stacks the inventory to check through
	 */
	public static void checkEmptyStacks(ItemStack[] stacks)
	{
		for (int i = 0; i < stacks.length; i ++)
		{
			// Null check
			if (stacks[i] == null) continue;
			
			// Set zero-size stacks to null
			if (stacks[i].stackSize == 0) stacks[i] = null;
		}
	}
	
	public static void checkEmptyStacks(ItemStack[][] inventories)
	{
		for (int i = 0; i < inventories.length; i ++) {
			for (int j = 0; j < inventories[i].length; j ++)
			{
				// Null check
				if (inventories[i][j] == null) continue;
				
				// Set zero-size stacks to null
				if (inventories[i][j].stackSize == 0) inventories[i][j] = null;
			}
		}
	}
	
	public static boolean hasStack(ItemStack stack, ItemStack[] inventory)
	{
		int numItemsFound = 0;
		
		for (ItemStack invSlot : inventory)
		{
			if (stack.isItemEqual(invSlot)) numItemsFound += invSlot.stackSize;
			
			if (numItemsFound >= stack.stackSize) return true;
		}
		
		return false;
	}
	
	public static boolean hasStack(ItemStack[] stacks, ItemStack[] inventory)
	{
		for (ItemStack currentStack : stacks) {
			if (!hasStack(currentStack, inventory)) return false;
		}
		
		return true;
	}
	
	/**
	 * WIP method to pull an item stack out of an inventory
	 * 
	 * @param stackToPull
	 * @param inventoryToPullFrom
	 * @param doUpdate if true, this method will change the inventory
	 * @return the pulled stack; this should be equal to the stackToPull parameter. If the stack cannot be pulled, null is reuturned.
	 */
	public static ItemStack pullStack(ItemStack stackToPull, ItemStack[] inventoryToPullFrom)
	{
		// If there is not enough to pull, return null.
		if (!(hasStack(stackToPull, inventoryToPullFrom))) return null;
		
		// Save pulling data for now and use local copies. If we want to update, do that at the end.
		ItemStack localPullStack = stackToPull.copy();
		ItemStack stackPulled = new ItemStack(stackToPull.getItem(), 0, stackToPull.getMetadata());
		// inventoryToPullFrom should not be modified in the first loop.
		
		for (int i = 0; i < inventoryToPullFrom.length; i ++)
		{
			ItemStack currentStack = inventoryToPullFrom[i];
			
			if (currentStack != null && currentStack.isItemEqual(localPullStack))
			{
				// The stack we want to pull can be acquired here
				int amountToPull;
				if (currentStack.stackSize > localPullStack.stackSize - stackPulled.stackSize) {
					// This stack has enough
					amountToPull = localPullStack.stackSize;
					inventoryToPullFrom[i].splitStack(amountToPull);
					localPullStack.splitStack(amountToPull);
					stackPulled.splitStack(-amountToPull);
					
					checkEmptyStacks(inventoryToPullFrom);
					return stackPulled;
				}
				else {
					// This stack does not have enough. Let's pull as many as we can.
					amountToPull = currentStack.stackSize;
					inventoryToPullFrom[i].splitStack(amountToPull);
					localPullStack.splitStack(amountToPull);
					stackPulled.splitStack(-amountToPull);
				}
			}
		}
		
		// Backup return - this should not trigger.
		LogHelper.warn("Backup return statement in pullStack triggered! What did you break?!");
		return null;
	}
	
	/**
	 * Takes a 2D ItemStack array and flattens it to a 1D array.
	 * 
	 * @param inventories the 2D array representing the inventories
	 * @return the flattened inventory
	 */
	public static ItemStack[] flattenInventoryArray(ItemStack[][] inventories)
	{
		// Calculate inventory size
		int invSize = 0;
		for (ItemStack[] inv : inventories)
			invSize += inv.length;
		
		ItemStack[] combinedInv = new ItemStack[invSize];
		// Put inventories together.
		int currentIndex = 0;
		for (ItemStack[] inv : inventories) {
			for (ItemStack stack : inv) {
				combinedInv[currentIndex] = stack;
				currentIndex ++;
			}
		}
		
		return combinedInv;
	}
	
	public static ItemStack[][] separateInventories(ItemStack[] inventory, int[] sectionLengths)
	{
		ItemStack[][] inventories = new ItemStack[sectionLengths.length + 1][];
		int currentIndex = 0;
		
		for (int i = 0; i < sectionLengths.length; i ++) {
			inventories[i] = new ItemStack[sectionLengths[i]];
			for (int j = 0; j < sectionLengths[i]; j ++)
			{
				inventories[i][j] = inventory[currentIndex];
				currentIndex ++;
				
				// Just a check for safety.
				if (currentIndex >= inventory.length) return inventories;
			}
		}
		
		return inventories;
	}
	
	public static ItemStack[][] readInvArrayFromNBT(NBTTagCompound tag)
	{
		NBTTagList tagList = tag.getTagList(NBTKeys.Inventory.ITEMS, NBTTypes.COMPOUND);
		ItemStack[][] inventories = new ItemStack[tag.getInteger(NBTKeys.Inventory.INVARRAY_SIZE)][];
		
		// For each tag in the tag list
		for (int i = 0; i < tagList.tagCount(); i ++)
		{
			// Extract the tag list and prep the inventory
			NBTTagCompound invTag = tagList.getCompoundTagAt(i);
			NBTTagList invTagList = invTag.getTagList(NBTKeys.Inventory.ITEMS, NBTTypes.COMPOUND);
			inventories[i] = new ItemStack[invTag.getInteger(NBTKeys.Inventory.INV_SIZE)];
			
			// For each tag representing an ItemStack
			for (int j = 0; j < invTagList.tagCount(); j ++)
			{
				// Load the ItemStack.
				NBTTagCompound stackTag = invTagList.getCompoundTagAt(j);
				byte stackIndex = stackTag.getByte(NBTKeys.Inventory.SLOT);
				inventories[i][stackIndex] = ItemStack.loadItemStackFromNBT(stackTag);
			}
		}
		
		return inventories;
	}
	
	public static void writeInvArrayToNBT(ItemStack[][] invArray, NBTTagCompound tag)
	{
		NBTTagList tagList = new NBTTagList();
		
		// For each inventory
		for (ItemStack[] inv : invArray)
		{
			NBTTagList invTagList = new NBTTagList();
			
			// For each item in the inventory
			for (int stackIndex = 0; stackIndex < inv.length; stackIndex ++)
			{
				// Record data and slot and append the tag to the sub tag list.
				if (inv[stackIndex] != null) {
					NBTTagCompound stackTag = new NBTTagCompound();
					stackTag.setByte(NBTKeys.Inventory.SLOT, (byte) stackIndex);
					inv[stackIndex].writeToNBT(stackTag);
					invTagList.appendTag(stackTag);
				}
			}
			
			// Append the sub tag list to the super tag list
			NBTTagCompound invTag = new NBTTagCompound();
			invTag.setTag(NBTKeys.Inventory.ITEMS, invTagList);
			invTag.setInteger(NBTKeys.Inventory.INV_SIZE, inv.length);
			tagList.appendTag(invTag);
		}
		
		tag.setTag(NBTKeys.Inventory.ITEMS, tagList);
		tag.setInteger(NBTKeys.Inventory.INVARRAY_SIZE, invArray.length);
	}
}
