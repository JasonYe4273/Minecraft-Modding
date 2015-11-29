package com.JasonILTG.ScienceMod.gui;

import com.JasonILTG.ScienceMod.gui.general.InventoryGUI;
import com.JasonILTG.ScienceMod.reference.Textures;
import com.JasonILTG.ScienceMod.tileentity.TEElectrolyzer;
import com.JasonILTG.ScienceMod.tileentity.general.TEInventory;

import net.minecraft.inventory.IInventory;

public class ElectrolyzerGUI extends InventoryGUI
{
	public static final int GUI_ID = 20;
	
	public ElectrolyzerGUI(IInventory playerInv, TEInventory te)
	{
		super(new ElectrolyzerGUIContainer(playerInv, te), playerInv);
		xSize = Math.max(Textures.GUI.ELECTROLYZER_GUI_WIDTH, Textures.GUI.PLAYER_INV_WIDTH);
		ySize = Textures.GUI.ELECTROLYZER_GUI_HEIGHT + Textures.GUI.PLAYER_INV_HEIGHT;
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
	{
		super.drawGuiContainerForegroundLayer(mouseX, mouseY);
	}
	
	@Override
	public void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
	{
		super.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);
		this.mc.getTextureManager().bindTexture(Textures.GUI.ELECROLYZER);
		this.drawTexturedModalRect(this.guiLeft + (Textures.GUI.DEFAULT_GUI_X_SIZE - Textures.GUI.ELECTROLYZER_GUI_WIDTH) / 2,
				this.guiTop,
				0, 0, Textures.GUI.ELECTROLYZER_GUI_WIDTH, Textures.GUI.ELECTROLYZER_GUI_HEIGHT);
		
		TEElectrolyzer te = (TEElectrolyzer) container.getTE();
		if (te != null)
		{
			drawPartial(Textures.GUI.WATER_TANK, guiLeft + Textures.GUI.ELECTROLYZER_TANK_X, guiTop + Textures.GUI.ELECTROLYZER_TANK_Y, Textures.GUI.DEFAULT_TANK_WIDTH, Textures.GUI.DEFAULT_TANK_HEIGHT, te.getFluidAmount(), TEElectrolyzer.DEFAULT_TANK_CAPACITY, 1, Textures.GUI.TANK);
		}
	}
}
