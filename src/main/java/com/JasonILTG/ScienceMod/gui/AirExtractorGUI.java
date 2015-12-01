package com.JasonILTG.ScienceMod.gui;

import com.JasonILTG.ScienceMod.gui.general.MachineGUI;
import com.JasonILTG.ScienceMod.reference.Textures;
import com.JasonILTG.ScienceMod.tileentity.general.TEMachine;

import net.minecraft.inventory.IInventory;

public class AirExtractorGUI extends MachineGUI
{
	public AirExtractorGUI(IInventory playerInv, TEMachine te)
	{
		super(new AirExtractorGUIContainer(playerInv, te), playerInv, te);
		xSize = Math.max(Textures.GUI.AIR_EXTRACTOR_GUI_WIDTH, Textures.GUI.PLAYER_INV_WIDTH);
		ySize = Textures.GUI.AIR_EXTRACTOR_GUI_HEIGHT + Textures.GUI.PLAYER_INV_HEIGHT;
	}
	
	@Override
	public void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
	{
		super.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);
		this.mc.getTextureManager().bindTexture(Textures.GUI.AIR_EXTRACTOR);
		this.drawTexturedModalRect(this.guiLeft + (Textures.GUI.DEFAULT_GUI_X_SIZE - Textures.GUI.AIR_EXTRACTOR_GUI_WIDTH) / 2, this.guiTop,
				0, 0, Textures.GUI.AIR_EXTRACTOR_GUI_WIDTH, Textures.GUI.AIR_EXTRACTOR_GUI_HEIGHT);
		
		TEMachine te = (TEMachine) container.getInv();
		if (te != null)
		{
			drawPartial(Textures.GUI.PROGRESS_BAR_FULL, guiLeft + Textures.GUI.AIR_EXTRACTOR_PROGRESS_X, guiTop
					+ Textures.GUI.AIR_EXTRACTOR_PROGRESS_Y,
					Textures.GUI.DEFAULT_PROGRESS_WIDTH, Textures.GUI.DEFAULT_PROGRESS_HEIGHT, te.getCurrentProgress(), te.getMaxProgress(),
					Textures.GUI.DEFAULT_PROGRESS_DIR, Textures.GUI.PROGRESS_BAR_EMPTY);
		}
	}
}
