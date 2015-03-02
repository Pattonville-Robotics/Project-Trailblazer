package m_Star_Pathfinder;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

public class GridDraw extends JComponent
{
	private static final long	serialVersionUID	= 1L;
	private static Kryo			kryo				= new Kryo();
	private static Grid			grid;
	private static JFrame		frame;
	private static GridDraw		component;
	private static JMenuBar		menuBar;
	private static JMenu		help;
	private static JMenuItem	help1, help2, help3, help4, help5, help6, help7;
	private boolean				drawPaths			= false;

	public static void main(String[] args)
	{
		setupDisplay();
		setupIOMapping();
		frame.setVisible(true);
	}

	public GridDraw()
	{
		super();
		init();
	}

	public static void setupDisplay()
	{
		frame = new JFrame("window");
		frame.setBounds(20, 20, 700, 700);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		component = new GridDraw();
		component.setBackground(Color.WHITE);
		frame.getContentPane().add(component);

		help = new JMenu("Help");

		help1 = new JMenuItem("You can use the arrow keys to select a square to modify.");
		help2 = new JMenuItem("Use \"ENTER\" and \"CTRL + ENTER\" to cycle through possible square contents.");
		help3 = new JMenuItem("Pressing \"P\" will toggle calculation and drawing of paths. Be forewarned: this can take a long time on slower hardware!");
		help4 = new JMenuItem("To save and load Grids, use the \"S\" and \"L\" keys.");
		help5 = new JMenuItem("Press \"R\" to recalculate the paths without toggling them and \"CTRL + R\" to redraw the screen.");
		help6 = new JMenuItem("Press \"O\" to optimize the calculated paths.");
		help7 = new JMenuItem(
				"The green square is start, the blue square is finish, the purple squares are walls, and the numbers are the distance to the start.");

		help.add(help1);
		help.add(help2);
		help.add(help3);
		help.add(help4);
		help.add(help5);
		help.add(help6);
		help.add(help7);
		menuBar = new JMenuBar();
		menuBar.add(help);

		frame.setJMenuBar(menuBar);
	}

	public static void setupIOMapping()
	{
		MouseCycleListener mouseCycleListener = new MouseCycleListener(grid, component);

		component.addMouseListener(mouseCycleListener);

		UpAction upAction = new UpAction(grid, component);
		DownAction downAction = new DownAction(grid, component);
		LeftAction leftAction = new LeftAction(grid, component);
		RightAction rightAction = new RightAction(grid, component);
		RotateClockWiseAction rotateClockWiseAction = new RotateClockWiseAction(grid, component);
		RotateCounterClockWiseAction rotateCounterClockWiseAction = new RotateCounterClockWiseAction(grid, component);
		TogglePathsAction togglePathsAction = new TogglePathsAction(grid, component);
		CalculatePathsAction calculatePathsAction = new CalculatePathsAction(grid, component);
		SaveAction saveAction = new SaveAction(component);
		LoadAction loadAction = new LoadAction(component);
		RedrawAction redrawAction = new RedrawAction(component);
		OptimizeAction optimizeAction = new OptimizeAction(grid, component);

		component.getInputMap().put(KeyStroke.getKeyStroke("UP"), "upAction");
		component.getActionMap().put("upAction", upAction);

		component.getInputMap().put(KeyStroke.getKeyStroke("DOWN"), "downAction");
		component.getActionMap().put("downAction", downAction);

		component.getInputMap().put(KeyStroke.getKeyStroke("LEFT"), "leftAction");
		component.getActionMap().put("leftAction", leftAction);

		component.getInputMap().put(KeyStroke.getKeyStroke("RIGHT"), "rightAction");
		component.getActionMap().put("rightAction", rightAction);

		component.getInputMap().put(KeyStroke.getKeyStroke("ENTER"), "rotateClockWiseAction");
		component.getActionMap().put("rotateClockWiseAction", rotateClockWiseAction);

		component.getInputMap().put(KeyStroke.getKeyStroke("control ENTER"), "rotateCounterClockWiseAction");
		component.getActionMap().put("rotateCounterClockWiseAction", rotateCounterClockWiseAction);

		component.getInputMap().put(KeyStroke.getKeyStroke("P"), "togglePathsAction");
		component.getActionMap().put("togglePathsAction", togglePathsAction);

		component.getInputMap().put(KeyStroke.getKeyStroke("R"), "calculatePathsAction");
		component.getActionMap().put("calculatePathsAction", calculatePathsAction);

		component.getInputMap().put(KeyStroke.getKeyStroke("control R"), "redrawAction");
		component.getActionMap().put("redrawAction", redrawAction);

		component.getInputMap().put(KeyStroke.getKeyStroke("S"), "saveAction");
		component.getActionMap().put("saveAction", saveAction);

		component.getInputMap().put(KeyStroke.getKeyStroke("L"), "loadAction");
		component.getActionMap().put("loadAction", loadAction);

		component.getInputMap().put(KeyStroke.getKeyStroke("O"), "optimizeAction");
		component.getActionMap().put("optimizeAction", optimizeAction);

	}

	@Override
	public void paintComponent(Graphics g)
	{
		// System.out.println("Began drawing to the screen.");
		grid.paint(g);
		// System.out.println("Lowest next to start: " +
		// grid.getLowestAdjacentSquares(grid.getFinishPoint()));

		g.setColor(new Color(255, 0, 0));

		if (drawPaths)
		{
			for (int i = 0; i < grid.getPaths().size(); i++)
			{
				grid.paintPointSet(g, grid.getPaths().get(i).getArray());
				// System.out.println("Distance of path " + i + " is " +
				// grid.getPaths().get(i).getTotalDistance());
			}

			g.setColor(new Color(0, 255, 255));

			double lowestDistance = grid.getPaths().get(0).getTotalDistance();
			for (int i = 0; i < grid.getPaths().size(); i++)
			{
				if (grid.getPaths().get(i).getTotalDistance() == lowestDistance)
				{
					grid.paintPointSet(g, grid.getPaths().get(i).getArray());
				}
			}
		}
		// System.out.println("Finished drawing " + grid.getPaths().size() +
		// " paths to the screen.");
		// System.out.println("Each path is " +
		// grid.getPaths().get(0).getTotalDistance() + " units long.");
	}

	public void cacheGrid()
	{
		try
		{
			/*
			 * FileOutputStream fileOut = new FileOutputStream("grid.data");
			 * ObjectOutputStream objOut = new ObjectOutputStream(fileOut);
			 * 
			 * objOut.writeObject(grid); objOut.close();
			 */

			Output output = new Output(new FileOutputStream("grid.data"));
			kryo.writeObject(output, grid);
			output.close();

			System.out.println("Sucessfully cached data.");
		}
		catch (Exception e1)
		{
			e1.printStackTrace();
			System.out.println("Failed to cache data.");
		}
	}

	public void togglePaths()
	{
		drawPaths = !drawPaths;
	}

	public void disablePaths()
	{
		drawPaths = false;
	}

	public void enablePaths()
	{
		drawPaths = true;
	}

	public boolean getDrawPaths()
	{
		return drawPaths;
	}

	public void loadGrid() throws Exception
	{
		System.out.println("Began reading in data file.");
		/*
		 * FileInputStream fileIn = new FileInputStream("grid.data");
		 * ObjectInputStream objIn = new ObjectInputStream(fileIn);
		 * 
		 * Object obj = objIn.readObject(); objIn.close();
		 * 
		 * if (obj instanceof Grid) { grid = (Grid) obj; }
		 */

		Input input = new Input(new FileInputStream("grid.data"));
		grid = kryo.readObject(input, Grid.class);
		input.close();

		System.out.println("Finished reading in data file.");
	}

	public void init()
	{
		try
		{
			loadGrid();
		}
		catch (Exception e)
		{
			System.out.println("File not found. Began generating new data.");
			grid = new Grid(16, 16, 40, 40);
			grid.setSquareContents(new Point(1, 2), SquareType.START);
			grid.setSquareContents(new Point(5, 11), SquareType.FINISH);

			grid.setSquareContents(new Point(3, 2), SquareType.HAZARD);
			grid.setSquareContents(new Point(3, 3), SquareType.HAZARD);
			grid.setSquareContents(new Point(3, 4), SquareType.HAZARD);
			grid.setSquareContents(new Point(2, 5), SquareType.HAZARD);
			grid.setSquareContents(new Point(1, 5), SquareType.HAZARD);
			grid.setSquareContents(new Point(0, 5), SquareType.HAZARD);
			grid.setSquareContents(new Point(4, 6), SquareType.HAZARD);
			grid.setSquareContents(new Point(5, 7), SquareType.HAZARD);
			grid.setSquareContents(new Point(6, 8), SquareType.HAZARD);
			grid.setSquareContents(new Point(4, 5), SquareType.HAZARD);
			grid.setSquareContents(new Point(7, 9), SquareType.HAZARD);
			grid.setSquareContents(new Point(8, 10), SquareType.HAZARD);
			grid.setSquareContents(new Point(9, 11), SquareType.HAZARD);
			grid.setSquareContents(new Point(3, 1), SquareType.HAZARD);
			System.out.println(grid.getDistance(grid.getStartPoint()));

			PathfindAI.computeDistance(grid, grid.getStartPoint());
			PathfindAI.computePaths(grid);

			System.out.println("Finished generating new data. Begin caching.");
			cacheGrid();
		}
		System.out.println("Finished initialization.");
	}
}

class UpAction extends AbstractAction
{
	private static final long	serialVersionUID	= 1L;
	private Grid				grid;
	private GridDraw			component;

	public UpAction(Grid grid, GridDraw component)
	{
		super();
		this.grid = grid;
		this.component = component;
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		// System.out.println("UpAction performed!");
		grid.moveHighlightedSquare(0);
		PathfindAI.computeDistance(grid, grid.getStartPoint());
		component.repaint();
	}

}

class DownAction extends AbstractAction
{
	private static final long	serialVersionUID	= 1L;
	private Grid				grid;
	private GridDraw			component;

	public DownAction(Grid grid, GridDraw component)
	{
		super();
		this.grid = grid;
		this.component = component;
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		// System.out.println("DownAction performed!");
		grid.moveHighlightedSquare(1);
		PathfindAI.computeDistance(grid, grid.getStartPoint());
		component.repaint();
	}

}

class LeftAction extends AbstractAction
{
	private static final long	serialVersionUID	= 1L;
	private Grid				grid;
	private GridDraw			component;

	public LeftAction(Grid grid, GridDraw component)
	{
		super();
		this.grid = grid;
		this.component = component;
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		// System.out.println("LeftAction performed!");
		grid.moveHighlightedSquare(2);
		// PathfindAI.computeDistance(grid, grid.getStartPoint());
		component.repaint();
	}

}

class RightAction extends AbstractAction
{
	private static final long	serialVersionUID	= 1L;
	private Grid				grid;
	private GridDraw			component;

	public RightAction(Grid grid, GridDraw component)
	{
		super();
		this.grid = grid;
		this.component = component;
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		// System.out.println("RightAction performed!");
		grid.moveHighlightedSquare(3);
		// PathfindAI.computeDistance(grid, grid.getStartPoint());
		component.repaint();
	}

}

class RotateClockWiseAction extends AbstractAction
{
	private static final long	serialVersionUID	= 1L;
	private Grid				grid;
	private GridDraw			component;

	public RotateClockWiseAction(Grid grid, GridDraw component)
	{
		super();
		this.grid = grid;
		this.component = component;
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		// System.out.println("RotateAction performed!");
		grid.rotateHighlightedSquare(true);
		PathfindAI.computeDistance(grid, grid.getStartPoint());
		component.disablePaths();
		component.repaint();
	}
}

class RotateCounterClockWiseAction extends AbstractAction
{
	private static final long	serialVersionUID	= 1L;
	private Grid				grid;
	private GridDraw			component;

	public RotateCounterClockWiseAction(Grid grid, GridDraw component)
	{
		super();
		this.grid = grid;
		this.component = component;
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		// System.out.println("RotateAction performed!");
		grid.rotateHighlightedSquare(false);
		PathfindAI.computeDistance(grid, grid.getStartPoint());
		component.disablePaths();
		component.repaint();
	}
}

class TogglePathsAction extends AbstractAction
{
	private static final long	serialVersionUID	= 1L;
	private Grid				grid;
	private GridDraw			component;

	public TogglePathsAction(Grid grid, GridDraw component)
	{
		super();
		this.grid = grid;
		this.component = component;
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		// System.out.println("TogglePathsAction performed!");
		if (!component.getDrawPaths())
		{
			PathfindAI.computeDistance(grid, grid.getStartPoint());
			PathfindAI.computePaths(grid);
		}

		component.togglePaths();
		component.repaint();
	}
}

class CalculatePathsAction extends AbstractAction
{
	private static final long	serialVersionUID	= 1L;
	private Grid				grid;
	private GridDraw			component;

	public CalculatePathsAction(Grid grid, GridDraw component)
	{
		super();
		this.grid = grid;
		this.component = component;
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		// System.out.println("CalculatePathsAction performed!");
		PathfindAI.computeDistance(grid, grid.getStartPoint());
		PathfindAI.computePaths(grid);

		component.repaint();
	}
}

class SaveAction extends AbstractAction
{
	private static final long	serialVersionUID	= 1L;
	private GridDraw			component;

	public SaveAction(GridDraw component)
	{
		super();
		this.component = component;
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		// System.out.println("SaveAction performed!");
		component.cacheGrid();
	}
}

class LoadAction extends AbstractAction
{
	private static final long	serialVersionUID	= 1L;
	private GridDraw			component;

	public LoadAction(GridDraw component)
	{
		super();
		this.component = component;
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		// System.out.println("LoadAction performed!");
		try
		{
			component.loadGrid();
		}
		catch (Exception e1)
		{
			System.out.println("No Grid found!");
		}
	}
}

class RedrawAction extends AbstractAction
{
	private static final long	serialVersionUID	= 1L;
	private GridDraw			component;

	public RedrawAction(GridDraw component)
	{
		super();
		this.component = component;
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		// System.out.println("RedrawAction performed!");

		component.repaint();
	}
}

class OptimizeAction extends AbstractAction
{
	private static final long	serialVersionUID	= 1L;
	private GridDraw			component;
	private Grid				grid;

	public OptimizeAction(Grid grid, GridDraw component)
	{
		super();
		this.component = component;
		this.grid = grid;
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		// System.out.println("OptimizeAction performed!");

		PathfindAI.optimizePaths(grid);
		component.repaint();
	}
}

class MouseCycleListener implements MouseListener
{
	private Grid		grid;
	private GridDraw	component;

	public MouseCycleListener(Grid grid, GridDraw component)
	{
		super();
		this.grid = grid;
		this.component = component;
	}

	@Override
	public void mouseClicked(MouseEvent e)
	{
	}

	@Override
	public void mouseEntered(MouseEvent e)
	{
	}

	@Override
	public void mouseExited(MouseEvent e)
	{
	}

	@Override
	public void mousePressed(MouseEvent e)
	{
	}

	@Override
	public void mouseReleased(MouseEvent e)
	{
		grid.setHighlightedSquare(e.getX() / grid.getSquareWidth(), e.getY() / grid.getSquareHeight());
		if (e.getButton() == MouseEvent.BUTTON1)
			grid.rotateHighlightedSquare(true);
		else if (e.getButton() == MouseEvent.BUTTON3)
			grid.rotateHighlightedSquare(false);
		else
		{
		}
		PathfindAI.computeDistance(grid, grid.getStartPoint());
		component.disablePaths();
		component.repaint();
	}
}