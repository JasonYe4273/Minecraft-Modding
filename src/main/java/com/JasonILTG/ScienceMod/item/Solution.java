package com.JasonILTG.ScienceMod.item;

import java.util.ArrayList;
import java.util.List;

import com.JasonILTG.ScienceMod.creativetabs.ScienceCreativeTabs;
import com.JasonILTG.ScienceMod.init.ScienceModItems;
import com.JasonILTG.ScienceMod.item.general.ItemJarred;
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
		NBTHelper.checkFracZero(stack, new String[]{ Chemical.IONS, Chemical.PRECIPITATES }, Chemical.MOLS);
	}
	
	public static ItemStack parseItemStackSolution(ItemStack stack)
	{
		//Null check
		if( stack == null ) return null;
		
		//Solutions
		if( stack.isItemEqual(new ItemStack(ScienceModItems.solution)) ) return stack.copy();
		
		//Water
		if( stack.isItemEqual(new ItemStack(ScienceModItems.water)) )
		{
			ItemStack solutionStack = new ItemStack(ScienceModItems.solution, stack.stackSize);
			NBTTagCompound solutionTag = new NBTTagCompound();
			solutionTag.setTag(Chemical.IONS, new NBTTagList());
			solutionTag.setTag(Chemical.PRECIPITATES, new NBTTagList());
			solutionStack.setTagCompound(solutionTag);
			return solutionStack;
		}
		
		//Everything else
		return null;
	}
	
	@Override
	public void addInformation(ItemStack stack, EntityPlayer playerIn, List tooltip, boolean advanced)
	{
		if (stack.getTagCompound() != null)
		{
			NBTTagList ionTagList = stack.getTagCompound().getTagList(Chemical.IONS, NBTTypes.COMPOUND);
			NBTTagList precipitateTagList = stack.getTagCompound().getTagList(Chemical.PRECIPITATES, NBTTypes.COMPOUND);
			
			for (int i = 0; i < ionTagList.tagCount(); i ++)
			{
				NBTTagCompound ionTagCompound = ionTagList.getCompoundTagAt(i);
				double mols = NBTHelper.parseFrac(ionTagCompound.getIntArray(Chemical.MOLS));
				String ion = ionTagCompound.getString(Chemical.ION);
				int charge = ionTagCompound.getInteger(Chemical.CHARGE);
				String state = ionTagCompound.getString(Chemical.STATE);
				
				tooltip.add(String.format("%s%3f mol %s (%s) (%s)", EnumChatFormatting.DARK_GRAY, mols, ion, String.valueOf(charge), state));
			}
			
			for (int i = 0; i < precipitateTagList.tagCount(); i ++)
			{
				NBTTagCompound precipitateTagCompound = precipitateTagList.getCompoundTagAt(i);
				double mols = NBTHelper.parseFrac(precipitateTagCompound.getIntArray(Chemical.MOLS));
				String precipitate = precipitateTagCompound.getString(Chemical.PRECIPITATE);
				String state = precipitateTagCompound.getString(Chemical.STATE);
				
				tooltip.add(String.format("%s%3f mol %s (%s)", EnumChatFormatting.DARK_GRAY, mols, precipitate, state));
			}
		}
	}
	
	private static void checkPrecipitates(ItemStack stack)
	{
		// Check that it is a solution
		if (!stack.isItemEqual(new ItemStack(ScienceModItems.solution))) return;
		
		// Check if it's already stable
		if (stack.getTagCompound().getBoolean(Chemical.STABLE)) return;
		
		NBTTagList ionList = stack.getTagCompound().getTagList(Chemical.IONS, NBTTypes.COMPOUND);
		NBTTagList precipitateList = stack.getTagCompound().getTagList(Chemical.PRECIPITATES, NBTTypes.COMPOUND);
		for (SolubleRecipe recipe : SolubleRecipe.values())
		{
			recipe.checkPrecipitateDissolved(ionList, precipitateList);
		}
		for (PrecipitateRecipe recipe : PrecipitateRecipe.values())
		{
			recipe.checkPrecipitateFormed(ionList, precipitateList);
		}
		
		stack.getTagCompound().setBoolean(Chemical.STABLE, true);
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
		Fe2CrO43("Fe", "CrO4", "Fe2(CrO4)3", 3, 2, 3, 1, "s")
		;
		
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
				ions.add(ionList.getCompoundTagAt(i).getString(Chemical.ION));
			}
			
			// Get the index of the cation
			int cationIndex = ions.lastIndexOf(cation);
			if (transitionCharge > 0)
			{
				// If needed, check for transition charge
				ArrayList<Integer> charges = new ArrayList<Integer>();
				for (int i = 0; i < ionList.tagCount(); i ++)
				{
					charges.add(ionList.getCompoundTagAt(i).getInteger(Chemical.CHARGE));
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
				mols.add(NBTHelper.parseFrac(ionList.getCompoundTagAt(i).getIntArray(Chemical.MOLS)));
			}
			
			// Create lists for precipitate information
			ArrayList<String> precipitates = new ArrayList<String>();
			ArrayList<Double> precipitateMols = new ArrayList<Double>();
			for (int i = 0; i < precipitateList.tagCount(); i ++)
			{
				precipitates.add(precipitateList.getCompoundTagAt(i).getString(Chemical.PRECIPITATE));
				precipitateMols.add(NBTHelper.parseFrac(precipitateList.getCompoundTagAt(i).getIntArray(Chemical.MOLS)));
			}
			
			// Calculate the limiting reactant
			double cationBaseMols = mols.get(cationIndex) / cationMols;
			double anionBaseMols = mols.get(anionIndex) / anionMols;
			double precipitateMolsFormed;
			
			if (cationBaseMols > anionBaseMols)
			{
				// Anion is limiting -> delete it, and decrease mols of cation
				ionList.getCompoundTagAt(cationIndex).setIntArray(Chemical.MOLS, NBTHelper.parseFrac(cationMols * (cationBaseMols - anionBaseMols)));
				ionList.removeTag(anionIndex);
				
				precipitateMolsFormed = this.precipitateMols * anionBaseMols;
			}
			else if (cationBaseMols < anionBaseMols)
			{
				// Cation is limiting -> delete it, and decrease mols of anion
				ionList.getCompoundTagAt(anionIndex).setIntArray(Chemical.MOLS, NBTHelper.parseFrac(anionMols * (anionBaseMols - cationBaseMols)));
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
				precipitate.setString(Chemical.PRECIPITATE, this.precipitate);
				precipitate.setIntArray(Chemical.MOLS, NBTHelper.parseFrac(precipitateMolsFormed));
				precipitate.setString(Chemical.STATE, precipitateState);
				precipitateList.appendTag(precipitate);
			}
			else
			{
				// If it does already exist, just increase the mols of theold tag
				precipitateList.getCompoundTagAt(precipitateIndex).setIntArray(Chemical.MOLS,
						NBTHelper.parseFrac(precipitateMols.get(precipitateIndex) + precipitateMolsFormed));
			}
		}
	}
	
	public enum SolubleRecipe
	{
		H2("H2", "g", "H", 1, "", 0, 1, 2, 0),
		F2("F2", "g", "", 0, "F", -1, 1, 0, 2),
		Cl2("Cl2", "g", "", 0, "Cl", -1, 1, 0, 2),
		Ag("Ag", "s", "Ag", 1, "", 0, 1, 1, 0)
		;

		private String precipitate;
		private String precipitateState;
		private String cation;
		private int pCharge;
		private String anion;
		private int nCharge;
		private int precipitateMols;
		private int cationMols;
		private int anionMols;
		
		private SolubleRecipe(String precipitate, String precipitateState, String cation, int pCharge, String anion, int nCharge, int precipitateMols, int cationMols, int anionMols)
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
			//Null check
			if (ionList == null || precipitateList == null) return;
			
			// Create list of precipitate names
			ArrayList<String> precipitates = new ArrayList<String>();
			for (int i = 0; i < precipitateList.tagCount(); i ++)
			{
				precipitates.add(precipitateList.getCompoundTagAt(i).getString(Chemical.PRECIPITATE));
			}
			
			// Get the index of the precipitate
			int precipitateIndex = precipitates.indexOf(precipitate);
			if (precipitateIndex < 0) return;
			
			// Create list of precipitate mols
			ArrayList<Double> mols = new ArrayList<Double>();
			for (int i = 0; i < ionList.tagCount(); i ++)
			{
				mols.add(NBTHelper.parseFrac(precipitateList.getCompoundTagAt(i).getIntArray(Chemical.MOLS)));
			}
			
			// Create lists for ion information
			ArrayList<String> ions = new ArrayList<String>();
			ArrayList<Double> ionMols = new ArrayList<Double>();
			for (int i = 0; i < ionList.tagCount(); i ++)
			{
				ions.add(ionList.getCompoundTagAt(i).getString(Chemical.ION));
				ionMols.add(NBTHelper.parseFrac(ionList.getCompoundTagAt(i).getIntArray(Chemical.MOLS)));
			}
			
			if (cationMols > 0)
			{
				int[] cationsFormed = NBTHelper.multFrac(precipitateList.getCompoundTagAt(precipitateIndex).getIntArray(Chemical.MOLS), new int[]{ cationMols, precipitateMols });
				int cationIndex = ions.indexOf(cation);
				
				if (cationIndex < 0)
				{
					NBTTagCompound cationTag = new NBTTagCompound();
					cationTag.setString(Chemical.ION, cation);
					cationTag.setInteger(Chemical.CHARGE, pCharge);
					cationTag.setString(Chemical.STATE, "aq");
					cationTag.setIntArray(Chemical.MOLS, cationsFormed);
					ionList.appendTag(cationTag);
				}
				else
				{
					ionList.getCompoundTagAt(cationIndex).setIntArray(Chemical.MOLS, NBTHelper.addFrac(ionList.getCompoundTagAt(cationIndex).getIntArray(Chemical.MOLS), cationsFormed));
				}
			}
			
			if (anionMols > 0)
			{
				int[] anionsFormed = NBTHelper.multFrac(precipitateList.getCompoundTagAt(precipitateIndex).getIntArray(Chemical.MOLS), new int[]{ anionMols, precipitateMols });
				int anionIndex = ions.indexOf(anion);
				
				if(anionIndex < 0)
				{
					NBTTagCompound anionTag = new NBTTagCompound();
					anionTag.setString(Chemical.ION, anion);
					anionTag.setInteger(Chemical.CHARGE, nCharge);
					anionTag.setString(Chemical.STATE, "aq");
					anionTag.setIntArray(Chemical.MOLS, anionsFormed);
					ionList.appendTag(anionTag);
				}
				else
				{
					ionList.getCompoundTagAt(anionIndex).setIntArray(Chemical.MOLS, NBTHelper.addFrac(ionList.getCompoundTagAt(anionIndex).getIntArray(Chemical.MOLS), anionsFormed));
				}
			}
			
			precipitateList.removeTag(precipitateIndex);
			LogHelper.info(precipitate);
		}
	}
}
