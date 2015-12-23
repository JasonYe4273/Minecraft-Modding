package com.JasonILTG.ScienceMod.messages;

/**
 * Message for resetting the progress of tile entities on the client side
 * 
 * @author JasonILTG and syy1125
 */
public class TEResetProgressMessage extends TEMessage
{
	public TEResetProgressMessage() {super();};
	
	/**
	 * Constructor.
	 * 
	 * @param x The BlockPos x-value of the tile entity
     * @param y The BlockPos y-value of the tile entity
     * @param z The BlockPos z-value of the tile entity
	 */
    public TEResetProgressMessage(int x, int y, int z)
    { 
        super(x, y, z);
    }
}