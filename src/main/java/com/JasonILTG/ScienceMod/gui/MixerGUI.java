package com.JasonILTG.ScienceMod.gui;

import java.util.ArrayList;
import java.util.List;

import com.JasonILTG.ScienceMod.gui.general.MachineGUI;
import com.JasonILTG.ScienceMod.reference.Textures;
import com.JasonILTG.ScienceMod.tileentity.TEElectrolyzer;
import com.JasonILTG.ScienceMod.tileentity.TEMixer;
import com.JasonILTG.ScienceMod.tileentity.general.TEMachine;

import net.minecraft.inventory.IInventory;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.fluids.FluidStack;

public class MixerGUI extends MachineGUI
{
	public MixerGUI(IInventory playerInv, TEMachine te)
	{
		super(new MixerGUIContainer(playerInv, te), playerInv, te);
		xSize = Math.max(Textures.GUI.MIXER_GUI_WIDTH, Textures.GUI.PLAYER_INV_WIDTH);
		ySize = Textures.GUI.MIXER_GUI_HEIGHT + Textures.GUI.PLAYER_INV_HEIGHT;
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
	{
		super.drawGuiContainerForegroundLayer(mouseX, mouseY);
		
		if (mouseX >= Textures.GUI.MIXER_TANK_MOUSE_X && mouseX < Textures.GUI.MIXER_TANK_MOUSE_X + Textures.GUI.DEFAULT_TANK_WIDTH
				&& mouseY >= Textures.GUI.MIXER_TANK_MOUSE_Y && mouseY < Textures.GUI.MIXER_TANK_MOUSE_Y + Textures.GUI.DEFAULT_TANK_HEIGHT)
		{
			TEMixer te = (TEMixer) container.getInv();
			if (te != null)
			{
				FluidStack fluid = te.getFluidInTank();
				List<String> ionText = te.getIonList();
				List<String> precipitateText = te.getPrecipitateList();
				List<String> text = new ArrayList<String>();
				if (fluid != null && fluid.amount != 0)
				{
					text.add("Solution");
					text.add(String.format("%s%s/%s mL", EnumChatFormatting.DARK_GRAY, te.getFluidAmount(), te.getTankCapacity()));
					for (String ion : ionText)
					{
						text.add(ion);
					}
				}
				else
				{
					text.add("Mixture");
				}
				
				for (String precipitate : precipitateText)
				{
					text.add(precipitate);
				}
				
				this.drawHoveringText(text, mouseX - guiLeft, mouseY - guiTop);
			}
		}
	}
	
	@Override
	public void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
	{
		super.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);
		this.mc.getTextureManager().bindTexture(Textures.GUI.MIXER);
		this.drawTexturedModalRect(this.guiLeft + (Textures.GUI.DEFAULT_GUI_X_SIZE - Textures.GUI.MIXER_GUI_WIDTH) / 2,
				this.guiTop,
				0, 0, Textures.GUI.MIXER_GUI_WIDTH, Textures.GUI.MIXER_GUI_HEIGHT);
		
		TEMixer te = (TEMixer) container.getInv();
		if (te != null)
		{
			drawPartial(Textures.GUI.WATER_TANK, guiLeft + Textures.GUI.MIXER_TANK_X, guiTop + Textures.GUI.MIXER_TANK_Y,
					Textures.GUI.DEFAULT_TANK_WIDTH, Textures.GUI.DEFAULT_TANK_HEIGHT, te.getFluidAmount(), TEElectrolyzer.DEFAULT_TANK_CAPACITY,
					Textures.GUI.DEFAULT_TANK_DIR, Textures.GUI.TANK);
			if (te.getFluidAmount() > 0)
			{
				drawPartial(Textures.GUI.MIXER_PROGRESS_FULL_SOLUTION, guiLeft + Textures.GUI.MIXER_PROGRESS_X, guiTop + Textures.GUI.MIXER_PROGRESS_Y,
						Textures.GUI.MIXER_PROGRESS_WIDTH, Textures.GUI.MIXER_PROGRESS_HEIGHT, te.getCurrentProgress(), te.getMaxProgress(),
						Textures.GUI.MIXER_PROGRESS_DIR, Textures.GUI.MIXER_PROGRESS_EMPTY);
			}
			else
			{
				drawPartial(Textures.GUI.MIXER_PROGRESS_FULL_MIXTURE, guiLeft + Textures.GUI.MIXER_PROGRESS_X, guiTop + Textures.GUI.MIXER_PROGRESS_Y,
						Textures.GUI.MIXER_PROGRESS_WIDTH, Textures.GUI.MIXER_PROGRESS_HEIGHT, te.getCurrentProgress(), te.getMaxProgress(),
						Textures.GUI.MIXER_PROGRESS_DIR, Textures.GUI.MIXER_PROGRESS_EMPTY);
			}
		}
	}
}
