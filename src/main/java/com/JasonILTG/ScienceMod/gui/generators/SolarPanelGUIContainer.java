package com.JasonILTG.ScienceMod.gui.generators;

import com.JasonILTG.ScienceMod.gui.slots.UpgradeSlot;
import com.JasonILTG.ScienceMod.reference.Textures;
import com.JasonILTG.ScienceMod.tileentity.general.TEInventory;
import com.JasonILTG.ScienceMod.tileentity.generators.TEGenerator;

import net.minecraft.inventory.IInventory;

public class SolarPanelGUIContainer extends GeneratorGUIContainer
{
	protected static final int PLAYER_INV_Y = Textures.GUI.Generator.COMBUSTER_GUI_HEIGHT + 22;
	
	protected static final int[] UPGRADE_SLOTS_ID = { 0, 1 };
	
	protected static final int[] UPGRADE_SLOTS_X = { Textures.GUI.UPGRADE_SLOT_X + (Textures.GUI.DEFAULT_GUI_X_SIZE + Textures.GUI.Machine.MIXER_GUI_WIDTH) / 2, Textures.GUI.UPGRADE_SLOT_X + (Textures.GUI.DEFAULT_GUI_X_SIZE + Textures.GUI.Machine.MIXER_GUI_WIDTH) / 2 };
	protected static final int[] UPGRADE_SLOTS_Y = { Textures.GUI.UPGRADE_SLOT_1_Y, Textures.GUI.UPGRADE_SLOT_2_Y };
	
	/**
	 * Constructor.
	 * 
	 * @param playerInv The player inventory
	 * @param te The tile entity for this container
	 */
	public SolarPanelGUIContainer(IInventory playerInv, TEGenerator te)
	{
		super(te, 2, PLAYER_INV_Y);
		addSlots();
		super.addPlayerInventorySlots(playerInv);
	}
	
	@Override
	public void addSlots()
	{
		for (int i = 0; i < UPGRADE_SLOTS_ID.length; i++)
			this.addSlotToContainer(new UpgradeSlot((TEInventory) inventory, UPGRADE_SLOTS_ID[i], UPGRADE_SLOTS_X[i], UPGRADE_SLOTS_Y[i]));
	}
}
