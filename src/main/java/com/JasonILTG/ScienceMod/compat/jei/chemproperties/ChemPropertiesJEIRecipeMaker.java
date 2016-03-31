package com.JasonILTG.ScienceMod.compat.jei.chemproperties;

import java.util.ArrayList;
import java.util.List;

import com.JasonILTG.ScienceMod.reference.chemistry.init.PropertyLoader;
import com.JasonILTG.ScienceMod.reference.chemistry.init.PropertyLoader.Property;

/**
 * Generates all JEI chem properties recipes.
 * 
 * @author JasonILTG and syy1125
 */
public class ChemPropertiesJEIRecipeMaker
{
	public static List<ChemPropertiesJEIRecipe> generate()
	{
		ArrayList<ChemPropertiesJEIRecipe> jeiRecipes = new ArrayList<ChemPropertiesJEIRecipe>();
		for (Property properties : PropertyLoader.getProperties())
		{
			jeiRecipes.add(new ChemPropertiesJEIRecipe(properties));
		}
		return jeiRecipes;
	}
}
