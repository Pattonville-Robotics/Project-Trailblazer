package org.psdr3.robotics.trailblazer;

import java.io.Serializable;

/**
 * Created by skaggsm on 9/3/15.
 */
public class Tile implements Serializable {

	private int row, col;
	private int distance;
	private boolean corner;
	private Type type;

	public Tile(int row, int col, Type type) {
		this.row = row;
		this.col = col;
		this.type = type;
	}

	public int getDistance() {
		return distance;
	}

	public Tile setDistance(int distance) {
		this.distance = distance;
		return this;
	}

	public boolean isCorner() {
		return corner;
	}

	public Tile setCorner(boolean corner) {
		this.corner = corner;
		return this;
	}

	public Type getType() {
		return this.type;
	}

	public Tile setType(Type type) {
		this.type = type;
		return this;
	}

	public int getRow() {
		return this.row;
	}

	public Tile setRow(int row) {
		this.row = row;
		return this;
	}

	public int getCol() {
		return this.col;
	}

	public Tile setCol(int col) {
		this.col = col;
		return this;
	}

	enum Type {
		EMPTY, FILLED, START, FINISH;
	}
}
