package com.JasonILTG.ScienceMod.tileentity.machines;

import com.JasonILTG.ScienceMod.crafting.MachineRecipe;

import net.minecraft.item.ItemStack;

public class TEThermoSmelter extends TEMachine
{
	public static final String NAME = "ThermoFurnace";
	
	public static final int UPGRADE_SIZE = 2;
	public static final int SMELT_INPUT_SIZE = 3;
	public static final int SMELT_OUTPUT_SIZE = 3;
	public static final int ALLOY_INPUT_SIZE = 3;
	public static final int ALLOY_OUTPUT_SIZE = 3;
	
	public TEThermoSmelter()
	{
		super(NAME, new int[] { UPGRADE_SIZE, SMELT_INPUT_SIZE, SMELT_OUTPUT_SIZE, ALLOY_INPUT_SIZE, ALLOY_OUTPUT_SIZE });
	}
	
	@Override
	public MachineRecipe[] getRecipes()
	{
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	protected void consumeInputs(MachineRecipe recipe)
	{
		// TODO Auto-generated method stub
		
	}
	
	@Override
	protected boolean hasIngredients(MachineRecipe recipeToUse)
	{
		// TODO Auto-generated method stub
		return false;
	}
	
	public enum SmelterRecipe implements MachineRecipe
	{
		;
		
		@Override
		public boolean canProcess(Object... params)
		{
			// TODO Auto-generated method stub
			return false;
		}
		
		@Override
		public int getTimeRequired()
		{
			// TODO Auto-generated method stub
			return 0;
		}
		
		@Override
		public ItemStack[] getItemOutputs()
		{
			// TODO Auto-generated method stub
			return null;
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
