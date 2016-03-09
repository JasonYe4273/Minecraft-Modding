package com.JasonILTG.ScienceMod.reference.chemistry.formula;

import net.minecraft.nbt.NBTTagCompound;

import com.JasonILTG.ScienceMod.reference.chemistry.basics.EnumElement;

public class ElementSubstance
		extends SubstanceBase
{
	private static final String ELEMENT_KEY = "Element";
	
	private EnumElement base;
	
	protected ElementSubstance()
	{
		this(null, 1);
	}
	
	public ElementSubstance(EnumElement baseElement, int elementCount)
	{
		super(SubstanceType.ELEMENT, elementCount);
		base = baseElement;
	}
	
	@Override
	public String getFormula()
	{
		return count == 1 ? base.getElementSymbol() : base.getElementSymbol() + count;
	}
	
	@Override
	public NBTTagCompound makeDataTag()
	{
		NBTTagCompound dataTag = super.makeDataTag();
		// Add the element information
		dataTag.setInteger(ELEMENT_KEY, base.ordinal());
		return dataTag;
	}
	
	@Override
	public void readFromDataTag(NBTTagCompound dataTag)
	{
		super.readFromDataTag(dataTag);
		base = EnumElement.VALUES[dataTag.getInteger(ELEMENT_KEY)];
	}
	
}
