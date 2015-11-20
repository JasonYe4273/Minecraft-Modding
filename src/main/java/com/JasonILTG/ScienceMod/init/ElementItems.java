package com.JasonILTG.ScienceMod.init;

import com.JasonILTG.ScienceMod.item.ElementItem;
import com.JasonILTG.ScienceMod.item.HeliumItem;
import com.JasonILTG.ScienceMod.item.HydrogenItem;
import com.JasonILTG.ScienceMod.references.Reference;

import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ElementItems
{
	//Example item
	public static final ElementItem hydrogen = new HydrogenItem();
	public static final ElementItem helium = new HeliumItem();
	
	public static void init()
	{
		register();
		registerRenders();
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
