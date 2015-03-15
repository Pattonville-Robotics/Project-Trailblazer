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
		final TankRobot robot = new TankRobot(new Motor(MotorData.MOTOR_RIGHT, new Wheel(1), 14, 10), new Motor(MotorData.MOTOR_LEFT, new Wheel(1), 12, 10));

		final JComponent component = new JComponent()
		{
			private static final long	serialVersionUID	= 1L;

			@Override
			public void paintComponent(final Graphics g)
			{
				int scale = 20;
				final Graphics2D g2d = (Graphics2D) g;
				robot.paint(g2d, scale);
				robot.setTime(2, g2d);
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
		robot.setMotorRPM(MotorData.MOTOR_LEFT, 15, (Graphics2D) component.getGraphics());
		robot.setMotorRPM(MotorData.MOTOR_RIGHT, 20, (Graphics2D) component.getGraphics());
	}
}