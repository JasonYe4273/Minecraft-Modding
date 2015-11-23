package com.JasonILTG.ScienceMod.gui;

import com.JasonILTG.ScienceMod.reference.Reference;

import net.minecraft.client.gui.inventory.GuiContainer;

public class InventoryGUI extends GuiContainer
{
	public InventoryGUI(InventoryGUIContainer container)
	{
		super(container);
		this.xSize = Reference.DEFUALT_GUI_X_SIZE;
		this.ySize = Reference.DEFUALT_GUI_Y_SIZE;
	}
	
	public InventoryGUI(InventoryGUIContainer container, int xSize, int ySize)
	{
		super(container);
		this.xSize = xSize;
		this.ySize = ySize;
	}
	
	@Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
	{
		
    }
	
}
