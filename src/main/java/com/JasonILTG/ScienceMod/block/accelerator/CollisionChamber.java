package com.JasonILTG.ScienceMod.block.accelerator;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import com.JasonILTG.ScienceMod.tileentity.accelerator.TECollisionChamber;

public class CollisionChamber
		extends AcceleratorOutput
{
	public static final String NAME = NAME_PREFIX + ".collision_chamber";
	
	public CollisionChamber()
	{
		super(NAME);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta)
	{
		return new TECollisionChamber();
	}
}
