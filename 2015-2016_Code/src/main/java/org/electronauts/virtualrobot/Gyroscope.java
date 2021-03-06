package org.electronauts.virtualrobot;

import org.apache.commons.math3.util.FastMath;

// TODO: Auto-generated Javadoc

/**
 * The Gyroscope class is attached to a robot of type {@link AbstractRobot} and provides a way to query the robot's direction.
 */
public class Gyroscope extends AbstractSensor {

    /**
     * The angle that is measured from.
     */
    private double baseAngle;

    /**
     * Instantiates a new gyroscope from a parent robot.
     *
     * @param parent the robot that the sensor is attached to.
     */
    public Gyroscope(final AbstractRobot parent) {
        super(parent, SensorType.GYROSCOPE);
        this.resetBaseAngle();
    }

    /**
     * Resets the base angle to the current orientation of the robot.
     *
     * @see this.setBaseAngle
     */
    public void resetBaseAngle() {
        this.setBaseAngle(this.getRobot().getAngle());
    }

    /*
     * (non-Javadoc)
     *
     * @see AbstractSensor#getRobot()
     */
    @Override
    public AbstractRobot getRobot() {
        return this.parent;
    }

    /*
     * (non-Javadoc)
     *
     * @see AbstractSensor#readValue()
     */
    @Override
    public Number readValue() {
        return (this.baseAngle - this.parent.getAngle()) % (2 * FastMath.PI);
    }

    /**
     * Gets the base angle. This is the angle from which the robot's position is measured from.
     *
     * @return the base angle
     */
    public double getBaseAngle() {
        return this.baseAngle;
    }

    /**
     * Sets the base angle to the specified value. This can be used to initialize the robot in a certain way or determine the orientation on the robot.
     *
     * @param baseAngle the new base angle
     */
    public void setBaseAngle(final double baseAngle) {
        this.baseAngle = baseAngle;
    }

}
