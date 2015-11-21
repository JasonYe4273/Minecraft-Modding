package com.JasonILTG.ScienceMod.init.crafting;

import com.JasonILTG.ScienceMod.init.ScienceItems;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ElementCrafting
{
	public static void init()
	{
		//Shapeless recipes for element -> jar
		for( int meta = 0; meta < ScienceItems.element.getNumSubtypes(); meta++ )
		{
			GameRegistry.addShapelessRecipe(new ItemStack(ScienceItems.jar, 1), 
					new ItemStack(ScienceItems.element, 1, meta));
		}
	}
}
