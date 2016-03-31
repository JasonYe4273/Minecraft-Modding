package com.JasonILTG.ScienceMod.reference.chemistry.formula;

import com.JasonILTG.ScienceMod.IScienceNBT;

import net.minecraft.nbt.NBTTagCompound;

/**
 * Abstract class representing what a substance is made of.
 * 
 * @author JasonILTG and syy1125
 */
public abstract class SubstanceBase
		implements IHasFormula, IScienceNBT
{
	/**
	 * The type of the substance.
	 * 
	 * @author JasonILTG and syy1125
	 */
	public static enum SubstanceType
	{
		ELEMENT, COMPOUND;
		public static final SubstanceType[] VALUES = values();
	}
	
	protected static final String COUNT_KEY = "Count";
	protected static final String TYPE_KEY = "Type";
	
	/** The amount of the substance */
	protected int count;
	/** The <code>SubstanceType</code> */
	protected SubstanceType type;
	
	/**
	 * Constructor.
	 * 
	 * @param compoundType The <code>SubstanceType</code>
	 */
	protected SubstanceBase(SubstanceType compoundType)
	{
		this(compoundType, 1);
	}
	
	/**
	 * Constructor.
	 * 
	 * @param compoundType The <code>SubstanceType</code>
	 * @param formulaCount The amount of the substance
	 */
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
