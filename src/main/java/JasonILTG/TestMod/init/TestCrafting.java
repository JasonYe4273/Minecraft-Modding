package JasonILTG.TestMod.init;

import java.util.ArrayList;

import com.jcraft.jorbis.Block;

import JasonILTG.TestMod.Reference;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class TestCrafting
{
	public static void init()
	{
		GameRegistry.addShapelessRecipe(new ItemStack(TestItems.numbers[0], 1), Blocks.dragon_egg);
		GameRegistry.addRecipe(new ItemStack(Blocks.dragon_egg, 1), new Object[]
		{
			"OOO",
			"OXO",
			"OOO",
			'O', TestItems.numbers[99], 'X', TestItems.numbers[0]
		});
		
		GameRegistry.addShapelessRecipe(new ItemStack(TestItems.numbers[1], 1), Items.diamond);

		for( int n = 1; n < Reference.MAX_NUMBER; n++ )
		{
			for( int a = 0; a <= n; a++ )
			{
				GameRegistry.addRecipe(new ItemStack(TestItems.numbers[n], 1), new Object[]
				{
					"A+B",
					'A', TestItems.numbers[a], '+', TestItems.addition, 'B', TestItems.numbers[n-a]
				});
			}
			
			for( int a = n; a < Reference.MAX_NUMBER; a++ )
			{
				GameRegistry.addRecipe(new ItemStack(TestItems.numbers[n], 1), new Object[]
				{
					"A-B",
					'A', TestItems.numbers[a], '-', TestItems.subtraction, 'B', TestItems.numbers[a-n]
				});
			}
			
			ArrayList<Integer> factors = Reference.factor(n);
			for( int f : factors )
			{
				GameRegistry.addRecipe(new ItemStack(TestItems.numbers[n], 1), new Object[]
				{
					"AXB",
					'A', TestItems.numbers[f], 'X', TestItems.multiplication, 'B', TestItems.numbers[n/f]
				});
				
				GameRegistry.addRecipe(new ItemStack(TestItems.numbers[f], 1), new Object[]
				{
					"A/B",
					'A', TestItems.numbers[n], '/', TestItems.division, 'B', TestItems.numbers[n/f]
				});
				
				if( f != n/f )
				{
					GameRegistry.addRecipe(new ItemStack(TestItems.numbers[n/f], 1), new Object[]
					{
						"A/B",
						'A', TestItems.numbers[n], '/', TestItems.division, 'B', TestItems.numbers[f]
					});
				}
			}
		}
		
		for( int a = 1; a < Reference.MAX_NUMBER; a++ )
		{
			GameRegistry.addRecipe(new ItemStack(TestItems.numbers[0], 1), new Object[]
			{
				"AXB",
				'A', TestItems.numbers[a], 'X', TestItems.multiplication, 'B', TestItems.numbers[0]
			});
			
			GameRegistry.addRecipe(new ItemStack(TestItems.numbers[0], 1), new Object[]
			{
				"AXB",
				'A', TestItems.numbers[0], 'X', TestItems.multiplication, 'B', TestItems.numbers[a]
			});
		}
		GameRegistry.addRecipe(new ItemStack(TestItems.numbers[0], 1), new Object[]
		{
			"AXA",
			'A', TestItems.numbers[0], 'X', TestItems.multiplication
		});
		
		GameRegistry.addRecipe(new ItemStack(TestItems.addition, 4), new Object[]
		{
			" X ",
			"X+X",
			" X ",
			'X', Items.diamond, '+', Items.nether_star
		});
		GameRegistry.addRecipe(new ItemStack(TestItems.subtraction, 4), new Object[]
		{
			"X+X",
			'X', Items.diamond, '+', Items.nether_star
		});
		GameRegistry.addRecipe(new ItemStack(TestItems.multiplication, 4), new Object[]
		{
			"X X",
			" + ",
			"X X",
			'X', Items.diamond, '+', Items.nether_star
		});
		GameRegistry.addRecipe(new ItemStack(TestItems.division, 4), new Object[]
		{
			"  X",
			" + ",
			"X  ",
			'X', Items.diamond, '+', Items.nether_star
		});
		
		GameRegistry.addShapelessRecipe(new ItemStack(TestItems.numbers[42], 1), new Object[]
		{
			TestItems.numbers[6], TestItems.numbers[9], TestItems.multiplication
		});
		
		GameRegistry.addSmelting(TestItems.numbers[99], new ItemStack(Blocks.bedrock, 4), 10);
	}
}
