package com.JasonILTG.ScienceMod.item.chemistry;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import com.JasonILTG.ScienceMod.init.ScienceModItems;
import com.JasonILTG.ScienceMod.item.general.ItemJarred;
import com.JasonILTG.ScienceMod.reference.NBTKeys;
import com.JasonILTG.ScienceMod.reference.Reference;
import com.JasonILTG.ScienceMod.reference.chemistry.basics.MatterState;
import com.JasonILTG.ScienceMod.util.LogHelper;
import com.JasonILTG.ScienceMod.util.MathUtil;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Wrapper class for compounds.
 * 
 * @author JasonILTG and syy1125
 */
public class CompoundItem
		extends ItemJarred
{
	/** A HashMap from chemical formulas to <code>CompoundItem</code>s */
	private static final HashMap<String, CompoundItem> compoundMap = new HashMap<String, CompoundItem>();
	/** An ArrayList with all <code>CompoundItem</code>s in order */
	private static final ArrayList<CompoundItem> compoundList = new ArrayList<CompoundItem>();
	
	/** The chemical formula of the compound */
	protected String formula;
	/** Default state of matter */
	protected MatterState state;
	/** The metadata of the compound */
	protected int ordinal;
	
	/**
	 * Constructor for compound items.
	 * 
	 * @param formula The compound's formula
	 * @param state The compound's default state
	 */
	public CompoundItem(String formula, MatterState state)
	{
		super();
		this.formula = formula;
		this.state = state;
		setHasSubtypes(true);
		setUnlocalizedName("compound");
		
		ordinal = compoundList.size();
		compoundMap.put(formula, this);
		compoundList.add(this);
		LogHelper.info(compoundList.size());
	}
	
	/**
	 * Returns an ItemStack with the given size the compound with the given formula.
	 * 
	 * @param formula The formula
	 * @param stackSize The size
	 * @return The ItemStack
	 */
	public static ItemStack getCompoundStack(String formula, int stackSize)
	{
		return getCompoundStack(formula, stackSize, 1);
	}
	
	/**
	 * Returns an ItemStack with the given size the compound with the given formula.
	 * 
	 * @param formula The formula
	 * @param stackSize The size
	 * @param mols The number of mols of compound
	 * @return The ItemStack
	 */
	public static ItemStack getCompoundStack(String formula, int stackSize, double mols)
	{
		ItemStack toReturn = new ItemStack(ScienceModItems.compound, stackSize, getCompoundItem(formula).ordinal);
		NBTTagCompound tag = new NBTTagCompound();
		tag.setIntArray(NBTKeys.Chemical.MOLS, MathUtil.parseFrac(mols));
		toReturn.setTagCompound(tag);
		return toReturn;
	}
	
	/**
	 * Returns the <code>CompoundItem</code> with the given formula.
	 * 
	 * @param formula The formula
	 * @return The <code>CompoundItem</code>
	 */
	public static CompoundItem getCompoundItem(String formula)
	{
		return compoundMap.get(formula);
	}
	
	public static Set<String> getFormulas()
	{
		return compoundMap.keySet();
	}
	
	/**
	 * @return A collection of all <code>CompoundItem</code>s
	 */
	public static Collection<CompoundItem> getCompounds()
	{
		return compoundMap.values();
	}
	
	/**
	 * Returns the ordinal (metadata) of the given <code>CompoundItem</code>.
	 * 
	 * @param compound The <code>CompoundItem</code>
	 * @return The ordinal
	 */
	public static int ordinal(CompoundItem compound)
	{
		return compound.ordinal;
	}
	
	@Override
	public String getUnlocalizedName(ItemStack stack)
	{
		return String.format("item.%s%s.%s", Reference.RESOURCE_PREFIX, "compound", compoundList.get(stack.getMetadata()).formula);
	}
	
	/**
	 * Adds items with the same ID, but different meta (eg: dye) to a list.
	 * 
	 * @param item The Item to get the subItems of
	 * @param creativeTab The Creative Tab the items belong to
	 * @param list The List of ItemStacks to add to
	 */
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item item, CreativeTabs creativeTab, List<ItemStack> list)
	{
		for (int meta = 0; meta < compoundList.size(); meta ++)
		{
			list.add(new ItemStack(this, 1, meta));
		}
	}
	
	@Override
	public boolean getHasSubtypes()
	{
		return true;
	}
	
	@Override
	public int getNumSubtypes()
	{
		return compoundList.size();
	}
	
	public static CompoundItem getCompound(int ordinal)
	{
		try {
			return compoundList.get(ordinal);
		}
		catch (IndexOutOfBoundsException ex) {
			return null;
		}
	}
	
	/**
	 * @return The chemical formula of the compound
	 */
	public String getChemFormula()
	{
		return formula;
	}
	
	/**
	 * @return The default state of matter of the compound
	 */
	public String getState()
	{
		return state.getShortName();
	}
	
	/**
	 * Allows items to add custom lines of information to the mouseover description.
	 * 
	 * @param tooltip All lines to display in the Item's tooltip. This is a List of Strings.
	 * @param advanced Whether the setting "Advanced tooltips" is enabled
	 */
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced)
	{
		super.addInformation(stack, playerIn, tooltip, advanced);
		
		CompoundItem compound = compoundList.get(stack.getMetadata());
		tooltip.add("Formula: " + compound.getChemFormula());
		tooltip.add("Current state: " + compound.getState());
		
		NBTTagCompound tag = stack.getTagCompound();
		int[] mols = tag == null ? null : tag.getIntArray(NBTKeys.Chemical.MOLS);
		tooltip.add(String.format("MMols: %.2f", mols == null ? 1 : MathUtil.parseFrac(mols)));
	}
}
