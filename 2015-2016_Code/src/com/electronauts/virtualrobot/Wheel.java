package com.electronauts.virtualrobot;

public class Wheel
{
	private double	radius;

	public Wheel(final double radius)
	{
		this.radius = radius;
	}

	public double getCircumference()
	{
		return 2 * Math.PI * this.getRadius();
	}

	public double getRadius()
	{
		return this.radius;
	}

	public void setRadius(final double radius)
	{
		this.radius = radius;
	}
}
