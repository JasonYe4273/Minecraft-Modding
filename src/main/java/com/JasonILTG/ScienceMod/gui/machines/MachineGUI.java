package com.JasonILTG.ScienceMod.gui.machines;

import java.util.ArrayList;
import java.util.List;

import com.JasonILTG.ScienceMod.ScienceMod;
import com.JasonILTG.ScienceMod.gui.general.InventoryGUI;
import com.JasonILTG.ScienceMod.messages.TEInfoRequestMessage;
import com.JasonILTG.ScienceMod.reference.Textures;
import com.JasonILTG.ScienceMod.tileentity.machines.TEElectrolyzer;
import com.JasonILTG.ScienceMod.tileentity.machines.TEMachine;

import net.minecraft.inventory.IInventory;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;

/**
 * Wrapper class for all machine GUIs in ScienceMod.
 * 
 * @author JasonILTG and syy1125
 */
public class MachineGUI extends InventoryGUI
{
	public ResourceLocation GUI;
	public int GUI_WIDTH;
	public int GUI_HEIGHT;
	public boolean HAS_PROGRESS;
	public ResourceLocation PROGRESS_FULL;
	public ResourceLocation PROGRESS_EMPTY;
	public int PROGRESS_WIDTH;
	public int PROGRESS_HEIGHT;
	public int PROGRESS_X;
	public int PROGRESS_Y;
	public int PROGRESS_DIR;
	public int NUM_TANKS;
	public int[] TANKS_X;
	public int[] TANKS_Y;
	public boolean HAS_POWER;
	public int POWER_X;
	public int POWER_Y;
	public boolean HAS_HEAT;
	public int TEMP_X;
	public int TEMP_Y;
	
	/**
	 * Constructor.
	 * 
	 * @param container The Container of this GUI
	 * @param playerInv the player's inventory
	 * @param te The tile entity of this GUI
	 * @param gui The GUI's texture
	 * @param guiWidth The GUI's width
	 * @param guiHeight The GUI's height
	 * @param hasProgress Whether the machine has progress
	 * @param progressFull The texture of the full progress bar (null if no progress)
	 * @param progressEmpty The texture of the empty progress bar (null if no progress)
	 * @param progressWidth The width of the progress bar (0 if no progress)
	 * @param progressHeight The height of the progress bar (0 if no progress)
	 * @param progressX The x-position of the progress bar (0 if no progress)
	 * @param progressY The y-position of the progress bar (0 if no progress)
	 * @param progressDir The direction the progress bar progresses (0 if no progress)
	 * @param numTanks The number of tanks the machine has
	 * @param tanksX The x-positions of the tanks (null if no tanks)
	 * @param tanksY The y-positions of the tanks (null if no tanks)
	 * @param hasPower Whether the machine has power
	 * @param powerX The x-position of the power display (0 if no power)
	 * @param powerY The y-position of the power display (0 if no power)
	 * @param hasHeat Whether the machine has heat
	 * @param tempX The x-position of the temperature display (0 if no heat)
	 * @param tempY The y-position of the temperature display (0 if no heat)
	 */
	public MachineGUI(MachineGUIContainer container, IInventory playerInv, TEMachine te, ResourceLocation gui, int guiWidth, int guiHeight,
			boolean hasProgress, ResourceLocation progressFull, ResourceLocation progressEmpty, int progressWidth, int progressHeight, int progressX, int progressY, int progressDir,
			int numTanks, int[] tanksX, int[] tanksY, boolean hasPower, int powerX, int powerY, boolean hasHeat, int tempX, int tempY)
	{
		super(container, playerInv);
		
		GUI = gui;
		GUI_WIDTH = guiWidth;
		GUI_HEIGHT = guiHeight;
		HAS_PROGRESS = hasProgress;
		PROGRESS_FULL = progressFull;
		PROGRESS_EMPTY = progressEmpty;
		PROGRESS_WIDTH = progressWidth;
		PROGRESS_HEIGHT = progressHeight;
		PROGRESS_X = progressX;
		PROGRESS_Y = progressY;
		PROGRESS_DIR = progressDir;
		NUM_TANKS = numTanks;
		TANKS_X = tanksX;
		TANKS_Y = tanksY;
		HAS_POWER = hasPower;
		POWER_X = powerX;
		POWER_Y = powerY;
		HAS_HEAT = hasHeat;
		TEMP_X = tempX;
		TEMP_Y = tempY;

		if (te.getWorld() != null && te.getWorld().isRemote) ScienceMod.snw.sendToServer(new TEInfoRequestMessage(te.getPos().getX(), te.getPos().getY(), te.getPos().getZ()));
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
	{
		super.drawGuiContainerForegroundLayer(mouseX, mouseY);
		TEMachine te = (TEMachine) container.getInv();
		if (te == null) return;
		int guiMouseX = mouseX - guiLeft;
		int guiMouseY = mouseY - guiTop;
		
		for (int i = 0; i < NUM_TANKS; i++)
		{
			if (!mouseInBounds(guiMouseX, guiMouseY, TANKS_X[i], TANKS_Y[i], Textures.GUI.DEFAULT_TANK_WIDTH, Textures.GUI.DEFAULT_TANK_HEIGHT)) continue;
			FluidStack fluid = te.getFluidInTank(i);
			if (fluid != null && fluid.amount > 0)
			{
				List<String> text = new ArrayList<String>();
				text.add(fluid.getLocalizedName());
				text.add(String.format("%s%s/%s mB", EnumChatFormatting.DARK_GRAY, te.getFluidAmount(i), te.getTankCapacity(i)));
				this.drawHoveringText(text, guiMouseX, guiMouseY);
			}
		}
		
		if (HAS_POWER && mouseInBounds(guiMouseX, guiMouseY, POWER_X, POWER_Y, Textures.GUI.POWER_WIDTH, Textures.GUI.POWER_HEIGHT))
		{
			List<String> text = new ArrayList<String>();
			text.add(te.getPowerManager().getPowerDisplay());
			this.drawHoveringText(text, guiMouseX, guiMouseY);
		}
		
		if (HAS_HEAT && mouseInBounds(guiMouseX, guiMouseY, TEMP_X, TEMP_Y, Textures.GUI.TEMP_WIDTH, Textures.GUI.TEMP_HEIGHT))
		{
			List<String> text = new ArrayList<String>();
			text.add(te.getHeatManager().getTempDisplayC());
			this.drawHoveringText(text, guiMouseX, guiMouseY);
		}
	}
	
	@Override
	public void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
	{
		super.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);
		this.mc.getTextureManager().bindTexture(GUI);
		this.drawTexturedModalRect(this.guiLeft + (Textures.GUI.DEFAULT_GUI_X_SIZE - GUI_WIDTH) / 2,
				this.guiTop, 0, 0, GUI_WIDTH, GUI_HEIGHT);

		this.mc.getTextureManager().bindTexture(Textures.GUI.UPGRADE);
		this.drawTexturedModalRect(this.guiLeft + (Textures.GUI.DEFAULT_GUI_X_SIZE + GUI_WIDTH) / 2, this.guiTop, 0, 0, Textures.GUI.UPGRADE_WIDTH, Textures.GUI.UPGRADE_HEIGHT);
		
		TEMachine te = (TEMachine) container.getInv();
		if (te == null) return;
		
		for (int i = 0; i < NUM_TANKS; i++)
		{
			drawPartial(Textures.GUI.WATER_TANK, guiLeft + TANKS_X[i], guiTop + TANKS_Y[i],
					Textures.GUI.DEFAULT_TANK_WIDTH, Textures.GUI.DEFAULT_TANK_HEIGHT, te.getFluidAmount(i), TEElectrolyzer.DEFAULT_TANK_CAPACITY,
					Textures.GUI.DEFAULT_TANK_DIR, Textures.GUI.TANK);
		}
		if (HAS_PROGRESS) drawPartial(PROGRESS_FULL, guiLeft + PROGRESS_X, guiTop + PROGRESS_Y, PROGRESS_WIDTH, PROGRESS_HEIGHT, te.getCurrentProgress(), te.getMaxProgress(), PROGRESS_DIR, PROGRESS_EMPTY);
		if (HAS_POWER) drawPartial(Textures.GUI.POWER_FULL, guiLeft + POWER_X, guiTop + POWER_Y, Textures.GUI.POWER_WIDTH, Textures.GUI.POWER_HEIGHT, te.getCurrentPower(), te.getPowerCapacity(), Textures.GUI.POWER_DIR, Textures.GUI.POWER_EMPTY);
		if (HAS_HEAT) drawPartial(Textures.GUI.TEMP_FULL, guiLeft + TEMP_X, guiTop + TEMP_Y, Textures.GUI.TEMP_WIDTH, Textures.GUI.TEMP_HEIGHT, (int) te.getCurrentTemp() - Textures.GUI.TEMP_MIN , Textures.GUI.TEMP_MAX, Textures.GUI.TEMP_DIR, Textures.GUI.TEMP_EMPTY);
	}
}
