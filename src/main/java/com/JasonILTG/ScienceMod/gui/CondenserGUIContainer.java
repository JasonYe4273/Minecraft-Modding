package com.JasonILTG.ScienceMod.gui;

import com.JasonILTG.ScienceMod.gui.general.InventoryGUIContainer;
import com.JasonILTG.ScienceMod.gui.general.ScienceSlot;
import com.JasonILTG.ScienceMod.reference.Textures;
import com.JasonILTG.ScienceMod.tileentity.general.TEInventory;

import net.minecraft.inventory.IInventory;

public class CondenserGUIContainer extends InventoryGUIContainer
{
	protected static final int JAR_INPUT_SLOTS_ID = 0;
	protected static final int OUTPUT_SLOTS_ID = 1;
	protected static final int JAR_INPUT_SLOTS_X = 40;
	protected static final int JAR_INPUT_SLOTS_Y = 18;
	protected static final int OUTPUT_SLOTS_X = 40;
	protected static final int OUTPUT_SLOTS_Y = 18;
	
	protected static final int PLAYER_INV_Y = Textures.GUI.CONDENSER_GUI_HEIGHT + 22;
	
	public CondenserGUIContainer(IInventory playerInv, TEInventory te)
	{
		super(te, 5, PLAYER_INV_Y);
		addSlots();
		super.addPlayerInventorySlots(playerInv);
	}
	
	public void addSlots()
	{
		// Jar Input, ID 0
		this.addSlotToContainer(new JarSlot(te, JAR_INPUT_SLOTS_ID, JAR_INPUT_SLOTS_X, JAR_INPUT_SLOTS_Y));
		
		// Output, ID 1
		this.addSlotToContainer(new ScienceSlot(te, OUTPUT_SLOTS_ID, OUTPUT_SLOTS_X, OUTPUT_SLOTS_Y));
	}
}
