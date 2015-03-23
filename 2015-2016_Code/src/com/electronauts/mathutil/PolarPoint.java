package com.electronauts.mathutil;

import java.awt.geom.Point2D;

// TODO: Auto-generated Javadoc
/**
 * The Class PolarPoint.
 */
public class PolarPoint
{
	
	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(final String[] args)
	{
		final PolarPoint p1 = new PolarPoint(-1, Math.PI / 4);
		System.out.println("DEBUG: (" + p1.getX() + ", " + p1.getY() + ")");
	}

	/** The theta. */
	private double	radius, theta;

	/**
	 * Instantiates a new polar point.
	 */
	public PolarPoint()
	{
	}

	/**
	 * Instantiates a new polar point.
	 *
	 * @param radius the radius
	 * @param theta the theta
	 */
	public PolarPoint(final double radius, final double theta)
	{
		this.setRadius(radius);
		this.setTheta(theta);
	}

	/**
	 * Instantiates a new polar point.
	 *
	 * @param p the p
	 */
	public PolarPoint(final Point2D.Double p)
	{
		this.setRadius(Math.sqrt(Math.pow(p.x, 2) + Math.pow(p.y, 2)));
		this.setTheta(Math.atan2(p.y, p.x));
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
	 * Gets the theta.
	 *
	 * @return the theta
	 */
	public double getTheta()
	{
		return this.theta;
	}

	/**
	 * Gets the x.
	 *
	 * @return the x
	 */
	public double getX()
	{
		return this.radius * Math.cos(this.theta);
	}

	/**
	 * Gets the y.
	 *
	 * @return the y
	 */
	public double getY()
	{
		return this.radius * Math.sin(this.theta);
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

	/**
	 * Sets the theta.
	 *
	 * @param theta the new theta
	 */
	public void setTheta(final double theta)
	{
		this.theta = theta;
	}

	/**
	 * To point double.
	 *
	 * @return the point2 d. double
	 */
	public Point2D.Double toPointDouble()
	{
		return new Point2D.Double(this.radius * Math.cos(this.theta), this.radius * Math.sin(this.theta));
	}
}
