package m_Star_Pathfinder;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

public class Grid
{
	private GridSquare[][]	grid;
	private Point			startPoint, finishPoint;

	public Grid(GridSquare[][] grid)
	{
		this.grid = grid;
	}

	public Grid(int width, int height, int squareWidth, int squareHeight)
	{
		grid = new GridSquare[height][width];
		startPoint = new Point(0, 0);

		for (int row = 0; row < grid.length; row++)
		{
			for (int column = 0; column < grid[row].length; column++)
			{
				this.setSquare(new GridSquare(SquareType.EMPTY, column * squareWidth, row
						* squareHeight, squareWidth, squareHeight), row, column);
			}
		}
	}

	public GridSquare[][] getGrid()
	{
		return grid;
	}

	public boolean canAccess(Point p)
	{
		try
		{
			this.getSquare(p);
		}
		catch (ArrayIndexOutOfBoundsException e)
		{
			return false;
		}
		return true;
	}

	public void setGrid(GridSquare[][] grid)
	{
		this.grid = grid;
	}

	private GridSquare getSquare(Point p)
	{
		return grid[p.y][p.x];
	}

	public GridSquare getSquareCopy(Point p)
	{
		return grid[p.y][p.x].clone();
	}

	public void setSquare(GridSquare gridSquare, int row, int column)
	{
		grid[row][column] = gridSquare;
	}

	public Point getStartPoint()
	{
		return startPoint;
	}

	public Point getFinishPoint()
	{
		return finishPoint;
	}

	public void setSquareContents(Point p, SquareType contents)
	{
		try
		{
			this.getSquare(p).setContents(contents);
			if (contents == SquareType.START)
			{
				startPoint = p;
			}
			else if (contents == SquareType.FINISH)
			{
				finishPoint = p;
			}
		}
		catch (ArrayIndexOutOfBoundsException e)
		{
			System.out.println("\"setSquareContents\" is trying to access out of bounds!");
		}
	}

	public void setSquareDistance(Point p, int distance)
	{
		try
		{
			this.getSquare(p).setDistance(distance);
		}
		catch (ArrayIndexOutOfBoundsException e)
		{
			System.out.println("\"setSquareDistance\" is trying to access out of bounds!");
		}
	}

	public void paintLine(Graphics g, Point p1, Point p2)
	{
		g.setColor(new Color(255, 0, 0));
		g.drawLine(this.getSquare(p1).getXCenter(), this.getSquare(p1).getYCenter(), this
				.getSquare(p2).getXCenter(), this.getSquare(p2).getYCenter());
	}

	public void paintPointSet(Graphics g, Point[] points)
	{
		for (int i = 0; i < points.length - 1; i++)
		{
			paintLine(g, points[i], points[i + 1]);
		}
	}

	public void paint(Graphics g)
	{
		for (int row = 0; row < grid.length; row++)
		{
			for (int column = 0; column < grid[row].length; column++)
			{
				this.getSquare(new Point(column, row)).paint(g);
			}
		}
	}
}
