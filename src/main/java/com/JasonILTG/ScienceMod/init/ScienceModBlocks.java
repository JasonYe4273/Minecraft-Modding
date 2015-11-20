package com.JasonILTG.ScienceMod.init;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;

import com.JasonILTG.ScienceMod.block.BlockScience;
import com.JasonILTG.ScienceMod.block.MachineElectrolyzer;
import com.JasonILTG.ScienceMod.references.Names;
import com.JasonILTG.ScienceMod.references.Reference;
import com.JasonILTG.ScienceMod.util.LogHelper;

public class ScienceModBlocks
{
	public static final BlockScience electrolyzer = new MachineElectrolyzer();
	
	public static void init()
	{
		GameRegistry.registerBlock(electrolyzer, Names.Blocks.MACHINE_ELECTROLYZER);
	}
	
	public static void registerRenders()
	{
		registerRender(electrolyzer);
	}
	
	public static void registerRender(BlockScience block)
	{
		LogHelper.trace("Using electrolyzer texture " + Reference.MOD_ID + ":" + block.getUnlocalizedName().substring(5));
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(Item.getItemFromBlock(block), 0,
				new ModelResourceLocation(block.getUnlocalizedName().substring(5), "inventory"));
	}
}