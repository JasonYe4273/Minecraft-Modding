/**
 * 
 */
package com.JasonILTG.ScienceMod.block.component.wire;

import com.JasonILTG.ScienceMod.reference.NBTKeys;

import net.minecraft.nbt.NBTTagCompound;

/**
 * An enum to keep track of the different possible wire materials and their properties.
 * 
 * @author JasonILTG and syy1125
 */
public enum MaterialWire
{
	COPPER(20F, 20F),
	IRON(100F, 100F),
	GOLD(100F, 100F)
	;
	
	/** The maximum input rate of the wire */
	public final float maxInRate;
	/** The maximum output rate of the wire */
	public final float maxOutRate;
	
	public static final MaterialWire[] VALUES = values();
	
	
	/**
	 * Constructor.
	 * 
	 * @param maxIn The maximum input rate
	 * @param maxOut The maximum output rate
	 */
	MaterialWire(float maxIn, float maxOut)
	{
		maxInRate = maxIn;
		maxOutRate = maxOut;
	}
	
	/**
	 * Creates a NBT tag for the wire.
	 * 
	 * @return An NBT tag with the appropriate maximum rates
	 */
	public NBTTagCompound createWireTag()
	{
		NBTTagCompound wireInTag = new NBTTagCompound();
		NBTTagCompound wireOutTag = new NBTTagCompound();
		wireInTag.setFloat(NBTKeys.Item.Component.MAX_IN, maxInRate);
		wireOutTag.setFloat(NBTKeys.Item.Component.MAX_OUT, maxOutRate);
		NBTTagCompound tag = new NBTTagCompound();
		tag.setTag(NBTKeys.Item.Component.WIRE_IN, wireInTag);
		tag.setTag(NBTKeys.Item.Component.WIRE_OUT, wireOutTag);
		return tag;
	}
}
