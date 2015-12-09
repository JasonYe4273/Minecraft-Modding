package com.JasonILTG.ScienceMod.reference;

/**
 * Reference class for names of items, blocks, and tile entities.
 * 
 * @author JasonILTG and syy1125
 */
public class Names
{
	public static final class Items
	{
		public static final String ELEMENT = "element";
		public static final String ELEMENT_DUST = "element_dust";
		
		public static final class Armor
		{
			public static final String EXO_PREFIX = "exo.";
			
			public static final String HELMET_NAME = "helmet";
			public static final String CHESTPLATE_NAME = "chest";
			public static final String LEGGINGS_NAME = "legs";
			public static final String BOOTS_NAME = "boots";
			public static final String[] ARMOR_PARTS_NAME = { HELMET_NAME, CHESTPLATE_NAME, LEGGINGS_NAME, BOOTS_NAME };
		}
	}
	
	public static final class Blocks
	{
		public static final class Machine
		{
			public static final String MACHINE_ELECTROLYZER = "electrolyzer";
			public static final String MACHINE_AIR_EXTRACTOR = "air_extractor";
			public static final String MACHINE_CONDENSER = "condenser";
			public static final String MACHINE_MIXER = "mixer";
			public static final String MACHINE_CENTRIFUGE = "centrifuge";
			public static final String MACHINE_DISTILLER = "distiller";
			public static final String MACHINE_CHEM_REACTOR = "chemical_reactor";
		}
		
		public static final class Generator
		{
			public static final String GENERATOR_COMBUSTER = "combuster";
			public static final String GENERATOR_SOLAR_PANEL = "solar_panel";
		}
	}
	
	public static final class Tiles
	{
		public static final class Machine
		{
			public static final String ELECTROLYZER = "tileEntityElectrolyzer";
			public static final String AIR_EXTRACTOR = "tileEntityAirExtractor";
			public static final String CONDENSER = "tileEntityCondenser";
			public static final String MIXER = "tileEntityMixer";
			public static final String CENTRIFUGE = "tileEntityCentrifuge";
			public static final String DISTILLER = "tileEntityDistiller";
			public static final String CHEM_REACTOR = "tileEntityChemicalReactor";
		}
		
		public static final class Generator
		{
			public static final String COMBUSTER = "tileEntityCombuster";
			public static final String SOLAR_PANEL = "tileEntitySolarPanel";
		}
	}
}
