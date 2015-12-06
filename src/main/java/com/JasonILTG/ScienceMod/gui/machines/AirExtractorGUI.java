package com.JasonILTG.ScienceMod.gui.machines;

import java.util.ArrayList;
import java.util.List;

import com.JasonILTG.ScienceMod.reference.Textures;
import com.JasonILTG.ScienceMod.tileentity.machines.TEAirExtractor;
import com.JasonILTG.ScienceMod.tileentity.machines.TEMachine;

import net.minecraft.inventory.IInventory;

public class AirExtractorGUI extends MachineGUI
{
	public AirExtractorGUI(IInventory playerInv, TEMachine te)
	{
		super(new AirExtractorGUIContainer(playerInv, te), playerInv, te);
		xSize = Math.max(Textures.GUI.Machine.AIR_EXTRACTOR_GUI_WIDTH, Textures.GUI.PLAYER_INV_WIDTH);
		ySize = Textures.GUI.Machine.AIR_EXTRACTOR_GUI_HEIGHT + Textures.GUI.PLAYER_INV_HEIGHT;
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
	{
		super.drawGuiContainerForegroundLayer(mouseX, mouseY);
		int guiMouseX = mouseX - guiLeft;
		int guiMouseY = mouseY - guiTop;
		
		if (guiMouseX >= Textures.GUI.Machine.AIR_EXTRACTOR_POWER_X && guiMouseX < Textures.GUI.Machine.AIR_EXTRACTOR_POWER_X + Textures.GUI.POWER_WIDTH
				&& guiMouseY >= Textures.GUI.Machine.AIR_EXTRACTOR_POWER_Y && guiMouseY < Textures.GUI.Machine.AIR_EXTRACTOR_POWER_Y + Textures.GUI.POWER_HEIGHT)
		{
			TEAirExtractor te = (TEAirExtractor) container.getInv();
			if (te != null)
			{
				List<String> text = new ArrayList<String>();
				text.add(te.getPowerManager().getPowerDisplay());
				this.drawHoveringText(text, guiMouseX, guiMouseY);
			}
		}
	}
	
	@Override
	public void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
	{
		super.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);
		this.mc.getTextureManager().bindTexture(Textures.GUI.Machine.AIR_EXTRACTOR);
		this.drawTexturedModalRect(this.guiLeft + (Textures.GUI.DEFAULT_GUI_X_SIZE - Textures.GUI.Machine.AIR_EXTRACTOR_GUI_WIDTH) / 2, this.guiTop,
				0, 0, Textures.GUI.Machine.AIR_EXTRACTOR_GUI_WIDTH, Textures.GUI.Machine.AIR_EXTRACTOR_GUI_HEIGHT);
		
		TEMachine te = (TEMachine) container.getInv();
		if (te != null)
		{
			drawPartial(Textures.GUI.PROGRESS_BAR_FULL, guiLeft + Textures.GUI.Machine.AIR_EXTRACTOR_PROGRESS_X, guiTop
					+ Textures.GUI.Machine.AIR_EXTRACTOR_PROGRESS_Y,
					Textures.GUI.DEFAULT_PROGRESS_WIDTH, Textures.GUI.DEFAULT_PROGRESS_HEIGHT, te.getCurrentProgress(), te.getMaxProgress(),
					Textures.GUI.DEFAULT_PROGRESS_DIR, Textures.GUI.PROGRESS_BAR_EMPTY);
			drawPartial(Textures.GUI.POWER_FULL, guiLeft + Textures.GUI.Machine.AIR_EXTRACTOR_POWER_X, guiTop + Textures.GUI.Machine.AIR_EXTRACTOR_POWER_Y,
					Textures.GUI.POWER_WIDTH, Textures.GUI.POWER_HEIGHT, te.getCurrentPower(), te.getPowerCapacity(),
					Textures.GUI.POWER_DIR, Textures.GUI.POWER_EMPTY);
		}
	}
}
