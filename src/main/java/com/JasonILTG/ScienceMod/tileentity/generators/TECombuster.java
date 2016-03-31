package com.JasonILTG.ScienceMod.tileentity.generators;

import com.JasonILTG.ScienceMod.ScienceMod;
import com.JasonILTG.ScienceMod.crafting.GeneratorHeatedRecipe;
import com.JasonILTG.ScienceMod.crafting.GeneratorRecipe;
import com.JasonILTG.ScienceMod.init.ScienceModItems;
import com.JasonILTG.ScienceMod.messages.TETankMessage;
import com.JasonILTG.ScienceMod.reference.Constants;
import com.JasonILTG.ScienceMod.util.InventoryHelper;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

/**
 * Tile entity class for combusters.
 * 
 * @author JasonILTG and syy1125
 */
public class TECombuster extends TEGenerator
{
	public static final String NAME = "Combuster";

	public static final int UPGRADE_INV_SIZE = 2;
	public static final int JAR_INV_SIZE = 1;
	public static final int INPUT_INV_SIZE = 1;
	public static final int OUTPUT_INV_SIZE = 1;
	
	public static final int NUM_TANKS = 2;
	public static final int FUEL_TANK_INDEX = 0;
	public static final int COOLANT_TANK_INDEX = 1;
	
	/**
	 * Default constructor.
	 */
	public TECombuster()
	{
		super(NAME, new int[] { UPGRADE_INV_SIZE, JAR_INV_SIZE, INPUT_INV_SIZE, OUTPUT_INV_SIZE, NO_INV_SIZE }, NUM_TANKS);
	}

	@Override
	public void update()
	{
		if (!worldObj.isRemote) checkBoil();
		super.update();
	}
	
	/**
	 * Checks for boiling.
	 */
	private void checkBoil()
	{
		int boilAmount = Math.min((int) (Constants.BOIL_RATE * (generatorHeat.getCurrentTemp() - Constants.BOIL_THRESHOLD + 1)), 
									tanks[COOLANT_TANK_INDEX].getFluidAmount());
		if (boilAmount > 0)
		{
			drainTank(new FluidStack(FluidRegistry.WATER, boilAmount), COOLANT_TANK_INDEX);
			generatorHeat.transferHeat(-boilAmount * Constants.BOIL_HEAT_LOSS);
			ScienceMod.snw.sendToAll(new TETankMessage(this.pos.getX(), this.pos.getY(), this.pos.getZ(), tanks[COOLANT_TANK_INDEX].getFluidAmount(), COOLANT_TANK_INDEX));
		}
	}
	
	@Override
	protected GeneratorRecipe[] getRecipes()
	{
		return CombustionRecipe.values();
	}

	@Override
	protected void consumeInputs(GeneratorRecipe recipe)
	{
		if (!(recipe instanceof CombustionRecipe)) return;
		CombustionRecipe validRecipe = (CombustionRecipe) recipe;
		
		if (validRecipe.reqItemStack != null) {
			allInventories[INPUT_INV_INDEX][0].splitStack(validRecipe.reqItemStack.stackSize);
			
			ItemStack inputContainer = validRecipe.reqItemStack.getItem().getContainerItem(validRecipe.reqItemStack);
			if (inputContainer != null && !inputContainer.isItemEqual(new ItemStack(ScienceModItems.jar, 1)))
				allInventories[INPUT_INV_INDEX][0] = inputContainer;
		}
		
		if (validRecipe.reqFluidStack != null) {
			drainTank(validRecipe.reqFluidStack, FUEL_TANK_INDEX);
			tanksUpdated[FUEL_TANK_INDEX] = false;
		}
		
		InventoryHelper.checkEmptyStacks(allInventories);
	}
	
	@Override
	protected void doOutput(GeneratorRecipe recipe)
	{
		CombustionRecipe validRecipe = (CombustionRecipe) recipe;
		
		super.doOutput(recipe);
		
		if (validRecipe.getJarOutput() > 0)
		{
			if (allInventories[JAR_INV_INDEX][0] != null) allInventories[JAR_INV_INDEX][0].stackSize += validRecipe.getJarOutput();
			else allInventories[JAR_INV_INDEX][0] = new ItemStack(ScienceModItems.jar);
		}
	}

	@Override
	protected boolean hasIngredients(GeneratorRecipe recipeToUse)
	{
		return ((CombustionRecipe) recipeToUse).canProcess(allInventories[INPUT_INV_INDEX][0], tanks[FUEL_TANK_INDEX].getFluid());
	}
	
	@Override
	protected boolean hasSpace(GeneratorRecipe recipe)
	{
		int jarOutputNum = ((CombustionRecipe) recipe).getJarOutput();
		if (jarOutputNum == 0) return super.hasSpace(recipe);
		if (allInventories[JAR_INV_INDEX][0] == null || !allInventories[JAR_INV_INDEX][0].isItemEqual(new ItemStack(ScienceModItems.jar))) return false;
		if (allInventories[JAR_INV_INDEX][0].stackSize < jarOutputNum) return false;
		return super.hasSpace(recipe);
	}
	
	private enum CombustionRecipe implements GeneratorHeatedRecipe
	{
		// Vanilla
		Lava(20000, 10F, -273F, 15F, new ItemStack(Items.lava_bucket), null, 0, null),
		LavaFluid(20, 10F, -273F, 15F, null, new FluidStack(FluidRegistry.LAVA, 1), 0, null),
		CoalBlock(16000, 10F, -273F, 15F, new ItemStack(Blocks.coal_block), null, 0, null),
		BlazeRod(2400, 10F, -273F, 15F, new ItemStack(Items.blaze_rod), null, 0, null),
		Coal(1600, 10F, -273F, 15F, new ItemStack(Items.coal), null, 0, null),
		Log(300, 10F, -273F, 15F, new ItemStack(Blocks.log), null, 0, null),
		Log2(300, 10F, -273F, 15F, new ItemStack(Blocks.log2), null, 0, null),
		Plank(300, 10F, -273F, 15F, new ItemStack(Blocks.planks), null, 0, null),
		PressurePlate(300, 10F, -273F, 15F, new ItemStack(Blocks.wooden_pressure_plate), null, 0, null),
		OakFence(300, 10F, -273F, 15F, new ItemStack(Blocks.oak_fence), null, 0, null),
		OakFenceGate(300, 10F, -273F, 15F, new ItemStack(Blocks.oak_fence_gate), null, 0, null),
		DarkOakFence(300, 10F, -273F, 15F, new ItemStack(Blocks.dark_oak_fence), null, 0, null),
		DarkOakFenceGate(300, 10F, -273F, 15F, new ItemStack(Blocks.dark_oak_fence_gate), null, 0, null),
		AcaciaFence(300, 10F, -273F, 15F, new ItemStack(Blocks.acacia_fence), null, 0, null),
		AcaciaFenceGate(300, 10F, -273F, 15F, new ItemStack(Blocks.acacia_fence_gate), null, 0, null),
		BirchFence(300, 10F, -273F, 15F, new ItemStack(Blocks.birch_fence), null, 0, null),
		BirchFenceGate(300, 10F, -273F, 15F, new ItemStack(Blocks.birch_fence_gate), null, 0, null),
		JungleFence(300, 10F, -273F, 15F, new ItemStack(Blocks.jungle_fence), null, 0, null),
		JungleFenceGate(300, 10F, -273F, 15F, new ItemStack(Blocks.jungle_fence_gate), null, 0, null),
		SpruceFence(300, 10F, -273F, 15F, new ItemStack(Blocks.spruce_fence), null, 0, null),
		SpruceFenceGate(300, 10F, -273F, 15F, new ItemStack(Blocks.spruce_fence_gate), null, 0, null),
		OakStairs(300, 10F, -273F, 15F, new ItemStack(Blocks.oak_stairs), null, 0, null),
		DarkOakStairs(300, 10F, -273F, 15F, new ItemStack(Blocks.dark_oak_stairs), null, 0, null),
		AcaciaStairs(300, 10F, -273F, 15F, new ItemStack(Blocks.acacia_stairs), null, 0, null),
		BirchStairs(300, 10F, -273F, 15F, new ItemStack(Blocks.birch_stairs), null, 0, null),
		JungleStairs(300, 10F, -273F, 15F, new ItemStack(Blocks.jungle_stairs), null, 0, null),
		SpruceStairs(300, 10F, -273F, 15F, new ItemStack(Blocks.spruce_stairs), null, 0, null),
		Trapdoor(300, 10F, -273F, 15F, new ItemStack(Blocks.trapdoor), null, 0, null),
		CraftingTable(300, 10F, -273F, 15F, new ItemStack(Blocks.crafting_table), null, 0, null),
		Bookshelf(300, 10F, -273F, 15F, new ItemStack(Blocks.bookshelf), null, 0, null),
		Chest(300, 10F, -273F, 15F, new ItemStack(Blocks.chest), null, 0, null),
		TrappedChest(300, 10F, -273F, 15F, new ItemStack(Blocks.trapped_chest), null, 0, null),
		DaylightSensor(300, 10F, -273F, 15F, new ItemStack(Blocks.daylight_detector), null, 0, null),
		Jukebox(300, 10F, -273F, 15F, new ItemStack(Blocks.jukebox), null, 0, null),
		NoteBlock(300, 10F, -273F, 15F, new ItemStack(Blocks.noteblock), null, 0, null),
		MushroomBlockBrown(300, 10F, -273F, 15F, new ItemStack(Blocks.brown_mushroom_block), null, 0, null),
		MushroomBlockRed(300, 10F, -273F, 15F, new ItemStack(Blocks.red_mushroom_block), null, 0, null),
		Banner(300, 10F, -273F, 15F, new ItemStack(Items.banner), null, 0, null),
		WoodenPickaxe(200, 10F, -273F, 15F, new ItemStack(Items.wooden_pickaxe), null, 0, null),
		WoodenSword(200, 10F, -273F, 15F, new ItemStack(Items.wooden_sword), null, 0, null),
		WoodenAxe(200, 10F, -273F, 15F, new ItemStack(Items.wooden_axe), null, 0, null),
		WoodenShovel(200, 10F, -273F, 15F, new ItemStack(Items.wooden_shovel), null, 0, null),
		WoodenHoe(200, 10F, -273F, 15F, new ItemStack(Items.wooden_hoe), null, 0, null),
		Slab(150, 10F, -273F, 15F, new ItemStack(Blocks.wooden_slab), null, 0, null),
		Sapling(100, 10F, -273F, 15F, new ItemStack(Blocks.sapling), null, 0, null),
		Stick(100, 10F, -273F, 15F, new ItemStack(Items.stick), null, 0, null),
		
		// 
		Hydrogen(1600, 10F, -273F, 15F, new ItemStack(ScienceModItems.element, 1, 0), null, 0, null)
		;
		
		/** The time required */
		public final int timeReq;
		/** The power used every tick */
		public final float powerGen;
		/** The temperature required */
		public final float tempReq;
		/** The heat released every tick */
		public final float heatReleased;
		/** The ItemStack input required */
		public final ItemStack reqItemStack;
		/** The FluidStack required */
		public final FluidStack reqFluidStack;
		/** The number of jars output */
		public final int jarOutput;
		/** The ItemStack outputs */
		public final ItemStack[] outItemStack;
		
		/**
		 * Constructor.
		 * 
		 * @param timeRequired The time required
		 * @param powerGenerated The power generated every tick
		 * @param tempRequirement The temperature required
		 * @param heatReleased The heat released every tick
		 * @param requiredItemStack The ItemStack input required
		 * @param requiredFluidStack The FluidStack required
		 * @param outputJarCount The number of jars outputed
		 * @param outputItemStacks The ItemStack outputs
		 */
		private CombustionRecipe(int timeRequired, float powerGenerated, float tempRequirement, float heatReleased, ItemStack requiredItemStack, FluidStack requiredFluidStack,
				int outputJarCount, ItemStack[] outputItemStacks)
		{
			timeReq = timeRequired;
			powerGen = powerGenerated;
			tempReq = tempRequirement;
			this.heatReleased = heatReleased;
			reqItemStack = requiredItemStack;
			reqFluidStack = requiredFluidStack;
			jarOutput = outputJarCount;
			outItemStack = outputItemStacks;
		}
		
		/**
		 * Determines whether the required ItemStack input is present.
		 * @param inputItemStack The ItemStack input
		 * @return Whether the required ItemStack input is present
		 */
		private boolean hasItem(ItemStack inputItemStack)
		{
			if (reqItemStack != null)
			{
				// null check
				if (inputItemStack == null) return false;
				
				if (!inputItemStack.isItemEqual(reqItemStack)) return false;
				if (inputItemStack.stackSize < reqItemStack.stackSize) return false;
			}
			return true;
		}
		
		/**
		 * Determines whether the required FluidStack input is present.
		 * 
		 * @param inputFluidStack The FluidStack input
		 * @return Whether the required FluidStack is present
		 */
		private boolean hasFluid(FluidStack inputFluidStack)
		{
			if (reqFluidStack != null)
			{
				if (inputFluidStack == null) return false;
				
				if (!inputFluidStack.containsFluid(reqFluidStack)) return false;
			}
			return true;
		}
		
		/**
		 * @param params Input format: jar input stack, item input stack, fluid input stack
		 */
		@Override
		public boolean canProcess(Object... params)
		{
			ItemStack inputItemStack = (ItemStack) params[0];
			FluidStack inputFluidStack = (FluidStack) params[1];
			
			return hasItem(inputItemStack) && hasFluid(inputFluidStack);
		}
		
		@Override
		public ItemStack[] getItemOutputs()
		{
			return outItemStack;
		}
		
		/**
		 * @return The number of jars to output
		 */
		public int getJarOutput()
		{
			return jarOutput;
		}
		
		@Override
		public int getTimeRequired()
		{
			return timeReq;
		}
		
		@Override
		public float getTempRequired()
		{
			return tempReq;
		}
		
		@Override
		public float getHeatReleased()
		{
			return heatReleased;
		}
		
		@Override
		public float getPowerGenerated()
		{
			return powerGen;
		}
	}

	/* (non-Javadoc)
	 * @see net.minecraft.inventory.IInventory#removeStackFromSlot(int)
	 */
	@Override
	public ItemStack removeStackFromSlot(int index)
	{
		// TODO Auto-generated method stub
		return null;
	}
}
