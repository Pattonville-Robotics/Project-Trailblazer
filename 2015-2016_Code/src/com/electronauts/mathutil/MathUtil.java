package com.electronauts.mathutil;

public class MathUtil
{
	private MathUtil()
	{
	}

	public static boolean isFinite(double d)
	{
		return !(Double.isNaN(d) || Double.isInfinite(d));
	}
}
