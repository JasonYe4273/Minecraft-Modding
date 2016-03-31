package com.JasonILTG.ScienceMod.compat.jei.chemproperties;

import javax.annotation.Nonnull;

import com.JasonILTG.ScienceMod.item.chemistry.CompoundItem;
import com.JasonILTG.ScienceMod.reference.chemistry.init.PropertyLoader.Property;

import mezz.jei.api.recipe.BlankRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;

/**
 * JEI recipe class for chem properties.
 * 
 * @author JasonILTG and syy1125
 */
public class ChemPropertiesJEIRecipe extends BlankRecipeWrapper
{
	public final ItemStack display;
	
	public ChemPropertiesJEIRecipe(Property properties)
	{
		display = CompoundItem.getCompoundStack(properties.formula, 1);
	}
	
	@Override
	public void drawInfo(@Nonnull Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY)
	{
		
	}
}
