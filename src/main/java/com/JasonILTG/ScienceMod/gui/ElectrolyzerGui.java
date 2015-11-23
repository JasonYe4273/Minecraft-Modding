package com.JasonILTG.ScienceMod.gui;

import com.JasonILTG.ScienceMod.tileentity.TEInventory;

import net.minecraft.inventory.IInventory;

public class ElectrolyzerGUI extends InventoryGUI
{
	public static final int GUI_ID = 20;
	
	public ElectrolyzerGUI(IInventory playerInv, TEInventory te)
	{
		super(new ElectrolyzerGUIContainer(playerInv, te));
	}
	/*
	@Override
	public void drawScreen(int x, int y, float f)
	{
		this.drawDefaultBackground();
	}
	*/
}
