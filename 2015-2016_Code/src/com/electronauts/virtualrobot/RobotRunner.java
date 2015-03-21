package com.electronauts.virtualrobot;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.KeyStroke;

public class RobotRunner
{
	public static void init(final int updateSpeed)
	{
		final int m1x = 22;
		final int m1y = 10;
		final int m2x = 20;
		final int m2y = 10;

		final TankRobot robot = new TankRobot(new Motor(MotorData.MOTOR_RIGHT, new Wheel(1), m1x, m1y), new Motor(MotorData.MOTOR_LEFT, new Wheel(1), m2x, m2y));

		final JComponent component = new JComponent()
		{
			private static final long	serialVersionUID	= 1L;

			@Override
			public void paintComponent(final Graphics g)
			{
				final long startTime = System.nanoTime();
				final int scale = 20;
				final Graphics2D g2d = (Graphics2D) g;

				g2d.setColor(Color.LIGHT_GRAY);
				for (int x = 0; x < this.getWidth(); x += scale)
					g2d.drawLine(x, 0, x, this.getHeight());
				for (int y = 0; y < this.getHeight(); y += scale)
					g2d.drawLine(0, y, this.getWidth(), y);

				robot.paint(g2d, scale);

				g2d.setColor(Color.RED);
				g2d.drawLine(scale * m1x, scale * m1y, scale * m2x, scale * m2y);

				try
				{
					if (updateSpeed - (System.nanoTime() - startTime) / 1000000 > 0) Thread.sleep(updateSpeed - (System.nanoTime() - startTime) / 1000000);
				}
				catch (final InterruptedException e)
				{
					e.printStackTrace();
				}

				g2d.setColor(Color.RED);
				g2d.drawString(String.format("FPS: %06.2f Left motor power: %06.1f Right motor power %06.1f",
						1 / ((double) (System.nanoTime() - startTime) / 1000000000), robot.getMotorRPM(MotorData.MOTOR_LEFT),
						robot.getMotorRPM(MotorData.MOTOR_RIGHT)), 10, this.getHeight() - 10);

				this.repaint();
			}
		};
		final JFrame frame = new JFrame("Robot testing in progress...");
		frame.setBounds(0, 0, 720, 720);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		component.setBackground(Color.WHITE);
		frame.setBackground(Color.WHITE);
		frame.getContentPane().add(component);
		frame.setVisible(true);
		robot.setMotorRPMs(MotorData.MOTOR_LEFT, MotorData.MOTOR_RIGHT, 0, 0);

		component.getInputMap().put(KeyStroke.getKeyStroke("LEFT"), "leftAction");
		component.getActionMap().put("leftAction", new AbstractAction()
		{
			private static final long	serialVersionUID	= 1L;

			@Override
			public void actionPerformed(final ActionEvent e)
			{
				robot.setMotorRPMs(MotorData.MOTOR_LEFT, MotorData.MOTOR_RIGHT, robot.getMotorRPM(MotorData.MOTOR_LEFT) + 5,
						robot.getMotorRPM(MotorData.MOTOR_RIGHT) - 5);
				System.out.println(robot.getMotorRPM(MotorData.MOTOR_LEFT) + " " + robot.getMotorRPM(MotorData.MOTOR_RIGHT));
			}
		});

		component.getInputMap().put(KeyStroke.getKeyStroke("RIGHT"), "rightAction");
		component.getActionMap().put("rightAction", new AbstractAction()
		{
			private static final long	serialVersionUID	= 1L;

			@Override
			public void actionPerformed(final ActionEvent e)
			{
				robot.setMotorRPMs(MotorData.MOTOR_LEFT, MotorData.MOTOR_RIGHT, robot.getMotorRPM(MotorData.MOTOR_LEFT) - 5,
						robot.getMotorRPM(MotorData.MOTOR_RIGHT) + 5);
				System.out.println(robot.getMotorRPM(MotorData.MOTOR_LEFT) + " " + robot.getMotorRPM(MotorData.MOTOR_RIGHT));
			}
		});

		component.getInputMap().put(KeyStroke.getKeyStroke("UP"), "upAction");
		component.getActionMap().put("upAction", new AbstractAction()
		{
			private static final long	serialVersionUID	= 1L;

			@Override
			public void actionPerformed(final ActionEvent e)
			{
				robot.setMotorRPMs(MotorData.MOTOR_LEFT, MotorData.MOTOR_RIGHT, robot.getMotorRPM(MotorData.MOTOR_LEFT) + 5,
						robot.getMotorRPM(MotorData.MOTOR_RIGHT) + 5);
				System.out.println(robot.getMotorRPM(MotorData.MOTOR_LEFT) + " " + robot.getMotorRPM(MotorData.MOTOR_RIGHT));
			}
		});

		component.getInputMap().put(KeyStroke.getKeyStroke("DOWN"), "downAction");
		component.getActionMap().put("downAction", new AbstractAction()
		{
			private static final long	serialVersionUID	= 1L;

			@Override
			public void actionPerformed(final ActionEvent e)
			{
				robot.setMotorRPMs(MotorData.MOTOR_LEFT, MotorData.MOTOR_RIGHT, robot.getMotorRPM(MotorData.MOTOR_LEFT) - 5,
						robot.getMotorRPM(MotorData.MOTOR_RIGHT) - 5);
				System.out.println(robot.getMotorRPM(MotorData.MOTOR_LEFT) + " " + robot.getMotorRPM(MotorData.MOTOR_RIGHT));
			}
		});
	}

	public static void main(final String[] args)
	{
		RobotRunner.init(16);
	}
}