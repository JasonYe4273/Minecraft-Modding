package com.JasonILTG.ScienceMod.reference.chemistry.formula;

import net.minecraft.nbt.NBTTagCompound;

import com.JasonILTG.ScienceMod.IScienceNBT;

public abstract class SubstanceBase
		implements IHasFormula, IScienceNBT
{
	public static enum SubstanceType
	{
		ELEMENT, COMPOUND;
		public static final SubstanceType[] VALUES = values();
	}
	
	protected static final String COUNT_KEY = "Count";
	protected static final String TYPE_KEY = "Type";
	
	protected int count;
	protected SubstanceType type;
	
	protected SubstanceBase(SubstanceType compoundType)
	{
		this(compoundType, 1);
	}
	
	protected SubstanceBase(SubstanceType compoundType, int formulaCount)
	{
		type = compoundType;
		count = formulaCount;
	}
	
	@Override
	public NBTTagCompound makeDataTag()
	{
		NBTTagCompound dataTag = new NBTTagCompound();
		// Save the count and type information
		dataTag.setInteger(COUNT_KEY, count);
		dataTag.setInteger(TYPE_KEY, type.ordinal());
		return dataTag;
	}
	
	@Override
	public void readFromDataTag(NBTTagCompound dataTag)
	{
		count = dataTag.getInteger(COUNT_KEY);
	}
}
