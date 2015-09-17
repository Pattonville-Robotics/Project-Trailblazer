package org.electronauts.virtualrobot;

// TODO: Auto-generated Javadoc

/**
 * The Class Motor.
 */
public class Motor {

	/**
	 * The motor data.
	 */
	private final MotorData motorData;

	/**
	 * The wheel.
	 */
	private Wheel wheel;

	/**
	 * The radius.
	 */
	private double x, y, xLineStart, yLineStart, rpm, radius, orientation;

	/**
	 * Instantiates a new motor.
	 *
	 * @param motorData the motor data
	 * @param wheel     the wheel
	 * @param x         the x
	 * @param y         the y
	 */
	public Motor(final MotorData motorData, final Wheel wheel, final double x, final double y) {
		this.motorData = motorData;
		this.wheel = wheel;
		this.x = x;
		this.y = y;
		this.rpm = 0;
	}

	/**
	 * Instantiates a new motor.
	 *
	 * @param motorData the motor data
	 * @param wheel     the wheel
	 * @param rpm       the rpm
	 * @param x         the x
	 * @param y         the y
	 */
	public Motor(final MotorData motorData, final Wheel wheel, final double rpm, final double x, final double y) {
		this.motorData = motorData;
		this.wheel = wheel;
		this.x = x;
		this.y = y;
		this.rpm = rpm;
	}

	public Motor(final MotorData motorData, final Wheel wheel, final double rpm, final double x, final double y, final double orientation) {
		this.motorData = motorData;
		this.wheel = wheel;
		this.x = x;
		this.y = y;
		this.rpm = rpm;
		this.orientation = orientation;
	}

	/**
	 * Gets the motor data.
	 *
	 * @return the motor data
	 */
	public MotorData getMotorData() {
		return this.motorData;
	}

	/**
	 * @return the orientation
	 */
	public double getOrientation() {
		return this.orientation;
	}

	/**
	 * @param orientation the orientation to set
	 */
	public void setOrientation(final double orientation) {
		this.orientation = orientation;
	}

	/**
	 * Gets the radius.
	 *
	 * @return the radius
	 */
	public double getRadius() {
		return this.radius;
	}

	/**
	 * Sets the radius.
	 *
	 * @param radius the new radius
	 */
	public void setRadius(final double radius) {
		this.radius = radius;
	}

	/**
	 * Gets the velocity.
	 *
	 * @return the velocity
	 */
	public double getVelocity() {
		return this.getRPM() * this.getWheel().getCircumference();
	}

	/**
	 * Gets the rpm.
	 *
	 * @return the rpm
	 */
	public double getRPM() {
		return this.rpm;
	}

	/**
	 * Gets the wheel.
	 *
	 * @return the wheel
	 */
	public Wheel getWheel() {
		return this.wheel;
	}

	/**
	 * Sets the wheel.
	 *
	 * @param wheel the new wheel
	 */
	public void setWheel(final Wheel wheel) {
		this.wheel = wheel;
	}

	/**
	 * Sets the rpm.
	 *
	 * @param rpm the new rpm
	 */
	public void setRPM(final double rpm) {
		this.rpm = rpm;
	}

	/**
	 * Gets the x.
	 *
	 * @return the x
	 */
	public double getX() {
		return this.x;
	}

	/**
	 * Sets the x.
	 *
	 * @param x the new x
	 */
	public void setX(final double x) {
		this.x = x;
	}

	/**
	 * Gets the x line start.
	 *
	 * @return the x line start
	 */
	public double getXLineStart() {
		return this.xLineStart;
	}

	/**
	 * Sets the x line start.
	 *
	 * @param xLineStart the new x line start
	 */
	public void setXLineStart(final double xLineStart) {
		this.xLineStart = xLineStart;
	}

	/**
	 * Gets the y.
	 *
	 * @return the y
	 */
	public double getY() {
		return this.y;
	}

	/**
	 * Sets the y.
	 *
	 * @param y the new y
	 */
	public void setY(final double y) {
		this.y = y;
	}

	/**
	 * Gets the y line start.
	 *
	 * @return the y line start
	 */
	public double getYLineStart() {
		return this.yLineStart;
	}

	/**
	 * Sets the y line start.
	 *
	 * @param yLineStart the new y line start
	 */
	public void setYLineStart(final double yLineStart) {
		this.yLineStart = yLineStart;
	}
}
