package m_Star_Pathfinder;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.AbstractSet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

public class PathfindAI implements Runnable
{
	public PathfindAI()
	{
		System.out.println("y u do dis?");
	}

	public static void computeDistance(Grid grid, Point start)
	{
		List<Point> points = new ArrayList<Point>(grid.getGrid().length * 4);
		AbstractSet<Point> pointSet = new HashSet<Point>((int) Math.pow(grid.getGrid().length, 2));
		points.add(start);
		boolean noMoreLeft = false;
		int distance = 0;
		while (!noMoreLeft)
		{
			for (int i = 0; i < points.size(); i++)
			{
				// System.out.println("Setting distance of: (" + points.get(i).x
				// + ", " + points.get(i).y + ")");
				grid.setSquareDistance(points.get(i), distance);
			}

			List<Point> newPoints = new ArrayList<Point>();
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
					Point prospectivePoint = new Point(points.get(i).x + xMod[j], points.get(i).y + yMod[j]);
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
					{
						numFailed++;
					}
				}

				if (numFailed == 4) // If all four sides failed to be set
				{
					numFailedCells++; // This cell completely failed
				}
			}
			// System.out.println(distance);
			distance++;
			/*
			 * System.out.println(Arrays.toString(points.toArray()));
			 * System.out.println(Arrays.toString(newPoints.toArray()));
			 */
			if (numFailedCells == points.size() || points.size() == 0)
			// If all cells completely failed
			{
				noMoreLeft = true; // End looping
			}
			points.clear();
			points.addAll(newPoints);
			grid.findFurthestPoint();
		}
	}

	public static void computePaths(Grid grid)
	{
		ArrayList<VertexMap> paths = new ArrayList<VertexMap>();
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
				// System.out.println("i = " + i); // DEBUG
				// System.out.println("progressFromFinish = " +
				// progressFromFinish); // DEBUG
				// System.out.println("lowestAdjacentSquares = " +
				// grid.getLowestAdjacentSquares(paths.get(i).getLastPoint()));
				// // DEBUG

				ArrayList<Point> adjacent = grid.getLowestAdjacentSquares(paths.get(i).getLastPoint());

				switch (adjacent.size())
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
					VertexMap altMap3 = paths.get(i).clone();
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
					System.out.println("Less than 0 or more than 2 adjacent points were returned! This is a sign of something bad!"); // DEBUG
					break;
				}
			}
			System.out.println(numZero + " paths out of " + paths.size() + " paths have finished.");
			if (numZero == paths.size())
			{
				allZero = true;
			}
			// progressFromFinish++;
			// System.out.println(progressFromFinish); // DEBUG
		}

		for (int i = 0; i < paths.size(); i++)
		{
			paths.get(i).reverseList();
		}

		grid.setPaths(paths);
	}

	public static void optimizePaths(Grid grid)
	{
		ArrayList<VertexMap> paths = grid.getPaths();
		ArrayList<VertexMap> newPaths = new ArrayList<VertexMap>(paths.size());

		for (int i = 0; i < paths.size(); i++)
		{
			System.out.println("Progress: " + i + " / " + paths.size());
			newPaths.add(optimizePath(grid, paths.get(i)));
		}
		Collections.sort(newPaths);
		grid.setPaths(newPaths);
	}

	public static VertexMap optimizePath(Grid grid, VertexMap map)
	{
		VertexMap newMap = new VertexMap();
		newMap.addPoint(map.getPoint(0));

		for (int i = 0; i < map.size(); i++)
		{
			for (int j = i; j < map.size(); j++)
			{
				// System.out.println("Point " + map.getPoint(i) + " to " +
				// map.getPoint(j) + " intersect status: "
				// + grid.collidesWithHazard(map.getPoint(i), map.getPoint(j)));
				if (grid.collidesWithHazard(map.getPoint(i), map.getPoint(j)))
				{
					// System.out.println("Line from " +
					// map.getPoint(i).toString() + " to " +
					// map.getPoint(j).toString() + " collided with a wall!");

					// System.out.println("Creating line from " +
					// newMap.getLastPoint() + " to " + map.getPoint(j));
					newMap.addPoint(map.getPoint(j - 1));
					i = j - 2;

					break;
				}
			}
		}
		newMap.addPoint(map.getLastPoint());

		return newMap;
	}

	public static void identifyNodes(Grid grid, Graphics g)
	{
		int[] xVarianceDiagonal = new int[] { -1, 1, 1, -1 };
		int[] yVarianceDiagonal = new int[] { -1, -1, 1, 1 };

		ArrayList<Point> nodes = new ArrayList<Point>();

		for (int x = 0; x < grid.getGrid().length; x++)
		{
			for (int y = 0; y < grid.getGrid()[x].length; y++)
			{
				if (grid.getSquareCopy(new Point(x, y)).getContents() == SquareType.HAZARD)
				{
					for (int i = 0; i < xVarianceDiagonal.length && i < yVarianceDiagonal.length; i++)
					{
						if (grid.canAccess(new Point(x + xVarianceDiagonal[i], y + yVarianceDiagonal[i]))
								&& grid.getSquareCopy(new Point(x + xVarianceDiagonal[i], y + yVarianceDiagonal[i])).getContents() == SquareType.EMPTY)
						{
							if (grid.getSquareCopy(new Point(x + xVarianceDiagonal[i], y)).getContents() == SquareType.EMPTY
									&& grid.getSquareCopy(new Point(x, y + yVarianceDiagonal[i])).getContents() == SquareType.EMPTY)
								nodes.add(new Point(x + xVarianceDiagonal[i], y + yVarianceDiagonal[i]));
						}
					}
				}
			}
		}

		g.setColor(new Color(255, 255, 0));
		for (Point p : nodes)
		{
			g.fillOval(p.x * grid.getSquareWidth() + grid.getSquareWidth() / 4, p.y * grid.getSquareHeight() + grid.getSquareHeight() / 4,
					grid.getSquareWidth() / 2, grid.getSquareHeight() / 2);
		}

		for (Point p1 : nodes)
			for (Point p2 : nodes)
				grid.collidesWithHazard(p1, p2);

	}

	@Override
	public void run()
	{
		// TODO Figure out how to make calculations run in a different thread
	}
}