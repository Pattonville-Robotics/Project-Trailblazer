package org.psdr3.robotics.trailblazer;

import java.awt.Point;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;

/**
 * Created by skaggsm on 9/3/15.
 */
public class Grid implements Serializable, Observable {

	private Tile[][] grid;

	private Point startPoint;
	private Point finishPoint;

	private List<InvalidationListener> invalidationListeners;

	public Grid(int rows, int cols) {
		if (rows < 2 || cols < 2) {
			throw new IllegalArgumentException("Grid cannot be smaller than 2x2.");
		}
		this.grid = new Tile[rows][cols];

		for (Tile[] row : this.grid) {
			Arrays.fill(row, Tile.EMPTY);
		}

		invalidationListeners = new ArrayList<InvalidationListener>();
	}

	public Tile getTile(int row, int col) {
		return this.grid[row][col];
	}

	public void setTile(int row, int col, Tile tile) {
		if (this.grid[row][col] != tile) {
			if (tile == Tile.START) {
				this.setTile(startPoint.y, startPoint.x, Tile.EMPTY);
				this.startPoint = new Point(col, row);
				this.grid[row][col] = Tile.START;
			} else if (tile == Tile.FINISH) {
				this.setTile(finishPoint.y, finishPoint.x, Tile.EMPTY);
				this.finishPoint = new Point(col, row);
				this.grid[row][col] = Tile.FINISH;
			} else {
				this.grid[row][col] = tile;
			}
			for (InvalidationListener listener : this.invalidationListeners) {
				listener.invalidated(this);
			}
		}
	}

	public int numRows() {
		return 0;
	}

	public int numCols() {
		return 0;
	}

	@Override
	public void addListener(InvalidationListener listener) {
		this.invalidationListeners.add(listener);
	}

	@Override
	public void removeListener(InvalidationListener listener) {
		this.invalidationListeners.remove(listener);
	}
}