package com.JasonILTG.ScienceMod.init;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;

import com.JasonILTG.ScienceMod.block.Electrolyzer;
import com.JasonILTG.ScienceMod.block.general.BlockScience;
import com.JasonILTG.ScienceMod.reference.Names;

public class ScienceModBlocks
{
	public static final BlockScience electrolyzer = new Electrolyzer();
	
	public static void init()
	{
		// Register the blocks with the game registry
		GameRegistry.registerBlock(electrolyzer, Names.Blocks.MACHINE_ELECTROLYZER);
	}
	
	public static void registerRenders()
	{
		registerRender(electrolyzer);
	}
	
	public static void registerRender(BlockScience block)
	{
		// Register the render of the block
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(Item.getItemFromBlock(block), 0,
				new ModelResourceLocation(block.getUnlocalizedName().substring(5), "inventory"));
	}
}
