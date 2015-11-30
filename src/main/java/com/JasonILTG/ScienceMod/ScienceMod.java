package com.JasonILTG.ScienceMod;

import com.JasonILTG.ScienceMod.handler.config.ConfigHandler;
import com.JasonILTG.ScienceMod.init.ScienceCrafting;
import com.JasonILTG.ScienceMod.init.ScienceModBlocks;
import com.JasonILTG.ScienceMod.init.ScienceModEntities;
import com.JasonILTG.ScienceMod.init.ScienceModItems;
import com.JasonILTG.ScienceMod.init.ScienceModTileEntities;
import com.JasonILTG.ScienceMod.messages.MixerSolutionMessage;
import com.JasonILTG.ScienceMod.messages.MixerSolutionMessageHandler;
import com.JasonILTG.ScienceMod.messages.TEMaxProgressMessage;
import com.JasonILTG.ScienceMod.messages.TEMaxProgressMessageHandler;
import com.JasonILTG.ScienceMod.messages.TEProgressMessage;
import com.JasonILTG.ScienceMod.messages.TEProgressMessageHandler;
import com.JasonILTG.ScienceMod.messages.TETankMessage;
import com.JasonILTG.ScienceMod.messages.TETankMessageHandler;
import com.JasonILTG.ScienceMod.proxy.CommonProxy;
import com.JasonILTG.ScienceMod.reference.Messages;
import com.JasonILTG.ScienceMod.reference.Reference;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME, version = Reference.VERSION)
public class ScienceMod
{
	//
	@Instance(Reference.MOD_ID)
	public static ScienceMod modInstance;
	
	@SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.SERVER_PROXY_CLASS)
	public static CommonProxy proxy;
	
	public static SimpleNetworkWrapper snw;
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		ConfigHandler.init(event.getSuggestedConfigurationFile());
		snw = NetworkRegistry.INSTANCE.newSimpleChannel(Reference.MOD_ID); 
		snw.registerMessage(TETankMessageHandler.class, TETankMessage.class, Messages.TE_TANK_MESSAGE_ID, Side.CLIENT);
		snw.registerMessage(TEProgressMessageHandler.class, TEProgressMessage.class, Messages.TE_PROGRESS_MESSAGE_ID, Side.CLIENT);
		snw.registerMessage(TEMaxProgressMessageHandler.class, TEMaxProgressMessage.class, Messages.TE_MAX_PROGRESS_MESSAGE_ID, Side.CLIENT);
		snw.registerMessage(MixerSolutionMessageHandler.class, MixerSolutionMessage.class, Messages.MIXER_SOLUTION_MESSAGE_ID, Side.CLIENT);
		ScienceModItems.init();
		ScienceModBlocks.init();
		ScienceModEntities.init();
		ScienceModTileEntities.init();
	}
	
	@EventHandler
	public void init(FMLInitializationEvent event)
	{
		proxy.init();
		proxy.addVariants();
		proxy.registerRenders();
		ScienceCrafting.init();
	}
	
	@EventHandler
	public void postInit(FMLPostInitializationEvent event)
	{	
		
	}
}
