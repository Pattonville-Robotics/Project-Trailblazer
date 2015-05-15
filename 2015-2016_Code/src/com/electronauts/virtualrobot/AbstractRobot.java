package com.electronauts.virtualrobot;

import java.awt.Graphics2D;
import java.awt.geom.Path2D;
import java.util.ArrayList;

// TODO: Auto-generated Javadoc
/**
 * The {@code AbstractRobot} class provides methods and fields that are common to all forms of robot. The implementation of movement and the number of motors is
 * left to the descendants.
 */
public abstract class AbstractRobot implements Runnable
{
	/** The start time. */
	private long						startTime	= System.nanoTime();

	/** The motors. */
	protected volatile Motor[]			motors;

	/** The sensors. */
	protected ArrayList<AbstractSensor>	sensors;

	/** The initial angle around the circle. */
	protected double					theta;

	/**
	 * Adds a sensor to the robot.
	 *
	 * @param abstractSensor
	 *            the sensor to be added to the robot
	 */
	public void addSensor(final AbstractSensor abstractSensor)
	{
		this.sensors.add(abstractSensor);
	}

	/**
	 * Gets the angle of the robot from the x-axis to the line perpendicular to the forward direction of the robot.
	 *
	 * @return the robot's angle
	 */
	public abstract double getAngle();

	/**
	 * Gets the bounds.
	 *
	 * @param scale
	 *            the multiplier to scale the bounds up
	 * @return the {@code Path2D.Double} that surrounds the entire robot
	 */
	public abstract Path2D.Double getBounds(int scale);

	/**
	 * Gets the delta time nano.
	 *
	 * @return the delta time nano
	 */
	public long getDeltaTimeNano()
	{
		return System.nanoTime() - this.getStartTime();
	}

	/**
	 * Gets the time that has elapsed since the last equation change in seconds.
	 *
	 * @return the time elapsed in seconds
	 */
	public double getDeltaTimeSeconds()
	{
		return (System.nanoTime() - this.getStartTime()) / 1000000000d;
	}

	/**
	 * Gets the specified motor.
	 *
	 * @param motorData
	 *            the motor to request
	 * @return the motor
	 */
	public Motor getMotor(final MotorData motorData)
	{
		for (final Motor motor : this.motors)
			if (motor.getMotorData() == motorData) return motor;
		throw new IllegalArgumentException("Motor not found!");
	}

	/**
	 * Gets the motor's RPM.
	 *
	 * @param motor
	 *            the requested motor
	 * @return the motor's RPM
	 */
	public abstract double getMotorRPM(final MotorData motor);

	/**
	 * Gets the specified sensor.
	 *
	 * @param sensorType
	 *            the sensor type to be requested
	 * @return the requested sensor, if available
	 */
	public AbstractSensor getSensor(final SensorType sensorType)
	{
		for (final AbstractSensor absr : this.sensors)
			if (absr.getSensorType() == sensorType) return absr;
		throw new IllegalArgumentException("Sensor not found!");
	}

	/**
	 * Gets the sensor value.
	 *
	 * @param sensorType
	 *            the sensor type
	 * @return the sensor value
	 */
	public Number getSensorValue(final SensorType sensorType)
	{
		return this.getSensor(sensorType).readValue();
	}

	/**
	 * Gets the start time.
	 *
	 * @return the time at which the robot began movement since the last RPM change
	 */
	public long getStartTime()
	{
		return this.startTime;
	}

	/**
	 * Gets the initial angle around the robot's turning circle from a line parallel to the x-axis.
	 *
	 * @return the angle, in radians
	 */
	public double getTheta()
	{
		return this.theta;
	}

	public abstract void paint(final Graphics2D g2d, final int scale);

	/**
	 * Sets the specified motor's RPMs.
	 *
	 * @param motor1
	 *            the first motor
	 * @param motor2
	 *            the second motor
	 * @param rpm1
	 *            the first RPM
	 * @param rpm2
	 *            the second RPM
	 */
	public abstract void setMotorRPMs(final MotorData motor1, final MotorData motor2, final double rpm1, final double rpm2);

	/**
	 * Sets the start time.
	 *
	 * @param startTime
	 *            the new start time
	 */
	public void setStartTime(final long startTime)
	{
		this.startTime = startTime;
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
}
