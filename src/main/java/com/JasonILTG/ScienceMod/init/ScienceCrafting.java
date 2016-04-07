package com.JasonILTG.ScienceMod.init;

import com.JasonILTG.ScienceMod.init.crafting.ChemCrafting;
import com.JasonILTG.ScienceMod.init.crafting.ComponentCrafting;
import com.JasonILTG.ScienceMod.init.crafting.MachineCrafting;
import com.JasonILTG.ScienceMod.init.crafting.MiscCrafting;

/**
 * Init class for all ScienceMod crafting recipes.
 * 
 * @author JasonILTG and syy1125
 */
public class ScienceCrafting
{
	/**
	 * Initializes all ScienceMod crafting recipes.
	 */
	public static void init()
	{
		MachineCrafting.init();
		ComponentCrafting.init();
		MiscCrafting.init();
		ChemCrafting.init();
	}
}
