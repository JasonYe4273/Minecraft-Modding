package com.JasonILTG.ScienceMod.creativetabs;

import java.util.List;

import com.JasonILTG.ScienceMod.init.ScienceModBlocks;
import com.JasonILTG.ScienceMod.item.component.PowerBlock;
import com.JasonILTG.ScienceMod.item.component.electronics.Battery;
import com.JasonILTG.ScienceMod.item.component.electronics.BatteryLevel;
import com.JasonILTG.ScienceMod.item.component.hull.Hull;
import com.JasonILTG.ScienceMod.item.component.hull.MaterialHeat;
import com.JasonILTG.ScienceMod.itemblock.component.WireItemBlock;
import com.JasonILTG.ScienceMod.itemblock.generators.GeneratorItemBlock;
import com.JasonILTG.ScienceMod.itemblock.machines.MachineItemBlock;
import com.JasonILTG.ScienceMod.reference.NBTKeys;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Creative tab class for machines.
 * 
 * @author JasonILTG and syy1125
 */
public class CreativeTabMachines extends CreativeTabs
{
	public CreativeTabMachines(int id, String name)
	{
		super(id, name);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public Item getTabIconItem()
	{
		return Item.getItemFromBlock(ScienceModBlocks.electrolyzer);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void displayAllReleventItems(List<ItemStack> itemList)
	{
		super.displayAllReleventItems(itemList);
		
		NBTTagCompound batteryDefault = new NBTTagCompound();
		batteryDefault.setFloat(NBTKeys.Item.Component.CAPACITY, BatteryLevel.BASIC.capacity);
		NBTTagCompound wireInDefault = new NBTTagCompound();
		wireInDefault.setFloat(NBTKeys.Item.Component.MAX_IN, 100F);
		NBTTagCompound wireOutDefault = new NBTTagCompound();
		wireOutDefault.setFloat(NBTKeys.Item.Component.MAX_OUT, 100F);
		NBTTagCompound hullDefault = MaterialHeat.IRON.createHullTag();
		
		NBTTagCompound wireDefaultTag = new NBTTagCompound();
		wireDefaultTag.setTag(NBTKeys.Item.Component.WIRE_IN, wireInDefault);
		wireDefaultTag.setTag(NBTKeys.Item.Component.WIRE_OUT, wireOutDefault);
		
		NBTTagCompound powerDefaultTag = new NBTTagCompound();
		powerDefaultTag.setTag(NBTKeys.Item.Component.BATTERY, batteryDefault);
		powerDefaultTag.setTag(NBTKeys.Item.Component.WIRE_IN, wireInDefault);
		powerDefaultTag.setTag(NBTKeys.Item.Component.WIRE_OUT, wireOutDefault);
		
		NBTTagCompound machineDefaultTag = (NBTTagCompound) hullDefault.copy();
		machineDefaultTag.setTag(NBTKeys.Item.Component.BATTERY, batteryDefault);
		machineDefaultTag.setTag(NBTKeys.Item.Component.WIRE_IN, wireInDefault);
		
		NBTTagCompound generatorDefaultTag = (NBTTagCompound) hullDefault.copy();
		generatorDefaultTag.setTag(NBTKeys.Item.Component.BATTERY, batteryDefault);
		generatorDefaultTag.setTag(NBTKeys.Item.Component.WIRE_OUT, wireOutDefault);
		
		for (ItemStack stack : itemList)
		{
			Item item = stack.getItem();
			if (item instanceof MachineItemBlock)
			{
				stack.setTagCompound((NBTTagCompound) machineDefaultTag.copy());
			}
			else if (item instanceof GeneratorItemBlock)
			{
				stack.setTagCompound((NBTTagCompound) generatorDefaultTag.copy());
			}
			else if (item instanceof PowerBlock)
			{
				stack.setTagCompound((NBTTagCompound) powerDefaultTag.copy());
			}
			else if (item instanceof WireItemBlock)
			{
				stack.setTagCompound((NBTTagCompound) wireDefaultTag.copy());
			}
			else if (item instanceof Hull)
			{
				stack.setTagCompound(MaterialHeat.VALUES[stack.getMetadata()].createHullTag());
			}
			else if (item instanceof Battery)
			{
				stack.setTagCompound(BatteryLevel.VALUES[stack.getMetadata()].createBatteryTag());
			}
		}
	}
}