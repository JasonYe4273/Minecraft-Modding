package com.JasonILTG.ScienceMod.gui;

import com.JasonILTG.ScienceMod.reference.Reference;
import com.JasonILTG.ScienceMod.tileentity.TEInventory;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;

public class ElectrolyzerGUI extends InventoryGUI
{
	public static final int GUI_ID = 20;
	
	public IInventory playerInv;
	public TEInventory te;
	
	public ElectrolyzerGUI(IInventory playerInv, TEInventory te)
	{
		super(new ElectrolyzerGUIContainer(playerInv, te));
		this.playerInv = playerInv;
		this.te = te;
	}
	
	@Override
	public void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
	{
		GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
		this.mc.getTextureManager().bindTexture(new ResourceLocation(Reference.MOD_ID + ":textures/gui/electrolyzer"));
		this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
	{
		String s = this.te.getDisplayName().getUnformattedText();
	    this.fontRendererObj.drawString(s, ( Reference.DEFUALT_GUI_X_SIZE - this.fontRendererObj.getStringWidth(s) ) / 2, 6, 4210752);
	    this.fontRendererObj.drawString(this.playerInv.getDisplayName().getUnformattedText(), 8, 72, 4210752);
	}
}
