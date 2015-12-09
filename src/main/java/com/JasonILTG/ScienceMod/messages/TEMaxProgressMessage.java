package com.JasonILTG.ScienceMod.messages;

import io.netty.buffer.ByteBuf;

/**
 * Message for updating tile entity max progress.
 * 
 * @author JasonILTG and syy1125
 */
public class TEMaxProgressMessage extends TEMessage
{
    public int maxProgress;
    
    public TEMaxProgressMessage() {super();};
    
    /**
     * Constructor.
     * 
     * @param x The BlockPos x-value of the tile entity
     * @param y The BlockPos y-value of the tile entity
     * @param z The BlockPos z-value of the tile entity
     * @param maxProgress The max progress
     */
    public TEMaxProgressMessage(int x, int y, int z, int maxProgress)
    { 
        super(x, y, z);
        this.maxProgress = maxProgress;
    }
    
    /**
     * @return The max progress
     */
    public int getMaxProgress()
    {
    	return maxProgress;
    }

    @Override
    public void toBytes(ByteBuf buf)
    { 
        super.toBytes(buf);
        buf.writeInt(maxProgress);
    }

    @Override
    public void fromBytes(ByteBuf buf)
    { 
        super.fromBytes(buf);
        maxProgress = buf.readInt();
    }
}