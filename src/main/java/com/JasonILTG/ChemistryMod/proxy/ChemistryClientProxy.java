package com.JasonILTG.ChemistryMod.proxy;

import com.JasonILTG.ChemistryMod.init.ChemistryItems;

public class ChemistryClientProxy extends ChemistryCommonProxy
{
	@Override
	public void registerRenders()
	{
		ChemistryItems.registerRenders();
	}
}
