package com.JasonILTG.ScienceMod.gui;

import java.util.ArrayList;
import java.util.List;

import com.JasonILTG.ScienceMod.gui.general.MachineGUI;
import com.JasonILTG.ScienceMod.reference.Textures;
import com.JasonILTG.ScienceMod.tileentity.TEChemReactor;
import com.JasonILTG.ScienceMod.tileentity.general.TEMachine;

import net.minecraft.inventory.IInventory;

public class ChemReactorGUI extends MachineGUI
{
	public ChemReactorGUI(IInventory playerInv, TEMachine te)
	{
		super(new ChemReactorGUIContainer(playerInv, te), playerInv, te);
		xSize = Math.max(Textures.GUI.CHEM_REACTOR_GUI_WIDTH, Textures.GUI.PLAYER_INV_WIDTH);
		ySize = Textures.GUI.CHEM_REACTOR_GUI_HEIGHT + Textures.GUI.PLAYER_INV_HEIGHT;
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
	{
		super.drawGuiContainerForegroundLayer(mouseX, mouseY);
		int guiMouseX = mouseX - guiLeft;
		int guiMouseY = mouseY - guiTop;
		
		if (guiMouseX >= Textures.GUI.CHEM_REACTOR_POWER_X && guiMouseX < Textures.GUI.CHEM_REACTOR_POWER_X + Textures.GUI.POWER_WIDTH
				&& guiMouseY >= Textures.GUI.CHEM_REACTOR_POWER_Y && guiMouseY < Textures.GUI.CHEM_REACTOR_POWER_Y + Textures.GUI.POWER_HEIGHT)
		{
			TEChemReactor te = (TEChemReactor) container.getInv();
			if (te != null)
			{
				List<String> text = new ArrayList<String>();
				text.add(String.format("%s/%s C", te.getCurrentPower(), te.getPowerCapacity()));
				this.drawHoveringText(text, guiMouseX, guiMouseY);
			}
		}
	}
	
	@Override
	public void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
	{
		super.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);
		this.mc.getTextureManager().bindTexture(Textures.GUI.CHEM_REACTOR);
		this.drawTexturedModalRect(this.guiLeft + (Textures.GUI.DEFAULT_GUI_X_SIZE - Textures.GUI.CHEM_REACTOR_GUI_WIDTH) / 2,
				this.guiTop,
				0, 0, Textures.GUI.CHEM_REACTOR_GUI_WIDTH, Textures.GUI.CHEM_REACTOR_GUI_HEIGHT);
		
		TEChemReactor te = (TEChemReactor) container.getInv();
		if (te != null)
		{
			drawPartial(Textures.GUI.CHEM_REACTOR_PROGRESS_FULL, guiLeft + Textures.GUI.CHEM_REACTOR_PROGRESS_X, guiTop + Textures.GUI.CHEM_REACTOR_PROGRESS_Y,
					Textures.GUI.CHEM_REACTOR_PROGRESS_WIDTH, Textures.GUI.CHEM_REACTOR_PROGRESS_HEIGHT, te.getCurrentProgress(), te.getMaxProgress(),
					Textures.GUI.CHEM_REACTOR_PROGRESS_DIR, Textures.GUI.CHEM_REACTOR_PROGRESS_EMPTY);
			drawPartial(Textures.GUI.POWER_FULL, guiLeft + Textures.GUI.CHEM_REACTOR_POWER_X, guiTop + Textures.GUI.CHEM_REACTOR_POWER_Y,
					Textures.GUI.POWER_WIDTH, Textures.GUI.POWER_HEIGHT, te.getCurrentPower(), te.getPowerCapacity(),
					Textures.GUI.POWER_DIR, Textures.GUI.POWER_EMPTY);
		}
	}
}
