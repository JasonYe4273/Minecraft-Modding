package com.JasonILTG.ScienceMod.reference.chemistry;

public class MultiAtomGroup implements MultiFormula
{
	private AtomGroup base;
	private int count;
	
	public MultiAtomGroup(AtomGroup group, int groupCount)
	{
		base = group;
		count = groupCount;
	}
	
	@Override
	public void setCount(int count)
	{
		this.count = count;
	}
	
	@Override
	public String getFormula()
	{
		String baseFormula = base.getFormula();
		return (count <= 1 ? baseFormula : "(" + baseFormula + ")" + Integer.toString(count));
	}
}
