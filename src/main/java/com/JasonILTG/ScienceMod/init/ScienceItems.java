package com.JasonILTG.ScienceMod.init;

import com.JasonILTG.ScienceMod.item.ItemScience;
import com.JasonILTG.ScienceMod.item.JarItem;
import com.JasonILTG.ScienceMod.item.compounds.H2OItem;
import com.JasonILTG.ScienceMod.item.elements.ElementItem;
import com.JasonILTG.ScienceMod.references.Reference;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ScienceItems
{
	//Initialize new items
	public static ItemScience jar = new JarItem();
	public static ItemScience element = new ElementItem();
	public static ItemScience water = new H2OItem();
	
	public static void init()
	{
		register();
	}
	
	public static void register()
	{
		//Register the items with the game registry
		GameRegistry.registerItem(jar, jar.getUnlocalizedName().substring(5));
		GameRegistry.registerItem(element, element.getUnlocalizedName().substring(5));
		GameRegistry.registerItem(water, water.getUnlocalizedName().substring(5));
	}
	
	public static void addVariants()
	{
	    addVariants(element);
	}
	
	public static void addVariants(ItemScience item)
	{
		//Register variant names for items with subtypes
		if( !item.getHasSubtypes() ) return;
		for( int meta = 0; meta < item.getNumSubtypes(); meta++ )
		{
			ModelBakery.addVariantName(item, item.getUnlocalizedName(new ItemStack(item, 1, meta)).substring(5));
		}
	}
	
	public static void registerRenders()
	{
		//Register th renders of all items
		registerRender(jar);
		registerRender(element);
		registerRender(water);
	}
	
	public static void registerRender(ItemScience item)
	{
		//Register renders of all subtypes if there are any
		if( item.getHasSubtypes() )
		{
			for( int meta = 0; meta < item.getNumSubtypes(); meta++ )
			{
				Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(item, meta, 
						new ModelResourceLocation(item.getUnlocalizedName(new ItemStack(item, 1, meta)).substring(5), "inventory"));
			}
			return;
		}
		//Otherwise, just register the render of the item
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(item, 0, 
				new ModelResourceLocation(Reference.MOD_ID + ":" + item.getUnlocalizedName().substring(5), "inventory"));
	}
}
