package com.JasonILTG.ScienceMod.manager;

import java.util.HashSet;
import java.util.Set;

public class PowerNetwork
{
	private Set<PowerManager> generators;
	private Set<PowerManager> consumers;
	
	public PowerNetwork()
	{
		generators = new HashSet<PowerManager>();
		consumers = new HashSet<PowerManager>();
	}
	
	public void addGenerator(PowerManager manager)
	{
		generators.add(manager);
	}
	
	public void addConsumer(PowerManager manager)
	{
		consumers.add(manager);
	}
	
	public void removeGenerator(PowerManager manager)
	{
		generators.remove(manager);
	}
	
	public void removeConsumer(PowerManager manager)
	{
		consumers.remove(manager);
	}
	
	/**
	 * Check and remove any managers that is no longer valid.
	 */
	private void checkValidManagers()
	{
		for (PowerManager m : generators) {
			if (!m.isValid()) generators.remove(m);
		}
		
		for (PowerManager m : consumers) {
			if (!m.isValid()) generators.remove(m);
		}
	}
	
	/**
	 * Do a normal tick with power transfer taking place.
	 */
	public void doUpdateTick()
	{
		int totalGen = 0;
		for (PowerManager m : generators)
			totalGen += m.getCurrentOutput();
		
		int totalUse = 0;
		for (PowerManager m : consumers)
			totalUse += m.getCurrentInput();
		
		int powerTransfer = totalGen < totalUse ? totalGen : totalUse;
		for (PowerManager m : generators)
			m.requestPower(m.getCurrentOutput() * powerTransfer / totalGen);
		
		for (PowerManager m : consumers)
			m.supplyPower(m.getCurrentInput() * powerTransfer / totalUse);
	}
	
	/**
	 * Do a complex update tick with various checks. (Various checks WIP)
	 */
	public void doFullUpdate()
	{
		checkValidManagers();
		
		doUpdateTick();
	}
}
