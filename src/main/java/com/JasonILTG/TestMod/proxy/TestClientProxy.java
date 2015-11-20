package com.JasonILTG.TestMod.proxy;

import com.JasonILTG.TestMod.init.TestItems;

public class ClientProxy extends CommonProxy
{
	@Override
	public void registerRenders()
	{
		TestItems.registerRenders();
	}
}
