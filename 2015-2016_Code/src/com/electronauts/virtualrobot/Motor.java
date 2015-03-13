package com.electronauts.virtualrobot;

public class Motor
{
	enum MotorData
	{
		MOTOR_RIGHT, MOTOR_LEFT
	}

	private final MotorData	motorData;
	private double			x, y, power;

	public Motor(MotorData motorData, double x, double y)
	{
		this.motorData = motorData;
		this.setX(x);
		this.setY(y);
	}

	public MotorData getMotorData()
	{
		return motorData;
	}

	public double getX()
	{
		return x;
	}

	public void setX(double x)
	{
		this.x = x;
	}

	public double getY()
	{
		return y;
	}

	public void setY(double y)
	{
		this.y = y;
	}

	public double getPower()
	{
		return power;
	}

	public void setPower(double power)
	{
		this.power = power;
	}
}
