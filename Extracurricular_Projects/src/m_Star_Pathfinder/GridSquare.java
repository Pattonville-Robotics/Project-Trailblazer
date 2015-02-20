package m_Star_Pathfinder;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class GridSquare implements Cloneable
{
	private SquareType			contents;
	private int					x, y, width, height;
	private int					distance;
	private static final Font	font1	= new Font("Arial", Font.PLAIN, 20);
	private static final Font	font2	= new Font("Arial", Font.PLAIN, 20);

	public GridSquare(SquareType contents, int x, int y, int width, int height)
	{
		this.contents = contents;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.distance = -1;
	}

	public GridSquare clone()
	{
		try
		{
			return (GridSquare) super.clone();
		}
		catch (Exception e)
		{
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

	public void paint(Graphics g)
	{
		switch (contents)
		{
		case EMPTY:
			g.setColor(new Color(255 - 4 * distance, 255 - 4 * distance, 255 - 4 * distance));
			g.fillRect(x, y, width, height);

			g.setFont(font2);
			g.setColor(new Color(31, 31, 31));
			g.drawString(distance + "", x, y + height / 2);

			g.setColor(new Color(255, 255, 255));
			g.drawRect(x, y, width, height);
			break;
		case HAZARD:
			g.setColor(new Color(63, 63, 63));
			g.fillRect(x, y, width, height);
			g.setColor(new Color(255, 255, 255));
			g.drawRect(x, y, width, height);
			break;
		case START:
			g.setColor(new Color(191, 191, 191));
			g.fillRect(x, y, width, height);

			g.setFont(font1);
			g.setColor(new Color(0, 255, 63));
			g.drawString("Start", x + width / 4, y + height / 2);

			g.setColor(new Color(255, 255, 255));
			g.drawRect(x, y, width, height);
			break;
		case FINISH:
			g.setColor(new Color(191, 191, 191));
			g.fillRect(x, y, width, height);

			g.setFont(font1);
			g.setColor(new Color(0, 63, 255));
			g.drawString("Finish", x + width / 4, y + height / 2);

			g.setColor(new Color(255, 255, 255));
			g.drawRect(x, y, width, height);
			break;
		}
	}

}