package m_Star_Pathfinder;

import java.applet.Applet;
import java.awt.Graphics;
import java.awt.Point;

public class GridDraw extends Applet
{
	private static final long	serialVersionUID	= 1L;

	public void paint(Graphics g)
	{
		// resize(600, 600);
		Grid grid = new Grid(20, 20, 30, 30);
		grid.setSquareContents(new Point(2, 2), SquareType.START);

		grid.setSquareContents(new Point(3, 2), SquareType.HAZARD);
		grid.setSquareContents(new Point(3, 3), SquareType.HAZARD);
		grid.setSquareContents(new Point(3, 4), SquareType.HAZARD);
		grid.setSquareContents(new Point(2, 5), SquareType.HAZARD);
		grid.setSquareContents(new Point(1, 5), SquareType.HAZARD);
		grid.setSquareContents(new Point(0, 5), SquareType.HAZARD);
		grid.setSquareContents(new Point(4, 6), SquareType.HAZARD);
		grid.setSquareContents(new Point(5, 7), SquareType.HAZARD);
		grid.setSquareContents(new Point(6, 8), SquareType.HAZARD);
		grid.setSquareContents(new Point(4, 5), SquareType.HAZARD);

		System.out.println(grid.getStartPoint().x + ", " + grid.getStartPoint().y);
		PathfindAI.computeDistance(grid, grid.getStartPoint());
		grid.paint(g);
		/*
		 * grid.paintPointSet(g, new Point[] { new Point(9, 4), new Point(1, 3),
		 * new Point(2, 1), new Point(5, 3) });
		 */
	}
}