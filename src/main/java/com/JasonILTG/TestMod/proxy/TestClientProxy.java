package com.JasonILTG.TestMod.proxy;

import com.JasonILTG.TestMod.init.TestItems;

public class TestClientProxy extends TestCommonProxy
{
	@Override
	public void registerRenders()
	{
		TestItems.registerRenders();
	}
}
