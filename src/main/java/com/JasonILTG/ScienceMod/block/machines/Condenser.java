package com.JasonILTG.ScienceMod.block.machines;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

import com.JasonILTG.ScienceMod.ScienceMod;
import com.JasonILTG.ScienceMod.block.general.IHasItemBlock;
import com.JasonILTG.ScienceMod.itemblock.machines.CondenserItemBlock;
import com.JasonILTG.ScienceMod.reference.EnumGUI;
import com.JasonILTG.ScienceMod.reference.Names;
import com.JasonILTG.ScienceMod.tileentity.machines.TECondenser;

/**
 * Machine that extracts water from the atmosphere.
 * 
 * @author JasonILTG and syy1125
 */
public class Condenser
		extends MachineScience
		implements IHasItemBlock
{
	/**
	 * Default constructor.
	 */
	public Condenser()
	{
		super(Material.iron);
		setUnlocalizedName(Names.Blocks.Machine.MACHINE_CONDENSER);
	}
	
	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta)
	{
		TileEntity condenserEntity = new TECondenser();
		condenserEntity.setWorldObj(worldIn);
		return condenserEntity;
	}
	
	@Override
	public int getRenderType()
	{
		return 3;
	}
	
	@Override
	public Class<? extends ItemBlock> getItemBlockClass()
	{
		return CondenserItemBlock.class;
	}
	
	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumFacing side, float hitX,
			float hitY, float hitZ)
	{
		// Null check
		if (player.inventory.getCurrentItem() != null)
		{
			// If the player is holding a water bucket and if the block has a tile entity Condenser
			if (player.inventory.getCurrentItem().isItemEqual(new ItemStack(Items.water_bucket)))
			{
				TileEntity entity = world.getTileEntity(pos);
				if (entity instanceof TECondenser)
				{
					TECondenser tileCondenser = (TECondenser) entity;
					
					// If the tank is successfully filled, change the bucket to empty.
					if (tileCondenser.fillAll(new FluidStack(FluidRegistry.WATER, 1000), 0)) {
						player.inventory.getCurrentItem().setItem(Items.bucket);
						return true;
					}
				}
			}
		}
		
		if (!world.isRemote)
		{
			player.openGui(ScienceMod.modInstance, EnumGUI.CONDENSER.ordinal(), world, pos.getX(), pos.getY(), pos.getZ());
		}
		return true;
	}
}
