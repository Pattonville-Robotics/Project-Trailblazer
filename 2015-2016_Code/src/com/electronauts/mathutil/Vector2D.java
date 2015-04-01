package com.electronauts.mathutil;

import java.awt.geom.Point2D;

public class Vector2D
{
	private double	direction, magnitude;

	private Vector2D(double theta, double rho)
	{
		this.setDirection(theta);
		this.setMagnitude(rho);
	}

	public static Vector2D createFromPolar(double theta, double rho)
	{
		return new Vector2D(theta, rho);
	}

	public static Vector2D createFromCartesian(double x, double y)
	{
		return new Vector2D(Math.atan2(y, x), Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2)));
	}

	public static Vector2D createFromPolarPoint(PolarPoint p)
	{
		return new Vector2D(p.getTheta(), p.getRadius());
	}

	public static Vector2D createFromPoint(Point2D p)
	{
		return Vector2D.createFromCartesian(p.getX(), p.getY());
	}

	public double getMagnitude()
	{
		return magnitude;
	}

	public void setMagnitude(double magnitude)
	{
		this.magnitude = magnitude;
	}

	public double getDirection()
	{
		return direction;
	}

	public void setDirection(double direction)
	{
		this.direction = direction;
	}
}
