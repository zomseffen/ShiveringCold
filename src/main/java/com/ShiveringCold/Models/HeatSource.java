package com.ShiveringCold.Models;

import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

public class HeatSource {
	public int x,y,z;
	public Block b;
	public TileEntity te;
	public HeatSource(int _x,int _y,int _z,Block _b, TileEntity _te)
	{
		x = _x;
		y = _y;
		z = _z;
		b = _b;
		te = _te;
	}
	HeatSource(NBTTagCompound tag){
		x = tag.getInteger("x");
		y = tag.getInteger("y");
		z = tag.getInteger("z");
	}
	NBTTagCompound toTag(){
		NBTTagCompound tag = new NBTTagCompound();
		tag.setInteger("x", x);
		tag.setInteger("y", y);
		tag.setInteger("z", z);
		return tag;
	}
	boolean isCloseBy(int _x, int _y, int _z, int _d)
	{
		return Math.sqrt((x-_x)*(x -_x) + (y-_y)*(y-_y) + (z-_z)*(z-_z)) <= _d;
	}
	public float distanceTo(int _x, int _y, int _z){
		return (float) Math.sqrt((x-_x)*(x -_x) + (y-_y)*(y-_y) + (z-_z)*(z-_z));
	}
}
