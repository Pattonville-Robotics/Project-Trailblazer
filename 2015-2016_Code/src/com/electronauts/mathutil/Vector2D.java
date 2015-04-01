package com.electronauts.mathutil;

import java.awt.geom.Point2D;

// TODO: Auto-generated Javadoc
/**
 * The Class Vector2D.
 */
public class Vector2D
{

	/**
	 * Adds the.
	 *
	 * @param v1
	 *            the v1
	 * @param v2
	 *            the v2
	 * @return the vector2 d
	 */
	public static Vector2D add(final Vector2D v1, final Vector2D v2)
	{
		return Vector2D.createFromCartesian(v1.getX() + v2.getX(), v1.getY() + v2.getY());
	}

	/**
	 * Creates the from cartesian.
	 *
	 * @param x
	 *            the x
	 * @param y
	 *            the y
	 * @return the vector2 d
	 */
	public static Vector2D createFromCartesian(final double x, final double y)
	{
		return new Vector2D(Math.atan2(y, x), Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2)));
	}

	/**
	 * Creates the from point.
	 *
	 * @param p
	 *            the p
	 * @return the vector2 d
	 */
	public static Vector2D createFromPoint(final Point2D p)
	{
		return Vector2D.createFromCartesian(p.getX(), p.getY());
	}

	/**
	 * Creates the from polar.
	 *
	 * @param theta
	 *            the theta
	 * @param rho
	 *            the rho
	 * @return the vector2 d
	 */
	public static Vector2D createFromPolar(final double theta, final double rho)
	{
		return new Vector2D(theta, rho);
	}

	/**
	 * Creates the from polar point.
	 *
	 * @param p
	 *            the p
	 * @return the vector2 d
	 */
	public static Vector2D createFromPolarPoint(final PolarPoint p)
	{
		return new Vector2D(p.getTheta(), p.getRadius());
	}

	/**
	 * Creates the normalized vector.
	 *
	 * @param theta
	 *            the theta
	 * @return the vector2 d
	 */
	public static Vector2D createNormalizedVector(final double theta)
	{
		return new Vector2D(theta, 1);
	}

	/**
	 * Dot product.
	 *
	 * @param v1
	 *            the v1
	 * @param v2
	 *            the v2
	 * @return the double
	 */
	public static double dotProduct(final Vector2D v1, final Vector2D v2)
	{
		return v1.getMagnitude() * v2.getMagnitude() * Math.cos(v1.getDirection() - v2.getDirection());
	}

	/** The magnitude. */
	private double	direction, magnitude;

	/**
	 * Instantiates a new vector2 d.
	 *
	 * @param theta
	 *            the theta
	 * @param rho
	 *            the rho
	 */
	private Vector2D(final double theta, final double rho)
	{
		this.setDirection(theta);
		this.setMagnitude(rho);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(final Object o)
	{
		return o instanceof Vector2D && ((Vector2D) o).getDirection() == this.getDirection() && ((Vector2D) o).getMagnitude() == this.getMagnitude();
	}

	/**
	 * Gets the direction.
	 *
	 * @return the direction
	 */
	public double getDirection()
	{
		return this.direction;
	}

	/**
	 * Gets the magnitude.
	 *
	 * @return the magnitude
	 */
	public double getMagnitude()
	{
		return this.magnitude;
	}

	/**
	 * Gets the normalized vector.
	 *
	 * @return the normalized vector
	 */
	public Vector2D getNormalizedVector()
	{
		return Vector2D.createFromPolar(this.direction, 1);
	}

	/**
	 * Gets the x.
	 *
	 * @return the x
	 */
	public double getX()
	{
		return this.magnitude * Math.cos(this.direction);
	}

	/**
	 * Gets the y.
	 *
	 * @return the y
	 */
	public double getY()
	{
		return this.magnitude * Math.sin(this.direction);
	}

	/**
	 * Sets the direction.
	 *
	 * @param direction
	 *            the new direction
	 */
	public void setDirection(final double direction)
	{
		this.direction = direction;
	}

	/**
	 * Sets the magnitude.
	 *
	 * @param magnitude
	 *            the new magnitude
	 */
	public void setMagnitude(final double magnitude)
	{
		this.magnitude = magnitude;
	}
}
