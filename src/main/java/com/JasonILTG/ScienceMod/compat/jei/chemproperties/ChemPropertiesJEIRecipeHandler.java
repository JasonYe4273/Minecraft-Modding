package com.JasonILTG.ScienceMod.compat.jei.chemproperties;

import com.JasonILTG.ScienceMod.reference.JEI;

import mezz.jei.api.recipe.IRecipeHandler;
import mezz.jei.api.recipe.IRecipeWrapper;

/**
 * Recipe Handler class for chem properties.
 * 
 * @author JasonILTG and syy1125
 */
public class ChemPropertiesJEIRecipeHandler implements IRecipeHandler<ChemPropertiesJEIRecipe>
{
	@Override
	public Class<ChemPropertiesJEIRecipe> getRecipeClass()
	{
		return ChemPropertiesJEIRecipe.class;
	}

	@Override
	public String getRecipeCategoryUid()
	{
		return JEI.JEI_CATEGORY_CHEM_PROPERTIES;
	}

	@Override
	public IRecipeWrapper getRecipeWrapper(ChemPropertiesJEIRecipe recipe)
	{
		return recipe;
	}

	@Override
	public boolean isRecipeValid(ChemPropertiesJEIRecipe recipe)
	{
		return true;
	}
}
