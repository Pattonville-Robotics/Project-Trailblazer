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

	public MotorData getMotorData()
	{
		return this.motorData;
	}

	public double getRadius()
	{
		return this.radius;
	}

	public double getRotX()
	{
		return this.rotX;
	}

	public double getRotY()
	{
		return this.rotY;
	}

	public double getRPM()
	{
		return this.rpm;
	}

	public double getTheta()
	{
		return this.theta;
	}

	public double getVelocity()
	{
		return this.getRPM() * this.getWheel().getCircumference();
	}

	public Wheel getWheel()
	{
		return this.wheel;
	}

	public double getX()
	{
		return this.x;
	}

	public double getY()
	{
		return this.y;
	}

	public void setRadius(final double radius)
	{
		this.radius = radius;
	}

	public void setRotX(final double rotX)
	{
		this.rotX = rotX;
	}

	public void setRotY(final double rotY)
	{
		this.rotY = rotY;
	}

	public void setRPM(final double rpm)
	{
		this.rpm = rpm;
	}

	public void setTheta(final double theta)
	{
		this.theta = theta;
	}

	public void setWheel(final Wheel wheel)
	{
		this.wheel = wheel;
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
