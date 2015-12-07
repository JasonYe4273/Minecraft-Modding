package com.JasonILTG.ScienceMod.block.machines;

import com.JasonILTG.ScienceMod.ScienceMod;
import com.JasonILTG.ScienceMod.reference.EnumGUI;
import com.JasonILTG.ScienceMod.reference.Names;
import com.JasonILTG.ScienceMod.tileentity.machines.TEElectrolyzer;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

/**
 * Machine that electrolyzes things.
 * 
 * @author JasonILTG and syy1125
 */
public class Electrolyzer extends MachineScience
{
	/**
	 * Default constructor.
	 */
	public Electrolyzer()
	{
		super(Material.iron);
		setUnlocalizedName(Names.Blocks.Machine.MACHINE_ELECTROLYZER);
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
	
	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumFacing side, float hitX,
			float hitY, float hitZ)
	{
		// If the player is holding a water bucket and if the block has a tile entity electrolyzer
		if (player.inventory.getCurrentItem() != null
				&& player.inventory.getCurrentItem().isItemEqual(new ItemStack(Items.water_bucket)))
		{
			TileEntity entity = world.getTileEntity(pos);
			if (entity instanceof TEElectrolyzer)
			{
				TEElectrolyzer tileElectrolyzer = (TEElectrolyzer) entity;
				
				// If the tank is successfully filled, change the bucket to empty.
				if (tileElectrolyzer.fillAll(new FluidStack(FluidRegistry.WATER, 1000))) {
					player.inventory.getCurrentItem().setItem(Items.bucket);
					return true;
				}
			}
		}
		
		if (!world.isRemote)
		{
			player.openGui(ScienceMod.modInstance, EnumGUI.ELECTROLYZER.ordinal(), world, pos.getX(), pos.getY(), pos.getZ());
		}
		return true;
	}
}
