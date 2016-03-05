package com.JasonILTG.ScienceMod.handler;

import com.JasonILTG.ScienceMod.handler.item.ArmorHandler;
import com.JasonILTG.ScienceMod.handler.item.ExoHandler;
import com.JasonILTG.ScienceMod.handler.manager.ManagerHandler;

import net.minecraftforge.common.MinecraftForge;

public class ScienceHandlers
{
	public static void init()
	{
		MinecraftForge.EVENT_BUS.register(ExoHandler.instance);
		MinecraftForge.EVENT_BUS.register(ArmorHandler.instance);
		MinecraftForge.EVENT_BUS.register(ScienceEventHandler.instance);
		MinecraftForge.EVENT_BUS.register(ManagerHandler.instance);
	}
}
