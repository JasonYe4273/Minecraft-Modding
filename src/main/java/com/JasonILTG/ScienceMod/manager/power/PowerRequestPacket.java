package com.JasonILTG.ScienceMod.manager.power;

import net.minecraft.util.BlockPos;

public class PowerRequestPacket
{
	public int powerRequested;
	public int timestamp;
	public BlockPos from;
	public int type;
	public boolean fulfilled;
	public PowerManager manager;
	public boolean interacting;
	
	public PowerRequestPacket(int power, int time, BlockPos requestFrom, int requestType, PowerManager requester)
	{
		powerRequested = power;
		timestamp = time;
		from = requestFrom;
		type = requestType;
		fulfilled = (power < 0 || power > requester.capacity);
		manager = requester;
		interacting = false;
	}
	
	public void limitPower(int limit)
	{
		if (limit < powerRequested) powerRequested = limit;
	}
	
	public int givePower(int amount)
	{
		if (fulfilled) return amount;
		int overflow = manager.supplyPower(amount);
		powerRequested -= amount - overflow;
		fulfilled = powerRequested <= 0;
		return overflow;
	}
	
	@Override
	public boolean equals(Object other)
	{
		PowerRequestPacket otherPacket = (PowerRequestPacket) other;
		return (otherPacket != null && timestamp == otherPacket.timestamp && from.equals(otherPacket.from));
	}
}
