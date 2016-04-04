package com.JasonILTG.ScienceMod.block.generators;

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
import com.JasonILTG.ScienceMod.itemblock.generators.CombusterItemBlock;
import com.JasonILTG.ScienceMod.reference.EnumGUI;
import com.JasonILTG.ScienceMod.tileentity.generators.TECombuster;

/**
 * Generator that generates power from burning items.
 * 
 * @author JasonILTG and syy1125
 */
public class Combuster
		extends GeneratorScience
		implements IHasItemBlock
{
	public static final String GENERATOR_COMBUSTER = "combuster";

	/**
	 * Default constructor.
	 */
	public Combuster()
	{
		super(Material.iron);
		setUnlocalizedName(Combuster.GENERATOR_COMBUSTER);
	}
	
	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta)
	{
		return new TECombuster();
	}
	
	@Override
	public int getRenderType()
	{
		return 3;
	}
	
	@Override
	public Class<? extends ItemBlock> getItemBlockClass()
	{
		return CombusterItemBlock.class;
	}
	
	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumFacing side, float hitX,
			float hitY, float hitZ)
	{
		// Null check
		if (player.inventory.getCurrentItem() != null)
		{
			// If the player is holding a water bucket and if the block has a tile entity Combuster
			if (player.inventory.getCurrentItem().isItemEqual(new ItemStack(Items.water_bucket)))
			{
				TileEntity entity = world.getTileEntity(pos);
				if (entity instanceof TECombuster)
				{
					TECombuster tileCombuster = (TECombuster) entity;
					
					// If the tank is successfully filled, change the bucket to empty.
					if (tileCombuster.fillAll(new FluidStack(FluidRegistry.WATER, 1000), TECombuster.COOLANT_TANK_INDEX)) {
						player.inventory.getCurrentItem().setItem(Items.bucket);
						return true;
					}
				}
			}
			// If the player is holding a lava bucket and if the block has a tile entity Combuster
			else if (player.inventory.getCurrentItem().isItemEqual(new ItemStack(Items.lava_bucket)))
			{
				TileEntity entity = world.getTileEntity(pos);
				if (entity instanceof TECombuster)
				{
					TECombuster tileCombuster = (TECombuster) entity;
					
					// If the tank is successfully filled, change the bucket to empty.
					if (tileCombuster.fillAll(new FluidStack(FluidRegistry.LAVA, 1000), TECombuster.FUEL_TANK_INDEX)) {
						player.inventory.getCurrentItem().setItem(Items.bucket);
						return true;
					}
				}
			}
		}
		
		if (!world.isRemote)
		{
			player.openGui(ScienceMod.modInstance, EnumGUI.COMBUSTER.ordinal(), world, pos.getX(), pos.getY(), pos.getZ());
		}
		return true;
	}
}
