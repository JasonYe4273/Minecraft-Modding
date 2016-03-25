package com.JasonILTG.ScienceMod.compat.jei.electrolyzer;

import com.JasonILTG.ScienceMod.reference.JEI;

import mezz.jei.api.recipe.IRecipeHandler;
import mezz.jei.api.recipe.IRecipeWrapper;

/**
 * Recipe Handler classes for electrolyzers.
 * 
 * @author JasonILTG and syy1125
 */
public class ElectrolyzerJEIRecipeHandler implements IRecipeHandler<ElectrolyzerJEIRecipe>
{
	@Override
	public Class<ElectrolyzerJEIRecipe> getRecipeClass()
	{
		return ElectrolyzerJEIRecipe.class;
	}

	@Override
	public String getRecipeCategoryUid()
	{
		return JEI.JEI_CATEGORY_ELECTROLYZER;
	}

	@Override
	public IRecipeWrapper getRecipeWrapper(ElectrolyzerJEIRecipe recipe)
	{
		return recipe;
	}

	@Override
	public boolean isRecipeValid(ElectrolyzerJEIRecipe recipe)
	{
		return true;
	}
}
