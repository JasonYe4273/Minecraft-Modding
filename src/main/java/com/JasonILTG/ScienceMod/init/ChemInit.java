package com.JasonILTG.ScienceMod.init;

import com.JasonILTG.ScienceMod.reference.chemistry.loaders.ChemReactorRecipeLoader;
import com.JasonILTG.ScienceMod.reference.chemistry.loaders.CompoundFactory;
import com.JasonILTG.ScienceMod.reference.chemistry.loaders.PropertyLoader;
import com.JasonILTG.ScienceMod.reference.chemistry.loaders.SolubilityLoader;

/**
 * 
 * @author JasonILTG and syy1125
 */
public class ChemInit
{
	public static void init()
	{
		CompoundFactory.init();
		PropertyLoader.init(null);
		ChemReactorRecipeLoader.init(null);
		SolubilityLoader.init(null, null);
	}
}
