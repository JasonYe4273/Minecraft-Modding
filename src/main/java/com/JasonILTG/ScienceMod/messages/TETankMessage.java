package com.JasonILTG.ScienceMod.messages;

import io.netty.buffer.ByteBuf;

/**
 * Message for updating tile entity tanks on the client side.
 * 
 * @author JasonILTG and syy1125
 */
public class TETankMessage extends TEMessage
{
    public int fluidAmount;

    /**
     * Constructor.
     * 
     * @param x The BlockPos x-value of the tile entity
     * @param y The BlockPos y-value of the tile entity
     * @param z The BlockPos z-value of the tile entity
     * @param fluidAmount The amount of fluid in the tank
     */
    public TETankMessage(int x, int y, int z, int fluidAmount)
    { 
        super(x, y, z);
        this.fluidAmount = fluidAmount;
    }
    
    /**
     * @return The amount of fluid
     */
    public int getFluidAmount()
    {
    	return fluidAmount;
    }

    @Override
    public void toBytes(ByteBuf buf)
    { 
        super.toBytes(buf);
        buf.writeInt(fluidAmount);
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        super.fromBytes(buf);
        fluidAmount = buf.readInt();
    }
}