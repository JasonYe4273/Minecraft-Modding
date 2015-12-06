package com.JasonILTG.ScienceMod.proxy;

import com.JasonILTG.ScienceMod.item.armor.exo.ExoHandler;

import net.minecraftforge.common.MinecraftForge;

/**
 * Common proxy class for client and server to inherit
 * 
 * @author JasonILTG and syy1125
 */
public class CommonProxy
{
	/**
	 * Initializes the proxy, registering the armor handlers
	 */
	public void init()
	{
		MinecraftForge.EVENT_BUS.register(new ExoHandler());
	}
	
	/**
	 * Overwritten on client side
	 */
	public void addVariants()
	{	
		
	}
	
	/**
	 * Overwritten on client side
	 */
	public void registerRenders()
	{	
		
	}
}
