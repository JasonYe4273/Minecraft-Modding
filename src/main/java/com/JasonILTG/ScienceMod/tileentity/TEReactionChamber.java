package com.JasonILTG.ScienceMod.tileentity;

import com.JasonILTG.ScienceMod.crafting.MachineRecipe;
import com.JasonILTG.ScienceMod.init.ScienceModItems;
import com.JasonILTG.ScienceMod.reference.ChemElement;
import com.JasonILTG.ScienceMod.tileentity.general.TEMachine;

import net.minecraft.item.ItemStack;

public class TEReactionChamber extends TEMachine
{
	public static final String NAME = "Combustion Chamber";
	
	public static final int INVENTORY_SIZE = 9;
	public static final int JAR_SLOT_INDEX = 0;
	public static final int[] INPUT_INDEX = { 1, 2, 3, 4 };
	public static final int[] OUTPUT_INDEX = { 5, 6, 7, 8 };
	
	public static final int DEFAULT_MAX_PROGRESS = 100;
	
	public TEReactionChamber()
	{
		super(NAME, DEFAULT_MAX_PROGRESS, INVENTORY_SIZE, OUTPUT_INDEX);
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
	
	public enum ReactionRecipe implements MachineRecipe
	{
		Hydrogen(20, 0, new ItemStack[] { new ItemStack(ScienceModItems.element, 2, ChemElement.HYDROGEN.ordinal()) });
		
		private ItemStack[] reactants;
		private ItemStack[] products;
		private int reqTime;
		private float reqTemp;
		
		private ReactionRecipe(int timeRequired, float reqTemperature, ItemStack[] reactants, ItemStack[] products)
		{
			reqTemp = timeRequired;
			reqTemp = reqTemperature;
			this.reactants = reactants;
			this.products = products;
		}
		
		@Override
		public boolean canProcess(Object... params)
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
