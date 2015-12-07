package com.JasonILTG.ScienceMod.init;

import com.JasonILTG.ScienceMod.block.general.BlockScience;
import com.JasonILTG.ScienceMod.block.machines.AirExtractor;
import com.JasonILTG.ScienceMod.block.machines.Centrifuge;
import com.JasonILTG.ScienceMod.block.machines.ChemReactor;
import com.JasonILTG.ScienceMod.block.machines.Condenser;
import com.JasonILTG.ScienceMod.block.machines.Distiller;
import com.JasonILTG.ScienceMod.block.machines.Electrolyzer;
import com.JasonILTG.ScienceMod.block.machines.Mixer;
import com.JasonILTG.ScienceMod.reference.Names;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;

/**
 * Init class for all ScienceMod blocks.
 * 
 * @author JasonILTG and syy1125
 */
public class ScienceModBlocks
{
	// Machines
	public static final BlockScience electrolyzer = new Electrolyzer();
	public static final BlockScience air_extractor = new AirExtractor();
	public static final BlockScience condenser = new Condenser();
	public static final BlockScience mixer = new Mixer();
	public static final BlockScience centrifuge = new Centrifuge();
	public static final BlockScience distiller = new Distiller();
	public static final BlockScience chemical_reactor = new ChemReactor();
	
	/**
	 * Initializes all ScienceMod blocks.
	 */
	public static void init()
	{
		register();
	}
	
	/**
	 * Registers all ScienceMod blocks.
	 */
	private static void register()
	{
		GameRegistry.registerBlock(electrolyzer, Names.Blocks.Machine.MACHINE_ELECTROLYZER);
		GameRegistry.registerBlock(air_extractor, Names.Blocks.Machine.MACHINE_AIR_EXTRACTOR);
		GameRegistry.registerBlock(condenser, Names.Blocks.Machine.MACHINE_CONDENSER);
		GameRegistry.registerBlock(mixer, Names.Blocks.Machine.MACHINE_MIXER);
		GameRegistry.registerBlock(centrifuge, Names.Blocks.Machine.MACHINE_CENTRIFUGE);
		GameRegistry.registerBlock(distiller, Names.Blocks.Machine.MACHINE_DISTILLER);
		GameRegistry.registerBlock(chemical_reactor, Names.Blocks.Machine.MACHINE_CHEM_REACTOR);
	}
	
	/**
	 * Registers the renders of all ScienceMod blocks.
	 */
	public static void registerRenders()
	{
		registerRender(electrolyzer);
		registerRender(air_extractor);
		registerRender(condenser);
		registerRender(mixer);
		registerRender(centrifuge);
		registerRender(distiller);
		registerRender(chemical_reactor);
	}
	
	/**
	 * Registers the render of the block.
	 * 
	 * @param block The block
	 */
	private static void registerRender(BlockScience block)
	{
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(Item.getItemFromBlock(block), 0,
				new ModelResourceLocation(block.getUnlocalizedName().substring(5), "inventory"));
	}
}
