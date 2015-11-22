package com.JasonILTG.ScienceMod.init.crafting;

import com.JasonILTG.ScienceMod.init.ScienceModBlocks;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class MachineCrafting
{
	public static void init()
	{
		//Recipe for electrolyzer
		GameRegistry.addRecipe(new ItemStack(ScienceModBlocks.electrolyzer),
			"III",
			"RWR",
			"III",
			'I', Items.iron_ingot, 'R', Items.redstone, 'W', Items.water_bucket
			);
	}
}
