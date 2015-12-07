package com.JasonILTG.ScienceMod.gui.machines;

import com.JasonILTG.ScienceMod.ScienceMod;
import com.JasonILTG.ScienceMod.gui.general.InventoryGUI;
import com.JasonILTG.ScienceMod.messages.TEInfoRequestMessage;
import com.JasonILTG.ScienceMod.reference.Textures;
import com.JasonILTG.ScienceMod.tileentity.machines.TEMachine;

import net.minecraft.inventory.IInventory;

/**
 * Wrapper class for all machine GUIs in ScienceMod.
 * 
 * @author JasonILTG and syy1125
 */
public class MachineGUI extends InventoryGUI
{
	/**
	 * Constructor
	 * 
	 * @param container The container for this GUI
	 * @param playerInv The player inventory
	 * @param te The tile entity for this GUI
	 */
	public MachineGUI(MachineGUIContainer container, IInventory playerInv, TEMachine te)
	{
		super(container, playerInv);

		if (te.getWorld() != null && te.getWorld().isRemote) ScienceMod.snw.sendToServer(new TEInfoRequestMessage(te.getPos().getX(), te.getPos().getY(), te.getPos().getZ()));
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
	{
		super.drawGuiContainerForegroundLayer(mouseX, mouseY);
		
		TEMachine te = (TEMachine) container.getInv();
		if (te == null) return;
		
		String s;
		if (te.getHeatManager() == null) s = "Temp: 20.0 C";
		else s = te.getHeatManager().getTempDisplayC();
		this.fontRendererObj.drawString(s, (Textures.GUI.DEFAULT_GUI_X_SIZE - this.fontRendererObj.getStringWidth(s)) / 2, -2, 4210752);
	}
}
