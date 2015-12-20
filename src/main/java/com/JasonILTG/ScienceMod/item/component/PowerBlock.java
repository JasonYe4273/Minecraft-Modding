package com.JasonILTG.ScienceMod.item.component;

import java.util.List;

import com.JasonILTG.ScienceMod.item.general.ItemScience;
import com.JasonILTG.ScienceMod.reference.NBTKeys;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class PowerBlock extends ItemScience
{
	public PowerBlock()
	{
		setUnlocalizedName("power_block");
	}
	
	@Override
	public boolean getHasSubtypes()
	{
		return false;
	}
	
	@Override
	public int getNumSubtypes()
	{
		return 1;
	}
	
	@Override
	public void addInformation(ItemStack stack, EntityPlayer playerIn, List tooltip, boolean advanced)
	{
		if (stack.getTagCompound() != null)
		{
			// Null check
			
			NBTTagCompound powerBlockTag = (NBTTagCompound) stack.getTagCompound().getTag(NBTKeys.Item.Component.HULL);
			if (powerBlockTag != null)
			{
				float capacity = powerBlockTag.getFloat(NBTKeys.Item.Component.CAPACITY);
				float maxIn = powerBlockTag.getFloat(NBTKeys.Item.Component.MAX_IN);
				float maxOut = powerBlockTag.getFloat(NBTKeys.Item.Component.MAX_OUT);
				
				tooltip.add(String.format("Power Capacity: %s C", capacity));
				tooltip.add(String.format("Maximum Input Rate: %.1f C/t", maxIn));
				tooltip.add(String.format("Maximum Output Rate: %.1f C/t", maxOut));
			}
		}
	}
}
