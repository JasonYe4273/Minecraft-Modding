package com.JasonILTG.ScienceMod.init;

import java.io.File;

import com.JasonILTG.ScienceMod.reference.chemistry.init.ChemReactorRecipeLoader;
import com.JasonILTG.ScienceMod.reference.chemistry.init.CompoundFactory;
import com.JasonILTG.ScienceMod.reference.chemistry.init.ElectrolyzerRecipeLoader;
import com.JasonILTG.ScienceMod.reference.chemistry.init.PropertyLoader;
import com.JasonILTG.ScienceMod.reference.chemistry.init.SolubilityLoader;

/**
 * Init class for all chemistry-related things.
 * 
 * @author JasonILTG and syy1125
 */
public class ChemInit
{
	/**
	 * Initializes all chemistry-related things.
	 */
	public static void init()
	{
		PropertyLoader.init(new File("./config/Science Mod/chemProps.cfg"));
		CompoundFactory.init();
		ScienceModItems.chemInit();
		ElectrolyzerRecipeLoader.init(new File("./config/Science Mod/electrolyzerRecipes.cfg"));
		ChemReactorRecipeLoader.init(new File("./config/Science Mod/chemReactorRecipes.cfg"));
		SolubilityLoader.init(new File("./config/Science Mod/precipitates.cfg"), new File("./config/Science Mod/soluble.cfg"));
	}
}
