package org.electronauts.pathfind;

import com.sun.istack.internal.NotNull;
import org.apache.commons.math3.util.FastMath;

import java.awt.Point;
import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedList;

// TODO: Auto-generated Javadoc

/**
 * The Class VertexMap.
 */
public class VertexMap implements Comparable<VertexMap>, Cloneable, Serializable {

    /**
     * The Constant serialVersionUID.
     */
    private static final long serialVersionUID = 1L;
    /**
     * The points.
     */
    private LinkedList<Point> points;

    /**
     * Instantiates a new vertex map.
     */
    public VertexMap() {
        this.points = new LinkedList<Point>();
    }

    /**
     * Instantiates a new vertex map.
     *
     * @param points the points
     */
    public VertexMap(final LinkedList<Point> points) {
        this.points = points;
    }

    /**
     * Gets the distance.
     *
     * @param p1 the p1
     * @param p2 the p2
     * @return the distance
     */
    public static double getDistance(final Point p1, final Point p2) {
        return FastMath.sqrt(FastMath.pow(p2.x - p1.x, 2) + FastMath.pow(p2.y - p1.y, 2));
    }

    /**
     * Adds the point.
     *
     * @param p the p
     */
    public void addPoint(final Point p) {
        this.points.add(p);
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#clone()
     */
    @Override
    public VertexMap clone() {
        final LinkedList<Point> newList = new LinkedList<Point>();

        for (final Point p : this.points)
            newList.add(p);

        return new VertexMap(newList);
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return this.points.toString();
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    @Override
    public int compareTo(final @NotNull VertexMap map) {
        if (this.getTotalDistance() > map.getTotalDistance())
            return 1;
        else if (this.getTotalDistance() < map.getTotalDistance())
            return -1;
        else
            return 0;
    }

    /**
     * Gets the total distance.
     *
     * @return the total distance
     */
    public double getTotalDistance() {
        double sum = 0;

        for (int i = 0; i < this.points.size() - 1; i++)
            sum += VertexMap.getDistance(this.points.get(i), this.points.get(i + 1));

        return sum;
    }

    /**
     * Gets the array.
     *
     * @return the array
     */
    public Point[] getArray() {
        final Point[] result = new Point[this.points.size()];
        return this.points.toArray(result);
    }

    /**
     * Gets the last point.
     *
     * @return the last point
     */
    public Point getLastPoint() {
        return this.points.get(this.points.size() - 1);
    }

    /**
     * Gets the list.
     *
     * @return the list
     */
    public LinkedList<Point> getList() {
        return this.points;
    }

    /**
     * Gets the point.
     *
     * @param i the i
     * @return the point
     */
    public Point getPoint(final int i) {
        try {
            return this.points.get(i);
        } catch (final Exception e) {
            System.out.println("Tried to get point at " + i + ", but the size of the array is only " + this.points.size() + "!");
            return this.getPoint(i - 1);
        }
    }

    /**
     * Gets the reverse map.
     *
     * @return the reverse map
     */
    public VertexMap getReverseMap() {
        return new VertexMap(this.getReverseList());
    }

    /**
     * Gets the reverse list.
     *
     * @return the reverse list
     */
    public LinkedList<Point> getReverseList() {
        final Iterator<Point> reverseIterator = this.points.descendingIterator();
        final LinkedList<Point> newPoints = new LinkedList<Point>();

        while (reverseIterator.hasNext())
            newPoints.add(reverseIterator.next());

        return newPoints;
    }

    /**
     * Reverse list.
     */
    public void reverseList() {
        final Iterator<Point> reverseIterator = this.points.descendingIterator();
        final LinkedList<Point> newPoints = new LinkedList<Point>();

        while (reverseIterator.hasNext())
            newPoints.add(reverseIterator.next());

        this.points = newPoints;
    }

    /**
     * Size.
     *
     * @return the int
     */
    public int size() {
        return this.points.size();
    }
}
