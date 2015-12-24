/**
 * 
 */
package com.JasonILTG.ScienceMod.gui;

import com.JasonILTG.ScienceMod.gui.general.InventoryGUIContainer;
import com.JasonILTG.ScienceMod.gui.slots.ScienceSlot;
import com.JasonILTG.ScienceMod.reference.Textures;
import com.JasonILTG.ScienceMod.tileentity.TEAssembler;

import net.minecraft.inventory.IInventory;

/**
 * 
 * @author JasonILTG and syy1125
 */
public class AssemblerGUIContainer extends InventoryGUIContainer
{
	protected static final int[] INPUT_SLOTS_ID = new int[9];
	{
		for (int i = 0; i < INPUT_SLOTS_ID.length; i ++)
			INPUT_SLOTS_ID[i] = i;
	}
	protected static final int OUTPUT_SLOT_ID = 9;
	
	protected static final int[] INPUT_SLOTS_X = new int[9];
	{
		for (int i = 0; i < INPUT_SLOTS_X.length; i ++)
			INPUT_SLOTS_X[i] = 3 + 18 * (i % 3);
	}
	protected static final int[] INPUT_SLOTS_Y = new int[9];
	{
		for (int i = 0; i < INPUT_SLOTS_Y.length; i ++)
			INPUT_SLOTS_Y[i] = 18 + (i / 3) * 18;
	}
	protected static final int OUTPUT_SLOT_X = 75;
	protected static final int OUTPUT_SLOT_Y = 36;
	
	protected static final int PLAYER_INV_Y = Textures.GUI.Misc.ASSEMBLER_GUI_HEIGHT + 22;
	
	public AssemblerGUIContainer(IInventory playerInv, TEAssembler te)
	{
		super(te, 10, PLAYER_INV_Y);
		addSlots();
		super.addPlayerInventorySlots(playerInv);
	}
	
	public void addSlots()
	{
		for (int i = 0; i < INPUT_SLOTS_ID.length; i++)
			this.addSlotToContainer(new ScienceSlot(inventory, INPUT_SLOTS_ID[i], INPUT_SLOTS_X[i], INPUT_SLOTS_Y[i]));
		
		this.addSlotToContainer(new ScienceSlot(inventory, OUTPUT_SLOT_ID, OUTPUT_SLOT_X, OUTPUT_SLOT_Y));
	}
}
