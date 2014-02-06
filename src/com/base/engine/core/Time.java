package com.base.engine.core;

public class Time 
{
	private static final long SECOND = 1000000000L;

	public static double getTime()
	{
		return (double)System.nanoTime()/(double)SECOND;
	}
}
