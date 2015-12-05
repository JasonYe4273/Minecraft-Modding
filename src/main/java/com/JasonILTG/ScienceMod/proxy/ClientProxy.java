package com.JasonILTG.ScienceMod.proxy;

import net.minecraftforge.fml.common.network.NetworkRegistry;

import com.JasonILTG.ScienceMod.ScienceMod;
import com.JasonILTG.ScienceMod.gui.general.ScienceGUIHandler;
import com.JasonILTG.ScienceMod.init.ScienceModBlocks;
import com.JasonILTG.ScienceMod.init.ScienceModEntities;
import com.JasonILTG.ScienceMod.init.ScienceModItems;

public class ClientProxy extends CommonProxy
{
	@Override
	public void init()
	{
		super.init();
		NetworkRegistry.INSTANCE.registerGuiHandler(ScienceMod.modInstance, new ScienceGUIHandler());
	}
	
	@Override
	public void addVariants()
	{
		ScienceModItems.addVariants();
	}
	
	@Override
	public void registerRenders()
	{
		ScienceModItems.registerRenders();
		ScienceModBlocks.registerRenders();
		ScienceModEntities.registerRenders();
	}
}
