package com.ShiveringCold.Models;

import java.util.HashMap;
import java.util.Map;

import javafx.util.Pair;

public class SCChunkDataManager {
	private Map<String,HeatSourceStorage> storage = new HashMap<String,HeatSourceStorage>();
	private Map<String,HeatSourceStorage> dormantStorage = new HashMap<String,HeatSourceStorage>();
	private static SCChunkDataManager instance;
	
	private SCChunkDataManager(){}
	public static SCChunkDataManager getInstance()
	{
		if(instance == null)
			instance = new SCChunkDataManager();
		return instance;
	}
	
	public HeatSourceStorage get(int x, int z){
		if(storage.containsKey(x+";"+z))
			return storage.get(x+";"+z);
		else
			if(dormantStorage.containsKey(x+";"+z))
			{
				HeatSourceStorage hs = dormantStorage.get(x+";"+z);
				storage.put(x+";"+z, hs);
				dormantStorage.remove(x+";"+z);
				return storage.get(x+";"+z);
			}
		return null;
	}
	public void set(HeatSourceStorage hs,int x, int z)
	{
		storage.put(x+";"+z,hs);
	}
	private void remove(int x, int z)
	{
		storage.remove(x+";"+z);
	}
	public void clear()
	{
		storage.clear();
	}
	public void putDormant(int x, int z){
		HeatSourceStorage hs = get(x, z);
		if(!(hs == null))
		{
			dormantStorage.put(x+";"+z, hs);
			remove(x, z);
		}
	}
}
