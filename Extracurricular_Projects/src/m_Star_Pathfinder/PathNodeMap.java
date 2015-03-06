package m_Star_Pathfinder;

import java.awt.Point;
import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedList;

public class PathNodeMap implements Comparable<PathNodeMap>, Cloneable, Serializable
{
	private static final long		serialVersionUID	= 1L;
	private LinkedList<PathNode>	points;

	public PathNodeMap()
	{
		this.points = new LinkedList<PathNode>();// Test
	}

	public PathNodeMap(final LinkedList<PathNode> points)
	{
		this.points = points;
	}

	public void addPoint(final PathNode p)
	{
		this.points.add(p);
	}

	@Override
	public PathNodeMap clone()
	{
		final LinkedList<PathNode> newList = new LinkedList<PathNode>();

		for (final PathNode p : this.points)
			newList.add(p);

		return new PathNodeMap(newList);
	}

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

	public PathNode[] getArray()
	{
		final PathNode[] result = new PathNode[this.points.size()];
		return this.points.toArray(result);
	}

	public Point[] getPointArray()
	{
		LinkedList<Point> newPoints = new LinkedList<Point>();
		for (PathNode p : points)
		{
			newPoints.add(p.getNode());
		}
		Point[] returnPoints = new Point[newPoints.size()];

		return newPoints.toArray(returnPoints);
	}

	public static double getDistance(final Point p1, final Point p2)
	{
		return Math.sqrt(Math.pow(p2.x - p1.x, 2) + Math.pow(p2.y - p1.y, 2));
	}

	public PathNode getLastPoint()
	{
		return this.points.get(this.points.size() - 1);
	}

	public LinkedList<PathNode> getList()
	{
		return this.points;
	}

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

	public LinkedList<PathNode> getReverseList()
	{
		final Iterator<PathNode> reverseIterator = this.points.descendingIterator();
		final LinkedList<PathNode> newPoints = new LinkedList<PathNode>();

		while (reverseIterator.hasNext())
			newPoints.add(reverseIterator.next());

		return newPoints;
	}

	public PathNodeMap getReverseMap()
	{
		return new PathNodeMap(this.getReverseList());
	}

	public double getTotalDistance()
	{
		double sum = 0;

		for (int i = 0; i < this.points.size() - 1; i++)
			sum += PathNodeMap.getDistance(this.points.get(i).getNode(), this.points.get(i + 1).getNode());

		return sum;
	}

	public void reverseList()
	{
		final Iterator<PathNode> reverseIterator = this.points.descendingIterator();
		final LinkedList<PathNode> newPoints = new LinkedList<PathNode>();

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
		StringBuilder s = new StringBuilder();
		for (PathNode p : points)
		{
			s.append(p.getNode().toString() + " ");
		}
		return s.toString();
	}
}
