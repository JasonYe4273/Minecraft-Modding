package com.JasonILTG.ScienceMod.compat.jei.electrolyzer;

import com.JasonILTG.ScienceMod.compat.jei.MachineRecipeJEI;
import com.JasonILTG.ScienceMod.tileentity.machines.TEElectrolyzer;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

/**
 * JEI recipe class for electrolyzers.
 * 
 * @author JasonILTG and syy1125
 */
public class ElectrolyzerJEIRecipe extends MachineRecipeJEI
{
	public ElectrolyzerJEIRecipe(TEElectrolyzer.ElectrolyzerRecipe recipe)
	{
		super(new ItemStack[]{ recipe.reqItemStack }, recipe.outItemStack, recipe.reqJarCount, true, new FluidStack[] { recipe.reqFluidStack }, null);
	}
}
