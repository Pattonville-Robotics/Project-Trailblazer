package org.psdr3.robotics.trailblazer.math.geom;

import org.apache.commons.lang3.Range;
import org.apache.commons.math3.fraction.BigFraction;
import org.psdr3.robotics.trailblazer.math.MathUtils;

/**
 * Created by Mitchell on 9/24/2015.
 */
public class LineSegment extends Line {
	public final BigFraction x1, y1, x2, y2, xMin, xMax, yMin, yMax;

	public LineSegment(double x1, double y1, double x2, double y2) {
		this(new BigFraction(x1), new BigFraction(y1), new BigFraction(x2), new BigFraction(y2));
	}

	public LineSegment(BigFraction x1, BigFraction y1, BigFraction x2, BigFraction y2) {
		super(y2.subtract(y1), x1.subtract(x2), x1.multiply(y2).subtract(x2.multiply(y1)));
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
		this.xMin = MathUtils.min(this.x1, this.x2);
		this.xMax = MathUtils.max(this.x1, this.x2);
		this.yMin = MathUtils.min(this.y1, this.y2);
		this.yMax = MathUtils.max(this.y1, this.y2);
	}

	public static void main(String[] args) {
		LineSegment ab = new LineSegment(1, 1, 5, 5);
		LineSegment cd = new LineSegment(5, 0, 4, 1);
		System.out.println(ab + " bounding box intersects " + cd + " bounding box: " + ab.axisAlignedBoundingBoxIntersects(cd));
	}

	@Deprecated
	public boolean axisAlignedBoundingBoxIntersects(LineSegment segment) {
		Range<BigFraction> xR1 = Range.between(this.xMin, this.xMax);
		Range<BigFraction> xR2 = Range.between(segment.xMin, segment.xMax);
		Range<BigFraction> yR1 = Range.between(this.yMin, this.yMax);
		Range<BigFraction> yR2 = Range.between(segment.yMin, segment.yMax);
		return xR1.isOverlappedBy(xR2) && yR1.isOverlappedBy(yR2);
	}

	@Override
	public String toString() {
		return String.format("\"Line Segment from (%s, %s) to (%s, %s)\"", this.x1.toString(), this.y1.toString(), this.x2.toString(), this.y2.toString());
	}
}