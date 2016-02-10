package com.JasonILTG.ScienceMod.compat.jei.assembler;

import com.JasonILTG.ScienceMod.reference.JEI;

import mezz.jei.api.recipe.IRecipeHandler;
import mezz.jei.api.recipe.IRecipeWrapper;

/**
 * Recipe Handler class for ChemReactors.
 * 
 * @author JasonILTG and syy1125
 */
public class AssemblerJEIRecipeHandler implements IRecipeHandler<AssemblerJEIRecipe>
{
	@Override
	public Class<AssemblerJEIRecipe> getRecipeClass()
	{
		return AssemblerJEIRecipe.class;
	}

	@Override
	public String getRecipeCategoryUid()
	{
		return JEI.JEI_CATEGORY_ASSEMBLER;
	}

	@Override
	public IRecipeWrapper getRecipeWrapper(AssemblerJEIRecipe recipe)
	{
		return recipe;
	}

	@Override
	public boolean isRecipeValid(AssemblerJEIRecipe recipe)
	{
		return true;
	}
}
