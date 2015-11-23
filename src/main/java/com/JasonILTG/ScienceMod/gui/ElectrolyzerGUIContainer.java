package com.JasonILTG.ScienceMod.gui;

import net.minecraft.inventory.IInventory;

import com.JasonILTG.ScienceMod.tileentity.TEInventory;

public class ElectrolyzerGUIContainer extends InventoryGUIContainer
{
	protected static final int INPUT_SLOT_ID = 36;
	protected static final int JAR_INPUT_SLOT_ID = 37;
	protected static final int[] OUTPUT_IDS = { 38, 39 };
	
	public ElectrolyzerGUIContainer(IInventory playerInv, TEInventory te)
	{
		super(te, 4, 84);
		addSlots();
		super.addPlayerInventorySlots(playerInv);
	}
	
	public void addSlots()
	{
		// Input, ID 0
		// Jar Input, ID 1
		// Outputs, IDs 2 and 3
		for (int y = 0; y < 2; y++)
		{
			for (int x = 0; x < 2; x++)
			{
				this.addSlotToContainer(new ScienceSlot(te, x + y * 2, 62 + x * 18, 17 + y * 18));
			}
		}
	}
}
