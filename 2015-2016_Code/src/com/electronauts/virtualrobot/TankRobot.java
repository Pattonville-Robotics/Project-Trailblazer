package com.electronauts.virtualrobot;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Path2D;

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
	public Path2D getBounds(final int scale)
	{
		final Path2D.Double output = new Path2D.Double();
		final PolarPoint p1 = new PolarPoint(this.getWidth() / 2, this.getAngle() + Math.PI / 2);
		final PolarPoint p2 = new PolarPoint(this.getWidth() / 2, this.getAngle() - Math.PI / 2);
		final Motor motorL = this.getMotor(MotorData.MOTOR_LEFT);
		final Motor motorR = this.getMotor(MotorData.MOTOR_RIGHT);

		output.moveTo(scale * (p1.getX() + motorL.getX()), scale * (p1.getY() + motorL.getY()));
		output.lineTo(scale * (p2.getX() + motorL.getX()), scale * (p2.getY() + motorL.getY()));

		output.lineTo(scale * (p2.getX() + motorR.getX()), scale * (p2.getY() + motorR.getY()));
		output.lineTo(scale * (p1.getX() + motorR.getX()), scale * (p1.getY() + motorR.getY()));
		output.closePath();

		return output;
	}

	@Override
	public double getMotorRPM(final MotorData motor)
	{
		return this.getMotor(motor).getRPM();
	}

	public double getWidth()
	{
		return Math.sqrt(Math.pow(this.getMotor(MotorData.MOTOR_RIGHT).getX() - this.getMotor(MotorData.MOTOR_LEFT).getX(), 2)
				+ Math.pow(this.getMotor(MotorData.MOTOR_RIGHT).getY() - this.getMotor(MotorData.MOTOR_LEFT).getY(), 2));
	}

	public void paint(final Graphics2D g2d, final int scale)
	{
		g2d.setColor(Color.BLACK);
		g2d.draw(this.getBounds(scale));
	}

	@Override
	public void run()
	{
	}

	@Override
	public void setMotorRPM(final MotorData motor, final double rpm)
	{
		this.getMotor(motor).setRPM(rpm);
	}
}
