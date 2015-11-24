package com.JasonILTG.ScienceMod.init.crafting;

import com.JasonILTG.ScienceMod.init.ScienceModBlocks;
import com.JasonILTG.ScienceMod.init.ScienceModItems;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class MachineCrafting
{
	public static void init()
	{
		// Temporary recipe for electrolyzer
		GameRegistry.addRecipe(new ItemStack(ScienceModBlocks.electrolyzer),
				"III",
				"RWR",
				"III",
				'I', Items.iron_ingot, 'R', Items.redstone, 'W', Items.water_bucket
				);
		// Temporary recipe for air extractor
		GameRegistry.addRecipe(new ItemStack(ScienceModBlocks.air_extractor),
				"IHI",
				"IJI",
				"ICI",
				'I', Items.iron_ingot, 'H', Blocks.hopper, 'J', ScienceModItems.jar, 'C', Blocks.chest
				);
	}
}
