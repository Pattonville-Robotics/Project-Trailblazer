package m_Star_Pathfinder;

import java.awt.Point;
import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedList;

public class VertexMap implements Comparable<VertexMap>, Cloneable, Serializable
{
	private static final long	serialVersionUID	= 1L;
	private LinkedList<Point>	points;

	public VertexMap()
	{
		this.points = new LinkedList<Point>();
	}

	public VertexMap(LinkedList<Point> points)
	{
		this.points = points;
	}

	@Override
	public VertexMap clone()
	{
		LinkedList<Point> newList = new LinkedList<Point>();

		for (Point p : points)
		{
			newList.add(p);
		}

		return new VertexMap(newList);
	}

	public void reverseList()
	{
		Iterator<Point> reverseIterator = points.descendingIterator();
		LinkedList<Point> newPoints = new LinkedList<Point>();

		while (reverseIterator.hasNext())
		{
			newPoints.add(reverseIterator.next());
		}

		points = newPoints;
	}

	public LinkedList<Point> getReverseList()
	{
		Iterator<Point> reverseIterator = points.descendingIterator();
		LinkedList<Point> newPoints = new LinkedList<Point>();

		while (reverseIterator.hasNext())
		{
			newPoints.add(reverseIterator.next());
		}

		return newPoints;
	}

	public VertexMap getReverseMap()
	{
		return new VertexMap(this.getReverseList());
	}

	public int size()
	{
		return points.size();
	}

	public LinkedList<Point> getList()
	{
		return points;
	}

	public void addPoint(Point p)
	{
		points.add(p);
	}

	public Point getPoint(int i)
	{
		try
		{
			return points.get(i);
		}
		catch (Exception e)
		{
			System.out.println("Tried to get point at " + i + ", but the size of the array is only " + points.size() + "!");
			return this.getPoint(i - 1);
		}
	}

	public Point getLastPoint()
	{
		return points.get(points.size() - 1);
	}

	public double getTotalDistance()
	{
		double sum = 0;

		for (int i = 0; i < points.size() - 1; i++)
		{
			sum += getDistance(points.get(i), points.get(i + 1));
		}

		return sum;
	}

	public double getDistance(Point p1, Point p2)
	{
		return Math.sqrt(Math.pow(p2.x - p1.x, 2) + Math.pow(p2.y - p1.y, 2));
	}

	public Point[] getArray()
	{
		Point[] result = new Point[points.size()];
		return points.toArray(result);
	}

	@Override
	public int compareTo(VertexMap map)
	{
		if (this.getTotalDistance() > map.getTotalDistance())
			return 1;
		else if (this.getTotalDistance() < map.getTotalDistance())
			return -1;
		else
			return 0;
	}

	@Override
	public String toString()
	{
		return points.toString();
	}
}