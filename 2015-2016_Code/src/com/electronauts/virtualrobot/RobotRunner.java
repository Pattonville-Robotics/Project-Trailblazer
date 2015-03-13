package com.electronauts.virtualrobot;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JComponent;
import javax.swing.JFrame;

public class RobotRunner
{
	public static void main(String[] args)
	{
		final TankRobot robot = new TankRobot(new Motor(MotorData.MOTOR_RIGHT, 1, 1), new Motor(MotorData.MOTOR_LEFT, 2, 1));
		JComponent component = new JComponent()
		{
			private static final long	serialVersionUID	= 1L;

			@Override
			public void paintComponent(Graphics g)
			{
				robot.paint(g, 20);
			}
		};
		JFrame frame = new JFrame("Robot testing in progress...");
		frame.setBounds(0, 0, 720, 720);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		component.setBackground(Color.WHITE);
		frame.setBackground(Color.GRAY);
		frame.getContentPane().add(component);
		frame.setVisible(true);
	}
}