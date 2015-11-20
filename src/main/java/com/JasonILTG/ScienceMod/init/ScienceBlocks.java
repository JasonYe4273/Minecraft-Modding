package com.JasonILTG.ScienceMod.init;

import com.JasonILTG.ScienceMod.block.ElectrolyzerBlock;
import com.JasonILTG.ScienceMod.references.Reference;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ScienceBlocks
{
	public static final Block electrolyzer = new ElectrolyzerBlock();
	
	public static void init()
	{
		GameRegistry.registerBlock(electrolyzer, "electrolyzer");
	}
	
	public static void registerRenders()
	{
		registerRender(electrolyzer);
	}
	
	public static void registerRender(Block block)
	{
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(Item.getItemFromBlock(block), 0, 
				new ModelResourceLocation(Reference.MOD_ID + ":" + block.getUnlocalizedName().substring(5), "inventory"));
	}
}
