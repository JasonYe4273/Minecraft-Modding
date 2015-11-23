package com.JasonILTG.ScienceMod.gui;

import com.JasonILTG.ScienceMod.tileentity.TEInventory;

import net.minecraft.inventory.IInventory;

public class ElectrolyzerGUIContainer extends InventoryGUIContainer
{
	protected static final int INPUT_SLOT_ID = 36;
	protected static final int JAR_INPUT_SLOT_ID = 37;
	protected static final int[] OUTPUT_IDS = { 38, 39 };
	
	public ElectrolyzerGUIContainer(IInventory playerInv, TEInventory te)
	{
		super(playerInv, te, 84);
		addSlots();
	}
	
	public void addSlots()
	{
		//Input, ID 36
		//Jar Input, ID 37
	    for( int y = 0; y < 2; y++ )
	    {
	        for( int x = 0; x < 2; x++ )
	        {
	            this.addSlotToContainer(new ScienceSlot(te, x + y * 3, 62 + x * 18, 17 + y * 18));
	        }
	    }
	}
}
