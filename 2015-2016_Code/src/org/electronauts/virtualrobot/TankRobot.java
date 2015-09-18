package org.electronauts.virtualrobot;

import org.apache.commons.math3.util.FastMath;
import org.electronauts.mathutil.PolarPoint;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;

// TODO: Auto-generated Javadoc

/**
 * The TankRobot class simulates the motion of a tank-like robot with two motors through time.
 * <p/>
 * It uses the system's nanosecond timer to keep track of how long it has driven. It uses an equation to find the turning circle of the robot, given the robot's
 * width, the radius of it's wheels, and the RPMs of each of the motors. This system of physics simulation, based on the initial conditions and elapsed time, is
 * much more accurate than one based on the previous state of the robot.
 * <p
 * By default, it runs in real-time, but in the future, a timescale option will be implemented.
 *
 * @author Mitchell Skaggs
 */
public class TankRobot extends AbstractRobot {

	/**
	 * The x and y rotation centers.
	 */
	private double xRotCenter, yRotCenter;

	/**
	 * Constructs a TankRobot object given two motor objects.
	 *
	 * @param motorLeft  a {@code Motor} object that is used as the "left" motor
	 * @param motorRight a {@code Motor} object that is used as the "right" motor
	 */
	public TankRobot(final Motor motorLeft, final Motor motorRight) {
		this.motors = new Motor[2];
		this.motors[0] = motorRight;
		this.motors[1] = motorLeft;
	}

	/**
	 * Gets the coordinates of the center of the robot.
	 *
	 * @return a {@code Point2D.Double} that describes the center of the robot at the time.
	 */
	public Point2D.Double getLocation() {
		this.updatePosition();
		return new Point2D.Double((this.getMotor(MotorData.MOTOR_LEFT).getX() + this.getMotor(MotorData.MOTOR_RIGHT).getX()) / 2, (this.getMotor(MotorData.MOTOR_LEFT).getY() + this.getMotor(MotorData.MOTOR_RIGHT).getY()) / 2);
	}

	/**
	 * Update the position of the robot based on the current time.
	 */
	public void updatePosition() {
		final Motor motorL = this.getMotor(MotorData.MOTOR_LEFT);
		final Motor motorR = this.getMotor(MotorData.MOTOR_RIGHT);

		final double currentDeltaTime = this.getDeltaTimeSeconds();
		final double motorLDistance = currentDeltaTime * (motorL.getRPM() / 60) * 2 * FastMath.PI * motorL.getWheel().getRadius();
		final double motorRDistance = currentDeltaTime * (motorR.getRPM() / 60) * 2 * FastMath.PI * motorR.getWheel().getRadius();

		double radiansTurned;
		if (motorL.getRadius() != 0 && motorR.getRadius() != 0)
			radiansTurned = (motorLDistance / (2 * FastMath.PI * motorL.getRadius()) * (2 * FastMath.PI) + motorRDistance / (2 * FastMath.PI * motorR.getRadius()) * (2 * FastMath.PI)) / 2;
		else if (motorL.getRadius() != 0)
			radiansTurned = motorLDistance / (2 * FastMath.PI * motorL.getRadius()) * (2 * FastMath.PI);
		else if (motorR.getRadius() != 0)
			radiansTurned = motorRDistance / (2 * FastMath.PI * motorR.getRadius()) * (2 * FastMath.PI);
		else
			radiansTurned = 0;

		radiansTurned = radiansTurned % (FastMath.PI * 2);

		if (!(motorLDistance == 0 && motorRDistance == 0 || motorLDistance == motorRDistance)) {
			motorL.setX(motorL.getRadius() * FastMath.cos(radiansTurned + this.getTheta()) + this.getXRotCenter());
			motorL.setY(motorL.getRadius() * FastMath.sin(radiansTurned + this.getTheta()) + this.getYRotCenter());

			motorR.setX(motorR.getRadius() * FastMath.cos(radiansTurned + this.getTheta()) + this.getXRotCenter());
			motorR.setY(motorR.getRadius() * FastMath.sin(radiansTurned + this.getTheta()) + this.getYRotCenter());
		} else if (motorLDistance == motorRDistance) {
			final PolarPoint p1 = new PolarPoint(motorLDistance, this.getAngle() + FastMath.PI / 2);
			final PolarPoint p2 = new PolarPoint(motorRDistance, this.getAngle() + FastMath.PI / 2);

			motorL.setX(motorL.getXLineStart() + p1.getX());
			motorL.setY(motorL.getYLineStart() + p1.getY());
			motorR.setX(motorR.getXLineStart() + p2.getX());
			motorR.setY(motorR.getYLineStart() + p2.getY());
		}
	}

	/**
	 * Gets the x rotation center.
	 *
	 * @return the x rotation center
	 */
	public double getXRotCenter() {
		return this.xRotCenter;
	}

	/**
	 * Gets the y rotation center.
	 *
	 * @return the y rotation center
	 */
	public double getYRotCenter() {
		return this.yRotCenter;
	}

	/**
	 * Returns the angle from the x-axis to the line segment formed by the two motors, using the {@code atan2} method in the {@code Math} class.
	 *
	 * @return the angle from the x-axis to the line segment formed by the two motors.
	 */
	@Override
	public double getAngle() {
		return FastMath.atan2(this.getMotor(MotorData.MOTOR_RIGHT).getY() - this.getMotor(MotorData.MOTOR_LEFT).getY(), this.getMotor(MotorData.MOTOR_RIGHT).getX() - this.getMotor(MotorData.MOTOR_LEFT).getX());
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.electronauts.virtualrobot.AbstractRobot#getBounds(int)
	 */
	@Override
	public Path2D.Double getBounds(final int scale) {
		this.updatePosition();
		final Path2D.Double output = new Path2D.Double();
		final PolarPoint p1 = new PolarPoint(this.getWidth() / 2, this.getAngle() + FastMath.PI / 2);
		final PolarPoint p2 = new PolarPoint(this.getWidth() / 2, this.getAngle() - FastMath.PI / 2);
		final Motor motorL = this.getMotor(MotorData.MOTOR_LEFT);
		final Motor motorR = this.getMotor(MotorData.MOTOR_RIGHT);

		output.moveTo(scale * (p1.getX() + motorL.getX()), scale * (p1.getY() + motorL.getY()));
		output.lineTo(scale * (p2.getX() + motorL.getX()), scale * (p2.getY() + motorL.getY()));

		output.lineTo(scale * (p2.getX() + motorR.getX()), scale * (p2.getY() + motorR.getY()));
		output.lineTo(scale * (p1.getX() + motorR.getX()), scale * (p1.getY() + motorR.getY()));
		output.closePath();

		return output;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.electronauts.virtualrobot.AbstractRobot#getMotorRPM(org.electronauts.virtualrobot.MotorData)
	 */
	@Override
	public double getMotorRPM(final MotorData motor) {
		return this.getMotor(motor).getRPM();
	}

	/**
	 * Paints the robot onto the specified Graphics2D object. The robot is shifted down {@code Y_SHIFT} pixels and reflected to account for the
	 * {@code JComponent} coordinate system.
	 * <p/>
	 * The {@code scale} is used to resize the robot to make it more visible. A scale of 1 corresponds to a 1:1 ratio of distance to pixels.
	 *
	 * @param g2d   the {@code Graphics2D} that is to be drawn on
	 * @param scale the scale at which it should be drawn
	 */
	@Override
	public void paint(final Graphics2D g2d, final int scale) {
		this.updatePosition();
		final PolarPoint p1 = new PolarPoint(this.getWidth() / 2, this.getAngle() + FastMath.PI / 2);
		final Motor motorL = this.getMotor(MotorData.MOTOR_LEFT);
		final Motor motorR = this.getMotor(MotorData.MOTOR_RIGHT);
		final Path2D.Double arrow = new Path2D.Double();
		arrow.moveTo(scale * motorL.getX(), scale * motorL.getY());
		arrow.lineTo(scale * motorR.getX(), scale * motorR.getY());
		arrow.lineTo(scale * ((motorL.getX() + motorR.getX()) / 2 + p1.getX()), scale * ((motorL.getY() + motorR.getY()) / 2 + p1.getY()));
		arrow.closePath();
		final Path2D.Double bounds = this.getBounds(scale);

		g2d.setColor(Color.BLACK);
		g2d.draw(bounds);
		g2d.setColor(Color.GRAY);
		g2d.fill(arrow);
		g2d.setColor(Color.RED);
		g2d.fillOval((int) (motorL.getX() * scale - 2), (int) (motorL.getY() * scale + 2), 4, 4);
		g2d.fillOval((int) (motorR.getX() * scale - 2), (int) (motorR.getY() * scale + 2), 4, 4);
		g2d.setColor(Color.DARK_GRAY);
		g2d.drawString("Left Motor", (int) (motorL.getX() * scale), (int) (motorL.getY() * scale));
		g2d.drawString("Right Motor", (int) (motorR.getX() * scale), (int) (motorR.getY() * scale));

		g2d.setColor(Color.BLUE);
		g2d.drawLine((int) (this.getXRotCenter() * scale), (int) (this.getYRotCenter() * scale), (int) (motorL.getX() * scale), (int) (motorL.getY() * scale));
		g2d.setColor(Color.RED);
		g2d.drawLine((int) (this.getXRotCenter() * scale), (int) (this.getYRotCenter() * scale), (int) (motorR.getX() * scale), (int) (motorR.getY() * scale));
	}

	/**
	 * Gets the width of the robot.
	 *
	 * @return the width
	 */
	public double getWidth() {
		return FastMath.sqrt(FastMath.pow(this.getMotor(MotorData.MOTOR_RIGHT).getX() - this.getMotor(MotorData.MOTOR_LEFT).getX(), 2) + FastMath.pow(this.getMotor(MotorData.MOTOR_RIGHT).getY() - this.getMotor(MotorData.MOTOR_LEFT).getY(), 2));
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.electronauts.virtualrobot.AbstractRobot#setMotorRPMs(org.electronauts.virtualrobot.MotorData, org.electronauts.virtualrobot.MotorData, double,
	 * double)
	 */
	@Override
	public synchronized void setMotorRPMs(final MotorData motor1, final MotorData motor2, final double rpm1, final double rpm2) {
		final Motor motorL = this.getMotor(MotorData.MOTOR_LEFT);
		final Motor motorR = this.getMotor(MotorData.MOTOR_RIGHT);

		if (!(motorL.getRPM() == 0 && motorR.getRPM() == 0))
			this.updatePosition();

		this.setStartTime(System.nanoTime());

		this.getMotor(motor1).setRPM(rpm1);
		this.getMotor(motor2).setRPM(rpm2);

		if (motorL.getRPM() != motorR.getRPM()) {
			final PolarPoint p1 = new PolarPoint(motorL.getVelocity() * this.getWidth() / (motorR.getVelocity() - motorL.getVelocity()), this.getAngle() + FastMath.PI);

			this.setXRotCenter(motorL.getX() + p1.getX());
			this.setYRotCenter(motorL.getY() + p1.getY());

			motorL.setRadius(p1.getRadius());

			motorR.setRadius(p1.getRadius() + this.getWidth());

			this.setTheta(this.getAngle());
		} else if (motorL.getRPM() == motorR.getRPM()) {
			motorL.setXLineStart(motorL.getX());
			motorL.setYLineStart(motorL.getY());
			motorR.setXLineStart(motorR.getX());
			motorR.setYLineStart(motorR.getY());
		}
	}

	/**
	 * Sets the y rotation center.
	 *
	 * @param yRotCenter the new y rotation center
	 */
	public void setYRotCenter(final double yRotCenter) {
		this.yRotCenter = yRotCenter;
	}

	/**
	 * Sets the x rotation center.
	 *
	 * @param xRotCenter the new x rotation center
	 */
	public void setXRotCenter(final double xRotCenter) {
		this.xRotCenter = xRotCenter;
	}

	/**
	 * Reads the virtual gyroscope onboard the robot.
	 *
	 * @return the angle, in radians, that the robot has turned
	 */
	public double readGyro() {
		return this.getAngle() + FastMath.PI / 2;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
	}
}
