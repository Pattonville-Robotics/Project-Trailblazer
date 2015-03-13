package com.electronauts.virtualrobot;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Shape;

public class TankRobot extends AbstractRobot
{
	public TankRobot(final Motor motorLeft, final Motor motorRight)
	{
		this.motors = new Motor[2];
		this.motors[0] = motorRight;
		this.motors[1] = motorLeft;
	}

	@Override
	public Shape getBounds()
	{
		return null;
	}

	@Override
	public double getMotorPower(final MotorData motor)
	{
		return this.getMotor(motor).getPower();
	}

	public double getWidth()
	{
		return Math.sqrt(Math.pow(this.getMotor(MotorData.MOTOR_RIGHT).getX() - this.getMotor(MotorData.MOTOR_LEFT).getX(), 2)
				+ Math.pow(this.getMotor(MotorData.MOTOR_RIGHT).getY() - this.getMotor(MotorData.MOTOR_LEFT).getY(), 2));
	}

	@Override
	public void run()
	{
	}

	@Override
	public void setMotorPower(final MotorData motor, final double power)
	{
		this.getMotor(motor).setPower(power);
	}

	public void paint(Graphics g, int scale)
	{
		g.setColor(Color.BLACK);
		g.drawLine((int) (this.getMotor(MotorData.MOTOR_RIGHT).getX() * scale), (int) (this.getMotor(MotorData.MOTOR_RIGHT).getY() * scale),
				(int) (this.getMotor(MotorData.MOTOR_LEFT).getX() * scale), (int) (this.getMotor(MotorData.MOTOR_LEFT).getY() * scale));
	}
}
