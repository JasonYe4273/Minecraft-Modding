package com.JasonILTG.ScienceMod.init;

import com.JasonILTG.ScienceMod.item.JarItem;
import com.JasonILTG.ScienceMod.item.Mixture;
import com.JasonILTG.ScienceMod.item.Solution;
import com.JasonILTG.ScienceMod.item.compounds.H2OItem;
import com.JasonILTG.ScienceMod.item.elements.ElementItem;
import com.JasonILTG.ScienceMod.item.general.ItemJarred;
import com.JasonILTG.ScienceMod.reference.Reference;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ScienceModItems
{
	//Initialize new items
	public static ItemJarred jar = new JarItem();
	public static ItemJarred element = new ElementItem();
	public static ItemJarred water = new H2OItem();
	public static ItemJarred mixture = new Mixture();
	public static ItemJarred solution = new Solution();
	
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
		GameRegistry.registerItem(mixture, mixture.getUnlocalizedName().substring(5));
		GameRegistry.registerItem(solution, solution.getUnlocalizedName().substring(5));
	}
	
	public static void addVariants()
	{
	    addVariants(element);
	}
	
	public static void addVariants(ItemJarred item)
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
		//Register the renders of all items
		registerRender(jar);
		registerRender(element);
		registerRender(water);
		registerRender(mixture);
		registerRender(solution);
	}
	
	public static void registerRender(ItemJarred item)
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
				new ModelResourceLocation(Reference.RESOURCE_PREFIX + item.getUnlocalizedName().substring(5), "inventory"));
	}
}
