package com.JasonILTG.ScienceMod.reference;

public class NBTKeys
{
	public static final String SLOT = "Slot";
	public static final String ITEMS = "Items";
	public static final String TANKS = "Tanks";
	
	public static final class Chemical
	{
		
		public static final String IONS = "Ions";
		public static final String PRECIPITATES = "Precipitates";
		public static final String ION = "Ion";
		public static final String PRECIPITATE = "Precipitate";
		public static final String MOLS = "Mols";
		public static final String CHARGE = "Charge";
		public static final String STATE = "State";
		public static final String STABLE = "Stable";
		
	}
	
	// Deprecated, will remove soon
	public static final String IONS = Chemical.IONS;
	public static final String PRECIPITATES = Chemical.PRECIPITATES;
	public static final String ION = Chemical.ION;
	public static final String PRECIPITATE = Chemical.PRECIPITATE;
	public static final String MOLS = Chemical.MOLS;
	public static final String CHARGE = Chemical.CHARGE;
	public static final String STATE = Chemical.STATE;
	public static final String STABLE = Chemical.STABLE;
	
	public static final class MachineData
	{
		public static final String RECIPE = "Recipe";
		public static final String MAX_PROGRESS = "MaxProgress";
		public static final String CURRENT_PROGRESS = "CurrentProgress";
	}
	
	public static final class Manager
	{
		public static final String HEAT = "HeatManager";
		public static final String POWER = "PowerManager";
		
		public static final class Power
		{
			public static final String CAPACITY = "Capacity";
			public static final String CURRENT = "CurrentPower";
			public static final String MAX_IN = "MaxInput";
			public static final String MAX_OUT = "MaxOutput";
		}
		
		public static final class Heat
		{
			public static final String TEMP_LIMIT = "MaxTemperature";
			public static final String CURRENT = "CurrentTemperature";
			public static final String SPECIFIC_HEAT = "SpecificHeat";
			public static final String HEAT_LOSS = "HeatLoss";
			public static final String HEAT_TRANSFER = "HeatTransfer";
			public static final String OVERHEAT = "Overheat";
		}
	}
}
