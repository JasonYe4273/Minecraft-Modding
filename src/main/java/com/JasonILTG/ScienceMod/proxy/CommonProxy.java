package com.JasonILTG.ScienceMod.proxy;

import com.JasonILTG.ScienceMod.handler.ScienceEventHandler;
import com.JasonILTG.ScienceMod.handler.item.ExoHandler;
import com.JasonILTG.ScienceMod.handler.manager.ManagerHandler;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;

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
		
		FMLCommonHandler.instance().bus().register(ManagerHandler.instance);
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
