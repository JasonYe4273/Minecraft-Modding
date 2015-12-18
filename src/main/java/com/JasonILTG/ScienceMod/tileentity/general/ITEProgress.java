package com.JasonILTG.ScienceMod.tileentity.general;

/**
 * Interface for tile entities that use progress.
 * 
 * @author JasonILTG and syy1125
 */
public interface ITEProgress
{
	/**
	 * Resets the current progress to 0.
	 */
	public void resetProgress();
	
	/**
	 * @return Whether to do progress on the client side
	 */
	public boolean getDoProgress();
	
	/**
	 * Sets whether to do progress on the client side.
	 * 
	 * @param doProgress Whether to do progress on the client side
	 */
	public void setDoProgress(boolean doProgress);

	/**
	 * @return The current progress
	 */
	public float getCurrentProgress();
	
	/**
	 * Sets the current progress.
	 * 
	 * @param progress The current progress
	 */
	public void setProgress(float progress);
	
	/**
	 * @return The amount the progress is incremented every tick
	 */
	public float getProgressInc();
	
	/**
	 * Sets the amount the progress is incremented every tick.
	 * 
	 * @param progressInc The amount the progress is incremented every tick
	 */
	public void setProgressInc(float progressInc);
	
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
