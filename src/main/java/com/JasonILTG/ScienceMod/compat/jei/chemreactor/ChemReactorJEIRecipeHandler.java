package com.JasonILTG.ScienceMod.compat.jei.chemreactor;

import com.JasonILTG.ScienceMod.reference.JEI;

import mezz.jei.api.recipe.IRecipeHandler;
import mezz.jei.api.recipe.IRecipeWrapper;

/**
 * Recipe Handler class for ChemReactors.
 * 
 * @author JasonILTG and syy1125
 */
public class ChemReactorJEIRecipeHandler implements IRecipeHandler<ChemReactorJEIRecipe>
{
	@Override
	public Class<ChemReactorJEIRecipe> getRecipeClass()
	{
		return ChemReactorJEIRecipe.class;
	}

	@Override
	public String getRecipeCategoryUid()
	{
		return JEI.JEI_CATEGORY_CHEM_REACTOR;
	}

	@Override
	public IRecipeWrapper getRecipeWrapper(ChemReactorJEIRecipe recipe)
	{
		return recipe;
	}

	@Override
	public boolean isRecipeValid(ChemReactorJEIRecipe recipe)
	{
		return true;
	}
}
