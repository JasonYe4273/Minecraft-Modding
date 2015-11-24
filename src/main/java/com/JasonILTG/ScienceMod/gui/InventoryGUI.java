package com.JasonILTG.ScienceMod.gui;

import com.JasonILTG.ScienceMod.reference.Reference;
import com.JasonILTG.ScienceMod.reference.Textures;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.IInventory;

public class InventoryGUI extends GuiContainer
{
	public InventoryGUIContainer container;
	public IInventory playerInv;
	
	public InventoryGUI(InventoryGUIContainer container, IInventory playerInv)
	{
		super(container);
		this.playerInv = playerInv;
		this.container = container;
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
		this.mc.getTextureManager().bindTexture(Textures.GUI.PLAYER_INV);
		this.drawTexturedModalRect(this.guiLeft, this.container.playerInvY - 18, 0, 0, Textures.GUI.PLAYER_INV_WIDTH, Textures.GUI.PLAYER_INV_HEIGHT);
    }
	
	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
	{
		this.fontRendererObj.drawString(this.playerInv.getDisplayName().getUnformattedText(), 8, this.container.playerInvY - 12, 4210752);
	}
}