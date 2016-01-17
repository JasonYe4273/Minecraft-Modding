package com.JasonILTG.ScienceMod.tileentity;

import java.util.ArrayList;
import java.util.List;

import com.JasonILTG.ScienceMod.handler.config.ConfigData;
import com.JasonILTG.ScienceMod.manager.Manager;
import com.JasonILTG.ScienceMod.manager.heat.HeatManager;
import com.JasonILTG.ScienceMod.manager.heat.TileHeatManager;
import com.JasonILTG.ScienceMod.manager.power.PowerManager;
import com.JasonILTG.ScienceMod.manager.power.TilePowerManager;
import com.JasonILTG.ScienceMod.reference.NBTKeys;
import com.JasonILTG.ScienceMod.tileentity.general.ITileEntityHeated;
import com.JasonILTG.ScienceMod.tileentity.general.ITileEntityPowered;
import com.JasonILTG.ScienceMod.tileentity.general.TEScience;
import com.JasonILTG.ScienceMod.util.BlockHelper;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.gui.IUpdatePlayerListBox;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.IChatComponent;

/**
 * Tile entity class for wires.
 * 
 * @author JasonILTG and syy1125
 */
public class TEWire extends TEScience implements IUpdatePlayerListBox, ITileEntityPowered, ITileEntityHeated
{
	/** The HeatManager of the wire */
	protected TileHeatManager wireHeat;
	/** The PowerManager of the wire */
	protected TilePowerManager wirePower;
	/** Whether or not the managers have had their <code>World</code>s updated */
	protected boolean managerWorldUpdated;
	/** The hull tag indicating the properties of the hull */
	protected NBTTagCompound hullTag;
	/** The custom name */
	protected String customName;
	
	public static final String NAME = "Wire";
	
	public static final int DEFAULT_POWER_CAPACITY = 400000;
	public static final int DEFAULT_MAX_RATE = 100;
	
	/**
	 * Constructor.
	 */
	public TEWire()
	{
		wireHeat = new TileHeatManager(this);
		wirePower = new TilePowerManager(this.worldObj, this.pos, DEFAULT_POWER_CAPACITY, DEFAULT_MAX_RATE, DEFAULT_MAX_RATE, TilePowerManager.WIRE);
		managerWorldUpdated = false;
		customName = NAME;
	}
	
	@Override
	public void update()
	{
		if (this.worldObj.isRemote) return;
		
		if (!managerWorldUpdated && this.worldObj != null && this.worldObj.isAreaLoaded(this.pos, 2))
		{
			updateManagers();
			managerWorldUpdated = true;
		}
	}
	
	/**
	 * Updates the information for the managers. Called when there is a block update.
	 */
	public void updateManagers()
	{
		wireHeat.updateWorldInfo(worldObj, pos);
		wirePower.updateWorldInfo(worldObj, pos);
	}
	
	@Override
	public HeatManager getHeatManager()
	{
		return wireHeat;
	}
	
	@Override
	public boolean hasHeat()
	{
		return true;
	}
	
	@Override
	public float getCurrentTemp()
	{
		return wireHeat.getCurrentTemp();
	}
	
	@Override
	public void setCurrentTemp(float temp)
	{
		// Only allowed on the client side
		if (!this.worldObj.isRemote) return;
		wireHeat.setCurrentTemp(temp);
	}
	
	@Override
	public void setFire()
	{
		int dist = ConfigData.Machine.fireDist;
		
		// Entities
		AxisAlignedBB affectedArea = new AxisAlignedBB(pos.add(-dist, -dist, -dist), pos.add(dist, dist, dist));
		@SuppressWarnings("unchecked")
		List<EntityLivingBase> entities = worldObj.getEntitiesWithinAABB(EntityLivingBase.class, affectedArea);
		int entityListLength = entities.size();
		
		// Blocks
		List<BlockPos> flammablePositions = new ArrayList<BlockPos>();
		for (int dx = -dist; dx <= dist; dx ++) {
			for (int dy = -dist; dy <= dist; dy ++) {
				for (int dz = -dist; dz <= dist; dz ++)
				{
					BlockPos newPos = pos.add(dx, dy, dz);
					if (worldObj.isAirBlock(newPos) && BlockHelper.getAdjacentBlocksFlammable(worldObj, newPos)) {
						flammablePositions.add(newPos);
					}
				}
			}
		}
		int flammableListLength = flammablePositions.size();
		
		if (entityListLength + flammableListLength == 0) return;
		
		// Set fire
		int index = Manager.RANDOMIZER.nextInt(entityListLength + flammableListLength);
		if (index < entityListLength) {
			// Set that unfortunate entity on fire
			entities.get(index).setFire(HeatManager.FIRE_LENGTH);
		}
		else {
			// Set block on fire
			worldObj.setBlockState(flammablePositions.get(index - entityListLength), Blocks.fire.getDefaultState());
		}
		
	}
	
	@Override
	public void explode()
	{
		this.worldObj.setBlockToAir(pos);
		this.worldObj.createExplosion(null, pos.getX(), pos.getY(), pos.getZ(), ConfigData.Machine.expStr, ConfigData.Machine.expDamageBlocks);
	}
	
	@Override
	public String getName()
	{
		return this.hasCustomName() ? this.customName : "container.inventory_tile_entity";
	}
	
	@Override
	public boolean hasCustomName()
	{
		return this.customName != null && !this.customName.equals("");
	}
	
	@Override
	public IChatComponent getDisplayName()
	{
		return this.hasCustomName() ? new ChatComponentText(this.getName()) : new ChatComponentTranslation(this.getName());
	}
	
	@Override
	public PowerManager getPowerManager()
	{
		return wirePower;
	}
	
	@Override
	public boolean hasPower()
	{
		return true;
	}
	
	@Override
	public float getPowerCapacity()
	{
		return wirePower.getCapacity();
	}
	
	@Override
	public float getCurrentPower()
	{
		return wirePower.getCurrentPower();
	}
	
	@Override
	public void setCurrentPower(float amount)
	{
		// Only allowed on the client side
		if (!this.worldObj.isRemote) return;
		wirePower.setCurrentPower(amount);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound tag)
	{
		super.readFromNBT(tag);
		
		// Load heat and power managers
		wireHeat.readFromNBT(tag);
		wirePower.readFromNBT(tag);
	}
	
	@Override
	public void writeToNBT(NBTTagCompound tag)
	{
		super.writeToNBT(tag);
		
		// Save heat and power managers
		wireHeat.writeToNBT(tag);
		wirePower.writeToNBT(tag);
	}
	
	@Override
	public void setHull(NBTTagCompound hull)
	{
		hullTag = hull;
	}
	
	@Override
	public float getBaseMaxTemp()
	{
		return hullTag.getFloat(NBTKeys.Item.Component.MAX_TEMP);
	}
	
	@Override
	public float getBaseSpecificHeat()
	{
		return hullTag.getFloat(NBTKeys.Item.Component.SPECIFIC_HEAT);
	}
	
	@Override
	public float getBaseHeatLoss()
	{
		return hullTag.getFloat(NBTKeys.Item.Component.HEAT_LOSS);
		
	}
	
	@Override
	public float getBaseHeatTransfer()
	{
		return hullTag.getFloat(NBTKeys.Item.Component.HEAT_TRANSFER);
	}
}
