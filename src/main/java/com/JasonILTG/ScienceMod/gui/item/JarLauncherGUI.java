package com.JasonILTG.ScienceMod.gui.item;

import com.JasonILTG.ScienceMod.gui.general.InventoryGUI;
import com.JasonILTG.ScienceMod.inventory.general.ItemInventory;
import com.JasonILTG.ScienceMod.reference.Textures;

import net.minecraft.entity.player.InventoryPlayer;

/**
 * GUI class for jar launchers.
 * 
 * @author JasonILTG and syy1125
 */
public class JarLauncherGUI extends InventoryGUI
{
	/**
	 * Constructor.
	 * 
	 * @param playerInv The player inventory
	 * @param inv The jar launcher inventory
	 */
	public JarLauncherGUI(InventoryPlayer playerInv, ItemInventory inv)
	{
		super(new JarLauncherGUIContainer(playerInv, inv), playerInv);
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
