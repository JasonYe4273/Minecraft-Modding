package com.JasonILTG.ScienceMod.tileentity;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import com.JasonILTG.ScienceMod.crafting.MachineRecipe;
import com.JasonILTG.ScienceMod.init.ScienceModItems;
import com.JasonILTG.ScienceMod.item.general.ItemJarred;
import com.JasonILTG.ScienceMod.manager.HeatManager;
import com.JasonILTG.ScienceMod.reference.ChemElements;
import com.JasonILTG.ScienceMod.tileentity.general.IMachineHeated;
import com.JasonILTG.ScienceMod.tileentity.general.TEMachine;
import com.JasonILTG.ScienceMod.util.InventoryHelper;
import com.JasonILTG.ScienceMod.util.LogHelper;

public class TEReactionChamber extends TEMachine implements IMachineHeated
{
	public static final String NAME = "Combustion Chamber";
	
	public static final int JAR_INV_SIZE = 1;
	public static final int INPUT_INV_SIZE = 4;
	public static final int OUTPUT_INV_SIZE = 4;
	
	public static final int DEFAULT_MAX_PROGRESS = 100;
	
	private HeatManager manager;
	
	public TEReactionChamber()
	{
		super(NAME, DEFAULT_MAX_PROGRESS, new int[] { NO_INV_SIZE, JAR_INV_SIZE, INPUT_INV_SIZE, OUTPUT_INV_SIZE });
		manager = new HeatManager(HeatManager.DEFAULT_MAX_TEMP, HeatManager.DEFAULT_SPECIFIC_HEAT);
	}
	
	@Override
	public MachineRecipe[] getRecipes()
	{
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	protected void consumeInputs(MachineRecipe recipe)
	{
		// TODO Auto-generated method stub
		
	}
	
	@Override
	protected boolean hasIngredients(MachineRecipe recipeToUse)
	{
		// This method here should not be called.
		LogHelper.warn("hasIngredients in TEReactionChamber should not be activated!");
		return false;
	}
	
	@Override
	public void update()
	{
		super.update();
		manager.update(worldObj, pos);
	}
	
	@Override
	public void craft()
	{
		if (currentRecipe != null && currentRecipe instanceof ReactionRecipe)
		{
			// The machine is processing.
			ReactionRecipe recipe = (ReactionRecipe) currentRecipe;
			
			if (!recipe.tempHighEnough(manager.getCurrentTemp())) {
				// Not enough temperature, process fails completely
				resetRecipe();
			}
			else {
				// The processing can continue
				currentProgress ++;
				manager.transferHeat(recipe.heatPerTick);
				
				// If the machine has finished processing
				if (currentProgress >= maxProgress)
				{
					if (recipe.canProcess(manager.getCurrentTemp(), allInventories[JAR_INV_INDEX][0], allInventories[OUTPUT_INV_INDEX])) {
						// Couldn't output
						currentProgress = maxProgress - 1;
					}
					else {
						doOutput(currentRecipe);
						allInventories[JAR_INV_INDEX][0].splitStack(-recipe.jarCountChange);
						
						resetRecipe();
					}
				}
			}
		}
		else {
			// The machine is stopped, search for a new recipe
			resetRecipe();
			
			for (ReactionRecipe recipe : ReactionRecipe.values())
			{
				if (recipe.canProcess(manager.getCurrentTemp(), allInventories[JAR_INV_INDEX][0], allInventories[OUTPUT_INV_INDEX])) {
					currentRecipe = recipe;
					
				}
			}
		}
	}
	
	@Override
	public HeatManager getHeatManager()
	{
		return manager;
	}
	
	@Override
	public boolean hasHeat()
	{
		if (currentRecipe == null || currentRecipe instanceof ReactionRecipe) return true;
		
		return ((ReactionRecipe) currentRecipe).tempHighEnough(manager.getCurrentTemp());
	}
	
	@Override
	public void readFromNBT(NBTTagCompound tag)
	{
		super.readFromNBT(tag);
		manager.readFromNBT(tag);
	}
	
	@Override
	public void writeToNBT(NBTTagCompound tag)
	{
		super.writeToNBT(tag);
		manager.writeToNBT(tag);
	}
	
	public enum ReactionRecipe implements MachineRecipe
	{
		Hydrogen(20, 0, 500, new ItemStack[] {
				new ItemStack(ScienceModItems.element, 2, ChemElements.HYDROGEN.ordinal()),
				new ItemStack(ScienceModItems.element, 1, ChemElements.OXYGEN.ordinal())
		}, new ItemStack[] {
				new ItemStack(ScienceModItems.water, 2)
		});
		
		private int jarCountChange;
		private int reqTime;
		private float reqTemp;
		private float heatPerTick;
		private float heat;
		private ItemStack[] reactants;
		private ItemStack[] products;
		
		private ReactionRecipe(int timeRequired, float reqTemperature, float heatChange, ItemStack[] reactants, ItemStack[] products)
		{
			reqTime = timeRequired;
			reqTemp = reqTemperature;
			heat = heatChange;
			heatPerTick = heat / reqTime;
			this.reactants = reactants;
			this.products = products;
			
			// Calculate jar count change
			jarCountChange = 0;
			for (ItemStack stack : reactants) {
				if (stack.getItem() instanceof ItemJarred) jarCountChange += stack.stackSize;
			}
			for (ItemStack stack : products) {
				if (stack.getItem() instanceof ItemJarred) jarCountChange -= stack.stackSize;
			}
		}
		
		public boolean tempHighEnough(float temperature)
		{
			return temperature < reqTemp;
		}
		
		/**
		 * Input format: temperature, jar item stack, output item stacks
		 */
		@Override
		public boolean canProcess(Object... params)
		{
			// Temperature check
			if (((Float) params[0]).floatValue() < reqTemp) return false;
			
			// Jar count check
			int predictedJarCount = ((ItemStack) params[1]).stackSize + jarCountChange;
			if (!(predictedJarCount > 0 && predictedJarCount < ((ItemStack) params[1]).getMaxStackSize())) return false;
			
			// We will consume input upon activation, so there is no need to check for input
			// Output check
			if (InventoryHelper.findInsertPattern(products, (ItemStack[]) params[2]) == null) return false;
			
			return true;
		}
		
		public float getHeatPerTick()
		{
			return heatPerTick;
		}
		
		@Override
		public ItemStack[] getItemOutputs()
		{
			return reactants;
		}
		
		public int getJarCountChange()
		{
			return jarCountChange;
		}
		
		@Override
		public int getTimeRequired()
		{
			return reqTime;
		}
	}
}