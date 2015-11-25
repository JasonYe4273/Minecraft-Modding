package com.JasonILTG.ScienceMod.init;

import com.JasonILTG.ScienceMod.block.AirExtractor;
import com.JasonILTG.ScienceMod.block.Electrolyzer;
import com.JasonILTG.ScienceMod.block.general.BlockScience;
import com.JasonILTG.ScienceMod.block.general.Condenser;
import com.JasonILTG.ScienceMod.reference.Names;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ScienceModBlocks
{
	public static final BlockScience electrolyzer = new Electrolyzer();
	public static final BlockScience air_extractor = new AirExtractor();
	public static final BlockScience condenser = new Condenser();
	
	public static void init()
	{
		// Register the blocks with the game registry
		GameRegistry.registerBlock(electrolyzer, Names.Blocks.MACHINE_ELECTROLYZER);
		GameRegistry.registerBlock(air_extractor, Names.Blocks.MACHINE_AIR_EXTRACTOR);
		GameRegistry.registerBlock(condenser, Names.Blocks.MACHINE_CONDENSER);
	}
	
	public static void registerRenders()
	{
		registerRender(electrolyzer);
		registerRender(air_extractor);
		registerRender(condenser);
	}
	
	public static void registerRender(BlockScience block)
	{
		// Register the render of the block
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(Item.getItemFromBlock(block), 0,
				new ModelResourceLocation(block.getUnlocalizedName().substring(5), "inventory"));
	}
}
