package com.JasonILTG.ScienceMod.gui.machines;

import com.JasonILTG.ScienceMod.gui.slots.JarSlot;
import com.JasonILTG.ScienceMod.gui.slots.ScienceSlot;
import com.JasonILTG.ScienceMod.gui.slots.UpgradeSlot;
import com.JasonILTG.ScienceMod.reference.Textures;
import com.JasonILTG.ScienceMod.tileentity.general.TEInventory;
import com.JasonILTG.ScienceMod.tileentity.machines.TEMachine;

import net.minecraft.inventory.IInventory;

/**
 * Container class for mixers.
 * 
 * @author JasonILTG and syy1125
 */
public class MixerGUIContainer extends MachineGUIContainer
{
	protected static final int[] UPGRADE_SLOTS_ID = { 0, 1 };
	protected static final int ITEM_INPUT_SLOT_ID = 2;
	protected static final int JAR_OUTPUT_SLOT_ID = 3;
	protected static final int JAR_INPUT_SLOT_ID = 4;
	protected static final int ITEM_OUTPUT_SLOT_ID = 5;
	
	protected static final int[] UPGRADE_SLOTS_X = { Textures.GUI.UPGRADE_SLOT_X + (Textures.GUI.DEFAULT_GUI_X_SIZE + Textures.GUI.Machine.MIXER_GUI_WIDTH) / 2, Textures.GUI.UPGRADE_SLOT_X + (Textures.GUI.DEFAULT_GUI_X_SIZE + Textures.GUI.Machine.MIXER_GUI_WIDTH) / 2 };
	protected static final int[] UPGRADE_SLOTS_Y = { Textures.GUI.UPGRADE_SLOT_1_Y, Textures.GUI.UPGRADE_SLOT_2_Y };
	protected static final int ITEM_INPUT_SLOT_X = 73;
	protected static final int ITEM_INPUT_SLOT_Y = 18;
	protected static final int JAR_OUTPUT_SLOT_X = 99;
	protected static final int JAR_OUTPUT_SLOT_Y = 18;
	protected static final int JAR_INPUT_SLOT_X = 73;
	protected static final int JAR_INPUT_SLOT_Y = 58;
	protected static final int ITEM_OUTPUT_SLOT_X = 99;
	protected static final int ITEM_OUTPUT_SLOT_Y = 58;
	
	protected static final int PLAYER_INV_Y = Textures.GUI.Machine.MIXER_GUI_HEIGHT + 22;
	
	/**
	 * Constructor.
	 * 
	 * @param playerInv The player inventory
	 * @param te The tile entity for this container
	 */
	public MixerGUIContainer(IInventory playerInv, TEMachine te)
	{
		super(te, 6, PLAYER_INV_Y);
		addSlots();
		super.addPlayerInventorySlots(playerInv);
	}
	
	@Override
	public void addSlots()
	{
		for (int i = 0; i < UPGRADE_SLOTS_ID.length; i++)
			this.addSlotToContainer(new UpgradeSlot((TEInventory) inventory, UPGRADE_SLOTS_ID[i], UPGRADE_SLOTS_X[i], UPGRADE_SLOTS_Y[i]));
		
		this.addSlotToContainer(new ScienceSlot(inventory, ITEM_INPUT_SLOT_ID, ITEM_INPUT_SLOT_X, ITEM_INPUT_SLOT_Y));
		
		this.addSlotToContainer(new JarSlot(inventory, JAR_OUTPUT_SLOT_ID, JAR_OUTPUT_SLOT_X, JAR_OUTPUT_SLOT_Y));
		
		this.addSlotToContainer(new JarSlot(inventory, JAR_INPUT_SLOT_ID, JAR_INPUT_SLOT_X, JAR_INPUT_SLOT_Y));
		
		this.addSlotToContainer(new ScienceSlot(inventory, ITEM_OUTPUT_SLOT_ID, ITEM_OUTPUT_SLOT_X, ITEM_OUTPUT_SLOT_Y));
	}
}
