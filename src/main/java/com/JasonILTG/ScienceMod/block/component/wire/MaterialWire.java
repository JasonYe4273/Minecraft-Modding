/**
 * 
 */
package com.JasonILTG.ScienceMod.block.component.wire;

import com.JasonILTG.ScienceMod.reference.NBTKeys;

import net.minecraft.nbt.NBTTagCompound;

/**
 * 
 * @author JasonILTG and syy1125
 */
public enum MaterialWire
{
	IRON(100F, 100F);
	
	public final float maxInRate;
	public final float maxOutRate;
	
	public static final MaterialWire[] VALUES = values();
	
	MaterialWire(float maxIn, float maxOut)
	{
		maxInRate = maxIn;
		maxOutRate = maxOut;
	}
	
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
