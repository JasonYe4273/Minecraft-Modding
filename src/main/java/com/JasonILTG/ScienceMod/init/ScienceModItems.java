package com.JasonILTG.ScienceMod.init;

import java.lang.reflect.Field;

import com.JasonILTG.ScienceMod.item.Dust;
import com.JasonILTG.ScienceMod.item.JarItem;
import com.JasonILTG.ScienceMod.item.Mixture;
import com.JasonILTG.ScienceMod.item.Solution;
import com.JasonILTG.ScienceMod.item.armor.ArmorScience;
import com.JasonILTG.ScienceMod.item.armor.Exoskeleton;
import com.JasonILTG.ScienceMod.item.component.PowerBlock;
import com.JasonILTG.ScienceMod.item.component.electronics.Battery;
import com.JasonILTG.ScienceMod.item.component.electronics.ItemWireCoil;
import com.JasonILTG.ScienceMod.item.component.hull.Hull;
import com.JasonILTG.ScienceMod.item.compounds.CO2Item;
import com.JasonILTG.ScienceMod.item.compounds.H2OItem;
import com.JasonILTG.ScienceMod.item.elements.ItemElement;
import com.JasonILTG.ScienceMod.item.general.IItemScienceMod;
import com.JasonILTG.ScienceMod.item.general.ItemScience;
import com.JasonILTG.ScienceMod.item.tool.JarLauncher;
import com.JasonILTG.ScienceMod.item.tool.TemperatureGuage;
import com.JasonILTG.ScienceMod.item.upgrades.PowerCapacityUpgrade;
import com.JasonILTG.ScienceMod.item.upgrades.PowerInputUpgrade;
import com.JasonILTG.ScienceMod.item.upgrades.PowerOutputUpgrade;
import com.JasonILTG.ScienceMod.item.upgrades.SpeedUpgrade;
import com.JasonILTG.ScienceMod.reference.Reference;
import com.JasonILTG.ScienceMod.util.LogHelper;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

/**
 * Init class for all ScienceMod items.
 * 
 * @author JasonILTG and syy1125
 */
public class ScienceModItems
{
	// Regular items
	public static final ItemScience jar = new JarItem();
	public static final ItemScience element = new ItemElement();
	public static final ItemScience water = new H2OItem();
	public static final ItemScience carbonDioxide = new CO2Item();
	public static final ItemScience mixture = new Mixture();
	public static final ItemScience solution = new Solution();
	public static final ItemScience dust = new Dust();
	public static final ItemScience jarLauncher = new JarLauncher();
	public static final ItemScience tempGauge = new TemperatureGuage();
	
	// Armor items
	public static final ArmorScience exoHelmet = Exoskeleton.makeHelmet();
	public static final ArmorScience exoChest = Exoskeleton.makeChestplate();
	public static final ArmorScience exoLegs = Exoskeleton.makeLeggings();
	public static final ArmorScience exoBoots = Exoskeleton.makeBoots();
	
	// Upgrades
	public static final ItemScience powerCapacityUpgrade = new PowerCapacityUpgrade();
	public static final ItemScience maxInUpgrade = new PowerInputUpgrade();
	public static final ItemScience maxOutUpgrade = new PowerOutputUpgrade();
	public static final ItemScience speedUpgrade = new SpeedUpgrade();
	
	// Components
	public static final ItemScience hull = new Hull();
	public static final ItemScience battery = new Battery();
	public static final ItemScience powerBlock = new PowerBlock();
	public static final ItemScience wireCoil = new ItemWireCoil();
	
	/**
	 * Initializes all ScienceMod items.
	 */
	public static void init()
	{
		register();
	}
	
	/**
	 * Registers all ScienceMod items.
	 * 
	 * @throws Exception
	 */
	private static void register()
	{
		for (Field f : ScienceModItems.class.getDeclaredFields()) {
			try {
				Object obj = f.get(null);
				if (obj instanceof IItemScienceMod && obj instanceof Item)
				{
					IItemScienceMod item = (IItemScienceMod) obj;
					GameRegistry.registerItem((Item) item, item.getUnlocalizedName().substring(5));
					if (item.getHasSubtypes())
					{
						addVariants(item);
					}
				}
			}
			catch (Exception e) {
				LogHelper.error("Error when registering " + f.getName());
				LogHelper.error(e.getStackTrace());
			}
		}
		
		/*
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
		
		GameRegistry.registerItem(powerCapacityUpgrade, powerCapacityUpgrade.getUnlocalizedName().substring(5));
		GameRegistry.registerItem(maxInUpgrade, maxInUpgrade.getUnlocalizedName().substring(5));
		GameRegistry.registerItem(maxOutUpgrade, maxOutUpgrade.getUnlocalizedName().substring(5));
		GameRegistry.registerItem(speedUpgrade, speedUpgrade.getUnlocalizedName().substring(5));
		
		GameRegistry.registerItem(hull, hull.getUnlocalizedName().substring(5));
		GameRegistry.registerItem(battery, battery.getUnlocalizedName().substring(5));
		GameRegistry.registerItem(powerBlock, powerBlock.getUnlocalizedName().substring(5));
		*/
	}
	
	/**
	 * Registers variant names for all ScienceMod items that have them.
	 */
	public static void addVariants()
	{
		addVariants(element);
		addVariants(jarLauncher);
		addVariants(hull);
		addVariants(battery);
	}
	
	/**
	 * Registers the variant names of an item.
	 * 
	 * @param (Item) item The item
	 */
	private static void addVariants(IItemScienceMod item)
	{
		// Check that the item has subtypes
		if (!item.getHasSubtypes()) return;
		
		for (int meta = 0; meta < item.getNumSubtypes(); meta ++)
		{
			ModelBakery.addVariantName((Item) item, item.getUnlocalizedName(new ItemStack((Item) item, 1, meta)).substring(5));
		}
	}
	
	/**
	 * Registers the renders of all ScienceMod items.
	 * 
	 * @throws Exception
	 */
	public static void registerRenders()
	{
		for (Field f : ScienceModItems.class.getDeclaredFields()) {
			try {
				Object obj = f.get(null);
				if (obj instanceof IItemScienceMod && obj instanceof Item)
				{
					IItemScienceMod item = (IItemScienceMod) obj;
					registerRender(item);
				}
			}
			catch (Exception e) {
				LogHelper.error("Error when registering render for " + f.getName());
				LogHelper.error(e.getStackTrace());
			}
		}
		// Register the renders of all items
		/*
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
		
		registerRender(powerCapacityUpgrade);
		registerRender(maxInUpgrade);
		registerRender(maxOutUpgrade);
		registerRender(speedUpgrade);
		
		registerRender(hull);
		registerRender(battery);
		registerRender(powerBlock);
		*/
	}
	
	/**
	 * Registers the renders of an item.
	 * 
	 * @param item The item
	 */
	private static void registerRender(IItemScienceMod item)
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
