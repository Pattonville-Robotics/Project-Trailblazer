package com.electronauts.pathfind;

import java.awt.Point;
import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedList;

public class VertexMap implements Comparable<VertexMap>, Cloneable, Serializable
{
	private static final long	serialVersionUID	= 1L;

	public static double getDistance(final Point p1, final Point p2)
	{
		return Math.sqrt(Math.pow(p2.x - p1.x, 2) + Math.pow(p2.y - p1.y, 2));
	}

	private LinkedList<Point>	points;

	public VertexMap()
	{
		this.points = new LinkedList<Point>();
	}

	public VertexMap(final LinkedList<Point> points)
	{
		this.points = points;
	}

	public void addPoint(final Point p)
	{
		this.points.add(p);
	}

	@Override
	public VertexMap clone()
	{
		final LinkedList<Point> newList = new LinkedList<Point>();

		for (final Point p : this.points)
			newList.add(p);

		return new VertexMap(newList);
	}

	@Override
	public int compareTo(final VertexMap map)
	{
		if (this.getTotalDistance() > map.getTotalDistance())
			return 1;
		else if (this.getTotalDistance() < map.getTotalDistance())
			return -1;
		else
			return 0;
	}

	public Point[] getArray()
	{
		final Point[] result = new Point[this.points.size()];
		return this.points.toArray(result);
	}

	public Point getLastPoint()
	{
		return this.points.get(this.points.size() - 1);
	}

	public LinkedList<Point> getList()
	{
		return this.points;
	}

	public Point getPoint(final int i)
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

	public LinkedList<Point> getReverseList()
	{
		final Iterator<Point> reverseIterator = this.points.descendingIterator();
		final LinkedList<Point> newPoints = new LinkedList<Point>();

		while (reverseIterator.hasNext())
			newPoints.add(reverseIterator.next());

		return newPoints;
	}

	public VertexMap getReverseMap()
	{
		return new VertexMap(this.getReverseList());
	}

	public double getTotalDistance()
	{
		double sum = 0;

		for (int i = 0; i < this.points.size() - 1; i++)
			sum += VertexMap.getDistance(this.points.get(i), this.points.get(i + 1));

		return sum;
	}

	public void reverseList()
	{
		final Iterator<Point> reverseIterator = this.points.descendingIterator();
		final LinkedList<Point> newPoints = new LinkedList<Point>();

		while (reverseIterator.hasNext())
			newPoints.add(reverseIterator.next());

		this.points = newPoints;
	}

	public int size()
	{
		return this.points.size();
	}

	@Override
	public String toString()
	{
		return this.points.toString();
	}
}