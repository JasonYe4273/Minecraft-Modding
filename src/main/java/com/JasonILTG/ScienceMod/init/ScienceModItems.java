package com.JasonILTG.ScienceMod.init;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

import com.JasonILTG.ScienceMod.item.Dust;
import com.JasonILTG.ScienceMod.item.JarItem;
import com.JasonILTG.ScienceMod.item.Mixture;
import com.JasonILTG.ScienceMod.item.Solution;
import com.JasonILTG.ScienceMod.item.compounds.CO2Item;
import com.JasonILTG.ScienceMod.item.compounds.H2OItem;
import com.JasonILTG.ScienceMod.item.elements.ElementItem;
import com.JasonILTG.ScienceMod.item.general.ItemScience;
import com.JasonILTG.ScienceMod.item.tool.JarLauncher;
import com.JasonILTG.ScienceMod.reference.Reference;

public class ScienceModItems
{
	// Initialize new items
	public static ItemScience jar = new JarItem();
	public static ItemScience element = new ElementItem();
	public static ItemScience water = new H2OItem();
	public static ItemScience carbonDioxide = new CO2Item();
	public static ItemScience mixture = new Mixture();
	public static ItemScience solution = new Solution();
	public static ItemScience dust = new Dust();
	public static ItemScience jarLauncher = new JarLauncher();
	
	public static void init()
	{
		register();
	}
	
	public static void register()
	{
		// Register the items with the game registry
		GameRegistry.registerItem(jar, jar.getUnlocalizedName().substring(5));
		GameRegistry.registerItem(element, element.getUnlocalizedName().substring(5));
		GameRegistry.registerItem(water, water.getUnlocalizedName().substring(5));
		GameRegistry.registerItem(mixture, mixture.getUnlocalizedName().substring(5));
		GameRegistry.registerItem(solution, solution.getUnlocalizedName().substring(5));
		GameRegistry.registerItem(carbonDioxide, carbonDioxide.getUnlocalizedName().substring(5));
		GameRegistry.registerItem(dust, dust.getUnlocalizedName().substring(5));
		GameRegistry.registerItem(jarLauncher, jarLauncher.getUnlocalizedName().substring(5));
	}
	
	public static void addVariants()
	{
		addVariants(element);
	}
	
	public static void addVariants(ItemScience element2)
	{
		// Register variant names for items with subtypes
		if (!element2.getHasSubtypes()) return;
		for (int meta = 0; meta < element2.getNumSubtypes(); meta ++)
		{
			ModelBakery.addVariantName(element2, element2.getUnlocalizedName(new ItemStack(element2, 1, meta)).substring(5));
		}
	}
	
	public static void registerRenders()
	{
		// Register the renders of all items
		registerRender(jar);
		registerRender(element);
		registerRender(water);
		registerRender(carbonDioxide);
		registerRender(mixture);
		registerRender(solution);
		registerRender(dust);
	}
	
	public static void registerRender(ItemScience jar2)
	{
		// Register renders of all subtypes if there are any
		if (jar2.getHasSubtypes())
		{
			for (int meta = 0; meta < jar2.getNumSubtypes(); meta ++)
			{
				Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(jar2, meta,
						new ModelResourceLocation(jar2.getUnlocalizedName(new ItemStack(jar2, 1, meta)).substring(5), "inventory"));
			}
			return;
		}
		// Otherwise, just register the render of the item
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(jar2, 0,
				new ModelResourceLocation(Reference.RESOURCE_PREFIX + jar2.getUnlocalizedName().substring(5), "inventory"));
	}
}
