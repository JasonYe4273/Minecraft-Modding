package com.JasonILTG.ScienceMod.reference.chemistry.formula;

import java.util.ArrayList;
import java.util.List;

import com.JasonILTG.ScienceMod.reference.NBTTypes;
import com.JasonILTG.ScienceMod.reference.chemistry.basics.EnumElement;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

/**
 * <code>SubstanceBase</code> class for compounds.
 * 
 * @author JasonILTG and syy1125
 */
public class CompoundSubstance
		extends SubstanceBase
{
	private static final String LIST_KEY = "ComponentList";
	
	/** The <code>List</code> of component <code>SubstanceBase</code>s */
	private List<SubstanceBase> components;
	
	/**
	 * Default constructor.
	 */
	protected CompoundSubstance()
	{
		this(1);
	}
	
	/**
	 * Constructor.
	 * 
	 * @param partsIn The component <code>SubstanceBase</code>s
	 */
	public CompoundSubstance(SubstanceBase... partsIn)
	{
		this(1, partsIn);
	}
	
	/**
	 * Constructor.
	 * 
	 * @param count The amount of substance
	 * @param partsIn The component <code>SubstanceBase</code>s
	 */
	public CompoundSubstance(int count, SubstanceBase... partsIn)
	{
		super(SubstanceType.COMPOUND, count);
		
		components = new ArrayList<SubstanceBase>();
		for (SubstanceBase part : partsIn) {
			components.add(part);
		}
	}
	
	/**
	 * Constructor for elements.
	 * 
	 * @param count The amount of substance
	 * @param element The element
	 */
	public CompoundSubstance(int count, EnumElement element)
	{
		super(SubstanceType.COMPOUND, 1);
		
		components = new ArrayList<SubstanceBase>();
		components.add(new ElementSubstance(element, count));
	}
	
	/**
	 * Adds another substance to the compound.
	 * 
	 * @param substance The substance to add
	 * @return The new <code>SubstanceBase</code>
	 */
	public CompoundSubstance append(SubstanceBase substance)
	{
		components.add(substance);
		return this;
	}
	
	@Override
	public String getFormula()
	{
		StringBuilder formulaBuilder = new StringBuilder();
		
		for (SubstanceBase formula : components) {
			formulaBuilder.append(formula.getFormula());
		}
		
		return count == 1 ? formulaBuilder.toString() : "(" + formulaBuilder.toString() + ")" + count;
	}
	
	/**
	 * Identifies the type of the NBT tag's <code>SubstanceBase</code>
	 * 
	 * @param dataTag The NBT tag
	 * @return The <code>SubstanceType</code>
	 */
	private static SubstanceType identifyType(NBTTagCompound dataTag)
	{
		return SubstanceType.VALUES[dataTag.getInteger(TYPE_KEY)];
	}
	
	@Override
	public NBTTagCompound makeDataTag()
	{
		NBTTagCompound dataTag = super.makeDataTag();
		
		// Use a TagList to store the information.
		NBTTagList tagList = new NBTTagList();
		
		for (SubstanceBase substance : components) {
			tagList.appendTag(substance.makeDataTag());
		}
		
		dataTag.setTag(LIST_KEY, tagList);
		
		return dataTag;
	}
	
	@Override
	public void readFromDataTag(NBTTagCompound dataTag)
	{
		super.readFromDataTag(dataTag);
		
		// Get the tag list and use it to unpack all the info.
		NBTTagList tagList = dataTag.getTagList(LIST_KEY, NBTTypes.COMPOUND);
		
		for (int i = 0; i < tagList.tagCount(); i ++)
		{
			// Load the tag and identify the type
			NBTTagCompound componentTag = tagList.getCompoundTagAt(i);
			SubstanceBase substance;
			
			if (identifyType(componentTag) == SubstanceType.ELEMENT) {
				substance = new ElementSubstance();
			}
			else {
				substance = new CompoundSubstance();
			}
			
			// Read the tag and store
			substance.readFromDataTag(componentTag);
			append(substance);
		}
	}
}
