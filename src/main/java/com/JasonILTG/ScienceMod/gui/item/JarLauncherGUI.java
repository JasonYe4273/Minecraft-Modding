package com.JasonILTG.ScienceMod.gui.item;

import net.minecraft.inventory.IInventory;

import com.JasonILTG.ScienceMod.gui.general.InventoryGUI;
import com.JasonILTG.ScienceMod.inventory.general.ItemInventory;
import com.JasonILTG.ScienceMod.reference.Textures;

public class JarLauncherGUI extends InventoryGUI
{
	public JarLauncherGUI(IInventory playerInv, ItemInventory te)
	{
		super(new JarLauncherGUIContainer(playerInv, te), playerInv);
		xSize = Math.max(Textures.GUI.JAR_LAUNCHER_GUI_WIDTH, Textures.GUI.PLAYER_INV_WIDTH);
		ySize = Textures.GUI.JAR_LAUNCHER_GUI_HEIGHT + Textures.GUI.PLAYER_INV_HEIGHT;
	}
	
	@Override
	public void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
	{
		super.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);
		this.mc.getTextureManager().bindTexture(Textures.GUI.JAR_LAUNCHER);
		this.drawTexturedModalRect(this.guiLeft + (Textures.GUI.DEFAULT_GUI_X_SIZE - Textures.GUI.JAR_LAUNCHER_GUI_WIDTH) / 2,
				this.guiTop, 0, 0, Textures.GUI.JAR_LAUNCHER_GUI_WIDTH, Textures.GUI.JAR_LAUNCHER_GUI_HEIGHT);
	}
}
