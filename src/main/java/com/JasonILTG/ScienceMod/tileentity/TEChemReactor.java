package com.JasonILTG.ScienceMod.tileentity;

import com.JasonILTG.ScienceMod.crafting.MachineRecipe;
import com.JasonILTG.ScienceMod.init.ScienceModItems;
import com.JasonILTG.ScienceMod.reference.ChemElements;
import com.JasonILTG.ScienceMod.tileentity.general.TEMachine;
import com.JasonILTG.ScienceMod.util.InventoryHelper;
import com.JasonILTG.ScienceMod.util.LogHelper;

import net.minecraft.item.ItemStack;

public class TEChemReactor extends TEMachine
{
	public static final String NAME = "Chemical Reactor";
	
	public static final int JAR_INV_SIZE = 1;
	public static final int INPUT_INV_SIZE = 3;
	public static final int OUTPUT_INV_SIZE = 3;
	
	public static final int DEFAULT_MAX_PROGRESS = 100;
	
	public static final int DEFAULT_ENERGY_CAPACITY = 0;
	
	public TEChemReactor()
	{
		// Initialize everything
		super(NAME, DEFAULT_MAX_PROGRESS, new int[] { NO_INV_SIZE, JAR_INV_SIZE, INPUT_INV_SIZE, OUTPUT_INV_SIZE });
	}
	
	@Override
	protected boolean hasIngredients(MachineRecipe recipeToUse)
	{
		// null check
		if (recipeToUse == null) return false;
		
		// If the recipe cannot use the input, the attempt fails.
		if (!recipeToUse.canProcess(allInventories[JAR_INV_INDEX][0], allInventories[INPUT_INV_INDEX]))
			return false;
		
		// Try to match output items with output slots.
		ItemStack[] newOutput = recipeToUse.getItemOutputs();
		
		if (InventoryHelper.findInsertPattern(newOutput, allInventories[OUTPUT_INV_INDEX]) == null) return false;
		
		return true;
	}
	
	@Override
	protected void consumeInputs(MachineRecipe recipe)
	{
		if (!(recipe instanceof ChemReactorRecipe)) return;
		ChemReactorRecipe validRecipe = (ChemReactorRecipe) recipe;
		
		// Consume input
		if (validRecipe.reqJarCount > 0)
		{
			if (allInventories[JAR_INV_INDEX][0] == null) LogHelper.fatal("Jar Stack is null!");
			allInventories[JAR_INV_INDEX][0].splitStack(validRecipe.reqJarCount);
		}
		
		if (validRecipe.reqItemStack != null) {
			for (ItemStack reqItem : validRecipe.reqItemStack)
			{
				if (reqItem == null) continue;
				
				int needed = reqItem.stackSize;
				for (int i = 0; i < allInventories[INPUT_INV_INDEX].length; i++)
				{
					if (allInventories[INPUT_INV_INDEX][i] != null && allInventories[INPUT_INV_INDEX][0].isItemEqual(reqItem))
					{
						if (allInventories[INPUT_INV_INDEX][i].stackSize >= needed)
						{
							allInventories[INPUT_INV_INDEX][i].splitStack(needed);
							needed = 0;
							break;
						}
						else
						{
							needed -= allInventories[INPUT_INV_INDEX][i].stackSize;
							allInventories[INPUT_INV_INDEX][i] = null;
						}
					}
				}
				if (needed > 0) LogHelper.fatal("Not enough items!");
			}
		}
		
		InventoryHelper.checkEmptyStacks(allInventories);
	}
	
	@Override
	public MachineRecipe[] getRecipes()
	{
		return ChemReactorRecipe.values();
	}
	
	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack)
	{
		if (getInvIndexBySlotIndex(index) == JAR_INV_INDEX && !stack.getIsItemStackEqual(new ItemStack(ScienceModItems.jar, 1))) return false;
		return true;
	}
	
	public enum ChemReactorRecipe implements MachineRecipe
	{
		CO2(2400, 0, new ItemStack[]{ new ItemStack(ScienceModItems.element, 1, ChemElements.CARBON.ordinal()), new ItemStack(ScienceModItems.element, 1, ChemElements.OXYGEN.ordinal()) }, new ItemStack[]{ new ItemStack(ScienceModItems.carbonDioxide) });
		
		public final int timeReq;
		public final int reqJarCount;
		public final ItemStack[] reqItemStack;
		public final ItemStack[] outItemStack;
		
		private ChemReactorRecipe(int timeRequired, int requiredJarCount, ItemStack[] requiredItemStacks, ItemStack[] outputItemStacks)
		{
			timeReq = timeRequired;
			reqJarCount = requiredJarCount;
			reqItemStack = requiredItemStacks;
			outItemStack = outputItemStacks;
		}
		
		private boolean hasJars(ItemStack inputJarStack)
		{
			if (reqJarCount == 0) return true;
			if (inputJarStack == null) return false;
			return inputJarStack.stackSize >= reqJarCount;
		}
		
		private boolean hasItems(ItemStack[] inputItemStacks)
		{
			if (reqItemStack != null)
			{
				// null check
				if (inputItemStacks == null) return false;
				
				for (ItemStack reqStack : reqItemStack)
				{
					if (reqStack == null) continue;
					
					int needed = reqStack.stackSize;
					for (ItemStack inputStack : inputItemStacks)
					{
						if (inputStack != null && inputStack.isItemEqual(reqStack)) needed -= inputStack.stackSize;
					}
					if (needed > 0) return false;
				}
			}
			return true;
		}
		
		/**
		 * @param params input format: jar input stack, item input stack, fluid input stack
		 */
		public boolean canProcess(Object... params)
		{
			ItemStack inputJarStack = (ItemStack) params[0];
			ItemStack[] inputItemStacks = (ItemStack[]) params[1];
			return hasJars(inputJarStack) && hasItems(inputItemStacks);
		}
		
		public ItemStack[] getItemOutputs()
		{
			return outItemStack;
		}
		
		public int getTimeRequired()
		{
			return timeReq;
		}
	}
}
