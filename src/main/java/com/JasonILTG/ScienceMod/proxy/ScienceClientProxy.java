package com.JasonILTG.ScienceMod.proxy;

import com.JasonILTG.ScienceMod.init.ScienceItems;

public class ScienceClientProxy extends ScienceCommonProxy
{
	@Override
	public void registerRenders()
	{
		ScienceItems.registerRenders();
	}
}
