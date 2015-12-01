package com.JasonILTG.ScienceMod.gui;

import com.JasonILTG.ScienceMod.gui.general.MachineGUIContainer;
import com.JasonILTG.ScienceMod.gui.general.ScienceSlot;
import com.JasonILTG.ScienceMod.reference.Textures;
import com.JasonILTG.ScienceMod.tileentity.general.TEMachine;

import net.minecraft.inventory.IInventory;

public class CondenserGUIContainer extends MachineGUIContainer
{
	protected static final int JAR_INPUT_SLOT_ID = 0;
	protected static final int OUTPUT_SLOT_ID = 1;
	protected static final int JAR_INPUT_SLOT_X = 81;
	protected static final int JAR_INPUT_SLOT_Y = 27;
	protected static final int OUTPUT_SLOT_X = 81;
	protected static final int OUTPUT_SLOT_Y = 49;
	
	protected static final int PLAYER_INV_Y = Textures.GUI.CONDENSER_GUI_HEIGHT + 22;
	
	public CondenserGUIContainer(IInventory playerInv, TEMachine te)
	{
		super(te, 2, PLAYER_INV_Y);
		addSlots();
		super.addPlayerInventorySlots(playerInv);
	}
	
	public void addSlots()
	{
		// Jar Input, ID 0
		this.addSlotToContainer(new JarSlot(inventory, JAR_INPUT_SLOT_ID, JAR_INPUT_SLOT_X, JAR_INPUT_SLOT_Y));
		
		// Output, ID 1
		this.addSlotToContainer(new ScienceSlot(inventory, OUTPUT_SLOT_ID, OUTPUT_SLOT_X, OUTPUT_SLOT_Y));
	}
}
