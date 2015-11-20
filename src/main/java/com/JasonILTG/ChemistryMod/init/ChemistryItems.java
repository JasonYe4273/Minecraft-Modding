package com.JasonILTG.ChemistryMod.init;

import com.JasonILTG.ChemistryMod.references.Reference;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ChemistryItems
{	
	//Example item
	public static Item hydrogen;
	
	public static void init()
	{
		//Initialize the item
		hydrogen = new Item().setUnlocalizedName("Chemistry_item").setCreativeTab(CreativeTabs.tabMisc);
	}
	
	public static void register()
	{
		//Register the item
		GameRegistry.registerItem(hydrogen, hydrogen.getUnlocalizedName().substring(5));
	}
	
	public static void registerRenders()
	{
		registerRender(hydrogen);
	}
	
	public static void registerRender(Item item)
	{
		//REgister how the item renders
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(item, 0, 
				new ModelResourceLocation(Reference.MOD_ID + ":" + item.getUnlocalizedName().substring(5), "inventory"));
	}
}
