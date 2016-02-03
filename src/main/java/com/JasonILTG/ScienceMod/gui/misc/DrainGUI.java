package com.JasonILTG.ScienceMod.gui.misc;

import com.JasonILTG.ScienceMod.ScienceMod;
import com.JasonILTG.ScienceMod.gui.general.InventoryGUI;
import com.JasonILTG.ScienceMod.messages.TEInfoRequestMessage;
import com.JasonILTG.ScienceMod.reference.Textures;
import com.JasonILTG.ScienceMod.tileentity.misc.TEDrain;

import net.minecraft.inventory.IInventory;

/**
 * GUI class for drains.
 * 
 * @author JasonILTG and syy1125
 */
public class DrainGUI extends InventoryGUI
{
	public DrainGUI(IInventory playerInv, TEDrain te)
	{
		super(new DrainGUIContainer(playerInv, te), playerInv);
		
		if (te.getWorld() != null && te.getWorld().isRemote) ScienceMod.snw.sendToServer(new TEInfoRequestMessage(te.getPos().getX(), te.getPos().getY(), te.getPos().getZ()));
	}
	
	@Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
	{
		super.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);
		
		this.mc.getTextureManager().bindTexture(Textures.GUI.Misc.DRAIN);
		this.drawTexturedModalRect(this.guiLeft + (Textures.GUI.DEFAULT_GUI_X_SIZE - Textures.GUI.Misc.DRAIN_GUI_WIDTH) / 2,
				this.guiTop, 0, 0, Textures.GUI.Misc.DRAIN_GUI_WIDTH, Textures.GUI.Misc.DRAIN_GUI_HEIGHT);
		xSize = Math.max(Textures.GUI.Misc.DRAIN_GUI_WIDTH, Textures.GUI.PLAYER_INV_WIDTH);
		ySize = Textures.GUI.Misc.DRAIN_GUI_HEIGHT + Textures.GUI.PLAYER_INV_HEIGHT;
    }
}
