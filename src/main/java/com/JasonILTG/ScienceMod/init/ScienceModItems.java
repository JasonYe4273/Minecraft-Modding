package com.JasonILTG.ScienceMod.init;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

import com.JasonILTG.ScienceMod.item.Dust;
import com.JasonILTG.ScienceMod.item.JarItem;
import com.JasonILTG.ScienceMod.item.Mixture;
import com.JasonILTG.ScienceMod.item.Solution;
import com.JasonILTG.ScienceMod.item.armor.ArmorScience;
import com.JasonILTG.ScienceMod.item.armor.exo.Exoskeleton;
import com.JasonILTG.ScienceMod.item.compounds.CO2Item;
import com.JasonILTG.ScienceMod.item.compounds.H2OItem;
import com.JasonILTG.ScienceMod.item.elements.ItemElement;
import com.JasonILTG.ScienceMod.item.general.IItemScienceMod;
import com.JasonILTG.ScienceMod.item.general.ItemScience;
import com.JasonILTG.ScienceMod.item.tool.JarLauncher;
import com.JasonILTG.ScienceMod.reference.Reference;

public class ScienceModItems
{
	// Initialize new items
	public static ItemScience jar = new JarItem();
	public static ItemScience element = new ItemElement();
	public static ItemScience water = new H2OItem();
	public static ItemScience carbonDioxide = new CO2Item();
	public static ItemScience mixture = new Mixture();
	public static ItemScience solution = new Solution();
	public static ItemScience dust = new Dust();
	public static ItemScience jarLauncher = new JarLauncher();
	
	public static ArmorScience exoHelmet = Exoskeleton.makeHelmet();
	public static ArmorScience exoChest = Exoskeleton.makeChestplate();
	public static ArmorScience exoLegs = Exoskeleton.makeLeggings();
	public static ArmorScience exoBoots = Exoskeleton.makeBoots();
	
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
		
		GameRegistry.registerItem(exoHelmet, exoHelmet.getUnlocalizedName().substring(5));
		GameRegistry.registerItem(exoChest, exoChest.getUnlocalizedName().substring(5));
		GameRegistry.registerItem(exoLegs, exoLegs.getUnlocalizedName().substring(5));
		GameRegistry.registerItem(exoBoots, exoBoots.getUnlocalizedName().substring(5));
	}
	
	public static void addVariants()
	{
		addVariants(element);
		addVariants(jarLauncher);
	}
	
	public static void addVariants(ItemScience item)
	{
		// Register variant names for items with subtypes
		if (!item.getHasSubtypes()) return;
		for (int meta = 0; meta < item.getNumSubtypes(); meta ++)
		{
			ModelBakery.addVariantName(item, item.getUnlocalizedName(new ItemStack(item, 1, meta)).substring(5));
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
		registerRender(jarLauncher);
		
		registerRender(exoHelmet);
		registerRender(exoChest);
		registerRender(exoLegs);
		registerRender(exoBoots);
	}
	
	public static void registerRender(IItemScienceMod item)
	{
		// Register renders of all subtypes if there are any
		if (item.getHasSubtypes())
		{
			for (int meta = 0; meta < item.getNumSubtypes(); meta ++)
			{
				Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register((Item) item, meta,
						new ModelResourceLocation(item.getUnlocalizedName(new ItemStack((Item) item, 1, meta)).substring(5), "inventory"));
			}
			return;
		}
		// Otherwise, just register the render of the item
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register((Item) item, 0,
				new ModelResourceLocation(Reference.RESOURCE_PREFIX + item.getUnlocalizedName().substring(5), "inventory"));
	}
}
