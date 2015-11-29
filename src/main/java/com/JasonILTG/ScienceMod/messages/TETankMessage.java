package com.JasonILTG.ScienceMod.messages;

import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class TETankMessage implements IMessage
{
    public int x;
    public int y;
    public int z;
    public int fluidAmount;
    
    public TETankMessage()
    {
    	
    }

    public TETankMessage(int x, int y, int z, int fluidAmount)
    { 
        this.x = x;
        this.y = y;
        this.z = z;
        this.fluidAmount = fluidAmount;
    }
    
    public int getTEX()
    {
    	return x;
    }
    
    public int getTEY()
    {
    	return y;
    }
    
    public int getTEZ()
    {
    	return z;
    }
    
    public int getFluidAmount()
    {
    	return fluidAmount;
    }

    @Override
    public void toBytes(ByteBuf buf)
    { 
        buf.writeInt(x);
        buf.writeInt(y);
        buf.writeInt(z);
        buf.writeInt(fluidAmount);
    }

    @Override
    public void fromBytes(ByteBuf buf)
    { 
        x = buf.readInt();
        y = buf.readInt();
        z = buf.readInt();
        fluidAmount = buf.readInt();
    }

}