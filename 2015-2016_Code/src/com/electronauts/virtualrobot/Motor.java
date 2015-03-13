package com.electronauts.virtualrobot;

public class Motor
{
	private final MotorData	motorData;
	private double			x, y, power;

	public Motor(final MotorData motorData, final double x, final double y)
	{
		this.motorData = motorData;
		this.setX(x);
		this.setY(y);
	}

	public MotorData getMotorData()
	{
		return this.motorData;
	}

	public double getPower()
	{
		return this.power;
	}

	public double getX()
	{
		return this.x;
	}

	public double getY()
	{
		return this.y;
	}

	public void setPower(final double power)
	{
		this.power = power;
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
