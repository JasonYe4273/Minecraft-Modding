package com.JasonILTG.ScienceMod.tileentity.general;

/**
 * Interface for tile entities that use progress.
 * 
 * @author JasonILTG and syy1125
 */
public interface ITEProgress
{

	/**
	 * @return The current progress
	 */
	public int getCurrentProgress();
	
	/**
	 * Resets the current progress to 0.
	 */
	public void resetProgress();
	
	/**
	 * Sets whether to do progress on the client side.
	 * 
	 * @param doProgress Whether to do progress on the client side
	 */
	public void setDoProgress(boolean doProgress);
	
	/**
	 * @return Whether to do progress on the client side
	 */
	public boolean getDoProgress();
	
	/**
	 * Sets the current progress.
	 * 
	 * @param progress The current progress
	 */
	public void setProgress(int progress);
	
	/**
	 * @return The max progress
	 */
	public int getMaxProgress();
	
	/**
	 * Sets the max progress.
	 * 
	 * @param maxProgress The max progress
	 */
	public void setMaxProgress(int maxProgress);
}
