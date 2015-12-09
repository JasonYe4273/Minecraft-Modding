package com.JasonILTG.ScienceMod.gui.machines;

import java.util.ArrayList;
import java.util.List;

import com.JasonILTG.ScienceMod.reference.Textures;
import com.JasonILTG.ScienceMod.tileentity.machines.TEElectrolyzer;
import com.JasonILTG.ScienceMod.tileentity.machines.TEMachine;

import net.minecraft.inventory.IInventory;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.fluids.FluidStack;

/**
 * GUI class for electrolyzers
 * 
 * @author JasonILTG and syy1125
 */
public class ElectrolyzerGUI extends MachineGUI
{
	/**
	 * Constructor.
	 * 
	 * @param playerInv The player inventory
	 * @param te The tile entity for this GUI
	 */
	public ElectrolyzerGUI(IInventory playerInv, TEMachine te)
	{
		super(new ElectrolyzerGUIContainer(playerInv, te), playerInv, te);
		xSize = Math.max(Textures.GUI.Machine.ELECTROLYZER_GUI_WIDTH, Textures.GUI.PLAYER_INV_WIDTH);
		ySize = Textures.GUI.Machine.ELECTROLYZER_GUI_HEIGHT + Textures.GUI.PLAYER_INV_HEIGHT;
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
	{
		super.drawGuiContainerForegroundLayer(mouseX, mouseY);
		int guiMouseX = mouseX - guiLeft;
		int guiMouseY = mouseY - guiTop;
		
		if (guiMouseX >= Textures.GUI.Machine.ELECTROLYZER_TANK_X && guiMouseX < Textures.GUI.Machine.ELECTROLYZER_TANK_X + Textures.GUI.DEFAULT_TANK_WIDTH
				&& guiMouseY >= Textures.GUI.Machine.ELECTROLYZER_TANK_Y && guiMouseY < Textures.GUI.Machine.ELECTROLYZER_TANK_Y + Textures.GUI.DEFAULT_TANK_HEIGHT)
		{
			TEElectrolyzer te = (TEElectrolyzer) container.getInv();
			if (te != null)
			{
				FluidStack fluid = te.getFluidInTank(0);
				if (fluid != null && fluid.amount > 0)
				{
					List<String> text = new ArrayList<String>();
					text.add(fluid.getLocalizedName());
					text.add(String.format("%s%s/%s mB", EnumChatFormatting.DARK_GRAY, te.getFluidAmount(0), te.getTankCapacity(0)));
					this.drawHoveringText(text, guiMouseX, guiMouseY);
				}
			}
		}
		
		if (guiMouseX >= Textures.GUI.Machine.ELECTROLYZER_POWER_X && guiMouseX < Textures.GUI.Machine.ELECTROLYZER_POWER_X + Textures.GUI.POWER_WIDTH
				&& guiMouseY >= Textures.GUI.Machine.ELECTROLYZER_POWER_Y && guiMouseY < Textures.GUI.Machine.ELECTROLYZER_POWER_Y + Textures.GUI.POWER_HEIGHT)
		{
			TEElectrolyzer te = (TEElectrolyzer) container.getInv();
			if (te != null)
			{
				List<String> text = new ArrayList<String>();
				text.add(te.getPowerManager().getPowerDisplay());
				this.drawHoveringText(text, guiMouseX, guiMouseY);
			}
		}
	}
	
	@Override
	public void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
	{
		super.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);
		this.mc.getTextureManager().bindTexture(Textures.GUI.Machine.ELECROLYZER);
		this.drawTexturedModalRect(this.guiLeft + (Textures.GUI.DEFAULT_GUI_X_SIZE - Textures.GUI.Machine.ELECTROLYZER_GUI_WIDTH) / 2,
				this.guiTop,
				0, 0, Textures.GUI.Machine.ELECTROLYZER_GUI_WIDTH, Textures.GUI.Machine.ELECTROLYZER_GUI_HEIGHT);
		
		TEElectrolyzer te = (TEElectrolyzer) container.getInv();
		if (te != null)
		{
			drawPartial(Textures.GUI.WATER_TANK, guiLeft + Textures.GUI.Machine.ELECTROLYZER_TANK_X, guiTop + Textures.GUI.Machine.ELECTROLYZER_TANK_Y,
					Textures.GUI.DEFAULT_TANK_WIDTH, Textures.GUI.DEFAULT_TANK_HEIGHT, te.getFluidAmount(0), TEElectrolyzer.DEFAULT_TANK_CAPACITY,
					Textures.GUI.DEFAULT_TANK_DIR, Textures.GUI.TANK);
			drawPartial(Textures.GUI.Machine.ELECTROLYZER_PROGRESS_FULL, guiLeft + Textures.GUI.Machine.ELECTROLYZER_PROGRESS_X, guiTop + Textures.GUI.Machine.ELECTROLYZER_PROGRESS_Y,
					Textures.GUI.Machine.ELECTROLYZER_PROGRESS_WIDTH, Textures.GUI.Machine.ELECTROLYZER_PROGRESS_HEIGHT, te.getCurrentProgress(), te.getMaxProgress(),
					Textures.GUI.Machine.ELECTROLYZER_PROGRESS_DIR, Textures.GUI.Machine.ELECTROLYZER_PROGRESS_EMPTY);
			drawPartial(Textures.GUI.POWER_FULL, guiLeft + Textures.GUI.Machine.ELECTROLYZER_POWER_X, guiTop + Textures.GUI.Machine.ELECTROLYZER_POWER_Y,
					Textures.GUI.POWER_WIDTH, Textures.GUI.POWER_HEIGHT, te.getCurrentPower(), te.getPowerCapacity(),
					Textures.GUI.POWER_DIR, Textures.GUI.POWER_EMPTY);
		}
	}
}
