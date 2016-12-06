package com.ShiveringCold.Models;

import net.minecraft.nbt.NBTTagCompound;

public class HeatSourceStorageFactory {
	private static HeatSourceStorageFactory instance;
	private HeatSourceStorageFactory()
	{
		
	}
	public static HeatSourceStorageFactory getInstance()
	{
		if(instance == null)
			instance = new HeatSourceStorageFactory();
		return instance;
	}
	
	protected static void setInstance(HeatSourceStorageFactory newInstance)
	{
		instance = newInstance;
	}
	
	public void test(){
		HeatSourceStorage hs = new HeatSourceStorage();
	}
	
	public HeatSourceStorage produceStorage(NBTTagCompound tag)
	{
		HeatSourceStorage hs = new HeatSourceStorage();
		int i = 0;
		while(tag.hasKey(""+i))
		{
			hs.addHS(new HeatSource(tag.getCompoundTag(""+i)));
			i++;
		}
		return hs;
	}
	
	public HeatSource produceSource(NBTTagCompound tag)
	{
		return new HeatSource(tag);
	}
	
	public HeatSourceStorage produce()
	{
		return new HeatSourceStorage();
	}
}
