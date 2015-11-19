package com.JasonILTG.NumbersMod.init;

import com.JasonILTG.NumbersMod.Reference;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class NumbersItems
{
	public static Item[] numbers;
	
	public static Item addition;
	public static Item subtraction;
	public static Item multiplication;
	public static Item division;
	
	public static void init()
	{
		numbers = new Item[Reference.MAX_NUMBER];
		
		//Numbers
		for( int n = 0; n < Reference.MAX_NUMBER; n++ )
			numbers[n] = new Item().setUnlocalizedName(String.valueOf(n)).setCreativeTab(CreativeTabs.tabMisc);
		
		//Operations
		addition = new Item().setUnlocalizedName("addition").setCreativeTab(CreativeTabs.tabMisc);
		subtraction = new Item().setUnlocalizedName("subtraction").setCreativeTab(CreativeTabs.tabMisc);
		multiplication = new Item().setUnlocalizedName("multiplication").setCreativeTab(CreativeTabs.tabMisc);
		division = new Item().setUnlocalizedName("division").setCreativeTab(CreativeTabs.tabMisc);
	}
	
	public static void register()
	{
		for( int n = 0; n < Reference.MAX_NUMBER; n++ )
			GameRegistry.registerItem(numbers[n], numbers[n].getUnlocalizedName().substring(5));
		
		GameRegistry.registerItem(addition, addition.getUnlocalizedName().substring(5));
		GameRegistry.registerItem(subtraction, subtraction.getUnlocalizedName().substring(5));
		GameRegistry.registerItem(multiplication,multiplication.getUnlocalizedName().substring(5));
		GameRegistry.registerItem(division, division.getUnlocalizedName().substring(5));
	}
	
	public static void registerRenders()
	{
		for( int n = 0; n < Reference.MAX_NUMBER; n++ )
			registerRender( numbers[n] );
		
		registerRender(addition);
		registerRender(subtraction);
		registerRender(multiplication);
		registerRender(division);
	}
	
	public static void registerRender(Item item)
	{
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(item, 0, 
				new ModelResourceLocation(Reference.MOD_ID + ":" + item.getUnlocalizedName().substring(5), "inventory"));
	}
}
