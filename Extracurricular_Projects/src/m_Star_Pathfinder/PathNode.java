package m_Star_Pathfinder;

import java.awt.Point;
import java.util.ArrayList;

public class PathNode
{
	private Point				node;
	// Can only go to lower squares with visibility
	private ArrayList<Point>	directedEdges	= new ArrayList<Point>();

	public PathNode(Point node)
	{
		this.node = node;
	}

	public PathNode(int x, int y)
	{
		this.node = new Point(x, y);
	}

	public Point getNode()
	{
		return node;
	}

	public void setNode(Point node)
	{
		this.node = node;
	}

	public ArrayList<Point> getDirectedEdges()
	{
		return directedEdges;
	}

	public void setDirectedEdges(ArrayList<Point> directedEdges)
	{
		this.directedEdges = directedEdges;
	}

	public void addDirectedEdge(Point edge)
	{
		directedEdges.add(edge);
	}

	public String toString()
	{
		return "PathNode at (" + node.x + ", " + node.y + ") links to " + directedEdges;
	}
}