package com.JasonILTG.ScienceMod.tileentity.generators;

import com.JasonILTG.ScienceMod.ScienceMod;
import com.JasonILTG.ScienceMod.crafting.GeneratorRecipe;
import com.JasonILTG.ScienceMod.messages.SolarPanelModeMessage;

public class TESolarPanel extends TEGenerator
{
	public static final String NAME = "Solar Panel";
	
	public static final int UPGRADE_INV_SIZE = 2;
	
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
		super(NAME, new int[] { UPGRADE_INV_SIZE, NO_INV_SIZE, NO_INV_SIZE, NO_INV_SIZE, NO_INV_SIZE }, NUM_TANKS);
		mode = OFF_MODE;
	}
	
	@Override
	public void generate()
	{	
		if (this.worldObj.canBlockSeeSky(this.pos))
		{
			if (this.worldObj.isDaytime())
			{
				if (mode != DAY_MODE) ScienceMod.snw.sendToAll(new SolarPanelModeMessage(this.pos.getX(), this.pos.getY(), this.pos.getZ(), DAY_MODE));
				else mode = DAY_MODE;
				generatorPower.producePower(DAY_POWER);
			}
			else
			{
				if (mode != NIGHT_MODE) ScienceMod.snw.sendToAll(new SolarPanelModeMessage(this.pos.getX(), this.pos.getY(), this.pos.getZ(), NIGHT_MODE));
				else mode = NIGHT_MODE;
				generatorPower.producePower(NIGHT_POWER);
			}
		}
		else
		{
			if (mode != OFF_MODE) ScienceMod.snw.sendToAll(new SolarPanelModeMessage(this.pos.getX(), this.pos.getY(), this.pos.getZ(), OFF_MODE));
			else mode = OFF_MODE;
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
	
	/**
	 * Sets the mode of the solar panel. This is only used on the client side for GUIs.
	 * 
	 * @param mode The mode
	 */
	public void setMode(int mode)
	{
		this.mode = mode;
	}
}
