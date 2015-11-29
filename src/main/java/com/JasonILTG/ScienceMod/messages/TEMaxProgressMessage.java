package com.JasonILTG.ScienceMod.messages;

import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class TEMaxProgressMessage implements IMessage
{
    public int x;
    public int y;
    public int z;
    public int maxProgress;
    
    public TEMaxProgressMessage()
    {
    	
    }

    public TEMaxProgressMessage(int x, int y, int z, int maxProgress)
    { 
        this.x = x;
        this.y = y;
        this.z = z;
        this.maxProgress = maxProgress;
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
    
    public int getMaxProgress()
    {
    	return maxProgress;
    }

    @Override
    public void toBytes(ByteBuf buf)
    { 
        buf.writeInt(x);
        buf.writeInt(y);
        buf.writeInt(z);
        buf.writeInt(maxProgress);
    }

    @Override
    public void fromBytes(ByteBuf buf)
    { 
        x = buf.readInt();
        y = buf.readInt();
        z = buf.readInt();
        maxProgress = buf.readInt();
    }
}