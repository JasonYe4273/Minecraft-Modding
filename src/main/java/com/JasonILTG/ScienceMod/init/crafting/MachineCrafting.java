package com.JasonILTG.ScienceMod.init.crafting;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

import com.JasonILTG.ScienceMod.init.ScienceModBlocks;

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
	}
}
