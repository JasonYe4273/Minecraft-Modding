package com.JasonILTG.ScienceMod.gui;

import com.JasonILTG.ScienceMod.gui.general.InventoryGUIContainer;
import com.JasonILTG.ScienceMod.gui.general.ScienceSlot;
import com.JasonILTG.ScienceMod.tileentity.general.TEInventory;

import net.minecraft.inventory.IInventory;

public class AirExtractorGUIContainer extends InventoryGUIContainer
{
	protected static final int JAR_INPUT_SLOT_ID = 0;
	protected static final int[] OUTPUT_SLOTS_ID = { 1, 2, 3, 4 };
	
	protected static final int JAR_INPUT_SLOT_X = 105;
	protected static final int JAR_INPUT_SLOT_Y = 18;
	protected static final int[] OUTPUT_SLOTS_X = { 66, 79, 92, 105 };
	protected static final int[] OUTPUT_SLOTS_Y = { 58, 58, 58, 58 };
	
	protected static final int PLAYER_INV_Y = 104;
	
	public AirExtractorGUIContainer(IInventory playerInv, TEInventory te)
	{
		super(te, 5, PLAYER_INV_Y);
		addSlots();
		super.addPlayerInventorySlots(playerInv);
	}
	
	public void addSlots()
	{
		// Jar Input, ID 0
		this.addSlotToContainer(new JarSlot(te, JAR_INPUT_SLOT_ID, JAR_INPUT_SLOT_X, JAR_INPUT_SLOT_Y));
		
		// Outputs, IDs 1-4
		for( int i = 0; i < OUTPUT_SLOTS_ID.length; i++ )
			this.addSlotToContainer(new ScienceSlot(te, OUTPUT_SLOTS_ID[ i ], OUTPUT_SLOTS_X[ i ], OUTPUT_SLOTS_Y[ i ]));
	}
}