package m_Star_Pathfinder;

import java.applet.Applet;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class GridDraw extends Applet
{
	private static final long	serialVersionUID	= 1L;
	private Grid				grid;

	public void paint(Graphics g)
	{
		grid.paint(g);
		// System.out.println("Lowest next to start: " +
		// grid.getLowestAdjacentSquares(grid.getFinishPoint()));

		g.setColor(new Color(255, 0, 0));
		for (int i = 0; i < grid.getPaths().size(); i++)
		{
			grid.paintPointSet(g, grid.getPaths().get(i).getArray());
		}
	}

	public void init()
	{

		try
		{
			FileInputStream fileIn = new FileInputStream("grid.data");
			ObjectInputStream objIn = new ObjectInputStream(fileIn);

			Object obj = objIn.readObject();
			objIn.close();

			if (obj instanceof Grid)
			{
				grid = (Grid) obj;
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			grid = new Grid(16, 16, 40, 40);
			grid.setSquareContents(new Point(1, 2), SquareType.START);
			grid.setSquareContents(new Point(5, 11), SquareType.FINISH);

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
			grid.setSquareContents(new Point(7, 9), SquareType.HAZARD);
			grid.setSquareContents(new Point(8, 10), SquareType.HAZARD);
			grid.setSquareContents(new Point(9, 11), SquareType.HAZARD);
			grid.setSquareContents(new Point(3, 1), SquareType.HAZARD);
			System.out.println(grid.getDistance(grid.getStartPoint()));

			PathfindAI.computeDistance(grid, grid.getStartPoint());
			PathfindAI.computePaths(grid);

			try
			{
				FileOutputStream fileOut = new FileOutputStream("grid.data");
				ObjectOutputStream objOut = new ObjectOutputStream(fileOut);

				objOut.writeObject(grid);
				objOut.close();
			}
			catch (Exception e1)
			{
				e1.printStackTrace();
			}
		}
		resize(700, 700);
	}
}