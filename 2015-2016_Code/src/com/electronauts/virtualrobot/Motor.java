package com.electronauts.virtualrobot;

public class Motor
{
	private final MotorData	motorData;
	private double			startX, startY, x, y, rpm, radius;
	private Wheel			wheel;

	public Motor(final MotorData motorData, final Wheel wheel, final double x, final double y)
	{
		this.motorData = motorData;
		this.wheel = wheel;
		this.x = x;
		this.startX = x;
		this.y = y;
		this.startY = y;
		this.rpm = 0;
	}

	public double getStartX()
	{
		return startX;
	}

	public void setStartX(double startX)
	{
		this.startX = startX;
	}

	public double getStartY()
	{
		return startY;
	}

	public void setStartY(double startY)
	{
		this.startY = startY;
	}

	public double getRpm()
	{
		return rpm;
	}

	public void setRpm(double rpm)
	{
		this.rpm = rpm;
	}

	public Wheel getWheel()
	{
		return wheel;
	}

	public void setWheel(Wheel wheel)
	{
		this.wheel = wheel;
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

	public double getRadius()
	{
		return radius;
	}

	public void setRadius(double radius)
	{
		this.radius = radius;
	}
}
