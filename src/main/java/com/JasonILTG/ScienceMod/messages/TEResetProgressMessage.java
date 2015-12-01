package com.JasonILTG.ScienceMod.messages;

import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class TEResetProgressMessage implements IMessage
{
    public int x;
    public int y;
    public int z;
    
    public TEResetProgressMessage()
    {
    	
    }

    public TEResetProgressMessage(int x, int y, int z)
    { 
        this.x = x;
        this.y = y;
        this.z = z;
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

    @Override
    public void toBytes(ByteBuf buf)
    { 
        buf.writeInt(x);
        buf.writeInt(y);
        buf.writeInt(z);
    }

    @Override
    public void fromBytes(ByteBuf buf)
    { 
        x = buf.readInt();
        y = buf.readInt();
        z = buf.readInt();
    }
}