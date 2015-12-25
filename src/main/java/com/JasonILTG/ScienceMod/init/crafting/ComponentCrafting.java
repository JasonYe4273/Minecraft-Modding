package com.JasonILTG.ScienceMod.init.crafting;

import com.JasonILTG.ScienceMod.init.ScienceModBlocks;
import com.JasonILTG.ScienceMod.init.ScienceModItems;
import com.JasonILTG.ScienceMod.item.component.battery.BatteryLevel;
import com.JasonILTG.ScienceMod.item.component.hull.MaterialHeat;
import com.JasonILTG.ScienceMod.reference.NBTKeys;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.registry.GameRegistry;

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
		ItemStack ironHull = new ItemStack(ScienceModItems.hull, 1, MaterialHeat.IRON.ordinal());
		ironHull.setTagCompound(MaterialHeat.IRON.createHullTag());
		GameRegistry.addRecipe(ironHull,
				"III",
				"I I",
				"III",
				'I', Items.iron_ingot
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
