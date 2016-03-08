package com.JasonILTG.ScienceMod.reference.chemistry.formula;

import net.minecraft.nbt.NBTBase;
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
	private static final String TYPE_KEY = "Type";
	
	protected int count;
	protected SubstanceType type;
	
	protected SubstanceBase()
	{
		this(1);
	}
	
	protected SubstanceBase(int formulaCount)
	{
		count = formulaCount;
	}
	
	@Override
	public NBTBase makeDataTag()
	{
		NBTTagCompound dataTag = new NBTTagCompound();
		dataTag.setInteger(COUNT_KEY, count);
		dataTag.setInteger(TYPE_KEY, type.ordinal());
		return dataTag;
	}
	
	@Override
	public void readFromDataTag(NBTBase dataTag)
	{
		count = ((NBTTagCompound) dataTag).getInteger(COUNT_KEY);
	}
	
	public SubstanceType identifyType(NBTBase dataTag)
	{
		return SubstanceType.VALUES[((NBTTagCompound) dataTag).getInteger(TYPE_KEY)];
	}
}
