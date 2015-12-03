package com.JasonILTG.ScienceMod.messages;

import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class TEPowerMessage implements IMessage
{
    public int x;
    public int y;
    public int z;
    public int currentPower;
    
    public TEPowerMessage()
    {
    	
    }

    public TEPowerMessage(int x, int y, int z, int currentPower)
    { 
        this.x = x;
        this.y = y;
        this.z = z;
        this.currentPower = currentPower;
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
    
    public int getCurrentPower()
    {
    	return currentPower;
    }

    @Override
    public void toBytes(ByteBuf buf)
    { 
        buf.writeInt(x);
        buf.writeInt(y);
        buf.writeInt(z);
        buf.writeInt(currentPower);
    }

    @Override
    public void fromBytes(ByteBuf buf)
    { 
        x = buf.readInt();
        y = buf.readInt();
        z = buf.readInt();
        currentPower = buf.readInt();
    }
}