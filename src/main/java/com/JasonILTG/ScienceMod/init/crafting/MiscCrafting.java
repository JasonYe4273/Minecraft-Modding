package com.JasonILTG.ScienceMod.init.crafting;

import com.JasonILTG.ScienceMod.init.ScienceModItems;

import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class MiscCrafting
{
	public static void init()
	{
		//Recipe for jars
		GameRegistry.addRecipe(new ItemStack(ScienceModItems.jar, 4),
			" X ",
			"O O",
			"OOO",
			'O', Blocks.glass_pane, 'X', Blocks.planks
			);
	}
}
