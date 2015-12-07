package com.JasonILTG.ScienceMod.proxy;

import com.JasonILTG.ScienceMod.ScienceMod;
import com.JasonILTG.ScienceMod.gui.general.ScienceGUIHandler;
import com.JasonILTG.ScienceMod.init.ScienceModBlocks;
import com.JasonILTG.ScienceMod.init.ScienceModEntities;
import com.JasonILTG.ScienceMod.init.ScienceModItems;

import net.minecraftforge.fml.common.network.NetworkRegistry;

/**
 * Proxy for the client side.
 * 
 * @author JasonILTG and syy1125
 */
public class ClientProxy extends CommonProxy
{
	/**
	 * Initializes the proxy, registering GUIHandler.
	 */
	@Override
	public void init()
	{
		super.init();
		NetworkRegistry.INSTANCE.registerGuiHandler(ScienceMod.modInstance, new ScienceGUIHandler());
	}
	
	/**
	 * Add variants to the items that have them.
	 */
	@Override
	public void addVariants()
	{
		ScienceModItems.addVariants();
	}
	
	/**
	 * Register the renders of all items, blocks, and entities.
	 */
	@Override
	public void registerRenders()
	{
		ScienceModItems.registerRenders();
		ScienceModBlocks.registerRenders();
		ScienceModEntities.registerRenders();
	}
}
