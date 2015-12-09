package com.JasonILTG.ScienceMod.tileentity.generators;

import com.JasonILTG.ScienceMod.crafting.GeneratorRecipe;

public class TESolarPanel extends TEGenerator
{
	public static final String NAME = "Solar Panel";
	
	public static final int NUM_TANKS = 0;
	
	public static final int DAY_POWER = 1;
	public static final int NIGHT_POWER = 0;
	
	/**
	 * Default constructor.
	 */
	public TESolarPanel()
	{
		super(NAME, new int[] { NO_INV_SIZE, NO_INV_SIZE, NO_INV_SIZE, NO_INV_SIZE, NO_INV_SIZE }, NUM_TANKS);
	}
	
	@Override
	public void generate()
	{	
		if (this.worldObj.canBlockSeeSky(this.pos))
		{
			if (this.worldObj.isDaytime()) generatorPower.producePower(DAY_POWER);
			else generatorPower.producePower(NIGHT_POWER);
		}
	}

	@Override
	protected GeneratorRecipe[] getRecipes()
	{
		return null;
	}

	@Override
	protected void consumeInputs(GeneratorRecipe recipe)
	{
		
	}

	@Override
	protected boolean hasIngredients(GeneratorRecipe recipeToUse)
	{
		return false;
	}
}
