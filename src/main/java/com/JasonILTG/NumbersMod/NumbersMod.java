package com.JasonILTG.NumbersMod;

import com.JasonILTG.NumbersMod.init.NumbersCrafting;
import com.JasonILTG.NumbersMod.init.NumbersItems;
import com.JasonILTG.NumbersMod.proxy.CommonProxy;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME, version = Reference.VERSION)
public class NumbersMod
{	 
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
