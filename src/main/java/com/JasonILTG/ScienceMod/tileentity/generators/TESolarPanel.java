package com.JasonILTG.ScienceMod.tileentity.generators;

import com.JasonILTG.ScienceMod.crafting.GeneratorRecipe;

public class TESolarPanel extends TEGenerator
{
	public static final String NAME = "Solar Panel";
	
	public static final int NUM_TANKS = 0;
	
	protected int mode;
	public static final int OFF_MODE = 0;
	public static final int DAY_MODE = 1;
	public static final int NIGHT_MODE = 2;
	
	public static final int DAY_POWER = 1;
	public static final int NIGHT_POWER = 0;
	
	/**
	 * Default constructor.
	 */
	public TESolarPanel()
	{
		super(NAME, new int[] { NO_INV_SIZE, NO_INV_SIZE, NO_INV_SIZE, NO_INV_SIZE, NO_INV_SIZE }, NUM_TANKS);
		mode = OFF_MODE;
	}
	
	@Override
	public void generate()
	{	
		if (this.worldObj.canBlockSeeSky(this.pos))
		{
			if (this.worldObj.isDaytime())
			{
				mode = DAY_MODE;
				generatorPower.producePower(DAY_POWER);
			}
			else
			{
				mode = NIGHT_MODE;
				generatorPower.producePower(NIGHT_POWER);
			}
		}
		else
		{
			mode = OFF_MODE;
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
	
	/**
	 * @return The mode of the solar panel (0 if off, 1, if day, 2 if night)
	 */
	public int getMode()
	{
		return mode;
	}
}
