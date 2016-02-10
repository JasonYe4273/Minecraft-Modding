package com.JasonILTG.ScienceMod.compat.jei.chemreactor;

import com.JasonILTG.ScienceMod.compat.jei.MachineRecipeJEI;
import com.JasonILTG.ScienceMod.tileentity.machines.TEChemReactor;

/**
 * JEI recipe class for ChemReactors.
 * 
 * @author JasonILTG and syy1125
 */
public class ChemReactorJEIRecipe extends MachineRecipeJEI
{
	public ChemReactorJEIRecipe(TEChemReactor.ChemReactorRecipe recipe)
	{
		super(recipe.reqItemStack, recipe.outItemStack, recipe.reqJarCount, true, null, null);
	}
}
