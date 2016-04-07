package com.JasonILTG.ScienceMod.init;

import java.lang.reflect.Field;

import com.JasonILTG.ScienceMod.item.JarItem;
import com.JasonILTG.ScienceMod.item.armor.ArmorScience;
import com.JasonILTG.ScienceMod.item.armor.Exoskeleton;
import com.JasonILTG.ScienceMod.item.chemistry.CompoundItem;
import com.JasonILTG.ScienceMod.item.chemistry.ItemElement;
import com.JasonILTG.ScienceMod.item.chemistry.Mixture;
import com.JasonILTG.ScienceMod.item.chemistry.Solution;
import com.JasonILTG.ScienceMod.item.component.PowerBlock;
import com.JasonILTG.ScienceMod.item.component.electronics.Battery;
import com.JasonILTG.ScienceMod.item.component.electronics.ItemWireCoil;
import com.JasonILTG.ScienceMod.item.component.hull.Hull;
import com.JasonILTG.ScienceMod.item.general.IItemScienceMod;
import com.JasonILTG.ScienceMod.item.general.ItemScience;
import com.JasonILTG.ScienceMod.item.metals.Dust;
import com.JasonILTG.ScienceMod.item.metals.Ingot;
import com.JasonILTG.ScienceMod.item.tool.JarLauncher;
import com.JasonILTG.ScienceMod.item.tool.TemperatureGauge;
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
import net.minecraft.util.ResourceLocation;
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
	public static final ItemScience jarLauncher = new JarLauncher();
	public static final ItemScience tempGauge = new TemperatureGauge();
	
	// Chemistry
	public static final ItemScience element = new ItemElement();
	public static ItemScience compound;
	public static final ItemScience mixture = new Mixture();
	public static final ItemScience solution = new Solution();
	
	// Metals
	public static final ItemScience dust = new Dust();
	public static final ItemScience ingot = new Ingot();
	
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
	 * Initializes chemistry-related items.
	 */
	public static void chemInit()
	{
		compound = CompoundItem.getCompoundItem("H2O");
		if (compound == null) compound = CompoundItem.getCompound(0);
	}
	
	/**
	 * Initializes all ScienceMod items.
	 */
	public static void init()
	{
		register();
	}
	
	/**
	 * Registers all ScienceMod items.
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
	}
	
	/**
	 * Registers variant names for all ScienceMod items that have them.
	 */
	public static void addVariants()
	{
		addVariants(element);
		addVariants(compound);
		addVariants(jarLauncher);
		addVariants(hull);
		addVariants(battery);
	}
	
	/**
	 * Registers the variant names of an item.
	 * 
	 * @param item The item
	 */
	private static void addVariants(IItemScienceMod item)
	{
		// Check that the item has subtypes
		if (!item.getHasSubtypes()) return;
		
		for (int meta = 0; meta < item.getNumSubtypes(); meta ++)
		{
			ModelBakery.registerItemVariants((Item) item, new ResourceLocation(
					item.getUnlocalizedName(new ItemStack((Item) item, 1, meta)).substring(5)));
		}
	}
	
	/**
	 * Registers the renders of all ScienceMod items.
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
