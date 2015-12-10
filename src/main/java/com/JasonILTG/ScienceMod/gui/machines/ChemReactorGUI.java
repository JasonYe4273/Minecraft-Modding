package com.JasonILTG.ScienceMod.gui.machines;

import com.JasonILTG.ScienceMod.reference.Textures;
import com.JasonILTG.ScienceMod.tileentity.machines.TEMachine;

import net.minecraft.inventory.IInventory;

/**
 * GUI class for chemical reactors.
 * 
 * @author JasonILTG and syy1125
 */
public class ChemReactorGUI extends MachineGUI
{
	/**
	 * Constructor.
	 * 
	 * @param playerInv The player inventory
	 * @param te The tile entity for this GUI
	 */
	public ChemReactorGUI(IInventory playerInv, TEMachine te)
	{
		super(new ChemReactorGUIContainer(playerInv, te), playerInv, te, Textures.GUI.Machine.CHEM_REACTOR, Textures.GUI.Machine.CHEM_REACTOR_GUI_WIDTH, Textures.GUI.Machine.CHEM_REACTOR_GUI_HEIGHT,
				true, Textures.GUI.Machine.CHEM_REACTOR_PROGRESS_FULL, Textures.GUI.Machine.CHEM_REACTOR_PROGRESS_EMPTY, Textures.GUI.Machine.CHEM_REACTOR_PROGRESS_WIDTH, Textures.GUI.Machine.CHEM_REACTOR_PROGRESS_HEIGHT,
				Textures.GUI.Machine.CHEM_REACTOR_PROGRESS_X, Textures.GUI.Machine.CHEM_REACTOR_PROGRESS_Y, Textures.GUI.Machine.CHEM_REACTOR_PROGRESS_DIR, 0, null, null,
				true, Textures.GUI.Machine.CHEM_REACTOR_POWER_X, Textures.GUI.Machine.CHEM_REACTOR_POWER_Y, true, Textures.GUI.Machine.CHEM_REACTOR_TEMP_X, Textures.GUI.Machine.CHEM_REACTOR_TEMP_Y);
		xSize = Math.max(Textures.GUI.Machine.CHEM_REACTOR_GUI_WIDTH, Textures.GUI.PLAYER_INV_WIDTH);
		ySize = Textures.GUI.Machine.CHEM_REACTOR_GUI_HEIGHT + Textures.GUI.PLAYER_INV_HEIGHT;
	}
}
