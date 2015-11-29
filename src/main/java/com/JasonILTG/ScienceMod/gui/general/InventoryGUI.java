package com.JasonILTG.ScienceMod.gui.general;

import com.JasonILTG.ScienceMod.reference.Textures;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;

public class InventoryGUI extends GuiContainer
{
	public InventoryGUIContainer container;
	public IInventory playerInv;
	
	public InventoryGUI(InventoryGUIContainer container, IInventory playerInv)
	{
		super(container);
		this.playerInv = playerInv;
		this.container = container;
		this.xSize = Textures.GUI.DEFAULT_GUI_X_SIZE;
		this.ySize = Textures.GUI.DEFAULT_GUI_Y_SIZE;
	}
	
	public InventoryGUI(InventoryGUIContainer container, IInventory playerInv, int xSize, int ySize)
	{
		super(container);
		this.playerInv = playerInv;
		this.xSize = xSize;
		this.ySize = ySize;
	}
	
	@Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
	{
		GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
		this.mc.getTextureManager().bindTexture(Textures.GUI.PLAYER_INV);
		this.drawTexturedModalRect(this.guiLeft, this.guiTop + this.container.playerInvY - 18, 0, 0, Textures.GUI.PLAYER_INV_WIDTH, Textures.GUI.PLAYER_INV_HEIGHT);
    }
	
	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
	{
		this.fontRendererObj.drawString(this.playerInv.getDisplayName().getUnformattedText(), 8, this.container.playerInvY - 12, 4210752);
		String s = this.container.inventory.getDisplayName().getUnformattedText();
		this.fontRendererObj.drawString(s, (Textures.GUI.DEFAULT_GUI_X_SIZE - this.fontRendererObj.getStringWidth(s)) / 2, 6, 4210752);
	}
	
	public void drawPartial(ResourceLocation img, int x, int y, int width, int height, int current, int max, int fromDir, ResourceLocation opposite)
	{
		this.mc.getTextureManager().bindTexture(img);
		switch (fromDir)
		{
			case 0: { // TOP
				this.drawTexturedModalRect(x, y, 0, 0, width, height * current / max);
				break;
			}
			case 1: { // BOTTOM
				this.drawTexturedModalRect(x, y + height - height * current / max, 0, height - height * current / max, width, height * current / max);
				break;
			}
			case 2: { // LEFT
				this.drawTexturedModalRect(x, y, 0, 0, width * current / max, height);
				break;
			}
			case 3: { // RIGHT
				this.drawTexturedModalRect(x + width - width * current / max, y, 0, 0, width * current / max, height);
				break;
			}
		}
		
		if (opposite == null) return;
		this.mc.getTextureManager().bindTexture(opposite);
		switch (fromDir)
		{
			case 0: { // TOP
				this.drawTexturedModalRect(x, y + height * current / max, 0, height * current / max, width, height - height * current / max);
				break;
			}
			case 1: { // BOTTOM
				this.drawTexturedModalRect(x, y, 0, 0, width, height - height * current / max);
				break;
			}
			case 2: { // LEFT
				this.drawTexturedModalRect(x + width * current / max, y, width * current / max, 0, width - width * current / max, height);
				break;
			}
			case 3: { // RIGHT
				this.drawTexturedModalRect(x, y, 0, 0, width - width * current / max, height);
				break;
			}
		}
	}
}