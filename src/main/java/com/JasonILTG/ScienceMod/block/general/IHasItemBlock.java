package com.JasonILTG.ScienceMod.block.general;

import net.minecraft.item.ItemBlock;

/**
 * Interface for blocks that have ItemBlock classes.
 * 
 * @author JasonILTG and syy1125
 */
public interface IHasItemBlock
{
	/**
	 * Gets the <code>ItemBlock</code> class responsible for this object.
	 * 
	 * @return The corresponding <code>ItemBlock</code> class
	 */
	Class<? extends ItemBlock> getItemBlockClass();
}
