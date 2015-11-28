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
		LogHelper.info("Checking Precipitates...");
		NBTTagList ionList = stack.getTagCompound().getTagList(Chemical.IONS, NBTTypes.COMPOUND);
		NBTTagList precipitateList = stack.getTagCompound().getTagList(Chemical.PRECIPITATES, NBTTypes.COMPOUND);
		for (PrecipitateRecipe recipe : PrecipitateRecipe.values())
		{
			recipe.checkPrecipitateFormed(ionList, precipitateList);
		}
		
		stack.getTagCompound().setBoolean(Chemical.STABLE, true);
	}
	
	public static void checkSolubility(ItemStack stack)
	{	
		
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
		AgCl("Ag", "Cl", "AgCl", 0, 1, 1, 1, "s"),
		AgBr("Ag", "Br", "AgBr", 0, 1, 1, 1, "s"),
		AgF("Ag", "F", "AgF", 0, 1, 1, 1, "s"),
		AgI("Ag", "I", "AgI", 1, 0, 1, 1, "s"),
		PbCl2("Pb", "Cl", "PbCl2", 2, 1, 2, 1, "s"),
		PbBr2("Pb", "Br", "PbBr2", 2, 1, 2, 1, "s"),
		PbI2("Pb", "I", "PbI2", 2, 1, 2, 1, "s"),
		Hg2Cl2("Hg2", "Cl", "Hg2Cl2", 0, 1, 2, 1, "s"),
		Hg2Br2("Hg2", "Br", "Hg2Br2", 0, 1, 2, 1, "s"),
		Hg2I2("Hg2", "I", "Hg2I2", 1, 0, 2, 1, "s"),
		BaSO4("Ba", "SO4", "BaSO4", 0, 1, 1, 1, "s"),
		PbSO4("Pb", "SO4", "PbSO4", 2, 1, 1, 1, "s"),
		Ag2SO4("Ag", "SO4", "Ag2SO4", 0, 2, 1, 1, "s"),
		SrSO4("Sr", "SO4", "SrSO4", 0, 1, 1, 1, "s"),
		
		CaOH2("Ca", "OH", "Ca(OH)2", 0, 1, 2, 1, "s"),
		BaOH2("Ba", "OH", "Ba(OH)2", 0, 1, 2, 1, "s"),
		SrOH2("Sr", "OH", "Sr(OH)2", 0, 1, 2, 1, "s"),
		AgOH("Ag", "OH", "AgOH", 0, 1, 1, 1, "s"),
		CuOH("Cu", "OH", "CuOH", 1, 1, 1, 1, "s"),
		TlOH("Tl", "OH", "TlOH", 1, 1, 1, 1, "s"),
		InOH("In", "OH", "InOH", 1, 1, 1, 1, "s"),
		CdOH2("Cd", "OH", "Cd(OH)2", 0, 1, 2, 1, "s"),
		NiOH2("Ni", "OH", "Ni(OH)2", 0, 1, 2, 1, "s"),
		ZnOH2("Zn", "OH", "Zn(OH)2", 0, 1, 2, 1, "s"),
		CuOH2("Cu", "OH", "Cu(OH)2", 2, 1, 2, 1, "s"),
		HgOH2("Hg", "OH", "Hg(OH)2", 0, 1, 2, 1, "s"),
		Hg2OH2("Hg", "OH", "Hg2(OH)2", 0, 1, 2, 1, "s"),
		CrOH2("Cr", "OH", "Cr(OH)2", 2, 1, 2, 1, "s"),
		CoOH2("Co", "OH", "Co(OH)2", 2, 1, 2, 1, "s"),
		FeOH2("Fe", "OH", "Fe(OH)2", 2, 1, 2, 1, "s"),
		SnOH2("Sn", "OH", "Sn(OH)2", 2, 1, 2, 1, "s"),
		PbOH2("Pb", "OH", "Pb(OH)2", 2, 1, 2, 1, "s"),
		PtOH2("Pt", "OH", "Pt(OH)2", 2, 1, 2, 1, "s"),
		ZrOH2("Zr", "OH", "Zr(OH)2", 2, 1, 2, 1, "s"),
		MnOH2("Mn", "OH", "Mn(OH)2", 2, 1, 2, 1, "s"),
		IrOH2("Ir", "OH", "Ir(OH)2", 2, 1, 2, 1, "s"),
		TiOH2("Ti", "OH", "Ti(OH)2", 2, 1, 2, 1, "s"),
		VOH2("V", "OH", "V(OH)2", 2, 1, 2, 1, "s"),
		WOH2("W", "OH", "W(OH)2", 2, 1, 2, 1, "s"),
		AlOH3("Al", "OH", "Al(OH)3", 0, 1, 3, 1, "s"),
		BOH3("B", "OH", "B(OH)3", 0, 1, 3, 1, "s"),
		RhOH3("Rh", "OH", "Rh(OH)3", 0, 1, 3, 1, "s"),
		AuOH3("Au", "OH", "Au(OH)3", 0, 1, 3, 1, "s"),
		TlOH3("Tl", "OH", "Tl(OH)3", 3, 1, 3, 1, "s"),
		InOH3("In", "OH", "In(OH)3", 3, 1, 3, 1, "s"),
		CrOH3("Cr", "OH", "Cr(OH)3", 3, 1, 3, 1, "s"),
		CoOH3("Co", "OH", "Co(OH)3", 3, 1, 3, 1, "s"),
		FeOH3("Fe", "OH", "Fe(OH)3", 3, 1, 3, 1, "s"),
		CeOH3("Ce", "OH", "Ce(OH)3", 3, 1, 3, 1, "s"),
		BiOH3("Bi", "OH", "Bi(OH)3", 3, 1, 3, 1, "s"),
		SbOH3("Sb", "OH", "Sb(OH)3", 3, 1, 3, 1, "s"),
		POH3("P", "OH", "P(OH)3", 3, 1, 3, 1, "s"),
		IrOH3("Ir", "OH", "Ir(OH)3", 3, 1, 3, 1, "s"),
		TiOH3("Ti", "OH", "Ti(OH)3", 3, 1, 3, 1, "s"),
		VOH3("V", "OH", "V(OH)3", 3, 1, 3, 1, "s"),
		UOH3("U", "OH", "U(OH)3", 3, 1, 3, 1, "s"),
		GeOH4("Ge", "OH", "Ge(OH)4", 0, 1, 4, 1, "s"),
		ThOH4("Th", "OH", "Th(OH)4", 0, 1, 4, 1, "s"),
		SnOH4("Sn", "OH", "Sn(OH)4", 4, 1, 4, 1, "s"),
		PbOH4("Pb", "OH", "Pb(OH)4", 4, 1, 4, 1, "s"),
		PtOH4("Pt", "OH", "Pt(OH)4", 4, 1, 4, 1, "s"),
		ZrOH4("Zr", "OH", "Zr(OH)4", 4, 1, 4, 1, "s"),
		MnOH4("Mn", "OH", "Mn(OH)4", 4, 1, 4, 1, "s"),
		CeOH4("Ce", "OH", "Ce(OH)4", 4, 1, 4, 1, "s"),
		IrOH4("Ir", "OH", "Ir(OH)4", 4, 1, 4, 1, "s"),
		TiOH4("Ti", "OH", "Ti(OH)4", 4, 1, 4, 1, "s"),
		VOH4("V", "OH", "V(OH)4", 4, 1, 4, 1, "s"),
		WOH4("W", "OH", "W(OH)4", 4, 1, 4, 1, "s"),
		UOH4("U", "OH", "U(OH)4", 4, 1, 4, 1, "s"),
		BiOH5("Bi", "OH", "Bi(OH)5", 5, 1, 5, 1, "s"),
		SbOH5("Sb", "OH", "Sb(OH)5", 5, 1, 5, 1, "s"),
		POH5("P", "OH", "P(OH)5", 5, 1, 5, 1, "s"),
		VOH5("V", "OH", "V(OH)5", 5, 1, 5, 1, "s"),
		WOH5("W", "OH", "W(OH)5", 5, 1, 5, 1, "s"),
		UOH5("U", "OH", "U(OH)5", 5, 1, 5, 1, "s"),
		
		Ag2S("Ag", "S", "Ag2S", 0, 2, 1, 1, "s"),
		Cu2S("Cu", "S", "Cu2S", 1, 2, 1, 1, "s"),
		Tl2S("Tl", "S", "Tl2S", 1, 2, 1, 1, "s"),
		In2S("In", "S", "In2S", 1, 2, 1, 1, "s"),
		CdS("Cd", "S", "CdS", 0, 1, 1, 1, "s"),
		NiS("Ni", "S", "NiS", 0, 1, 1, 1, "s"),
		ZnS("Zn", "S", "ZnS", 0, 1, 1, 1, "s"),
		CuS("Cu", "S", "CuS", 2, 1, 1, 1, "s"),
		HgS("Hg", "S", "HgS", 0, 1, 1, 1, "s"),
		Hg2S("Hg", "S", "Hg2S", 0, 1, 1, 1, "s"),
		CrS("Cr", "S", "CrS", 2, 1, 1, 1, "s"),
		CoS("Co", "S", "CoS", 2, 1, 1, 1, "s"),
		FeS("Fe", "S", "FeS", 2, 1, 1, 1, "s"),
		SnS("Sn", "S", "SnS", 2, 1, 1, 1, "s"),
		PbS("Pb", "S", "PbS", 2, 1, 1, 1, "s"),
		PtS("Pt", "S", "PtS", 2, 1, 1, 1, "s"),
		ZrS("Zr", "S", "ZrS", 2, 1, 1, 1, "s"),
		MnS("Mn", "S", "MnS", 2, 1, 1, 1, "s"),
		IrS("Ir", "S", "IrS", 2, 1, 1, 1, "s"),
		TiS("Ti", "S", "TiS", 2, 1, 1, 1, "s"),
		VS("V", "S", "VS", 2, 1, 1, 1, "s"),
		WS("W", "S", "WS", 2, 1, 1, 1, "s"),
		Al2S3("Al", "S", "Al2S3", 0, 2, 3, 1, "s"),
		B2S3("B", "S", "B2S3", 0, 2, 3, 1, "s"),
		Rh2S3("Rh", "S", "Rh2S3", 0, 2, 3, 1, "s"),
		Au2S3("Au", "S", "Au2S3", 0, 2, 3, 1, "s"),
		Tl2S3("Tl", "S", "Tl2S3", 3, 2, 3, 1, "s"),
		In2S3("In", "S", "In2S3", 3, 2, 3, 1, "s"),
		Cr2S3("Cr", "S", "Cr2S3", 3, 2, 3, 1, "s"),
		Co2S3("Co", "S", "Co2S3", 3, 2, 3, 1, "s"),
		Fe2S3("Fe", "S", "Fe2S3", 3, 2, 3, 1, "s"),
		Ce2S3("Ce", "S", "Ce2S3", 3, 2, 3, 1, "s"),
		Bi2S3("Bi", "S", "Bi2S3", 3, 2, 3, 1, "s"),
		Sb2S3("Sb", "S", "Sb2S3", 3, 2, 3, 1, "s"),
		P2S3("P", "S", "P2S3", 3, 2, 3, 1, "s"),
		Ir2S3("Ir", "S", "Ir2S3", 3, 2, 3, 1, "s"),
		Ti2S3("Ti", "S", "Ti2S3", 3, 2, 3, 1, "s"),
		V2S3("V", "S", "V2S3", 3, 2, 3, 1, "s"),
		U2S3("U", "S", "U2S3", 3, 2, 3, 1, "s"),
		GeS2("Ge", "S", "GeS2", 0, 1, 2, 1, "s"),
		ThS2("Th", "S", "ThS2", 0, 1, 2, 1, "s"),
		SnS2("Sn", "S", "SnS2", 4, 1, 2, 1, "s"),
		PbS2("Pb", "S", "PbS2", 4, 1, 2, 1, "s"),
		PtS2("Pt", "S", "PtS2", 4, 1, 2, 1, "s"),
		ZrS2("Zr", "S", "ZrS2", 4, 1, 2, 1, "s"),
		MnS2("Mn", "S", "MnS2", 4, 1, 2, 1, "s"),
		CeS2("Ce", "S", "CeS2", 4, 1, 2, 1, "s"),
		IrS2("Ir", "S", "IrS2", 4, 1, 2, 1, "s"),
		TiS2("Ti", "S", "TiS2", 4, 1, 2, 1, "s"),
		VS2("V", "S", "VS2", 4, 1, 2, 1, "s"),
		WS2("W", "S", "WS2", 4, 1, 2, 1, "s"),
		US2("U", "S", "US2", 4, 1, 2, 1, "s"),
		Bi2S5("Bi", "S", "Bi2S5", 5, 2, 5, 1, "s"),
		Sb2S5("Sb", "S", "Sb2S5", 5, 2, 5, 1, "s"),
		P2S5("P", "S", "P2S5", 5, 2, 5, 1, "s"),
		V2S5("V", "S", "V2S5", 5, 2, 5, 1, "s"),
		W2S5("W", "S", "W2S5", 5, 2, 5, 1, "s"),
		U2S5("U", "S", "U2S5", 5, 2, 5, 1, "s");
		
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
}
