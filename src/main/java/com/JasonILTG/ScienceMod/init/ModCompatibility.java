package com.JasonILTG.ScienceMod.init;

import java.util.ArrayList;

import com.JasonILTG.ScienceMod.compat.ICompatibility;
import com.JasonILTG.ScienceMod.compat.jei.JEICompatibility;

import net.minecraftforge.fml.common.Loader;

/**
 * Init class for compatibility with other mods.
 */
public class ModCompatibility
{
	private static ArrayList<ICompatibility> compatibilities = new ArrayList<ICompatibility>();

    public static void registerModCompat()
    {
        compatibilities.add(new JEICompatibility());
    }

    public static void loadCompat(ICompatibility.InitializationPhase phase)
    {
        for (ICompatibility compatibility : compatibilities)
            if (Loader.isModLoaded(compatibility.getModId()) && compatibility.enableCompat())
                compatibility.loadCompatibility(phase);
    }
}
