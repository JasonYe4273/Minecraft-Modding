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
    public int tankIndex;
    
    public TETankMessage() {super();};

    /**
     * Constructor.
     * 
     * @param x The BlockPos x-value of the tile entity
     * @param y The BlockPos y-value of the tile entity
     * @param z The BlockPos z-value of the tile entity
     * @param fluidAmount The amount of fluid in the tank
     */
    public TETankMessage(int x, int y, int z, int fluidAmount, int tankIndex)
    { 
        super(x, y, z);
        this.fluidAmount = fluidAmount;
        this.tankIndex = tankIndex;
    }
    
    /**
     * @return The amount of fluid
     */
    public int getFluidAmount()
    {
    	return fluidAmount;
    }
    
    /**
     * @return The tank index
     */
    public int getTankIndex()
    {
    	return tankIndex;
    }

    @Override
    public void toBytes(ByteBuf buf)
    { 
        super.toBytes(buf);
        buf.writeInt(fluidAmount);
        buf.writeInt(tankIndex);
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        super.fromBytes(buf);
        fluidAmount = buf.readInt();
        tankIndex = buf.readInt();
    }
}