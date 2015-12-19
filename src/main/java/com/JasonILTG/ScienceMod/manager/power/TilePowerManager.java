package com.JasonILTG.ScienceMod.manager.power;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

import com.JasonILTG.ScienceMod.manager.TileManager;
import com.JasonILTG.ScienceMod.reference.NBTKeys;
import com.JasonILTG.ScienceMod.tileentity.general.ITileEntityPowered;
import com.JasonILTG.ScienceMod.util.BlockHelper;

public class TilePowerManager extends PowerManager implements TileManager
{
	public static final int GENERATOR = 0;
	public static final int WIRE = 1;
	public static final int MACHINE = 2;
	public static final int STORAGE = 3;
	
	/** The world the manager is in */
	protected World worldObj;
	/** The BlockPos of the manager */
	protected BlockPos pos;
	
	/** The type of PowerManager (0: generator, 1: wiring, 2: machine, 3: storage */
	protected int type;
	
	/** The array of adjacent managers */
	protected TilePowerManager[] adjManagers;
	
	/** The active packets */
	protected ArrayList<PowerRequestPacket> packets;
	/** The time each packet was received at */
	protected ArrayList<Integer> receivedTimestamp;
	/** The distance each packet has traveled */
	protected ArrayList<Integer> packetDistance;
	
	/** The archived packets */
	protected ArrayList<PowerRequestPacket> archive;
	/** The time each packet was archived at */
	protected ArrayList<Integer> archiveTimestamp;
	
	public TilePowerManager(World worldIn, BlockPos position, float powerCapacity, float inputRate, float outputRate, int teType)
	{
		super(powerCapacity, inputRate, outputRate);
		
		worldObj = worldIn;
		pos = position;
		
		type = teType;
		
		packets = new ArrayList<PowerRequestPacket>();
		receivedTimestamp = new ArrayList<Integer>();
		packetDistance = new ArrayList<Integer>();
		archive = new ArrayList<PowerRequestPacket>();
		archiveTimestamp = new ArrayList<Integer>();
	}
	
	@Override
	public BlockPos getPos()
	{
		return pos;
	}
	
	/**
	 * @return The type of the machine
	 */
	public int getType()
	{
		return type;
	}
	
	/**
	 * Updates the world info of the power manager.
	 * 
	 * @param worldIn The world the manager is in
	 * @param pos The BlockPos of the manager
	 */
	public void updateWorldInfo(World worldIn, BlockPos pos)
	{
		this.worldObj = worldIn;
		this.pos = pos;
		List<TilePowerManager> adjacentManagers = new ArrayList<TilePowerManager>();
		
		// Load all adjacent blocks
		BlockPos[] adjacentPositions = BlockHelper.getAdjacentBlockPositions(pos);
		
		// For each adjacent block
		for (BlockPos adjPos : adjacentPositions) {
			TileEntity te = worldIn.getTileEntity(adjPos);
			if (te != null && te instanceof ITileEntityPowered)
			{
				// This adjacent machine can exchange power
				PowerManager adjMng = ((ITileEntityPowered) te).getPowerManager();
				if (adjMng instanceof TilePowerManager) adjacentManagers.add((TilePowerManager) adjMng);
			}
		}
		
		adjManagers = adjacentManagers.toArray(new TilePowerManager[adjacentManagers.size()]);
	}
	
	@Override
	public void onTickStart()
	{
		super.onTickStart();
		
		deleteOldPackets();
		
		sendOwnPacket();
	}
	
	@Override
	public void onTickEnd()
	{
		super.onTickEnd();
		
		processPackets();
	}
	
	/**
	 * Sends a packet from the manager (if it is a machine or storage).
	 */
	private void sendOwnPacket()
	{
		if ((type == MACHINE || type == STORAGE) && capacity > currentPower && adjManagers != null)
		{
			float powerToRequest = Math.min(maxInRate, capacity - currentPower);
			PowerRequestPacket packet = new PowerRequestPacket(powerToRequest, (int) (System.currentTimeMillis() % 1000000), pos, this);
			for (TilePowerManager adj : adjManagers)
			{
				if (adj != null && adj.type != MACHINE)
				{
					adj.receivePacket(packet, 0);
				}
			}
		}
	}
	
	/**
	 * Sends a packet to all adjacent managers.
	 * 
	 * @param packet The packet to send
	 * @param currDistance The distance the packet has traveled so far
	 */
	private void sendPacket(PowerRequestPacket packet, int currDistance)
	{
		if (type == GENERATOR || adjManagers == null) return;
		
		for (TilePowerManager adj : adjManagers)
		{
			if (adj != null && adj.type != MACHINE)
			{
				adj.receivePacket(packet, currDistance);
			}
		}
	}
	
	/**
	 * Receives a packet, and stores it and sends it if it is new.
	 * 
	 * @param packet The packet to receive
	 * @param distance The distance the packet has traveled so far
	 */
	public void receivePacket(PowerRequestPacket packet, int distance)
	{
		int time = (int) (System.currentTimeMillis() % 1000000);
		if (!packets.contains(packet) && !archive.contains(packet))
		{
			packet.limitPower(maxOutRate);
			packets.add(packet);
			receivedTimestamp.add(time);
			packetDistance.add(distance + 1);
			sendPacket(packet, distance + 1);
		}
	}
	
	/**
	 * Deletes all old archived packets and archives all old active packets.
	 */
	private void deleteOldPackets()
	{
		int time = (int) (System.currentTimeMillis() % 1000000);
		for (int i = 0; i < archive.size(); i ++)
		{
			int timeDiff = time - archiveTimestamp.get(i);
			if (timeDiff > 1000 || timeDiff < 0)
			{
				archive.remove(i);
				archiveTimestamp.remove(i);
				i --;
			}
			else
				break;
		}
		for (int i = packets.size() - 1; i >= 0; i --)
		{
			int timeDiff = time - receivedTimestamp.get(i);
			if (packets.get(i).fulfilled || ((type == MACHINE || type == STORAGE) && packets.get(i).from.equals(pos)))
			{
				packets.remove(i);
				receivedTimestamp.remove(i);
				packetDistance.remove(i);
			}
			else if (timeDiff > 1000 || timeDiff < 0)
			{
				archive.add(packets.remove(i));
				receivedTimestamp.remove(i);
				packetDistance.remove(i);
				archiveTimestamp.add(time);
			}
		}
	}
	
	/**
	 * Processes active packets, archiving all packets that aren't fulfilled.
	 */
	private void processPackets()
	{
		if (type == MACHINE || type == WIRE) return;
		
		float totalPowerRequested = 0;
		ArrayList<Integer> packetIndices = new ArrayList<Integer>();
		for (int i = 0; i < packets.size(); i ++)
		{
			if (!packets.get(i).fulfilled && (type != STORAGE || !packets.get(i).from.equals(pos)))
			{
				TileEntity te = this.worldObj.getTileEntity(packets.get(i).from);
				if (te != null && te instanceof ITileEntityPowered)
				{
					packets.get(i).interacting = true;
					totalPowerRequested += packets.get(i).powerRequested;
					packetIndices.add(i);
				}
				else
					packets.get(i).fulfilled = true;
			}
		}
		
		totalPowerRequested = Math.min(totalPowerRequested, currentPower);
		float currPowerRequested = totalPowerRequested;
		float prevPowerRequested = 0;
		while (currPowerRequested != 0 && prevPowerRequested != currPowerRequested && packetIndices.size() != 0)
		{
			float overflow = 0;
			prevPowerRequested = currPowerRequested;
			for (int i = packetIndices.size() - 1; i >= 0; i --)
			{
				int index = packetIndices.get(i);
				float toGive = currPowerRequested / (i + 1);
				overflow += packets.get(index).givePower(toGive);
				currPowerRequested -= toGive;
				if (packets.get(index).fulfilled) packetIndices.remove(i);
			}
			currPowerRequested += overflow;
		}
		currentPower -= totalPowerRequested - currPowerRequested;
		
		for (int i = packetIndices.size() - 1; i >= 0; i --)
		{
			PowerRequestPacket p = packets.remove((int) packetIndices.get(i));
			receivedTimestamp.remove(i);
			packetDistance.remove(i);
			archive.add(p);
			archiveTimestamp.add((int) (System.currentTimeMillis() % 1000000));
			p.interacting = false;
		}
	}
	
	@Override
	protected void readFromDataTag(NBTTagCompound dataTag)
	{
		super.readFromDataTag(dataTag);
		
		type = dataTag.getInteger(NBTKeys.Manager.Power.TYPE);
		
		packets = new ArrayList<PowerRequestPacket>();
		archive = new ArrayList<PowerRequestPacket>();
		archiveTimestamp = new ArrayList<Integer>();
	}
	
	@Override
	protected NBTTagCompound makeDataTag()
	{
		NBTTagCompound dataTag = super.makeDataTag();
		dataTag.setInteger(NBTKeys.Manager.Power.TYPE, type);
		return dataTag;
	}
	
}
