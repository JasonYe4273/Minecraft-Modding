package com.JasonILTG.ScienceMod.init;

import net.minecraftforge.fml.common.registry.GameRegistry;

import com.JasonILTG.ScienceMod.reference.Names;
import com.JasonILTG.ScienceMod.tileentity.TEElectrolyzer;

public class ScienceModTileEntities
{
	
	public static void init()
	{
		// Register the blocks with the game registry
		GameRegistry.registerTileEntity(TEElectrolyzer.class, Names.Blocks.MACHINE_ELECTROLYZER);
	}
	
}
