package org.psdr3.robotics.trailblazer.math.geom;

import junit.framework.TestCase;

public class RectangleTest extends TestCase {

	Rectangle rectangle1;
	Rectangle rectangle2;

	public void setUp() throws Exception {
		super.setUp();
		this.rectangle1 = new Rectangle(0, 0, 10, 10);
		this.rectangle2 = new Rectangle(5, 5, 15, 15);
	}

	public void tearDown() throws Exception {
		super.tearDown();
	}

	public void testIntersects() {
		assertEquals(rectangle1.intersects(rectangle2), true);
		assertEquals(rectangle2.intersects(rectangle1), true);
	}
}