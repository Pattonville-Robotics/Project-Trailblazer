package com.electronauts.pathfind;

import java.awt.Point;
import java.util.AbstractSet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

// TODO: Auto-generated Javadoc
/**
 * The Class PathfindAI.
 */
public class PathfindAI implements Runnable
{

	/**
	 * Compute distance.
	 *
	 * @param grid
	 *            the grid
	 * @param start
	 *            the start
	 */
	public static void computeDistance(final Grid grid, final Point start)
	{
		for (int y = 0; y < grid.getGrid().length; y++)
			for (int x = 0; x < grid.getGrid()[y].length; x++)
				grid.setSquareDistance(new Point(x, y), -2);
		final List<Point> points = new ArrayList<Point>(grid.getGrid().length * 4);
		final AbstractSet<Point> pointSet = new HashSet<Point>((int) Math.pow(grid.getGrid().length, 2));
		points.add(start);
		boolean noMoreLeft = false;
		int distance = 0;
		while (!noMoreLeft)
		{
			for (int i = 0; i < points.size(); i++)
				grid.setSquareDistance(points.get(i), distance);

			final List<Point> newPoints = new ArrayList<Point>();
			int numFailedCells = 0;

			final int[] xMod = new int[] { 1, -1, 0, 0 };
			final int[] yMod = new int[] { 0, 0, 1, -1 };

			for (int i = 0; i < points.size(); i++)
			{
				// System.out.println("Point " + i + " (" + points.get(i).x +
				// ", " + points.get(i).y + ")");
				int numFailed = 0;
				for (int j = 0; j < xMod.length; j++)
				{
					final Point prospectivePoint = new Point(points.get(i).x + xMod[j], points.get(i).y + yMod[j]);
					if (grid.canAccess(prospectivePoint)
							&& (grid.getSquareCopy(prospectivePoint).getContents() == SquareType.EMPTY || grid.getSquareCopy(prospectivePoint).getContents() == SquareType.FINISH))
						// If it's not outside the grid and is an empty square
					{
						if (!pointSet.contains(new Point(points.get(i).x + xMod[j], points.get(i).y + yMod[j])))
						{
							newPoints.add(new Point(points.get(i).x + xMod[j], points.get(i).y + yMod[j]));
							pointSet.add(new Point(points.get(i).x + xMod[j], points.get(i).y + yMod[j]));
						}
						else
						{
							// System.out.println("Tried to add a point twice!");
						}
						// Add it to the new list
					}
					else
						numFailed++;
				}

				if (numFailed == 4) numFailedCells++; // This cell completely
				// failed
			}
			// System.out.println(distance);
			distance++;
			if (numFailedCells == points.size() || points.size() == 0) noMoreLeft = true; // End
			// looping
			points.clear();
			points.addAll(newPoints);
			grid.findFurthestPoint();
		}
	}

	/**
	 * Compute paths.
	 *
	 * @param grid
	 *            the grid
	 */
	public static void computePaths(final Grid grid)
	{
		final ArrayList<VertexMap> paths = new ArrayList<VertexMap>();
		paths.add(new VertexMap());
		paths.get(0).addPoint(grid.getFinishPoint());
		// System.out.println(paths);

		boolean allZero = false;
		int numZero = 0;
		// int progressFromFinish = 0;
		while (!allZero)
		{
			numZero = 0;
			for (int i = 0; i < paths.size(); i++)
			{
				final ArrayList<Point> adjacent = grid.getLowestAdjacentSquares(paths.get(i).getLastPoint());

				switch (adjacent.size())
				// These handle the different methods of path diverging
				{
				case 1:
					paths.get(i).addPoint(adjacent.get(0));
					break;
				case 2:
					VertexMap altMap1 = paths.get(i).clone();
					paths.get(i).addPoint(adjacent.get(0));
					altMap1.addPoint(adjacent.get(1));
					paths.add(altMap1);
					i--;
					break;
				case 3:
					System.out.println("Oh baby, a triple!");
					altMap1 = paths.get(i).clone();
					VertexMap altMap2 = paths.get(i).clone();
					paths.get(i).addPoint(adjacent.get(0));
					altMap1.addPoint(adjacent.get(1));
					altMap2.addPoint(adjacent.get(2));
					paths.add(altMap1);
					paths.add(altMap2);
					i--;
					break;
				case 4:
					System.out.println("Mum, get the camera!");
					altMap1 = paths.get(i).clone();
					altMap2 = paths.get(i).clone();
					final VertexMap altMap3 = paths.get(i).clone();
					paths.get(i).addPoint(adjacent.get(0));
					altMap1.addPoint(adjacent.get(1));
					altMap2.addPoint(adjacent.get(2));
					altMap3.addPoint(adjacent.get(3));
					paths.add(altMap1);
					paths.add(altMap2);
					paths.add(altMap3);
					i--;
					break;
				case 0:
					// allZero = true;
					numZero++;
					// System.out.println("Found zero adjacent lower squares");
					// //
					break;
				default:
					// allZero = true;
					numZero++;
					System.out.println("Less than 0 or more than 4 adjacent points were returned! This is a sign of something bad!"); // DEBUG
					break;
				}
			}
			// System.out.println(numZero + " paths out of " + paths.size() +
			// " paths have finished.");
			if (numZero == paths.size()) allZero = true;
		}

		for (int i = 0; i < paths.size(); i++)
			paths.get(i).reverseList();

		grid.setPaths(paths);
	}

	/**
	 * Connect all nodes.
	 *
	 * @param grid
	 *            the grid
	 */
	public static void connectAllNodes(final Grid grid)
	{
		final ArrayList<PathNodeMap> maps = new ArrayList<PathNodeMap>();
		maps.add(new PathNodeMap());
		maps.get(0).addPoint(grid.getNodes().get(0));

		while (true)
		{
			// System.out.println(maps.size());
			for (int i = 0; i < maps.size(); i++)
				if (maps.get(i).getLastPoint().getDirectedEdges().size() != 0)
				{
					// System.out.println("i = " + i);
					for (int j = 1; j < maps.get(i).getLastPoint().getDirectedEdges().size(); j++)
					{
						maps.add(maps.get(i).clone());
						maps.get(maps.size() - 1).addPoint(maps.get(i).getLastPoint().getDirectedEdges().get(j));
					}
					maps.get(i).addPoint(maps.get(i).getLastPoint().getDirectedEdges().get(0));
				}
			int numFinished = 0;

			for (final PathNodeMap p : maps)
				if (grid.getSquareCopy(p.getLastPoint().getNode()).getDistance() == 0) numFinished++;

			if (numFinished == maps.size()) break;
		}

		Collections.sort(maps);
		grid.setPathNodeMaps(maps);
	}

	/**
	 * Connect nodes.
	 *
	 * @param grid
	 *            the grid
	 */
	public static void connectNodes(final Grid grid)
	// The first node is always the finish point and the last is always the
	// start
	{
		final PathNodeMap map = new PathNodeMap();
		PathNode current = grid.getNodes().get(0);

		while (current.getDirectedEdges().size() != 0)
		{
			int bestIndex = 0;
			for (int i = 0; i < current.getDirectedEdges().size(); i++)
				if (grid.getSquareCopy(current.getDirectedEdges().get(i).getNode()).getDistance() <= grid.getSquareCopy(
						current.getDirectedEdges().get(bestIndex).getNode()).getDistance()) bestIndex = i;
			map.addPoint(current);
			current = current.getDirectedEdges().get(bestIndex);
		}
		map.addPoint(current);
		grid.setPathNodeMap(map);
	}

	/**
	 * Identify nodes.
	 *
	 * @param grid
	 *            the grid
	 */
	public static void identifyNodes(final Grid grid)
	{
		final int[] xVarianceDiagonal = new int[] { -1, 1, 1, -1 };
		final int[] yVarianceDiagonal = new int[] { -1, -1, 1, 1 };

		final ArrayList<PathNode> nodes = new ArrayList<PathNode>();
		final HashSet<PathNode> nodeSet = new HashSet<PathNode>();

		nodes.add(new PathNode(grid.getFinishPoint()));
		nodeSet.add(new PathNode(grid.getFinishPoint()));

		for (int y = 0; y < grid.getGrid().length; y++)
			for (int x = 0; x < grid.getGrid()[y].length; x++)
				if (grid.getSquareCopy(new Point(x, y)).getContents() == SquareType.HAZARD)
					for (int i = 0; i < xVarianceDiagonal.length && i < yVarianceDiagonal.length; i++)
						if (grid.canAccess(new Point(x + xVarianceDiagonal[i], y + yVarianceDiagonal[i]))
								&& grid.getSquareCopy(new Point(x + xVarianceDiagonal[i], y + yVarianceDiagonal[i])).getContents() == SquareType.EMPTY
								&& grid.getSquareCopy(new Point(x + xVarianceDiagonal[i], y)).getContents() != SquareType.HAZARD
								&& grid.getSquareCopy(new Point(x, y + yVarianceDiagonal[i])).getContents() != SquareType.HAZARD
								&& !nodeSet.contains(new PathNode(x + xVarianceDiagonal[i], y + yVarianceDiagonal[i])))
						{
							nodes.add(new PathNode(x + xVarianceDiagonal[i], y + yVarianceDiagonal[i]));
							nodeSet.add(new PathNode(x + xVarianceDiagonal[i], y + yVarianceDiagonal[i]));
						}
		nodes.add(new PathNode(grid.getStartPoint()));
		nodeSet.add(new PathNode(grid.getStartPoint()));

		grid.setNodes(nodes);
		for (int i = 0; i < nodes.size(); i++)
			for (int j = 0; j < nodes.size(); j++)
				if (i != j && !grid.collidesWithHazard(nodes.get(i).getNode(), nodes.get(j).getNode())
				&& grid.getSquareCopy(nodes.get(i).getNode()).getDistance() > grid.getSquareCopy(nodes.get(j).getNode()).getDistance())
					nodes.get(i).addDirectedEdge(nodes.get(j));

	}

	/**
	 * Optimize path.
	 *
	 * @param grid
	 *            the grid
	 * @param map
	 *            the map
	 * @return the vertex map
	 */
	public static VertexMap optimizePath(final Grid grid, final VertexMap map)
	{
		final VertexMap newMap = new VertexMap();
		newMap.addPoint(map.getPoint(0));

		for (int i = 0; i < map.size(); i++)
		{
			int max = 0;
			for (int j = i; j < map.size(); j++)
				if (!grid.collidesWithHazard(map.getPoint(i), map.getPoint(j))) max = j;
			newMap.addPoint(map.getPoint(max));
			if (i != max) i = max - 1;
		}
		newMap.addPoint(map.getLastPoint());

		return newMap;
	}

	/**
	 * Optimize paths.
	 *
	 * @param grid
	 *            the grid
	 */
	public static void optimizePaths(final Grid grid)
	{
		final ArrayList<VertexMap> paths = grid.getPaths();
		final ArrayList<VertexMap> newPaths = new ArrayList<VertexMap>(paths.size());

		for (int i = 0; i < paths.size(); i++)
			// System.out.println("Progress: " + i + " / " + paths.size());
			newPaths.add(PathfindAI.optimizePath(grid, paths.get(i)));
		Collections.sort(newPaths);
		grid.setPaths(newPaths);
	}

	/**
	 * Instantiates a new pathfind ai.
	 */
	public PathfindAI()
	{
		System.out.println("y u do dis?");
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run()
	{
		// TODO Figure out how to make calculations run in a different thread
	}
}