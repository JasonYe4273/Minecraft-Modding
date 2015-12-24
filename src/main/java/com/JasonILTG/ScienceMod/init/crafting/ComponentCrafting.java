package com.JasonILTG.ScienceMod.init.crafting;

import com.JasonILTG.ScienceMod.init.ScienceModBlocks;
import com.JasonILTG.ScienceMod.init.ScienceModItems;
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
		ItemStack ironHull = new ItemStack(ScienceModItems.ironHull);
		ironHull.setTagCompound(MaterialHeat.IRON.createHullTag());
		GameRegistry.addRecipe(ironHull,
				"III",
				"I I",
				"III",
				'I', Items.iron_ingot
				);
		
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
		
		ItemStack battery = new ItemStack(ScienceModItems.battery, 2);
		NBTTagCompound batteryTag = new NBTTagCompound();
		NBTTagCompound capacityTag = new NBTTagCompound();
		capacityTag.setFloat(NBTKeys.Item.Component.CAPACITY, 100000F);
		batteryTag.setTag(NBTKeys.Item.Component.BATTERY, capacityTag);
		battery.setTagCompound(batteryTag);
		GameRegistry.addRecipe(battery,
				" W ",
				"IWI",
				"III",
				'I', Items.iron_ingot, 'W', ScienceModBlocks.wire
				);
	}
}
