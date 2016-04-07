package com.JasonILTG.ScienceMod.handler.config;

/**
 * A reference class for storing the values loaded from config.
 * 
 * @author JasonILTG and syy1125
 */
public class ConfigData
{
	public static boolean configBoolean1;
	
	public static class World
	{
		public static float jarLauncherStr;
		public static boolean chemicalExplosionDamageBlocks;
	}
	
	public static class Machine
	{
		public static boolean fireOnOverheat;
		public static float fireWeight;
		public static int fireDist;
		public static int fireDistMult;
		public static int maxFireDist;
		
		public static boolean expOnOverheat;
		public static float expWeight;
		public static float expStr;
		public static boolean expDamageBlocks;
	}
}
