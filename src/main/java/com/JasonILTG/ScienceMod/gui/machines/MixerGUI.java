package com.JasonILTG.ScienceMod.gui.machines;

import java.util.ArrayList;
import java.util.List;

import com.JasonILTG.ScienceMod.reference.Textures;
import com.JasonILTG.ScienceMod.tileentity.machines.TEMachine;
import com.JasonILTG.ScienceMod.tileentity.machines.TEMixer;

import net.minecraft.inventory.IInventory;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.fluids.FluidStack;

/**
 * GUI class for mixers
 * 
 * @author JasonILTG and syy1125
 */
public class MixerGUI extends MachineGUI
{
	/**
	 * Constructor.
	 * 
	 * @param playerInv The player inventory
	 * @param te The tile entity for this GUI
	 */
	public MixerGUI(IInventory playerInv, TEMachine te)
	{
		super(new MixerGUIContainer(playerInv, te), playerInv, te, Textures.GUI.Machine.MIXER, Textures.GUI.Machine.MIXER_GUI_WIDTH, Textures.GUI.Machine.MIXER_GUI_HEIGHT,
				false, null, null, 0, 0, Textures.GUI.Machine.MIXER_PROGRESS_X, Textures.GUI.Machine.MIXER_PROGRESS_Y, Textures.GUI.Machine.MIXER_PROGRESS_DIR,
				0, null, null, false, 0, 0, true, Textures.GUI.Machine.MIXER_TEMP_X, Textures.GUI.Machine.MIXER_TEMP_Y);
		xSize = Math.max(Textures.GUI.Machine.MIXER_GUI_WIDTH, Textures.GUI.PLAYER_INV_WIDTH);
		ySize = Textures.GUI.Machine.MIXER_GUI_HEIGHT + Textures.GUI.PLAYER_INV_HEIGHT;
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
	{
		super.drawGuiContainerForegroundLayer(mouseX, mouseY);
		int guiMouseX = mouseX - guiLeft;
		int guiMouseY = mouseY - guiTop;
		
		if (mouseInBounds(guiMouseX, guiMouseY, Textures.GUI.Machine.MIXER_TANK_X, Textures.GUI.Machine.MIXER_TANK_Y, Textures.GUI.DEFAULT_TANK_WIDTH, Textures.GUI.DEFAULT_TANK_HEIGHT))
		{
			TEMixer te = (TEMixer) container.getInv();
			if (te != null)
			{
				FluidStack fluid = te.getFluidInTank(0);
				List<String> ionText = te.getIonList();
				List<String> precipitateText = te.getPrecipitateList();
				List<String> text = new ArrayList<String>();
				if (fluid != null && fluid.amount != 0)
				{
					text.add("Solution");
					text.add(String.format("%s%s/%s mB", EnumChatFormatting.DARK_GRAY, te.getFluidAmount(0), te.getTankCapacity(0)));
					for (String ion : ionText)
					{
						text.add(ion);
					}
				}
				else if (precipitateText.size() > 0)
				{
					text.add("Mixture");
				}
				
				for (String precipitate : precipitateText)
				{
					text.add(precipitate);
				}
				
				this.drawHoveringText(text, guiMouseX, guiMouseY);
			}
		}
	}
	
	@Override
	public void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
	{
		super.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);
		TEMachine te = (TEMachine) container.getInv();
		if (te == null) return;

		drawPartial(Textures.GUI.WATER_TANK, guiLeft + Textures.GUI.Machine.MIXER_TANK_X, guiTop + Textures.GUI.Machine.MIXER_TANK_Y,
				Textures.GUI.DEFAULT_TANK_WIDTH, Textures.GUI.DEFAULT_TANK_HEIGHT, te.getFluidAmount(0), TEMachine.DEFAULT_TANK_CAPACITY,
				Textures.GUI.DEFAULT_TANK_DIR, Textures.GUI.TANK);
		
		if (te.getFluidAmount(0) > 0)
		{
			drawPartial(Textures.GUI.Machine.MIXER_PROGRESS_FULL_SOLUTION, guiLeft + Textures.GUI.Machine.MIXER_PROGRESS_X, guiTop + Textures.GUI.Machine.MIXER_PROGRESS_Y,
					Textures.GUI.Machine.MIXER_PROGRESS_WIDTH, Textures.GUI.Machine.MIXER_PROGRESS_HEIGHT, (int) te.getCurrentProgress(), te.getMaxProgress(),
					Textures.GUI.Machine.MIXER_PROGRESS_DIR, Textures.GUI.Machine.MIXER_PROGRESS_EMPTY);
		}
		else
		{
			drawPartial(Textures.GUI.Machine.MIXER_PROGRESS_FULL_MIXTURE, guiLeft + Textures.GUI.Machine.MIXER_PROGRESS_X, guiTop + Textures.GUI.Machine.MIXER_PROGRESS_Y,
					Textures.GUI.Machine.MIXER_PROGRESS_WIDTH, Textures.GUI.Machine.MIXER_PROGRESS_HEIGHT, (int) te.getCurrentProgress(), te.getMaxProgress(),
					Textures.GUI.Machine.MIXER_PROGRESS_DIR, Textures.GUI.Machine.MIXER_PROGRESS_EMPTY);
		}
	}
}
