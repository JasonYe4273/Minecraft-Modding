package com.JasonILTG.TestMod.init;

import com.JasonILTG.NumbersMod.init.NumbersItems;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class TestCrafting
{
	public static void init()
	{
		//Example shaped recipe
		GameRegistry.addRecipe(new ItemStack(Blocks.dragon_egg, 1), new Object[]
		{
			"XXX",
			"XXX",
			"XXX",
			'X', TestItems.test_item
		});
		
		//Exampe shapeless recipe
		GameRegistry.addShapelessRecipe(new ItemStack(TestItems.test_item, 1), Items.cake);
	}
}
