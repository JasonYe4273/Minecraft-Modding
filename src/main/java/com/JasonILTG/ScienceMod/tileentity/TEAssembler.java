package com.JasonILTG.ScienceMod.tileentity;

import com.JasonILTG.ScienceMod.tileentity.general.TEInventory;

import net.minecraft.server.gui.IUpdatePlayerListBox;

public class TEAssembler extends TEInventory implements IUpdatePlayerListBox
{
	public static final String NAME = "Assembler";
	
	public static final int INPUT_INV_SIZE = 9;
	public static final int OUTPUT_INV_SIZE = 1;
	
	public boolean toUpdate;
	
	public TEAssembler()
	{
		super(NAME, new int[]{ INPUT_INV_SIZE, OUTPUT_INV_SIZE }, 0);
		toUpdate = false;
	}

	@Override
	public void update()
	{
		if (!worldObj.isRemote && !toUpdate)
		{
			
		}
	}
	
	public void markForUpdate()
	{
		toUpdate = true;
	}
}
