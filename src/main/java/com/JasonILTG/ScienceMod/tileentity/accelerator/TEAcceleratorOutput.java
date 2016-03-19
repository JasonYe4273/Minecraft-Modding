package com.JasonILTG.ScienceMod.tileentity.accelerator;

import com.JasonILTG.ScienceMod.item.chemistry.ItemElement;

public abstract class TEAcceleratorOutput extends TEAccelerator
{
	public abstract void receiveItem(ItemElement item, int meta);
}
