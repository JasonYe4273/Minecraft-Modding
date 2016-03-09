package com.JasonILTG.ScienceMod.reference.chemistry.basics;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import com.JasonILTG.ScienceMod.reference.MatterState;
import com.JasonILTG.ScienceMod.reference.chemistry.formula.ElementSubstance;
import com.JasonILTG.ScienceMod.reference.chemistry.formula.SubstanceBase;

/**
 * Enum for all of the elements and their data.
 * 
 * @author JasonILTG and syy1125
 */
public enum EnumElement
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
	CESIUM("Cesium", "Cs"), BARIUM("Barium", "Ba"), LANTHANUM("Lanthanum", "La"), CERIUM("Cerium", "Ce"),
	PRASEODYMIUM("Praseodymium", "Pr"), NEODYMIUM("Neodymium", "Nd"), PROMETHIUM("Promethium", "Pm"), SAMARIUM("Samarium", "Sm"),
	EUROPIUM("Europium", "Eu"), GADOLINIUM("Gadolinium", "Gd"), TERBIUM("Terbium", "Tb"), DYSPROSIUM("Dysprosium", "Dy"),
	HOLMIUM("Holmium", "Ho"), ERBIUM("Erbium", "Er"), THULIUM("Thulium", "Tm"), YTTERBIUM("Ytterbium", "Yb"),
	LUTETIUM("Lutetium", "Lu"), HAFNIUM("Hafnium", "Hf"), TANTALUM("Tantalum", "Ta"), TUNGSTEN("Tungsten", "W"),
	RHENIUM("Rhenium", "Re"), OSMIUM("Osmium", "Os"), IRIDIIUM("Iridium", "Ir"), PLATINUM("Platinum", "Pt"), GOLD("Gold", "Au"),
	MERCURY("Mercury", "Hg"), THALLIUM("Thallium", "Tl"), LEAD("Lead", "Pb"), BISMUTH("Bismuth", "Bi"), POLONIUM("Polonium", "Po"),
	ASTATINE("Astatine", "At"), RADON("Radon", "Rn"),
	// Period 7
	FRANCIUM("Francium", "Fr"), RADIUM("Radium", "Ra"), ACTINIUM("Actinium", "Ac"), THORIUM("Thorium", "Th"),
	PROTACTINIUM("Protactinium", "Pa"), URANIUM("Uranium", "U"), NEPTUNIUM("Neptunium", "Np"), PLUTONIUM("Plutonium", "Pu"),
	AMERICIUM("Americium", "Am"), CURIUM("Curium", "Cm"), BERKELIUM("Berkelium", "Bk"), CALIFORNIUM("Californium", "Cf"),
	EINSTEINIUM("Einsteinium", "Es"), FERMIUM("Fermium", "Fm"), MENDELEVIUM("Mendelevium", "Md"), NOBELIUM("Nobelium", "No"),
	LAWRENCIUM("Lawrencium", "Lr"), RUTHERFORDIUM("Rutherfordium", "Rf"), DUBNIUM("Dubnium", "Db"), SEABORGIUM("Seaborgium", "Sg"),
	BOHRIUM("Bohrium", "Bh"), HASSIUM("Hassium", "Hs"), MEITNERIUM("Meitnerium", "Mt"), DARMSTADTIUM("Darmstadtium", "Ds"),
	ROENTGENIUM("Roentgenium", "Rg"), COPERNICIUM("Copernicium", "Cn"), UNUNTRIUM("Ununtrium", "Uut"), FLEROVIUM("Flerovium", "Fl"),
	UNUNPENTIUM("Ununpentium", "Uup"), LIVERMORIUM("Livermorium", "Lv"), UNUNSEPTIUM("Ununseptium", "Uus"),
	UNUNOCTIUM("Ununoctium", "Uuo");
	
	private static final HashMap<String, EnumElement> elementMap = new HashMap<String, EnumElement>();
	
	// All package access intended for more efficient access
	final String name;
	final String lowerCaseName;
	final String symbol;
	/** Whether the element is naturally polyatomic */
	final boolean isPoly;
	private Set<Ion> ionizedForms;
	/** The array of ions that this element has */
	private Ion[] ionArray;
	
	// Elements that are naturally polyatomic
	public static final int[] polyatomics = { 0, 6, 7, 8, 16, 34 };
	// Elements that are naturally gaseous
	public static final int[] gases = { 0, 1, 6, 7, 8, 9, 16, 17, 35, 53, 85 };
	// Elements that are naturally liquid
	public static final int[] liquids = { 34, 79 };
	// All other elements are assumed to be naturally solid
	
	/** An instance array for faster access */
	public static final EnumElement[] VALUES = values();
	public static final int ELEMENT_COUNT = VALUES.length;
	
	private static boolean[] isPolyatomic = new boolean[VALUES.length];
	private static MatterState[] states = new MatterState[VALUES.length];
	private static boolean polyatomicInit = false;
	private static boolean statesInit = false;
	
	private EnumElement(String elementName, String elementSymbol)
	{
		name = elementName;
		lowerCaseName = elementName.toLowerCase();
		symbol = elementSymbol;
		ionizedForms = new HashSet<Ion>();
		ionArray = null;

		initPoly();
		initStates();
		isPoly = isPoly();
		addElement();
	}
	
	public static void initPoly()
	{
		if (polyatomicInit) return;
		
		for (int p : polyatomics)
		{
			isPolyatomic[p] = true;
		}
		polyatomicInit = true;
	}
	
	public static void initStates()
	{
		if (statesInit) return;
		
		for (int g : gases)
		{
			states[g] = MatterState.GAS;
		}
		
		for (int l : liquids)
		{
			states[l] = MatterState.GAS;
		}
		
		for (int i = 0; i < VALUES.length; i++)
		{
			if (states[i] == null) states[i] = MatterState.SOLID;
		}
		
		statesInit = true;
	}
	
	public static EnumElement getElement(String formula)
	{
		return elementMap.get(formula);
	}
	
	public void addElement()
	{
		elementMap.put(getElementCompound(), this);
	}
	
	/**
	 * @return Whether the element is naturally polyatomic.
	 */
 	private boolean isPoly()
	{
		return isPolyatomic[ordinal()];
	}
	
	/**
	 * @return The element's atomic number
	 */
	public int getAtomicNumber()
	{
		return ordinal() + 1;
	}
	
	/**
	 * @return The symbol of the element
	 */
	public String getElementSymbol()
	{
		return symbol;
	}
	
	/**
	 * @return The name of the element
	 */
	public String getElementName()
	{
		return name;
	}
	
	/**
	 * @return The substance form of the element
	 */
	public SubstanceBase getElementSubstance()
	{
		if (isPoly) {
			return new ElementSubstance(this, 2);
		}
		
		return new ElementSubstance(this, 1);
	}
	
	/**
	 * @return The state of matter the element is in naturally
	 */
	public MatterState getElementState()
	{
		return states[ordinal()];
	}
	
	/**
	 * @return The unlocalized name
	 */
	public String getUnlocalizedName()
	{
		return lowerCaseName;
	}
	
	/**
	 * @return The compound formula of the element.
	 */
	public String getElementCompound()
	{
		int i = ordinal();
		for (int p : polyatomics)
		{
			if (i == p) return symbol + "2";
		}
		return symbol;
	}
	
	/**
	 * Returns an element given its atomic number.
	 * 
	 * @param atomicNumber The atomic number of the element
	 * @return The element
	 */
	public static EnumElement getElementByAtomicNumber(int atomicNumber)
	{
		if (atomicNumber < 0 || atomicNumber > ELEMENT_COUNT) return null;
		
		// Indexes are one lower that atomic number.
		return VALUES[atomicNumber - 1];
	}
	
	/**
	 * Records an ionized form of this element. (Package access is intended.)
	 * 
	 * @param i The ion to record.
	 */
	void addIon(Ion i)
	{
		ionizedForms.add(i);
		ionArray = ionizedForms.toArray(new Ion[ionizedForms.size()]);
	}
	
	/**
	 * @return All the ionized forms of this element
	 */
	public Ion[] getIons()
	{
		return ionArray;
	}
}
