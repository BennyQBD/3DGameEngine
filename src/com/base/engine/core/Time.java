package com.base.engine.core;

public class Time 
{
	public static final long SECOND = 1000000000L;
	
	private static double delta;
	
	public static long getTime()
	{
		return System.nanoTime();
	}
	
	public static double getDelta()
	{
		return delta;
	}
	
	public static void setDelta(double delta)
	{
		Time.delta = delta;
	}
}
