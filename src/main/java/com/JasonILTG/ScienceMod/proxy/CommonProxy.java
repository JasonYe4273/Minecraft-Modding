package com.JasonILTG.ScienceMod.proxy;

import com.JasonILTG.ScienceMod.handler.ScienceHandlers;

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
		ScienceHandlers.init();
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
