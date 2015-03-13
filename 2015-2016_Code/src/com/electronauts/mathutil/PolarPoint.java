package com.electronauts.mathutil;

import java.awt.Point;

public class PolarPoint
{
	private double	radius, theta;

	public PolarPoint(final double radius, final double theta)
	{
		this.setRadius(radius);
		this.setTheta(theta);
	}

	public PolarPoint(final Point p)
	{
	}

	public double getRadius()
	{
		return this.radius;
	}

	public double getTheta()
	{
		return this.theta;
	}

	public void setRadius(final double radius)
	{
		this.radius = radius;
	}

	public void setTheta(final double theta)
	{
		this.theta = theta;
	}
}
