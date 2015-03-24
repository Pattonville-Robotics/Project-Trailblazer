package com.electronauts.virtualrobot;

// TODO: Auto-generated Javadoc
/**
 * The AbstractSensor encapsulates methods common to all types of sensor. It allows the AbstractRobot class to 
 */
public abstract class AbstractSensor
{

	/** The parent robot. */
	public AbstractRobot	parent;

	/**
	 * Instantiates a new abstract sensor from the robot on which it is mounted.
	 *
	 * @param parent
	 *            the parent robot
	 */
	public AbstractSensor(final AbstractRobot parent)
	{
		this.parent = parent;
	}

	/**
	 * Gets the robot that the sensor is mounted on.
	 *
	 * @return the robot on which the sensor is mounted
	 */
	public abstract AbstractRobot getRobot();

	/**
	 * Reads the value of the sensor. This can be in a variety of precisions, but it will always be a {@code Number}
	 *
	 * @return the value of the sensor
	 */
	public abstract Number readValue();
}
