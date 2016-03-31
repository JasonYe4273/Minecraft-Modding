package com.JasonILTG.ScienceMod.manager;

import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

/**
 * Interface for <code>TileEntity</code> managers.
 * 
 * @author JasonILTG and syy1125
 */
public interface ITileManager
{
	/**
	 * Updates the world info of the <code>Manager</code>.
	 * 
	 * @param worldIn The world the <code>Manager</code> is in
	 * @param pos The <code>BlockPos</code> of the <code>Manager</code>
	 */
	void updateWorldInfo(World worldIn, BlockPos pos);
	
	/**
	 * Called at the start of a tick.
	 */
	void onTickStart();

	/**
	 * Called at the end of a tick.
	 */
	void onTickEnd();
}
