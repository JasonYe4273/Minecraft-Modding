package com.JasonILTG.ScienceMod.block;

import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

import com.JasonILTG.ScienceMod.block.general.MachineScience;
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
	
	// The super method for this doesn't do anything, so we can overrride safely.
	/**
	 * When right clicked on by a water bucket, it filles with 1000 mB of water.
	 */
	@Override
	public void onBlockClicked(World worldIn, BlockPos pos, EntityPlayer playerIn)
	{
		// If the player is holding a water bucket and if the block has a tile entity electrolyzer
		if (playerIn.inventory.getCurrentItem().isItemEqual(new ItemStack(Items.water_bucket)))
		{
			TileEntity entity = worldIn.getTileEntity(pos);
			if (entity instanceof TEElectrolyzer)
			{
				TEElectrolyzer tileElectrolyzer = (TEElectrolyzer) entity;
				
				// If the tank is successfully filled, change the bucket to empty.
				if (tileElectrolyzer.fillAll(new FluidStack(FluidRegistry.WATER, 1000))) {
					playerIn.inventory.getCurrentItem().setItem(Items.bucket);
				}
				
			}
		}
	}
}
