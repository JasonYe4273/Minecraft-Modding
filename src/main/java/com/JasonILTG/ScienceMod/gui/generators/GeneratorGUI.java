package com.JasonILTG.ScienceMod.gui.generators;

import com.JasonILTG.ScienceMod.ScienceMod;
import com.JasonILTG.ScienceMod.gui.general.InventoryGUI;
import com.JasonILTG.ScienceMod.messages.TEInfoRequestMessage;
import com.JasonILTG.ScienceMod.tileentity.generators.TEGenerator;

import net.minecraft.inventory.IInventory;

/**
 * Wrapper class for all generator GUIs in ScienceMod.
 * 
 * @author JasonILTG and syy1125
 */
public class GeneratorGUI extends InventoryGUI
{
	/**
	 * Constructor.
	 * 
	 * @param container The container for this GUI
	 * @param playerInv The player inventory
	 * @param te The tile entity for this GUI
	 */
	public GeneratorGUI(GeneratorGUIContainer container, IInventory playerInv, TEGenerator te)
	{
		super(container, playerInv);

		if (te.getWorld() != null && te.getWorld().isRemote) ScienceMod.snw.sendToServer(new TEInfoRequestMessage(te.getPos().getX(), te.getPos().getY(), te.getPos().getZ()));
	}
}
