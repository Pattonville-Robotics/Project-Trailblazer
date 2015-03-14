package com.electronauts.virtualrobot;

public class Motor
{
	private final MotorData	motorData;
	private double			x, y, rpm;

	public Motor(final MotorData motorData, final double x, final double y)
	{
		this.motorData = motorData;
		this.x = x;
		this.y = y;
		this.rpm = 0;
	}

	public MotorData getMotorData()
	{
		return this.motorData;
	}

	public double getRPM()
	{
		return this.rpm;
	}

	public double getX()
	{
		return this.x;
	}

	public double getY()
	{
		return this.y;
	}

	public void setRPM(final double rpm)
	{
		this.rpm = rpm;
	}

	public void setX(final double x)
	{
		this.x = x;
	}

	public void setY(final double y)
	{
		this.y = y;
	}
}
