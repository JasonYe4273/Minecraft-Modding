package com.JasonILTG.ScienceMod.init;

import com.JasonILTG.ScienceMod.item.ElementItem;
import com.JasonILTG.ScienceMod.item.HeliumItem;
import com.JasonILTG.ScienceMod.item.HydrogenItem;
import com.JasonILTG.ScienceMod.references.Reference;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ScienceItems
{
	public static ElementItem hydrogen = new HydrogenItem();
	public static ElementItem helium = new HeliumItem();
	
	public static void init()
	{
		register();
	}
	
	public static void register()
	{
		GameRegistry.registerItem(hydrogen, hydrogen.getUnlocalizedName().substring(5));
		GameRegistry.registerItem(helium, helium.getUnlocalizedName().substring(5));
	}
	
	public static void registerRenders()
	{
		registerRender(hydrogen);
		registerRender(helium);
	}
	
	public static void registerRender(Item item)
	{
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(item, 0, 
				new ModelResourceLocation(Reference.MOD_ID + ":" + item.getUnlocalizedName().substring(5), "inventory"));
	}
}
