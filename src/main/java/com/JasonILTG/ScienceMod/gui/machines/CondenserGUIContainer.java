package com.JasonILTG.ScienceMod.gui.machines;

import com.JasonILTG.ScienceMod.gui.slots.JarSlot;
import com.JasonILTG.ScienceMod.gui.slots.ScienceSlot;
import com.JasonILTG.ScienceMod.gui.slots.UpgradeSlot;
import com.JasonILTG.ScienceMod.reference.Textures;
import com.JasonILTG.ScienceMod.tileentity.general.TEInventory;
import com.JasonILTG.ScienceMod.tileentity.machines.TEMachine;

import net.minecraft.inventory.IInventory;

/**
 * Container class for condensers.
 * 
 * @author JasonILTG and syy1125
 */
public class CondenserGUIContainer extends MachineGUIContainer
{
	protected static final int[] UPGRADE_SLOTS_ID = { 0, 1 };
	protected static final int JAR_INPUT_SLOT_ID = 2;
	protected static final int OUTPUT_SLOT_ID = 3;
	
	protected static final int[] UPGRADE_SLOTS_X = { Textures.GUI.UPGRADE_SLOT_X + Textures.GUI.Machine.CONDENSER_GUI_WIDTH, Textures.GUI.UPGRADE_SLOT_X + Textures.GUI.Machine.CONDENSER_GUI_WIDTH };
	protected static final int[] UPGRADE_SLOTS_Y = { Textures.GUI.UPGRADE_SLOT_1_Y, Textures.GUI.UPGRADE_SLOT_2_Y };
	protected static final int JAR_INPUT_SLOT_X = 74;
	protected static final int JAR_INPUT_SLOT_Y = 27;
	protected static final int OUTPUT_SLOT_X = 74;
	protected static final int OUTPUT_SLOT_Y = 49;
	
	protected static final int PLAYER_INV_Y = Textures.GUI.Machine.CONDENSER_GUI_HEIGHT + 22;
	
	/**
	 * Constructor.
	 * 
	 * @param playerInv The player inventory
	 * @param te The tile entity of the container
	 */
	public CondenserGUIContainer(IInventory playerInv, TEMachine te)
	{
		super(te, 4, PLAYER_INV_Y);
		addSlots();
		super.addPlayerInventorySlots(playerInv);
	}
	
	@Override
	public void addSlots()
	{
		for (int i = 0; i < UPGRADE_SLOTS_ID.length; i++)
			this.addSlotToContainer(new UpgradeSlot((TEInventory) inventory, UPGRADE_SLOTS_ID[i], UPGRADE_SLOTS_X[i], UPGRADE_SLOTS_Y[i]));
		
		this.addSlotToContainer(new JarSlot(inventory, JAR_INPUT_SLOT_ID, JAR_INPUT_SLOT_X, JAR_INPUT_SLOT_Y));
		
		this.addSlotToContainer(new ScienceSlot(inventory, OUTPUT_SLOT_ID, OUTPUT_SLOT_X, OUTPUT_SLOT_Y));
	}
}
