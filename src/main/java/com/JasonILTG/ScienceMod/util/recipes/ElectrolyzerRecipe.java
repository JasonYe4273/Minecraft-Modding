package com.JasonILTG.ScienceMod.util.recipes;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

import com.JasonILTG.ScienceMod.init.ScienceItems;

public enum ElectrolyzerRecipe
{
	WaterSplitting(new ItemStack(ScienceItems.jar, 1), new FluidStack(FluidRegistry.WATER, 1000), new ItemStack[] {
			new ItemStack(ScienceItems.hydrogen, 2), new ItemStack(ScienceItems.oxygen, 1) });
	
	public final ItemStack reqItemStack;
	public final FluidStack reqFluidStack;
	public final ItemStack[] outItemStack;
	
	ElectrolyzerRecipe(ItemStack requiredItemStack, FluidStack requiredFluidStack, ItemStack[] outputItemStack)
	{
		reqItemStack = requiredItemStack;
		reqFluidStack = requiredFluidStack;
		outItemStack = outputItemStack;
	}
}
