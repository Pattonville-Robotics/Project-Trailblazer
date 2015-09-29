package org.psdr3.robotics.trailblazer.math.geom;

import org.apache.commons.math3.fraction.BigFraction;

import java.util.function.Function;

/**
 * Created by skaggsm on 9/19/15.
 */
public class Line {
	BigFraction a, b, c;

	public Line(double a, double b, double c) {
		this.a = new BigFraction(a);
		this.b = new BigFraction(b);
		this.c = new BigFraction(c);
	}

	public Line(BigFraction a, BigFraction b, BigFraction c) {
		this.a = a;
		this.b = b;
		this.c = c;
	}

	public Function<BigFraction, BigFraction> getXToYFunction() {
		if (this.isVertical()) {
			throw new UnsupportedOperationException("Not a function of X; vertical line.");
		} else if (this.isHorizontal()) {
			return new Function<BigFraction, BigFraction>() {
				@Override
				public BigFraction apply(BigFraction bigFraction) {
					return c.divide(b);
				}
			};
		} else {
			return new Function<BigFraction, BigFraction>() {
				@Override
				public BigFraction apply(BigFraction bigFraction) {

					return c.subtract(a.multiply(bigFraction)).divide(b);
				}
			};
		}
	}

	public boolean isVertical() {
		return this.b.equals(BigFraction.ZERO);
	}

	public boolean isHorizontal() {
		return this.a.equals(BigFraction.ZERO);
	}

	public Function<BigFraction, BigFraction> getYToXFunction() {
		if (this.isHorizontal()) {
			throw new UnsupportedOperationException("Not a function of Y; horizontal line.");
		} else if (this.isVertical()) {
			return new Function<BigFraction, BigFraction>() {
				@Override
				public BigFraction apply(BigFraction bigFraction) {
					return c.divide(a);
				}
			};
		} else {
			return new Function<BigFraction, BigFraction>() {
				@Override
				public BigFraction apply(BigFraction bigFraction) {

					return c.subtract(b.multiply(bigFraction)).divide(a);
				}
			};
		}
	}

	public boolean isOblique() {
		return !this.isVertical() && !this.isHorizontal();
	}
}
