package com.JasonILTG.ScienceMod.gui.generators;

import com.JasonILTG.ScienceMod.reference.Textures;
import com.JasonILTG.ScienceMod.tileentity.generators.TEGenerator;
import com.JasonILTG.ScienceMod.tileentity.generators.TESolarPanel;

import net.minecraft.inventory.IInventory;

/**
 * GUI class for solar panels.
 * 
 * @author JasonILTG and syy1125
 */
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
		super(new SolarPanelGUIContainer(playerInv, te), playerInv, te, Textures.GUI.Generator.SOLAR_PANEL, Textures.GUI.Generator.SOLAR_PANEL_GUI_WIDTH, Textures.GUI.Generator.SOLAR_PANEL_GUI_HEIGHT,
				false, null, null, 0, 0, 0, 0, 0, 0, null, null, true, Textures.GUI.Generator.SOLAR_PANEL_POWER_X, Textures.GUI.Generator.SOLAR_PANEL_POWER_Y, true, Textures.GUI.Generator.SOLAR_PANEL_TEMP_X, Textures.GUI.Generator.SOLAR_PANEL_TEMP_Y);
		xSize = Math.max(Textures.GUI.Generator.SOLAR_PANEL_GUI_WIDTH, Textures.GUI.PLAYER_INV_WIDTH);
		ySize = Textures.GUI.Generator.SOLAR_PANEL_GUI_HEIGHT + Textures.GUI.PLAYER_INV_HEIGHT;
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
					Textures.GUI.POWER_WIDTH, Textures.GUI.POWER_HEIGHT, (int) te.getCurrentPower(), (int) te.getPowerCapacity(),
					Textures.GUI.POWER_DIR, Textures.GUI.POWER_EMPTY);
			drawPartial(Textures.GUI.TEMP_FULL, guiLeft + Textures.GUI.Generator.SOLAR_PANEL_TEMP_X, guiTop + Textures.GUI.Generator.SOLAR_PANEL_TEMP_Y,
					Textures.GUI.TEMP_WIDTH, Textures.GUI.TEMP_HEIGHT, (int) te.getCurrentTemp() - Textures.GUI.TEMP_MIN , Textures.GUI.TEMP_MAX,
					Textures.GUI.TEMP_DIR, Textures.GUI.TEMP_EMPTY);
			
			if (te.getMode() == TESolarPanel.DAY_MODE)
			{
				this.mc.getTextureManager().bindTexture(Textures.GUI.Generator.SOLAR_PANEL_DAY);
				this.drawTexturedModalRect(guiLeft + Textures.GUI.Generator.SOLAR_PANEL_ICON_X, guiTop + Textures.GUI.Generator.SOLAR_PANEL_ICON_Y,
						0, 0, Textures.GUI.Generator.SOLAR_PANEL_ICON_WIDTH, Textures.GUI.Generator.SOLAR_PANEL_ICON_HEIGHT);
			}
			else if (te.getMode() == TESolarPanel.NIGHT_MODE)
			{
				this.mc.getTextureManager().bindTexture(Textures.GUI.Generator.SOLAR_PANEL_NIGHT);
				this.drawTexturedModalRect(guiLeft + Textures.GUI.Generator.SOLAR_PANEL_ICON_X, guiTop + Textures.GUI.Generator.SOLAR_PANEL_ICON_Y,
						0, 0, Textures.GUI.Generator.SOLAR_PANEL_ICON_WIDTH, Textures.GUI.Generator.SOLAR_PANEL_ICON_HEIGHT);
			}
			else
			{
				this.mc.getTextureManager().bindTexture(Textures.GUI.Generator.SOLAR_PANEL_OFF);
				this.drawTexturedModalRect(guiLeft + Textures.GUI.Generator.SOLAR_PANEL_ICON_X, guiTop + Textures.GUI.Generator.SOLAR_PANEL_ICON_Y,
						0, 0, Textures.GUI.Generator.SOLAR_PANEL_ICON_WIDTH, Textures.GUI.Generator.SOLAR_PANEL_ICON_HEIGHT);
			}
		}
	}
}
