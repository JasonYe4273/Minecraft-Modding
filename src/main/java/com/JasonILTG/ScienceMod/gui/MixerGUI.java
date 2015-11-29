package com.JasonILTG.ScienceMod.gui;

import net.minecraft.inventory.IInventory;

import com.JasonILTG.ScienceMod.gui.general.InventoryGUI;
import com.JasonILTG.ScienceMod.reference.Textures;
import com.JasonILTG.ScienceMod.tileentity.TEElectrolyzer;
import com.JasonILTG.ScienceMod.tileentity.TEMixer;
import com.JasonILTG.ScienceMod.tileentity.general.TEInventory;

public class MixerGUI extends InventoryGUI
{
	public MixerGUI(IInventory playerInv, TEInventory te)
	{
		super(new MixerGUIContainer(playerInv, te), playerInv);
		xSize = Math.max(Textures.GUI.MIXER_GUI_WIDTH, Textures.GUI.PLAYER_INV_WIDTH);
		ySize = Textures.GUI.MIXER_GUI_HEIGHT + Textures.GUI.PLAYER_INV_HEIGHT;
	}
	
	@Override
	public void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
	{
		super.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);
		this.mc.getTextureManager().bindTexture(Textures.GUI.MIXER);
		this.drawTexturedModalRect(this.guiLeft + (Textures.GUI.DEFAULT_GUI_X_SIZE - Textures.GUI.MIXER_GUI_WIDTH) / 2,
				this.guiTop,
				0, 0, Textures.GUI.MIXER_GUI_WIDTH, Textures.GUI.MIXER_GUI_HEIGHT);
		
		TEMixer te = (TEMixer) container.getInv();
		if (te != null)
		{
			drawPartial(Textures.GUI.WATER_TANK, guiLeft + Textures.GUI.MIXER_TANK_X, guiTop + Textures.GUI.MIXER_TANK_Y,
					Textures.GUI.DEFAULT_TANK_WIDTH, Textures.GUI.DEFAULT_TANK_HEIGHT, te.getFluidAmount(), TEElectrolyzer.DEFAULT_TANK_CAPACITY, 1,
					Textures.GUI.TANK);
		}
	}
}
