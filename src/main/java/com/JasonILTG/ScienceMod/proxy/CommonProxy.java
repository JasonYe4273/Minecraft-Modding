package com.JasonILTG.ScienceMod.proxy;

import net.minecraftforge.common.MinecraftForge;

import com.JasonILTG.ScienceMod.handler.ScienceEventHandler;
import com.JasonILTG.ScienceMod.item.armor.exo.ExoHandler;

/**
 * Common proxy class for client and server to inherit/
 * 
 * @author JasonILTG and syy1125
 */
public class CommonProxy
{
	/**
	 * Initializes the proxy, registering the armor handlers.
	 */
	public void init()
	{
		MinecraftForge.EVENT_BUS.register(new ExoHandler());
		MinecraftForge.EVENT_BUS.register(new ScienceEventHandler());
	}
	
	/**
	 * Overwritten on client side.
	 */
	public void addVariants()
	{	
		
	}
	
	/**
	 * Overwritten on client side.
	 */
	public void registerRenders()
	{	
		
	}
}
