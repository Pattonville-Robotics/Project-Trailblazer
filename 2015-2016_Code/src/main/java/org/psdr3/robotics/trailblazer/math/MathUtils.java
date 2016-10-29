package org.psdr3.robotics.trailblazer.math;

import org.apache.commons.math3.fraction.BigFraction;

/**
 * Created by Mitchell on 9/24/2015.
 */
public class MathUtils {
    public static void main(String[] args) {
        BigFraction a = BigFraction.ONE;
        BigFraction b = BigFraction.ONE_HALF;
        System.out.println(MathUtils.max(a, b) + "      Expected: 1");
        System.out.println(MathUtils.max(b, a) + "      Expected: 1");
        System.out.println(MathUtils.min(a, b) + "  Expected: 1 / 2");
        System.out.println(MathUtils.min(b, a) + "  Expected: 1 / 2");
        System.out.println(MathUtils.max(a, a) + "      Expected: 1");
        System.out.println(MathUtils.min(a, a) + "      Expected: 1");
    }

    public static BigFraction max(BigFraction a, BigFraction b) {
        return a.compareTo(b) == 1 ? a : b;
    }

    public static BigFraction min(BigFraction a, BigFraction b) {
        return a.compareTo(b) == 1 ? b : a;
    }
}
