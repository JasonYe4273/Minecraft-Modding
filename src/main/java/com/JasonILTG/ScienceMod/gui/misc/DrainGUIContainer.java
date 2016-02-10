package com.JasonILTG.ScienceMod.gui.misc;

import com.JasonILTG.ScienceMod.gui.general.InventoryGUIContainer;
import com.JasonILTG.ScienceMod.gui.slots.ScienceSlot;
import com.JasonILTG.ScienceMod.reference.Textures;
import com.JasonILTG.ScienceMod.tileentity.misc.TEDrain;

import net.minecraft.inventory.IInventory;

/**
 * Container class for drains.
 * 
 * @author JasonILTG and syy1125
 */
public class DrainGUIContainer extends InventoryGUIContainer
{
	protected static final int[] INPUT_SLOTS_ID = new int[9];
	{
		for (int i = 0; i < INPUT_SLOTS_ID.length; i ++)
			INPUT_SLOTS_ID[i] = i;
	}
	protected static final int[] OUTPUT_SLOTS_ID = new int[9];
	{
		for (int i = 0; i < OUTPUT_SLOTS_ID.length; i ++)
			OUTPUT_SLOTS_ID[i] = i + 9;
	}
	
	protected static final int[] INPUT_SLOTS_X = new int[9];
	{
		for (int i = 0; i < INPUT_SLOTS_X.length; i ++)
			INPUT_SLOTS_X[i] = 25 + 18 * (i % 3);
	}
	protected static final int[] INPUT_SLOTS_Y = new int[9];
	{
		for (int i = 0; i < INPUT_SLOTS_Y.length; i ++)
			INPUT_SLOTS_Y[i] = 18 + (i / 3) * 18;
	}
	protected static final int[] OUTPUT_SLOTS_X = new int[9];
	{
		for (int i = 0; i < OUTPUT_SLOTS_X.length; i ++)
			OUTPUT_SLOTS_X[i] = 100 + 18 * (i % 3);
	}
	protected static final int[] OUTPUT_SLOTS_Y = new int[9];
	{
		for (int i = 0; i < OUTPUT_SLOTS_Y.length; i ++)
			OUTPUT_SLOTS_Y[i] = 18 + (i / 3) * 18;
	}
	
	protected static final int PLAYER_INV_Y = Textures.GUI.Misc.DRAIN_GUI_HEIGHT + 22;
	
	public DrainGUIContainer(IInventory playerInv, TEDrain te)
	{
		super(te, 18, PLAYER_INV_Y);
		addSlots();
		super.addPlayerInventorySlots(playerInv);
	}
	
	public void addSlots()
	{
		for (int i = 0; i < INPUT_SLOTS_ID.length; i++)
			this.addSlotToContainer(new ScienceSlot(inventory, INPUT_SLOTS_ID[i], INPUT_SLOTS_X[i], INPUT_SLOTS_Y[i]));
		
		for (int i = 0; i < OUTPUT_SLOTS_ID.length; i++)
			this.addSlotToContainer(new ScienceSlot(inventory, OUTPUT_SLOTS_ID[i], OUTPUT_SLOTS_X[i], OUTPUT_SLOTS_Y[i]));
	}
}
