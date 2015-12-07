package com.JasonILTG.ScienceMod.gui.machines;

import com.JasonILTG.ScienceMod.reference.Textures;
import com.JasonILTG.ScienceMod.tileentity.machines.TEMachine;

import net.minecraft.inventory.IInventory;

public class CentrifugeGUI extends MachineGUI
{
	public CentrifugeGUI(IInventory playerInv, TEMachine te)
	{
		super(new CentrifugeGUIContainer(playerInv, te), playerInv, te);
		xSize = Math.max(Textures.GUI.Machine.CENTRIFUGE_GUI_WIDTH, Textures.GUI.PLAYER_INV_WIDTH);
		ySize = Textures.GUI.Machine.CENTRIFUGE_GUI_HEIGHT + Textures.GUI.PLAYER_INV_HEIGHT;
	}
	
	@Override
	public void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
	{
		super.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);
		this.mc.getTextureManager().bindTexture(Textures.GUI.Machine.CENTRIFUGE);
		this.drawTexturedModalRect(this.guiLeft + (Textures.GUI.DEFAULT_GUI_X_SIZE - Textures.GUI.Machine.CENTRIFUGE_GUI_WIDTH) / 2,
				this.guiTop,
				0, 0, Textures.GUI.Machine.CENTRIFUGE_GUI_WIDTH, Textures.GUI.Machine.CENTRIFUGE_GUI_HEIGHT);
	}
}