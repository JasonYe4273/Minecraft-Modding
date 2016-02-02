package com.JasonILTG.ScienceMod.item;

import java.util.List;

import com.JasonILTG.ScienceMod.creativetabs.ScienceCreativeTabs;
import com.JasonILTG.ScienceMod.init.ScienceModItems;
import com.JasonILTG.ScienceMod.item.compounds.Compound;
import com.JasonILTG.ScienceMod.item.compounds.CompoundItem;
import com.JasonILTG.ScienceMod.item.general.ItemJarred;
import com.JasonILTG.ScienceMod.reference.NBTKeys;
import com.JasonILTG.ScienceMod.reference.NBTTypes;
import com.JasonILTG.ScienceMod.reference.chemistry.Element;
import com.JasonILTG.ScienceMod.util.MathUtil;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumChatFormatting;

/**
 * Item class for mixtures.
 * 
 * @author JasonILTG and syy1125
 */
public class Mixture extends ItemJarred
{
	/**
	 * Default constructor.
	 */
	public Mixture()
	{
		setUnlocalizedName("mixture");
		setCreativeTab(ScienceCreativeTabs.tabCompounds);
	}

	@Override
	public boolean getHasSubtypes()
	{
		return false;
	}
	
	@Override
	public int getNumSubtypes()
	{
		return 1;
	}
	
	/**
	 * Checks the components of the mixture ItemStack.
	 * 
	 * @param stack The stack
	 */
	public static void check(ItemStack stack)
	{
		MathUtil.checkFracZero(stack, new String[] { NBTKeys.Chemical.PRECIPITATES }, NBTKeys.Chemical.MOLS);
	}
	
	/**
	 * Tries to parse the ItemStack into a mixture ItemStack. If unsuccessful, returns null.
	 * 
	 * @param stack The ItemStack to be parsed
	 * @return Parsed mixture ItemStack if possible, null otherwise
	 */
	public static ItemStack parseItemStackMixture(ItemStack stack)
	{
		// Null check
		if (stack == null) return null;
		
		// Mixtures
		if (stack.isItemEqual(new ItemStack(ScienceModItems.mixture))) return stack.copy();
		
		Item item = stack.getItem();
		
		// Elements
		if ((new ItemStack(item)).isItemEqual(new ItemStack(ScienceModItems.element)))
		{
			int meta = stack.getMetadata();
			
			ItemStack mixtureStack = new ItemStack(ScienceModItems.mixture, stack.stackSize);
			NBTTagCompound mixtureTag = new NBTTagCompound();
			NBTTagList precipitateList = new NBTTagList();
			
			NBTTagCompound elementTag = new NBTTagCompound();
			elementTag.setString(NBTKeys.Chemical.PRECIPITATE, Element.VALUES[meta].getElementCompound());
			
			NBTTagCompound tag = stack.getTagCompound();
			int[] mols = tag == null ? null : tag.getIntArray(NBTKeys.Chemical.MOLS);
			elementTag.setIntArray(NBTKeys.Chemical.MOLS, mols == null ? new int[] { 1, 1 } : mols);
			
			elementTag.setString(NBTKeys.Chemical.STATE, Element.values()[meta].getElementState().getShortName());
			precipitateList.appendTag(elementTag);
			
			mixtureTag.setTag(NBTKeys.Chemical.PRECIPITATES, precipitateList);
			mixtureStack.setTagCompound(mixtureTag);
			return mixtureStack;
		}
		
		// Compounds
		if (item instanceof CompoundItem)
		{
			CompoundItem compound = (CompoundItem) item;
			ItemStack mixtureStack = new ItemStack(ScienceModItems.mixture, stack.stackSize);
			NBTTagCompound mixtureTag = new NBTTagCompound();
			NBTTagList precipitateList = new NBTTagList();
			
			NBTTagCompound compoundTag = new NBTTagCompound();
			compoundTag.setString(NBTKeys.Chemical.PRECIPITATE, compound.getChemFormula());
			
			NBTTagCompound tag = stack.getTagCompound();
			int[] mols = tag == null ? null : tag.getIntArray(NBTKeys.Chemical.MOLS);
			compoundTag.setIntArray(NBTKeys.Chemical.MOLS, mols == null ? new int[] { 1, 1 } : mols);
			compoundTag.setString(NBTKeys.Chemical.STATE, compound.getState());
			precipitateList.appendTag(compoundTag);
			
			mixtureTag.setTag(NBTKeys.Chemical.PRECIPITATES, precipitateList);
			mixtureStack.setTagCompound(mixtureTag);
			return mixtureStack;
		}
		
		// Everything else
		return null;
	}
	
	public static ItemStack unparseItemStackMixture(ItemStack stack)
	{
		// Null check
		if (stack == null) return null;
		
		// Not a mixture; can't unparse
		if (!stack.isItemEqual(new ItemStack(ScienceModItems.mixture))) return null;
		
		NBTTagCompound tag = stack.getTagCompound();
		NBTTagList precipitates = tag.getTagList(NBTKeys.Chemical.PRECIPITATES, NBTTypes.COMPOUND);
		
		// No precipitates or more than one
		if (precipitates == null || precipitates.tagCount() != 1) return null;
		
		NBTTagCompound precipitate = precipitates.getCompoundTagAt(0);
		int[] mols = precipitate.getIntArray(NBTKeys.Chemical.MOLS);
		
		String formula = precipitate.getString(NBTKeys.Chemical.PRECIPITATE);
		for (Element e : Element.VALUES)
		{
			if (formula.equals(e.getElementCompound()))
			{
				ItemStack elementStack = new ItemStack(ScienceModItems.element, stack.stackSize, e.ordinal());
				NBTTagCompound elementTag = new NBTTagCompound();
				elementTag.setIntArray(NBTKeys.Chemical.MOLS, mols);
				elementStack.setTagCompound(elementTag);
				return elementStack;
			}
		}
		
		for (Compound c : Compound.VALUES)
		{
			if (formula.equals(c.getFormula()))
			{
				ItemStack compoundStack = new ItemStack(c.getCompoundItem(), stack.stackSize);
				NBTTagCompound elementTag = new NBTTagCompound();
				elementTag.setIntArray(NBTKeys.Chemical.MOLS, mols);
				compoundStack.setTagCompound(elementTag);
				return compoundStack;
			}
		}
		
		return null;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void addInformation(ItemStack stack, EntityPlayer playerIn, List tooltip, boolean advanced)
	{
		if (stack.getTagCompound() != null)
		{
			// Null check
			
			// Add information for precipitates
			NBTTagList tagList = stack.getTagCompound().getTagList(NBTKeys.Chemical.PRECIPITATES, NBTTypes.COMPOUND);
			for (int i = 0; i < tagList.tagCount(); i ++)
			{
				NBTTagCompound tagCompound = tagList.getCompoundTagAt(i);
				double mols = MathUtil.parseFrac(tagCompound.getIntArray(NBTKeys.Chemical.MOLS));
				String precipitate = tagCompound.getString(NBTKeys.Chemical.PRECIPITATE);
				String state = tagCompound.getString(NBTKeys.Chemical.STATE);
				
				tooltip.add(String.format("%s%.3f mol %s (%s)", EnumChatFormatting.DARK_GRAY, mols, precipitate, state));
			}
		}
	}
}
