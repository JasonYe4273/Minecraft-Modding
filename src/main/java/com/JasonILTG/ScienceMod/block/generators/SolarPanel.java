package com.JasonILTG.ScienceMod.block.generators;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

import com.JasonILTG.ScienceMod.ScienceMod;
import com.JasonILTG.ScienceMod.annotation.RawName;
import com.JasonILTG.ScienceMod.block.general.IHasItemBlock;
import com.JasonILTG.ScienceMod.itemblock.generators.SolarPanelItemBlock;
import com.JasonILTG.ScienceMod.reference.EnumGUI;
import com.JasonILTG.ScienceMod.tileentity.generators.TESolarPanel;

/**
 * Generator that generates free power from sunlight.
 * 
 * @author JasonILTG and syy1125
 */
public class SolarPanel
		extends GeneratorScience
		implements IHasItemBlock
{
	@RawName
	public static final String NAME = "solar_panel";
	
	/**
	 * Default constructor.
	 */
	public SolarPanel()
	{
		super(Material.iron);
	}
	
	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta)
	{
		return new TESolarPanel();
	}
	
	@Override
	public int getRenderType()
	{
		return 3;
	}
	
	@Override
	public Class<? extends ItemBlock> getItemBlockClass()
	{
		return SolarPanelItemBlock.class;
	}
	
	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumFacing side, float hitX,
			float hitY, float hitZ)
	{
		if (!world.isRemote)
		{
			player.openGui(ScienceMod.modInstance, EnumGUI.SOLAR_PANEL.ordinal(), world, pos.getX(), pos.getY(), pos.getZ());
		}
		return true;
	}
}
