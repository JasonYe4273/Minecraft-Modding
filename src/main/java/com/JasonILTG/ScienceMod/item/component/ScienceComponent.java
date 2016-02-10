package com.JasonILTG.ScienceMod.item.component;

import java.util.List;

import com.JasonILTG.ScienceMod.creativetabs.ScienceCreativeTabs;
import com.JasonILTG.ScienceMod.item.general.ItemScience;
import com.JasonILTG.ScienceMod.reference.NBTKeys;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public abstract class ScienceComponent extends ItemScience
{
	public ScienceComponent()
	{
		setCreativeTab(ScienceCreativeTabs.tabMachines);
	}
	
	@Override
	public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced)
	{
		super.addInformation(stack, playerIn, tooltip, advanced);
		
		if (!GuiScreen.isShiftKeyDown())
		{
			tooltip.add("Hold Shift for more information");
			return;
		}
		
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
				else
				{
					tooltip.add("* Does not overheat");
				}
				tooltip.add(String.format("* Specific Heat: %.1f J/K", hullTag.getFloat(NBTKeys.Item.Component.SPECIFIC_HEAT)));
				tooltip.add(String.format("* Heat Loss: %.2e J/Kt", hullTag.getFloat(NBTKeys.Item.Component.HEAT_LOSS)));
				tooltip.add(String.format("* Heat Transfer: %.2e J/Kt", hullTag.getFloat(NBTKeys.Item.Component.HEAT_TRANSFER)));
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
				tooltip.add(String.format("* Max Input: %.0f C/t", powerInTag.getFloat(NBTKeys.Item.Component.MAX_IN)));
			}
			
			if (powerOutTag != null)
			{
				tooltip.add(String.format("* Max Output: %.0f C/t", powerOutTag.getFloat(NBTKeys.Item.Component.MAX_OUT)));
			}
		}
	}
}
