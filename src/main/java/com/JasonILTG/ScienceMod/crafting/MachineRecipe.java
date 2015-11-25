package com.JasonILTG.ScienceMod.crafting;

import net.minecraft.item.ItemStack;

public interface MachineRecipe
{
	boolean canProcess(Object... params);
	
	ItemStack[] getItemOutputs();
	
	int getTimeRequired();
	
	int ordinal();
}
