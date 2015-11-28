package com.JasonILTG.ScienceMod.gui;

import com.JasonILTG.ScienceMod.gui.general.InventoryGUI;
import com.JasonILTG.ScienceMod.reference.Textures;
import com.JasonILTG.ScienceMod.tileentity.general.TEInventory;

import net.minecraft.inventory.IInventory;

public class FilterGUI extends InventoryGUI
{
	public static final int GUI_ID = 70;
	
	public FilterGUI(IInventory playerInv, TEInventory te)
	{
		super(new FilterGUIContainer(playerInv, te), playerInv);
		xSize = Math.max(Textures.GUI.FILTER_GUI_WIDTH, Textures.GUI.PLAYER_INV_WIDTH);
		ySize = Textures.GUI.FILTER_GUI_HEIGHT + Textures.GUI.PLAYER_INV_HEIGHT;
	}
	
	@Override
	public void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
	{
		super.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);
		this.mc.getTextureManager().bindTexture(Textures.GUI.FILTER);
		this.drawTexturedModalRect(this.guiLeft + (Textures.GUI.DEFUALT_GUI_X_SIZE - Textures.GUI.FILTER_GUI_WIDTH) / 2,
				this.guiTop,
				0, 0, Textures.GUI.FILTER_GUI_WIDTH, Textures.GUI.FILTER_GUI_HEIGHT);
	}
}
