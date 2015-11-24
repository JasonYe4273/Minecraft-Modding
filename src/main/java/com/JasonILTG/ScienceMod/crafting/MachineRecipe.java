package com.JasonILTG.ScienceMod.crafting;

import net.minecraft.item.ItemStack;

public interface MachineRecipe
{
	boolean canProcessUsing(Object... params);
	
	ItemStack[] getItemOutputs();
	
	int getTimeRequired();
}
