package com.electronauts.mathutil;

// TODO: Auto-generated Javadoc
/**
 * The Class MathUtil.
 */
public class MathUtil
{

	/**
	 * Checks if is finite.
	 *
	 * @param d
	 *            the d
	 * @return true, if is finite
	 */
	public static boolean isFinite(final double d)
	{
		return !(Double.isNaN(d) || Double.isInfinite(d));
	}

	/**
	 * Instantiates a new math util.
	 */
	private MathUtil()
	{
	}
}
