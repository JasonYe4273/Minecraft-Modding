package com.JasonILTG.ScienceMod.reference;

public enum ChemElement
{
	// Period 1
	HYDROGEN("Hydrogen", "H"), HELIUM("Helium", "He"),
	// Period 2
	LITHIUM("Lithium", "Li"), BERYLLIUM("Beryllium", "Be"), BORON("Boron", "Bo"), CARBON("Carbon", "C"),
	NITROGEN("Nitrogen", "N"), OXYGEN("Oxygen", "O"), FLUORINE("Fluorine", "F"), NEON("Neon", "Ne"),
	// Period 3
	SODIUM("Sodium", "Na"), MAGNESIUM("Magnesium", "Mg"), ALUMINIUM("Aluminium", "Al"), SILICON("Silicon", "Si"),
	PHOSPHORUS("Phosphorus", "P"), SULFUR("Sulfur", "S"), CHLORINE("Chlorine", "Cl"), ARGON("Argon", "Ar"),
	// Period 4
	POTASSIUM("Potassium", "K"), CALCIUM("Calcium", "Ca"), SCANDIUM("Scandium", "Sc"), TITANIUM("Titanium", "Ti"),
	VANADIUM("Vanadium", "V"), CHROMIUM("Chromium", "Cr"), MANGANESE("Manganese", "Mn"), IRON("Iron", "Fe"), COBALT("Cobalt", "Co"),
	NICKEL("Nickel", "Ni"), COPPER("Copper", "Cu"), ZINC("Zinc", "Zn"), GALLIUM("Gallium", "Ga"), GERMANIUM("Germanium", "Ge"),
	ARSENIC("Arsenic", "As"), SELENIUM("Selenium", "Se"), BROMINE("Bromine", "Br"), KRYPTON("Krypton", "Kr"),
	// Period 5
	RUBIDIUM("Rubidium", "Rb"), STRONTIUM("Strontium", "Sr"), YTTRIUM("Yttrium", "Y"), ZIRCONIUM("Zirconium", "Zr"),
	NIOBIUM("Niobium", "Nb"), MOLYBDENUM("Molybdenum", "Mo"), TECHNETIUM("Technetium", "Tc"), RUTHENIUM("Ruthenium", "Ru"),
	RHODIUM("Rhodium", "Rh"), PALLADIUM("Palladium", "Pd"), SILVER("Silver", "Ag"), CADMIUM("Cadmium", "Cd"), INDIUM("Indium", "In"),
	TIN("Tin", "Sn"), ANTIMONY("Antimony", "Sb"), TELLURIUM("Tellurium", "Te"), IODINE("Iodine", "I"), XENON("Xenon", "Xe"),
	// Period 6
	CAESIUM("Caesium", "Cs"), BARIUM("Barium", "Ba"), LANTHANUM("Lanthanum", "La"), CERIUM("Cerium", "Ce"),
	PRASEODYMIUM("Praseodymium", "Pr"), NEODYMIUM("Neodymium", "Nd"), PROMETHIUM("Promethium", "Pm"), SAMARIUM("Samarium", "Sm"),
	EUROPIUM, GADOLINIUM, TERBIUM, DYSPROSIUM, HOLMIUM, ERBIUM, THULIUM, YTTERBIUM,
	LUTETIUM, HAFNIUM, TANTALUM, TUNGSTEN, RHENIUM, OSMIUM, IRIDIIUM, PLATINUM, GOLD, MERCURY, THALLIUM, LEAD, BISMUTH, POLONIUM, ASTATINE, RADON,
	// Period 7
	FRANCIUM, RADIUM, ACTINIUM, THORIUM, PROTACTINIUM, URANIUM, NEPTUNIUM, PLUTONIUM, AMERICIUM, CURIUM, BERKELIUM, CALIFORNIUM, EINSTEINIUM, FERMIUM, MENDELEVIUM, NOBELIUM,
	LAWRENCIUM, RUTHERFORDIUM, DUBNIUM, SEABORGIUM, BOHRIUM, HASSIUM, MEITNERIUM, DARMSTADTIUM, ROENTGENIUM, COPERNICIUM, UNUNTRIUM, FLEROVIUM, UNUNPENTIUM, LIVERMORIUM, UNUNSEPTIUM, UNUNOCTIUM;
	
	private String name;
	private String unlocalizedName;
	private String symbol;
	
	private ChemElement(String elementName, String elementSymbol)
	{
		name = elementName;
		unlocalizedName = elementName.toLowerCase();
		symbol = elementSymbol;
	}
	
	public int getAtomicNumber()
	{
		return ordinal() + 1;
	}
	
	public String getElementSymbol()
	{
		return symbol;
	}
	
	public String getElementName()
	{
		return name;
	}
	
	public String getUnlocalizedName()
	{
		return unlocalizedName;
	}
	
	private static final int ELEMENT_COUNT = values().length;
	
	public static ChemElement getElementByAtomicNumber(int atomicNumber)
	{
		if (atomicNumber < 0 || atomicNumber > ELEMENT_COUNT) return null;
		
		// Indexes are one lower that atomic number.
		return values()[atomicNumber - 1];
	}
}
