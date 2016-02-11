package com.JasonILTG.ScienceMod.reference.chemistry;

import java.util.ArrayList;
import java.util.List;

public class AtomGroup
		implements IHasFormula
{
	private List<MultiElement> elementList;
	
	public AtomGroup()
	{
		elementList = new ArrayList<MultiElement>();
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
	public String getFormula()
	{
		String out = "";
		for (MultiElement e : elementList) {
			out += e.getFormula();
		}
		
		return out;
	}
}
