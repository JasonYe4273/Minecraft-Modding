package com.JasonILTG.ScienceMod.compat.jei;

import java.util.ArrayList;
import java.util.List;

import com.JasonILTG.ScienceMod.init.ScienceModItems;

import mezz.jei.api.recipe.BlankRecipeWrapper;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

/**
 * JEI recipe class for all machine recipes.
 * 
 * @author JasonILTG and syy1125
 */
public class MachineRecipeJEI extends BlankRecipeWrapper
{
	private final List<ItemStack> inputs;
	private final List<ItemStack> outputs;
	private final int jarIndex;
	
	private final List<FluidStack> fluidInputs;
	private final List<FluidStack> fluidOutputs;
	
	public MachineRecipeJEI(ItemStack[] in, ItemStack[] out, int numJars, boolean jarIn, FluidStack[] fluidIn, FluidStack[] fluidOut)
	{
		inputs = new ArrayList<ItemStack>();
		if (in != null) for (int i = 0; i < in.length; i++) if (in[i] != null) inputs.add(in[i]);
		
		outputs = new ArrayList<ItemStack>();
		for (int i = 0; i < out.length; i++) if (out[i] != null) outputs.add(out[i]);

		if (numJars > 0)
		{
			if (jarIn)
			{
				jarIndex = inputs.size();
				inputs.add(new ItemStack(ScienceModItems.jar));
			}
			else
			{
				jarIndex = outputs.size();
				outputs.add(new ItemStack(ScienceModItems.jar));
			}
		}
		else jarIndex = -1;
		
		fluidInputs = new ArrayList<FluidStack>();
		if (fluidIn != null) for (int i = 0; i < fluidIn.length; i++) if (fluidIn[i] != null) fluidInputs.add(fluidIn[i]);
		
		fluidOutputs = new ArrayList<FluidStack>();
		if (fluidOut != null) for (int i = 0; i < fluidOut.length; i++) if (fluidOut[i] != null) fluidOutputs.add(fluidOut[i]);
	}
	
	@Override
	public List<ItemStack> getInputs()
	{
		return inputs;
	}

	@Override
	public List<ItemStack> getOutputs()
	{
		return outputs;
	}

	@Override
	public List<FluidStack> getFluidInputs()
	{
		return fluidInputs;
	}

	@Override
	public List<FluidStack> getFluidOutputs()
	{
		return fluidOutputs;
	}
	
	public int getJarIndex()
	{
		return jarIndex;
	}
}
