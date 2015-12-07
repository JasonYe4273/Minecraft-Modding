package com.JasonILTG.ScienceMod.init;

import com.JasonILTG.ScienceMod.init.crafting.CompoundCrafting;
import com.JasonILTG.ScienceMod.init.crafting.ElementCrafting;
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
		ElementCrafting.init();
		MachineCrafting.init();
		MiscCrafting.init();
		CompoundCrafting.init();
	}
}
