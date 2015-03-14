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
		final TankRobot robot = new TankRobot(new Motor(MotorData.MOTOR_RIGHT, new Wheel(1), 5, 3), new Motor(MotorData.MOTOR_LEFT, new Wheel(1), 6, 3));
		robot.setMotorRPM(MotorData.MOTOR_LEFT, 60);
		robot.setMotorRPM(MotorData.MOTOR_RIGHT, 30);

		final JComponent component = new JComponent()
		{
			private static final long	serialVersionUID	= 1L;
			public double				t					= 0.0;

			@Override
			public void paintComponent(final Graphics g)
			{
				int scale = 20;
				final Graphics2D g2d = (Graphics2D) g;
				robot.paint(g2d, scale);
				g2d.setColor(Color.RED);
				g2d.fillOval((int) (robot.getMotor(MotorData.MOTOR_LEFT).getX() * scale - 2), (int) (robot.getMotor(MotorData.MOTOR_LEFT).getY() * scale - 2),
						4, 4);
				g2d.fillOval((int) (robot.getMotor(MotorData.MOTOR_RIGHT).getX() * scale - 2),
						(int) (robot.getMotor(MotorData.MOTOR_RIGHT).getY() * scale - 2), 4, 4);
				robot.setTime(t);
				this.t += 0.0025;
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