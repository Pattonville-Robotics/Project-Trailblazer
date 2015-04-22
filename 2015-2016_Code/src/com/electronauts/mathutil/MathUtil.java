package com.electronauts.mathutil;

import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

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

	public static double distance(Point2D p1, Point2D p2)
	{
		return distance(p1.getX(), p1.getY(), p2.getX(), p2.getY());
	}

	public static double distance(double x1, double y1, double x2, double y2)
	{
		return Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
	}

	/**
	 * All methods are static.
	 */
	private MathUtil()
	{
	}

	public static double angleTo(Point2D p1, Point2D p2)
	{
		return Math.atan2(p2.getY() - p1.getY(), p2.getX() - p1.getX());
	}

	public static Point2D getIntersectionPoint(final Point2D p1, final Point2D p2, final Point2D p3, final Point2D p4)
	{
		// Lines from p1 to p2 and from p3 to p4
		// Returns null if they do not intersect
		final double denominator = (p1.getX() - p2.getX()) * (p3.getY() - p4.getY()) - (p1.getY() - p2.getY()) * (p3.getX() - p4.getX());
		if (denominator == 0)
			return null;
		else
		{
			final double top1 = p1.getX() * p2.getY() - p1.getY() * p2.getX();
			final double top2 = p3.getX() * p4.getY() - p3.getY() * p4.getX();
			final Point2D.Double p = new Point2D.Double((top1 * (p3.getX() - p4.getX()) - (p1.getX() - p2.getX()) * top2) / denominator, (top1
					* (p3.getY() - p4.getY()) - (p1.getY() - p2.getY()) * top2)
					/ denominator);
			if (isInRange(p.x, p1.getX(), p2.getX()) && isInRange(p.x, p3.getX(), p4.getX()) && isInRange(p.y, p1.getY(), p2.getY())
					&& isInRange(p.y, p3.getY(), p4.getY()))
				return p;
			else
				return null;
		}
	}

	public static Point2D getIntersectionPoint(Line2D l1, Line2D l2)
	{
		return getIntersectionPoint(l1.getP1(), l1.getP2(), l2.getP1(), l2.getP2());
	}

	public static boolean isInRange(final double value, final double range1, final double range2)
	{
		return range2 > range1 ? value > range1 && value < range2 : value > range2 && value < range1;
	}
}
