package com.JasonILTG.ScienceMod.tileentity.machines;

import java.util.ArrayList;

import com.JasonILTG.ScienceMod.crafting.MachineHeatedRecipe;
import com.JasonILTG.ScienceMod.crafting.MachinePoweredRecipe;
import com.JasonILTG.ScienceMod.crafting.MachineRecipe;
import com.JasonILTG.ScienceMod.init.ScienceModItems;
import com.JasonILTG.ScienceMod.util.InventoryHelper;
import com.JasonILTG.ScienceMod.util.LogHelper;

import net.minecraft.item.ItemStack;

/**
 * Tile entity class for chemical reactors.
 * 
 * @author JasonILTG and syy1125
 */
public class TEChemReactor extends TEMachine
{
	public static final String NAME = "Chemical Reactor";

	public static final int UPGRADE_INV_SIZE = 2;
	public static final int JAR_INV_SIZE = 1;
	public static final int INPUT_INV_SIZE = 3;
	public static final int OUTPUT_INV_SIZE = 3;
	
	public static final int DEFAULT_ENERGY_CAPACITY = 0;
	
	/**
	 * Default Constructor.
	 */
	public TEChemReactor()
	{
		// Initialize everything
		super(NAME, new int[] { UPGRADE_INV_SIZE, JAR_INV_SIZE, INPUT_INV_SIZE, OUTPUT_INV_SIZE, NO_INV_SIZE });
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
					if (allInventories[INPUT_INV_INDEX][i] != null && allInventories[INPUT_INV_INDEX][i].isItemEqual(reqItem))
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
	
	/**
	 * Enum for chemical reactor recipes.
	 * 
	 * @author JasonILTG and syy1125
	 */
	public static class ChemReactorRecipe implements MachinePoweredRecipe, MachineHeatedRecipe
	{
		// CO2(2000, 5F, 0, 2.5375F, 0, new ItemStack[]{ new ItemStack(ScienceModItems.element, 1, EnumElement.CARBON.ordinal()), new ItemStack(ScienceModItems.element, 1, EnumElement.OXYGEN.ordinal()) }, new ItemStack[]{ new ItemStack(ScienceModItems.carbonDioxide), new ItemStack(ScienceModItems.jar) });
		
		private static ArrayList<ChemReactorRecipe> recipeList = new ArrayList<ChemReactorRecipe>();
		private static ChemReactorRecipe[] recipes;
		
		private static int ordinal = 0;
		private final int idx;
		
		/** The time required */
		public final int timeReq;
		/** The power used every tick */
		public final float powerReq;
		/** The temperature required */
		public final float tempReq;
		/** The heat released every tick */
		public final float heatReleased;
		/** The number of jars required */
		public final int reqJarCount;
		/** The ItemStacks required */
		public final ItemStack[] reqItemStack;
		/** The ItemStack outputs */
		public final ItemStack[] outItemStack;
		
		/**
		 * Constructor.
		 * 
		 * @param timeRequired The time required
		 * @param powerRequired The power used every tick
		 * @param tempRequired The temperature required
		 * @param heatReleased The heat released every tick
		 * @param requiredJarCount The number of jars required
		 * @param requiredItemStacks The ItemStacks required
		 * @param outputItemStacks The ItemStack outputs
		 */
		public ChemReactorRecipe(int timeRequired, float powerRequired, float tempRequired, float heatReleased, int requiredJarCount, ItemStack[] requiredItemStacks, ItemStack[] outputItemStacks)
		{
			idx = ordinal++;
			recipeList.add(this);
			
			timeReq = timeRequired;
			powerReq = powerRequired;
			tempReq = tempRequired;
			this.heatReleased = heatReleased;
			reqJarCount = requiredJarCount;
			reqItemStack = requiredItemStacks;
			outItemStack = outputItemStacks;
		}
		
		@Override
		public int ordinal()
		{
			return idx;
		}
		
		public static void makeRecipeArray()
		{
			recipes = recipeList.toArray(new ChemReactorRecipe[0]);
		}
		
		public static ChemReactorRecipe[] values()
		{
			return recipes;
		}
		
		/**
		 * Determines whether there are enough jars.
		 * 
		 * @param inputJarStack The jar ItemStack input
		 * @return Whether there are enough jars
		 */
		private boolean hasJars(ItemStack inputJarStack)
		{
			if (reqJarCount == 0) return true;
			if (inputJarStack == null) return false;
			return inputJarStack.stackSize >= reqJarCount;
		}
		
		/**
		 * Determine whether the required input ItemStacks are present.
		 * 
		 * @param inputItemStacks The ItemStack inputs
		 * @return Whether the required input is present
		 */
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
		 * @param params Input format: jar input stack, item input stacks
		 */
		@Override
		public boolean canProcess(Object... params)
		{
			ItemStack inputJarStack = (ItemStack) params[0];
			ItemStack[] inputItemStacks = (ItemStack[]) params[1];
			return hasJars(inputJarStack) && hasItems(inputItemStacks);
		}
		
		@Override
		public ItemStack[] getItemOutputs()
		{
			return outItemStack;
		}
		
		@Override
		public int getTimeRequired()
		{
			return timeReq;
		}
		
		@Override
		public float getTempRequired()
		{
			return tempReq;
		}
		
		@Override
		public float getHeatReleased()
		{
			return heatReleased;
		}
		
		@Override
		public float getPowerRequired()
		{
			return powerReq;
		}
	}

	/* (non-Javadoc)
	 * @see net.minecraft.inventory.IInventory#removeStackFromSlot(int)
	 */
	@Override
	public ItemStack removeStackFromSlot(int index)
	{
		// TODO Auto-generated method stub
		return null;
	}
}
