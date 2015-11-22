package com.JasonILTG.ScienceMod.init;

import com.JasonILTG.ScienceMod.init.crafting.CompoundCrafting;
import com.JasonILTG.ScienceMod.init.crafting.ElementCrafting;
import com.JasonILTG.ScienceMod.init.crafting.MachineCrafting;
import com.JasonILTG.ScienceMod.init.crafting.MiscCrafting;

public class ScienceCrafting
{
	public static void init()
	{
		ElementCrafting.init();
		MachineCrafting.init();
		MiscCrafting.init();
		CompoundCrafting.init();
	}
}
