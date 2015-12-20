package com.JasonILTG.ScienceMod.item.component.hull;

import java.util.List;

import com.JasonILTG.ScienceMod.item.general.ItemScience;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public abstract class Hull extends ItemScience
{
	private MaterialHeat heatMaterial;
	
	public Hull(MaterialHeat heatMat)
	{
		setUnlocalizedName("hull." + heatMat.name);
		heatMaterial = heatMat;
	}
	
	@Override
	public boolean getHasSubtypes()
	{
		return false;
	}
	
	@Override
	public int getNumSubtypes()
	{
		return 1;
	}
	
	public boolean getCanOverheat()
	{
		return heatMaterial.canOverheat;
	}
	
	public float getMaxTemperate()
	{
		return heatMaterial.maxTemp;
	}
	
	public float getSpecificHeat()
	{
		return heatMaterial.specificHeat;
	}
	
	public float getHeatLoss()
	{
		return heatMaterial.heatLoss;
	}
	
	public float getHeatTransfer()
	{
		return heatMaterial.heatTransfer;
	}
	
	@Override
	public void addInformation(ItemStack stack, EntityPlayer playerIn, List tooltip, boolean advanced)
	{
		if (heatMaterial.canOverheat)
		{
			tooltip.add(String.format("Overheats at %.1f C", heatMaterial.maxTemp));
		}
		else tooltip.add("Does not overheat");
		tooltip.add(String.format("Specific Heat: %.5f J/C", heatMaterial.specificHeat));
		tooltip.add(String.format("Heat Loss: %.5f J/t", heatMaterial.heatLoss));
		tooltip.add(String.format("Heat Transfer: %.5f J/C", heatMaterial.heatTransfer));
		
		/* For use by machine
		if (stack.getTagCompound() != null)
		{
			// Null check
			
			NBTTagCompound hullTag = (NBTTagCompound) stack.getTagCompound().getTag(NBTKeys.Item.Component.HULL);
			if (hullTag != null)
			{
				tooltip.add("Hull information:");
				
				boolean overheat = hullTag.getBoolean(NBTKeys.Item.Component.OVERHEAT);
				float maxTemp = hullTag.getFloat(NBTKeys.Item.Component.MAX_TEMP);
				float specificHeat = hullTag.getFloat(NBTKeys.Item.Component.SPECIFIC_HEAT);
				float heatLoss = hullTag.getFloat(NBTKeys.Item.Component.HEAT_LOSS);
				float heatTransfer = hullTag.getFloat(NBTKeys.Item.Component.HEAT_TRANSFER);
				
				if (overheat)
				{
					tooltip.add(String.format("* Overheats at %.1f C", maxTemp));
				}
				tooltip.add(String.format("* Specific Heat: %.1f J/C", specificHeat));
				tooltip.add(String.format("* Heat Loss: %.1f J/t", heatLoss));
				tooltip.add(String.format("* Heat Transfer: %.1f %.1f J/C", heatTransfer));
			}
		}*/
	}
}
