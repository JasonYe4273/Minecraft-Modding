package com.JasonILTG.ScienceMod.proxy;

import com.JasonILTG.ScienceMod.init.ScienceItems;
import com.JasonILTG.ScienceMod.init.ScienceBlocks;

public class ClientProxy extends CommonProxy
{
	@Override
	public void registerRenders()
	{
		ScienceItems.registerRenders();
		ScienceBlocks.registerRenders();
	}
}
