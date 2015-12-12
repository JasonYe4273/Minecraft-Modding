package com.JasonILTG.ScienceMod.reference.chemistry;

import java.util.ArrayList;
import java.util.List;

public class Compound implements Formula
{
	private List<AtomGroup> groupList;
	private int count;
	
	public Compound()
	{
		groupList = new ArrayList<AtomGroup>();
	}
	
	@Override
	public void setCount(int count)
	{
		this.count = count;
	}
	
	@Override
	public String getFormula()
	{
		String out = (count <= 1 ? "" : Integer.toString(count));
		for (AtomGroup group : groupList)
		{
			out += group.getFormula();
		}
		return out;
	}
}
