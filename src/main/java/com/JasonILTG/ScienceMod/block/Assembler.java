package com.JasonILTG.ScienceMod.block;

import com.JasonILTG.ScienceMod.block.general.BlockContainerScience;
import com.JasonILTG.ScienceMod.creativetabs.ScienceCreativeTabs;
import com.JasonILTG.ScienceMod.reference.Names;
import com.JasonILTG.ScienceMod.tileentity.TEAssembler;

import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

/**
 * Block class for assemblers.
 * 
 * @author JasonILTG and syy1125
 */
public class Assembler extends BlockContainerScience
{
	/**
	 * Default constructor.
	 */
	public Assembler()
	{
		super(Material.iron);
		setCreativeTab(ScienceCreativeTabs.tabMachines);
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
}
