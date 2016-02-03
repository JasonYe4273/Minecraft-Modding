package com.JasonILTG.ScienceMod.init;

import com.JasonILTG.ScienceMod.block.component.Assembler;
import com.JasonILTG.ScienceMod.block.component.wire.Wire;
import com.JasonILTG.ScienceMod.block.general.BlockScience;
import com.JasonILTG.ScienceMod.block.generators.Combuster;
import com.JasonILTG.ScienceMod.block.generators.SolarPanel;
import com.JasonILTG.ScienceMod.block.machines.AirExtractor;
import com.JasonILTG.ScienceMod.block.machines.Centrifuge;
import com.JasonILTG.ScienceMod.block.machines.ChemReactor;
import com.JasonILTG.ScienceMod.block.machines.Condenser;
import com.JasonILTG.ScienceMod.block.machines.Electrolyzer;
import com.JasonILTG.ScienceMod.block.machines.Mixer;
import com.JasonILTG.ScienceMod.block.misc.Drain;
import com.JasonILTG.ScienceMod.itemblock.component.WireItemBlock;
import com.JasonILTG.ScienceMod.itemblock.generators.CombusterItemBlock;
import com.JasonILTG.ScienceMod.itemblock.generators.SolarPanelItemBlock;
import com.JasonILTG.ScienceMod.itemblock.machines.AirExtractorItemBlock;
import com.JasonILTG.ScienceMod.itemblock.machines.ChemReactorItemBlock;
import com.JasonILTG.ScienceMod.itemblock.machines.CondenserItemBlock;
import com.JasonILTG.ScienceMod.itemblock.machines.ElectrolyzerItemBlock;
import com.JasonILTG.ScienceMod.itemblock.machines.MixerItemBlock;
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
	public static final BlockScience chemical_reactor = new ChemReactor();
	
	// Generators
	public static final BlockScience combuster = new Combuster();
	public static final BlockScience solar_panel = new SolarPanel();
	
	// Power
	public static final BlockScience wire = new Wire();
	
	// Component
	public static final BlockScience assembler = new Assembler();
	
	// Misc
	public static final BlockScience drain = new Drain();
	
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
		GameRegistry.registerBlock(electrolyzer, ElectrolyzerItemBlock.class, Names.Blocks.Machine.MACHINE_ELECTROLYZER);
		GameRegistry.registerBlock(air_extractor, AirExtractorItemBlock.class, Names.Blocks.Machine.MACHINE_AIR_EXTRACTOR);
		GameRegistry.registerBlock(condenser, CondenserItemBlock.class, Names.Blocks.Machine.MACHINE_CONDENSER);
		GameRegistry.registerBlock(mixer, MixerItemBlock.class, Names.Blocks.Machine.MACHINE_MIXER);
		GameRegistry.registerBlock(centrifuge, Names.Blocks.Machine.MACHINE_CENTRIFUGE);
		GameRegistry.registerBlock(chemical_reactor, ChemReactorItemBlock.class, Names.Blocks.Machine.MACHINE_CHEM_REACTOR);
		
		GameRegistry.registerBlock(combuster, CombusterItemBlock.class, Names.Blocks.Generator.GENERATOR_COMBUSTER);
		GameRegistry.registerBlock(solar_panel, SolarPanelItemBlock.class, Names.Blocks.Generator.GENERATOR_SOLAR_PANEL);
		
		GameRegistry.registerBlock(wire, WireItemBlock.class, Names.Blocks.Power.WIRE);
		
		GameRegistry.registerBlock(assembler, Names.Blocks.Component.ASSEMBLER);
		
		GameRegistry.registerBlock(drain, Names.Blocks.Misc.DRAIN);
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
		registerRender(chemical_reactor);
		
		registerRender(combuster);
		registerRender(solar_panel);
		
		registerRender(wire);
		
		registerRender(assembler);
		
		registerRender(drain);
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
