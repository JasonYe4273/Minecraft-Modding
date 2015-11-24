package com.JasonILTG.ScienceMod.gui;

import com.JasonILTG.ScienceMod.gui.general.InventoryGUI;
import com.JasonILTG.ScienceMod.reference.Textures;
import com.JasonILTG.ScienceMod.tileentity.general.TEInventory;

import net.minecraft.inventory.IInventory;

public class AirExtractorGUI extends InventoryGUI
{
public static final int GUI_ID = 20;
	
	public IInventory playerInv;
	public TEInventory te;
	
	public AirExtractorGUI(IInventory playerInv, TEInventory te)
	{
		super(new AirExtractorGUIContainer(playerInv, te), playerInv);
		this.playerInv = playerInv;
		this.te = te;
	}
	
	@Override
	public void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
	{
		super.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);
		this.mc.getTextureManager().bindTexture(Textures.GUI.AIR_EXTRACTOR);
		this.drawTexturedModalRect(this.guiLeft + (Textures.GUI.DEFUALT_GUI_X_SIZE - Textures.GUI.ELECTROLYZER_GUI_WIDTH) / 2, this.guiTop, 
				0, 0, Textures.GUI.AIR_EXTRACTOR_GUI_WIDTH, Textures.GUI.AIR_EXTRACTOR_GUI_HEIGHT);
	}
}
