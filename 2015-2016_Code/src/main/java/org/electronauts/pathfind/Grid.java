/*
 *
 */
package org.electronauts.pathfind;

import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.io.Serializable;
import java.util.ArrayList;

// TODO: Auto-generated Javadoc

/**
 * The Class Grid.
 */
public class Grid implements Serializable {

    /**
     * The Constant serialVersionUID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * The grid.
     */
    private GridSquare[][] grid;

    /**
     * The highlight thickness.
     */
    private int highlightThickness = 2;

    /**
     * The nodes.
     */
    private ArrayList<PathNode> nodes;

    /**
     * The path node map.
     */
    private PathNodeMap pathNodeMap;

    /**
     * The path node maps.
     */
    private ArrayList<PathNodeMap> pathNodeMaps;

    /**
     * The paths.
     */
    private ArrayList<VertexMap> paths;

    /**
     * The square height.
     */
    private int squareWidth, squareHeight;

    /**
     * The highlighted point.
     */
    private Point startPoint, finishPoint, furthestPoint, highlightedPoint;

    /**
     * Instantiates a new grid.
     *
     * @param grid the grid
     */
    public Grid(final GridSquare[][] grid) {
        this.grid = grid;
    }

    /**
     * Constructs a new {@code Grid} object with the specified parameters.
     *
     * @param width        - The number of squares horizontally
     * @param height       - The number of squares vertically
     * @param squareWidth  - The width (in pixels) of each square
     * @param squareHeight - The height (in pixels) of each square
     */
    public Grid(final int width, final int height, final int squareWidth, final int squareHeight) {
        this.grid = new GridSquare[height][width];
        this.startPoint = new Point(-1, -1);
        this.finishPoint = new Point(-1, -1);
        this.furthestPoint = new Point(-1, -1);
        this.highlightedPoint = new Point(0, 0);

        this.squareWidth = squareWidth;
        this.squareHeight = squareHeight;
        this.nodes = new ArrayList<PathNode>();
        this.pathNodeMap = new PathNodeMap();
        this.pathNodeMaps = new ArrayList<PathNodeMap>();
        this.paths = new ArrayList<VertexMap>();

        for (int row = 0; row < this.grid.length; row++)
            for (int column = 0; column < this.grid[row].length; column++)
                this.setSquare(new GridSquare(this, SquareType.EMPTY, column * squareWidth, row * squareHeight, squareWidth, squareHeight), row, column);
        PathfindAI.computeDistance(this, this.getStartPoint());
    }

    /**
     * Sets the square.
     *
     * @param gridSquare the grid square
     * @param row        the row
     * @param column     the column
     */
    public void setSquare(final GridSquare gridSquare, final int row, final int column) {
        this.grid[row][column] = gridSquare;
    }

    /**
     * Gets the start point.
     *
     * @return the start point
     */
    public Point getStartPoint() {
        return this.startPoint;
    }

    /**
     * Sets the start point.
     *
     * @param p the new start point
     */
    public void setStartPoint(final Point p) {
        this.startPoint = p;
    }

    /**
     * Collides with hazard.
     *
     * @param p1 the p1
     * @param p2 the p2
     * @return true, if successful
     */
    public boolean collidesWithHazard(final Point p1, final Point p2) {
        final Line2D.Double line = new Line2D.Double(p1.x + .5, p1.y + .5, p2.x + .5, p2.y + .5);

        for (int y = 0; y < this.grid.length; y++)
            for (int x = 0; x < this.grid[y].length; x++)
                if (this.getSquare(new Point(x, y)).getContents() == SquareType.HAZARD)
                    if (this.getSquare(new Point(x, y)).getBounds().intersectsLine(line))
                        return true;

        return false;
    }

    /**
     * Gets the square.
     *
     * @param p the p
     * @return the square
     */
    private GridSquare getSquare(final Point p) {
        return this.grid[p.y][p.x];
    }

    /**
     * Find furthest point.
     */
    public void findFurthestPoint() {
        final Point p = new Point(0, 0);

        for (int i = 0; i < this.grid.length; i++)
            for (int j = 0; j < this.grid[i].length; j++)
                // System.out.println("Checked distance: " + this.getSquare(new
                // Point(j, i)).getDistance() + ", Previous best distance: " +
                // this.getSquare(p).getDistance());
                if (this.getSquare(new Point(j, i)).getDistance() > this.getSquare(p).getDistance())
                    p.setLocation(j, i);

        this.furthestPoint = p;
    }

    /**
     * Gets the finish point.
     *
     * @return the finish point
     */
    public Point getFinishPoint() {
        return this.finishPoint;
    }

    /**
     * Gets the lowest adjacent squares.
     *
     * @param p the p
     * @return the lowest adjacent squares
     */
    public ArrayList<Point> getLowestAdjacentSquares(final Point p) {
        final ArrayList<Point> adjacent = new ArrayList<Point>();
        final int[] xMod = new int[]{1, -1, 0, 0};
        final int[] yMod = new int[]{0, 0, 1, -1};

        for (int i = 0; i < xMod.length; i++) {
            final Point possiblePoint = new Point(p.x + xMod[i], p.y + yMod[i]);
            if (this.canAccess(possiblePoint) && this.getSquareCopy(possiblePoint).getDistance() == this.getDistance(p) - 1)
                adjacent.add(new Point(p.x + xMod[i], p.y + yMod[i]));
        }

        return adjacent;
    }

    /**
     * Can access.
     *
     * @param p - The point to be checked.
     * @return A {@code boolean} value describing if the specified point can be accessed in the internal array.
     */
    public boolean canAccess(final Point p) {
        try {
            this.getSquare(p);
        } catch (final ArrayIndexOutOfBoundsException e) {
            return false;
        }
        return true;
    }

    /**
     * Gets the square copy.
     *
     * @param p the p
     * @return the square copy
     */
    public GridSquare getSquareCopy(final Point p) {
        return this.grid[p.y][p.x].clone();
    }

    /**
     * Gets the distance.
     *
     * @param p the p
     * @return the distance
     */
    public int getDistance(final Point p) {
        return this.getSquare(p).getDistance();
    }

    /**
     * Gets the nodes.
     *
     * @return the nodes
     */
    public ArrayList<PathNode> getNodes() {
        return this.nodes;
    }

    /**
     * Sets the nodes.
     *
     * @param nodes the new nodes
     */
    public void setNodes(final ArrayList<PathNode> nodes) {
        this.nodes = nodes;
    }

    /**
     * Gets the path node map.
     *
     * @return the path node map
     */
    public PathNodeMap getPathNodeMap() {
        return this.pathNodeMap;
    }

	/*
     * public GridDraw getGridDraw() { return gridDraw; }
	 */

    /**
     * Sets the path node map.
     *
     * @param pathNodeMap the new path node map
     */
    public void setPathNodeMap(final PathNodeMap pathNodeMap) {
        this.pathNodeMap = pathNodeMap;
    }

    /**
     * Gets the path node maps.
     *
     * @return the path node maps
     */
    public ArrayList<PathNodeMap> getPathNodeMaps() {
        return this.pathNodeMaps;
    }

    /**
     * Sets the path node maps.
     *
     * @param pathNodeMaps the new path node maps
     */
    public void setPathNodeMaps(final ArrayList<PathNodeMap> pathNodeMaps) {
        this.pathNodeMaps = pathNodeMaps;
    }

    /**
     * Gets the paths.
     *
     * @return the paths
     */
    public ArrayList<VertexMap> getPaths() {
        return this.paths;
    }

    /**
     * Sets the paths.
     *
     * @param v the new paths
     */
    public void setPaths(final ArrayList<VertexMap> v) {
        this.paths = v;
    }

    /**
     * Move highlighted square.
     *
     * @param direction the direction
     */
    public void moveHighlightedSquare(final int direction) {
        switch (direction) {
            case 0: // Up
                if (this.getHighlightedPoint().y > 0)
                    this.getHighlightedPoint().y -= 1;
                break;
            case 1: // Down
                if (this.getHighlightedPoint().y < this.getGrid().length - 1)
                    this.getHighlightedPoint().y += 1;
                break;
            case 2: // Left
                if (this.getHighlightedPoint().x > 0)
                    this.getHighlightedPoint().x -= 1;
                break;
            case 3: // Right
                if (this.getHighlightedPoint().x < this.getGrid()[0].length - 1)
                    this.getHighlightedPoint().x += 1;
                break;
        }
    }

    /**
     * Gets the highlighted point.
     *
     * @return the highlighted point
     */
    public Point getHighlightedPoint() {
        return this.highlightedPoint;
    }

    /**
     * Gets the grid.
     *
     * @return the grid
     */
    public GridSquare[][] getGrid() {
        return this.grid;
    }

    /**
     * Sets the grid.
     *
     * @param grid the new grid
     */
    public void setGrid(final GridSquare[][] grid) {
        this.grid = grid;
    }

    /**
     * Paint.
     *
     * @param g the g
     */
    public void paint(final Graphics g) {
        for (int row = 0; row < this.grid.length; row++)
            for (int column = 0; column < this.grid[row].length; column++)
                this.getSquare(new Point(column, row)).paint(g);

        g.setColor(new Color(0, 63, 255));
        for (int i = 0; i <= this.highlightThickness; i++)
            g.drawRect(this.highlightedPoint.x * this.squareWidth - i, this.highlightedPoint.y * this.squareHeight - i, this.squareWidth + 2 * i, this.squareHeight + 2 * i);
    }

    /**
     * Paint nodes.
     *
     * @param g2d the g2d
     */
    public void paintNodes(final Graphics2D g2d) {
        g2d.setColor(new Color(204, 0, 102));
        for (final PathNode p : this.nodes) {
            final int shade = (int) (191 * (this.getSquareCopy(p.getNode()).getDistance() / (double) this.getSquareCopy(this.getFurthestPoint()).getDistance()));
            g2d.setColor(new Color(shade + 64, shade + 64, shade));
            g2d.fillOval(p.getNode().x * this.getSquareWidth() + this.getSquareWidth() * 3 / 8, p.getNode().y * this.getSquareHeight() + this.getSquareHeight() * 3 / 8, this.getSquareWidth() / 4, this.getSquareHeight() / 4);
        }
    }

    /**
     * Gets the furthest point.
     *
     * @return the furthest point
     */
    public Point getFurthestPoint() {
        return this.furthestPoint;
    }

    /**
     * Sets the furthest point.
     *
     * @param p the new furthest point
     */
    public void setFurthestPoint(final Point p) {
        this.furthestPoint = p;
    }

    /**
     * Gets the square width.
     *
     * @return the square width
     */
    public int getSquareWidth() {
        return this.squareWidth;
    }

    /**
     * Gets the square height.
     *
     * @return the square height
     */
    public int getSquareHeight() {
        return this.squareHeight;
    }

    /**
     * Paint point set.
     *
     * @param g2d           the g2d
     * @param points        the points
     * @param drawDirection the draw direction
     * @param xVar          the x var
     * @param yVar          the y var
     */
    public void paintPointSet(final Graphics2D g2d, final Point[] points, final boolean drawDirection, final int xVar, final int yVar) {
        for (int i = 0; i < points.length - 1; i++)
            this.paintLine(g2d, points[i], points[i + 1], drawDirection, xVar, yVar);
    }

    /**
     * Paint line.
     *
     * @param g2d           the g2d
     * @param p1            the p1
     * @param p2            the p2
     * @param drawDirection the draw direction
     * @param xVar          the x var
     * @param yVar          the y var
     */
    public void paintLine(final Graphics2D g2d, final Point p1, final Point p2, final boolean drawDirection, final int xVar, final int yVar) {
        // g.setColor(new Color(255, 0, 0));
        if (drawDirection)
            g2d.setPaint(new GradientPaint(p1.x * this.getSquareWidth(), p1.y * this.getSquareHeight(), new Color(0, 0, 255), p2.x * this.getSquareWidth(), p2.y * this.getSquareHeight(), new Color(0, 255, 0)));
        g2d.drawLine(this.getSquare(p1).getXCenter() + xVar, this.getSquare(p1).getYCenter() + yVar, this.getSquare(p2).getXCenter() + xVar, this.getSquare(p2).getYCenter() + yVar);
    }

    /**
     * Paint rectangle.
     *
     * @param g the g
     * @param r the r
     */
    public void paintRectangle(final Graphics g, final Rectangle2D r) {
        g.drawRect((int) r.getX(), (int) r.getY(), (int) r.getWidth(), (int) r.getHeight());
    }

    /**
     * Rotate highlighted square.
     *
     * @param clockWise the clock wise
     */
    public void rotateHighlightedSquare(final boolean clockWise) {
        if (clockWise)
            switch (this.getSquare(this.highlightedPoint).getContents()) {
                case EMPTY:
                    this.setSquareContents(new Point(this.highlightedPoint), SquareType.HAZARD);
                    break;
                case HAZARD:
                    this.setSquareContents(new Point(this.highlightedPoint), SquareType.START);
                    break;
                case START:
                    this.setSquareContents(new Point(this.highlightedPoint), SquareType.START);
                    break;
                case FINISH:
                    this.setSquareContents(new Point(this.highlightedPoint), SquareType.FINISH);
                    break;
            }
        else
            switch (this.getSquare(this.highlightedPoint).getContents()) {
                case EMPTY:
                    this.setSquareContents(new Point(this.highlightedPoint), SquareType.FINISH);
                    break;
                case HAZARD:
                    this.setSquareContents(new Point(this.highlightedPoint), SquareType.EMPTY);
                    break;
                case START:
                    this.setSquareContents(new Point(this.highlightedPoint), SquareType.START);
                    break;
                case FINISH:
                    this.setSquareContents(new Point(this.highlightedPoint), SquareType.FINISH);
                    break;
            }
    }

    /**
     * Sets the highlighted square.
     *
     * @param x the x
     * @param y the y
     */
    public void setHighlightedSquare(final int x, final int y) {
        if (x >= 0 && x < this.grid[0].length && y >= 0 && y < this.grid.length)
            this.highlightedPoint = new Point(x, y);
    }

    /**
     * Sets the self.
     *
     * @param newGrid the new self
     */
    public void setSelf(final Grid newGrid) {
        this.grid = newGrid.grid;
        this.highlightThickness = newGrid.highlightThickness;
        this.nodes = newGrid.nodes;
        this.pathNodeMap = newGrid.pathNodeMap;
        this.pathNodeMaps = newGrid.pathNodeMaps;
        this.paths = newGrid.paths;
        this.squareWidth = newGrid.squareWidth;
        this.squareHeight = newGrid.squareHeight;
        this.startPoint = newGrid.startPoint;
        this.finishPoint = newGrid.finishPoint;
        this.furthestPoint = newGrid.furthestPoint;
        this.highlightedPoint = newGrid.highlightedPoint;
    }

    /**
     * Sets the square contents.
     *
     * @param p        the p
     * @param contents the contents
     */
    public void setSquareContents(final Point p, final SquareType contents) {
        try {
            this.getSquare(p).setContents(contents);
            if (contents == SquareType.HAZARD)
                this.getSquare(p).setDistance(-2);
            else if (contents == SquareType.START) {
                for (int y = 0; y < this.grid.length; y++)
                    for (int x = 0; x < this.grid[y].length; x++)
                        if (this.getSquare(new Point(x, y)).getContents() == SquareType.START)
                            this.setSquareContents(new Point(x, y), SquareType.EMPTY);

                this.startPoint = p;
                this.getSquare(p).setContents(contents);
                this.getSquare(p).setDistance(0);
            } else if (contents == SquareType.FINISH) {
                for (int y = 0; y < this.grid.length; y++)
                    for (int x = 0; x < this.grid[y].length; x++)
                        if (this.getSquare(new Point(x, y)).getContents() == SquareType.FINISH)
                            this.setSquareContents(new Point(x, y), SquareType.EMPTY);

                this.finishPoint = p;
                this.getSquare(p).setContents(contents);
            }
        } catch (final ArrayIndexOutOfBoundsException e) {
            System.out.println("\"setSquareContents\" is trying to access out of bounds!");
        }
    }

    /**
     * Sets the square distance.
     *
     * @param p        the p
     * @param distance the distance
     */
    public void setSquareDistance(final Point p, final int distance) {
        try {
            this.getSquare(p).setDistance(distance);
        } catch (final ArrayIndexOutOfBoundsException e) {
            // System.out.println("\"setSquareDistance\" is trying to access out of bounds!");
        }
    }

    /**
     * Test set highlighted square.
     *
     * @param x the x
     * @param y the y
     * @return true, if successful
     */
    public boolean testSetHighlightedSquare(final int x, final int y) {
        return x >= 0 && x < this.grid[0].length && y >= 0 && y < this.grid.length;
    }
}
