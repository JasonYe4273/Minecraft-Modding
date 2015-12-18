package com.JasonILTG.ScienceMod.gui.machines;

import com.JasonILTG.ScienceMod.gui.slots.JarSlot;
import com.JasonILTG.ScienceMod.gui.slots.ScienceSlot;
import com.JasonILTG.ScienceMod.gui.slots.UpgradeSlot;
import com.JasonILTG.ScienceMod.reference.Textures;
import com.JasonILTG.ScienceMod.tileentity.general.TEInventory;
import com.JasonILTG.ScienceMod.tileentity.machines.TEMachine;

import net.minecraft.inventory.IInventory;

/**
 * Container class for chemical reactors.
 * 
 * @author JasonILTG and syy1125
 */
public class ChemReactorGUIContainer extends MachineGUIContainer
{
	protected static final int[] UPGRADE_SLOTS_ID = { 0, 1 };
	protected static final int JAR_INPUT_SLOT_ID = 2;
	protected static final int[] INPUT_SLOTS_ID = { 3, 4, 5 };
	protected static final int[] OUTPUT_SLOTS_ID = { 6, 7, 8 };
	
	protected static final int[] UPGRADE_SLOTS_X = { Textures.GUI.UPGRADE_SLOT_X + Textures.GUI.Machine.CHEM_REACTOR_GUI_WIDTH, Textures.GUI.UPGRADE_SLOT_X + Textures.GUI.Machine.CHEM_REACTOR_GUI_WIDTH };
	protected static final int[] UPGRADE_SLOTS_Y = { Textures.GUI.UPGRADE_SLOT_1_Y, Textures.GUI.UPGRADE_SLOT_2_Y };
	protected static final int[] INPUT_SLOTS_X = { 36, 36, 36 };
	protected static final int[] INPUT_SLOTS_Y = { 18, 40, 62 };
	protected static final int JAR_INPUT_SLOT_X = 112;
	protected static final int JAR_INPUT_SLOT_Y = 18;
	protected static final int[] OUTPUT_SLOTS_X = { 86, 86, 86 };
	protected static final int[] OUTPUT_SLOTS_Y = { 18, 40, 62 };
	
	protected static final int PLAYER_INV_Y = Textures.GUI.Machine.CHEM_REACTOR_GUI_HEIGHT + 22;
	
	/**
	 * Constructor.
	 * 
	 * @param playerInv The player inventory
	 * @param te The tile entity for this container
	 */
	public ChemReactorGUIContainer(IInventory playerInv, TEMachine te)
	{
		super(te, 9, PLAYER_INV_Y);
		addSlots();
		super.addPlayerInventorySlots(playerInv);
	}
	
	@Override
	public void addSlots()
	{
		for (int i = 0; i < UPGRADE_SLOTS_ID.length; i++)
			this.addSlotToContainer(new UpgradeSlot((TEInventory) inventory, UPGRADE_SLOTS_ID[i], UPGRADE_SLOTS_X[i], UPGRADE_SLOTS_Y[i]));
		
		this.addSlotToContainer(new JarSlot(inventory, JAR_INPUT_SLOT_ID, JAR_INPUT_SLOT_X, JAR_INPUT_SLOT_Y));
		
		for (int i = 0; i < INPUT_SLOTS_ID.length; i ++)
			this.addSlotToContainer(new ScienceSlot(inventory, INPUT_SLOTS_ID[i], INPUT_SLOTS_X[i], INPUT_SLOTS_Y[i]));
		
		for (int i = 0; i < OUTPUT_SLOTS_ID.length; i ++)
			this.addSlotToContainer(new ScienceSlot(inventory, OUTPUT_SLOTS_ID[i], OUTPUT_SLOTS_X[i], OUTPUT_SLOTS_Y[i]));
	}
}