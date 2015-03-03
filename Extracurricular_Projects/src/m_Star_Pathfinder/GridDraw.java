package m_Star_Pathfinder;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
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

	public static void main(final String[] args)
	{
		final GridDraw gridDraw = new GridDraw();
		gridDraw.init();
		gridDraw.setupDisplay();
		gridDraw.setupIOMapping();
		gridDraw.frame.setVisible(true);
	}

	private boolean	drawPaths	= false;
	private JFrame	frame;
	private Grid	grid;
	private JMenu	help;
	private JMenuItem	help1, help2, help3, help4, help5, help6, help7;
	private final Kryo	kryo	= new Kryo();

	private JMenuBar	menuBar;

	public GridDraw()
	{
		super();
	}

	public void cacheGrid()
	{
		try
		{
			final Output output = new Output(new FileOutputStream("grid.data"));
			this.kryo.writeObject(output, this.grid);
			output.close();

			System.out.println("Sucessfully cached data.");
		}
		catch (final Exception e1)
		{
			e1.printStackTrace();
			System.out.println("Failed to cache data.");
		}
	}

	public void disablePaths()
	{
		this.drawPaths = false;
	}

	public void enablePaths()
	{
		this.drawPaths = true;
	}

	public boolean getDrawPaths()
	{
		return this.drawPaths;
	}

	public void init()
	{
		try
		{
			this.loadGrid();
		}
		catch (final Exception e)
		{
			e.printStackTrace();
			System.out.println("File not found. Began generating new data.");
			this.grid = new Grid(16, 16, 40, 40);
			this.grid.setSquareContents(new Point(1, 2), SquareType.START);
			this.grid.setSquareContents(new Point(5, 11), SquareType.FINISH);

			this.grid.setSquareContents(new Point(3, 2), SquareType.HAZARD);
			this.grid.setSquareContents(new Point(3, 3), SquareType.HAZARD);
			this.grid.setSquareContents(new Point(3, 4), SquareType.HAZARD);
			this.grid.setSquareContents(new Point(2, 5), SquareType.HAZARD);
			this.grid.setSquareContents(new Point(1, 5), SquareType.HAZARD);
			this.grid.setSquareContents(new Point(0, 5), SquareType.HAZARD);
			this.grid.setSquareContents(new Point(4, 6), SquareType.HAZARD);
			this.grid.setSquareContents(new Point(5, 7), SquareType.HAZARD);
			this.grid.setSquareContents(new Point(6, 8), SquareType.HAZARD);
			this.grid.setSquareContents(new Point(4, 5), SquareType.HAZARD);
			this.grid.setSquareContents(new Point(7, 9), SquareType.HAZARD);
			this.grid.setSquareContents(new Point(8, 10), SquareType.HAZARD);
			this.grid.setSquareContents(new Point(9, 11), SquareType.HAZARD);
			this.grid.setSquareContents(new Point(3, 1), SquareType.HAZARD);

			PathfindAI.computeDistance(this.grid, this.grid.getStartPoint());
			// PathfindAI.computePaths(this.grid);
			// PathfindAI.optimizePaths(this.grid);

			System.out.println("Finished generating new data. Begin caching.");
			this.cacheGrid();
		}
		System.out.println("Finished initialization.");
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

		final Input input = new Input(new FileInputStream("grid.data"));
		this.grid = this.kryo.readObject(input, Grid.class);
		input.close();

		System.out.println("Finished reading in data file.");
	}

	@Override
	public void paintComponent(final Graphics g)
	{
		Graphics2D g2d = (Graphics2D) g;
		// System.out.println("Began drawing to the screen.");
		this.grid.paint(g);
		// System.out.println("Lowest next to start: " +
		// grid.getLowestAdjacentSquares(grid.getFinishPoint()));

		g.setColor(new Color(255, 0, 0));

		if (this.drawPaths)
		{
			for (int i = 0; i < this.grid.getPaths().size(); i++)
				this.grid.paintPointSet(g, this.grid.getPaths().get(i).getArray());
			// System.out.println("Distance of path " + i + " is " +
			// grid.getPaths().get(i).getTotalDistance());

			g.setColor(new Color(0, 255, 255));

			final double lowestDistance = this.grid.getPaths().get(0).getTotalDistance();
			for (int i = 0; i < this.grid.getPaths().size(); i++)
			{
				if (this.grid.getPaths().get(i).getTotalDistance() == lowestDistance) this.grid.paintPointSet(g, this.grid.getPaths().get(i).getArray());
				System.out.println(i + " out of " + this.grid.getPaths().size() + " paths have been drawn.");
			}
		}
		PathfindAI.identifyNodes(this.grid, g2d);
		// System.out.println("Finished drawing " + grid.getPaths().size() +
		// " paths to the screen.");
		// System.out.println("Each path is " +
		// grid.getPaths().get(0).getTotalDistance() + " units long.");
	}

	public void setupDisplay()
	{
		this.frame = new JFrame("window");
		this.frame.setBounds(0, 0, 690, 690);
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setBackground(Color.WHITE);
		this.frame.getContentPane().add(this);

		this.help = new JMenu("Help");

		this.help1 = new JMenuItem("You can use the arrow keys to select a square to modify.");
		this.help2 = new JMenuItem("Use \"ENTER\" and \"CTRL + ENTER\" to cycle through possible square contents.");
		this.help3 = new JMenuItem("Pressing \"P\" will toggle calculation and drawing of paths. Be forewarned: this can take a long time on slower hardware!");
		this.help4 = new JMenuItem("To save and load Grids, use the \"S\" and \"L\" keys.");
		this.help5 = new JMenuItem("Press \"R\" to recalculate the paths without toggling them and \"CTRL + R\" to redraw the screen.");
		this.help6 = new JMenuItem("Press \"O\" to optimize the calculated paths.");
		this.help7 = new JMenuItem(
				"The green square is start, the blue square is finish, the purple squares are walls, and the numbers are the distance to the start.");

		this.help.add(this.help1);
		this.help.add(this.help2);
		this.help.add(this.help3);
		this.help.add(this.help4);
		this.help.add(this.help5);
		this.help.add(this.help6);
		this.help.add(this.help7);
		this.menuBar = new JMenuBar();
		this.menuBar.add(this.help);

		this.frame.setJMenuBar(this.menuBar);
	}

	public void setupIOMapping()
	{
		final MouseCycleListener mouseCycleListener = new MouseCycleListener(this.grid, this);

		this.addMouseListener(mouseCycleListener);

		final UpAction upAction = new UpAction(this.grid, this);
		final DownAction downAction = new DownAction(this.grid, this);
		final LeftAction leftAction = new LeftAction(this.grid, this);
		final RightAction rightAction = new RightAction(this.grid, this);
		final RotateClockWiseAction rotateClockWiseAction = new RotateClockWiseAction(this.grid, this);
		final RotateCounterClockWiseAction rotateCounterClockWiseAction = new RotateCounterClockWiseAction(this.grid, this);
		final TogglePathsAction togglePathsAction = new TogglePathsAction(this.grid, this);
		final CalculatePathsAction calculatePathsAction = new CalculatePathsAction(this.grid, this);
		final SaveAction saveAction = new SaveAction(this);
		final LoadAction loadAction = new LoadAction(this);
		final RedrawAction redrawAction = new RedrawAction(this);
		final OptimizeAction optimizeAction = new OptimizeAction(this.grid, this);

		this.getInputMap().put(KeyStroke.getKeyStroke("UP"), "upAction");
		this.getActionMap().put("upAction", upAction);

		this.getInputMap().put(KeyStroke.getKeyStroke("DOWN"), "downAction");
		this.getActionMap().put("downAction", downAction);

		this.getInputMap().put(KeyStroke.getKeyStroke("LEFT"), "leftAction");
		this.getActionMap().put("leftAction", leftAction);

		this.getInputMap().put(KeyStroke.getKeyStroke("RIGHT"), "rightAction");
		this.getActionMap().put("rightAction", rightAction);

		this.getInputMap().put(KeyStroke.getKeyStroke("ENTER"), "rotateClockWiseAction");
		this.getActionMap().put("rotateClockWiseAction", rotateClockWiseAction);

		this.getInputMap().put(KeyStroke.getKeyStroke("control ENTER"), "rotateCounterClockWiseAction");
		this.getActionMap().put("rotateCounterClockWiseAction", rotateCounterClockWiseAction);

		this.getInputMap().put(KeyStroke.getKeyStroke("P"), "togglePathsAction");
		this.getActionMap().put("togglePathsAction", togglePathsAction);

		this.getInputMap().put(KeyStroke.getKeyStroke("R"), "calculatePathsAction");
		this.getActionMap().put("calculatePathsAction", calculatePathsAction);

		this.getInputMap().put(KeyStroke.getKeyStroke("control R"), "redrawAction");
		this.getActionMap().put("redrawAction", redrawAction);

		this.getInputMap().put(KeyStroke.getKeyStroke("S"), "saveAction");
		this.getActionMap().put("saveAction", saveAction);

		this.getInputMap().put(KeyStroke.getKeyStroke("L"), "loadAction");
		this.getActionMap().put("loadAction", loadAction);

		this.getInputMap().put(KeyStroke.getKeyStroke("O"), "optimizeAction");
		this.getActionMap().put("optimizeAction", optimizeAction);

	}

	public void togglePaths()
	{
		this.drawPaths = !this.drawPaths;
	}
}

class CalculatePathsAction extends AbstractAction
{
	private static final long	serialVersionUID	= 1L;
	private final GridDraw		component;
	private final Grid			grid;

	public CalculatePathsAction(final Grid grid, final GridDraw component)
	{
		super();
		this.grid = grid;
		this.component = component;
	}

	@Override
	public void actionPerformed(final ActionEvent e)
	{
		// System.out.println("CalculatePathsAction performed!");
		PathfindAI.computeDistance(this.grid, this.grid.getStartPoint());
		PathfindAI.computePaths(this.grid);

		this.component.repaint();
	}
}

class DownAction extends AbstractAction
{
	private static final long	serialVersionUID	= 1L;
	private final GridDraw		component;
	private final Grid			grid;

	public DownAction(final Grid grid, final GridDraw component)
	{
		super();
		this.grid = grid;
		this.component = component;
	}

	@Override
	public void actionPerformed(final ActionEvent e)
	{
		// System.out.println("DownAction performed!");
		this.grid.moveHighlightedSquare(1);
		PathfindAI.computeDistance(this.grid, this.grid.getStartPoint());
		this.component.repaint();
	}

}

class LeftAction extends AbstractAction
{
	private static final long	serialVersionUID	= 1L;
	private final GridDraw		component;
	private final Grid			grid;

	public LeftAction(final Grid grid, final GridDraw component)
	{
		super();
		this.grid = grid;
		this.component = component;
	}

	@Override
	public void actionPerformed(final ActionEvent e)
	{
		// System.out.println("LeftAction performed!");
		this.grid.moveHighlightedSquare(2);
		// PathfindAI.computeDistance(grid, grid.getStartPoint());
		this.component.repaint();
	}

}

class LoadAction extends AbstractAction
{
	private static final long	serialVersionUID	= 1L;
	private final GridDraw		component;

	public LoadAction(final GridDraw component)
	{
		super();
		this.component = component;
	}

	@Override
	public void actionPerformed(final ActionEvent e)
	{
		// System.out.println("LoadAction performed!");
		try
		{
			this.component.loadGrid();
		}
		catch (final Exception e1)
		{
			System.out.println("No Grid found!");
		}
	}
}

class MouseCycleListener implements MouseListener
{
	private final GridDraw	component;
	private final Grid		grid;

	public MouseCycleListener(final Grid grid, final GridDraw component)
	{
		super();
		this.grid = grid;
		this.component = component;
	}

	@Override
	public void mouseClicked(final MouseEvent e)
	{
	}

	@Override
	public void mouseEntered(final MouseEvent e)
	{
	}

	@Override
	public void mouseExited(final MouseEvent e)
	{
	}

	@Override
	public void mousePressed(final MouseEvent e)
	{
	}

	@Override
	public void mouseReleased(final MouseEvent e)
	{
		this.grid.setHighlightedSquare(e.getX() / this.grid.getSquareWidth(), e.getY() / this.grid.getSquareHeight());
		if (this.grid.testSetHighlightedSquare(e.getX() / this.grid.getSquareWidth(), e.getY() / this.grid.getSquareHeight()))
		{
			if (e.getButton() == MouseEvent.BUTTON1)
				this.grid.rotateHighlightedSquare(true);
			else if (e.getButton() == MouseEvent.BUTTON3)
				this.grid.rotateHighlightedSquare(false);
			else
			{
			}
			PathfindAI.computeDistance(this.grid, this.grid.getStartPoint());
			this.component.disablePaths();
			this.component.repaint();
		}
	}
}

class OptimizeAction extends AbstractAction
{
	private static final long	serialVersionUID	= 1L;
	private final GridDraw		component;
	private final Grid			grid;

	public OptimizeAction(final Grid grid, final GridDraw component)
	{
		super();
		this.component = component;
		this.grid = grid;
	}

	@Override
	public void actionPerformed(final ActionEvent e)
	{
		// System.out.println("OptimizeAction performed!");

		PathfindAI.optimizePaths(this.grid);
		this.component.repaint();
	}
}

class RedrawAction extends AbstractAction
{
	private static final long	serialVersionUID	= 1L;
	private final GridDraw		component;

	public RedrawAction(final GridDraw component)
	{
		super();
		this.component = component;
	}

	@Override
	public void actionPerformed(final ActionEvent e)
	{
		// System.out.println("RedrawAction performed!");

		this.component.repaint();
	}
}

class RightAction extends AbstractAction
{
	private static final long	serialVersionUID	= 1L;
	private final GridDraw		component;
	private final Grid			grid;

	public RightAction(final Grid grid, final GridDraw component)
	{
		super();
		this.grid = grid;
		this.component = component;
	}

	@Override
	public void actionPerformed(final ActionEvent e)
	{
		// System.out.println("RightAction performed!");
		this.grid.moveHighlightedSquare(3);
		// PathfindAI.computeDistance(grid, grid.getStartPoint());
		this.component.repaint();
	}

}

class RotateClockWiseAction extends AbstractAction
{
	private static final long	serialVersionUID	= 1L;
	private final GridDraw		component;
	private final Grid			grid;

	public RotateClockWiseAction(final Grid grid, final GridDraw component)
	{
		super();
		this.grid = grid;
		this.component = component;
	}

	@Override
	public void actionPerformed(final ActionEvent e)
	{
		// System.out.println("RotateAction performed!");
		this.grid.rotateHighlightedSquare(true);
		PathfindAI.computeDistance(this.grid, this.grid.getStartPoint());
		this.component.disablePaths();
		this.component.repaint();
	}
}

class RotateCounterClockWiseAction extends AbstractAction
{
	private static final long	serialVersionUID	= 1L;
	private final GridDraw		component;
	private final Grid			grid;

	public RotateCounterClockWiseAction(final Grid grid, final GridDraw component)
	{
		super();
		this.grid = grid;
		this.component = component;
	}

	@Override
	public void actionPerformed(final ActionEvent e)
	{
		// System.out.println("RotateAction performed!");
		this.grid.rotateHighlightedSquare(false);
		PathfindAI.computeDistance(this.grid, this.grid.getStartPoint());
		this.component.disablePaths();
		this.component.repaint();
	}
}

class SaveAction extends AbstractAction
{
	private static final long	serialVersionUID	= 1L;
	private final GridDraw		component;

	public SaveAction(final GridDraw component)
	{
		super();
		this.component = component;
	}

	@Override
	public void actionPerformed(final ActionEvent e)
	{
		// System.out.println("SaveAction performed!");
		this.component.cacheGrid();
	}
}

class TogglePathsAction extends AbstractAction
{
	private static final long	serialVersionUID	= 1L;
	private final GridDraw		component;
	private final Grid			grid;

	public TogglePathsAction(final Grid grid, final GridDraw component)
	{
		super();
		this.grid = grid;
		this.component = component;
	}

	@Override
	public void actionPerformed(final ActionEvent e)
	{
		// System.out.println("TogglePathsAction performed!");
		if (!this.component.getDrawPaths())
		{
			PathfindAI.computeDistance(this.grid, this.grid.getStartPoint());
			PathfindAI.computePaths(this.grid);
		}

		this.component.togglePaths();
		this.component.repaint();
	}
}

class UpAction extends AbstractAction
{
	private static final long	serialVersionUID	= 1L;
	private final GridDraw		component;
	private final Grid			grid;

	public UpAction(final Grid grid, final GridDraw component)
	{
		super();
		this.grid = grid;
		this.component = component;
	}

	@Override
	public void actionPerformed(final ActionEvent e)
	{
		// System.out.println("UpAction performed!");
		this.grid.moveHighlightedSquare(0);
		PathfindAI.computeDistance(this.grid, this.grid.getStartPoint());
		this.component.repaint();
	}

}