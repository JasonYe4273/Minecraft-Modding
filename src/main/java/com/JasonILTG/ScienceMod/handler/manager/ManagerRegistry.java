package com.JasonILTG.ScienceMod.handler.manager;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import com.JasonILTG.ScienceMod.manager.Manager;

/**
 * Registry for managers.
 * 
 * @author JasonILTG and syy1125
 */
public class ManagerRegistry
{
	/** The set of all <code>Manger</code>s in the world */
	private static Set<Manager> allManagers = new HashSet<Manager>();
	
	/**
	 * Registers a <code>Manager</code>.
	 * 
	 * @param m The <code>Manager</code> to register
	 */
	public static synchronized void registerManager(Manager m)
	{
		allManagers.add(m);
	}
	
	/**
	 * Called on the start of a tick.
	 */
	protected static synchronized void onTickStart()
	{
		Iterator<Manager> it = allManagers.iterator();
		
		while (it.hasNext())
		{
			Manager current = it.next();
			
			// If the manager is no longer valid, remove it from the registry.
			if (!current.isValid()) {
				it.remove();
				continue;
			}
			
			current.onTickStart();
		}
	}
	
	/**
	 * Called on the end of a tick.
	 */
	protected static synchronized void onTickEnd()
	{
		for (Manager current : allManagers)
		{
			current.onTickEnd();
		}
	}
}
