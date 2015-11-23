package com.JasonILTG.ScienceMod.util;

import net.minecraft.nbt.NBTTagCompound;

import com.JasonILTG.ScienceMod.reference.NBTKeys;
import com.JasonILTG.ScienceMod.tileentity.MachineRecipe;
import com.JasonILTG.ScienceMod.tileentity.TEElectrolyzer;

public class RecipeHelper
{
	public static void writeRecipeToNBT(MachineRecipe recipe, NBTTagCompound tag)
	{
		tag.setInteger(NBTKeys.MachineData.RECIPE, recipe.getId());
	}
	
	public static final class Electrolyzer
	{
		public static final int WATER_SPLITTING = 0;
		
		public static TEElectrolyzer.ElectrolyzerRecipe readRecipeFromNBT(NBTTagCompound tag)
		{
			int recipeId = tag.getInteger(NBTKeys.MachineData.RECIPE);
			
			// Search through recipes to find the recipe
			for (TEElectrolyzer.ElectrolyzerRecipe currentRecipe : TEElectrolyzer.ElectrolyzerRecipe.values())
			{
				if (currentRecipe.getId() == recipeId) return currentRecipe;
			}
			
			return null;
		}
		
		public static void writeRecipeToNBT(MachineRecipe recipe, NBTTagCompound tag)
		{
			RecipeHelper.writeRecipeToNBT(recipe, tag);
		}
	}
}
