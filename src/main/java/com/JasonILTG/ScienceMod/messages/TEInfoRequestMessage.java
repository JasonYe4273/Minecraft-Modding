package com.JasonILTG.ScienceMod.messages;

/**
 * Message for requesting info for tile entities from the server side.
 * 
 * @author JasonILTG and syy1125
 */
public class TEInfoRequestMessage extends TEMessage
{
	public TEInfoRequestMessage() {super();};
	
	/**
	 * Constructor.
	 * 
	 * @param x The BlockPos x-value of the tile entity
	 * @param y The BlockPos y-value of the tile entity
	 * @param z The BlockPos z-value of the tile entity
	 */
	public TEInfoRequestMessage(int x, int y, int z)
    { 
        super(x, y, z);
    }
}