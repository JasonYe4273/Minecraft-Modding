package com.JasonILTG.ScienceMod.messages;

import io.netty.buffer.ByteBuf;

/**
 * Message for updating whether a tile entity increments progress on the client side.
 * 
 * @author JasonILTG and syy1125
 */
public class TEDoProgressMessage extends TEMessage
{
    public boolean doProgress;
    
    public TEDoProgressMessage() {super();};
    
    /**
     * Constructor.
     * 
     * @param x The BlockPos x-value of the tile entity
     * @param y The BlockPos y-value of the tile entity
     * @param z The BlockPos z-value of the tile entity
     * @param doProgress Whether to increment progress
     */
    public TEDoProgressMessage(int x, int y, int z, boolean doProgress)
    { 
        super(x, y, z);
        this.doProgress = doProgress;
    }

    /**
     * @return Whether to increment progress
     */
    public boolean getDoProgress()
    {
    	return doProgress;
    }

    @Override
    public void toBytes(ByteBuf buf)
    { 
        super.toBytes(buf);
        buf.writeBoolean(doProgress);
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
       super.fromBytes(buf);
        doProgress = buf.readBoolean();
    }
}