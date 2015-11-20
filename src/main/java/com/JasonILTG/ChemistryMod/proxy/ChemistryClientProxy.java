package com.JasonILTG.ChemistryMod.proxy;

import com.JasonILTG.ChemistryMod.init.ChemistryItems;
import com.JasonILTG.ChemistryMod.init.ElementItems;

public class ChemistryClientProxy extends ChemistryCommonProxy
{
	@Override
	public void registerRenders()
	{
		ChemistryItems.registerRenders();
		ElementItems.registerRenders();
	}
}
