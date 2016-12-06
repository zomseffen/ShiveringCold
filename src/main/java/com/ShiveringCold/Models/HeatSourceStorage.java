package com.ShiveringCold.Models;

import java.util.HashSet;

import net.minecraft.nbt.NBTTagCompound;

public class HeatSourceStorage {
	public Boolean isUnloaded = false;
	private HashSet<HeatSource> hs = new HashSet<HeatSource>();
	public HashSet<HeatSource> getCloseBy(int x, int y, int z, int d){
		HashSet<HeatSource> out = new HashSet<HeatSource>();
		if(!hs.isEmpty())
		{
			for(HeatSource h:hs){
				if(h.isCloseBy(x, y, z, d))
					out.add(h);
			}
		}
		return out;
	}
	public void addHS(HeatSource h)
	{
		hs.add(h);
	}
	public void removeHS(HeatSource h)
	{
		hs.remove(h);
	}
	public NBTTagCompound toTag()
	{
		NBTTagCompound tag = new NBTTagCompound();
		int i = 0;
		for(HeatSource h:hs){
			tag.setTag(""+i,h.toTag());
			i++;
		}
		return tag;
	}
}
