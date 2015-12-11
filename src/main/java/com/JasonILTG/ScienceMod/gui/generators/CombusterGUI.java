package com.JasonILTG.ScienceMod.gui.generators;

import com.JasonILTG.ScienceMod.reference.Textures;
import com.JasonILTG.ScienceMod.tileentity.generators.TEGenerator;

import net.minecraft.inventory.IInventory;

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
		super(new CombusterGUIContainer(playerInv, te), playerInv, te, Textures.GUI.Generator.COMBUSTER, Textures.GUI.Generator.COMBUSTER_GUI_WIDTH, Textures.GUI.Generator.COMBUSTER_GUI_HEIGHT,
				true, Textures.GUI.FIRE_EMPTY, Textures.GUI.FIRE_FULL, Textures.GUI.FIRE_WIDTH, Textures.GUI.FIRE_HEIGHT, Textures.GUI.Generator.COMBUSTER_PROGRESS_X, Textures.GUI.Generator.COMBUSTER_PROGRESS_Y, Textures.GUI.FIRE_DIR,
				2, new int[]{ Textures.GUI.Generator.COMBUSTER_FUEL_TANK_X, Textures.GUI.Generator.COMBUSTER_COOLANT_TANK_X }, new int[] { Textures.GUI.Generator.COMBUSTER_FUEL_TANK_Y, Textures.GUI.Generator.COMBUSTER_COOLANT_TANK_Y }, 
				true, Textures.GUI.Generator.COMBUSTER_POWER_X, Textures.GUI.Generator.COMBUSTER_POWER_Y, true, Textures.GUI.Generator.COMBUSTER_TEMP_X, Textures.GUI.Generator.COMBUSTER_TEMP_Y);
		xSize = Math.max(Textures.GUI.Generator.COMBUSTER_GUI_WIDTH, Textures.GUI.PLAYER_INV_WIDTH);
		ySize = Textures.GUI.Generator.COMBUSTER_GUI_HEIGHT + Textures.GUI.PLAYER_INV_HEIGHT;
	}
}
