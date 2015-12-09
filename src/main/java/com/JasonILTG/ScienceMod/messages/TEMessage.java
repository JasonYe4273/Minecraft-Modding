package com.JasonILTG.ScienceMod.messages;

import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

/**
 * Wrapper class for all messages involving tile entities in ScienceMod.
 * 
 * @author JasonILTG and syy1125
 */
public class TEMessage implements IMessage
{
	public int x;
	public int y;
	public int z;
	
	public TEMessage() {};
	
	/**
	 * Constructor.
	 * 
	 * @param x The BlockPos x-value of the tile entity
	 * @param y The BlockPos y-value of the tile entity
	 * @param z The BlockPos z-value of the tile entity
	 */
	public TEMessage(int x, int y, int z)
	{
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	/**
	 * @return The BlockPos x-value of the tile entity
	 */
	public int getTEX()
	{
		return x;
	}
	
	/**
	 * @return The BlockPos y-value of the tile entity
	 */
	public int getTEY()
	{
		return y;
	}
	
	/**
	 * @return The BlockPos z-value of the tile entity
	 */
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
