package com.JasonILTG.ScienceMod.gui.machines;

import com.JasonILTG.ScienceMod.reference.Textures;
import com.JasonILTG.ScienceMod.tileentity.machines.TEMachine;

import net.minecraft.inventory.IInventory;

/**
 * GUI class for distillers.
 * 
 * @author JasonILTG and syy1125
 */
public class DistillerGUI extends MachineGUI // TODO Finish this.
{
	/**
	 * Constructor.
	 * 
	 * @param playerInv The player inventory
	 * @param te The tile entity for this GUI
	 */
	public DistillerGUI(IInventory playerInv, TEMachine te)
	{
		super(new DistillerGUIContainer(playerInv, te), playerInv, te, Textures.GUI.Machine.ELECTROLYZER, Textures.GUI.Machine.ELECTROLYZER_GUI_WIDTH, Textures.GUI.Machine.ELECTROLYZER_GUI_HEIGHT,
				true, Textures.GUI.Machine.ELECTROLYZER_PROGRESS_FULL, Textures.GUI.Machine.ELECTROLYZER_PROGRESS_EMPTY, Textures.GUI.Machine.ELECTROLYZER_PROGRESS_WIDTH, Textures.GUI.Machine.ELECTROLYZER_PROGRESS_HEIGHT,
				Textures.GUI.Machine.ELECTROLYZER_PROGRESS_X, Textures.GUI.Machine.ELECTROLYZER_PROGRESS_Y, Textures.GUI.Machine.ELECTROLYZER_PROGRESS_DIR,
				1, new int[]{ Textures.GUI.Machine.ELECTROLYZER_TANK_X }, new int[] { Textures.GUI.Machine.ELECTROLYZER_TANK_Y }, 
				true, Textures.GUI.Machine.ELECTROLYZER_POWER_X, Textures.GUI.Machine.ELECTROLYZER_POWER_Y, true, Textures.GUI.Machine.ELECTROLYZER_TEMP_X, Textures.GUI.Machine.ELECTROLYZER_TEMP_Y);
		xSize = Math.max(Textures.GUI.Machine.DISTILLER_GUI_WIDTH, Textures.GUI.PLAYER_INV_WIDTH);
		ySize = Textures.GUI.Machine.DISTILLER_GUI_HEIGHT + Textures.GUI.PLAYER_INV_HEIGHT;
	}
}
