package org.psdr3.robotics.trailblazer.ai;

import org.psdr3.robotics.trailblazer.Tile;

import java.awt.geom.Path2D;
import java.util.LinkedList;

/**
 * Created by skaggsm on 9/17/15.
 */
public class Path {
	LinkedList<Tile> tiles;

	public Path() {
		this.tiles = new LinkedList<Tile>();
	}

	public void append(Tile tile) {
		this.tiles.add(tile);
	}

	public Path2D.Double getFancyPath() {
		Path2D.Double fancyPath = new Path2D.Double();
		fancyPath.moveTo(this.tiles.getFirst().getCol() + 0.5, this.tiles.getFirst().getRow() + 0.5);
		for (Tile tile : this.tiles.subList(1, this.tiles.size())) {
			fancyPath.lineTo(tile.getCol() + 0.5, tile.getRow() + 0.5);
		}
		return fancyPath;
	}
}
