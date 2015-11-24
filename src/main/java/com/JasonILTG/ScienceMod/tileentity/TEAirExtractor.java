package com.JasonILTG.ScienceMod.tileentity;

import com.JasonILTG.ScienceMod.crafting.MachineRecipe;

import net.minecraft.item.ItemStack;

public class TEAirExtractor extends TEMachine
{
	public static final String NAME = "Air Extractor";
	
	public static final int INVENTORY_SIZE = 28;
	public static final int JAR_INPUT_INDEX = 0;
	public static final int[] OUTPUT_INDEX = new int[INVENTORY_SIZE - 1];
	{
		for (int i = 0; i < OUTPUT_INDEX.length; i++) {
			OUTPUT_INDEX[i] = i + 1;
		}
	}
	
	public static final int DEFAULT_MAX_PROGRESS = 200;
	
	public TEAirExtractor()
	{
		super(NAME, DEFAULT_MAX_PROGRESS, INVENTORY_SIZE, OUTPUT_INDEX);
	}
	
	@Override
	public void craft()
	{	
		
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
	protected boolean canCraft(MachineRecipe recipeToUse)
	{
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public void checkFields()
	{
		// TODO Auto-generated method stub
		
	}
	
	public enum AirExtractorRecipe implements MachineRecipe
	{
		DEFAULT(200);
		
		@Override
		public boolean canProcessUsing(Object... params)
		{
			// TODO Auto-generated method stub
			return false;
		}
		
		@Override
		public ItemStack[] getItemOutputs()
		{
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public int getTimeRequired()
		{
			// TODO Auto-generated method stub
			return 0;
		}
		
	}
}
