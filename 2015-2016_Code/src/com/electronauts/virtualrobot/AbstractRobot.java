package com.electronauts.virtualrobot;

import java.awt.geom.Path2D;

public abstract class AbstractRobot implements Runnable
{
	protected Motor[]	motors;
	private double		time;

	public abstract Path2D getBounds(int scale);

	public Motor getMotor(final MotorData motorData)
	{
		for (final Motor motor : this.motors)
			if (motor.getMotorData() == motorData) return motor;
		throw new IllegalArgumentException("Motor not found!");
	}

	public abstract double getMotorRPM(final MotorData motor);

	public abstract void setMotorRPM(final MotorData motor, final double rpm);

	public double getTime()
	{
		return time;
	}

	public void setTime(double time)
	{
		this.time = time;
	}
}
