package com.JasonILTG.ScienceMod.gui;

import com.JasonILTG.ScienceMod.reference.Reference;
import com.JasonILTG.ScienceMod.tileentity.TEInventory;

import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;

public class ElectrolyzerGUI extends InventoryGUI
{
	public static final int GUI_ID = 20;
	
	public ElectrolyzerGUI(IInventory playerInv, TEInventory te)
	{
		super(new ElectrolyzerGUIContainer(playerInv, te));
	}
	
	@Override
	public void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
	{
		this.drawDefaultBackground();
		this.mc.getTextureManager().bindTexture(new ResourceLocation(Reference.GUI_TEXTURES_PATH + "electrolyzer.png"));
	}
}
