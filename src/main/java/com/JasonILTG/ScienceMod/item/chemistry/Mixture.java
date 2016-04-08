package com.JasonILTG.ScienceMod.item.chemistry;

import java.util.ArrayList;
import java.util.List;

import com.JasonILTG.ScienceMod.ScienceMod;
import com.JasonILTG.ScienceMod.init.ScienceModItems;
import com.JasonILTG.ScienceMod.item.general.ItemJarred;
import com.JasonILTG.ScienceMod.item.metals.EnumDust;
import com.JasonILTG.ScienceMod.reference.NBTKeys;
import com.JasonILTG.ScienceMod.reference.NBTTypes;
import com.JasonILTG.ScienceMod.reference.chemistry.basics.EnumElement;
import com.JasonILTG.ScienceMod.util.MathUtil;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

/**
 * Item class for mixtures.
 * 
 * @author JasonILTG and syy1125
 */
public class Mixture
		extends ItemJarred
{
	/**
	 * Default constructor.
	 */
	public Mixture()
	{
		setUnlocalizedName("mixture");
		setCreativeTab(ScienceMod.tabCompounds);
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
		if (stack.getItem() == ScienceModItems.mixture) return stack.copy();
		
		// Elements
		if (stack.getItem().equals(ScienceModItems.element))
		{
			int meta = stack.getMetadata();
			
			ItemStack mixtureStack = new ItemStack(ScienceModItems.mixture, stack.stackSize);
			NBTTagCompound mixtureTag = new NBTTagCompound();
			NBTTagList precipitateList = new NBTTagList();
			
			NBTTagCompound elementTag = new NBTTagCompound();
			elementTag.setString(NBTKeys.Chemical.PRECIPITATE, EnumElement.VALUES[meta].getElementSubstance().getFormula());
			
			NBTTagCompound tag = stack.getTagCompound();
			int[] mols = tag == null ? null : tag.getIntArray(NBTKeys.Chemical.MOLS);
			elementTag.setIntArray(NBTKeys.Chemical.MOLS, mols == null ? new int[] { 1, 1 } : mols);
			
			elementTag.setString(NBTKeys.Chemical.STATE, EnumElement.values()[meta].getElementState().getShortName());
			precipitateList.appendTag(elementTag);
			
			mixtureTag.setTag(NBTKeys.Chemical.PRECIPITATES, precipitateList);
			mixtureStack.setTagCompound(mixtureTag);
			return mixtureStack;
		}
		
		// Compounds
		if (stack.isItemEqual(new ItemStack(ScienceModItems.compound)))
		{
			CompoundItem compound = CompoundItem.getCompound(stack.getMetadata());
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
	
	/**
	 * Tries to unparse the given Mixture stack, and returns null if not possible.
	 * 
	 * @param stack The Mixture stack to unparse
	 * @return The unparsed Mixture stack, or null if not possible
	 */
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
		for (EnumElement e : EnumElement.VALUES)
		{
			if (formula.equals(e.getElementSubstance().getFormula()))
			{
				ItemStack elementStack = new ItemStack(ScienceModItems.element, stack.stackSize, e.ordinal());
				NBTTagCompound elementTag = new NBTTagCompound();
				elementTag.setIntArray(NBTKeys.Chemical.MOLS, mols);
				elementStack.setTagCompound(elementTag);
				return elementStack;
			}
		}
		
		for (CompoundItem c : CompoundItem.getCompounds())
		{
			if (formula.equals(c.getChemFormula()))
			{
				ItemStack compoundStack = new ItemStack(c, stack.stackSize);
				NBTTagCompound elementTag = new NBTTagCompound();
				elementTag.setIntArray(NBTKeys.Chemical.MOLS, mols);
				compoundStack.setTagCompound(elementTag);
				return compoundStack;
			}
		}
		
		return null;
	}
	
	/**
	 * Makes a <code>Mixture ItemStack</code> with the specified size that has the specified mols (int arrays) of the specified precipitates.
	 * 
	 * @param size The size of the stack
	 * @param precipitates The precipitates
	 * @param mols The mols
	 * @return The <code>ItemStack</code>
	 */
	public static ItemStack getMixture(int size, String[] precipitates, int[][] mols)
	{
		NBTTagList precipitateList = new NBTTagList();
		for (int i = 0; i < precipitates.length; i++)
		{
			NBTTagCompound precipitateTag = new NBTTagCompound();
			precipitateTag.setString(NBTKeys.Chemical.PRECIPITATE, precipitates[i]);
			precipitateTag.setIntArray(NBTKeys.Chemical.MOLS, mols[i]);
			precipitateList.appendTag(precipitateTag);
		}
		
		NBTTagCompound precipitatesTag = new NBTTagCompound();
		precipitatesTag.setTag(NBTKeys.Chemical.PRECIPITATES, precipitateList);
		ItemStack mixture = new ItemStack(ScienceModItems.mixture, size);
		mixture.setTagCompound(precipitatesTag);
		return mixture;
	}
	
	/**
	 * Makes a <code>Mixture ItemStack</code> with the specified size that has the specified mols (doubles) of the specified precipitates.
	 * 
	 * @param size The size of the stack
	 * @param precipitates The precipitates
	 * @param mols The mols
	 * @return The <code>ItemStack</code>
	 */
	public static ItemStack getMixture(int size, String[] precipitates, double[] mols)
	{
		int[][] molArray = new int[mols.length][];
		for (int i = 0; i < mols.length; i++) molArray[i] = MathUtil.parseFrac(mols[i]);
		return getMixture(size, precipitates, molArray);
	}
	
	/**
	 * Makes a <code>Mixture ItemStack</code> that has the specified mols (int arrays) of the specified precipitates.
	 * 
	 * @param precipitates The precipitates
	 * @param mols The mols
	 * @return The <code>ItemStack</code>
	 */
	public static ItemStack getMixture(String[] precipitates, int[][] mols)
	{
		return getMixture(1, precipitates, mols);
	}
	
	/**
	 * Makes a <code>Mixture ItemStack</code> that has the specified mols (doubles) of the specified precipitates.
	 * 
	 * @param size The size of the stack
	 * @param precipitates The precipitates
	 * @param mols The mols
	 * @return The <code>ItemStack</code>
	 */
	public static ItemStack getMixture(String[] precipitates, double[] mols)
	{
		int[][] molArray = new int[mols.length][];
		for (int i = 0; i < mols.length; i++) molArray[i] = MathUtil.parseFrac(mols[i]);
		return getMixture(1, precipitates, molArray);
	}
	
	@Override
	public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced)
	{
		super.addInformation(stack, playerIn, tooltip, advanced);
		
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
				
				tooltip.add(String.format("%s%.3f mmol %s (%s)", EnumChatFormatting.DARK_GRAY, mols, precipitate, state));
			}
		}
	}

	@Override
	public ItemStack onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn)
	{
		MovingObjectPosition mop = playerIn.rayTrace(200, 1.0F);
		TileEntity lookingAt = worldIn.getTileEntity(mop.getBlockPos());
		if (lookingAt == null || playerIn.isSneaking())
		{
			// If the player isn't looking at a TileEntity, or is sneaking
			
			// Create a list of all solid precipitates
			NBTTagList precipitateList = itemStackIn.getTagCompound().getTagList(NBTKeys.Chemical.PRECIPITATES, NBTTypes.COMPOUND);
			ArrayList<String> solids = new ArrayList<String>();
			ArrayList<Double> mols = new ArrayList<Double>();
			for (int i = 0; i < precipitateList.tagCount(); i++)
			{
				NBTTagCompound precipitateTag = precipitateList.getCompoundTagAt(i);
				if (precipitateTag.getString(NBTKeys.Chemical.STATE).equals("s"))
				{
					solids.add(precipitateTag.getString(NBTKeys.Chemical.PRECIPITATE));
					mols.add(MathUtil.parseFrac(precipitateTag.getIntArray(NBTKeys.Chemical.MOLS)));
				}
			}
			
			// If there aren't exactly 2 precipitates, it can't possibly be an alloy
			if (solids.size() != 2) return itemStackIn;
			
			boolean success = false;
			
			int copper = solids.indexOf(EnumElement.COPPER.getElementSymbol());
			if (copper != -1)
			{
				// If there is copper
				int tin = solids.indexOf(EnumElement.TIN.getElementSymbol());
				if (tin != -1)
				{
					// If there is tin
					double ratio = mols.get(copper) / mols.get(tin);
					double total = mols.get(copper) + mols.get(tin);
					if (ratio > 2.7 && ratio < 3.3 && total >= 1)
					{
						// If the ratio is within 10% of the proper ratio, and there is enough, drop bronze
						playerIn.dropItem(new ItemStack(ScienceModItems.dust, (int) total, EnumDust.BRONZE.ordinal()), false, false);
						success = true;
					}
				}
				else
				{
					int zinc = solids.indexOf(EnumElement.ZINC.getElementSymbol());
					if (zinc != -1)
					{
						// If there is zinc
						double ratio = mols.get(copper) / mols.get(zinc);
						double total = mols.get(copper) + mols.get(zinc);
						if (ratio > 2.7 && ratio < 3.3 && total >= 1)
						{
							// If the ratio is within 10% of the proper ratio, and there is enough, drop brass
							playerIn.dropItem(new ItemStack(ScienceModItems.dust, (int) total, EnumDust.BRASS.ordinal()), false, false);
							success = true;
						}
					}
				}
			}
			else
			{
				int iron = solids.indexOf(EnumElement.IRON.getElementSymbol());
				if (iron != -1)
				{
					// If there is iron
					int nickel = solids.indexOf(EnumElement.NICKEL.getElementSymbol());
					if (nickel != -1)
					{
						// If there is nickel
						double ratio = mols.get(iron) / mols.get(nickel);
						double total = mols.get(iron) / mols.get(nickel);
						if (ratio > 1.8 && ratio < 2.2 && total > 1)
						{
							// If the ratio is within 10% of the proper ratio, and there is enough, drop invar
							playerIn.dropItem(new ItemStack(ScienceModItems.dust, (int) total, EnumDust.INVAR.ordinal()), false, false);
							success = true;
						}
					}
				}
				else
				{
					int silver = solids.indexOf(EnumElement.SILVER.getElementSymbol());
					if (silver != -1)
					{
						// If there is silver
						int gold = solids.indexOf(EnumElement.GOLD.getElementSymbol());
						if (gold != -1)
						{
							// If there is gold
							double ratio = mols.get(silver) / mols.get(gold);
							double total = mols.get(silver) / mols.get(gold);
							if (ratio > .9 && ratio < 1.1 && total > 1)
							{
								// If the ratio is within 10% of the proper ratio, and there is enough, drop electrum
								playerIn.dropItem(new ItemStack(ScienceModItems.dust, (int) total, EnumDust.ELECTRUM.ordinal()), false, false);
								success = true;
							}
						}
					}
				}
			}
			
			if (success)
			{
				// If it was successful
				
				// Consume item if not in creative mode
				if (!playerIn.capabilities.isCreativeMode) itemStackIn.stackSize--;

				// Drop jar
				playerIn.dropItem(new ItemStack(ScienceModItems.jar), false, false);
			}
		}
		
		if (itemStackIn.stackSize == 0) return null;
		return itemStackIn;
	}
}
