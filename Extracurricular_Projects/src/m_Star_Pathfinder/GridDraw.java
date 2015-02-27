package m_Star_Pathfinder;

import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Point;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.swing.JComponent;
import javax.swing.JFrame;

public class GridDraw extends JComponent
{
	private static final long	serialVersionUID	= 1L;
	private Grid				grid;

	public static void main(String[] args)
	{
		JFrame window = new JFrame("window");
		window.setBounds(0, 0, 700, 700);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GridDraw component = new GridDraw();
		component.setBackground(Color.WHITE);
		Container container = window.getContentPane();
		container.add(component);
		window.setVisible(true);
	}

	public GridDraw()
	{
		super();
		init();
	}

	@Override
	public void paintComponent(Graphics g)
	{
		// TODO Use ActionListeners to make the blue highlighted box move around
		// the screen
		// TODO Use ActionListeners to rotate through cell options
		// TODO Use ActionListeners to show/hide paths
		// TODO Use ActionListeners to recalculate paths
		System.out.println("Began drawing to the screen.");
		grid.paint(g);
		// System.out.println("Lowest next to start: " +
		// grid.getLowestAdjacentSquares(grid.getFinishPoint()));

		g.setColor(new Color(255, 0, 0));

		for (int i = 0; i < grid.getPaths().size(); i++)
		{
			grid.paintPointSet(g, grid.getPaths().get(i).getArray());
		}
		System.out.println("Finished drawing " + grid.getPaths().size() + " paths to the screen.");
		System.out.println("Each path is " + grid.getPaths().get(0).getTotalDistance() + " units long.");
	}

	public void init()
	{
		// TODO Experiment to make loading faster
		try
		{
			System.out.println("Began reading in data file.");
			FileInputStream fileIn = new FileInputStream("grid.data");
			ObjectInputStream objIn = new ObjectInputStream(fileIn);

			Object obj = objIn.readObject();
			objIn.close();

			if (obj instanceof Grid)
			{
				grid = (Grid) obj;
			}
			System.out.println("Finished reading in data file.");
		}
		catch (Exception e)
		{
			System.out.println("File not found. Began generating new data.");
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

			System.out.println("Finished generating new data. Begin caching.");
			try
			{
				FileOutputStream fileOut = new FileOutputStream("grid.data");
				ObjectOutputStream objOut = new ObjectOutputStream(fileOut);

				objOut.writeObject(grid);
				objOut.close();
				System.out.println("Sucessfully cached data.");
			}
			catch (Exception e1)
			{
				System.out.println("Failed to cache data.");
			}
		}
		System.out.println("Finished initialization.");
	}
}