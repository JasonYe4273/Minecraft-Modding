package com.JasonILTG.ScienceMod.init;

import com.JasonILTG.ScienceMod.reference.Names;
import com.JasonILTG.ScienceMod.tileentity.machines.TEAirExtractor;
import com.JasonILTG.ScienceMod.tileentity.machines.TEChemReactor;
import com.JasonILTG.ScienceMod.tileentity.machines.TECondenser;
import com.JasonILTG.ScienceMod.tileentity.machines.TEElectrolyzer;
import com.JasonILTG.ScienceMod.tileentity.machines.TEMixer;

import net.minecraftforge.fml.common.registry.GameRegistry;

/**
 * Init class for tile entities in ScienceMod.
 * 
 * @author JasonILTG and syy1125
 */
public class ScienceModTileEntities
{
	/**
	 * Registers all tile entities in ScienceMod.
	 */
	public static void init()
	{
		// Machines
		GameRegistry.registerTileEntity(TEElectrolyzer.class, Names.Tiles.Machine.ELECTROLYZER);
		GameRegistry.registerTileEntity(TEAirExtractor.class, Names.Tiles.Machine.AIR_EXTRACTOR);
		GameRegistry.registerTileEntity(TECondenser.class, Names.Tiles.Machine.CONDENSER);
		GameRegistry.registerTileEntity(TEMixer.class, Names.Tiles.Machine.MIXER);
		GameRegistry.registerTileEntity(TEMixer.class, Names.Tiles.Machine.CENTRIFUGE);
		GameRegistry.registerTileEntity(TEMixer.class, Names.Tiles.Machine.DISTILLER);
		GameRegistry.registerTileEntity(TEChemReactor.class, Names.Tiles.Machine.CHEM_REACTOR);
	}
}
