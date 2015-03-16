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

		robot.setMotorRPM(MotorData.MOTOR_LEFT, 99);
		robot.setMotorRPM(MotorData.MOTOR_RIGHT, 100);

		final JComponent component = new JComponent()
		{
			private static final long	serialVersionUID	= 1L;

			@Override
			public void paintComponent(final Graphics g)
			{
				int scale = 20;
				final Graphics2D g2d = (Graphics2D) g;
				robot.paint(g2d, scale);
				robot.setTime(robot.getTime() + 0.01);
				System.out.println(robot.getTime());

				g2d.setColor(Color.BLACK);
				for (int x = 0; x < this.getWidth(); x += scale)
				{
					g2d.drawLine(x, 0, x, this.getHeight());
				}
				for (int y = 0; y < this.getHeight(); y += scale)
				{
					g2d.drawLine(0, y, this.getWidth(), y);
				}
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