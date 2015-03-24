package com.electronauts.virtualrobot;

// TODO: Auto-generated Javadoc
/**
 * The Gyroscope class is attached to a robot of type {@link AbstractRobot} and provides a way to query the robot's direction.
 */
public class Gyroscope extends AbstractSensor
{

	/** The angle that is measured from. */
	private double	baseAngle;

	/**
	 * Instantiates a new gyroscope from a parent robot.
	 *
	 * @param parent
	 *            the robot that the sensor is attached to.
	 */
	public Gyroscope(final AbstractRobot parent)
	{
		super(parent);
		this.resetBaseAngle();
	}

	/**
	 * Gets the base angle. This is the angle from which the robot's position is measured from.
	 *
	 * @return the base angle
	 */
	public double getBaseAngle()
	{
		return this.baseAngle;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.electronauts.virtualrobot.AbstractSensor#getRobot()
	 */
	@Override
	public AbstractRobot getRobot()
	{
		return this.parent;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.electronauts.virtualrobot.AbstractSensor#readValue()
	 */
	@Override
	public Number readValue()
	{
		return (this.baseAngle - this.parent.getAngle()) % (2 * Math.PI);
	}

	/**
	 * Resets the base angle to the current orientation of the robot.
	 *
	 * @see setBaseAngle()
	 */
	public void resetBaseAngle()
	{
		this.setBaseAngle(this.getRobot().getAngle());
	}

	/**
	 * Sets the base angle to the specified value. This can be used to initialize the robot in a certain way or determine the orientation on the robot.
	 *
	 * @param baseAngle
	 *            the new base angle
	 */
	public void setBaseAngle(final double baseAngle)
	{
		this.baseAngle = baseAngle;
	}

}
