package com.electronauts.virtualrobot;

import java.awt.Shape;

public abstract class AbstractRobot implements Runnable
{
	protected Motor[]	motors;

	public abstract Shape getBounds();

	public Motor getMotor(final MotorData motorData)
	{
		for (final Motor motor : this.motors)
			if (motor.getMotorData() == motorData) return motor;
		throw new IllegalArgumentException("Motor not found!");
	}

	public abstract double getMotorPower(final MotorData motor);

	public abstract void setMotorPower(final MotorData motor, final double power);

}
