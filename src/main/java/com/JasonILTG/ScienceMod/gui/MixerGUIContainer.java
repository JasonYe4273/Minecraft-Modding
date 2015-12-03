package com.JasonILTG.ScienceMod.gui;

import com.JasonILTG.ScienceMod.gui.general.MachineGUIContainer;
import com.JasonILTG.ScienceMod.gui.general.ScienceSlot;
import com.JasonILTG.ScienceMod.reference.Textures;
import com.JasonILTG.ScienceMod.tileentity.general.TEMachine;

import net.minecraft.inventory.IInventory;

public class MixerGUIContainer extends MachineGUIContainer
{
	protected static final int ITEM_INPUT_SLOT_ID = 0;
	protected static final int JAR_OUTPUT_SLOT_ID = 1;
	protected static final int JAR_INPUT_SLOT_ID = 2;
	protected static final int ITEM_OUTPUT_SLOT_ID = 3;
	protected static final int ITEM_INPUT_SLOT_X = 79;
	protected static final int ITEM_INPUT_SLOT_Y = 18;
	protected static final int JAR_OUTPUT_SLOT_X = 105;
	protected static final int JAR_OUTPUT_SLOT_Y = 18;
	protected static final int JAR_INPUT_SLOT_X = 79;
	protected static final int JAR_INPUT_SLOT_Y = 58;
	protected static final int ITEM_OUTPUT_SLOT_X = 105;
	protected static final int ITEM_OUTPUT_SLOT_Y = 58;
	
	protected static final int PLAYER_INV_Y = Textures.GUI.MIXER_GUI_HEIGHT + 22;
	
	public MixerGUIContainer(IInventory playerInv, TEMachine te)
	{
		super(te, 4, PLAYER_INV_Y);
		addSlots();
		super.addPlayerInventorySlots(playerInv);
	}
	
	public void addSlots()
	{
		this.addSlotToContainer(new ScienceSlot(inventory, ITEM_INPUT_SLOT_ID, ITEM_INPUT_SLOT_X, ITEM_INPUT_SLOT_Y));
		
		this.addSlotToContainer(new JarSlot(inventory, JAR_OUTPUT_SLOT_ID, JAR_OUTPUT_SLOT_X, JAR_OUTPUT_SLOT_Y));
		
		this.addSlotToContainer(new JarSlot(inventory, JAR_INPUT_SLOT_ID, JAR_INPUT_SLOT_X, JAR_INPUT_SLOT_Y));
		
		this.addSlotToContainer(new ScienceSlot(inventory, ITEM_OUTPUT_SLOT_ID, ITEM_OUTPUT_SLOT_X, ITEM_OUTPUT_SLOT_Y));
	}
}
