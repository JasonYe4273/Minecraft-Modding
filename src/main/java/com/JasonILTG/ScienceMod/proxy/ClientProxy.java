package com.JasonILTG.ScienceMod.proxy;

import com.JasonILTG.ScienceMod.init.ElementItems;
import com.JasonILTG.ScienceMod.init.ScienceBlocks;

public class ClientProxy extends CommonProxy
{
	@Override
	public void registerRenders()
	{
		ElementItems.registerRenders();
		ScienceBlocks.registerRenders();
	}
}
