package com.JasonILTG.ScienceMod.gui.general;

import com.JasonILTG.ScienceMod.ScienceMod;
import com.JasonILTG.ScienceMod.messages.TEInfoRequestMessage;
import com.JasonILTG.ScienceMod.tileentity.general.TEMachine;

import net.minecraft.inventory.IInventory;

public class MachineGUI extends InventoryGUI
{
	public MachineGUI(MachineGUIContainer container, IInventory playerInv, TEMachine te)
	{
		super(container, playerInv);

		if (te.getWorld() != null && te.getWorld().isRemote) ScienceMod.snw.sendToServer(new TEInfoRequestMessage(te.getPos().getX(), te.getPos().getY(), te.getPos().getZ()));
	}
}
