package com.JasonILTG.ScienceMod.init;

import com.JasonILTG.ScienceMod.ScienceReference;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ScienceItems
{
	
	public static void init()
	{
		
	}
	
	public static void register()
	{
		
	}
	
	public static void registerRenders()
	{
		
	}
	
	public static void registerRender(Item item)
	{
		//Register how the item renders
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(item, 0, 
				new ModelResourceLocation(ScienceReference.MOD_ID + ":" + item.getUnlocalizedName().substring(5), "inventory"));
	}
}
