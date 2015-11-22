package com.JasonILTG.ScienceMod.proxy;

import com.JasonILTG.ScienceMod.ScienceMod;
import com.JasonILTG.ScienceMod.gui.ScienceGUIHandler;

import net.minecraftforge.fml.common.network.NetworkRegistry;

public class CommonProxy implements IProxy
{
	public void init()
	{
		NetworkRegistry.INSTANCE.registerGuiHandler(ScienceMod.modInstance, new ScienceGUIHandler());
	}
	
	public void addVariants()
	{
		
	}
	
	public void registerRenders()
	{	
		
	}
}
