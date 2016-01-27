package com.JasonILTG.ScienceMod.init.crafting;

import com.JasonILTG.ScienceMod.init.ScienceModBlocks;
import com.JasonILTG.ScienceMod.init.ScienceModItems;
import com.JasonILTG.ScienceMod.item.component.electronics.BatteryLevel;
import com.JasonILTG.ScienceMod.item.component.hull.MaterialHeat;
import com.JasonILTG.ScienceMod.reference.NBTKeys;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.ShapedOreRecipe;

/**
 * Init class for all recipes for components.
 * 
 * @author JasonILTG and syy1125
 */
public class ComponentCrafting
{
	/**
	 * Initializes all recipes for components.
	 */
	public static void init()
	{
		// Hull
		ItemStack copperHull = new ItemStack(ScienceModItems.hull, 1, MaterialHeat.COPPER.ordinal());
		copperHull.setTagCompound(MaterialHeat.COPPER.createHullTag());
		GameRegistry.addRecipe(new ShapedOreRecipe(copperHull,
				"III",
				"I I",
				"III",
				'I', "ingotCopper"
				));
		ItemStack tinHull = new ItemStack(ScienceModItems.hull, 1, MaterialHeat.TIN.ordinal());
		tinHull.setTagCompound(MaterialHeat.TIN.createHullTag());
		GameRegistry.addRecipe(new ShapedOreRecipe(tinHull,
				"III",
				"I I",
				"III",
				'I', "ingotTin"
				));
		ItemStack bronzeHull = new ItemStack(ScienceModItems.hull, 1, MaterialHeat.BRONZE.ordinal());
		bronzeHull.setTagCompound(MaterialHeat.BRONZE.createHullTag());
		GameRegistry.addRecipe(new ShapedOreRecipe(bronzeHull,
				"III",
				"I I",
				"III",
				'I', "ingotBronze"
				));
		ItemStack ironHull = new ItemStack(ScienceModItems.hull, 1, MaterialHeat.IRON.ordinal());
		ironHull.setTagCompound(MaterialHeat.IRON.createHullTag());
		GameRegistry.addRecipe(new ShapedOreRecipe(ironHull,
				"III",
				"I I",
				"III",
				'I', "ingotIron"
				));
		ItemStack steelHull = new ItemStack(ScienceModItems.hull, 1, MaterialHeat.STEEL.ordinal());
		steelHull.setTagCompound(MaterialHeat.STEEL.createHullTag());
		GameRegistry.addRecipe(new ShapedOreRecipe(steelHull,
				"III",
				"I I",
				"III",
				'I', "ingotSteel"
				));
		ItemStack leadHull = new ItemStack(ScienceModItems.hull, 1, MaterialHeat.LEAD.ordinal());
		leadHull.setTagCompound(MaterialHeat.LEAD.createHullTag());
		GameRegistry.addRecipe(new ShapedOreRecipe(leadHull,
				"III",
				"I I",
				"III",
				'I', "ingotLead"
				));
		ItemStack silverHull = new ItemStack(ScienceModItems.hull, 1, MaterialHeat.SILVER.ordinal());
		silverHull.setTagCompound(MaterialHeat.SILVER.createHullTag());
		GameRegistry.addRecipe(new ShapedOreRecipe(silverHull,
				"III",
				"I I",
				"III",
				'I', "ingotSilver"
				));
		ItemStack goldHull = new ItemStack(ScienceModItems.hull, 1, MaterialHeat.GOLD.ordinal());
		goldHull.setTagCompound(MaterialHeat.GOLD.createHullTag());
		GameRegistry.addRecipe(new ShapedOreRecipe(goldHull,
				"III",
				"I I",
				"III",
				'I', "ingotGold"
				));
		ItemStack diamondHull = new ItemStack(ScienceModItems.hull, 1, MaterialHeat.DIAMOND.ordinal());
		diamondHull.setTagCompound(MaterialHeat.DIAMOND.createHullTag());
		GameRegistry.addRecipe(new ShapedOreRecipe(diamondHull,
				"III",
				"I I",
				"III",
				'I', "gemDiamond"
				));
		ItemStack obsidianHull = new ItemStack(ScienceModItems.hull, 1, MaterialHeat.OBSIDIAN.ordinal());
		obsidianHull.setTagCompound(MaterialHeat.OBSIDIAN.createHullTag());
		GameRegistry.addRecipe(obsidianHull,
				"III",
				"I I",
				"III",
				'I', Blocks.obsidian
				);
		
		// Wire
		ItemStack wire = new ItemStack(ScienceModBlocks.wire, 6);
		NBTTagCompound wireTag = new NBTTagCompound();
		NBTTagCompound inTag = new NBTTagCompound();
		inTag.setFloat(NBTKeys.Item.Component.MAX_IN, 100F);
		NBTTagCompound outTag = new NBTTagCompound();
		outTag.setFloat(NBTKeys.Item.Component.MAX_OUT, 100F);
		wireTag.setTag(NBTKeys.Item.Component.WIRE_IN, inTag);
		wireTag.setTag(NBTKeys.Item.Component.WIRE_OUT, outTag);
		wire.setTagCompound(wireTag);
		GameRegistry.addRecipe(wire,
				"III",
				'I', Items.iron_ingot
				);
		
		// Battery
		ItemStack basicBattery = new ItemStack(ScienceModItems.battery, 2, BatteryLevel.BASIC.ordinal());
		basicBattery.setTagCompound(BatteryLevel.BASIC.createBatteryTag());
		GameRegistry.addRecipe(basicBattery,
				" W ",
				"IWI",
				"III",
				'I', Items.iron_ingot, 'W', ScienceModBlocks.wire
				);
		ItemStack doubleBattery = new ItemStack(ScienceModItems.battery, 1, BatteryLevel.DOUBLE.ordinal());
		doubleBattery.setTagCompound(BatteryLevel.DOUBLE.createBatteryTag());
		GameRegistry.addRecipe(doubleBattery,
				"BWB",
				'B', basicBattery, 'W', ScienceModBlocks.wire
				);
		ItemStack batteryPack = new ItemStack(ScienceModItems.battery, 1, BatteryLevel.PACK.ordinal());
		batteryPack.setTagCompound(BatteryLevel.PACK.createBatteryTag());
		GameRegistry.addRecipe(batteryPack,
				"IBI",
				"BWB",
				"IBI",
				'I', Items.iron_ingot, 'B', basicBattery, 'W', ScienceModBlocks.wire
				);
		GameRegistry.addRecipe(batteryPack,
				"III",
				"BWB",
				"III",
				'I', Items.iron_ingot, 'B', doubleBattery, 'W', ScienceModBlocks.wire
				);
		ItemStack doubleBatteryPack = new ItemStack(ScienceModItems.battery, 1, BatteryLevel.DOUBLE_PACK.ordinal());
		doubleBatteryPack.setTagCompound(BatteryLevel.DOUBLE_PACK.createBatteryTag());
		GameRegistry.addRecipe(doubleBatteryPack,
				"BWB",
				'B', batteryPack, 'W', ScienceModBlocks.wire
				);
		ItemStack batteryBundle = new ItemStack(ScienceModItems.battery, 1, BatteryLevel.BUNDLE.ordinal());
		batteryBundle.setTagCompound(BatteryLevel.BUNDLE.createBatteryTag());
		GameRegistry.addRecipe(batteryBundle,
				"IWI",
				"DBD",
				"IWI",
				'I', Items.iron_ingot, 'B', batteryPack, 'D', doubleBatteryPack, 'W', ScienceModBlocks.wire
				);
	}
}
