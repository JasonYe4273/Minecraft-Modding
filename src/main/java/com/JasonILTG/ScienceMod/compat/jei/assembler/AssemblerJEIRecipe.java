package com.JasonILTG.ScienceMod.compat.jei.assembler;

import com.JasonILTG.ScienceMod.compat.jei.MachineRecipeJEI;
import com.JasonILTG.ScienceMod.tileentity.component.TEAssembler;

import net.minecraft.item.ItemStack;

/**
 * JEI recipe class for assemblers.
 * 
 * @author JasonILTG and syy1125
 */
public class AssemblerJEIRecipe extends MachineRecipeJEI
{
	public AssemblerJEIRecipe(TEAssembler.AssemblerRecipe recipe)
	{
		super(recipe.inputItems, new ItemStack[] { recipe.outputItem }, 0, true, null, null);
	}
}
