package org.electronauts.virtualrobot;

// TODO: Auto-generated Javadoc

/**
 * The AbstractSensor encapsulates methods common to all types of sensor. It allows the AbstractRobot class to query the various sensors onboard and make
 * informed decisions based on the state of the simulation and surroundings.
 */
public abstract class AbstractSensor {

	/**
	 * The parent robot.
	 */
	public AbstractRobot parent;

	/**
	 * The sensor type.
	 */
	private SensorType sensorType;

	/**
	 * Instantiates a new abstract sensor from the robot on which it is mounted.
	 *
	 * @param parent     the parent robot
	 * @param sensorType the sensor type
	 */
	protected AbstractSensor(final AbstractRobot parent, final SensorType sensorType) {
		this.parent = parent;
		this.sensorType = sensorType;
	}

	/**
	 * Gets the robot that the sensor is mounted on.
	 *
	 * @return the robot on which the sensor is mounted
	 */
	public abstract AbstractRobot getRobot();

	/**
	 * Gets the sensor type.
	 *
	 * @return the sensorType
	 */
	public SensorType getSensorType() {
		return this.sensorType;
	}

	/**
	 * Sets the sensor type.
	 *
	 * @param sensorType the sensorType to set
	 */
	public void setSensorType(final SensorType sensorType) {
		this.sensorType = sensorType;
	}

	/**
	 * Reads the value of the sensor. This can be in a variety of precisions, but it will always be a {@code Number}
	 *
	 * @return the value of the sensor
	 */
	public abstract Number readValue();
}
