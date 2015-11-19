package JasonILTG.TestMod.proxy;

import JasonILTG.TestMod.init.TestItems;

public class ClientProxy extends CommonProxy
{
	@Override
	public void registerRenders()
	{
		TestItems.registerRenders();
	}
}
