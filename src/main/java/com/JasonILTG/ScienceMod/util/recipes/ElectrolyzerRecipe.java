package com.JasonILTG.ScienceMod.util.recipes;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

import com.JasonILTG.ScienceMod.init.ScienceItems;

public enum ElectrolyzerRecipe
{
	WaterSplitting(new ItemStack(ScienceItems.jar, 1), 3, new FluidStack(FluidRegistry.WATER, 1000),
			new ItemStack[] { new ItemStack(ScienceItems.element, 2, 1), new ItemStack(ScienceItems.element, 1, 1) });
	
	public final ItemStack reqItemStack;
	public final int reqJarCount;
	public final FluidStack reqFluidStack;
	public final ItemStack[] outItemStack;
	
	ElectrolyzerRecipe(ItemStack requiredItemStack, int requiredJarCount, FluidStack requiredFluidStack, ItemStack[] outputItemStack)
	{
		reqItemStack = requiredItemStack;
		reqJarCount = requiredJarCount;
		reqFluidStack = requiredFluidStack;
		outItemStack = outputItemStack;
	}
	
	public boolean canCraftUsing(ItemStack inputItemStack, int inputJarCount, FluidStack inputFluidStack)
	{
		// See if the items required are provided.
		boolean itemMatch = true;
		if (reqItemStack != null)
		{
			// null check
			if (inputItemStack == null) return false;
			
			itemMatch = inputItemStack.isItemEqual(reqItemStack) && inputItemStack.stackSize >= reqItemStack.stackSize;
		}
		
		// See if the jars required are provided
		boolean jarMatch = inputJarCount >= reqJarCount;
		
		// See if the required fluids are provided
		boolean fluidMatch = true;
		if (reqFluidStack != null)
		{
			if (inputFluidStack == null) return false;
			
			fluidMatch = inputFluidStack.containsFluid(reqFluidStack);
		}
		
		return itemMatch && jarMatch && fluidMatch;
	}
}
