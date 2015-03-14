package com.electronauts.virtualrobot;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;

import com.electronauts.mathutil.PolarPoint;

public class TankRobot extends AbstractRobot
{
	public TankRobot(final Motor motorLeft, final Motor motorRight)
	{
		this.motors = new Motor[2];
		this.motors[0] = motorRight;
		this.motors[1] = motorLeft;
	}

	public double getAngle()
	{
		return Math.atan2(this.getMotor(MotorData.MOTOR_LEFT).getY() - this.getMotor(MotorData.MOTOR_RIGHT).getY(), this.getMotor(MotorData.MOTOR_LEFT).getX()
				- this.getMotor(MotorData.MOTOR_RIGHT).getX());
	}

	@Override
	public Polygon getBounds(int scale)
	{
		final Polygon output = new Polygon();
		PolarPoint p1 = new PolarPoint(this.getWidth() / 2, this.getAngle() + Math.PI / 2);
		PolarPoint p2 = new PolarPoint(this.getWidth() / 2, this.getAngle() - Math.PI / 2);
		Motor motorL = this.getMotor(MotorData.MOTOR_LEFT);
		Motor motorR = this.getMotor(MotorData.MOTOR_RIGHT);

		output.addPoint((int) (scale * (p1.getX() + motorL.getX())), (int) (scale * (p1.getY() + motorL.getY())));
		output.addPoint((int) (scale * (p2.getX() + motorL.getX())), (int) (scale * (p2.getY() + motorL.getY())));

		output.addPoint((int) (scale * (p2.getX() + motorR.getX())), (int) (scale * (p2.getY() + motorR.getY())));
		output.addPoint((int) (scale * (p1.getX() + motorR.getX())), (int) (scale * (p1.getY() + motorR.getY())));

		return output;
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

	public void paint(final Graphics g, final int scale)
	{
		g.setColor(Color.BLACK);
		g.drawPolygon(this.getBounds(scale));
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
}
