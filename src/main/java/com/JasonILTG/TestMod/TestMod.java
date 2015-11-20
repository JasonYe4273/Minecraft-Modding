package com.JasonILTG.TestMod;

import com.JasonILTG.TestMod.init.TestCrafting;
import com.JasonILTG.TestMod.init.TestItems;
import com.JasonILTG.TestMod.proxy.TestCommonProxy;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = TestReference.MOD_ID, name = TestReference.MOD_NAME, version = TestReference.VERSION)
public class TestMod
{	 
	@SidedProxy(clientSide = TestReference.CLIENT_PROXY_CLASS, serverSide = TestReference.SERVER_PROXY_CLASS)
	public static TestCommonProxy proxy;
	
	@EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
    	TestItems.init();
    	TestItems.register();
    }
	
    @EventHandler
    public void init(FMLInitializationEvent event)
    {
    	proxy.registerRenders();
    	TestCrafting.init();
    }
    
    @EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
    	
    }
}
