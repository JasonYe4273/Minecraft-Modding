package com.JasonILTG.ScienceMod.init;

import com.JasonILTG.ScienceMod.references.Reference;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ElementItems
{
	//Example item
	public static Item hydrogen;
	public static Item helium;
	
	public static void init()
	{
		hydrogen = new Item().setUnlocalizedName("hydrogen").setCreativeTab(CreativeTabs.tabMisc);
		helium = new Item().setUnlocalizedName("helium").setCreativeTab(CreativeTabs.tabMisc);
	}
	
	public static void register()
	{
		GameRegistry.registerItem(helium, helium.getUnlocalizedName().substring(5));
	}
	
	public static void registerRenders()
	{
		registerRender(hydrogen);
	}
	
	public static void registerRender(Item item)
	{
		//Register how the item renders
		new ModelResourceLocation(Reference.MOD_ID + ":" + item.getUnlocalizedName().substring(5), "inventory");
	}
}
