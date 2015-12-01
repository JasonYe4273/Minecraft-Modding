package com.JasonILTG.ScienceMod.gui;

import com.JasonILTG.ScienceMod.gui.general.MachineGUIContainer;
import com.JasonILTG.ScienceMod.gui.general.ScienceSlot;
import com.JasonILTG.ScienceMod.reference.Textures;
import com.JasonILTG.ScienceMod.tileentity.general.TEMachine;

import net.minecraft.inventory.IInventory;

public class CentrifugeGUIContainer extends MachineGUIContainer
{
	protected static final int JAR_INPUT_SLOT_ID = 0;
	protected static final int INPUT_SLOT_ID = 1;
	protected static final int[] OUTPUT_SLOTS_ID = { 2, 3, 4, 5 };
	
	protected static final int INPUT_SLOT_X = 79;
	protected static final int INPUT_SLOT_Y = 18;
	protected static final int JAR_INPUT_SLOT_X = 105;
	protected static final int JAR_INPUT_SLOT_Y = 18;
	protected static final int[] OUTPUT_SLOTS_X = { 66, 92, 118, 144 };
	protected static final int[] OUTPUT_SLOTS_Y = { 58, 58, 58, 58 };
	
	protected static final int PLAYER_INV_Y = Textures.GUI.CENTRIFUGE_GUI_HEIGHT + 22;
	
	public CentrifugeGUIContainer(IInventory playerInv, TEMachine te)
	{
		super(te, 6, PLAYER_INV_Y);
		addSlots();
		super.addPlayerInventorySlots(playerInv);
	}
	
	public void addSlots()
	{
		// Input, ID 0
		this.addSlotToContainer(new ScienceSlot(inventory, INPUT_SLOT_ID, INPUT_SLOT_X, INPUT_SLOT_Y));
		
		// Jar Input, ID 1
		this.addSlotToContainer(new JarSlot(inventory, JAR_INPUT_SLOT_ID, JAR_INPUT_SLOT_X, JAR_INPUT_SLOT_Y));
		
		// Outputs, IDs 2-5
		for (int i = 0; i < OUTPUT_SLOTS_ID.length; i ++)
			this.addSlotToContainer(new ScienceSlot(inventory, OUTPUT_SLOTS_ID[i], OUTPUT_SLOTS_X[i], OUTPUT_SLOTS_Y[i]));
	}
}
