package com.JasonILTG.ScienceMod.tileentity.accelerator;

import com.JasonILTG.ScienceMod.item.elements.ItemElement;

public abstract class TEAcceleratorOutput extends TEAccelerator
{
	public abstract void receiveItem(ItemElement item, int meta);
}
