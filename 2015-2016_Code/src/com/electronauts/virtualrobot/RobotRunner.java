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

		robot.setMotorRPMs(MotorData.MOTOR_LEFT, MotorData.MOTOR_RIGHT, 1, 2);

		final JComponent component = new JComponent()
		{
			private static final long	serialVersionUID	= 1L;

			// private final long startTime = System.nanoTime();

			@Override
			public void paintComponent(final Graphics g)
			{
				final int scale = 20;
				final Graphics2D g2d = (Graphics2D) g;

				g2d.setColor(Color.GRAY);
				for (int x = 0; x < this.getWidth(); x += scale)
					g2d.drawLine(x, 0, x, this.getHeight());
				for (int y = 0; y < this.getHeight(); y += scale)
					g2d.drawLine(0, y, this.getWidth(), y);

				robot.paint(g2d, scale);
				robot.setTime(robot.getTime() + 0.01);
				// robot.setTime((System.nanoTime() - this.startTime) /
				// 1000000000d);
				robot.updateTime();

				g2d.setColor(Color.RED);
				g2d.drawLine(14 * scale, 10 * scale, 12 * scale, 10 * scale);
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