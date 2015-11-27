package com.JasonILTG.ScienceMod.item;

import java.util.ArrayList;
import java.util.List;

import com.JasonILTG.ScienceMod.creativetabs.ScienceCreativeTabs;
import com.JasonILTG.ScienceMod.init.ScienceModItems;
import com.JasonILTG.ScienceMod.item.general.ItemJarred;
import com.JasonILTG.ScienceMod.reference.NBTKeys;
import com.JasonILTG.ScienceMod.reference.NBTTypes;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;

public class Solution extends ItemJarred
{
	public Solution()
	{
		setUnlocalizedName("solution");
		setCreativeTab(ScienceCreativeTabs.tabCompounds);
		setContainerItem(ScienceModItems.jar);
	}
	
	public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected)
	{
		checkPrecipitates(stack);
	}
	
	@Override
	public void onCreated(ItemStack stack, World worldIn, EntityPlayer playerIn)
	{
		checkPrecipitates(stack);
		stack.getIsItemStackEqual(stack);
	}
	
	@Override
	public void addInformation(ItemStack stack, EntityPlayer playerIn, List tooltip, boolean advanced)
	{
		if(stack.getTagCompound() != null)
		{
			NBTTagList ionTagList = stack.getTagCompound().getTagList(NBTKeys.IONS, NBTTypes.COMPOUND);
			NBTTagList precipitateTagList = stack.getTagCompound().getTagList(NBTKeys.PRECIPITATES, NBTTypes.COMPOUND);
			
			for( int i = 0; i < ionTagList.tagCount(); i++ )
			{
				NBTTagCompound ionTagCompound = ionTagList.getCompoundTagAt(i);
				double mols = ionTagCompound.getDouble(NBTKeys.MOLS);
				String ion = ionTagCompound.getString(NBTKeys.ION);
				int charge = ionTagCompound.getInteger(NBTKeys.CHARGE);
				String state = ionTagCompound.getString(NBTKeys.STATE);
				
				tooltip.add(String.format("%s%3f mol %s (%s) (%s)", EnumChatFormatting.DARK_GRAY, mols, ion, String.valueOf(charge), state));
			}
			
			for( int i = 0; i < precipitateTagList.tagCount(); i++ )
			{
				NBTTagCompound precipitateTagCompound = precipitateTagList.getCompoundTagAt(i);
				double mols = precipitateTagCompound.getDouble(NBTKeys.MOLS);
				String precipitate = precipitateTagCompound.getString(NBTKeys.PRECIPITATE);
				String state = precipitateTagCompound.getString(NBTKeys.STATE);

				tooltip.add(String.format("%s%3f mol %s (%s)", EnumChatFormatting.DARK_GRAY, mols, precipitate, state));
			}
		}
	}
	
	public static void checkPrecipitates(ItemStack stack)
	{
		//Check that it is a solution
		if(!stack.isItemEqual(new ItemStack(ScienceModItems.solution))) return;
		
		//Check if it's already stable
		if(stack.getTagCompound().getBoolean(NBTKeys.STABLE)) return;
		
		NBTTagList ionList = stack.getTagCompound().getTagList(NBTKeys.IONS, NBTTypes.COMPOUND);
		NBTTagList precipitateList = stack.getTagCompound().getTagList(NBTKeys.PRECIPITATES, NBTTypes.COMPOUND);
		for( PrecipitateRecipe recipe : PrecipitateRecipe.values() )
		{
			recipe.checkPrecipitateFormed(ionList, precipitateList);
		}
		
		stack.getTagCompound().setBoolean(NBTKeys.STABLE, true);
	}
	
	public static void checkSolubility(ItemStack stack)
	{
		
	}
	
	public enum PrecipitateRecipe
	{
		
		Water("H", "OH", "H2O", 0, 1, 1, 1, "l"),
		AgCl("Ag", "Cl", "AgCl", 0, 1, 1, 1, "s"),
		AgBr("Ag", "Br", "AgBr", 0, 1, 1, 1, "s"),
		AgI("Ag", "I", "AgI", 1, 0, 1, 1, "s"),
		PbCl2("Pb", "Cl", "PbCl2", 2, 1, 2, 1, "s"),
		PbBr2("Pb", "Br", "PbBr2", 2, 1, 2, 1, "s"),
		PbI2("Pb", "I", "PbI2", 2, 1, 2, 1, "s"),
		Hg2Cl2("Hg2", "Cl", "Hg2Cl2", 0, 1, 2, 1, "s"),
		Hg2Br2("Hg2", "Br", "Hg2Br2", 0, 1, 2, 1, "s"),
		Hg2I2("Hg2", "I", "Hg2I2", 1, 0, 2, 1, "s");
		
		private String cation;
		private String anion;
		private String precipitate;
		private int transitionCharge;
		private int cationMols;
		private int anionMols;
		private int precipitateMols;
		private String precipitateState;
		
		private PrecipitateRecipe(String cation, String anion, String precipitate, int transitionCharge, int cationMols, int anionMols, int precipitateMols, String precipitateState)
		{
			//Ion and precipitate names
			this.cation = cation;
			this.anion = anion;
			this.precipitate = precipitate;
			//transition metal charge to distinguis between different possible charges (0 if not needed)
			this.transitionCharge = transitionCharge;
			//Number of moles in one reaction
			this.cationMols = cationMols;
			this.anionMols = anionMols;
			this.precipitateMols = precipitateMols;
			//State of the precipitate
			this.precipitateState = precipitateState;
		}
		
		public void checkPrecipitateFormed(NBTTagList ionList, NBTTagList precipitateList)
		{
			//Create list of ion names
			ArrayList<String> ions = new ArrayList<String>();
			for( int i = 0; i < ionList.tagCount(); i++ )
			{
				ions.add(ionList.getCompoundTagAt(i).getString(NBTKeys.ION));
			}
			
			//Get the index of the cation
			int cationIndex = ions.lastIndexOf(cation);
			if( transitionCharge > 0 )
			{
				//If needed, check for transition charge
				ArrayList<Integer> charges = new ArrayList<Integer>();
				for( int i = 0; i < ionList.tagCount(); i++ )
				{
					charges.add(ionList.getCompoundTagAt(i).getInteger(NBTKeys.CHARGE));
				}
				
				while( cationIndex > -1 )
				{
					if( cationIndex > -1 && charges.get(cationIndex) == transitionCharge )
					{
						break;
					}
					cationIndex = ions.lastIndexOf(cation);
				}
			}
			
			//Get the index anion, and check that both ions are present
			if( cationIndex < 0 ) return;
			int anionIndex = ions.indexOf(anion);
			if( anionIndex < 0 ) return;
			
			//Create list of ion mols
			ArrayList<Double> mols = new ArrayList<Double>();
			for( int i = 0; i < ionList.tagCount(); i++ )
			{
				mols.add(ionList.getCompoundTagAt(i).getDouble(NBTKeys.MOLS));
			}
			
			//Create lists for precipitate information
			ArrayList<String> precipitates = new ArrayList<String>();
			ArrayList<Double> precipitateMols = new ArrayList<Double>();
			for( int i = 0; i < precipitateList.tagCount(); i++ )
			{
				precipitates.add(precipitateList.getCompoundTagAt(i).getString(NBTKeys.PRECIPITATE));
				precipitateMols.add(precipitateList.getCompoundTagAt(i).getDouble(NBTKeys.MOLS));
			}
			
			//Calculate the limiting reactant
			double cationBaseMols = mols.get(cationIndex) / cationMols;
			double anionBaseMols = mols.get(anionIndex) / anionMols;
			double precipitateMolsFormed;
			
			if(cationBaseMols > anionBaseMols)
			{
				//Anion is limiting -> delete it, and decrease mols of cation
				ionList.getCompoundTagAt(cationIndex).setDouble(NBTKeys.MOLS, cationMols * (cationBaseMols - anionBaseMols));
				ionList.removeTag(anionIndex);
				
				precipitateMolsFormed = this.precipitateMols * anionBaseMols;
			}
			else if(cationBaseMols < anionBaseMols)
			{
				//Cation is limiting -> delete it, and decrease mols of anion
				ionList.getCompoundTagAt(anionIndex).setDouble(NBTKeys.MOLS, anionMols * (anionBaseMols - cationBaseMols));
				ionList.removeTag(cationIndex);
				
				precipitateMolsFormed = this.precipitateMols * cationBaseMols;
			}
			else
			{
				//Otherwise, both are fully used
				precipitateMolsFormed = this.precipitateMols * anionBaseMols;
				
				//Delete both tags in reverse order to preserve indices
				if( cationIndex > anionIndex )
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
			
			//Create new precipitate tag
			NBTTagCompound precipitate;
			int precipitateIndex = precipitates.indexOf(this.precipitate);
			
			if( precipitateIndex < 0 )
			{
				//If the precipitate doesn't already exist in solution, make a new tag
				precipitate = new NBTTagCompound();
				precipitate.setString(NBTKeys.PRECIPITATE, this.precipitate);
				precipitate.setDouble(NBTKeys.MOLS, precipitateMolsFormed);
				precipitate.setString(NBTKeys.STATE, precipitateState);
				precipitateList.appendTag(precipitate);
			}
			else
			{
				//If it does already exist, just increase the mols of theold tag
				precipitateList.getCompoundTagAt(precipitateIndex).setDouble(NBTKeys.MOLS, precipitateMols.get(precipitateIndex) + precipitateMolsFormed);
			}
		}
	}
}