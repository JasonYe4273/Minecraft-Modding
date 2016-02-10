package com.JasonILTG.ScienceMod.compat.jei.chemreactor;

import java.util.ArrayList;
import java.util.List;

import com.JasonILTG.ScienceMod.tileentity.machines.TEChemReactor;
import com.JasonILTG.ScienceMod.tileentity.machines.TEChemReactor.ChemReactorRecipe;

/**
 * Generates all JEI ChemReactor recipes.
 * 
 * @author JasonILTG and syy1125
 */
public class ChemReactorJEIRecipeMaker
{
	public static List<ChemReactorJEIRecipe> generate()
	{
		ArrayList<ChemReactorJEIRecipe> jeiRecipes = new ArrayList<ChemReactorJEIRecipe>();
		for (ChemReactorRecipe recipe : TEChemReactor.ChemReactorRecipe.values())
		{
			jeiRecipes.add(new ChemReactorJEIRecipe(recipe));
		}
		
		return jeiRecipes;
	}
}
