/**
 * 
 */
package com.JasonILTG.ScienceMod.gui;

import com.JasonILTG.ScienceMod.ScienceMod;
import com.JasonILTG.ScienceMod.gui.general.InventoryGUI;
import com.JasonILTG.ScienceMod.messages.TEInfoRequestMessage;
import com.JasonILTG.ScienceMod.tileentity.TEAssembler;

import net.minecraft.inventory.IInventory;

/**
 * 
 * @author JasonILTG and syy1125
 */
public class AssemblerGUI extends InventoryGUI
{
	public AssemblerGUI(IInventory playerInv, TEAssembler te)
	{
		super(new AssemblerGUIContainer(playerInv, te), te);
		
		if (te.getWorld() != null && te.getWorld().isRemote) ScienceMod.snw.sendToServer(new TEInfoRequestMessage(te.getPos().getX(), te.getPos().getY(), te.getPos().getZ()));
	}
}
