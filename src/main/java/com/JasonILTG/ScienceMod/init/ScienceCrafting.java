package com.JasonILTG.ScienceMod.init;

import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ScienceCrafting
{
	public static void init()
	{
		//Recipe for jars
		GameRegistry.addRecipe(new ItemStack(ScienceItems.jar, 4),
			" X ",
			"O O",
			"OOO",
			'O', Blocks.glass_pane, 'X', Blocks.planks
			);
		
		//Recipe for electrolyzer
		GameRegistry.addRecipe(new ItemStack(ScienceModBlocks.electrolyzer),
			"III",
			"RWR",
			"III",
			'I', Items.iron_ingot, 'R', Items.redstone, 'W', Items.water_bucket
			);
		
		//Shapeless recipes for element -> jar
		for( int meta = 0; meta < ScienceItems.element.getNumSubtypes(); meta++ )
		{
			GameRegistry.addShapelessRecipe(new ItemStack(ScienceItems.jar, 1), new ItemStack(ScienceItems.element, 1, meta));
		}
	}
}
