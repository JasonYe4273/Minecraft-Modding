package com.JasonILTG.ScienceMod.init.crafting;

import com.JasonILTG.ScienceMod.init.ScienceItems;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class CompoundCrafting
{
	public static void init()
	{
		//Shapeless recipes for water bucket -> water jars and water jars -> bucket
		GameRegistry.addShapelessRecipe(new ItemStack(ScienceItems.water, 4), 
				Items.water_bucket.setContainerItem(Items.bucket), ScienceItems.jar, ScienceItems.jar, ScienceItems.jar, ScienceItems.jar);
		GameRegistry.addShapelessRecipe(new ItemStack(ScienceItems.water, 3), 
				Items.water_bucket.setContainerItem(Items.bucket), ScienceItems.jar, ScienceItems.jar, ScienceItems.jar);
		GameRegistry.addShapelessRecipe(new ItemStack(ScienceItems.water, 2), 
				Items.water_bucket.setContainerItem(Items.bucket), ScienceItems.jar, ScienceItems.jar);
		GameRegistry.addShapelessRecipe(new ItemStack(ScienceItems.water, 1), 
				Items.water_bucket.setContainerItem(Items.bucket), ScienceItems.jar);
		GameRegistry.addShapelessRecipe(new ItemStack(Items.water_bucket, 1),
				Items.bucket, ScienceItems.water, ScienceItems.water, ScienceItems.water, ScienceItems.water);
	}
}
