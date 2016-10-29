package org.psdr3.robotics.trailblazer;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;

import java.awt.Point;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by skaggsm on 9/3/15.
 */
public class Grid implements Serializable, Observable {

    private Tile[][] grid;
    private Point startPoint, finishPoint;
    private Set<Point> cornerSet;
    private volatile List<InvalidationListener> invalidationListeners;

    public Grid(int rows, int cols) {
        if (rows < 2 || cols < 2) {
            throw new IllegalArgumentException("Grid cannot be smaller than 2x2.");
        }
        this.grid = new Tile[rows][cols];

        for (Tile[] row : this.grid) {
            for (Tile tile : row) {
                tile.setType(Tile.Type.EMPTY);
            }
        }

        this.invalidationListeners = new ArrayList<InvalidationListener>();
        this.cornerSet = new HashSet<Point>();
    }

    public Tile getTile(int row, int col) {
        return this.grid[row][col];
    }

    public void setTile(int row, int col, Tile.Type type) {
        Tile oldTile = this.getTile(row, col);
        if (this.grid[row][col].getType() != type) {
            if (type == Tile.Type.START) {
                this.setTile(startPoint.y, startPoint.x, Tile.Type.EMPTY);
                this.startPoint = new Point(col, row);
                this.grid[row][col].setType(Tile.Type.START);
            } else if (type == Tile.Type.FINISH) {
                this.setTile(finishPoint.y, finishPoint.x, Tile.Type.EMPTY);
                this.finishPoint = new Point(col, row);
                this.grid[row][col].setType(Tile.Type.FINISH);
            } else {
                this.grid[row][col].setType(type);
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

    public void addCorners(int row, int col) {
        //TODO Implement corner finding
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
