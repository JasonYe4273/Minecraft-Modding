package com.JasonILTG.ScienceMod.gui.generators;

import java.util.ArrayList;
import java.util.List;

import com.JasonILTG.ScienceMod.reference.Textures;
import com.JasonILTG.ScienceMod.tileentity.generators.TECombuster;
import com.JasonILTG.ScienceMod.tileentity.generators.TEGenerator;
import com.JasonILTG.ScienceMod.tileentity.machines.TEElectrolyzer;

import net.minecraft.inventory.IInventory;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.fluids.FluidStack;

/**
 * GUI class for combusters.
 * 
 * @author JasonILTG and syy1125
 */
public class CombusterGUI extends GeneratorGUI
{
	/**
	 * Constructor.
	 * 
	 * @param playerInv The player inventory
	 * @param te The tile entity for this GUI
	 */
	public CombusterGUI(IInventory playerInv, TEGenerator te)
	{
		super(new CombusterGUIContainer(playerInv, te), playerInv, te);
		xSize = Math.max(Textures.GUI.Generator.COMBUSTER_GUI_WIDTH, Textures.GUI.PLAYER_INV_WIDTH);
		ySize = Textures.GUI.Generator.COMBUSTER_GUI_HEIGHT + Textures.GUI.PLAYER_INV_HEIGHT;
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
	{
		super.drawGuiContainerForegroundLayer(mouseX, mouseY);
		int guiMouseX = mouseX - guiLeft;
		int guiMouseY = mouseY - guiTop;
		
		if (guiMouseX >= Textures.GUI.Generator.COMBUSTER_FUEL_TANK_X && guiMouseX < Textures.GUI.Generator.COMBUSTER_FUEL_TANK_X + Textures.GUI.DEFAULT_TANK_WIDTH
				&& guiMouseY >= Textures.GUI.Generator.COMBUSTER_FUEL_TANK_Y && guiMouseY < Textures.GUI.Generator.COMBUSTER_FUEL_TANK_Y + Textures.GUI.DEFAULT_TANK_HEIGHT)
		{
			TECombuster te = (TECombuster) container.getInv();
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
		
		if (guiMouseX >= Textures.GUI.Generator.COMBUSTER_POWER_X && guiMouseX < Textures.GUI.Generator.COMBUSTER_POWER_X + Textures.GUI.POWER_WIDTH
				&& guiMouseY >= Textures.GUI.Generator.COMBUSTER_POWER_Y && guiMouseY < Textures.GUI.Generator.COMBUSTER_POWER_Y + Textures.GUI.POWER_HEIGHT)
		{
			TECombuster te = (TECombuster) container.getInv();
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
		this.mc.getTextureManager().bindTexture(Textures.GUI.Generator.COMBUSTER);
		this.drawTexturedModalRect(this.guiLeft + (Textures.GUI.DEFAULT_GUI_X_SIZE - Textures.GUI.Generator.COMBUSTER_GUI_WIDTH) / 2,
				this.guiTop,
				0, 0, Textures.GUI.Generator.COMBUSTER_GUI_WIDTH, Textures.GUI.Generator.COMBUSTER_GUI_HEIGHT);
		
		TECombuster te = (TECombuster) container.getInv();
		if (te != null)
		{
			drawPartial(Textures.GUI.WATER_TANK, guiLeft + Textures.GUI.Generator.COMBUSTER_FUEL_TANK_X, guiTop + Textures.GUI.Generator.COMBUSTER_FUEL_TANK_Y,
					Textures.GUI.DEFAULT_TANK_WIDTH, Textures.GUI.DEFAULT_TANK_HEIGHT, te.getFluidAmount(0), TEElectrolyzer.DEFAULT_TANK_CAPACITY,
					Textures.GUI.DEFAULT_TANK_DIR, Textures.GUI.TANK);
			drawPartial(Textures.GUI.Generator.COMBUSTER_PROGRESS_FULL, guiLeft + Textures.GUI.Generator.COMBUSTER_PROGRESS_X, guiTop + Textures.GUI.Generator.COMBUSTER_PROGRESS_Y,
					Textures.GUI.Generator.COMBUSTER_PROGRESS_WIDTH, Textures.GUI.Generator.COMBUSTER_PROGRESS_HEIGHT, te.getCurrentProgress(), te.getMaxProgress(),
					Textures.GUI.Generator.COMBUSTER_PROGRESS_DIR, Textures.GUI.Generator.COMBUSTER_PROGRESS_EMPTY);
			drawPartial(Textures.GUI.POWER_FULL, guiLeft + Textures.GUI.Generator.COMBUSTER_POWER_X, guiTop + Textures.GUI.Generator.COMBUSTER_POWER_Y,
					Textures.GUI.POWER_WIDTH, Textures.GUI.POWER_HEIGHT, te.getCurrentPower(), te.getPowerCapacity(),
					Textures.GUI.POWER_DIR, Textures.GUI.POWER_EMPTY);
		}
	}
}
