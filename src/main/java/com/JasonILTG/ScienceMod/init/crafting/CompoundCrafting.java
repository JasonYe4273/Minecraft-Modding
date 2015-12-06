package com.JasonILTG.ScienceMod.init.crafting;

import com.JasonILTG.ScienceMod.init.ScienceModItems;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

/**
 * Init class for all recipes for compounds
 * 
 * @author JasonILTG and syy1125
 */
public class CompoundCrafting
{
	/**
	 * Initializes all recipes for compounds
	 */
	public static void init()
	{
		//Shapeless recipes for water bucket -> water jars and water jars -> bucket
		GameRegistry.addShapelessRecipe(new ItemStack(ScienceModItems.water, 4), 
				Items.water_bucket, ScienceModItems.jar, ScienceModItems.jar, ScienceModItems.jar, ScienceModItems.jar);
		GameRegistry.addShapelessRecipe(new ItemStack(ScienceModItems.water, 3), 
				Items.water_bucket, ScienceModItems.jar, ScienceModItems.jar, ScienceModItems.jar);
		GameRegistry.addShapelessRecipe(new ItemStack(ScienceModItems.water, 2), 
				Items.water_bucket, ScienceModItems.jar, ScienceModItems.jar);
		GameRegistry.addShapelessRecipe(new ItemStack(ScienceModItems.water, 1), 
				Items.water_bucket, ScienceModItems.jar);
		GameRegistry.addShapelessRecipe(new ItemStack(Items.water_bucket, 1),
				Items.bucket, ScienceModItems.water, ScienceModItems.water, ScienceModItems.water, ScienceModItems.water);
	}
}
