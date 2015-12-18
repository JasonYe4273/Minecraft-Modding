package com.JasonILTG.ScienceMod.gui.machines;

import com.JasonILTG.ScienceMod.gui.general.InventoryGUIContainer;
import com.JasonILTG.ScienceMod.tileentity.machines.TEMachine;

/**
 * Wrapper class for all machine Containers in ScienceMod.
 * 
 * @author JasonILTG and syy1125
 */
public class MachineGUIContainer extends InventoryGUIContainer
{
	/**
	 * Constructor.
	 * 
	 * @param inv The inventory for this container
	 * @param playerInvStartID The starting slot ID for the player inventory
	 * @param playerInvY The y-position of the player inventory in the GUI
	 */
	public MachineGUIContainer(TEMachine inv, int playerInvStartID, int playerInvY)
	{
		super(inv, playerInvStartID, playerInvY);
	}
	
	/**
	 * Adds the slots for the inventory.
	 */
	public void addSlots()
	{
		
	}
}
