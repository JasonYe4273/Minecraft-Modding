package com.JasonILTG.ScienceMod.block;

import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import com.JasonILTG.ScienceMod.block.general.MachineScience;
import com.JasonILTG.ScienceMod.reference.Names;
import com.JasonILTG.ScienceMod.tileentity.TEReactionChamber;

public abstract class ReactionChamber extends MachineScience
{
	public ReactionChamber()
	{
		super(Material.iron);
		setUnlocalizedName(Names.Blocks.MACHINE_REACTION_CHAMBER);
	}
	
	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta)
	{
		return new TEReactionChamber();
	}
	
	@Override
	public int getRenderType()
	{
		return 3;
	}
}
