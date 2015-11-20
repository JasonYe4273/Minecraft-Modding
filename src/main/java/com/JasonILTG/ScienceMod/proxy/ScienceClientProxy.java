package com.JasonILTG.ScienceMod.proxy;

import com.JasonILTG.ScienceMod.init.ScienceItems;
import com.JasonILTG.ScienceMod.init.ElementItems;

public class ScienceClientProxy extends ScienceCommonProxy
{
	@Override
	public void registerRenders()
	{
		ScienceItems.registerRenders();
	}
}
