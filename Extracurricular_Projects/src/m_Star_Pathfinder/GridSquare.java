package m_Star_Pathfinder;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.io.Serializable;

public class GridSquare implements Cloneable, Serializable
{
	private static final long	serialVersionUID	= 1L;
	private SquareType			contents;
	private int					x, y, width, height;
	private int					distance;
	private static final Font	font1				= new Font("Arial", Font.PLAIN, 20);
	private static final Font	font2				= new Font("Arial", Font.PLAIN, 12);
	public Grid					superGrid;

	public GridSquare(Grid superGrid, SquareType contents, int x, int y, int width, int height)
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
		catch (Exception e)
		{
			System.out.println("Couldn't clone GridSquare!");
			return null;
		}
	}

	public void setContents(SquareType contents)
	{
		this.contents = contents;
	}

	public void setLocation(int x, int y)
	{
		this.x = x;
		this.y = y;
	}

	public void setSize(int width, int height)
	{
		this.width = width;
		this.height = height;
	}

	public void setDistance(int distance)
	{
		this.distance = distance;
	}

	public int getDistance()
	{
		return distance;
	}

	public int getX()
	{
		return x;
	}

	public int getY()
	{
		return y;
	}

	public Point getPoint()
	{
		return new Point(x, y);
	}

	public int getWidth()
	{
		return width;
	}

	public int getHeight()
	{
		return height;
	}

	public int getXCenter()
	{
		return x + width / 2;
	}

	public int getYCenter()
	{
		return y + height / 2;
	}

	public SquareType getContents()
	{
		return contents;
	}

	public Grid getSuperGrid()
	{
		return superGrid;
	}

	public Rectangle getBounds()
	{
		return new Rectangle(x, y, width, height);
	}

	public void paint(Graphics g)
	{
		switch (contents)
		{
		case EMPTY:
			int shade = 255 - (int) (255 * (distance / (double) superGrid.getSquareCopy(superGrid.getFurthestPoint()).getDistance()));
			if (shade >= 0 && shade <= 255)
			{
				g.setColor(new Color(shade, shade, shade));
			}
			else
			{
				System.out.println("Out of bounds.");
				g.setColor(new Color(255, 0, 0));
			}
			g.fillRect(x, y, width, height);

			g.setFont(font2);
			g.setColor(new Color(191, 127, 63));
			g.drawString(distance + "", x, y + height / 2);

			g.setColor(new Color(255, 255, 255));
			g.drawRect(x, y, width, height);
			break;
		case HAZARD:
			g.setColor(new Color(127, 63, 127));
			g.fillRect(x, y, width, height);
			g.setColor(new Color(255, 255, 255));
			g.drawRect(x, y, width, height);

			g.setFont(font2);
			g.setColor(new Color(191, 127, 63));
			g.drawString(distance + "", x, y + height / 2);
			break;
		case START:
			// g.setColor(new Color(191, 191, 191));
			g.setColor(new Color(0, 255, 63));
			g.fillRect(x, y, width, height);

			g.setFont(font1);
			g.setColor(new Color(0, 255, 63));
			// g.drawString("Start", x + width / 4, y + height / 2);

			g.setColor(new Color(255, 255, 255));
			g.drawRect(x, y, width, height);

			g.setFont(font2);
			g.setColor(new Color(191, 127, 63));
			g.drawString(distance + "", x, y + height / 2);
			break;
		case FINISH:
			// g.setColor(new Color(191, 191, 191));
			g.setColor(new Color(0, 63, 255));
			g.fillRect(x, y, width, height);

			g.setFont(font1);
			g.setColor(new Color(0, 63, 255));
			// g.drawString("Finish", x + width / 4, y + height / 2);

			g.setColor(new Color(255, 255, 255));
			g.drawRect(x, y, width, height);

			g.setFont(font2);
			g.setColor(new Color(191, 127, 63));
			g.drawString(distance + "", x, y + height / 2);
			break;
		}
	}
}