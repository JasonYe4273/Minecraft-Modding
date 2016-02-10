package com.JasonILTG.ScienceMod.block.general;

import net.minecraft.item.ItemBlock;

public interface IHasItemBlock
{
	/**
	 * Gets the <code>ItemBlock</code> class responsible for this object.
	 * 
	 * @return The corresponding <code>ItemBlock</code> class
	 */
	Class<? extends ItemBlock> getItemBlockClass();
}
