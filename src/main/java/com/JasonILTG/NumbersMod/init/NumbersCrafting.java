package com.JasonILTG.NumbersMod.init;

import java.util.ArrayList;

import com.jcraft.jorbis.Block;

import com.JasonILTG.NumbersMod.Reference;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class NumbersCrafting
{
	public static void init()
	{
		GameRegistry.addShapelessRecipe(new ItemStack(NumbersItems.numbers[0], 1), Blocks.dragon_egg);
		GameRegistry.addRecipe(new ItemStack(Blocks.dragon_egg, 1), new Object[]
		{
			"OOO",
			"OXO",
			"OOO",
			'O', NumbersItems.numbers[99], 'X', NumbersItems.numbers[0]
		});
		
		GameRegistry.addShapelessRecipe(new ItemStack(NumbersItems.numbers[1], 1), Items.diamond);

		for( int n = 1; n < Reference.MAX_NUMBER; n++ )
		{
			for( int a = 0; a <= n; a++ )
			{
				GameRegistry.addRecipe(new ItemStack(NumbersItems.numbers[n], 1), new Object[]
				{
					"A+B",
					'A', NumbersItems.numbers[a], '+', NumbersItems.addition, 'B', NumbersItems.numbers[n-a]
				});
			}
			
			for( int a = n; a < Reference.MAX_NUMBER; a++ )
			{
				GameRegistry.addRecipe(new ItemStack(NumbersItems.numbers[n], 1), new Object[]
				{
					"A-B",
					'A', NumbersItems.numbers[a], '-', NumbersItems.subtraction, 'B', NumbersItems.numbers[a-n]
				});
			}
			
			ArrayList<Integer> factors = Reference.factor(n);
			for( int f : factors )
			{
				GameRegistry.addRecipe(new ItemStack(NumbersItems.numbers[n], 1), new Object[]
				{
					"AXB",
					'A', NumbersItems.numbers[f], 'X', NumbersItems.multiplication, 'B', NumbersItems.numbers[n/f]
				});
				
				GameRegistry.addRecipe(new ItemStack(NumbersItems.numbers[f], 1), new Object[]
				{
					"A/B",
					'A', NumbersItems.numbers[n], '/', NumbersItems.division, 'B', NumbersItems.numbers[n/f]
				});
				
				if( f != n/f )
				{
					GameRegistry.addRecipe(new ItemStack(NumbersItems.numbers[n/f], 1), new Object[]
					{
						"A/B",
						'A', NumbersItems.numbers[n], '/', NumbersItems.division, 'B', NumbersItems.numbers[f]
					});
				}
			}
		}
		
		for( int a = 1; a < Reference.MAX_NUMBER; a++ )
		{
			GameRegistry.addRecipe(new ItemStack(NumbersItems.numbers[0], 1), new Object[]
			{
				"AXB",
				'A', NumbersItems.numbers[a], 'X', NumbersItems.multiplication, 'B', NumbersItems.numbers[0]
			});
			
			GameRegistry.addRecipe(new ItemStack(NumbersItems.numbers[0], 1), new Object[]
			{
				"AXB",
				'A', NumbersItems.numbers[0], 'X', NumbersItems.multiplication, 'B', NumbersItems.numbers[a]
			});
		}
		GameRegistry.addRecipe(new ItemStack(NumbersItems.numbers[0], 1), new Object[]
		{
			"AXA",
			'A', NumbersItems.numbers[0], 'X', NumbersItems.multiplication
		});
		
		GameRegistry.addRecipe(new ItemStack(NumbersItems.addition, 4), new Object[]
		{
			" X ",
			"X+X",
			" X ",
			'X', Items.diamond, '+', Items.nether_star
		});
		GameRegistry.addRecipe(new ItemStack(NumbersItems.subtraction, 4), new Object[]
		{
			"X+X",
			'X', Items.diamond, '+', Items.nether_star
		});
		GameRegistry.addRecipe(new ItemStack(NumbersItems.multiplication, 4), new Object[]
		{
			"X X",
			" + ",
			"X X",
			'X', Items.diamond, '+', Items.nether_star
		});
		GameRegistry.addRecipe(new ItemStack(NumbersItems.division, 4), new Object[]
		{
			"  X",
			" + ",
			"X  ",
			'X', Items.diamond, '+', Items.nether_star
		});
		
		GameRegistry.addShapelessRecipe(new ItemStack(NumbersItems.numbers[42], 1), new Object[]
		{
			NumbersItems.numbers[6], NumbersItems.numbers[9], NumbersItems.multiplication
		});
		
		GameRegistry.addSmelting(NumbersItems.numbers[99], new ItemStack(Blocks.bedrock, 4), 10);
	}
}
