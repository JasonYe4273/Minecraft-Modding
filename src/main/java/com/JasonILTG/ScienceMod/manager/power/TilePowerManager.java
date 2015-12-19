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
	protected PowerManager[] adjManagers;
	
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
		List<PowerManager> adjacentManagers = new ArrayList<PowerManager>();
		
		// Load all adjacent blocks
		BlockPos[] adjacentPositions = BlockHelper.getAdjacentBlockPositions(pos);
		// For each adjacent block
		for (BlockPos adjPos : adjacentPositions) {
			TileEntity te = worldIn.getTileEntity(adjPos);
			if (te != null && te instanceof ITileEntityPowered) {
				// This adjacent machine can exchange heat
				adjacentManagers.add(((ITileEntityPowered) te).getPowerManager());
			}
		}
		
		adjManagers = adjacentManagers.toArray(new PowerManager[adjacentManagers.size()]);
	}
	
	@Override
	public void onTickStart()
	{
		deleteOldPackets();
		
		sendOwnPacket();
	}
	
	@Override
	public void onTickEnd()
	{
		processPackets();
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
	public void writeToNBT(NBTTagCompound tag)
	{
		super.writeToNBT(tag);
	}
	
	@Override
	protected NBTTagCompound makeDataTag()
	{
		NBTTagCompound dataTag = super.makeDataTag();
		dataTag.setInteger(NBTKeys.Manager.Power.TYPE, type);
		return dataTag;
	}
	
}
