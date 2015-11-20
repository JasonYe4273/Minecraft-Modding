package com.JasonILTG.Template;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import com.JasonILTG.Template.reference.Reference;

@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME, version = Reference.VERSION)
public class ModTemplate
{
	@Mod.Instance(Reference.MOD_ID)
	public static ModTemplate modInstance;
	
	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		// Pre-initialization
	}
	
	@Mod.EventHandler
	public void init(FMLInitializationEvent event)
	{
		// Initialization
	}
	
	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent event)
	{
		// Post-initialization
	}
	
}
