package com.JasonILTG.ScienceMod.item.component;

import java.util.List;

import com.JasonILTG.ScienceMod.creativetabs.ScienceCreativeTabs;
import com.JasonILTG.ScienceMod.item.general.ItemScience;
import com.JasonILTG.ScienceMod.reference.NBTKeys;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public abstract class ScienceComponent extends ItemScience
{
	public ScienceComponent()
	{
		setCreativeTab(ScienceCreativeTabs.tabMachines);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void addInformation(ItemStack stack, EntityPlayer playerIn, List tooltip, boolean advanced)
	{
		NBTTagCompound tag = stack.getTagCompound();
		if (tag != null)
		{
			// Null check
			
			NBTTagCompound hullTag = (NBTTagCompound) tag.getTag(NBTKeys.Item.Component.HULL);
			if (hullTag != null)
			{
				tooltip.add("Heat information:");
				
				if (hullTag.getBoolean(NBTKeys.Item.Component.OVERHEAT))
				{
					tooltip.add(String.format("* Overheats at %.1f C", hullTag.getFloat(NBTKeys.Item.Component.MAX_TEMP)));
				}
				tooltip.add(String.format("* Specific Heat: %.1f J/C", hullTag.getFloat(NBTKeys.Item.Component.SPECIFIC_HEAT)));
				tooltip.add(String.format("* Heat Loss: %.1f J/t", hullTag.getFloat(NBTKeys.Item.Component.HEAT_LOSS)));
				tooltip.add(String.format("* Heat Transfer: %.1f J/C", hullTag.getFloat(NBTKeys.Item.Component.HEAT_TRANSFER)));
			}
			
			NBTTagCompound powerCapacityTag = (NBTTagCompound) tag.getTag(NBTKeys.Item.Component.BATTERY);
			NBTTagCompound powerInTag = (NBTTagCompound) tag.getTag(NBTKeys.Item.Component.WIRE_IN);
			NBTTagCompound powerOutTag = (NBTTagCompound) tag.getTag(NBTKeys.Item.Component.WIRE_OUT);
			if (powerCapacityTag != null || powerInTag != null || powerOutTag != null)
			{
				tooltip.add("Power information: ");
			}
			
			if (powerCapacityTag != null)
			{
				tooltip.add(String.format("* Capacity: %.0f C", powerCapacityTag.getFloat(NBTKeys.Item.Component.CAPACITY)));
			}
			
			if (powerInTag != null)
			{
				tooltip.add(String.format("* Max Input: %.0f C", powerInTag.getFloat(NBTKeys.Item.Component.MAX_IN)));
			}
			
			if (powerOutTag != null)
			{
				tooltip.add(String.format("* Max Output: %.0f C", powerOutTag.getFloat(NBTKeys.Item.Component.MAX_OUT)));
			}
		}
	}
}
