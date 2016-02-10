package com.JasonILTG.ScienceMod.compat.jei.electrolyzer;

import java.util.ArrayList;
import java.util.List;

import com.JasonILTG.ScienceMod.tileentity.machines.TEElectrolyzer;
import com.JasonILTG.ScienceMod.tileentity.machines.TEElectrolyzer.ElectrolyzerRecipe;

/**
 * Generates all JEI electrolyzer recipes.
 * 
 * @author JasonILTG and syy1125
 */
public class ElectrolyzerJEIRecipeMaker
{
	public static List<ElectrolyzerJEIRecipe> generate()
	{
		ArrayList<ElectrolyzerJEIRecipe> jeiRecipes = new ArrayList<ElectrolyzerJEIRecipe>();
		for (ElectrolyzerRecipe recipe : TEElectrolyzer.ElectrolyzerRecipe.values())
		{
			jeiRecipes.add(new ElectrolyzerJEIRecipe(recipe));
		}
		
		return jeiRecipes;
	}
}
