package com.JasonILTG.ScienceMod.gui;

import com.JasonILTG.ScienceMod.tileentity.TEInventory;

import net.minecraft.inventory.IInventory;

public class ElectrolyzerGUIContainer extends InventoryGUIContainer
{
	protected static final int INPUT_SLOT_ID = 0;
	protected static final int JAR_INPUT_SLOT_ID = 1;
	protected static final int[] OUTPUT_SLOTS_ID = { 2, 3 };
	
	protected static final int INPUT_SLOT_X = 62;
	protected static final int INPUT_SLOT_Y = 17;
	protected static final int JAR_INPUT_SLOT_X = 80;
	protected static final int JAR_INPUT_SLOT_Y = 17;
	protected static final int[] OUTPUT_SLOTS_X = { 62, 80 };
	protected static final int[] OUTPUT_SLOTS_Y = { 35, 35 };
	
	protected static final int PLAYER_INV_Y = 100;
	
	public ElectrolyzerGUIContainer(IInventory playerInv, TEInventory te)
	{
		super(te, 4, PLAYER_INV_Y);
		addSlots();
		super.addPlayerInventorySlots(playerInv);
	}
	
	public void addSlots()
	{
		// Input, ID 0
		this.addSlotToContainer(new ScienceSlot(te, INPUT_SLOT_ID, INPUT_SLOT_X, INPUT_SLOT_Y));
		
		// Jar Input, ID 1
		this.addSlotToContainer(new JarSlot(te, JAR_INPUT_SLOT_ID, JAR_INPUT_SLOT_X, JAR_INPUT_SLOT_Y));
		
		// Outputs, IDs 2 and 3
		for( int i = 0; i < OUTPUT_SLOTS_ID.length; i++ )
			this.addSlotToContainer(new ScienceSlot(te, OUTPUT_SLOTS_ID[ i ], OUTPUT_SLOTS_X[ i ], OUTPUT_SLOTS_Y[ i ]));
		}
}
