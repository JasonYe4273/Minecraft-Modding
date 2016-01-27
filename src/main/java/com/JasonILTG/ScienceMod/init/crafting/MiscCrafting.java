package com.JasonILTG.ScienceMod.init.crafting;

import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.ShapedOreRecipe;

import com.JasonILTG.ScienceMod.init.ScienceModItems;

/**
 * Init class for all miscellaneous recipes.
 * 
 * @author JasonILTG and syy1125
 */
public class MiscCrafting
{
	/**
	 * Initializes all miscellaneous recipes.
	 */
	public static void init()
	{
		// Recipe for jars
		GameRegistry.addRecipe(new ItemStack(ScienceModItems.jar, 4),
				" X ",
				"O O",
				"OOO",
				'O', Blocks.glass_pane, 'X', Blocks.planks
				);
		
		// Wires
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ScienceModItems.wireCoil, 4),
				"IRI",
				"RGR",
				"IRI",
				'I', "ingotIron", 'R', "dustRedstone", 'G', "ingotGold"));
	}
}
