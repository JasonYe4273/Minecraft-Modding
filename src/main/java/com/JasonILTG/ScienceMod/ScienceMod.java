package com.JasonILTG.ScienceMod;

import com.JasonILTG.ScienceMod.init.ScienceCrafting;
import com.JasonILTG.ScienceMod.init.ScienceItems;
import com.JasonILTG.ScienceMod.init.ElementItems;
import com.JasonILTG.ScienceMod.proxy.ScienceCommonProxy;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = ScienceReference.MOD_ID, name = ScienceReference.MOD_NAME, version = ScienceReference.VERSION)
public class ScienceMod
{	 
	@SidedProxy(clientSide = ScienceReference.CLIENT_PROXY_CLASS, serverSide = ScienceReference.SERVER_PROXY_CLASS)
	public static ScienceCommonProxy proxy;
	
	@EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
    	ScienceItems.init();
    	ScienceItems.register();
    	ElementItems.init();
    	ElementItems.register();
    }
	
    @EventHandler
    public void init(FMLInitializationEvent event)
    {
    	proxy.registerRenders();
    	ScienceCrafting.init();
    }
    
    @EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
    	
    }
}
