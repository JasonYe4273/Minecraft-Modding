package com.JasonILTG.ScienceMod.messages;

import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class TEProgressMessage implements IMessage
{
    public int x;
    public int y;
    public int z;
    public int progress;
    
    public TEProgressMessage()
    {
    	
    }

    public TEProgressMessage(int x, int y, int z, int progress)
    { 
        this.x = x;
        this.y = y;
        this.z = z;
        this.progress = progress;
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
    
    public int getProgress()
    {
    	return progress;
    }

    @Override
    public void toBytes(ByteBuf buf)
    { 
        buf.writeInt(x);
        buf.writeInt(y);
        buf.writeInt(z);
        buf.writeInt(progress);
    }

    @Override
    public void fromBytes(ByteBuf buf)
    { 
        x = buf.readInt();
        y = buf.readInt();
        z = buf.readInt();
        progress = buf.readInt();
    }
}