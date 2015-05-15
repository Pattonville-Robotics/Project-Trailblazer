package com.electronauts.virtualrobot;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Path2D.Double;

import org.apache.commons.math3.util.FastMath;

import com.electronauts.mathutil.PolarPoint;

public class HolonomicRobot extends AbstractRobot
{
	private double	xCenter, yCenter, width, length, orientation;

	public HolonomicRobot(final double x, final double y, final double width, final double length)
	{
		this.motors = new Motor[4];
		this.orientation = 0;
		this.xCenter = x;
		this.yCenter = y;
		this.width = width;
		this.length = length;
		this.motors[0] = new Motor(MotorData.MOTOR_FRONT_LEFT, new Wheel(1), x - width / 2, y + length / 2, this.orientation + FastMath.PI / 4);
		this.motors[1] = new Motor(MotorData.MOTOR_FRONT_RIGHT, new Wheel(1), x + width / 2, y + length / 2, this.orientation - FastMath.PI / 4);
		this.motors[2] = new Motor(MotorData.MOTOR_BACK_LEFT, new Wheel(1), x - width / 2, y - length / 2, this.orientation - 3 * FastMath.PI / 4);
		this.motors[3] = new Motor(MotorData.MOTOR_BACK_RIGHT, new Wheel(1), x + width / 2, y - length / 2, this.orientation + 3 * FastMath.PI / 4);

	}

	@Override
	public double getAngle()
	{
		// FIXME Auto-generated method stub
		return this.theta;
	}

	@Override
	public Double getBounds(final int scale)
	{
		// FIXME Auto-generated method stub
		return null;
	}

	/**
	 * @return the length
	 */
	public double getLength()
	{
		return this.length;
	}

	@Override
	public double getMotorRPM(final MotorData motor)
	{
		// FIXME Auto-generated method stub
		return 0;
	}

	/**
	 * @return the orientation
	 */
	public double getOrientation()
	{
		return this.orientation;
	}

	/**
	 * @return the width
	 */
	public double getWidth()
	{
		return this.width;
	}

	/**
	 * @return the xCenter
	 */
	public double getxCenter()
	{
		return this.xCenter;
	}

	/**
	 * @return the yCenter
	 */
	public double getyCenter()
	{
		return this.yCenter;
	}

	@Override
	public void paint(final Graphics2D g2d, final int scale)
	{
		final PolarPoint p1 = new PolarPoint(2, this.getMotor(MotorData.MOTOR_FRONT_LEFT).getOrientation());
		final PolarPoint p2 = new PolarPoint(2, this.getMotor(MotorData.MOTOR_FRONT_RIGHT).getOrientation());
		final PolarPoint p3 = new PolarPoint(2, this.getMotor(MotorData.MOTOR_BACK_LEFT).getOrientation());
		final PolarPoint p4 = new PolarPoint(2, this.getMotor(MotorData.MOTOR_BACK_RIGHT).getOrientation());

		g2d.setColor(Color.BLUE);

		g2d.drawLine((int) (scale * this.getMotor(MotorData.MOTOR_FRONT_LEFT).getX()), (int) (scale * this.getMotor(MotorData.MOTOR_FRONT_LEFT).getY()),
				(int) (scale * (this.getMotor(MotorData.MOTOR_FRONT_LEFT).getX() + p1.getX())), (int) (scale * (this.getMotor(MotorData.MOTOR_FRONT_LEFT)
						.getY() + p1.getY())));

		g2d.drawLine((int) (scale * this.getMotor(MotorData.MOTOR_FRONT_RIGHT).getX()), (int) (scale * this.getMotor(MotorData.MOTOR_FRONT_RIGHT).getY()),
				(int) (scale * (this.getMotor(MotorData.MOTOR_FRONT_RIGHT).getX() + p2.getX())), (int) (scale * (this.getMotor(MotorData.MOTOR_FRONT_RIGHT)
						.getY() + p2.getY())));

		g2d.drawLine((int) (scale * this.getMotor(MotorData.MOTOR_BACK_LEFT).getX()), (int) (scale * this.getMotor(MotorData.MOTOR_BACK_LEFT).getY()),
				(int) (scale * (this.getMotor(MotorData.MOTOR_BACK_LEFT).getX() + p3.getX())),
				(int) (scale * (this.getMotor(MotorData.MOTOR_BACK_LEFT).getY() + p3.getY())));

		g2d.drawLine((int) (scale * this.getMotor(MotorData.MOTOR_BACK_RIGHT).getX()), (int) (scale * this.getMotor(MotorData.MOTOR_BACK_RIGHT).getY()),
				(int) (scale * (this.getMotor(MotorData.MOTOR_BACK_RIGHT).getX() + p4.getX())), (int) (scale * (this.getMotor(MotorData.MOTOR_BACK_RIGHT)
						.getY() + p4.getY())));
	}

	@Override
	public void run()
	{
		// FIXME Auto-generated method stub

	}

	/**
	 * @param length
	 *            the length to set
	 */
	public void setLength(final double length)
	{
		this.length = length;
	}

	@Override
	public void setMotorRPMs(final MotorData motor1, final MotorData motor2, final double rpm1, final double rpm2)
	{
		// FIXME Auto-generated method stub

	}

	/**
	 * @param orientation
	 *            the orientation to set
	 */
	public void setOrientation(final double orientation)
	{
		this.orientation = orientation;
	}

	/**
	 * @param width
	 *            the width to set
	 */
	public void setWidth(final double width)
	{
		this.width = width;
	}

	/**
	 * @param xCenter
	 *            the xCenter to set
	 */
	public void setxCenter(final double xCenter)
	{
		this.xCenter = xCenter;
	}

	/**
	 * @param yCenter
	 *            the yCenter to set
	 */
	public void setyCenter(final double yCenter)
	{
		this.yCenter = yCenter;
	}

}
