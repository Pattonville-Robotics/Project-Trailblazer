package com.electronauts.mathutil;

import java.awt.geom.Point2D;

public class Vector2D
{
	public static Vector2D add(final Vector2D v1, final Vector2D v2)
	{
		return Vector2D.createFromCartesian(v1.getX() + v2.getX(), v1.getY() + v2.getY());
	}

	public static Vector2D createFromCartesian(final double x, final double y)
	{
		return new Vector2D(Math.atan2(y, x), Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2)));
	}

	public static Vector2D createFromPoint(final Point2D p)
	{
		return Vector2D.createFromCartesian(p.getX(), p.getY());
	}

	public static Vector2D createFromPolar(final double theta, final double rho)
	{
		return new Vector2D(theta, rho);
	}

	public static Vector2D createFromPolarPoint(final PolarPoint p)
	{
		return new Vector2D(p.getTheta(), p.getRadius());
	}

	public static Vector2D createNormalizedVector(final double theta)
	{
		return new Vector2D(theta, 1);
	}

	private double	direction, magnitude;

	private Vector2D(final double theta, final double rho)
	{
		this.setDirection(theta);
		this.setMagnitude(rho);
	}

	@Override
	public boolean equals(final Object o)
	{
		return o instanceof Vector2D && ((Vector2D) o).getDirection() == this.getDirection() && ((Vector2D) o).getMagnitude() == this.getMagnitude();
	}

	public double getDirection()
	{
		return this.direction;
	}

	public double getMagnitude()
	{
		return this.magnitude;
	}

	public Vector2D getNormalizedVector()
	{
		return Vector2D.createFromPolar(this.direction, 1);
	}

	public double getX()
	{
		return this.magnitude * Math.cos(this.direction);
	}

	public double getY()
	{
		return this.magnitude * Math.sin(this.direction);
	}

	public void setDirection(final double direction)
	{
		this.direction = direction;
	}

	public static double dotProduct(Vector2D v1, Vector2D v2)
	{
		return v1.getMagnitude() * v2.getMagnitude() * Math.cos(v1.getDirection() - v2.getDirection());
	}

	public void setMagnitude(final double magnitude)
	{
		this.magnitude = magnitude;
	}
}
