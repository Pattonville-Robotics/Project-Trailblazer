package com.electronauts.mathutil;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;

import javax.swing.JComponent;
import javax.swing.JFrame;

import org.apache.commons.math3.util.FastMath;

// TODO: Auto-generated Javadoc
/**
 * The Class Vector2D.
 */
public class Vector2D
{
	/**
	 * Adds the two {@code Vector2D} objects together into a new composite {@code Vector2D}
	 *
	 * @param v1
	 *            the first {@code Vector2D}
	 * @param v2
	 *            the second {@code Vector2D}
	 * @return the addition of {@code v1} and {@code v2}
	 */
	public static Vector2D add(final Vector2D v1, final Vector2D v2)
	{
		return Vector2D.createFromCartesian(v1.getX() + v2.getX(), v1.getY() + v2.getY());
	}

	/**
	 * Creates a new Vector2D from a Cartesian. The Vector stretches from the origin to the specified point.
	 *
	 * @param x
	 *            the x coordinate of the end point of the vector
	 * @param y
	 *            the y coordinate of the end point of the vector
	 * @return a new Vector2D
	 */
	public static Vector2D createFromCartesian(final double x, final double y)
	{
		return new Vector2D(FastMath.atan2(y, x), FastMath.sqrt(FastMath.pow(x, 2) + FastMath.pow(y, 2)));
	}

	/**
	 * Creates a new {@code Vector2D} from a {@code Point2D} object. The {@code Vector2D} extends from the origin to the specified {@code Point2D}.
	 *
	 * @param p
	 *            the {@code Point2D}
	 * @return the {@code Vector2D} that measures the displacement of the {@code Point2D} from the origin.
	 */
	public static Vector2D createFromPoint(final Point2D p)
	{
		return Vector2D.createFromCartesian(p.getX(), p.getY());
	}

	/**
	 * Creates a new {@code Vector2D} from a polar point. The resulting {@code Vector2D} measures the displacement from the origin.
	 *
	 * @param theta
	 *            the angle between a line intersecting both the origin and this point and the positive x-axis
	 * @param rho
	 *            the distance from the origin to this point
	 * @return a {@code Vector2D} that measures the displacement of this point from the origin.
	 */
	public static Vector2D createFromPolar(final double theta, final double rho)
	{
		return new Vector2D(theta, rho);
	}

	/**
	 * Creates a new {@code Vector2D} from a {@link PolarPoint}. The {@code Vector2D} measures the displacement of this {@code PolarPoint} from the origin.
	 *
	 * @param p
	 *            the {@code PolarPoint}
	 * @return a {@code Vector2D} that measures the displacement from the {@code PolarPoint} to the origin.
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
		return v1.getMagnitude() * v2.getMagnitude() * FastMath.cos(v1.getDirection() - v2.getDirection());
	}

	public static void main(final String[] args)
	{
		final Vector2D v1 = Vector2D.createFromPolar(FastMath.PI / 3, 100);
		final JComponent comp = new JComponent()
		{
			private static final long	serialVersionUID	= 1L;

			@Override
			public void paint(final Graphics g)
			{
				final Graphics2D g2d = (Graphics2D) g;
				g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				v1.paintAt(20, 20, g2d);
			}
		};
		final JFrame f = new JFrame();
		f.add(comp);
		f.setSize(400, 400);
		f.setVisible(true);
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
		return this.magnitude * FastMath.cos(this.direction);
	}

	/**
	 * Gets the y.
	 *
	 * @return the y
	 */
	public double getY()
	{
		return this.magnitude * FastMath.sin(this.direction);
	}

	public void paintAt(final double x, final double y, final Graphics2D g2d)
	{
		g2d.setPaint(new GradientPaint((float) x, (float) y, Color.BLUE, (float) (x + this.getX()), (float) (y + this.getY()), Color.RED));
		g2d.drawLine((int) x, (int) y, (int) (x + this.getX()), (int) (y + this.getY()));
		final Path2D.Double p2d = new Path2D.Double();
		final PolarPoint p1 = new PolarPoint(this.getMagnitude() / 5, this.getDirection() + 7 * FastMath.PI / 8);
		final PolarPoint p2 = new PolarPoint(this.getMagnitude() / 5, this.getDirection() - 7 * FastMath.PI / 8);
		p2d.moveTo(x + this.getX(), y + this.getY());
		p2d.lineTo(x + this.getX() + p1.getX(), y + this.getY() + p1.getY());
		p2d.lineTo(x + this.getX() + p2.getX(), y + this.getY() + p2.getY());
		p2d.closePath();
		g2d.fill(p2d);
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
