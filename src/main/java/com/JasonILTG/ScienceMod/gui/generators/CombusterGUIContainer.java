package com.JasonILTG.ScienceMod.gui.generators;

import com.JasonILTG.ScienceMod.gui.JarSlot;
import com.JasonILTG.ScienceMod.gui.general.ScienceSlot;
import com.JasonILTG.ScienceMod.reference.Textures;
import com.JasonILTG.ScienceMod.tileentity.generators.TEGenerator;

import net.minecraft.inventory.IInventory;

/**
 * Container class for combusters.
 * 
 * @author JasonILTG and syy1125
 */
public class CombusterGUIContainer extends GeneratorGUIContainer
{
	protected static final int JAR_OUTPUT_SLOT_ID = 0;
	protected static final int INPUT_SLOT_ID = 1;
	protected static final int OUTPUT_SLOT_ID = 2;

	protected static final int JAR_OUTPUT_SLOT_X = 114;
	protected static final int JAR_OUTPUT_SLOT_Y = 58;
	protected static final int INPUT_SLOT_X = 92;
	protected static final int INPUT_SLOT_Y = 18;
	protected static final int OUTPUT_SLOT_X = 92;
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
		super(te, 3, PLAYER_INV_Y);
		addSlots();
		super.addPlayerInventorySlots(playerInv);
	}
	
	@Override
	public void addSlots()
	{
		// Jar Input, ID 0
		this.addSlotToContainer(new JarSlot(inventory, JAR_OUTPUT_SLOT_ID, JAR_OUTPUT_SLOT_X, JAR_OUTPUT_SLOT_Y));
		
		// Input, ID 1
		this.addSlotToContainer(new ScienceSlot(inventory, INPUT_SLOT_ID, INPUT_SLOT_X, INPUT_SLOT_Y));
		
		// Outputs, IDs 2 and 3
		this.addSlotToContainer(new ScienceSlot(inventory, OUTPUT_SLOT_ID, OUTPUT_SLOT_X, OUTPUT_SLOT_Y));
	}
}
