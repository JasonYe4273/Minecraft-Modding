package com.JasonILTG.ScienceMod;

import com.JasonILTG.ScienceMod.init.ScienceCrafting;
import com.JasonILTG.ScienceMod.init.ScienceItems;
import com.JasonILTG.ScienceMod.proxy.ScienceCommonProxy;
import com.JasonILTG.ScienceMod.references.Reference;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME, version = Reference.VERSION)
public class ScienceMod
{	 
	@SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.SERVER_PROXY_CLASS)
	public static ScienceCommonProxy proxy;
	
	@EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
    	ScienceItems.init();
    	ScienceItems.register();
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
