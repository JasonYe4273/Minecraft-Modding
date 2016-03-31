package com.JasonILTG.ScienceMod.reference.chemistry.formula;

import com.JasonILTG.ScienceMod.reference.chemistry.basics.EnumElement;

import net.minecraft.nbt.NBTTagCompound;

/**
 * <code>SubstanceBase</code> class for elements
 * 
 * @author JasonILTG and syy1125
 */
public class ElementSubstance
		extends SubstanceBase
{
	private static final String ELEMENT_KEY = "Element";
	
	/** The element */
	private EnumElement base;
	
	/**
	 * Default constructor.
	 */
	protected ElementSubstance()
	{
		this(null, 1);
	}
	
	/**
	 * Constructor.
	 * 
	 * @param baseElement The element
	 * @param elementCount The number of element atoms
	 */
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
