package com.JasonILTG.NumbersMod;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import com.JasonILTG.NumbersMod.init.NumbersCrafting;
import com.JasonILTG.NumbersMod.init.NumbersItems;
import com.JasonILTG.NumbersMod.proxy.CommonProxy;

@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME, version = Reference.VERSION)
public class NumbersMod
{
	// Pahimar said it is good to have an untempered mod instance to refer back to.
	@Instance(Reference.MOD_ID)
	public static NumbersMod modInstance;
	
	@SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.SERVER_PROXY_CLASS)
	public static CommonProxy proxy;
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		NumbersItems.init();
		NumbersItems.register();
	}
	
	@EventHandler
	public void init(FMLInitializationEvent event)
	{
		proxy.registerRenders();
		NumbersCrafting.init();
	}
	
	@EventHandler
	public void postInit(FMLPostInitializationEvent event)
	{	
		
	}
}
