package com.JasonILTG.ScienceMod;

import java.io.File;

import com.JasonILTG.ScienceMod.compat.ICompatibility;
import com.JasonILTG.ScienceMod.creativetabs.CreativeTabCompounds;
import com.JasonILTG.ScienceMod.creativetabs.CreativeTabElements;
import com.JasonILTG.ScienceMod.creativetabs.CreativeTabMachines;
import com.JasonILTG.ScienceMod.creativetabs.CreativeTabMiscScience;
import com.JasonILTG.ScienceMod.creativetabs.CreativeTabTools;
import com.JasonILTG.ScienceMod.handler.config.ConfigHandler;
import com.JasonILTG.ScienceMod.init.ChemInit;
import com.JasonILTG.ScienceMod.init.ModCompatibility;
import com.JasonILTG.ScienceMod.init.OreDictInit;
import com.JasonILTG.ScienceMod.init.ScienceCrafting;
import com.JasonILTG.ScienceMod.init.ScienceModBlocks;
import com.JasonILTG.ScienceMod.init.ScienceModEntities;
import com.JasonILTG.ScienceMod.init.ScienceModItems;
import com.JasonILTG.ScienceMod.init.ScienceModTileEntities;
import com.JasonILTG.ScienceMod.messages.MixerSolutionMessage;
import com.JasonILTG.ScienceMod.messages.MixerSolutionMessageHandler;
import com.JasonILTG.ScienceMod.messages.SolarPanelModeMessage;
import com.JasonILTG.ScienceMod.messages.SolarPanelModeMessageHandler;
import com.JasonILTG.ScienceMod.messages.TEDoProgressMessage;
import com.JasonILTG.ScienceMod.messages.TEDoProgressMessageHandler;
import com.JasonILTG.ScienceMod.messages.TEInfoRequestMessage;
import com.JasonILTG.ScienceMod.messages.TEInfoRequestMessageHandler;
import com.JasonILTG.ScienceMod.messages.TEMaxProgressMessage;
import com.JasonILTG.ScienceMod.messages.TEMaxProgressMessageHandler;
import com.JasonILTG.ScienceMod.messages.TEPowerMessage;
import com.JasonILTG.ScienceMod.messages.TEPowerMessageHandler;
import com.JasonILTG.ScienceMod.messages.TEProgressIncMessage;
import com.JasonILTG.ScienceMod.messages.TEProgressIncMessageHandler;
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
import com.JasonILTG.ScienceMod.reference.chemistry.compounds.CommonCompounds;
import com.JasonILTG.ScienceMod.reference.chemistry.init.PropertyLoader;

import net.minecraft.creativetab.CreativeTabs;
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
 * The central class for ScienceMod.
 * 
 * @author JasonILTG and syy1125
 */
@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME, version = Reference.VERSION)
public class ScienceMod
{
	/** Instance of the mod */
	@Instance(Reference.MOD_ID)
	public static ScienceMod modInstance;
	
	// Proxy
	@SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.SERVER_PROXY_CLASS)
	public static CommonProxy proxy;
	
	// Creative tabs
	public static final CreativeTabs tabElements = new CreativeTabElements(CreativeTabs.getNextID(), "elements");
	public static final CreativeTabs tabCompounds = new CreativeTabCompounds(CreativeTabs.getNextID(), "compounds");
	public static final CreativeTabs tabMachines = new CreativeTabMachines(CreativeTabs.getNextID(), "machines");
	public static final CreativeTabs tabMiscScience = new CreativeTabMiscScience(CreativeTabs.getNextID(), "misc");
	public static final CreativeTabs tabTools = new CreativeTabTools(CreativeTabs.getNextID(), "tools");
	
	// SimpleNetworkWrapper for messages
	public static SimpleNetworkWrapper snw;
	
	/**
	 * Called on pre-initialization of the mod.
	 * 
	 * @param event The pre-initialization event
	 */
	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		File configFolder = new File(event.getModConfigurationDirectory(), Reference.MOD_NAME + "\\");
		ConfigHandler.init(new File(configFolder, Reference.MOD_ID + ".cfg"));
		PropertyLoader.init(new File(configFolder, PropertyLoader.PROPERTY_FILE_NAME + ".cfg"));
		
		// Registering messages; TODO need to move this to proxies at some point
		snw = NetworkRegistry.INSTANCE.newSimpleChannel(Reference.MOD_ID);
		snw.registerMessage(TETankMessageHandler.class, TETankMessage.class, Messages.TE_TANK_MESSAGE_ID, Side.CLIENT);
		snw.registerMessage(TEResetProgressMessageHandler.class, TEResetProgressMessage.class, Messages.TE_RESET_PROGRESS_MESSAGE_ID, Side.CLIENT);
		snw.registerMessage(TEDoProgressMessageHandler.class, TEDoProgressMessage.class, Messages.TE_DO_PROGRESS_MESSAGE_ID, Side.CLIENT);
		snw.registerMessage(TEProgressMessageHandler.class, TEProgressMessage.class, Messages.TE_PROGRESS_MESSAGE_ID, Side.CLIENT);
		snw.registerMessage(TEProgressIncMessageHandler.class, TEProgressIncMessage.class, Messages.TE_PROGRESS_INC_MESSAGE_ID, Side.CLIENT);
		snw.registerMessage(TEMaxProgressMessageHandler.class, TEMaxProgressMessage.class, Messages.TE_MAX_PROGRESS_MESSAGE_ID, Side.CLIENT);
		snw.registerMessage(MixerSolutionMessageHandler.class, MixerSolutionMessage.class, Messages.MIXER_SOLUTION_MESSAGE_ID, Side.CLIENT);
		snw.registerMessage(SolarPanelModeMessageHandler.class, SolarPanelModeMessage.class, Messages.SOLAR_PANEL_MODE_MESSAGE_ID, Side.CLIENT);
		snw.registerMessage(TEPowerMessageHandler.class, TEPowerMessage.class, Messages.TE_POWER_MESSAGE_ID, Side.CLIENT);
		snw.registerMessage(TETempMessageHandler.class, TETempMessage.class, Messages.TE_TEMP_MESSAGE_ID, Side.CLIENT);
		snw.registerMessage(TEInfoRequestMessageHandler.class, TEInfoRequestMessage.class, Messages.TE_INFO_REQUEST_MESSAGE_ID, Side.SERVER);
		
		// Initialize chemistry info, items, blocks, entities, and tile entities
		ChemInit.init();
		ScienceModItems.init();
		CommonCompounds.init();
		ScienceModBlocks.init();
		OreDictInit.addOreDict();
		ScienceModEntities.init();
		ScienceModTileEntities.init();
		ModCompatibility.registerModCompat();
		ModCompatibility.loadCompat(ICompatibility.InitializationPhase.PRE_INIT);
	}
	
	/**
	 * Called on initialization of the mod.
	 * 
	 * @param event The initialization event
	 */
	@EventHandler
	public void init(FMLInitializationEvent event)
	{
		// Register renders, item variants, ore dictionary, and crafting recipes
		
		proxy.init();
		proxy.addVariants();
		proxy.registerRenders();
		OreDictInit.initOreMap();
		ScienceCrafting.init();
		ModCompatibility.loadCompat(ICompatibility.InitializationPhase.INIT);
	}
	
	/**
	 * Called on post-initialization of the mod.
	 * 
	 * @param event the post-initialization event
	 */
	@EventHandler
	public void postInit(FMLPostInitializationEvent event)
	{
		ModCompatibility.loadCompat(ICompatibility.InitializationPhase.POST_INIT);
	}
}
