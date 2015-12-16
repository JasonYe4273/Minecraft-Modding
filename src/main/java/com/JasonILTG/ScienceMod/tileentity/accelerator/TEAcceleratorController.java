package com.JasonILTG.ScienceMod.tileentity.accelerator;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.gui.IUpdatePlayerListBox;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

import com.JasonILTG.ScienceMod.manager.power.PowerManager;
import com.JasonILTG.ScienceMod.tileentity.general.ITileEntityPowered;

public class TEAcceleratorController extends TEAccelerator implements ITileEntityPowered, IUpdatePlayerListBox
{
	private static final String NAME = NAME_PREFIX + "Controller";
	private static final int DEFAULT_POWER_DRAIN = 100;
	private static final int MAX_CHARGE_TIME = 200;
	
	private PowerManager power;
	private int powerPerTick;
	
	private int maxCharge;
	private int currentCharge;
	private boolean isActive;
	
	private boolean isFormed;
	
	public TEAcceleratorController(World worldIn, BlockPos position)
	{
		super(worldIn, position);
		
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
	public void update()
	{
		// Do action only when formed and active
		if (!isFormed || !isActive) return;
		
		if (hasPower())
		{	
			
		}
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
