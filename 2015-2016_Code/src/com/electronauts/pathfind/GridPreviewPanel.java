package com.electronauts.pathfind;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

public class GridPreviewPanel extends JPanel
{

	/**
	 *
	 */
	private static final long	serialVersionUID	= 1L;

	/**
	 * Create the panel.
	 */

	private int					numRows, numColumns, squareWidth, squareHeight;

	public GridPreviewPanel(final int numRows, final int numColumns, final int squareWidth, final int squareHeight)
	{
		super();
		this.numRows = numRows;
		this.numColumns = numColumns;
		this.squareWidth = squareWidth;
		this.squareHeight = squareHeight;
	}

	@Override
	public void paint(final Graphics g)
	{
		this.getParent().getParent();
		g.setColor(Color.BLACK);
		for (int x = 0; x < this.numRows; x++)
			for (int y = 0; y < this.numColumns; y++)
				g.drawRect(x * this.squareWidth + 10, y * this.squareHeight + 10, this.squareWidth, this.squareHeight);
	}

	public void setNumColumns(final int numColumns)
	{
		this.numColumns = numColumns;
	}

	public void setNumRows(final int numRows)
	{
		this.numRows = numRows;
	}

	public void setSquareHeight(final int squareHeight)
	{
		this.squareHeight = squareHeight;
	}

	public void setSquareWidth(final int squareWidth)
	{
		this.squareWidth = squareWidth;
	}
}
