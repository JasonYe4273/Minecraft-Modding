package com.JasonILTG.ScienceMod.messages;

import io.netty.buffer.ByteBuf;

/**
 * Message for updating tile entity max progress.
 * 
 * @author JasonILTG and syy1125
 */
public class SolarPanelModeMessage extends TEMessage
{
	/** The mode of the solar panel */
    public int mode;
    
    public SolarPanelModeMessage() {super();};
    
    /**
     * Constructor.
     * 
     * @param x The BlockPos x-value of the tile entity
     * @param y The BlockPos y-value of the tile entity
     * @param z The BlockPos z-value of the tile entity
     * @param mode The max progress
     */
    public SolarPanelModeMessage(int x, int y, int z, int mode)
    { 
        super(x, y, z);
        this.mode = mode;
    }
    
    /**
     * @return The max progress
     */
    public int getMode()
    {
    	return mode;
    }

    @Override
    public void toBytes(ByteBuf buf)
    { 
        super.toBytes(buf);
        buf.writeInt(mode);
    }

    @Override
    public void fromBytes(ByteBuf buf)
    { 
        super.fromBytes(buf);
        mode = buf.readInt();
    }
}