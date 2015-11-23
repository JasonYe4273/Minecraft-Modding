package com.JasonILTG.ScienceMod.gui;

import com.JasonILTG.ScienceMod.tileentity.TEElectrolyzer;

import net.minecraft.inventory.IInventory;

public class ElectrolyzerGUIContainer extends InventoryGUIContainer
{
	public ElectrolyzerGUIContainer(IInventory playerInv)
	{
		super(playerInv, new TEElectrolyzer());
	}
}
