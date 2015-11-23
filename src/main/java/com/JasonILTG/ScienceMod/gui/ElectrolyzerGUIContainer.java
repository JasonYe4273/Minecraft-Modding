package com.JasonILTG.ScienceMod.gui;

import com.JasonILTG.ScienceMod.tileentity.TEElectrolyzer;

import net.minecraft.inventory.IInventory;

public class ElectrolyzerGUIContainer extends InventoryGUIContainer
{
	protected static final int INPUT_SLOT_ID = 36;
	protected static final int JAR_INPUT_SLOT_ID = 37;
	protected static final int[] OUTPUT_IDS = { 38, 39 };
	
	public ElectrolyzerGUIContainer(IInventory playerInv)
	{
		super(playerInv, new TEElectrolyzer(), 84);
		addSlots();
	}
	
	public void addSlots()
	{
		//Input, ID 36
		//Jar Input, ID 37
		//Output 1, ID
	}
}
