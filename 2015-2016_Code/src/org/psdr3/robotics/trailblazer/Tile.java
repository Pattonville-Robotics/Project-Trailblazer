package org.psdr3.robotics.trailblazer;

import java.io.Serializable;

/**
 * Created by skaggsm on 9/3/15.
 */
public enum Tile implements Serializable {
	EMPTY, FILLED, START, FINISH;

	private int x, y;

	private int distance;
	private boolean corner;

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

	public int getX() {
		return this.x;
	}

	public Tile setX(int x) {
		this.x = x;
		return this;
	}

	public int getY() {
		return this.y;
	}

	public Tile setY(int y) {
		this.y = y;
		return this;
	}
}
