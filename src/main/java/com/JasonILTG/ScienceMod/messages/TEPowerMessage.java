package com.JasonILTG.ScienceMod.messages;

import io.netty.buffer.ByteBuf;

/**
 * Message for updating tile entity power on the client side.
 * 
 * @author JasonILTG and syy1125
 */
public class TEPowerMessage extends TEMessage
{
    public int currentPower;
    
    public TEPowerMessage() {super();};
    
    /**
     * Constructor.
     * 
     * @param x The BlockPos x-value of the tile entity
     * @param y The BlockPos y-value of the tile entity
     * @param z The BlockPos z-value of the tile entity
     * @param currentPower The current power
     */
    public TEPowerMessage(int x, int y, int z, int currentPower)
    { 
        super(x, y, x);
        this.currentPower = currentPower;
    }

    /**
     * @return The current power
     */
    public int getCurrentPower()
    {
    	return currentPower;
    }

    @Override
    public void toBytes(ByteBuf buf)
    { 
        super.toBytes(buf);
        buf.writeInt(currentPower);
    }

    @Override
    public void fromBytes(ByteBuf buf)
    { 
        super.fromBytes(buf);
        currentPower = buf.readInt();
    }
}