package com.electronauts.virtualrobot;

public class Motor
{
	private final MotorData	motorData;
	private double			rotX, rotY, x, y, rpm, radius, theta;
	private Wheel			wheel;

	public Motor(final MotorData motorData, final Wheel wheel, final double x, final double y)
	{
		this.motorData = motorData;
		this.wheel = wheel;
		this.x = x;
		this.rotX = x;
		this.y = y;
		this.rotY = y;
		this.rpm = 0;
		this.theta = 0;
	}

	public double getRotX()
	{
		return rotX;
	}

	public void setRotX(double rotX)
	{
		this.rotX = rotX;
	}

	public double getRotY()
	{
		return rotY;
	}

	public void setRotY(double rotY)
	{
		this.rotY = rotY;
	}

	public double getTheta()
	{
		return theta;
	}

	public void setTheta(double theta)
	{
		this.theta = theta;
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
