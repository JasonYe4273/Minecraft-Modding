package com.JasonILTG.ScienceMod.messages;

import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class TEDoProgressMessage implements IMessage
{
    public int x;
    public int y;
    public int z;
    public boolean doProgress;
    
    public TEDoProgressMessage()
    {
    	
    }

    public TEDoProgressMessage(int x, int y, int z, boolean doProgress)
    { 
        this.x = x;
        this.y = y;
        this.z = z;
        this.doProgress = doProgress;
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
    
    public boolean getDoProgress()
    {
    	return doProgress;
    }

    @Override
    public void toBytes(ByteBuf buf)
    { 
        buf.writeInt(x);
        buf.writeInt(y);
        buf.writeInt(z);
        buf.writeBoolean(doProgress);
    }

    @Override
    public void fromBytes(ByteBuf buf)
    { 
        x = buf.readInt();
        y = buf.readInt();
        z = buf.readInt();
        doProgress = buf.readBoolean();
    }
}