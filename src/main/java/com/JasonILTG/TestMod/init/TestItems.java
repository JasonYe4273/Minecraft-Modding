package com.JasonILTG.TestMod.init;

import com.JasonILTG.TestMod.TestReference;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class TestItems
{	
	//Example item
	public static Item test_item;
	
	public static void init()
	{
		//Initialize the item
		test_item = new Item().setUnlocalizedName("test_item").setCreativeTab(CreativeTabs.tabMisc);
	}
	
	public static void register()
	{
		//Register the item
		GameRegistry.registerItem(test_item, test_item.getUnlocalizedName().substring(5));
	}
	
	public static void registerRenders()
	{
		registerRender(test_item);
	}
	
	public static void registerRender(Item item)
	{
		//REgister how the item renders
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(item, 0, 
				new ModelResourceLocation(TestReference.MOD_ID + ":" + item.getUnlocalizedName().substring(5), "inventory"));
	}
}
