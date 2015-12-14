package com.JasonILTG.ScienceMod.manager.power;

import net.minecraft.util.BlockPos;

public class PowerRequestPacket
{
	public int powerRequested;
	public int timestamp;
	public BlockPos from;
	public int type;
	public boolean fulfilled;
	
	public PowerRequestPacket(int power, int time, BlockPos requestFrom, int requestType)
	{
		powerRequested = power;
		timestamp = time;
		from = requestFrom;
		type = requestType;
		fulfilled = false;
	}
	
	public void limitPower(int limit)
	{
		if (limit < powerRequested) powerRequested = limit;
	}
	
	@Override
	public boolean equals(Object other)
	{
		PowerRequestPacket otherPacket = (PowerRequestPacket) other;
		return (otherPacket != null && timestamp == otherPacket.timestamp && from.equals(otherPacket.from));
	}
}
