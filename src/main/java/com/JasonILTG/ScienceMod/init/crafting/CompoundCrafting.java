package com.JasonILTG.ScienceMod.init.crafting;

import com.JasonILTG.ScienceMod.init.ScienceModItems;
import com.JasonILTG.ScienceMod.item.compounds.CompoundItem;
import com.JasonILTG.ScienceMod.reference.chemistry.CommonCompounds;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

/**
 * Init class for all recipes for compounds.
 * 
 * @author JasonILTG and syy1125
 */
public class CompoundCrafting
{
	/**
	 * Initializes all recipes for compounds.
	 */
	public static void init()
	{
		CompoundItem water = CommonCompounds.water;
		//Shapeless recipes for water bucket -> water jars and water jars -> bucket
		GameRegistry.addShapelessRecipe(new ItemStack(water, 4), 
				Items.water_bucket, ScienceModItems.jar, ScienceModItems.jar, ScienceModItems.jar, ScienceModItems.jar);
		GameRegistry.addShapelessRecipe(new ItemStack(water, 3), 
				Items.water_bucket, ScienceModItems.jar, ScienceModItems.jar, ScienceModItems.jar);
		GameRegistry.addShapelessRecipe(new ItemStack(water, 2), 
				Items.water_bucket, ScienceModItems.jar, ScienceModItems.jar);
		GameRegistry.addShapelessRecipe(new ItemStack(water, 1), 
				Items.water_bucket, ScienceModItems.jar);
		GameRegistry.addShapelessRecipe(new ItemStack(Items.water_bucket, 1),
				Items.bucket, water, water, water, water);
	}
}
