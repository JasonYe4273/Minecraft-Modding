package com.JasonILTG.ScienceMod.messages;

import io.netty.buffer.ByteBuf;

/**
 * Message for updating tile entity temperature on the client side.
 * 
 * @author JasonILTG and syy1125
 */
public class TETempMessage extends TEMessage
{
	/** The current temperature */
    public float currentTemp;

    public TETempMessage() {super();};
    
    /**
     * Constructor.
     * 
     * @param x The BlockPos x-value of the tile entity
     * @param y The BlockPos y-value of the tile entity
     * @param z The BlockPos z-value of the tile entity
     * @param currentTemp The current temperature of the tile entity
     */
    public TETempMessage(int x, int y, int z, float currentTemp)
    { 
        super(x, y, z);
        this.currentTemp = currentTemp;
    }
    
    /**
     * @return The current temperature
     */
    public float getCurrentTemp()
    {
    	return currentTemp;
    }

    @Override
    public void toBytes(ByteBuf buf)
    { 
        super.toBytes(buf);
        buf.writeFloat(currentTemp);
    }

    @Override
    public void fromBytes(ByteBuf buf)
    { 
        super.fromBytes(buf);
        currentTemp = buf.readFloat();
    }
}