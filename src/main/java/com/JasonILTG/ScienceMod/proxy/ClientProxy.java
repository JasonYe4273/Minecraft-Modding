package com.JasonILTG.ScienceMod.proxy;

import com.JasonILTG.ScienceMod.init.ScienceItems;
import com.JasonILTG.ScienceMod.init.ScienceModBlocks;

public class ClientProxy extends CommonProxy
{
	@Override
	public void registerRenders()
	{
		ScienceItems.registerRenders();
		ScienceModBlocks.registerRenders();
	}
}
