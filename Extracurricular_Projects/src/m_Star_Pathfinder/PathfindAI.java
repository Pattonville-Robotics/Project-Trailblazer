package m_Star_Pathfinder;

import java.awt.Point;
import java.util.AbstractSet;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class PathfindAI
{
	private PathfindAI()
	{
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
					if (grid.canAccess(new Point(points.get(i).x + xMod[j], points.get(i).y + yMod[j]))
							&& grid.getSquareCopy(new Point(points.get(i).x + xMod[j], points.get(i).y + yMod[j])).getDistance() == -1
							&& grid.getSquareCopy(new Point(points.get(i).x + xMod[j], points.get(i).y + yMod[j])).getContents() == SquareType.EMPTY)
					// If it's not outside the grid and hasn't been set already
					// and is an empty square
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
			points.clear();
			points.addAll(newPoints);

			if (numFailedCells == points.size() || points.size() == 0)
			// If all cells completely failed
			{
				noMoreLeft = true; // End looping
			}
		}
	}
}