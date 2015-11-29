package com.JasonILTG.ScienceMod.gui;

import net.minecraft.inventory.IInventory;

import com.JasonILTG.ScienceMod.gui.general.InventoryGUI;
import com.JasonILTG.ScienceMod.reference.Textures;
import com.JasonILTG.ScienceMod.tileentity.general.TEInventory;

public class CentrifugeGUI extends InventoryGUI
{
	public CentrifugeGUI(IInventory playerInv, TEInventory te)
	{
		super(new CentrifugeGUIContainer(playerInv, te), playerInv);
		xSize = Math.max(Textures.GUI.CENTRIFUGE_GUI_WIDTH, Textures.GUI.PLAYER_INV_WIDTH);
		ySize = Textures.GUI.CENTRIFUGE_GUI_HEIGHT + Textures.GUI.PLAYER_INV_HEIGHT;
	}
	
	@Override
	public void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
	{
		super.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);
		this.mc.getTextureManager().bindTexture(Textures.GUI.CENTRIFUGE);
		this.drawTexturedModalRect(this.guiLeft + (Textures.GUI.DEFAULT_GUI_X_SIZE - Textures.GUI.CENTRIFUGE_GUI_WIDTH) / 2,
				this.guiTop,
				0, 0, Textures.GUI.CENTRIFUGE_GUI_WIDTH, Textures.GUI.CENTRIFUGE_GUI_HEIGHT);
	}
}
