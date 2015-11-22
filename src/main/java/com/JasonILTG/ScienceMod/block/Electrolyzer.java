package com.JasonILTG.ScienceMod.block;

import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import com.JasonILTG.ScienceMod.reference.Names;
import com.JasonILTG.ScienceMod.tileentity.TEElectrolyzer;

/**
 * An electrolyzer for electrolyzing things
 */
public class Electrolyzer extends MachineScience
{
	public Electrolyzer()
	{
		super(Material.iron);
		setUnlocalizedName(Names.Blocks.MACHINE_ELECTROLYZER);
	}
	
	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta)
	{
		return new TEElectrolyzer();
	}
	
	@Override
	public int getRenderType()
	{
		return 3;
	}
	
}
