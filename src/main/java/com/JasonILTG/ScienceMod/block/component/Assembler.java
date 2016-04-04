package com.JasonILTG.ScienceMod.block.component;

import com.JasonILTG.ScienceMod.ScienceMod;
import com.JasonILTG.ScienceMod.block.general.BlockContainerScience;
import com.JasonILTG.ScienceMod.block.general.IHasItemBlock;
import com.JasonILTG.ScienceMod.itemblock.component.AssemblerItemBlock;
import com.JasonILTG.ScienceMod.reference.EnumGUI;
import com.JasonILTG.ScienceMod.reference.Names;
import com.JasonILTG.ScienceMod.tileentity.component.TEAssembler;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

/**
 * Block class for assemblers.
 * 
 * @author JasonILTG and syy1125
 */
public class Assembler extends BlockContainerScience implements IHasItemBlock
{
	/**
	 * Default constructor.
	 */
	public Assembler()
	{
		super(Material.iron);
		setCreativeTab(ScienceMod.tabMachines);
		setUnlocalizedName(Names.Blocks.Component.ASSEMBLER);
	}
	
	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta)
	{
		TileEntity assemblerEntity = new TEAssembler();
		assemblerEntity.setWorldObj(worldIn);
		return assemblerEntity;
	}
	
	@Override
	public int getRenderType()
	{
		return 3;
	}
	
	@Override
	public Class<? extends ItemBlock> getItemBlockClass()
	{
		return AssemblerItemBlock.class;
	}
	
	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumFacing side, float hitX,
			float hitY, float hitZ)
	{
		if (!world.isRemote)
		{
			player.openGui(ScienceMod.modInstance, EnumGUI.ASSEMBLER.ordinal(), world, pos.getX(), pos.getY(), pos.getZ());
		}
		return true;
	}
}
