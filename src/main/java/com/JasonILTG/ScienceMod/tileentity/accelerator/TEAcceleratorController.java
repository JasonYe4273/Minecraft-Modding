package com.JasonILTG.ScienceMod.tileentity.accelerator;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.gui.IUpdatePlayerListBox;

import com.JasonILTG.ScienceMod.item.elements.ItemElement;
import com.JasonILTG.ScienceMod.manager.power.PowerManager;
import com.JasonILTG.ScienceMod.reference.Reference;
import com.JasonILTG.ScienceMod.tileentity.general.ITileEntityPowered;
import com.JasonILTG.ScienceMod.tileentity.machines.TEMachine;

public class TEAcceleratorController extends TEAccelerator implements ITileEntityPowered, IInventory, IUpdatePlayerListBox
{
	private static final String NAME = NAME_PREFIX + "Accelerator Controller";
	private static final int DEFAULT_POWER_DRAIN = 100;
	private static final int MAX_CHARGE_TIME = 200;
	private static final int DEFAULT_POWER_IN = DEFAULT_POWER_DRAIN * 5;
	private static final int DEFAULT_POWER_OUT = 0;
	
	private PowerManager power;
	private int powerPerTick;
	
	private int maxCharge;
	private int currentCharge;
	private boolean isActive;
	private boolean isFormed;
	
	private ItemStack[] inputInv;
	private static final int INPUT_INDEX = 0;
	
	private TEAcceleratorOutput teOutput;
	
	public TEAcceleratorController()
	{
		super();
		
		maxCharge = MAX_CHARGE_TIME;
		currentCharge = 0;
		isActive = false;
		isFormed = false;
		
		power = new PowerManager(worldObj, pos, TEMachine.DEFAULT_POWER_CAPACITY, DEFAULT_POWER_IN, DEFAULT_POWER_OUT, PowerManager.MACHINE);
		powerPerTick = DEFAULT_POWER_DRAIN;
		inputInv = new ItemStack[1];
	}
	
	public void form(TEAcceleratorOutput output)
	{
		isFormed = true;
		teOutput = output;
	}
	
	public void dismantle()
	{
		isFormed = false;
		teOutput = null;
	}
	
	public void activate()
	{
		isActive = true;
	}
	
	public void deactivate()
	{
		isActive = false;
	}
	
	@Override
	public void update()
	{
		// Do action only when formed and has power.
		if (!isFormed || !hasPower()) return;
		
		if (isActive)
		{
			power.consumePower(DEFAULT_POWER_DRAIN);
			currentCharge ++;
			
			if (currentCharge >= maxCharge)
			{
				currentCharge = 0;
				deactivate();
				// TODO Send launch message to the output block.
			}
		}
		else {
			
		}
	}
	
	public void tryActivate()
	{
		ItemStack input = getStackInSlot(INPUT_INDEX);
		if (input.getItem() instanceof ItemElement)
		{
			// Consume the item, activate.
			this.decrStackSize(INPUT_INDEX, 1);
			activate();
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
	
	@Override
	public String getName()
	{
		return NAME;
	}
	
	@Override
	public int getSizeInventory()
	{
		return 1;
	}
	
	@Override
	public ItemStack getStackInSlot(int index)
	{
		if (index < 0 || index > getSizeInventory() - 1) return null;
		return inputInv[index];
	}
	
	@Override
	public ItemStack decrStackSize(int index, int count)
	{
		ItemStack stack = getStackInSlot(index);
		
		if (stack != null)
		{
			if (count >= stack.stackSize) {
				// The action will deplete the stack.
				setInventorySlotContents(index, null);
			}
			else {
				// The action should not deplete the stack
				stack = stack.splitStack(count);
			}
		}
		
		return stack;
	}
	
	@Override
	public ItemStack getStackInSlotOnClosing(int index)
	{
		// TODO What exactly does this method do?
		return null;
	}
	
	@Override
	public void setInventorySlotContents(int index, ItemStack stack)
	{
		if (index < 0 || index > getSizeInventory() - 1) return;
		inputInv[index] = stack;
	}
	
	@Override
	public int getInventoryStackLimit()
	{
		return Reference.DEFAULT_STACK_LIMIT;
	}
	
	@Override
	public boolean isUseableByPlayer(EntityPlayer player)
	{
		return this.worldObj.getTileEntity(this.getPos()) == this && player.getDistanceSq(this.pos.add(0.5, 0.5, 0.5)) <= 64;
	}
	
	@Override
	public void openInventory(EntityPlayer player)
	{
		// Empty method
	}
	
	@Override
	public void closeInventory(EntityPlayer player)
	{
		// Empty method
	}
	
	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack)
	{
		// Cannot insert item.
		return false;
	}
	
	@Override
	public int getField(int id)
	{
		return 0;
	}
	
	@Override
	public void setField(int id, int value)
	{
		// Empty method
	}
	
	@Override
	public int getFieldCount()
	{
		return 0;
	}
	
	@Override
	public void clear()
	{
		for (int i = 0; i < this.getSizeInventory(); i ++)
			this.setInventorySlotContents(i, null);
	}
}
