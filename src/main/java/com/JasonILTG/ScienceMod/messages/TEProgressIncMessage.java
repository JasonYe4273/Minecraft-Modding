package com.JasonILTG.ScienceMod.messages;

import io.netty.buffer.ByteBuf;

/**
 * Message for updating tile entity progress on the client side.
 * 
 * @author JasonILTG and syy1125
 */
public class TEProgressIncMessage extends TEMessage
{
    public float progressInc;
    
    public TEProgressIncMessage() {super();};

    /**
     * Constructor.
     * 
     * @param x The BlockPos x-value of the tile entity
     * @param y The BlockPos y-value of the tile entity
     * @param z The BlockPos z-value of the tile entity
     * @param progress The current progress
     */
    public TEProgressIncMessage(int x, int y, int z, float progressInc)
    { 
        super(x, y, z);
        this.progressInc = progressInc;
    }
    
    /**
     * @return The current progress
     */
    public float getProgressInc()
    {
    	return progressInc;
    }

    @Override
    public void toBytes(ByteBuf buf)
    { 
        super.toBytes(buf);
        buf.writeFloat(progressInc);
    }

    @Override
    public void fromBytes(ByteBuf buf)
    { 
        super.fromBytes(buf);
        progressInc = buf.readFloat();
    }
}