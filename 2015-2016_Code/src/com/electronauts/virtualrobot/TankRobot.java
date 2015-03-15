package com.electronauts.virtualrobot;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Path2D;

import com.electronauts.mathutil.PolarPoint;

public class TankRobot extends AbstractRobot
{
	private double	radius;

	public double getRadius()
	{
		return radius;
	}

	public void setRadius(double radius)
	{
		this.radius = radius;
	}

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
	public Path2D.Double getBounds(final int scale)
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
		final PolarPoint p1 = new PolarPoint(this.getWidth() / 2, this.getAngle() + Math.PI / 2);
		final Motor motorL = this.getMotor(MotorData.MOTOR_LEFT);
		final Motor motorR = this.getMotor(MotorData.MOTOR_RIGHT);
		Path2D.Double arrow = new Path2D.Double();
		arrow.moveTo(scale * motorL.getX(), scale * motorL.getY());
		arrow.lineTo(scale * motorR.getX(), scale * motorR.getY());
		arrow.lineTo((scale * ((motorL.getX() + motorR.getX()) / 2 + p1.getX())), (scale * ((motorL.getY() + motorR.getY()) / 2 + p1.getY())));
		arrow.closePath();
		Path2D.Double bounds = this.getBounds(scale);

		g2d.setColor(Color.BLACK);
		g2d.draw(bounds);
		g2d.setColor(Color.GRAY);
		g2d.fill(arrow);
		g2d.setColor(Color.RED);
		g2d.fillOval((int) (motorL.getX() * scale - 2), (int) (motorL.getY() * scale - 2), 4, 4);
		g2d.fillOval((int) (motorR.getX() * scale - 2), (int) (motorR.getY() * scale - 2), 4, 4);
	}

	@Override
	public void run()
	{
	}

	@Override
	public void setMotorRPM(final MotorData motor, final double rpm, Graphics2D g2d)
	{
		this.getMotor(motor).setRPM(rpm);
		Motor motorL = this.getMotor(MotorData.MOTOR_LEFT);
		Motor motorR = this.getMotor(MotorData.MOTOR_RIGHT);
		double motorLUnitDistance = motorL.getRPM() * 2 * Math.PI * motorL.getWheel().getRadius();
		System.out.println("MotorL unit = " + motorLUnitDistance);
		double motorRUnitDistance = motorR.getRPM() * 2 * Math.PI * motorR.getWheel().getRadius();
		System.out.println("MotorR unit = " + motorRUnitDistance);
		PolarPoint p1 = new PolarPoint(motorLUnitDistance / ((motorLUnitDistance - motorRUnitDistance) / this.getWidth()), this.getAngle() + Math.PI);
		System.out.println("Width = " + this.getWidth());

		motorL.setRotX(motorL.getX() + p1.getX());
		motorL.setRotY(motorL.getY() + p1.getY());

		motorL.setRadius(p1.getRadius());
		motorL.setTheta(p1.getTheta());
		motorR.setRotX(motorL.getX() + p1.getX());
		motorR.setRotY(motorL.getY() + p1.getY());
		motorR.setRadius(p1.getRadius() - this.getWidth());
		motorR.setTheta(p1.getTheta());
		this.setTime(0, g2d);
		System.out.println("L radius = " + motorL.getRadius());
		System.out.println("R radius = " + motorR.getRadius());
	}

	@Override
	public void setTime(double time, Graphics2D g2d)
	// Time is measured in seconds
	{
		// TODO Compute turn at time. See whiteboard for details
		super.setTime(time, g2d);

		Motor motorL = this.getMotor(MotorData.MOTOR_LEFT);
		Motor motorR = this.getMotor(MotorData.MOTOR_RIGHT);

		g2d.setColor(Color.BLUE);
		g2d.drawLine((int) (motorL.getRotX() * 20), (int) (motorL.getRotY() * 20), (int) (motorL.getX() * 20), (int) (motorL.getY() * 20));
		g2d.setColor(Color.RED);
		g2d.drawLine((int) (motorR.getRotX() * 20), (int) (motorR.getRotY() * 20), (int) (motorR.getX() * 20), (int) (motorR.getY() * 20));

		double motorLDistance = time * (motorL.getRPM() / 60) * 2 * Math.PI * motorL.getWheel().getRadius();
		double motorRDistance = time * (motorR.getRPM() / 60) * 2 * Math.PI * motorR.getWheel().getRadius();

		double radiansTurned = ((motorLDistance) / (2 * Math.PI * motorL.getRadius())) * (2 * Math.PI);
		System.out.println(radiansTurned);
	}
}
