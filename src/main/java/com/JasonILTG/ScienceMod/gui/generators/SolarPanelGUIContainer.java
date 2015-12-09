package com.JasonILTG.ScienceMod.gui.generators;

import com.JasonILTG.ScienceMod.reference.Textures;
import com.JasonILTG.ScienceMod.tileentity.generators.TEGenerator;

import net.minecraft.inventory.IInventory;

public class SolarPanelGUIContainer extends GeneratorGUIContainer
{
	protected static final int PLAYER_INV_Y = Textures.GUI.Generator.COMBUSTER_GUI_HEIGHT + 22;
	
	/**
	 * Constructor.
	 * 
	 * @param playerInv The player inventory
	 * @param te The tile entity for this container
	 */
	public SolarPanelGUIContainer(IInventory playerInv, TEGenerator te)
	{
		super(te, 0, PLAYER_INV_Y);
		addSlots();
		super.addPlayerInventorySlots(playerInv);
	}
	
	@Override
	public void addSlots()
	{
		
	}
}
