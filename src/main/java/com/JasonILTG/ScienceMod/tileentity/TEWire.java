package com.JasonILTG.ScienceMod.tileentity;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.gui.IUpdatePlayerListBox;

import com.JasonILTG.ScienceMod.manager.heat.HeatManager;
import com.JasonILTG.ScienceMod.manager.heat.TileHeatManager;
import com.JasonILTG.ScienceMod.manager.power.PowerManager;
import com.JasonILTG.ScienceMod.manager.power.TilePowerManager;
import com.JasonILTG.ScienceMod.tileentity.general.ITileEntityHeated;
import com.JasonILTG.ScienceMod.tileentity.general.ITileEntityPowered;
import com.JasonILTG.ScienceMod.tileentity.general.TEScience;

/**
 * Tile entity class for wires.
 * 
 * @author JasonILTG and syy1125
 */
public class TEWire extends TEScience implements IUpdatePlayerListBox, ITileEntityPowered, ITileEntityHeated
{
	/** The HeatManager of the wire */
	protected TileHeatManager wireHeat;
	/** The PowerManager of the wire */
	protected TilePowerManager wirePower;
	protected boolean managerWorldUpdated;
	
	public static final int DEFAULT_POWER_CAPACITY = 400000;
	public static final int DEFAULT_MAX_RATE = 100;
	
	/**
	 * Constructor.
	 * 
	 * @param name The name of the wire
	 * @param inventorySizes The sizes of the inventories
	 */
	public TEWire()
	{
		wireHeat = new TileHeatManager(this.worldObj, this.pos);
		wirePower = new TilePowerManager(this.worldObj, this.pos, DEFAULT_POWER_CAPACITY, DEFAULT_MAX_RATE, DEFAULT_MAX_RATE, TilePowerManager.WIRE);
		managerWorldUpdated = false;
	}
	
	@Override
	public void update()
	{
		if (this.worldObj.isRemote) return;
		
		if (!managerWorldUpdated)
		{
			updateManagers();
			managerWorldUpdated = true;
		}
	}
	
	/**
	 * Updates the information for the managers. Called when there is a block update.
	 */
	public void updateManagers()
	{
		wireHeat.updateWorldInfo(worldObj, pos);
		wirePower.updateWorldInfo(worldObj, pos);
	}
	
	@Override
	public HeatManager getHeatManager()
	{
		return wireHeat;
	}
	
	@Override
	public boolean hasHeat()
	{
		return true;
	}
	
	@Override
	public float getCurrentTemp()
	{
		return wireHeat.getCurrentTemp();
	}
	
	@Override
	public void setCurrentTemp(float temp)
	{
		// Only allowed on the client side
		if (!this.worldObj.isRemote) return;
		wireHeat.setCurrentTemp(temp);
	}
	
	@Override
	public PowerManager getPowerManager()
	{
		return wirePower;
	}
	
	@Override
	public boolean hasPower()
	{
		return true;
	}
	
	@Override
	public float getPowerCapacity()
	{
		return wirePower.getCapacity();
	}
	
	@Override
	public float getCurrentPower()
	{
		return wirePower.getCurrentPower();
	}
	
	@Override
	public void setCurrentPower(float amount)
	{
		// Only allowed on the client side
		if (!this.worldObj.isRemote) return;
		wirePower.setCurrentPower(amount);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound tag)
	{
		super.readFromNBT(tag);
		
		// Load heat and power managers
		wireHeat.readFromNBT(tag);
		wirePower.readFromNBT(tag);
	}
	
	@Override
	public void writeToNBT(NBTTagCompound tag)
	{
		super.writeToNBT(tag);
		
		// Save heat and power managers
		wireHeat.writeToNBT(tag);
		wirePower.writeToNBT(tag);
	}
}
