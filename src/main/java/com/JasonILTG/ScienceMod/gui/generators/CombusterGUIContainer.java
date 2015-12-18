package com.JasonILTG.ScienceMod.gui.generators;

import com.JasonILTG.ScienceMod.gui.slots.JarSlot;
import com.JasonILTG.ScienceMod.gui.slots.ScienceSlot;
import com.JasonILTG.ScienceMod.gui.slots.UpgradeSlot;
import com.JasonILTG.ScienceMod.reference.Textures;
import com.JasonILTG.ScienceMod.tileentity.general.TEInventory;
import com.JasonILTG.ScienceMod.tileentity.generators.TEGenerator;

import net.minecraft.inventory.IInventory;

/**
 * Container class for combusters.
 * 
 * @author JasonILTG and syy1125
 */
public class CombusterGUIContainer extends GeneratorGUIContainer
{
	protected static final int[] UPGRADE_SLOTS_ID = { 0, 1 };
	protected static final int JAR_OUTPUT_SLOT_ID = 2;
	protected static final int INPUT_SLOT_ID = 3;
	protected static final int OUTPUT_SLOT_ID = 4;

	protected static final int[] UPGRADE_SLOTS_X = { Textures.GUI.UPGRADE_SLOT_X + Textures.GUI.Generator.COMBUSTER_GUI_WIDTH, Textures.GUI.UPGRADE_SLOT_X + Textures.GUI.Generator.COMBUSTER_GUI_WIDTH };
	protected static final int[] UPGRADE_SLOTS_Y = { Textures.GUI.UPGRADE_SLOT_1_Y, Textures.GUI.UPGRADE_SLOT_2_Y };
	protected static final int JAR_OUTPUT_SLOT_X = 107;
	protected static final int JAR_OUTPUT_SLOT_Y = 58;
	protected static final int INPUT_SLOT_X = 85;
	protected static final int INPUT_SLOT_Y = 18;
	protected static final int OUTPUT_SLOT_X = 85;
	protected static final int OUTPUT_SLOT_Y = 58;
	
	protected static final int PLAYER_INV_Y = Textures.GUI.Generator.COMBUSTER_GUI_HEIGHT + 22;
	
	/**
	 * Constructor.
	 * 
	 * @param playerInv The player inventory
	 * @param te The tile entity for this container
	 */
	public CombusterGUIContainer(IInventory playerInv, TEGenerator te)
	{
		super(te, 5, PLAYER_INV_Y);
		addSlots();
		super.addPlayerInventorySlots(playerInv);
	}
	
	@Override
	public void addSlots()
	{
		for (int i = 0; i < UPGRADE_SLOTS_ID.length; i++)
			this.addSlotToContainer(new UpgradeSlot((TEInventory) inventory, UPGRADE_SLOTS_ID[i], UPGRADE_SLOTS_X[i], UPGRADE_SLOTS_Y[i]));
		
		this.addSlotToContainer(new JarSlot(inventory, JAR_OUTPUT_SLOT_ID, JAR_OUTPUT_SLOT_X, JAR_OUTPUT_SLOT_Y));
		
		this.addSlotToContainer(new ScienceSlot(inventory, INPUT_SLOT_ID, INPUT_SLOT_X, INPUT_SLOT_Y));
		
		this.addSlotToContainer(new ScienceSlot(inventory, OUTPUT_SLOT_ID, OUTPUT_SLOT_X, OUTPUT_SLOT_Y));
	}
}
