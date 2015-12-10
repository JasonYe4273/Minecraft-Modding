package com.JasonILTG.ScienceMod.gui.machines;

import com.JasonILTG.ScienceMod.reference.Textures;
import com.JasonILTG.ScienceMod.tileentity.machines.TEMachine;

import net.minecraft.inventory.IInventory;

/**
 * GUI class for condensers.
 * 
 * @author JasonILTG and syy1125
 */
public class CondenserGUI extends MachineGUI
{
	/**
	 * Constructor.
	 * 
	 * @param playerInv The player inventory
	 * @param te The tile entity for this container
	 */
	public CondenserGUI(IInventory playerInv, TEMachine te)
	{
		super(new CondenserGUIContainer(playerInv, te), playerInv, te, Textures.GUI.Machine.CONDENSER, Textures.GUI.Machine.CONDENSER_GUI_WIDTH, Textures.GUI.Machine.CONDENSER_GUI_HEIGHT,
				true, Textures.GUI.Machine.CONDENSER_PROGRESS_FULL, Textures.GUI.Machine.CONDENSER_PROGRESS_EMPTY, Textures.GUI.Machine.CONDENSER_PROGRESS_WIDTH, Textures.GUI.Machine.CONDENSER_PROGRESS_HEIGHT,
				Textures.GUI.Machine.CONDENSER_PROGRESS_X, Textures.GUI.Machine.CONDENSER_PROGRESS_Y, Textures.GUI.Machine.CONDENSER_PROGRESS_DIR,
				1, new int[]{ Textures.GUI.Machine.CONDENSER_TANK_X }, new int[] { Textures.GUI.Machine.CONDENSER_TANK_Y }, 
				true, Textures.GUI.Machine.CONDENSER_POWER_X, Textures.GUI.Machine.CONDENSER_POWER_Y, true, Textures.GUI.Machine.CONDENSER_TEMP_X, Textures.GUI.Machine.CONDENSER_TEMP_Y);
		xSize = Math.max(Textures.GUI.Machine.CONDENSER_GUI_WIDTH, Textures.GUI.PLAYER_INV_WIDTH);
		ySize = Textures.GUI.Machine.CONDENSER_GUI_HEIGHT + Textures.GUI.PLAYER_INV_HEIGHT;
	}
}
