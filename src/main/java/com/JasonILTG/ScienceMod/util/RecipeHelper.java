package com.JasonILTG.ScienceMod.util;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;

/**
 * Helps determine recipe outputs.
 */
public class RecipeHelper
{
	public class Electrolyzer
	{
		public ItemStack getResult(FluidTank tank)
		{
			if (tank == null || tank.getFluid() == null) return null;
			
			FluidStack craftingFluid = tank.getFluid();
			if (craftingFluid.containsFluid(craftingFluid)) {
				
			}
			return new ItemStack(Items.apple, 1);
		}
	}
}
