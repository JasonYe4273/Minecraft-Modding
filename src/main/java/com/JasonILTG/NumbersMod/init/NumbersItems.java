package com.JasonILTG.NumbersMod.init;

import com.JasonILTG.NumbersMod.NumbersReference;
import com.JasonILTG.NumbersMod.creativetab.NumbersCreativeTab;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelResourceLocation;
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
		numbers = new Item[NumbersReference.MAX_NUMBER];
		
		//Number Items
		for( int n = 0; n < NumbersReference.MAX_NUMBER; n++ )
			numbers[n] = new Item().setUnlocalizedName(String.valueOf(n)).setCreativeTab(NumbersCreativeTab.tabNumbers);
		
		//Operation Items
		addition = new Item().setUnlocalizedName("addition").setCreativeTab(NumbersCreativeTab.tabNumbers);
		subtraction = new Item().setUnlocalizedName("subtraction").setCreativeTab(NumbersCreativeTab.tabNumbers);
		multiplication = new Item().setUnlocalizedName("multiplication").setCreativeTab(NumbersCreativeTab.tabNumbers);
		division = new Item().setUnlocalizedName("division").setCreativeTab(NumbersCreativeTab.tabNumbers);
	}
	
	public static void register()
	{
		for( int n = 0; n < NumbersReference.MAX_NUMBER; n++ )
			GameRegistry.registerItem(numbers[n], numbers[n].getUnlocalizedName().substring(5));
		
		GameRegistry.registerItem(addition, addition.getUnlocalizedName().substring(5));
		GameRegistry.registerItem(subtraction, subtraction.getUnlocalizedName().substring(5));
		GameRegistry.registerItem(multiplication,multiplication.getUnlocalizedName().substring(5));
		GameRegistry.registerItem(division, division.getUnlocalizedName().substring(5));
	}
	
	public static void registerRenders()
	{
		for( int n = 0; n < NumbersReference.MAX_NUMBER; n++ )
			registerRender( numbers[n] );
		
		registerRender(addition);
		registerRender(subtraction);
		registerRender(multiplication);
		registerRender(division);
	}
	
	public static void registerRender(Item item)
	{
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(item, 0, 
				new ModelResourceLocation(NumbersReference.MOD_ID + ":" + item.getUnlocalizedName().substring(5), "inventory"));
	}
}
