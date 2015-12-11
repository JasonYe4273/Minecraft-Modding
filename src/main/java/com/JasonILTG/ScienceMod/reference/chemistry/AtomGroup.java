package com.JasonILTG.ScienceMod.reference.chemistry;

import java.util.HashMap;
import java.util.Map;

public class AtomGroup
{
	private Map<Element, Integer> elementMap;
	
	public AtomGroup()
	{
		elementMap = new HashMap<Element, Integer>();
	}
	
	/**
	 * Adds a specific number of atoms of an element to the compound.
	 * 
	 * @param e The element to add
	 * @param count The number of atoms added
	 */
	public void addElement(Element e, int count)
	{
		elementMap.put(e, count);
	}
	
	@Override
	public String toString()
	{
		String out = "";
		
		for (Element e : elementMap.keySet())
		{
			out += e.getElementSymbol();
			int count = elementMap.get(e);
			if (count > 1) out += count;
		}
		
		return out;
	}
}
