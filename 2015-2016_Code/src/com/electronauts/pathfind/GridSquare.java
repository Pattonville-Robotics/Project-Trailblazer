package com.electronauts.pathfind;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.io.Serializable;

import org.apache.commons.math3.util.FastMath;

// TODO: Auto-generated Javadoc
/**
 * The Class GridSquare.
 */
public class GridSquare implements Cloneable, Serializable
{

	/** The Constant font1. */
	private static final Font	font1				= new Font("Arial", Font.PLAIN, 20);

	/** The Constant font2. */
	private static final Font	font2				= new Font("Arial", Font.PLAIN, 12);

	/** The Constant serialVersionUID. */
	private static final long	serialVersionUID	= 1L;

	/** The super grid. */
	public Grid					superGrid;

	/** The contents. */
	private SquareType			contents;

	/** The distance. */
	private int					distance;

	/** The height. */
	private int					x, y, width, height;

	/**
	 * Instantiates a new grid square.
	 *
	 * @param superGrid
	 *            the super grid
	 * @param contents
	 *            the contents
	 * @param x
	 *            the x
	 * @param y
	 *            the y
	 * @param width
	 *            the width
	 * @param height
	 *            the height
	 */
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

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#clone()
	 */
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

	/**
	 * Gets the bounds.
	 *
	 * @return the bounds
	 */
	public Rectangle getBounds()
	{
		return new Rectangle(this.x / this.superGrid.getSquareWidth(), this.y / this.superGrid.getSquareHeight(), this.width / this.superGrid.getSquareWidth(),
				this.height / this.superGrid.getSquareHeight());
	}

	/**
	 * Gets the center point.
	 *
	 * @return the center point
	 */
	public Point getCenterPoint()
	{
		return new Point(this.getXCenter(), this.getYCenter());
	}

	/**
	 * Gets the contents.
	 *
	 * @return the contents
	 */
	public SquareType getContents()
	{
		return this.contents;
	}

	/**
	 * Gets the distance.
	 *
	 * @return the distance
	 */
	public int getDistance()
	{
		return this.distance;
	}

	/**
	 * Gets the height.
	 *
	 * @return the height
	 */
	public int getHeight()
	{
		return this.height;
	}

	/**
	 * Gets the point.
	 *
	 * @return the point
	 */
	public Point getPoint()
	{
		return new Point(this.x, this.y);
	}

	/**
	 * Gets the radial distance.
	 *
	 * @return the radial distance
	 */
	public double getRadialDistance()
	{
		return FastMath.sqrt(FastMath.pow(this.x - this.superGrid.getStartPoint().x, 2) + FastMath.pow(this.y - this.superGrid.getStartPoint().y, 2));
	}

	/**
	 * Gets the super grid.
	 *
	 * @return the super grid
	 */
	public Grid getSuperGrid()
	{
		return this.superGrid;
	}

	/**
	 * Gets the width.
	 *
	 * @return the width
	 */
	public int getWidth()
	{
		return this.width;
	}

	/**
	 * Gets the x.
	 *
	 * @return the x
	 */
	public int getX()
	{
		return this.x;
	}

	/**
	 * Gets the x center.
	 *
	 * @return the x center
	 */
	public int getXCenter()
	{
		return this.x + this.width / 2;
	}

	/**
	 * Gets the y.
	 *
	 * @return the y
	 */
	public int getY()
	{
		return this.y;
	}

	/**
	 * Gets the y center.
	 *
	 * @return the y center
	 */
	public int getYCenter()
	{
		return this.y + this.height / 2;
	}

	/**
	 * Paint.
	 *
	 * @param g
	 *            the g
	 */
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
				// System.out.println("Out of bounds.");
				g.setColor(new Color(0, 0, 0));
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

	/**
	 * Sets the contents.
	 *
	 * @param contents
	 *            the new contents
	 */
	public void setContents(final SquareType contents)
	{
		this.contents = contents;
	}

	/**
	 * Sets the distance.
	 *
	 * @param distance
	 *            the new distance
	 */
	public void setDistance(final int distance)
	{
		this.distance = distance;
	}

	/**
	 * Sets the location.
	 *
	 * @param x
	 *            the x
	 * @param y
	 *            the y
	 */
	public void setLocation(final int x, final int y)
	{
		this.x = x;
		this.y = y;
	}

	/**
	 * Sets the size.
	 *
	 * @param width
	 *            the width
	 * @param height
	 *            the height
	 */
	public void setSize(final int width, final int height)
	{
		this.width = width;
		this.height = height;
	}
}