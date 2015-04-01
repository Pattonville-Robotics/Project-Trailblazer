package com.electronauts.pathfind;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

// TODO: Auto-generated Javadoc
/**
 * The Class GridPreviewPanel.
 */
public class GridPreviewPanel extends JPanel
{

	/** The Constant serialVersionUID. */
	private static final long	serialVersionUID	= 1L;

	/**
	 * Create the panel.
	 */

	private int					numRows, numColumns, squareWidth, squareHeight;

	/**
	 * Instantiates a new grid preview panel.
	 *
	 * @param numRows
	 *            the num rows
	 * @param numColumns
	 *            the num columns
	 * @param squareWidth
	 *            the square width
	 * @param squareHeight
	 *            the square height
	 */
	public GridPreviewPanel(final int numRows, final int numColumns, final int squareWidth, final int squareHeight)
	{
		super();
		this.numRows = numRows;
		this.numColumns = numColumns;
		this.squareWidth = squareWidth;
		this.squareHeight = squareHeight;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.JComponent#paint(java.awt.Graphics)
	 */
	@Override
	public void paint(final Graphics g)
	{
		this.getParent().getParent();
		g.setColor(Color.BLACK);
		for (int x = 0; x < this.numRows; x++)
			for (int y = 0; y < this.numColumns; y++)
				g.drawRect(x * this.squareWidth + 10, y * this.squareHeight + 10, this.squareWidth, this.squareHeight);
	}

	/**
	 * Sets the num columns.
	 *
	 * @param numColumns
	 *            the new num columns
	 */
	public void setNumColumns(final int numColumns)
	{
		this.numColumns = numColumns;
	}

	/**
	 * Sets the num rows.
	 *
	 * @param numRows
	 *            the new num rows
	 */
	public void setNumRows(final int numRows)
	{
		this.numRows = numRows;
	}

	/**
	 * Sets the square height.
	 *
	 * @param squareHeight
	 *            the new square height
	 */
	public void setSquareHeight(final int squareHeight)
	{
		this.squareHeight = squareHeight;
	}

	/**
	 * Sets the square width.
	 *
	 * @param squareWidth
	 *            the new square width
	 */
	public void setSquareWidth(final int squareWidth)
	{
		this.squareWidth = squareWidth;
	}
}
