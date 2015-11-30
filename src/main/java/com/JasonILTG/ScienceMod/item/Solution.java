package com.JasonILTG.ScienceMod.item;

import java.util.ArrayList;
import java.util.List;

import com.JasonILTG.ScienceMod.creativetabs.ScienceCreativeTabs;
import com.JasonILTG.ScienceMod.init.ScienceModItems;
import com.JasonILTG.ScienceMod.item.general.ItemJarred;
import com.JasonILTG.ScienceMod.reference.NBTKeys;
import com.JasonILTG.ScienceMod.reference.NBTKeys.Chemical;
import com.JasonILTG.ScienceMod.reference.NBTTypes;
import com.JasonILTG.ScienceMod.util.LogHelper;
import com.JasonILTG.ScienceMod.util.NBTHelper;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumChatFormatting;

public class Solution extends ItemJarred
{
	public Solution()
	{
		setUnlocalizedName("solution");
		setCreativeTab(ScienceCreativeTabs.tabCompounds);
	}
	
	public static void check(ItemStack stack)
	{
		checkPrecipitates(stack);
		NBTHelper.checkFracZero(stack, new String[] { NBTKeys.Chemical.IONS, NBTKeys.Chemical.PRECIPITATES }, NBTKeys.Chemical.MOLS);
	}
	
	public static ItemStack parseItemStackSolution(ItemStack stack)
	{
		// Null check
		if (stack == null) return null;
		
		// Solutions
		if (stack.isItemEqual(new ItemStack(ScienceModItems.solution))) return stack.copy();
		
		// Water
		if (stack.isItemEqual(new ItemStack(ScienceModItems.water)))
		{
			ItemStack solutionStack = new ItemStack(ScienceModItems.solution, stack.stackSize);
			NBTTagCompound solutionTag = new NBTTagCompound();
			solutionTag.setTag(NBTKeys.Chemical.IONS, new NBTTagList());
			solutionTag.setTag(NBTKeys.Chemical.PRECIPITATES, new NBTTagList());
			solutionStack.setTagCompound(solutionTag);
			return solutionStack;
		}
		
		// Everything else
		return null;
	}
	
	@Override
	public void addInformation(ItemStack stack, EntityPlayer playerIn, List tooltip, boolean advanced)
	{
		if (stack.getTagCompound() != null)
		{
			NBTTagList ionTagList = stack.getTagCompound().getTagList(NBTKeys.Chemical.IONS, NBTTypes.COMPOUND);
			NBTTagList precipitateTagList = stack.getTagCompound().getTagList(NBTKeys.Chemical.PRECIPITATES, NBTTypes.COMPOUND);
			
			for (int i = 0; i < ionTagList.tagCount(); i ++)
			{
				NBTTagCompound ionTagCompound = ionTagList.getCompoundTagAt(i);
				double mols = NBTHelper.parseFrac(ionTagCompound.getIntArray(NBTKeys.Chemical.MOLS));
				String ion = ionTagCompound.getString(NBTKeys.Chemical.ION);
				int charge = ionTagCompound.getInteger(NBTKeys.Chemical.CHARGE);
				String state = ionTagCompound.getString(NBTKeys.Chemical.STATE);
				
				tooltip.add(String.format("%s%3f mol %s (%s) (%s)", EnumChatFormatting.DARK_GRAY, mols, ion, String.valueOf(charge), state));
			}
			
			for (int i = 0; i < precipitateTagList.tagCount(); i ++)
			{
				NBTTagCompound precipitateTagCompound = precipitateTagList.getCompoundTagAt(i);
				double mols = NBTHelper.parseFrac(precipitateTagCompound.getIntArray(NBTKeys.Chemical.MOLS));
				String precipitate = precipitateTagCompound.getString(NBTKeys.Chemical.PRECIPITATE);
				String state = precipitateTagCompound.getString(NBTKeys.Chemical.STATE);
				
				tooltip.add(String.format("%s%3f mol %s (%s)", EnumChatFormatting.DARK_GRAY, mols, precipitate, state));
			}
		}
	}
	
	private static void checkPrecipitates(ItemStack stack)
	{
		// Check that it is a solution
		if (!stack.isItemEqual(new ItemStack(ScienceModItems.solution))) return;
		
		// Check if it's already stable
		if (stack.getTagCompound().getBoolean(NBTKeys.Chemical.STABLE)) return;
		
		NBTTagList ionList = stack.getTagCompound().getTagList(NBTKeys.Chemical.IONS, NBTTypes.COMPOUND);
		NBTTagList precipitateList = stack.getTagCompound().getTagList(NBTKeys.Chemical.PRECIPITATES, NBTTypes.COMPOUND);
		for (SolubleRecipe recipe : SolubleRecipe.values())
		{
			recipe.checkPrecipitateDissolved(ionList, precipitateList);
		}
		for (PrecipitateRecipe recipe : PrecipitateRecipe.values())
		{
			recipe.checkPrecipitateFormed(ionList, precipitateList);
		}
		
		stack.getTagCompound().setBoolean(NBTKeys.Chemical.STABLE, true);
	}
	
	public enum PrecipitateRecipe
	{
		
		H2O_1("H", "OH", "H2O", 0, 1, 1, 1, "l"),
		H2O_2("H", "O", "H2O", 0, 2, 1, 1, "l"),
		H2CO3_1("H", "HCO3", "CO2", 0, 1, 1, 1, "g"),
		H2CO3_2("H", "CO3", "CO2", 0, 2, 1, 1, "g"),
		H2S("H", "S", "H2S", 0, 2, 1, 1, "g"),
		SO2("H", "SO3", "SO2", 0, 2, 1, 1, "g"),
		NH3("NH4", "OH", "NH3", 0, 1, 1, 1, "l"),
		
		MgF2("Mg", "F", "MgF2", 0, 1, 2, 1, "s"),
		CaF2("Ca", "F", "CaF2", 0, 1, 2, 1, "s"),
		BaF2("Ba", "F", "BaF2", 0, 1, 2, 1, "s"),
		FeF3("Fe", "F", "FeF3", 3, 1, 3, 1, "s"),
		PbF2("Pb", "F", "PbF2", 2, 1, 2, 1, "s"),
		AgCl("Ag", "Cl", "AgCl", 0, 1, 1, 1, "s"),
		Hg2Cl2("Hg2", "Cl", "Hg2Cl2", 0, 1, 2, 1, "s"),
		AgBr("Ag", "Br", "AgBr", 0, 1, 1, 1, "s"),
		PbBr2("Pb", "Br", "PbBr2", 2, 1, 2, 1, "s"),
		Hg2Br2("Hg2", "Br", "Hg2Br2", 0, 1, 2, 1, "s"),
		AgI("Ag", "I", "AgI", 1, 0, 1, 1, "s"),
		PbI2("Pb", "I", "PbI2", 2, 1, 2, 1, "s"),
		Hg2I2("Hg2", "I", "Hg2I2", 1, 0, 2, 1, "s"),
		
		AlC2H3O23("Al", "C2H3O2", "Al(C2H3O2)3", 0, 1, 3, 1, "s"),
		
		MgOH2("Mg", "OH", "Mg(OH)2", 0, 1, 2, 1, "s"),
		CaOH2("Ca", "OH", "Ca(OH)2", 0, 1, 2, 1, "s"),
		BaOH2("Ba", "OH", "Ba(OH)2", 0, 1, 2, 1, "s"),
		AgOH("Ag", "OH", "AgOH", 0, 1, 1, 1, "s"),
		ZnOH2("Zn", "OH", "Zn(OH)2", 0, 1, 2, 1, "s"),
		CuOH2("Cu", "OH", "Cu(OH)2", 2, 1, 2, 1, "s"),
		PbOH2("Pb", "OH", "Pb(OH)2", 2, 1, 2, 1, "s"),
		AlOH3("Al", "OH", "Al(OH)3", 0, 1, 3, 1, "s"),
		FeOH3("Fe", "OH", "Fe(OH)3", 3, 1, 3, 1, "s"),
		
		Ag2S("Ag", "S", "Ag2S", 0, 2, 1, 1, "s"),
		ZnS("Zn", "S", "ZnS", 0, 1, 1, 1, "s"),
		CuS("Cu", "S", "CuS", 2, 1, 1, 1, "s"),
		PbS("Pb", "S", "PbS", 2, 1, 1, 1, "s"),
		Al2S3("Al", "S", "Al2S3", 0, 2, 3, 1, "s"),
		Fe2S3("Fe", "S", "Fe2S3", 3, 2, 3, 1, "s"),
		
		CaSO4("Ca", "SO4", "CaSO4", 0, 1, 1, 1, "s"),
		BaSO4("Ba", "SO4", "BaSO4", 0, 1, 1, 1, "s"),
		Ag2SO4("Ag", "SO4", "Ag2SO4", 0, 2, 1, 1, "s"),
		PbSO4("Pb", "SO4", "PbSO4", 2, 1, 1, 1, "s"),
		
		MgCO3("Mg", "CO3", "MgCO3", 0, 1, 1, 1, "s"),
		CaCO3("Ca", "CO3", "CaCO3", 0, 1, 1, 1, "s"),
		BaCO3("Ba", "CO3", "BaCO3", 0, 1, 1, 1, "s"),
		Ag2CO3("Ag", "CO3", "Ag2CO3", 0, 2, 1, 1, "s"),
		ZnCO3("Zn", "CO3", "ZnCO3", 0, 1, 1, 1, "s"),
		PbCO3("Pb", "CO3", "PbCO3", 2, 1, 1, 1, "s"),
		
		Li3PO4("Li", "PO4", "Li3PO4", 0, 3, 1, 1, "s"),
		Mg3PO42("Mg", "PO4", "Mg3(PO4)2", 0, 3, 2, 1, "s"),
		Ca3PO42("Ca", "PO4", "Ca3(PO4)2", 0, 3, 2, 1, "s"),
		Ba3PO42("Ba", "PO4", "Ba3(PO4)2", 0, 3, 2, 1, "s"),
		Ag3PO4("Ag", "PO4", "Ag3PO4", 0, 3, 1, 1, "s"),
		Zn3PO42("Zn", "PO4", "Zn3(PO4)2", 0, 3, 2, 1, "s"),
		Cu3PO42("Cu", "PO4", "Cu3(PO4)2", 2, 3, 2, 1, "s"),
		Pb3PO42("Pb", "PO4", "Pb3(PO4)2", 2, 3, 2, 1, "s"),
		AlPO4("Al", "PO4", "AlPO4", 0, 1, 1, 1, "s"),
		FePO4("Fe", "PO4", "FePO4", 3, 1, 1, 1, "s"),
		
		BaCrO4("Ba", "CrO4", "BaCrO4", 0, 1, 1, 1, "s"),
		Ag2CrO4("Ag", "CrO4", "Ag2CrO4", 0, 2, 1, 1, "s"),
		ZnCrO4("Zn", "CrO4", "ZnCrO4", 0, 1, 1, 1, "s"),
		CuCrO4("Cu", "CrO4", "CuCrO4", 2, 1, 1, 1, "s"),
		PbCrO4("Pb", "CrO4", "PbCrO4", 2, 1, 1, 1, "s"),
		Fe2CrO43("Fe", "CrO4", "Fe2(CrO4)3", 3, 2, 3, 1, "s");
		
		private String cation;
		private String anion;
		private String precipitate;
		private int transitionCharge;
		private int cationMols;
		private int anionMols;
		private int precipitateMols;
		private String precipitateState;
		
		private PrecipitateRecipe(String cation, String anion, String precipitate, int transitionCharge, int cationMols, int anionMols,
				int precipitateMols, String precipitateState)
		{
			// Ion and precipitate names
			this.cation = cation;
			this.anion = anion;
			this.precipitate = precipitate;
			// transition metal charge to distinguis between different possible charges (0 if not needed)
			this.transitionCharge = transitionCharge;
			// Number of moles in one reaction
			this.cationMols = cationMols;
			this.anionMols = anionMols;
			this.precipitateMols = precipitateMols;
			// State of the precipitate
			this.precipitateState = precipitateState;
		}
		
		public void checkPrecipitateFormed(NBTTagList ionList, NBTTagList precipitateList)
		{
			// Create list of ion names
			ArrayList<String> ions = new ArrayList<String>();
			for (int i = 0; i < ionList.tagCount(); i ++)
			{
				ions.add(ionList.getCompoundTagAt(i).getString(NBTKeys.Chemical.ION));
			}
			
			// Get the index of the cation
			int cationIndex = ions.lastIndexOf(cation);
			if (transitionCharge > 0)
			{
				// If needed, check for transition charge
				ArrayList<Integer> charges = new ArrayList<Integer>();
				for (int i = 0; i < ionList.tagCount(); i ++)
				{
					charges.add(ionList.getCompoundTagAt(i).getInteger(NBTKeys.Chemical.CHARGE));
				}
				
				while (cationIndex > -1)
				{
					if (cationIndex > -1 && charges.get(cationIndex) == transitionCharge)
					{
						break;
					}
					cationIndex = ions.lastIndexOf(cation);
				}
			}
			
			// Get the index anion, and check that both ions are present
			if (cationIndex < 0) return;
			int anionIndex = ions.indexOf(anion);
			if (anionIndex < 0) return;
			
			// Create list of ion mols
			ArrayList<Double> mols = new ArrayList<Double>();
			for (int i = 0; i < ionList.tagCount(); i ++)
			{
				mols.add(NBTHelper.parseFrac(ionList.getCompoundTagAt(i).getIntArray(NBTKeys.Chemical.MOLS)));
			}
			
			// Create lists for precipitate information
			ArrayList<String> precipitates = new ArrayList<String>();
			ArrayList<Double> precipitateMols = new ArrayList<Double>();
			for (int i = 0; i < precipitateList.tagCount(); i ++)
			{
				precipitates.add(precipitateList.getCompoundTagAt(i).getString(NBTKeys.Chemical.PRECIPITATE));
				precipitateMols.add(NBTHelper.parseFrac(precipitateList.getCompoundTagAt(i).getIntArray(NBTKeys.Chemical.MOLS)));
			}
			
			// Calculate the limiting reactant
			double cationBaseMols = mols.get(cationIndex) / cationMols;
			double anionBaseMols = mols.get(anionIndex) / anionMols;
			double precipitateMolsFormed;
			
			if (cationBaseMols > anionBaseMols)
			{
				// Anion is limiting -> delete it, and decrease mols of cation
				ionList.getCompoundTagAt(cationIndex).setIntArray(NBTKeys.Chemical.MOLS,
						NBTHelper.parseFrac(cationMols * (cationBaseMols - anionBaseMols)));
				ionList.removeTag(anionIndex);
				
				precipitateMolsFormed = this.precipitateMols * anionBaseMols;
			}
			else if (cationBaseMols < anionBaseMols)
			{
				// Cation is limiting -> delete it, and decrease mols of anion
				ionList.getCompoundTagAt(anionIndex).setIntArray(NBTKeys.Chemical.MOLS,
						NBTHelper.parseFrac(anionMols * (anionBaseMols - cationBaseMols)));
				ionList.removeTag(cationIndex);
				
				precipitateMolsFormed = this.precipitateMols * cationBaseMols;
			}
			else
			{
				// Otherwise, both are fully used
				precipitateMolsFormed = this.precipitateMols * anionBaseMols;
				
				// Delete both tags in reverse order to preserve indices
				if (cationIndex > anionIndex)
				{
					ionList.removeTag(cationIndex);
					ionList.removeTag(anionIndex);
				}
				else
				{
					ionList.removeTag(anionIndex);
					ionList.removeTag(cationIndex);
				}
			}
			
			// Create new precipitate tag
			NBTTagCompound precipitate;
			int precipitateIndex = precipitates.indexOf(this.precipitate);
			
			if (precipitateIndex < 0)
			{
				// If the precipitate doesn't already exist in solution, make a new tag
				precipitate = new NBTTagCompound();
				precipitate.setString(NBTKeys.Chemical.PRECIPITATE, this.precipitate);
				precipitate.setIntArray(NBTKeys.Chemical.MOLS, NBTHelper.parseFrac(precipitateMolsFormed));
				precipitate.setString(NBTKeys.Chemical.STATE, precipitateState);
				precipitateList.appendTag(precipitate);
			}
			else
			{
				// If it does already exist, just increase the mols of theold tag
				precipitateList.getCompoundTagAt(precipitateIndex).setIntArray(NBTKeys.Chemical.MOLS,
						NBTHelper.parseFrac(precipitateMols.get(precipitateIndex) + precipitateMolsFormed));
			}
		}
	}
	
	public enum SolubleRecipe
	{
		NH4F("NH4F", "s", "NH4", 1, "F", -1, 1, 1, 1),
		NH4Cl("NH4Cl", "s", "NH4", 1, "Cl", -1, 1, 1, 1),
		NH4Br("NH4Br", "s", "NH4", 1, "Br", -1, 1, 1, 1),
		NH4I("NH4I", "s", "NH4", 1, "I", -1, 1, 1, 1),
		NH42S("NH42S", "s", "NH4", 1, "S", -2, 2, 1, 1),
		NH42SO4("NH42SO4", "s", "NH4", 1, "SO4", -2, 2, 1, 1),
		NH42CO3("NH42CO3", "s", "NH4", 1, "CO3", -2, 2, 1, 1),
		NH4NO3("NH4NO3", "s", "NH4", 1, "NO3", -1, 1, 1, 1),
		NH43PO4("NH43PO4", "s", "NH4", 1, "PO4", -3, 3, 1, 1),
		NH42CrO4("NH42CrO4", "s", "NH4", 1, "CrO4", -2, 2, 1, 1),
		NH4C2H3O2("NH4C2H3O2", "s", "NH4", 1, "C2H3O2", -1, 1, 1, 1),
		
		LiF("LiF", "s", "Li", 1, "F", -1, 1, 1, 1),
		LiCl("LiCl", "s", "Li", 1, "Cl", -1, 1, 1, 1),
		LiBr("LiBr", "s", "Li", 1, "Br", -1, 1, 1, 1),
		LiI("LiI", "s", "Li", 1, "I", -1, 1, 1, 1),
		LiOH("LiOH", "s", "Li", 1, "OH", -1, 1, 1, 1),
		Li2S("Li2S", "s", "Li", 1, "S", -2, 2, 1, 1),
		Li2SO4("Li2SO4", "s", "Li", 1, "SO4", -2, 2, 1, 1),
		Li2CO3("Li2CO3", "s", "Li", 1, "CO3", -2, 2, 1, 1),
		LiNO3("LiNO3", "s", "Li", 1, "NO3", -1, 1, 1, 1),
		Li2CrO4("Li2CrO4", "s", "Li", 1, "CrO4", -2, 2, 1, 1),
		LiC2H3O2("LiC2H3O2", "s", "Li", 1, "C2H3O2", -1, 1, 1, 1),
		
		NaF("NaF", "s", "Na", 1, "F", -1, 1, 1, 1),
		NaCl("NaCl", "s", "Na", 1, "Cl", -1, 1, 1, 1),
		NaBr("NaBr", "s", "Na", 1, "Br", -1, 1, 1, 1),
		NaI("NaI", "s", "Na", 1, "I", -1, 1, 1, 1),
		NaOH("NaOH", "s", "Na", 1, "OH", -1, 1, 1, 1),
		Na2S("Na2S", "s", "Na", 1, "S", -2, 2, 1, 1),
		Na2SO4("Na2SO4", "s", "Na", 1, "SO4", -2, 2, 1, 1),
		Na2CO3("Na2CO3", "s", "Na", 1, "CO3", -2, 2, 1, 1),
		NaNO3("NaNO3", "s", "Na", 1, "NO3", -1, 1, 1, 1),
		Na3PO4("Na3PO4", "s", "Na", 1, "PO4", -1, 3, 1, 1),
		Na2CrO4("Na2CrO4", "s", "Na", 1, "CrO4", -2, 2, 1, 1),
		NaC2H3O2("NaC2H3O2", "s", "Na", 1, "C2H3O2", -1, 1, 1, 1),
		
		KF("KF", "s", "K", 1, "F", -1, 1, 1, 1),
		KCl("KCl", "s", "K", 1, "Cl", -1, 1, 1, 1),
		KBr("KBr", "s", "K", 1, "Br", -1, 1, 1, 1),
		KI("KI", "s", "K", 1, "I", -1, 1, 1, 1),
		KOH("KOH", "s", "K", 1, "OH", -1, 1, 1, 1),
		K2S("K2S", "s", "K", 1, "S", -2, 2, 1, 1),
		K2SO4("K2SO4", "s", "K", 1, "SO4", -2, 2, 1, 1),
		K2CO3("K2CO3", "s", "K", 1, "CO3", -2, 2, 1, 1),
		KNO3("KNO3", "s", "K", 1, "NO3", -1, 1, 1, 1),
		K3PO4("K3PO4", "s", "K", 1, "PO4", -1, 3, 1, 1),
		K2CrO4("K2CrO4", "s", "K", 1, "CrO4", -2, 2, 1, 1),
		KC2H3O2("KC2H3O2", "s", "K", 1, "C2H3O2", -1, 1, 1, 1),

		MgCl2("MgCl2", "s", "Mg", 2, "Cl", -1, 1, 2, 1),
		MgBr2("MgBr2", "s", "Mg", 2, "Br", -1, 1, 2, 1),
		MgI2("MgI2", "s", "Mg", 2, "I", -1, 1, 2, 1),
		MgSO4("Mg2SO4", "s", "Mg", 2, "SO4", -2, 1, 1, 1),
		MgNO32("Mg(NO3)2", "s", "Mg", 2, "NO3", -1, 1, 2, 1),
		MgCrO4("MgCrO4", "s", "Mg", 2, "CrO4", -2, 1, 1, 1),
		MgC2H3O22("Mg(C2H3O2)2", "s", "Mg", 2, "C2H3O2", -1, 1, 2, 1),

		CaCl2("CaCl2", "s", "Ca", 2, "Cl", -1, 1, 2, 1),
		CaBr2("CaBr2", "s", "Ca", 2, "Br", -1, 1, 2, 1),
		CaI2("CaI2", "s", "Ca", 2, "I", -1, 1, 2, 1),
		CaNO32("Ca(NO3)2", "s", "Ca", 2, "NO3", -1, 1, 2, 1),
		CaCrO4("CaCrO4", "s", "Ca", 2, "CrO4", -2, 1, 1, 1),
		CaC2H3O22("Ca(C2H3O2)2", "s", "Ca", 2, "C2H3O2", -1, 1, 2, 1),

		BaCl2("BaCl2", "s", "Ba", 2, "Cl", -1, 1, 2, 1),
		BaBr2("BaBr2", "s", "Ba", 2, "Br", -1, 1, 2, 1),
		BaI2("BaI2", "s", "Ba", 2, "I", -1, 1, 2, 1),
		BaOH2("Ba(OH)2", "s", "Ba", 2, "OH", -1, 1, 2, 1),
		BaNO32("Ba(NO3)2", "s", "Ba", 2, "NO3", -1, 1, 2, 1),
		BaC2H3O22("Ba(C2H3O2)2", "s", "Ba", 2, "C2H3O2", -1, 1, 2, 1),

		AlF3("AlF3", "s", "Al", 3, "F", -1, 1, 3, 1),
		AlCl3("AlCl3", "s", "Al", 3, "Cl", -1, 1, 3, 1),
		AlBr3("AlBr3", "s", "Al", 3, "Br", -1, 1, 3, 1),
		AlI3("AlI3", "s", "Al", 3, "I", -1, 1, 3, 1),
		Al2SO43("Al2(SO4)3", "s", "Al", 3, "SO4", -2, 2, 3, 1),
		AlNO33("Al(NO3)3", "s", "Al", 3, "NO3", -1, 1, 3, 1),
		
		FeCl3("FeCl3", "s", "Fe", 3, "Cl", -1, 1, 3, 1),
		FeBr3("FeBr3", "s", "Fe", 3, "Br", -1, 1, 3, 1),
		Fe2SO43("Fe2(SO4)3", "s", "Fe", 3, "SO4", -2, 2, 3, 1),
		FeNO33("Fe(NO3)3", "s", "Fe", 3, "NO3", -1, 1, 3, 1),
		FeC2H3O23("Fe(C2H3O2)3", "s", "Fe", 3, "C2H3O2", -1, 1, 3, 1),
		
		CuF2("CuF2", "s", "Cu", 2, "F", -1, 1, 2, 1),
		CuCl2("CuCl2", "s", "Cu", 2, "Cl", -1, 1, 2, 1),
		CuBr2("CuBr2", "s", "Cu", 2, "Br", -1, 1, 2, 1),
		CuSO4("CuSO4", "s", "Cu", 2, "SO4", -2, 1, 1, 1),
		CuNO32("Cu(NO3)2", "s", "Cu", 2, "NO3", -1, 1, 2, 1),
		CuC2H3O22("Cu(C2H3O2)2", "s", "Cu", 2, "C2H3O2", -1, 1, 2, 1),
		
		AgF("AgF", "s", "Ag", 1, "F", -1, 1, 1, 1),
		AgNO3("AgNO3", "s", "Ag", 1, "NO3", -1, 1, 1, 1),
		AgC2H3O2("AgC2H3O2", "s", "Ag", 1, "C2H3O2", -1, 1, 1, 1),
		
		ZnF2("ZnF2", "s", "Zn", 2, "F", -1, 1, 2, 1),
		ZnCl2("ZnCl2", "s", "Zn", 2, "Cl", -1, 1, 2, 1),
		ZnBr2("ZnBr2", "s", "Zn", 2, "Br", -1, 1, 2, 1),
		ZnI("ZnI", "s", "Zn", 2, "I", -1, 1, 2, 1),
		ZnSO4("ZnSO4", "s", "Zn", 2, "SO4", -2, 1, 1, 1),
		ZnNO32("Zn(NO3)2", "s", "Zn", 2, "NO3", -1, 1, 2, 1),
		ZnC2H3O22("Zn(C2H3O2)2", "s", "Zn", 2, "C2H3O2", -1, 1, 2, 1),
		
		H2("H2", "g", "H", 1, "", 0, 1, 2, 0),
		F2("F2", "g", "", 0, "F", -1, 1, 0, 2),
		Cl2("Cl2", "g", "", 0, "Cl", -1, 1, 0, 2),
		Ag("Ag", "s", "Ag", 1, "", 0, 1, 1, 0);
		
		private String precipitate;
		private String precipitateState;
		private String cation;
		private int pCharge;
		private String anion;
		private int nCharge;
		private int precipitateMols;
		private int cationMols;
		private int anionMols;
		
		private SolubleRecipe(String precipitate, String precipitateState, String cation, int pCharge, String anion, int nCharge,
				int precipitateMols, int cationMols, int anionMols)
		{
			// Ion and precipitate names
			this.precipitate = precipitate;
			this.cation = cation;
			this.anion = anion;
			// Ion charges
			this.pCharge = pCharge;
			this.nCharge = nCharge;
			// Number of moles in one reaction
			this.precipitateMols = precipitateMols;
			this.cationMols = cationMols;
			this.anionMols = anionMols;
			// State of the precipitate
			this.precipitateState = precipitateState;
		}
		
		public void checkPrecipitateDissolved(NBTTagList ionList, NBTTagList precipitateList)
		{
			// Null check
			if (ionList == null || precipitateList == null) return;
			
			// Create list of precipitate names
			ArrayList<String> precipitates = new ArrayList<String>();
			for (int i = 0; i < precipitateList.tagCount(); i ++)
			{
				precipitates.add(precipitateList.getCompoundTagAt(i).getString(Chemical.PRECIPITATE));
			}
			
			// Get the index of the precipitate
			int precipitateIndex = precipitates.indexOf(precipitate);
			if (precipitateIndex < 0 || !precipitateList.getCompoundTagAt(precipitateIndex).getString(Chemical.STATE).equals(precipitateState)) return;
			
			// Create list of precipitate mols
			ArrayList<Double> mols = new ArrayList<Double>();
			for (int i = 0; i < ionList.tagCount(); i ++)
			{
				mols.add(NBTHelper.parseFrac(precipitateList.getCompoundTagAt(i).getIntArray(NBTKeys.Chemical.MOLS)));
			}
			
			// Create lists for ion information
			ArrayList<String> ions = new ArrayList<String>();
			ArrayList<Double> ionMols = new ArrayList<Double>();
			for (int i = 0; i < ionList.tagCount(); i ++)
			{
				ions.add(ionList.getCompoundTagAt(i).getString(NBTKeys.Chemical.ION));
				ionMols.add(NBTHelper.parseFrac(ionList.getCompoundTagAt(i).getIntArray(NBTKeys.Chemical.MOLS)));
			}
			
			if (cationMols > 0)
			{
				int[] cationsFormed = NBTHelper.multFrac(precipitateList.getCompoundTagAt(precipitateIndex).getIntArray(NBTKeys.Chemical.MOLS),
						new int[] { cationMols, precipitateMols });
				int cationIndex = ions.indexOf(cation);
				
				if (cationIndex < 0)
				{
					NBTTagCompound cationTag = new NBTTagCompound();
					cationTag.setString(NBTKeys.Chemical.ION, cation);
					cationTag.setInteger(NBTKeys.Chemical.CHARGE, pCharge);
					cationTag.setString(NBTKeys.Chemical.STATE, "aq");
					cationTag.setIntArray(NBTKeys.Chemical.MOLS, cationsFormed);
					ionList.appendTag(cationTag);
				}
				else
				{
					ionList.getCompoundTagAt(cationIndex).setIntArray(NBTKeys.Chemical.MOLS,
							NBTHelper.addFrac(ionList.getCompoundTagAt(cationIndex).getIntArray(NBTKeys.Chemical.MOLS), cationsFormed));
				}
			}
			
			if (anionMols > 0)
			{
				int[] anionsFormed = NBTHelper.multFrac(precipitateList.getCompoundTagAt(precipitateIndex).getIntArray(NBTKeys.Chemical.MOLS),
						new int[] { anionMols, precipitateMols });
				int anionIndex = ions.indexOf(anion);
				
				if (anionIndex < 0)
				{
					NBTTagCompound anionTag = new NBTTagCompound();
					anionTag.setString(NBTKeys.Chemical.ION, anion);
					anionTag.setInteger(NBTKeys.Chemical.CHARGE, nCharge);
					anionTag.setString(NBTKeys.Chemical.STATE, "aq");
					anionTag.setIntArray(NBTKeys.Chemical.MOLS, anionsFormed);
					ionList.appendTag(anionTag);
				}
				else
				{
					ionList.getCompoundTagAt(anionIndex).setIntArray(NBTKeys.Chemical.MOLS,
							NBTHelper.addFrac(ionList.getCompoundTagAt(anionIndex).getIntArray(NBTKeys.Chemical.MOLS), anionsFormed));
				}
			}
			
			precipitateList.removeTag(precipitateIndex);
			LogHelper.info(precipitate);
		}
	}
}
