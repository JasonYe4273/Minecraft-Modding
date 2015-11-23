package com.JasonILTG.ScienceMod.tileentity;

import net.minecraft.item.ItemStack;

public interface MachineRecipe
{
	boolean canProcessUsing(Object... params);
	
	ItemStack[] getItemOutputs();
}
