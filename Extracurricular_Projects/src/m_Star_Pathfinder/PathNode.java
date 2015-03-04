package m_Star_Pathfinder;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;

public class PathNode
{
	private Point				node;
	// Can only go to lower squares with visibility
	private ArrayList<PathNode>	directedEdges	= new ArrayList<PathNode>();

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

	public ArrayList<PathNode> getDirectedEdges()
	{
		return directedEdges;
	}

	public void setDirectedEdges(ArrayList<PathNode> directedEdges)
	{
		this.directedEdges = directedEdges;
	}

	public void addDirectedEdge(PathNode edge)
	{
		directedEdges.add(edge);
	}

	@Override
	public boolean equals(Object o)
	{
		return (o instanceof PathNode) && ((PathNode) o).getNode().equals(this.node) && ((PathNode) o).getDirectedEdges().equals(this.getDirectedEdges());
	}

	@Override
	public int hashCode()
	{
		return Arrays.hashCode(new Object[] { node, directedEdges });
	}

	@Override
	public String toString()
	{
		return "PathNode at (" + node.x + ", " + node.y + ") links to " + directedEdges;
	}
}