package com.JasonILTG.ScienceMod.gui;

import com.JasonILTG.ScienceMod.gui.general.InventoryGUI;
import com.JasonILTG.ScienceMod.reference.Textures;
import com.JasonILTG.ScienceMod.tileentity.general.TEInventory;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.inventory.IInventory;

public class ElectrolyzerGUI extends InventoryGUI
{
	public static final int GUI_ID = 20;
	
	public IInventory playerInv;
	public TEInventory te;
	
	public ElectrolyzerGUI(IInventory playerInv, TEInventory te)
	{
		super(new ElectrolyzerGUIContainer(playerInv, te), playerInv);
		this.playerInv = playerInv;
		this.te = te;
	}
	
	@Override
	public void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
	{
		super.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);
		GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
		this.mc.getTextureManager().bindTexture(Textures.GUI.ELECROLYZER);
		this.drawTexturedModalRect(this.guiLeft + (Textures.GUI.DEFUALT_GUI_X_SIZE - Textures.GUI.ELECTROLYZER_GUI_WIDTH) / 2, this.guiTop, 
				0, 0, Textures.GUI.ELECTROLYZER_GUI_WIDTH, Textures.GUI.ELECTROLYZER_GUI_HEIGHT);
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
	{
		super.drawGuiContainerForegroundLayer(mouseX, mouseY);
		String s = this.te.getDisplayName().getUnformattedText();
		this.fontRendererObj.drawString(s, (Textures.GUI.DEFUALT_GUI_X_SIZE - this.fontRendererObj.getStringWidth(s)) / 2, 6, 4210752);
	}
}
