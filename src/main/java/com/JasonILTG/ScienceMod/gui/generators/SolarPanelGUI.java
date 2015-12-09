package com.JasonILTG.ScienceMod.gui.generators;

import java.util.ArrayList;
import java.util.List;

import com.JasonILTG.ScienceMod.reference.Textures;
import com.JasonILTG.ScienceMod.tileentity.generators.TECombuster;
import com.JasonILTG.ScienceMod.tileentity.generators.TEGenerator;
import com.JasonILTG.ScienceMod.tileentity.generators.TESolarPanel;

import net.minecraft.inventory.IInventory;

public class SolarPanelGUI extends GeneratorGUI
{
	/**
	 * Constructor.
	 * 
	 * @param playerInv The player inventory
	 * @param te The tile entity for this GUI
	 */
	public SolarPanelGUI(IInventory playerInv, TEGenerator te)
	{
		super(new SolarPanelGUIContainer(playerInv, te), playerInv, te);
		xSize = Math.max(Textures.GUI.Generator.SOLAR_PANEL_GUI_WIDTH, Textures.GUI.PLAYER_INV_WIDTH);
		ySize = Textures.GUI.Generator.SOLAR_PANEL_GUI_HEIGHT + Textures.GUI.PLAYER_INV_HEIGHT;
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
	{
		super.drawGuiContainerForegroundLayer(mouseX, mouseY);
		int guiMouseX = mouseX - guiLeft;
		int guiMouseY = mouseY - guiTop;
		
		if (guiMouseX >= Textures.GUI.Generator.SOLAR_PANEL_POWER_X && guiMouseX < Textures.GUI.Generator.SOLAR_PANEL_POWER_X + Textures.GUI.POWER_WIDTH
				&& guiMouseY >= Textures.GUI.Generator.SOLAR_PANEL_POWER_Y && guiMouseY < Textures.GUI.Generator.SOLAR_PANEL_POWER_Y + Textures.GUI.POWER_HEIGHT)
		{
			TECombuster te = (TECombuster) container.getInv();
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
		this.mc.getTextureManager().bindTexture(Textures.GUI.Generator.SOLAR_PANEL);
		this.drawTexturedModalRect(this.guiLeft + (Textures.GUI.DEFAULT_GUI_X_SIZE - Textures.GUI.Generator.SOLAR_PANEL_GUI_WIDTH) / 2,
				this.guiTop,
				0, 0, Textures.GUI.Generator.SOLAR_PANEL_GUI_WIDTH, Textures.GUI.Generator.SOLAR_PANEL_GUI_HEIGHT);
		
		TESolarPanel te = (TESolarPanel) container.getInv();
		if (te != null)
		{
			drawPartial(Textures.GUI.POWER_FULL, guiLeft + Textures.GUI.Generator.SOLAR_PANEL_POWER_X, guiTop + Textures.GUI.Generator.SOLAR_PANEL_POWER_Y,
					Textures.GUI.POWER_WIDTH, Textures.GUI.POWER_HEIGHT, te.getCurrentPower(), te.getPowerCapacity(),
					Textures.GUI.POWER_DIR, Textures.GUI.POWER_EMPTY);
		}
	}
}
