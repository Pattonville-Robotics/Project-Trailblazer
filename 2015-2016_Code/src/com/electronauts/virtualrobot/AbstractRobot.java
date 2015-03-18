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

	public double getTime()
	{
		return this.time;
	}

	public abstract void setMotorRPMs(final MotorData motor1, final MotorData motor2, final double rpm1, final double rpm2);

	public void setTime(final double time)
	{
		this.time = time;
	}
}
