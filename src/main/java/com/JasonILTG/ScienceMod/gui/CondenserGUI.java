package com.JasonILTG.ScienceMod.gui;

import java.util.ArrayList;
import java.util.List;

import com.JasonILTG.ScienceMod.gui.general.MachineGUI;
import com.JasonILTG.ScienceMod.reference.Textures;
import com.JasonILTG.ScienceMod.tileentity.TECondenser;
import com.JasonILTG.ScienceMod.tileentity.TEElectrolyzer;
import com.JasonILTG.ScienceMod.tileentity.general.TEMachine;

import net.minecraft.inventory.IInventory;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.fluids.FluidStack;

public class CondenserGUI extends MachineGUI
{
	public CondenserGUI(IInventory playerInv, TEMachine te)
	{
		super(new CondenserGUIContainer(playerInv, te), playerInv, te);
		xSize = Math.max(Textures.GUI.CONDENSER_GUI_WIDTH, Textures.GUI.PLAYER_INV_WIDTH);
		ySize = Textures.GUI.CONDENSER_GUI_HEIGHT + Textures.GUI.PLAYER_INV_HEIGHT;
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
	{
		super.drawGuiContainerForegroundLayer(mouseX, mouseY);
		
		if (mouseX >= Textures.GUI.CONDENSER_TANK_MOUSE_X && mouseX < Textures.GUI.CONDENSER_TANK_MOUSE_X + Textures.GUI.DEFAULT_TANK_WIDTH
				&& mouseY >= Textures.GUI.CONDENSER_TANK_MOUSE_Y && mouseY < Textures.GUI.CONDENSER_TANK_MOUSE_Y + Textures.GUI.DEFAULT_TANK_HEIGHT)
		{
			TECondenser te = (TECondenser) container.getInv();
			if (te != null)
			{
				FluidStack fluid = te.getFluidInTank();
				if (fluid != null)
				{
					List<String> text = new ArrayList<String>();
					text.add(fluid.getLocalizedName());
					text.add(String.format("%s%s/%s mL", EnumChatFormatting.DARK_GRAY, te.getFluidAmount(), te.getTankCapacity()));
					this.drawHoveringText(text, mouseX - guiLeft, mouseY - guiTop);
				}
			}
		}
	}
	
	@Override
	public void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
	{
		super.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);
		this.mc.getTextureManager().bindTexture(Textures.GUI.CONDENSER);
		this.drawTexturedModalRect(this.guiLeft + (Textures.GUI.DEFAULT_GUI_X_SIZE - Textures.GUI.CONDENSER_GUI_WIDTH) / 2,
				this.guiTop,
				0, 0, Textures.GUI.CONDENSER_GUI_WIDTH, Textures.GUI.CONDENSER_GUI_HEIGHT);
		
		TECondenser te = (TECondenser) container.getInv();
		if (te != null)
		{
			drawPartial(Textures.GUI.WATER_TANK, guiLeft + Textures.GUI.CONDENSER_TANK_X, guiTop + Textures.GUI.CONDENSER_TANK_Y,
					Textures.GUI.DEFAULT_TANK_WIDTH, Textures.GUI.DEFAULT_TANK_HEIGHT, te.getFluidAmount(), TEElectrolyzer.DEFAULT_TANK_CAPACITY,
					Textures.GUI.DEFAULT_TANK_DIR, Textures.GUI.TANK);
			drawPartial(Textures.GUI.CONDENSER_PROGRESS_FULL, guiLeft + Textures.GUI.CONDENSER_PROGRESS_X, guiTop + Textures.GUI.CONDENSER_PROGRESS_Y,
					Textures.GUI.CONDENSER_PROGRESS_WIDTH, Textures.GUI.CONDENSER_PROGRESS_HEIGHT, te.getCurrentProgress(), te.getMaxProgress(),
					Textures.GUI.CONDENSER_PROGRESS_DIR, Textures.GUI.CONDENSER_PROGRESS_EMPTY);
		}
	}
}
