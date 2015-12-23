package com.JasonILTG.ScienceMod.manager.power;

import java.util.ArrayList;

import com.JasonILTG.ScienceMod.util.BlockHelper;

import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;

/**
 * An attempt to make power networks.
 * 
 * @deprecated Use {@link PowerRequestPacket} instead.
 * @author JasonILTG and syy1125
 */
@Deprecated
public class PowerNetwork
{
	public ArrayList<PowerNode> nodes;
	
	public class PowerNode
	{
		public PowerManager manager;
		public BlockPos pos;
		public PowerNode[] directions;
		public PowerNetwork network;
		
		public PowerNode(PowerManager manager, BlockPos pos, PowerNetwork network)
		{
			this.manager = manager;
			this.pos = pos;
			directions = new PowerNode[EnumFacing.VALUES.length];
			this.network = network;
		}
		
		@Override
		public boolean equals(Object other)
		{
			return pos.equals(((PowerNode) other).pos);
		}
	}
	
	public PowerNetwork()
	{
		nodes = new ArrayList<PowerNode>();
	}
	
	public PowerNetwork(ArrayList<PowerNode> nodes)
	{
		this.nodes = nodes;
	}
	
	public void addNode(PowerManager manager, BlockPos pos)
	{
		nodes.add(new PowerNode(manager, pos, this));
	}
	
	public void joinNetwork(PowerNetwork network)
	{
		for (PowerNode node : network.nodes)
		{
			nodes.add(node);
		}
	}
	
	public PowerNetwork[] removeNode(BlockPos pos)
	{
		int removeIndex = getNodeIndexFromPos(pos);
		PowerNode toRemove = nodes.get(removeIndex);
		for (int i = 0; i < toRemove.directions.length; i ++)
		{
			PowerNode adj = toRemove.directions[i];
			if (adj == null) continue;
			int adjToRemoveDir = BlockHelper.getOppositeFacingIndex(i);
			adj.directions[adjToRemoveDir] = null;
		}
		nodes.remove(removeIndex);
		
		return checkSplit();
	}
	
	public int getNodeIndexFromPos(BlockPos pos)
	{
		for (int i = 0; i < nodes.size(); i ++)
		{
			if (nodes.get(i).pos.equals(pos)) return i;
		}
		return -1;
	}
	
	public PowerNetwork[] checkSplit()
	{
		boolean[] used = new boolean[nodes.size()];
		for (int i = 0; i < nodes.size(); i ++)
			used[i] = false;
		int numUsed = 0;
		ArrayList<PowerNetwork> split = new ArrayList<PowerNetwork>();
		while (numUsed < nodes.size())
		{
			int prevNumUsed = numUsed - 1;
			split.add(new PowerNetwork());
			while (prevNumUsed != numUsed)
			{
				prevNumUsed = numUsed;
				
			}
		}
		
		return new PowerNetwork[] { this };
	}
}
