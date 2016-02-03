/**
 * 
 */
package com.JasonILTG.ScienceMod.gui.component;

import com.JasonILTG.ScienceMod.ScienceMod;
import com.JasonILTG.ScienceMod.gui.general.InventoryGUI;
import com.JasonILTG.ScienceMod.messages.TEInfoRequestMessage;
import com.JasonILTG.ScienceMod.reference.Textures;
import com.JasonILTG.ScienceMod.tileentity.component.TEAssembler;

import net.minecraft.inventory.IInventory;

/**
 * 
 * @author JasonILTG and syy1125
 */
public class AssemblerGUI extends InventoryGUI
{
	public AssemblerGUI(IInventory playerInv, TEAssembler te)
	{
		super(new AssemblerGUIContainer(playerInv, te), playerInv);
		
		if (te.getWorld() != null && te.getWorld().isRemote) ScienceMod.snw.sendToServer(new TEInfoRequestMessage(te.getPos().getX(), te.getPos().getY(), te.getPos().getZ()));
	}
	
	@Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
	{
		super.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);
		
		this.mc.getTextureManager().bindTexture(Textures.GUI.Component.ASSEMBLER);
		this.drawTexturedModalRect(this.guiLeft + (Textures.GUI.DEFAULT_GUI_X_SIZE - Textures.GUI.Component.ASSEMBLER_GUI_WIDTH) / 2,
				this.guiTop, 0, 0, Textures.GUI.Component.ASSEMBLER_GUI_WIDTH, Textures.GUI.Component.ASSEMBLER_GUI_HEIGHT);
    }
}
