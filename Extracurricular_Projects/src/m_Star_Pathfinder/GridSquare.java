package m_Star_Pathfinder;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.io.Serializable;

public class GridSquare implements Cloneable, Serializable
{
	private static final Font	font1				= new Font("Arial", Font.PLAIN, 20);
	private static final Font	font2				= new Font("Arial", Font.PLAIN, 12);
	private static final long	serialVersionUID	= 1L;
	public Grid					superGrid;
	private SquareType			contents;
	private int					distance;
	private int					x, y, width, height;

	public GridSquare(final Grid superGrid, final SquareType contents, final int x, final int y, final int width, final int height)
	{
		this.contents = contents;
		this.superGrid = superGrid;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.distance = -1;
	}

	@Override
	public GridSquare clone()
	{
		try
		{
			return (GridSquare) super.clone();
		}
		catch (final Exception e)
		{
			System.out.println("Couldn't clone GridSquare!");
			return null;
		}
	}

	public Rectangle getBounds()
	{
		return new Rectangle(this.x / this.superGrid.getSquareWidth(), this.y / this.superGrid.getSquareHeight(), this.width / this.superGrid.getSquareWidth(),
				this.height / this.superGrid.getSquareHeight());
	}

	public Point getCenterPoint()
	{
		return new Point(this.getXCenter(), this.getYCenter());
	}

	public SquareType getContents()
	{
		return this.contents;
	}

	public int getDistance()
	{
		return this.distance;
	}

	public int getHeight()
	{
		return this.height;
	}

	public Point getPoint()
	{
		return new Point(this.x, this.y);
	}

	public double getRadialDistance()
	{
		return Math.sqrt(Math.pow(this.x - this.superGrid.getStartPoint().x, 2) + Math.pow(this.y - this.superGrid.getStartPoint().y, 2));
	}

	public Grid getSuperGrid()
	{
		return this.superGrid;
	}

	public int getWidth()
	{
		return this.width;
	}

	public int getX()
	{
		return this.x;
	}

	public int getXCenter()
	{
		return this.x + this.width / 2;
	}

	public int getY()
	{
		return this.y;
	}

	public int getYCenter()
	{
		return this.y + this.height / 2;
	}

	public void paint(final Graphics g)
	{
		switch (this.contents)
		{
		case EMPTY:
			final int shade = 255 - (int) (255 * (this.distance / (double) this.superGrid.getSquareCopy(this.superGrid.getFurthestPoint()).getDistance()));
			if (shade >= 0 && shade <= 255)
				g.setColor(new Color(shade, shade, shade));
			else
			{
				this.superGrid.findFurthestPoint();
				System.out.println("Out of bounds.");
				g.setColor(new Color(255, 0, 0));
			}
			g.fillRect(this.x, this.y, this.width, this.height);

			g.setFont(GridSquare.font2);
			g.setColor(new Color(191, 127, 63));
			g.drawString(this.distance + "", this.x, this.y + this.height / 2);

			g.setColor(new Color(255, 255, 255));
			g.drawRect(this.x, this.y, this.width, this.height);
			break;
		case HAZARD:
			g.setColor(new Color(127, 63, 127));
			g.fillRect(this.x, this.y, this.width, this.height);
			g.setColor(new Color(255, 255, 255));
			g.drawRect(this.x, this.y, this.width, this.height);

			g.setFont(GridSquare.font2);
			g.setColor(new Color(191, 127, 63));
			g.drawString(this.distance + "", this.x, this.y + this.height / 2);
			break;
		case START:
			// g.setColor(new Color(191, 191, 191));
			g.setColor(new Color(0, 255, 63));
			g.fillRect(this.x, this.y, this.width, this.height);

			g.setFont(GridSquare.font1);
			g.setColor(new Color(0, 255, 63));
			// g.drawString("Start", x + width / 4, y + height / 2);

			g.setColor(new Color(255, 255, 255));
			g.drawRect(this.x, this.y, this.width, this.height);

			g.setFont(GridSquare.font2);
			g.setColor(new Color(191, 127, 63));
			g.drawString(this.distance + "", this.x, this.y + this.height / 2);
			break;
		case FINISH:
			// g.setColor(new Color(191, 191, 191));
			g.setColor(new Color(0, 63, 255));
			g.fillRect(this.x, this.y, this.width, this.height);

			g.setFont(GridSquare.font1);
			g.setColor(new Color(0, 63, 255));
			// g.drawString("Finish", x + width / 4, y + height / 2);

			g.setColor(new Color(255, 255, 255));
			g.drawRect(this.x, this.y, this.width, this.height);

			g.setFont(GridSquare.font2);
			g.setColor(new Color(191, 127, 63));
			g.drawString(this.distance + "", this.x, this.y + this.height / 2);
			break;
		}
	}

	public void setContents(final SquareType contents)
	{
		this.contents = contents;
	}

	public void setDistance(final int distance)
	{
		this.distance = distance;
	}

	public void setLocation(final int x, final int y)
	{
		this.x = x;
		this.y = y;
	}

	public void setSize(final int width, final int height)
	{
		this.width = width;
		this.height = height;
	}
}