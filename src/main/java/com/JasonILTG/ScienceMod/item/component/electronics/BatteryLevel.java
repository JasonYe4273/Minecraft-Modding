package com.JasonILTG.ScienceMod.item.component.electronics;

import com.JasonILTG.ScienceMod.reference.NBTKeys;

import net.minecraft.nbt.NBTTagCompound;

/**
 * Enum for battery levels.
 * 
 * @author JasonILTG and syy1125
 */
public enum BatteryLevel
{
	BASIC(2000F, "basic"),
	DOUBLE(4000F, "double"),
	PACK(8000F, "pack"),
	DOUBLE_PACK(16000F, "double_pack"),
	BUNDLE(40000F, "bundle")
	;
	
	/** The power capacity of the battery */
	public final float capacity;
	/** The displayed name */
	public final String name;
	
	public static final BatteryLevel[] VALUES = values();
	
	/**
	 * Constructor.
	 * 
	 * @param capacity The capacity
	 * @param name The name
	 */
	BatteryLevel(float capacity, String name)
	{
		this.capacity = capacity;
		this.name = name;
	}
	
	/**
	 * @return An NBT tag for the battery
	 */
	public NBTTagCompound createBatteryTag()
	{
		NBTTagCompound batteryTag = new NBTTagCompound();
		batteryTag.setFloat(NBTKeys.Item.Component.CAPACITY, capacity);
		NBTTagCompound tag = new NBTTagCompound();
		tag.setTag(NBTKeys.Item.Component.BATTERY, batteryTag);
		return tag;
	}
}
