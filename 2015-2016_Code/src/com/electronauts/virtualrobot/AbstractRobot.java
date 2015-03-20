package com.electronauts.virtualrobot;

import java.awt.geom.Path2D;

public abstract class AbstractRobot implements Runnable
{
	protected volatile Motor[]	motors;
	private long				startTime	= System.nanoTime();

	public abstract Path2D getBounds(int scale);

	public long getDeltaTimeNano()
	{
		return System.nanoTime() - this.getStartTime();
	}

	public double getDeltaTimeSeconds()
	{
		return (System.nanoTime() - this.getStartTime()) / 1000000000d;
	}

	public Motor getMotor(final MotorData motorData)
	{
		for (final Motor motor : this.motors)
			if (motor.getMotorData() == motorData) return motor;
		throw new IllegalArgumentException("Motor not found!");
	}

	public abstract double getMotorRPM(final MotorData motor);

	public long getStartTime()
	{
		return this.startTime;
	}

	public abstract void setMotorRPMs(final MotorData motor1, final MotorData motor2, final double rpm1, final double rpm2);

	public void setStartTime(final long startTime)
	{
		this.startTime = startTime;
	}
}
