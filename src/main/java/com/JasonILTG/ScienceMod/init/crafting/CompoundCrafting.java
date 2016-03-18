package com.JasonILTG.ScienceMod.init.crafting;

import com.JasonILTG.ScienceMod.init.ScienceModItems;
import com.JasonILTG.ScienceMod.reference.chemistry.CommonCompounds;

import net.minecraft.init.Items;
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
		// Shapeless recipes for water bucket -> water jars
		GameRegistry.addShapelessRecipe(CommonCompounds.getWater(8), 
				Items.water_bucket, ScienceModItems.jar, ScienceModItems.jar, ScienceModItems.jar, ScienceModItems.jar,
				ScienceModItems.jar, ScienceModItems.jar, ScienceModItems.jar, ScienceModItems.jar);
		GameRegistry.addShapelessRecipe(CommonCompounds.getWater(7), 
				Items.water_bucket, ScienceModItems.jar, ScienceModItems.jar, ScienceModItems.jar,
				ScienceModItems.jar, ScienceModItems.jar, ScienceModItems.jar, ScienceModItems.jar);
		GameRegistry.addShapelessRecipe(CommonCompounds.getWater(6), 
				Items.water_bucket, ScienceModItems.jar, ScienceModItems.jar,
				ScienceModItems.jar, ScienceModItems.jar, ScienceModItems.jar, ScienceModItems.jar);
		GameRegistry.addShapelessRecipe(CommonCompounds.getWater(5), 
				Items.water_bucket, ScienceModItems.jar,
				ScienceModItems.jar, ScienceModItems.jar, ScienceModItems.jar, ScienceModItems.jar);
		GameRegistry.addShapelessRecipe(CommonCompounds.getWater(4), 
				Items.water_bucket, ScienceModItems.jar, ScienceModItems.jar, ScienceModItems.jar, ScienceModItems.jar);
		GameRegistry.addShapelessRecipe(CommonCompounds.getWater(3), 
				Items.water_bucket, ScienceModItems.jar, ScienceModItems.jar, ScienceModItems.jar);
		GameRegistry.addShapelessRecipe(CommonCompounds.getWater(2), 
				Items.water_bucket, ScienceModItems.jar, ScienceModItems.jar);
		GameRegistry.addShapelessRecipe(CommonCompounds.getWater(1), 
				Items.water_bucket, ScienceModItems.jar);
	}
}
