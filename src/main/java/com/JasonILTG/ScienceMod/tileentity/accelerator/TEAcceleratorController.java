package com.JasonILTG.ScienceMod.tileentity.accelerator;

import net.minecraft.nbt.NBTTagCompound;

import com.JasonILTG.ScienceMod.manager.power.PowerManager;
import com.JasonILTG.ScienceMod.tileentity.general.ITileEntityPowered;

public class TEAcceleratorController extends TEAccelerator implements ITileEntityPowered
{
	private static final String NAME = NAME_PREFIX + "Controller";
	private static final int DEFAULT_POWER_DRAIN = 100;
	
	private PowerManager power;
	private int powerPerTick;
	private boolean isFormed;
	
	public TEAcceleratorController()
	{
		isFormed = false;
		powerPerTick = DEFAULT_POWER_DRAIN;
	}
	
	public void form()
	{
		isFormed = true;
	}
	
	public void dismantle()
	{
		isFormed = false;
	}
	
	@Override
	public PowerManager getPowerManager()
	{
		return power;
	}
	
	@Override
	public boolean hasPower()
	{
		return power.getCurrentPower() >= powerPerTick;
	}
	
	@Override
	public void powerAction()
	{
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public int getPowerCapacity()
	{
		return power.getCapacity();
	}
	
	@Override
	public int getCurrentPower()
	{
		return power.getCurrentPower();
	}
	
	@Override
	public void setCurrentPower(int amount)
	{
		power.setCurrentPower(amount);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound)
	{
		super.readFromNBT(compound);
		power.readFromNBT(compound);
	}
	
	@Override
	public void writeToNBT(NBTTagCompound compound)
	{
		super.writeToNBT(compound);
		power.writeToNBT(compound);
	}
}
