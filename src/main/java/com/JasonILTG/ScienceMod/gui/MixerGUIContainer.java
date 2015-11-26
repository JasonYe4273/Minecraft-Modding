package com.JasonILTG.ScienceMod.gui;

import com.JasonILTG.ScienceMod.gui.general.InventoryGUIContainer;
import com.JasonILTG.ScienceMod.gui.general.ScienceSlot;
import com.JasonILTG.ScienceMod.reference.Textures;
import com.JasonILTG.ScienceMod.tileentity.general.TEInventory;

import net.minecraft.inventory.IInventory;

public class MixerGUIContainer extends InventoryGUIContainer
{
	protected static final int[] ITEM_INPUT_SLOTS_ID = { 0, 1, 2, 3 };
	protected static final int[] JAR_OUTPUT_SLOTS_ID = { 4, 5, 6, 7 };
	protected static final int JAR_INPUT_SLOT_ID = 8;
	protected static final int ITEM_OUTPUT_SLOT_ID = 9;
	protected static final int[] ITEM_INPUT_SLOTS_X = { 0, 0, 0, 0 };
	protected static final int[] ITEM_INPUT_SLOTS_Y = { 0, 0, 0, 0 };
	protected static final int[] JAR_OUTPUT_SLOTS_X = { 0, 0, 0, 0 };
	protected static final int[] JAR_OUTPUT_SLOTS_Y = { 0, 0, 0, 0 };
	protected static final int JAR_INPUT_SLOT_X = 0;
	protected static final int JAR_INPUT_SLOT_Y = 0;
	protected static final int ITEM_OUTPUT_SLOT_X = 0;
	protected static final int ITEM_OUTPUT_SLOT_Y = 0;
	
	protected static final int PLAYER_INV_Y = Textures.GUI.MIXER_GUI_HEIGHT + 22;
	
	public MixerGUIContainer(IInventory playerInv, TEInventory te)
	{
		super(te, 10, PLAYER_INV_Y);
		addSlots();
		super.addPlayerInventorySlots(playerInv);
	}
	
	public void addSlots()
	{
		for( int i = 0; i < ITEM_INPUT_SLOTS_ID.length; i++ )
			this.addSlotToContainer(new ScienceSlot(te, ITEM_INPUT_SLOTS_ID[i], ITEM_INPUT_SLOTS_X[i], ITEM_INPUT_SLOTS_Y[i]));
		
		for( int i = 0; i < ITEM_INPUT_SLOTS_ID.length; i++ )
			this.addSlotToContainer(new JarSlot(te, JAR_OUTPUT_SLOTS_ID[i], JAR_OUTPUT_SLOTS_X[i], JAR_OUTPUT_SLOTS_Y[i]));
		
		this.addSlotToContainer(new JarSlot(te, JAR_INPUT_SLOT_ID, JAR_INPUT_SLOT_X, JAR_INPUT_SLOT_Y));
		
		this.addSlotToContainer(new ScienceSlot(te, ITEM_OUTPUT_SLOT_ID, ITEM_OUTPUT_SLOT_X, ITEM_OUTPUT_SLOT_Y));
	}
}
