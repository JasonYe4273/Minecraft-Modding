package com.JasonILTG.ScienceMod.gui;

import net.minecraft.inventory.IInventory;

import com.JasonILTG.ScienceMod.gui.general.InventoryGUI;
import com.JasonILTG.ScienceMod.reference.Textures;
import com.JasonILTG.ScienceMod.tileentity.TECondenser;
import com.JasonILTG.ScienceMod.tileentity.TEElectrolyzer;
import com.JasonILTG.ScienceMod.tileentity.general.TEInventory;

public class CondenserGUI extends InventoryGUI
{
	public CondenserGUI(IInventory playerInv, TEInventory te)
	{
		super(new CondenserGUIContainer(playerInv, te), playerInv);
		xSize = Math.max(Textures.GUI.CONDENSER_GUI_WIDTH, Textures.GUI.PLAYER_INV_WIDTH);
		ySize = Textures.GUI.CONDENSER_GUI_HEIGHT + Textures.GUI.PLAYER_INV_HEIGHT;
	}
	
	@Override
	public void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
	{
		super.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);
		this.mc.getTextureManager().bindTexture(Textures.GUI.CONDENSER);
		this.drawTexturedModalRect(this.guiLeft + (Textures.GUI.DEFAULT_GUI_X_SIZE - Textures.GUI.CONDENSER_GUI_WIDTH) / 2,
				this.guiTop,
				0, 0, Textures.GUI.CONDENSER_GUI_WIDTH, Textures.GUI.CONDENSER_GUI_HEIGHT);
		
		TECondenser te = (TECondenser) container.getTE();
		if (te != null)
		{
			drawPartial(Textures.GUI.WATER_TANK, guiLeft + Textures.GUI.CONDENSER_TANK_X, guiTop + Textures.GUI.CONDENSER_TANK_Y,
					Textures.GUI.DEFAULT_TANK_WIDTH, Textures.GUI.DEFAULT_TANK_HEIGHT, te.getFluidAmount(), TEElectrolyzer.DEFAULT_TANK_CAPACITY, 1,
					Textures.GUI.TANK);
		}
	}
}
