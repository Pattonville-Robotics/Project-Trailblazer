package org.psdr3.robotics.trailblazer.math.geom;

import org.apache.commons.lang3.Range;
import org.apache.commons.math3.fraction.BigFraction;
import org.psdr3.robotics.trailblazer.math.MathUtils;

/**
 * Created by skaggsm on 9/29/15.
 */
public class Rectangle {
	public final BigFraction x1, y1, x2, y2, xMin, xMax, yMin, yMax;

	public Rectangle(double x1, double y1, double x2, double y2) {
		this(new BigFraction(x1), new BigFraction(y1), new BigFraction(x2), new BigFraction(y2));
	}

	public Rectangle(BigFraction x1, BigFraction y1, BigFraction x2, BigFraction y2) {
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
		this.xMin = MathUtils.min(this.x1, this.x2);
		this.xMax = MathUtils.max(this.x1, this.x2);
		this.yMin = MathUtils.min(this.y1, this.y2);
		this.yMax = MathUtils.max(this.y1, this.y2);
	}

	public Rectangle(long x1, long y1, long x2, long y2) {
		this(new BigFraction(x1), new BigFraction(y1), new BigFraction(x2), new BigFraction(y2));
	}

	public boolean intersects(Rectangle other) {
		Range<BigFraction> xR1 = Range.between(this.xMin, this.xMax);
		Range<BigFraction> xR2 = Range.between(other.xMin, other.xMax);
		Range<BigFraction> yR1 = Range.between(this.yMin, this.yMax);
		Range<BigFraction> yR2 = Range.between(other.yMin, other.yMax);
		return xR1.isOverlappedBy(xR2) && yR1.isOverlappedBy(yR2);
	}

	@Override
	public String toString() {
		return String.format("\"Rectangle from (%s, %s) to (%s, %s)\"", x1, y1, x2, y2);
	}
}
