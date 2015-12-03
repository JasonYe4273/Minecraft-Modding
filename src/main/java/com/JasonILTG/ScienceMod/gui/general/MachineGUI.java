package com.JasonILTG.ScienceMod.gui.general;

import com.JasonILTG.ScienceMod.ScienceMod;
import com.JasonILTG.ScienceMod.messages.TEInfoRequestMessage;
import com.JasonILTG.ScienceMod.tileentity.general.TEMachine;

import net.minecraft.inventory.IInventory;

public class MachineGUI extends InventoryGUI
{
	public MachineGUI(MachineGUIContainer container, IInventory playerInv, TEMachine te)
	{
		super(container, playerInv);

		if (te.getWorld() != null && te.getWorld().isRemote) ScienceMod.snw.sendToServer(new TEInfoRequestMessage(te.getPos().getX(), te.getPos().getY(), te.getPos().getZ()));
	}
	/*
	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
	{
		super.drawGuiContainerForegroundLayer(mouseX, mouseY);
		
		if (mouseX >= Textures.GUI.ELECTROLYZER_TANK_MOUSE_X && mouseX < Textures.GUI.ELECTROLYZER_TANK_MOUSE_X + Textures.GUI.DEFAULT_TANK_WIDTH
				&& mouseY >= Textures.GUI.ELECTROLYZER_TANK_MOUSE_Y && mouseY < Textures.GUI.ELECTROLYZER_TANK_MOUSE_Y + Textures.GUI.DEFAULT_TANK_HEIGHT)
		{
			TEMachine te = (TEMachine) container.getInv();
			if (te != null)
			{
				FluidStack fluid = te.getFluidInTank();
				if (fluid != null && fluid.amount > 0)
				{
					List<String> text = new ArrayList<String>();
					text.add(fluid.getLocalizedName());
					text.add(String.format("%s%s/%s mB", EnumChatFormatting.DARK_GRAY, te.getFluidAmount(), te.getTankCapacity()));
					this.drawHoveringText(text, mouseX - guiLeft, mouseY - guiTop);
				}
			}
		}
	}
	*/
}
