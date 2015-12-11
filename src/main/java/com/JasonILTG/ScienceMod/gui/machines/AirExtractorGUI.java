package com.JasonILTG.ScienceMod.gui.machines;

import com.JasonILTG.ScienceMod.reference.Textures;
import com.JasonILTG.ScienceMod.tileentity.machines.TEMachine;

import net.minecraft.inventory.IInventory;

/**
 * GUI class for air extractors.
 * 
 * @author JasonILTG and syy1125
 */
public class AirExtractorGUI extends MachineGUI
{
	/**
	 * Constructor.
	 * 
	 * @param playerInv The player inventory
	 * @param te The tile entity for this GUI
	 */
	public AirExtractorGUI(IInventory playerInv, TEMachine te)
	{
		super(new AirExtractorGUIContainer(playerInv, te), playerInv, te, Textures.GUI.Machine.AIR_EXTRACTOR, Textures.GUI.Machine.AIR_EXTRACTOR_GUI_WIDTH, Textures.GUI.Machine.AIR_EXTRACTOR_GUI_HEIGHT,
				true, Textures.GUI.PROGRESS_BAR_FULL, Textures.GUI.PROGRESS_BAR_EMPTY, Textures.GUI.DEFAULT_PROGRESS_WIDTH, Textures.GUI.DEFAULT_PROGRESS_HEIGHT,
				Textures.GUI.Machine.AIR_EXTRACTOR_PROGRESS_X, Textures.GUI.Machine.AIR_EXTRACTOR_PROGRESS_Y, Textures.GUI.DEFAULT_PROGRESS_DIR, 0, null, null, 
				true, Textures.GUI.Machine.AIR_EXTRACTOR_POWER_X, Textures.GUI.Machine.AIR_EXTRACTOR_POWER_Y, true, Textures.GUI.Machine.AIR_EXTRACTOR_TEMP_X, Textures.GUI.Machine.AIR_EXTRACTOR_TEMP_Y);
		xSize = Math.max(Textures.GUI.Machine.AIR_EXTRACTOR_GUI_WIDTH, Textures.GUI.PLAYER_INV_WIDTH);
		ySize = Textures.GUI.Machine.AIR_EXTRACTOR_GUI_HEIGHT + Textures.GUI.PLAYER_INV_HEIGHT;
	}
}
