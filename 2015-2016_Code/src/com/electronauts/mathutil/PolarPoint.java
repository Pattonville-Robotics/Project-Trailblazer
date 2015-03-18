package com.electronauts.mathutil;

import java.awt.geom.Point2D;

public class PolarPoint
{
	public static void main(final String[] args)
	{
		final PolarPoint p1 = new PolarPoint(-1, Math.PI / 4);
		System.out.println("DEBUG: (" + p1.getX() + ", " + p1.getY() + ")");
	}

	private double	radius, theta;

	public PolarPoint()
	{
	}

	public PolarPoint(final double radius, final double theta)
	{
		this.setRadius(radius);
		this.setTheta(theta);
	}

	public PolarPoint(final Point2D.Double p)
	{
		this.setRadius(Math.sqrt(Math.pow(p.x, 2) + Math.pow(p.y, 2)));
		this.setTheta(Math.atan2(p.y, p.x));
	}

	public double getRadius()
	{
		return this.radius;
	}

	public double getTheta()
	{
		return this.theta;
	}

	public double getX()
	{
		return this.radius * Math.cos(this.theta);
	}

	public double getY()
	{
		return this.radius * Math.sin(this.theta);
	}

	public void setRadius(final double radius)
	{
		this.radius = radius;
	}

	public void setTheta(final double theta)
	{
		this.theta = theta;
	}

	public Point2D.Double toPointDouble()
	{
		return new Point2D.Double(this.radius * Math.cos(this.theta), this.radius * Math.sin(this.theta));
	}
}
