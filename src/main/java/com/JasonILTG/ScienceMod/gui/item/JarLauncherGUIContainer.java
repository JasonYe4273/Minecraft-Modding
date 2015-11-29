package com.JasonILTG.ScienceMod.gui.item;

import net.minecraft.inventory.IInventory;

import com.JasonILTG.ScienceMod.gui.general.InventoryGUIContainer;
import com.JasonILTG.ScienceMod.gui.general.ScienceSlot;
import com.JasonILTG.ScienceMod.inventory.general.ItemInventory;
import com.JasonILTG.ScienceMod.reference.Textures;

public class JarLauncherGUIContainer extends InventoryGUIContainer
{
	protected static final int[] X_COORD = { 50, 70, 90, 110 };
	protected static final int[] Y_COORD = { 18, 36 };
	
	protected static final int PLAYER_INV_Y = Textures.GUI.JAR_LAUNCHER_GUI_HEIGHT + 22;
	
	public JarLauncherGUIContainer(IInventory playerInv, ItemInventory itemInv)
	{
		super(playerInv, itemInv.getSizeInventory(), PLAYER_INV_Y);
		addSlots();
		super.addPlayerInventorySlots(playerInv);
	}
	
	private void addSlots()
	{
		for (int i = 0; i < 4; i ++)
		{
			for (int j = 0; j < 2; j ++) {
				this.addSlotToContainer(new ScienceSlot(inventory, i + j * 4, X_COORD[i], Y_COORD[j]));
			}
		}
	}
}
