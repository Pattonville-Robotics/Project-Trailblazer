package com.electronauts.virtualrobot;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;

import com.electronauts.mathutil.PolarPoint;

// TODO: Auto-generated Javadoc
/**
 * The Class TankRobot.
 *
 * @author Mitchell Skaggs
 */
public class TankRobot extends AbstractRobot
{

	/** The x and y rotation centers. */
	private double	xRotCenter, yRotCenter;

	/** The initial angle around the circle */
	private double	theta;

	/**
	 * Constructs a TankRobot object given two motor objects.
	 *
	 * @param motorLeft
	 *            a {@code Motor} object that is used as the "left" motor
	 * @param motorRight
	 *            a {@code Motor} object that is used as the "right" motor
	 */
	public TankRobot(final Motor motorLeft, final Motor motorRight)
	{
		this.motors = new Motor[2];
		this.motors[0] = motorRight;
		this.motors[1] = motorLeft;
	}

	/**
	 * Returns the angle from the x-axis to the line segment formed by the two motors, using the {@code atan2} method in the {@code Math} class.
	 * 
	 * @return the angle from the x-axis to the line segment formed by the two motors.
	 */
	public double getAngle()
	{
		return Math.atan2(this.getMotor(MotorData.MOTOR_RIGHT).getY() - this.getMotor(MotorData.MOTOR_LEFT).getY(), this.getMotor(MotorData.MOTOR_RIGHT).getX()
				- this.getMotor(MotorData.MOTOR_LEFT).getX());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.electronauts.virtualrobot.AbstractRobot#getBounds(int)
	 */
	@Override
	public Path2D.Double getBounds(final int scale)
	{
		this.updatePosition();
		final Path2D.Double output = new Path2D.Double();
		final PolarPoint p1 = new PolarPoint(this.getWidth() / 2, this.getAngle() + Math.PI / 2);
		final PolarPoint p2 = new PolarPoint(this.getWidth() / 2, this.getAngle() - Math.PI / 2);
		final Motor motorL = this.getMotor(MotorData.MOTOR_LEFT);
		final Motor motorR = this.getMotor(MotorData.MOTOR_RIGHT);

		output.moveTo(scale * (p1.getX() + motorL.getX()), scale * (p1.getY() + motorL.getY()));
		output.lineTo(scale * (p2.getX() + motorL.getX()), scale * (p2.getY() + motorL.getY()));

		output.lineTo(scale * (p2.getX() + motorR.getX()), scale * (p2.getY() + motorR.getY()));
		output.lineTo(scale * (p1.getX() + motorR.getX()), scale * (p1.getY() + motorR.getY()));
		output.closePath();

		return output;
	}

	/**
	 * Gets the coordinates of the center of the robot.
	 *
	 * @return a {@code Point2D.Double} that describes the center of the robot at the time.
	 */
	public Point2D.Double getLocation()
	{
		this.updatePosition();
		return new Point2D.Double((this.getMotor(MotorData.MOTOR_LEFT).getX() + this.getMotor(MotorData.MOTOR_RIGHT).getX()) / 2, (this.getMotor(
				MotorData.MOTOR_LEFT).getY() + this.getMotor(MotorData.MOTOR_RIGHT).getY()) / 2);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.electronauts.virtualrobot.AbstractRobot#getMotorRPM(com.electronauts.virtualrobot.MotorData)
	 */
	@Override
	public double getMotorRPM(final MotorData motor)
	{
		return this.getMotor(motor).getRPM();
	}

	/**
	 * Gets the initial angle around the robot's turning circle.
	 *
	 * @return the angle, in radians
	 */
	public double getTheta()
	{
		return this.theta;
	}

	/**
	 * Gets the width of the robot.
	 *
	 * @return the width
	 */
	public double getWidth()
	{
		return Math.sqrt(Math.pow(this.getMotor(MotorData.MOTOR_RIGHT).getX() - this.getMotor(MotorData.MOTOR_LEFT).getX(), 2)
				+ Math.pow(this.getMotor(MotorData.MOTOR_RIGHT).getY() - this.getMotor(MotorData.MOTOR_LEFT).getY(), 2));
	}

	/**
	 * Gets the x rotation center.
	 *
	 * @return the x rotation center
	 */
	public double getXRotCenter()
	{
		return this.xRotCenter;
	}

	/**
	 * Gets the y rotation center.
	 *
	 * @return the y rotation center
	 */
	public double getYRotCenter()
	{
		return this.yRotCenter;
	}

	/**
	 * Paints the robot onto the specified Graphics2D object.
	 *
	 * @param g2d
	 *            the {@code Graphics2D} that is to be drawn on
	 * @param scale
	 *            the scale at which it should be drawn
	 */
	public void paint(final Graphics2D g2d, final int scale)
	{
		this.updatePosition();
		final PolarPoint p1 = new PolarPoint(this.getWidth() / 2, this.getAngle() + Math.PI / 2);
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
		g2d.fillOval((int) (motorL.getX() * scale - 2), (int) (motorL.getY() * scale - 2), 4, 4);
		g2d.fillOval((int) (motorR.getX() * scale - 2), (int) (motorR.getY() * scale - 2), 4, 4);
		g2d.setColor(Color.DARK_GRAY);
		g2d.drawString("Left Motor", (int) (motorL.getX() * scale), (int) (motorL.getY() * scale));
		g2d.drawString("Right Motor", (int) (motorR.getX() * scale), (int) (motorR.getY() * scale));

		g2d.setColor(Color.BLUE);
		g2d.drawLine((int) (this.getXRotCenter() * scale), (int) (this.getYRotCenter() * scale), (int) (motorL.getX() * scale), (int) (motorL.getY() * scale));
		g2d.setColor(Color.RED);
		g2d.drawLine((int) (this.getXRotCenter() * scale), (int) (this.getYRotCenter() * scale), (int) (motorR.getX() * scale), (int) (motorR.getY() * scale));
	}

	/**
	 * Reads the virtual gyroscope onboard the robot.
	 *
	 * @return the angle, in radians, that the robot has turned
	 */
	public double readGyro()
	{
		return this.getAngle() + Math.PI / 2;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run()
	{
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.electronauts.virtualrobot.AbstractRobot#setMotorRPMs(com.electronauts.virtualrobot.MotorData, com.electronauts.virtualrobot.MotorData, double,
	 * double)
	 */
	@Override
	public synchronized void setMotorRPMs(final MotorData motor1, final MotorData motor2, final double rpm1, final double rpm2)
	{
		final Motor motorL = this.getMotor(MotorData.MOTOR_LEFT);
		final Motor motorR = this.getMotor(MotorData.MOTOR_RIGHT);

		if (!(motorL.getRPM() == 0 && motorR.getRPM() == 0)) this.updatePosition(); // Prevent resolution errors if called just before the next update

		this.setStartTime(System.nanoTime());

		this.getMotor(motor1).setRPM(rpm1);
		this.getMotor(motor2).setRPM(rpm2);

		if (motorL.getRPM() != motorR.getRPM())
		{
			final PolarPoint p1 = new PolarPoint(motorL.getVelocity() * this.getWidth() / (motorR.getVelocity() - motorL.getVelocity()), this.getAngle()
					+ Math.PI);

			this.setXRotCenter(motorL.getX() + p1.getX());
			this.setYRotCenter(motorL.getY() + p1.getY());

			motorL.setRadius(p1.getRadius());

			motorR.setRadius(p1.getRadius() + this.getWidth());

			this.setTheta(this.getAngle());
		}
		else if (motorL.getRPM() == motorR.getRPM())
		{
			motorL.setxLineStart(motorL.getX());
			motorL.setyLineStart(motorL.getY());
			motorR.setxLineStart(motorR.getX());
			motorR.setyLineStart(motorR.getY());
		}
	}

	/**
	 * Sets the theta.
	 *
	 * @param theta
	 *            the new theta
	 */
	public void setTheta(final double theta)
	{
		this.theta = theta;
	}

	/**
	 * Sets the x rotation center.
	 *
	 * @param xRotCenter
	 *            the new x rotation center
	 */
	public void setXRotCenter(final double xRotCenter)
	{
		this.xRotCenter = xRotCenter;
	}

	/**
	 * Sets the y rotation center.
	 *
	 * @param yRotCenter
	 *            the new y rotation center
	 */
	public void setYRotCenter(final double yRotCenter)
	{
		this.yRotCenter = yRotCenter;
	}

	/**
	 * Update the position of the robot based on the current time.
	 */
	public void updatePosition()
	{
		final Motor motorL = this.getMotor(MotorData.MOTOR_LEFT);
		final Motor motorR = this.getMotor(MotorData.MOTOR_RIGHT);

		final double currentDeltaTime = this.getDeltaTimeSeconds(); // Synchronize left and right motors
		final double motorLDistance = currentDeltaTime * (motorL.getRPM() / 60) * 2 * Math.PI * motorL.getWheel().getRadius();
		final double motorRDistance = currentDeltaTime * (motorR.getRPM() / 60) * 2 * Math.PI * motorR.getWheel().getRadius();

		double radiansTurned;
		if (motorL.getRadius() != 0 && motorR.getRadius() != 0)
			radiansTurned = (motorLDistance / (2 * Math.PI * motorL.getRadius()) * (2 * Math.PI) + motorRDistance / (2 * Math.PI * motorR.getRadius())
					* (2 * Math.PI)) / 2;
		else if (motorL.getRadius() != 0)
			radiansTurned = motorLDistance / (2 * Math.PI * motorL.getRadius()) * (2 * Math.PI);
		else if (motorR.getRadius() != 0)
			radiansTurned = motorRDistance / (2 * Math.PI * motorR.getRadius()) * (2 * Math.PI);
		else
			radiansTurned = 0;

		radiansTurned = radiansTurned % (Math.PI * 2);

		if (!(motorL.getRPM() == 0 && motorR.getRPM() == 0 || motorL.getRPM() == motorR.getRPM()))
		{
			motorL.setX(motorL.getRadius() * Math.cos(radiansTurned + this.getTheta()) + this.getXRotCenter());
			motorL.setY(motorL.getRadius() * Math.sin(radiansTurned + this.getTheta()) + this.getYRotCenter());

			motorR.setX(motorR.getRadius() * Math.cos(radiansTurned + this.getTheta()) + this.getXRotCenter());
			motorR.setY(motorR.getRadius() * Math.sin(radiansTurned + this.getTheta()) + this.getYRotCenter());
		}
		else if (motorL.getRPM() == motorR.getRPM())
		{
			final PolarPoint p1 = new PolarPoint(motorLDistance, this.getAngle() + Math.PI / 2);
			final PolarPoint p2 = new PolarPoint(motorRDistance, this.getAngle() + Math.PI / 2);

			motorL.setX(motorL.getxLineStart() + p1.getX());
			motorL.setY(motorL.getyLineStart() + p1.getY());
			motorR.setX(motorR.getxLineStart() + p2.getX());
			motorR.setY(motorR.getyLineStart() + p2.getY());
		}
	}
}
