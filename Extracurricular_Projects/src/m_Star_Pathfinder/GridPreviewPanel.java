package m_Star_Pathfinder;

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

	public GridPreviewPanel(int numRows, int numColumns, int squareWidth, int squareHeight)
	{
		super();
		this.numRows = numRows;
		this.numColumns = numColumns;
		this.squareWidth = squareWidth;
		this.squareHeight = squareHeight;
	}

	public void setNumRows(int numRows)
	{
		this.numRows = numRows;
	}

	public void setNumColumns(int numColumns)
	{
		this.numColumns = numColumns;
	}

	public void setSquareWidth(int squareWidth)
	{
		this.squareWidth = squareWidth;
	}

	public void setSquareHeight(int squareHeight)
	{
		this.squareHeight = squareHeight;
	}

	@Override
	public void paint(Graphics g)
	{
		this.getParent().getParent();
		g.setColor(Color.BLACK);
		for (int x = 0; x < numRows; x++)
		{
			for (int y = 0; y < numColumns; y++)
			{
				g.drawRect(x * squareWidth + 10, y * squareHeight + 10, squareWidth, squareHeight);
			}
		}
	}
}
