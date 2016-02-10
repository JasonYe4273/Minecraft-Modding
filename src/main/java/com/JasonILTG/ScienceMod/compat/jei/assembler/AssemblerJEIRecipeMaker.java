package com.JasonILTG.ScienceMod.compat.jei.assembler;

import java.util.ArrayList;
import java.util.List;

import com.JasonILTG.ScienceMod.tileentity.component.TEAssembler;
import com.JasonILTG.ScienceMod.tileentity.component.TEAssembler.AssemblerRecipe;

/**
 * Generates all JEI assembler recipes.
 * 
 * @author JasonILTG and syy1125
 */
public class AssemblerJEIRecipeMaker
{
	public static List<AssemblerJEIRecipe> generate()
	{
		ArrayList<AssemblerJEIRecipe> jeiRecipes = new ArrayList<AssemblerJEIRecipe>();
		for (AssemblerRecipe recipe : TEAssembler.AssemblerRecipe.values())
		{
			jeiRecipes.add(new AssemblerJEIRecipe(recipe));
		}
		
		return jeiRecipes;
	}
}
