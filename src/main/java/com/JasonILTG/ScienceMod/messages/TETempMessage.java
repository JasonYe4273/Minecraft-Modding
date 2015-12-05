package com.JasonILTG.ScienceMod.messages;

import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class TETempMessage implements IMessage
{
    public int x;
    public int y;
    public int z;
    public float currentTemp;
    
    public TETempMessage()
    {
    	
    }

    public TETempMessage(int x, int y, int z, float currentTemp)
    { 
        this.x = x;
        this.y = y;
        this.z = z;
        this.currentTemp = currentTemp;
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
    
    public float getCurrentTemp()
    {
    	return currentTemp;
    }

    @Override
    public void toBytes(ByteBuf buf)
    { 
        buf.writeInt(x);
        buf.writeInt(y);
        buf.writeInt(z);
        buf.writeFloat(currentTemp);
    }

    @Override
    public void fromBytes(ByteBuf buf)
    { 
        x = buf.readInt();
        y = buf.readInt();
        z = buf.readInt();
        currentTemp = buf.readFloat();
    }
}