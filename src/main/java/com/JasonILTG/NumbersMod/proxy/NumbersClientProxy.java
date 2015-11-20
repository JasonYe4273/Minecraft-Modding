package com.JasonILTG.NumbersMod.proxy;

import com.JasonILTG.NumbersMod.init.NumbersItems;

public class NumbersClientProxy extends NumbersCommonProxy
{
	@Override
	public void registerRenders()
	{
		NumbersItems.registerRenders();
	}
}
