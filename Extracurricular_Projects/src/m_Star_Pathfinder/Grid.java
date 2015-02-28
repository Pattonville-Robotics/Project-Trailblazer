package m_Star_Pathfinder;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.io.Serializable;
import java.util.ArrayList;

public class Grid implements Serializable
{
	private static final long		serialVersionUID	= 1L;

	private GridSquare[][]			grid;
	private Point					startPoint, finishPoint, furthestPoint, highlightedPoint;
	private ArrayList<VertexMap>	paths;
	private int						squareWidth, squareHeight;
	private int						highlightThickness	= 2;

	public Grid(GridSquare[][] grid)
	{
		this.grid = grid;
	}

	public Grid(int width, int height, int squareWidth, int squareHeight)
	{
		grid = new GridSquare[height][width];
		startPoint = new Point(-1, -1);
		finishPoint = new Point(-1, -1);
		furthestPoint = new Point(-1, -1);
		highlightedPoint = new Point(0, 0);

		this.squareWidth = squareWidth;
		this.squareHeight = squareHeight;

		for (int row = 0; row < grid.length; row++)
		{
			for (int column = 0; column < grid[row].length; column++)
			{
				this.setSquare(new GridSquare(this, SquareType.EMPTY, column * squareWidth, row * squareHeight, squareWidth, squareHeight), row, column);
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

	public void setPaths(ArrayList<VertexMap> v)
	{
		this.paths = v;
	}

	public ArrayList<VertexMap> getPaths()
	{
		return paths;
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

	public void setStartPoint(Point p)
	{
		startPoint = p;
	}

	public Point getStartPoint()
	{
		return startPoint;
	}

	public Point getFinishPoint()
	{
		return finishPoint;
	}

	public Point getFurthestPoint()
	{
		return furthestPoint;
	}

	public void setFurthestPoint(Point p)
	{
		furthestPoint = p;
	}

	public void findFurthestPoint()
	{
		Point p = new Point(0, 0);

		for (int i = 0; i < grid.length; i++)
		{
			for (int j = 0; j < grid[i].length; j++)
			{
				// System.out.println("Checked distance: " + this.getSquare(new
				// Point(j, i)).getDistance() + ", Previous best distance: " +
				// this.getSquare(p).getDistance());
				if (this.getSquare(new Point(j, i)).getDistance() > this.getSquare(p).getDistance())
				{
					p.setLocation(j, i);
				}
			}
		}

		furthestPoint = p;
	}

	public Point getHighlightedPoint()
	{
		return highlightedPoint;
	}

	public void moveHighlightedSquare(int direction)
	{
		switch (direction)
		{
		case 0: // Up
			if (this.getHighlightedPoint().y > 0)
			{
				this.getHighlightedPoint().y -= 1;
			}
			break;
		case 1: // Down
			if (this.getHighlightedPoint().y < this.getGrid().length - 1)
			{
				this.getHighlightedPoint().y += 1;
			}
			break;
		case 2: // Left
			if (this.getHighlightedPoint().x > 0)
			{
				this.getHighlightedPoint().x -= 1;
			}
			break;
		case 3: // Right
			if (this.getHighlightedPoint().x < this.getGrid()[0].length - 1)
			{
				this.getHighlightedPoint().x += 1;
			}
			break;
		}
	}

	public int getDistance(Point p)
	{
		return this.getSquare(p).getDistance();
	}

	public void setSquareContents(Point p, SquareType contents)
	{
		try
		{
			this.getSquare(p).setContents(contents);
			if (contents == SquareType.HAZARD)
			{
				this.getSquare(p).setDistance(-2);
			}
			else if (contents == SquareType.START)
			{
				for (int y = 0; y < grid.length; y++)
				{
					for (int x = 0; x < grid[y].length; x++)
					{
						if (this.getSquare(new Point(x, y)).getContents() == SquareType.START)
						{
							this.setSquareContents(new Point(x, y), SquareType.EMPTY);
						}
					}
				}

				startPoint = p;
				this.getSquare(p).setContents(contents);
				this.getSquare(p).setDistance(0);
			}
			else if (contents == SquareType.FINISH)
			{
				for (int y = 0; y < grid.length; y++)
				{
					for (int x = 0; x < grid[y].length; x++)
					{
						if (this.getSquare(new Point(x, y)).getContents() == SquareType.FINISH)
						{
							this.setSquareContents(new Point(x, y), SquareType.EMPTY);
						}
					}
				}

				finishPoint = p;
				this.getSquare(p).setContents(contents);
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
			// System.out.println("\"setSquareDistance\" is trying to access out of bounds!");
		}
	}

	public int getSquareHeight()
	{
		return squareHeight;
	}

	public int getSquareWidth()
	{
		return squareWidth;
	}

	public void rotateHighlightedSquare(boolean clockWise)
	{
		if (clockWise)
		{
			switch (this.getSquare(highlightedPoint).getContents())
			{
			case EMPTY:
				this.setSquareContents(new Point(highlightedPoint), SquareType.HAZARD);
				break;
			case HAZARD:
				this.setSquareContents(new Point(highlightedPoint), SquareType.START);
				break;
			case START:
				this.setSquareContents(new Point(highlightedPoint), SquareType.FINISH);
				break;
			case FINISH:
				this.setSquareContents(new Point(highlightedPoint), SquareType.EMPTY);
				break;
			}
		}
		else
		{
			switch (this.getSquare(highlightedPoint).getContents())
			{
			case EMPTY:
				this.setSquareContents(new Point(highlightedPoint), SquareType.FINISH);
				break;
			case HAZARD:
				this.setSquareContents(new Point(highlightedPoint), SquareType.EMPTY);
				break;
			case START:
				this.setSquareContents(new Point(highlightedPoint), SquareType.HAZARD);
				break;
			case FINISH:
				this.setSquareContents(new Point(highlightedPoint), SquareType.START);
				break;
			}
		}
	}

	public void paintLine(Graphics g, Point p1, Point p2)
	{
		// g.setColor(new Color(255, 0, 0));
		g.drawLine(this.getSquare(p1).getXCenter(), this.getSquare(p1).getYCenter(), this.getSquare(p2).getXCenter(), this.getSquare(p2).getYCenter());
	}

	public void paintPointSet(Graphics g, Point[] points)
	{
		for (int i = 0; i < points.length - 1; i++)
		{
			paintLine(g, points[i], points[i + 1]);
		}
	}

	public void paintRectangle(Graphics g, Rectangle2D r)
	{
		g.drawRect((int) r.getX(), (int) r.getY(), (int) r.getWidth(), (int) r.getHeight());
	}

	public ArrayList<Point> getLowestAdjacentSquares(Point p)
	{
		ArrayList<Point> adjacent = new ArrayList<Point>();
		final int[] xMod = new int[] { 1, -1, 0, 0 };
		final int[] yMod = new int[] { 0, 0, 1, -1 };

		for (int i = 0; i < xMod.length; i++)
		{
			Point possiblePoint = new Point(p.x + xMod[i], p.y + yMod[i]);
			if (this.canAccess(possiblePoint) && this.getSquareCopy(possiblePoint).getDistance() == this.getDistance(p) - 1)
			// If it exists and is a lower value than the center
			{
				adjacent.add(new Point(p.x + xMod[i], p.y + yMod[i]));
			}
		}

		return adjacent;
	}

	public boolean collidesWithHazard(Point p1, Point p2, Graphics g)
	{
		Line2D.Double line = new Line2D.Double(p1, p2);

		for (int y = 0; y < grid.length; y++)
		{
			for (int x = 0; x < grid[y].length; x++)
			{
				if (this.getSquare(new Point(x, y)).getContents() == SquareType.HAZARD)
				{
					if (this.getSquare(new Point(x, y)).getBounds().intersectsLine(line))
					{
						this.paintRectangle(g, this.getSquare(new Point(x, y)).getBounds());
						System.out.println("Collides with: " + this.getSquare(new Point(x, y)).getBounds());
						return true;
					}
				}
			}
		}

		return false;
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

		g.setColor(new Color(0, 63, 255));
		for (int i = 0; i <= highlightThickness; i++)
		{
			g.drawRect(highlightedPoint.x * squareWidth - i, highlightedPoint.y * squareHeight - i, squareWidth + 2 * i, squareHeight + 2 * i);
		}
	}
}
