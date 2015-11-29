package com.JasonILTG.ScienceMod.init;

import com.JasonILTG.ScienceMod.reference.Names;
import com.JasonILTG.ScienceMod.tileentity.TEAirExtractor;
import com.JasonILTG.ScienceMod.tileentity.TECondenser;
import com.JasonILTG.ScienceMod.tileentity.TEElectrolyzer;
import com.JasonILTG.ScienceMod.tileentity.TEMixer;

import net.minecraftforge.fml.common.registry.GameRegistry;

public class ScienceModTileEntities
{
	
	public static void init()
	{
		// Register the blocks with the game registry
		GameRegistry.registerTileEntity(TEElectrolyzer.class, Names.Tiles.ELECTROLYZER);
		GameRegistry.registerTileEntity(TEAirExtractor.class, Names.Tiles.AIR_EXTRACTOR);
		GameRegistry.registerTileEntity(TECondenser.class, Names.Tiles.CONDENSER);
		GameRegistry.registerTileEntity(TEMixer.class, Names.Tiles.MIXER);
		GameRegistry.registerTileEntity(TEMixer.class, Names.Tiles.CENTRIFUGE);
		GameRegistry.registerTileEntity(TEMixer.class, Names.Tiles.FILTER);
		GameRegistry.registerTileEntity(TEMixer.class, Names.Tiles.DISTILLER);
	}
}
