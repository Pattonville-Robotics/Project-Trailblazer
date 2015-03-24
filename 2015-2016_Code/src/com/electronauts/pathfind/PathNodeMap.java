package com.electronauts.pathfind;

import java.awt.Point;
import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedList;

// TODO: Auto-generated Javadoc
/**
 * The Class PathNodeMap.
 */
public class PathNodeMap implements Comparable<PathNodeMap>, Cloneable, Serializable
{

	/** The Constant serialVersionUID. */
	private static final long	serialVersionUID	= 1L;

	/**
	 * Gets the distance.
	 *
	 * @param p1
	 *            the p1
	 * @param p2
	 *            the p2
	 * @return the distance
	 */
	public static double getDistance(final Point p1, final Point p2)
	{
		return Math.sqrt(Math.pow(p2.x - p1.x, 2) + Math.pow(p2.y - p1.y, 2));
	}

	/** The points. */
	private LinkedList<PathNode>	points;

	/**
	 * Instantiates a new path node map.
	 */
	public PathNodeMap()
	{
		this.points = new LinkedList<PathNode>();
	}

	/**
	 * Instantiates a new path node map.
	 *
	 * @param points
	 *            the points
	 */
	public PathNodeMap(final LinkedList<PathNode> points)
	{
		this.points = points;
	}

	/**
	 * Adds the point.
	 *
	 * @param p
	 *            the p
	 */
	public void addPoint(final PathNode p)
	{
		this.points.add(p);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#clone()
	 */
	@Override
	public PathNodeMap clone()
	{
		final LinkedList<PathNode> newList = new LinkedList<PathNode>();

		for (final PathNode p : this.points)
			newList.add(p);

		return new PathNodeMap(newList);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(final PathNodeMap map)
	{
		if (this.getTotalDistance() > map.getTotalDistance())
			return 1;
		else if (this.getTotalDistance() < map.getTotalDistance())
			return -1;
		else
			return 0;
	}

	/**
	 * Gets the array.
	 *
	 * @return the array
	 */
	public PathNode[] getArray()
	{
		final PathNode[] result = new PathNode[this.points.size()];
		return this.points.toArray(result);
	}

	/**
	 * Gets the last point.
	 *
	 * @return the last point
	 */
	public PathNode getLastPoint()
	{
		return this.points.get(this.points.size() - 1);
	}

	/**
	 * Gets the list.
	 *
	 * @return the list
	 */
	public LinkedList<PathNode> getList()
	{
		return this.points;
	}

	/**
	 * Gets the point.
	 *
	 * @param i
	 *            the i
	 * @return the point
	 */
	public PathNode getPoint(final int i)
	{
		try
		{
			return this.points.get(i);
		}
		catch (final Exception e)
		{
			System.out.println("Tried to get point at " + i + ", but the size of the array is only " + this.points.size() + "!");
			return this.getPoint(i - 1);
		}
	}

	/**
	 * Gets the point array.
	 *
	 * @return the point array
	 */
	public Point[] getPointArray()
	{
		final LinkedList<Point> newPoints = new LinkedList<Point>();
		for (final PathNode p : this.points)
			newPoints.add(p.getNode());
		final Point[] returnPoints = new Point[newPoints.size()];

		return newPoints.toArray(returnPoints);
	}

	/**
	 * Gets the reverse list.
	 *
	 * @return the reverse list
	 */
	public LinkedList<PathNode> getReverseList()
	{
		final Iterator<PathNode> reverseIterator = this.points.descendingIterator();
		final LinkedList<PathNode> newPoints = new LinkedList<PathNode>();

		while (reverseIterator.hasNext())
			newPoints.add(reverseIterator.next());

		return newPoints;
	}

	/**
	 * Gets the reverse map.
	 *
	 * @return the reverse map
	 */
	public PathNodeMap getReverseMap()
	{
		return new PathNodeMap(this.getReverseList());
	}

	/**
	 * Gets the total distance.
	 *
	 * @return the total distance
	 */
	public double getTotalDistance()
	{
		double sum = 0;

		for (int i = 0; i < this.points.size() - 1; i++)
			sum += PathNodeMap.getDistance(this.points.get(i).getNode(), this.points.get(i + 1).getNode());

		return sum;
	}

	/**
	 * Reverse list.
	 */
	public void reverseList()
	{
		final Iterator<PathNode> reverseIterator = this.points.descendingIterator();
		final LinkedList<PathNode> newPoints = new LinkedList<PathNode>();

		while (reverseIterator.hasNext())
			newPoints.add(reverseIterator.next());

		this.points = newPoints;
	}

	/**
	 * Size.
	 *
	 * @return the int
	 */
	public int size()
	{
		return this.points.size();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		final StringBuilder s = new StringBuilder();
		for (final PathNode p : this.points)
			s.append(p.getNode().toString() + " ");
		return s.toString();
	}
}
