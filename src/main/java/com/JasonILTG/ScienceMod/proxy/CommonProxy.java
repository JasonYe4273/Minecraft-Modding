package com.JasonILTG.ScienceMod.proxy;

import net.minecraftforge.common.MinecraftForge;

import com.JasonILTG.ScienceMod.item.armor.exo.ExoHandler;

public class CommonProxy implements IProxy
{
	public void init()
	{
		MinecraftForge.EVENT_BUS.register(new ExoHandler());
	}
	
	public void addVariants()
	{	
		
	}
	
	public void registerRenders()
	{	
		
	}
}
