package com.electronauts.virtualrobot;

// TODO: Auto-generated Javadoc
/**
 * The Class Wheel.
 */
public class Wheel
{
	
	/** The radius. */
	private double	radius;

	/**
	 * Instantiates a new wheel.
	 *
	 * @param radius the radius
	 */
	public Wheel(final double radius)
	{
		this.radius = radius;
	}

	/**
	 * Gets the circumference.
	 *
	 * @return the circumference
	 */
	public double getCircumference()
	{
		return 2 * Math.PI * this.getRadius();
	}

	/**
	 * Gets the radius.
	 *
	 * @return the radius
	 */
	public double getRadius()
	{
		return this.radius;
	}

	/**
	 * Sets the radius.
	 *
	 * @param radius the new radius
	 */
	public void setRadius(final double radius)
	{
		this.radius = radius;
	}
}
