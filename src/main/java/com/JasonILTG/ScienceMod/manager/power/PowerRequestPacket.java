package com.JasonILTG.ScienceMod.manager.power;

import net.minecraft.util.BlockPos;

/**
 * A packet of information for PowerMangers to request power.
 * 
 * @author JasonILTG and syy1125
 */
public class PowerRequestPacket
{
	/** The amount of power left to request */
	public float powerRequested;
	/** The time the packet was created (for packet differentiation) */
	public int timestamp;
	/** The BlockPos of the manager that created the packet (for packet differentiation) */
	public BlockPos from;
	/** Whether the packet has been removed */
	public boolean fulfilled;
	/** The manager that created the packet */
	public PowerManager manager;
	/** Whether the packet is currently interacting with a manager */
	public boolean interacting;
	
	/**
	 * Constructor.
	 * 
	 * @param power The power to request
	 * @param time The time the packet was created
	 * @param requestFrom The BlockPos of the manager that created the packet
	 * @param requester The manager that created the packet
	 */
	public PowerRequestPacket(float power, int time, BlockPos requestFrom, PowerManager requester)
	{
		powerRequested = power;
		timestamp = time;
		from = requestFrom;
		fulfilled = (power < 0 || power > requester.capacity);
		manager = requester;
		interacting = false;
	}
	
	/**
	 * Limits the power requested.
	 * 
	 * @param limit The amount to limit it to
	 */
	public void limitPower(float limit)
	{
		if (limit < powerRequested) powerRequested = limit;
	}
	
	/**
	 * Give power to the manager that sent the packet.
	 * 
	 * @param amount The amount of power to give
	 * @return The amount of power overflow
	 */
	public float givePower(float amount)
	{
		if (fulfilled) return amount;
		float overflow = manager.supplyPower(amount);
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
