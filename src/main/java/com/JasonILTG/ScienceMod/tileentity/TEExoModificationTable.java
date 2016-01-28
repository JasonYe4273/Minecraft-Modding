package com.JasonILTG.ScienceMod.tileentity;

import com.JasonILTG.ScienceMod.tileentity.general.ITileEntityGUI;
import com.JasonILTG.ScienceMod.tileentity.general.TEInventory;

public class TEExoModificationTable
		extends TEInventory implements ITileEntityGUI
{
	public static final String NAME = "Exoskeleton Modification Table";
	
	private static final int ARMOR_SLOT_SIZE = 1;
	private static final int UPGRADE_INV_SIZE = 4;
	
	public TEExoModificationTable()
	{
		super(NAME, new int[] { ARMOR_SLOT_SIZE, UPGRADE_INV_SIZE }, 0);
	}
	
}
