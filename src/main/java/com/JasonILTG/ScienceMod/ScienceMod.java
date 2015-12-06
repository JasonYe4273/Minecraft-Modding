package com.JasonILTG.ScienceMod;

import com.JasonILTG.ScienceMod.handler.config.ConfigHandler;
import com.JasonILTG.ScienceMod.init.ScienceCrafting;
import com.JasonILTG.ScienceMod.init.ScienceModBlocks;
import com.JasonILTG.ScienceMod.init.ScienceModEntities;
import com.JasonILTG.ScienceMod.init.ScienceModItems;
import com.JasonILTG.ScienceMod.init.ScienceModTileEntities;
import com.JasonILTG.ScienceMod.messages.MixerSolutionMessage;
import com.JasonILTG.ScienceMod.messages.MixerSolutionMessageHandler;
import com.JasonILTG.ScienceMod.messages.TEDoProgressMessage;
import com.JasonILTG.ScienceMod.messages.TEDoProgressMessageHandler;
import com.JasonILTG.ScienceMod.messages.TEInfoRequestMessage;
import com.JasonILTG.ScienceMod.messages.TEInfoRequestMessageHandler;
import com.JasonILTG.ScienceMod.messages.TEMaxProgressMessage;
import com.JasonILTG.ScienceMod.messages.TEMaxProgressMessageHandler;
import com.JasonILTG.ScienceMod.messages.TEPowerMessage;
import com.JasonILTG.ScienceMod.messages.TEPowerMessageHandler;
import com.JasonILTG.ScienceMod.messages.TEProgressMessage;
import com.JasonILTG.ScienceMod.messages.TEProgressMessageHandler;
import com.JasonILTG.ScienceMod.messages.TEResetProgressMessage;
import com.JasonILTG.ScienceMod.messages.TEResetProgressMessageHandler;
import com.JasonILTG.ScienceMod.messages.TETankMessage;
import com.JasonILTG.ScienceMod.messages.TETankMessageHandler;
import com.JasonILTG.ScienceMod.messages.TETempMessage;
import com.JasonILTG.ScienceMod.messages.TETempMessageHandler;
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

/**
 * The central class for ScienceMod
 * 
 * @author JasonILTG and syy1125
 */
@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME, version = Reference.VERSION)
public class ScienceMod
{
	/** Instance of the mod*/
	@Instance(Reference.MOD_ID)
	public static ScienceMod modInstance;
	
	// Proxy
	@SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.SERVER_PROXY_CLASS)
	public static CommonProxy proxy;
	
	// SimpleNetworkWrapper for messages
	public static SimpleNetworkWrapper snw;
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		ConfigHandler.init(event.getSuggestedConfigurationFile());
		
		// Registering messages; TODO need to move this to proxies at some point
		snw = NetworkRegistry.INSTANCE.newSimpleChannel(Reference.MOD_ID); 
		snw.registerMessage(TETankMessageHandler.class, TETankMessage.class, Messages.TE_TANK_MESSAGE_ID, Side.CLIENT);
		snw.registerMessage(TEPowerMessageHandler.class, TEPowerMessage.class, Messages.TE_POWER_MESSAGE_ID, Side.CLIENT);
		snw.registerMessage(TETempMessageHandler.class, TETempMessage.class, Messages.TE_TEMP_MESSAGE_ID, Side.CLIENT);
		snw.registerMessage(TEResetProgressMessageHandler.class, TEResetProgressMessage.class, Messages.TE_RESET_PROGRESS_MESSAGE_ID, Side.CLIENT);
		snw.registerMessage(TEDoProgressMessageHandler.class, TEDoProgressMessage.class, Messages.TE_DO_PROGRESS_MESSAGE_ID, Side.CLIENT);
		snw.registerMessage(TEProgressMessageHandler.class, TEProgressMessage.class, Messages.TE_PROGRESS_MESSAGE_ID, Side.CLIENT);
		snw.registerMessage(TEMaxProgressMessageHandler.class, TEMaxProgressMessage.class, Messages.TE_MAX_PROGRESS_MESSAGE_ID, Side.CLIENT);
		snw.registerMessage(MixerSolutionMessageHandler.class, MixerSolutionMessage.class, Messages.MIXER_SOLUTION_MESSAGE_ID, Side.CLIENT);
		snw.registerMessage(TEInfoRequestMessageHandler.class, TEInfoRequestMessage.class, Messages.TE_INFO_REQUEST_MESSAGE_ID, Side.SERVER);
		
		// Initialize items, blocks, entities, and tile entities
		ScienceModItems.init();
		ScienceModBlocks.init();
		ScienceModEntities.init();
		ScienceModTileEntities.init();
	}
	
	@EventHandler
	public void init(FMLInitializationEvent event)
	{
		// Register renders, item variants, and crafting recipes
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
