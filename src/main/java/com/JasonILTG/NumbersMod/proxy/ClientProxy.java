package com.JasonILTG.NumbersMod.proxy;

import com.JasonILTG.NumbersMod.init.NumbersItems;

public class ClientProxy extends CommonProxy
{
	@Override
	public void registerRenders()
	{
		NumbersItems.registerRenders();
	}
}
