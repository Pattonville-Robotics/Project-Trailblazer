package com.electronauts.virtualrobot;

public abstract class AbstractRobot implements Runnable
{
	private Motor	motorLeft, motorRight;

	public double getMotorPower(final Motor.MotorData motor)
	{
		switch (motor)
		{
		case MOTOR_RIGHT:
		{
			return this.motorRight.getPower();
		}
		case MOTOR_LEFT:
		{
			return this.motorLeft.getPower();
		}
		default:
		{
			throw new IllegalArgumentException("Invalid motor specified!");
		}
		}
	}

	public void setMotorPower(final Motor.MotorData motor, final double power)
	{
		switch (motor)
		{
		case MOTOR_RIGHT:
		{
			this.motorRight.setPower(power);
			break;
		}
		case MOTOR_LEFT:
		{
			this.motorLeft.setPower(power);
			break;
		}
		default:
		{
			throw new IllegalArgumentException("Invalid motor specified!");
		}
		}
	}
}
