package com.JasonILTG.ScienceMod.reference.chemistry;

import java.util.ArrayList;
import java.util.List;

public class AtomGroup implements Formula
{
	private List<MultiElement> elementList;
	private int count;
	
	public AtomGroup()
	{
		this(1);
	}
	
	public AtomGroup(int count)
	{
		elementList = new ArrayList<MultiElement>();
		this.count = count;
	}
	
	public AtomGroup addElement(MultiElement e)
	{
		elementList.add(e);
		return this;
	}
	
	public AtomGroup addElement(Element base, int count)
	{
		return addElement(new MultiElement(base, count));
	}
	
	@Override
	public void setCount(int count)
	{
		this.count = count;
	}
	
	@Override
	public String getFormula()
	{
		String out = "";
		for (MultiElement e : elementList) {
			out += e.getFormula();
		}
		
		if (count > 1)
		{
			out = "(" + out + ")" + count;
		}
		
		return out;
	}
}
