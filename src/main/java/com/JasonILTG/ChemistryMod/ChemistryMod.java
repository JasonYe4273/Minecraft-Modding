package com.JasonILTG.ChemistryMod;

import com.JasonILTG.ChemistryMod.init.ChemistryCrafting;
import com.JasonILTG.ChemistryMod.init.ChemistryItems;
import com.JasonILTG.ChemistryMod.init.ElementItems;
import com.JasonILTG.ChemistryMod.proxy.ChemistryCommonProxy;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = ChemistryReference.MOD_ID, name = ChemistryReference.MOD_NAME, version = ChemistryReference.VERSION)
public class ChemistryMod
{	 
	@SidedProxy(clientSide = ChemistryReference.CLIENT_PROXY_CLASS, serverSide = ChemistryReference.SERVER_PROXY_CLASS)
	public static ChemistryCommonProxy proxy;
	
	@EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
    	ChemistryItems.init();
    	ChemistryItems.register();
    	ElementItems.init();
    	ElementItems.register();
    }
	
    @EventHandler
    public void init(FMLInitializationEvent event)
    {
    	proxy.registerRenders();
    	ChemistryCrafting.init();
    }
    
    @EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
    	
    }
}
