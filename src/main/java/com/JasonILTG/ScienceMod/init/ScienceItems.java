package com.JasonILTG.ScienceMod.init;

import com.JasonILTG.ScienceMod.item.BerylliumItem;
import com.JasonILTG.ScienceMod.item.BoronItem;
import com.JasonILTG.ScienceMod.item.CarbonItem;
import com.JasonILTG.ScienceMod.item.ElementItem;
import com.JasonILTG.ScienceMod.item.FluorineItem;
import com.JasonILTG.ScienceMod.item.HeliumItem;
import com.JasonILTG.ScienceMod.item.HydrogenItem;
import com.JasonILTG.ScienceMod.item.JarItem;
import com.JasonILTG.ScienceMod.item.LithiumItem;
import com.JasonILTG.ScienceMod.item.NeonItem;
import com.JasonILTG.ScienceMod.item.NitrogenItem;
import com.JasonILTG.ScienceMod.item.OxygenItem;
import com.JasonILTG.ScienceMod.references.Reference;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ScienceItems
{
	public static ElementItem jar = new JarItem();
	public static ElementItem hydrogen = new HydrogenItem();
	public static ElementItem helium = new HeliumItem();
	public static ElementItem lithium = new LithiumItem();
	public static ElementItem beryllium = new BerylliumItem();
	public static ElementItem boron = new BoronItem();
	public static ElementItem carbon = new CarbonItem();
	public static ElementItem nitrogen = new NitrogenItem();
	public static ElementItem oxygen = new OxygenItem();
	public static ElementItem fluorine = new FluorineItem();
	public static ElementItem neon = new NeonItem();
	
	public static void init()
	{
		register();
	}
	
	public static void register()
	{
		GameRegistry.registerItem(jar, jar.getUnlocalizedName().substring(5));
		GameRegistry.registerItem(hydrogen, hydrogen.getUnlocalizedName().substring(5));
		GameRegistry.registerItem(helium, helium.getUnlocalizedName().substring(5));
		GameRegistry.registerItem(lithium, lithium.getUnlocalizedName().substring(5));
		GameRegistry.registerItem(beryllium, beryllium.getUnlocalizedName().substring(5));
		GameRegistry.registerItem(boron, boron.getUnlocalizedName().substring(5));
		GameRegistry.registerItem(carbon, carbon.getUnlocalizedName().substring(5));
		GameRegistry.registerItem(nitrogen, nitrogen.getUnlocalizedName().substring(5));
		GameRegistry.registerItem(oxygen, oxygen.getUnlocalizedName().substring(5));
		GameRegistry.registerItem(fluorine, fluorine.getUnlocalizedName().substring(5));
		GameRegistry.registerItem(neon, neon.getUnlocalizedName().substring(5));
	}
	
	public static void registerRenders()
	{
		registerRender(jar);
		registerRender(hydrogen);
		registerRender(helium);
		registerRender(lithium);
		registerRender(beryllium);
		registerRender(boron);
		registerRender(carbon);
		registerRender(nitrogen);
		registerRender(oxygen);
		registerRender(fluorine);
		registerRender(neon);
	}
	
	public static void registerRender(Item item)
	{
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(item, 0, 
				new ModelResourceLocation(Reference.MOD_ID + ":" + item.getUnlocalizedName().substring(5), "inventory"));
	}
}
