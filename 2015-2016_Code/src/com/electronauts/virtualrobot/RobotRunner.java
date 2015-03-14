package com.electronauts.virtualrobot;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JComponent;
import javax.swing.JFrame;

public class RobotRunner
{
	public static void main(final String[] args)
	{
		final TankRobot robot = new TankRobot(new Motor(MotorData.MOTOR_RIGHT, 5, 1), new Motor(MotorData.MOTOR_LEFT, 2, 5));
		final JComponent component = new JComponent()
		{
			private static final long	serialVersionUID	= 1L;
			public double				i					= 0.0;

			@Override
			public void paintComponent(final Graphics g)
			{
				Graphics2D g2d = (Graphics2D) g;
				robot.paint(g2d, 50);
				robot.getMotor(MotorData.MOTOR_RIGHT).setY(Math.sin(this.i) * 2 + 5);
				robot.getMotor(MotorData.MOTOR_LEFT).setX(Math.cos(this.i) * 2 + 5);
				g2d.setColor(Color.RED);
				g2d.drawOval((int) (robot.getMotor(MotorData.MOTOR_LEFT).getX() * 50 - 5), (int) (robot.getMotor(MotorData.MOTOR_LEFT).getY() * 50 - 5), 10, 10);
				g2d.drawOval((int) (robot.getMotor(MotorData.MOTOR_RIGHT).getX() * 50 - 5), (int) (robot.getMotor(MotorData.MOTOR_RIGHT).getY() * 50 - 5), 10, 10);
				this.i += 0.0025;
				this.repaint();
			}
		};
		final JFrame frame = new JFrame("Robot testing in progress...");
		frame.setBounds(0, 0, 720, 720);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		component.setBackground(Color.WHITE);
		frame.setBackground(Color.GRAY);
		frame.getContentPane().add(component);
		frame.setVisible(true);
	}
}