package m_Star_Pathfinder;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;

public class PathNode
{
	private Point				node;
	// Can only go to lower squares with visibility
	private ArrayList<PathNode>	directedEdges	= new ArrayList<PathNode>();

	public PathNode(final int x, final int y)
	{
		this.node = new Point(x, y);
	}

	public PathNode(final Point node)
	{
		this.node = node;
	}

	public void addDirectedEdge(final PathNode edge)
	{
		this.directedEdges.add(edge);
	}

	@Override
	public boolean equals(final Object o)
	{
		return o instanceof PathNode && ((PathNode) o).getNode().equals(this.node) && ((PathNode) o).getDirectedEdges().equals(this.getDirectedEdges());
	}

	public ArrayList<PathNode> getDirectedEdges()
	{
		return this.directedEdges;
	}

	public Point getNode()
	{
		return this.node;
	}

	@Override
	public int hashCode()
	{
		return Arrays.hashCode(new Object[] { this.node, this.directedEdges });
	}

	public void setDirectedEdges(final ArrayList<PathNode> directedEdges)
	{
		this.directedEdges = directedEdges;
	}

	public void setNode(final Point node)
	{
		this.node = node;
	}

	@Override
	public String toString()
	{
		return "PathNode at (" + this.node.x + ", " + this.node.y + ") links to " + this.directedEdges;
	}
}