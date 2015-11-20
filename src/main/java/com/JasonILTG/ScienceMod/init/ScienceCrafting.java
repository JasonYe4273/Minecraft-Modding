package com.JasonILTG.ScienceMod.init;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ScienceCrafting
{
	public static void init()
	{
		GameRegistry.addRecipe(new ItemStack(ScienceItems.jar, 4), new Object[]
		{
			" X ",
			"O O",
			"OOO",
			'O', Blocks.glass_pane, 'X', Blocks.planks
		});
		
		GameRegistry.addRecipe(new ItemStack(ScienceModBlocks.electrolyzer),
			"III",
			"RWR",
			"III",
			'I', Items.iron_ingot, 'R', Items.redstone, 'W', Items.water_bucket
			);
	}
}
