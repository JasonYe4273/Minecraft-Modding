package com.JasonILTG.ScienceMod.tileentity.generators;

import com.JasonILTG.ScienceMod.crafting.GeneratorRecipe;

/**
 * Tile entity class for combusters.
 * 
 * @author JasonILTG and syy1125
 */
public class TECombuster extends TEGenerator
{
	public static final String NAME = "Combuster";
	
	public static final int JAR_INV_SIZE = 1;
	public static final int INPUT_INV_SIZE = 1;
	public static final int OUTPUT_INV_SIZE = 2;
	
	public static final int NUM_TANKS = 2;
	public static final int FUEL_TANK_INDEX = 0;
	public static final int COOLANT_TANK_INDEX = 1;
	
	public static final int DEFAULT_ENERGY_CAPACITY = 0;
	
	/**
	 * Default constructor.
	 */
	public TECombuster()
	{
		super(NAME, new int[] { NO_INV_SIZE, JAR_INV_SIZE, INPUT_INV_SIZE, OUTPUT_INV_SIZE, NO_INV_SIZE }, NUM_TANKS);
	}

	@Override
	protected GeneratorRecipe[] getRecipes()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void consumeInputs(GeneratorRecipe recipe)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	protected boolean hasIngredients(GeneratorRecipe recipeToUse)
	{
		// TODO Auto-generated method stub
		return false;
	}
	
	private enum CombustionRecipes
	{
		
	}
}
