package com.JasonILTG.ScienceMod.handler.manager;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import com.JasonILTG.ScienceMod.manager.Manager;

public class ManagerRegistry
{
	private static Set<Manager> allManagers = new HashSet<Manager>();
	
	public static synchronized void registerManager(Manager m)
	{
		allManagers.add(m);
	}
	
	/*package*/static synchronized void onTickStart()
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
	
	/*package*/static synchronized void onTickEnd()
	{
		for (Manager current : allManagers)
		{
			current.onTickEnd();
		}
	}
}
