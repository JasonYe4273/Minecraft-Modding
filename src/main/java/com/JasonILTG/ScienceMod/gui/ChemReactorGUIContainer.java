package com.JasonILTG.ScienceMod.gui;

import com.JasonILTG.ScienceMod.gui.general.InventoryGUIContainer;
import com.JasonILTG.ScienceMod.gui.general.ScienceSlot;
import com.JasonILTG.ScienceMod.reference.Textures;
import com.JasonILTG.ScienceMod.tileentity.general.TEInventory;

import net.minecraft.inventory.IInventory;

public class ChemReactorGUIContainer extends InventoryGUIContainer
{
	protected static final int JAR_INPUT_SLOT_ID = 0;
	protected static final int[] INPUT_SLOTS_ID = { 1, 2, 3 };
	protected static final int[] OUTPUT_SLOTS_ID = { 4, 5, 6 };
	
	protected static final int[] INPUT_SLOTS_X = { 42, 42, 42 };
	protected static final int[] INPUT_SLOTS_Y = { 18, 40, 62 };
	protected static final int JAR_INPUT_SLOT_X = 118;
	protected static final int JAR_INPUT_SLOT_Y = 18;
	protected static final int[] OUTPUT_SLOTS_X = { 92, 92, 92 };
	protected static final int[] OUTPUT_SLOTS_Y = { 18, 40, 62 };
	
	protected static final int PLAYER_INV_Y = Textures.GUI.ELECTROLYZER_GUI_HEIGHT + 22;
	
	public ChemReactorGUIContainer(IInventory playerInv, TEInventory te)
	{
		super(te, 7, PLAYER_INV_Y);
		addSlots();
		super.addPlayerInventorySlots(playerInv);
	}
	
	public void addSlots()
	{
		// Jar Input, ID 0
		this.addSlotToContainer(new JarSlot(inventory, JAR_INPUT_SLOT_ID, JAR_INPUT_SLOT_X, JAR_INPUT_SLOT_Y));
		
		// Input, IDs 1-3
		for (int i = 0; i < INPUT_SLOTS_ID.length; i ++)
			this.addSlotToContainer(new ScienceSlot(inventory, INPUT_SLOTS_ID[i], INPUT_SLOTS_X[i], INPUT_SLOTS_Y[i]));
		
		// Outputs, IDs 4-6
		for (int i = 0; i < OUTPUT_SLOTS_ID.length; i ++)
			this.addSlotToContainer(new ScienceSlot(inventory, OUTPUT_SLOTS_ID[i], OUTPUT_SLOTS_X[i], OUTPUT_SLOTS_Y[i]));
	}
}