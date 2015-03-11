package m_Star_Pathfinder;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.io.Serializable;
import java.util.ArrayList;

public class Grid implements Serializable
{
	private static final long		serialVersionUID	= 1L;

	private GridSquare[][]			grid;
	// private GridDraw gridDraw;
	private int						highlightThickness	= 2;
	private ArrayList<PathNode>		nodes;
	private PathNodeMap				pathNodeMap;
	private ArrayList<PathNodeMap>	pathNodeMaps;
	private ArrayList<VertexMap>	paths;
	private int						squareWidth, squareHeight;
	private Point					startPoint, finishPoint, furthestPoint, highlightedPoint;

	public Grid(final GridSquare[][] grid)
	{
		this.grid = grid;
	}

	/**
	 * Constructs a new {@code Grid} object with the specified parameters.
	 *
	 * @param width
	 *            - The number of squares horizontally
	 * @param height
	 *            - The number of squares vertically
	 * @param squareWidth
	 *            - The width (in pixels) of each square
	 * @param squareHeight
	 *            - The height (in pixels) of each square
	 */
	public Grid(final int width, final int height, final int squareWidth, final int squareHeight)
	{
		this.grid = new GridSquare[height][width];
		this.startPoint = new Point(-1, -1);
		this.finishPoint = new Point(-1, -1);
		this.furthestPoint = new Point(-1, -1);
		this.highlightedPoint = new Point(0, 0);

		this.squareWidth = squareWidth;
		this.squareHeight = squareHeight;
		nodes = new ArrayList<PathNode>();
		pathNodeMap = new PathNodeMap();
		pathNodeMaps = new ArrayList<PathNodeMap>();
		paths = new ArrayList<VertexMap>();

		for (int row = 0; row < this.grid.length; row++)
			for (int column = 0; column < this.grid[row].length; column++)
				this.setSquare(new GridSquare(this, SquareType.EMPTY, column * squareWidth, row * squareHeight, squareWidth, squareHeight), row, column);
		PathfindAI.computeDistance(this, this.getStartPoint());
	}

	/**
	 * @param p
	 *            - The point to be checked.
	 * @return A {@code boolean} value describing if the specified point can be accessed in the internal array.
	 */
	public boolean canAccess(final Point p)
	{
		try
		{
			this.getSquare(p);
		}
		catch (final ArrayIndexOutOfBoundsException e)
		{
			return false;
		}
		return true;
	}

	public boolean collidesWithHazard(final Point p1, final Point p2)
	{
		final Line2D.Double line = new Line2D.Double(p1.x + .5, p1.y + .5, p2.x + .5, p2.y + .5);

		for (int y = 0; y < this.grid.length; y++)
			for (int x = 0; x < this.grid[y].length; x++)
				if (this.getSquare(new Point(x, y)).getContents() == SquareType.HAZARD)
					if (this.getSquare(new Point(x, y)).getBounds().intersectsLine(line)) return true;

		return false;
	}

	public void findFurthestPoint()
	{
		final Point p = new Point(0, 0);

		for (int i = 0; i < this.grid.length; i++)
			for (int j = 0; j < this.grid[i].length; j++)
				// System.out.println("Checked distance: " + this.getSquare(new
				// Point(j, i)).getDistance() + ", Previous best distance: " +
				// this.getSquare(p).getDistance());
				if (this.getSquare(new Point(j, i)).getDistance() > this.getSquare(p).getDistance()) p.setLocation(j, i);

		this.furthestPoint = p;
	}

	public int getDistance(final Point p)
	{
		return this.getSquare(p).getDistance();
	}

	public Point getFinishPoint()
	{
		return this.finishPoint;
	}

	public Point getFurthestPoint()
	{
		return this.furthestPoint;
	}

	public GridSquare[][] getGrid()
	{
		return this.grid;
	}

	public Point getHighlightedPoint()
	{
		return this.highlightedPoint;
	}

	public ArrayList<Point> getLowestAdjacentSquares(final Point p)
	{
		final ArrayList<Point> adjacent = new ArrayList<Point>();
		final int[] xMod = new int[] { 1, -1, 0, 0 };
		final int[] yMod = new int[] { 0, 0, 1, -1 };

		for (int i = 0; i < xMod.length; i++)
		{
			final Point possiblePoint = new Point(p.x + xMod[i], p.y + yMod[i]);
			if (this.canAccess(possiblePoint) && this.getSquareCopy(possiblePoint).getDistance() == this.getDistance(p) - 1)
				adjacent.add(new Point(p.x + xMod[i], p.y + yMod[i]));
		}

		return adjacent;
	}

	public ArrayList<PathNode> getNodes()
	{
		return this.nodes;
	}

	public PathNodeMap getPathNodeMap()
	{
		return this.pathNodeMap;
	}

	public ArrayList<PathNodeMap> getPathNodeMaps()
	{
		return this.pathNodeMaps;
	}

	public ArrayList<VertexMap> getPaths()
	{
		return this.paths;
	}

	private GridSquare getSquare(final Point p)
	{
		return this.grid[p.y][p.x];
	}

	/*
	 * public GridDraw getGridDraw() { return gridDraw; }
	 */

	public GridSquare getSquareCopy(final Point p)
	{
		return this.grid[p.y][p.x].clone();
	}

	public int getSquareHeight()
	{
		return this.squareHeight;
	}

	public int getSquareWidth()
	{
		return this.squareWidth;
	}

	public Point getStartPoint()
	{
		return this.startPoint;
	}

	public void moveHighlightedSquare(final int direction)
	{
		switch (direction)
		{
		case 0: // Up
			if (this.getHighlightedPoint().y > 0) this.getHighlightedPoint().y -= 1;
			break;
		case 1: // Down
			if (this.getHighlightedPoint().y < this.getGrid().length - 1) this.getHighlightedPoint().y += 1;
			break;
		case 2: // Left
			if (this.getHighlightedPoint().x > 0) this.getHighlightedPoint().x -= 1;
			break;
		case 3: // Right
			if (this.getHighlightedPoint().x < this.getGrid()[0].length - 1) this.getHighlightedPoint().x += 1;
			break;
		}
	}

	public void paint(final Graphics g)
	{
		for (int row = 0; row < this.grid.length; row++)
			for (int column = 0; column < this.grid[row].length; column++)
				this.getSquare(new Point(column, row)).paint(g);

		g.setColor(new Color(0, 63, 255));
		for (int i = 0; i <= this.highlightThickness; i++)
			g.drawRect(this.highlightedPoint.x * this.squareWidth - i, this.highlightedPoint.y * this.squareHeight - i, this.squareWidth + 2 * i,
					this.squareHeight + 2 * i);
	}

	public void paintLine(final Graphics2D g2d, final Point p1, final Point p2, final boolean drawDirection, final int xVar, final int yVar)
	{
		// g.setColor(new Color(255, 0, 0));
		if (drawDirection)
			g2d.setPaint(new GradientPaint(p1.x * this.getSquareWidth(), p1.y * this.getSquareHeight(), new Color(0, 0, 255), p2.x * this.getSquareWidth(),
					p2.y * this.getSquareHeight(), new Color(0, 255, 0)));
		g2d.drawLine(this.getSquare(p1).getXCenter() + xVar, this.getSquare(p1).getYCenter() + yVar, this.getSquare(p2).getXCenter() + xVar, this.getSquare(p2)
				.getYCenter() + yVar);
	}

	public void paintNodes(final Graphics2D g2d)
	{
		g2d.setColor(new Color(204, 0, 102));
		for (final PathNode p : this.nodes)
		{
			final int shade = (int) (191 * (this.getSquareCopy(p.getNode()).getDistance() / (double) this.getSquareCopy(this.getFurthestPoint()).getDistance()));
			g2d.setColor(new Color(shade + 64, shade + 64, shade));
			g2d.fillOval(p.getNode().x * this.getSquareWidth() + this.getSquareWidth() * 3 / 8, p.getNode().y * this.getSquareHeight() + this.getSquareHeight()
					* 3 / 8, this.getSquareWidth() / 4, this.getSquareHeight() / 4);
		}
	}

	public void paintPointSet(final Graphics2D g2d, final Point[] points, final boolean drawDirection, final int xVar, final int yVar)
	{
		for (int i = 0; i < points.length - 1; i++)
			this.paintLine(g2d, points[i], points[i + 1], drawDirection, xVar, yVar);
	}

	public void paintRectangle(final Graphics g, final Rectangle2D r)
	{
		g.drawRect((int) r.getX(), (int) r.getY(), (int) r.getWidth(), (int) r.getHeight());
	}

	public void rotateHighlightedSquare(final boolean clockWise)
	{
		if (clockWise)
			switch (this.getSquare(this.highlightedPoint).getContents())
			{
			case EMPTY:
				this.setSquareContents(new Point(this.highlightedPoint), SquareType.HAZARD);
				break;
			case HAZARD:
				this.setSquareContents(new Point(this.highlightedPoint), SquareType.START);
				break;
			case START:
				this.setSquareContents(new Point(this.highlightedPoint), SquareType.START);
				break;
			case FINISH:
				this.setSquareContents(new Point(this.highlightedPoint), SquareType.FINISH);
				break;
			}
		else
			switch (this.getSquare(this.highlightedPoint).getContents())
			{
			case EMPTY:
				this.setSquareContents(new Point(this.highlightedPoint), SquareType.FINISH);
				break;
			case HAZARD:
				this.setSquareContents(new Point(this.highlightedPoint), SquareType.EMPTY);
				break;
			case START:
				this.setSquareContents(new Point(this.highlightedPoint), SquareType.START);
				break;
			case FINISH:
				this.setSquareContents(new Point(this.highlightedPoint), SquareType.FINISH);
				break;
			}
	}

	public void setFurthestPoint(final Point p)
	{
		this.furthestPoint = p;
	}

	public void setGrid(final GridSquare[][] grid)
	{
		this.grid = grid;
	}

	public void setHighlightedSquare(final int x, final int y)
	{
		if (x >= 0 && x < this.grid[0].length && y >= 0 && y < this.grid.length)
			this.highlightedPoint = new Point(x, y);
		else
		{
			// System.out.println("X or Y out of bounds!");
		}
	}

	public void setNodes(final ArrayList<PathNode> nodes)
	{
		this.nodes = nodes;
	}

	public void setPathNodeMap(final PathNodeMap pathNodeMap)
	{
		this.pathNodeMap = pathNodeMap;
	}

	public void setPathNodeMaps(final ArrayList<PathNodeMap> pathNodeMaps)
	{
		this.pathNodeMaps = pathNodeMaps;
	}

	public void setPaths(final ArrayList<VertexMap> v)
	{
		this.paths = v;
	}

	public void setSelf(final Grid newGrid)
	{
		this.grid = newGrid.grid;
		this.highlightThickness = newGrid.highlightThickness;
		this.nodes = newGrid.nodes;
		this.pathNodeMap = newGrid.pathNodeMap;
		this.pathNodeMaps = newGrid.pathNodeMaps;
		this.paths = newGrid.paths;
		this.squareWidth = newGrid.squareWidth;
		this.squareHeight = newGrid.squareHeight;
		this.startPoint = newGrid.startPoint;
		this.finishPoint = newGrid.finishPoint;
		this.furthestPoint = newGrid.furthestPoint;
		this.highlightedPoint = newGrid.highlightedPoint;
	}

	public void setSquare(final GridSquare gridSquare, final int row, final int column)
	{
		this.grid[row][column] = gridSquare;
	}

	public void setSquareContents(final Point p, final SquareType contents)
	{
		try
		{
			this.getSquare(p).setContents(contents);
			if (contents == SquareType.HAZARD)
				this.getSquare(p).setDistance(-2);
			else if (contents == SquareType.START)
			{
				for (int y = 0; y < this.grid.length; y++)
					for (int x = 0; x < this.grid[y].length; x++)
						if (this.getSquare(new Point(x, y)).getContents() == SquareType.START) this.setSquareContents(new Point(x, y), SquareType.EMPTY);

				this.startPoint = p;
				this.getSquare(p).setContents(contents);
				this.getSquare(p).setDistance(0);
			}
			else if (contents == SquareType.FINISH)
			{
				for (int y = 0; y < this.grid.length; y++)
					for (int x = 0; x < this.grid[y].length; x++)
						if (this.getSquare(new Point(x, y)).getContents() == SquareType.FINISH) this.setSquareContents(new Point(x, y), SquareType.EMPTY);

				this.finishPoint = p;
				this.getSquare(p).setContents(contents);
			}
		}
		catch (final ArrayIndexOutOfBoundsException e)
		{
			System.out.println("\"setSquareContents\" is trying to access out of bounds!");
		}
	}

	public void setSquareDistance(final Point p, final int distance)
	{
		try
		{
			this.getSquare(p).setDistance(distance);
		}
		catch (final ArrayIndexOutOfBoundsException e)
		{
			// System.out.println("\"setSquareDistance\" is trying to access out of bounds!");
		}
	}

	public void setStartPoint(final Point p)
	{
		this.startPoint = p;
	}

	public boolean testSetHighlightedSquare(final int x, final int y)
	{
		if (x >= 0 && x < this.grid[0].length && y >= 0 && y < this.grid.length)
			return true;
		else
			return false;
	}
}
