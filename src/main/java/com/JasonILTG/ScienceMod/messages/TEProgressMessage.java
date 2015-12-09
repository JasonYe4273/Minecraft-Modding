package com.JasonILTG.ScienceMod.messages;

import io.netty.buffer.ByteBuf;

/**
 * Message for updating tile entity progress on the client side.
 * 
 * @author JasonILTG and syy1125
 */
public class TEProgressMessage extends TEMessage
{
    public int progress;
    
    public TEProgressMessage() {super();};

    /**
     * Constructor.
     * 
     * @param x The BlockPos x-value of the tile entity
     * @param y The BlockPos y-value of the tile entity
     * @param z The BlockPos z-value of the tile entity
     * @param progress The current progress
     */
    public TEProgressMessage(int x, int y, int z, int progress)
    { 
        super(x, y, z);
        this.progress = progress;
    }
    
    /**
     * @return The current progress
     */
    public int getProgress()
    {
    	return progress;
    }

    @Override
    public void toBytes(ByteBuf buf)
    { 
        super.toBytes(buf);
        buf.writeInt(progress);
    }

    @Override
    public void fromBytes(ByteBuf buf)
    { 
        super.fromBytes(buf);
        progress = buf.readInt();
    }
}