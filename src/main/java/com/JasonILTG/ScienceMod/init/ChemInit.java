package com.JasonILTG.ScienceMod.init;

import java.io.File;

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
		PropertyLoader.init(new File("./config/Science Mod/chemProps.cfg"));
		CompoundFactory.init();
		ChemReactorRecipeLoader.init(new File("./config/Science Mod/chemReactorRecipes.cfg"));
		SolubilityLoader.init(new File("./config/Science Mod/precipitates.cfg"), new File("./config/Science Mod/soluble.cfg"));
	}
}
