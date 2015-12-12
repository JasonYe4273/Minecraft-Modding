package com.JasonILTG.ScienceMod.reference.chemistry;

public class MultiElement implements Formula
{
	private Element base;
	private int count;
	
	MultiElement(Element baseElement, int elementCount)
	{
		base = baseElement;
		count = elementCount;
	}
	
	@Override
	public void setCount(int count)
	{
		this.count = count;
	}
	
	@Override
	public String getFormula()
	{
		return count <= 1 ? base.symbol : base.symbol + count;
	}
}
