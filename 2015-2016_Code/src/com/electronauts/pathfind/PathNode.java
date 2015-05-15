package com.electronauts.pathfind;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;

// TODO: Auto-generated Javadoc
/**
 * The Class PathNode.
 */
public class PathNode
{
	// Can only go to lower squares with visibility
	/** The directed edges. */
	private ArrayList<PathNode>	directedEdges	= new ArrayList<PathNode>();

	/** The node. */
	private Point				node;

	/**
	 * Instantiates a new path node.
	 *
	 * @param x
	 *            the x
	 * @param y
	 *            the y
	 */
	public PathNode(final int x, final int y)
	{
		this.node = new Point(x, y);
	}

	/**
	 * Instantiates a new path node.
	 *
	 * @param node
	 *            the node
	 */
	public PathNode(final Point node)
	{
		this.node = node;
	}

	/**
	 * Adds the directed edge.
	 *
	 * @param edge
	 *            the edge
	 */
	public void addDirectedEdge(final PathNode edge)
	{
		this.directedEdges.add(edge);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(final Object o)
	{
		return o instanceof PathNode && ((PathNode) o).getNode().equals(this.node) && ((PathNode) o).getDirectedEdges().equals(this.getDirectedEdges());
	}

	/**
	 * Gets the directed edges.
	 *
	 * @return the directed edges
	 */
	public ArrayList<PathNode> getDirectedEdges()
	{
		return this.directedEdges;
	}

	/**
	 * Gets the node.
	 *
	 * @return the node
	 */
	public Point getNode()
	{
		return this.node;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode()
	{
		return Arrays.hashCode(new Object[] { this.node, this.directedEdges });
	}

	/**
	 * Sets the directed edges.
	 *
	 * @param directedEdges
	 *            the new directed edges
	 */
	public void setDirectedEdges(final ArrayList<PathNode> directedEdges)
	{
		this.directedEdges = directedEdges;
	}

	/**
	 * Sets the node.
	 *
	 * @param node
	 *            the new node
	 */
	public void setNode(final Point node)
	{
		this.node = node;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{

		return "PathNode at (" + this.node.x + ", " + this.node.y + ")";
	}
}