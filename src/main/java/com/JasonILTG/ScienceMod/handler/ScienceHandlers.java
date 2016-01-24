package com.JasonILTG.ScienceMod.handler;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;

import com.JasonILTG.ScienceMod.handler.item.ArmorHandler;
import com.JasonILTG.ScienceMod.handler.item.ExoHandler;
import com.JasonILTG.ScienceMod.handler.manager.ManagerHandler;

public class ScienceHandlers
{
	public static void init()
	{
		MinecraftForge.EVENT_BUS.register(ExoHandler.instance);
		MinecraftForge.EVENT_BUS.register(ArmorHandler.instance);
		MinecraftForge.EVENT_BUS.register(ScienceEventHandler.instance);
		MinecraftForge.EVENT_BUS.register(ManagerHandler.instance);
		
		FMLCommonHandler.instance().bus().register(ExoHandler.instance);
		FMLCommonHandler.instance().bus().register(ArmorHandler.instance);
		FMLCommonHandler.instance().bus().register(ScienceEventHandler.instance);
		FMLCommonHandler.instance().bus().register(ManagerHandler.instance);
	}
}
