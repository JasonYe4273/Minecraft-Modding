package com.JasonILTG.ScienceMod.item.compounds;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import com.JasonILTG.ScienceMod.creativetabs.ScienceCreativeTabs;
import com.JasonILTG.ScienceMod.item.general.ItemJarred;
import com.JasonILTG.ScienceMod.reference.MatterState;
import com.JasonILTG.ScienceMod.reference.NBTKeys;
import com.JasonILTG.ScienceMod.util.MathUtil;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Wrapper class for compounds.
 * 
 * @author JasonILTG and syy1125
 */
public class CompoundItem extends ItemJarred
{
	private static final HashMap<String, CompoundItem> compoundMap = new HashMap<String, CompoundItem>();
	
	/** The chemical formula of the compound */
	protected String formula;
	/** Default state of matter */
	protected MatterState state;
	
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
		setCreativeTab(ScienceCreativeTabs.tabCompounds);
		
		compoundMap.put(formula, this);
	}
	
	public static CompoundItem getCompoundItem(String formula)
	{
		return compoundMap.get(formula);
	}
	
	public static Collection<CompoundItem> getCompounds()
	{
		return compoundMap.values();
	}
	
	@Override
	public String getUnlocalizedName()
	{
		return formula;
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
		
		CompoundItem compound = (CompoundItem) stack.getItem();
		tooltip.add("Formula: " + compound.getChemFormula());
		tooltip.add("Current state: " + compound.getState());
		
		NBTTagCompound tag = stack.getTagCompound();
		int[] mols = tag == null ? null : tag.getIntArray(NBTKeys.Chemical.MOLS);
		tooltip.add(String.format("Mols: %.2f", mols == null ? 1 : MathUtil.parseFrac(mols)));
	}
}
