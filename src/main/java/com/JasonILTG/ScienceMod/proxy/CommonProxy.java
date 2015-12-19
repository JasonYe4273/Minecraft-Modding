package com.JasonILTG.ScienceMod.proxy;

import net.minecraftforge.common.MinecraftForge;

import com.JasonILTG.ScienceMod.handler.ScienceEventHandler;
import com.JasonILTG.ScienceMod.handler.item.ExoHandler;
import com.JasonILTG.ScienceMod.handler.manager.ManagerHandler;

/**
 * Common proxy class for client and server to inherit.
 * 
 * @author JasonILTG and syy1125
 */
public class CommonProxy
{
	/**
	 * Initializes the proxy, registering the handlers.
	 */
	public void init()
	{
		MinecraftForge.EVENT_BUS.register(ExoHandler.instance);
		MinecraftForge.EVENT_BUS.register(ScienceEventHandler.instance);
		MinecraftForge.EVENT_BUS.register(ManagerHandler.instance);
	}
	
	/**
	 * Overridden on client side.
	 */
	public void addVariants()
	{	
		
	}
	
	/**
	 * Overridden on client side.
	 */
	public void registerRenders()
	{	
		
	}
}
