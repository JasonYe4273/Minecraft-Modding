package com.JasonILTG.ScienceMod.gui.generators;

import com.JasonILTG.ScienceMod.gui.general.InventoryGUIContainer;

import net.minecraft.inventory.IInventory;

/**
 * Wrapper class for all generator Containers in ScienceMod.
 * 
 * @author JasonILTG and syy1125
 */
public class GeneratorGUIContainer extends InventoryGUIContainer
{
	/**
	 * Constructor.
	 * 
	 * @param inv The inventory of this container
	 * @param playerInvStartID The starting slot ID of the player inventory
	 * @param playerInvY The y-position of the player inventory
	 */
	public GeneratorGUIContainer(IInventory inv, int playerInvStartID, int playerInvY)
	{
		super(inv, playerInvStartID, playerInvY);
	}
}
