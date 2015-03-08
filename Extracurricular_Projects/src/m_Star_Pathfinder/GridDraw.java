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

//TODO Reduce source code size; only include needed things
//TODO Make GridMaker application or functionality to make custom sized grids
//TODO Design easier interface for code and movement
//TODO Merge actionListeners to reduce inner classes

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

	private boolean	drawNodes			= false;
	private boolean	drawBalencedNodes	= false;
	private boolean	drawPaths			= false;
	private boolean	drawDirection		= false;

	public boolean getDrawDirection()
	{
		return drawDirection;
	}

	public void setDrawDirection(boolean drawDirection)
	{
		this.drawDirection = drawDirection;
	}

	private JFrame	frame;
	private Grid	grid;
	private JMenu	file, help;
	private JMenuItem	help1, help2, help3, help4, help5, help6, help7, help8;
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

	public void disableNodes()
	{
		this.drawNodes = false;
	}

	public void disablePaths()
	{
		this.drawPaths = false;
	}

	public void enableNodes()
	{
		this.drawNodes = true;
	}

	public void enablePaths()
	{
		this.drawPaths = true;
	}

	public boolean getDrawBalencedNodes()
	{
		return this.drawBalencedNodes;
	}

	public boolean getDrawNodes()
	{
		return this.drawNodes;
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

		final Input input = new Input(new FileInputStream("grid.data"));
		this.grid = this.kryo.readObject(input, Grid.class);
		input.close();

		System.out.println("Finished reading in data file.");
	}

	@Override
	public void paintComponent(final Graphics g)
	{
		final long startTime = System.nanoTime();
		final Graphics2D g2d = (Graphics2D) g;
		// System.out.println("Began drawing to the screen.");
		this.grid.paint(g2d);
		// System.out.println("Lowest next to start: " +
		// grid.getLowestAdjacentSquares(grid.getFinishPoint()));

		if (this.getDrawPaths())
		{
			g2d.setColor(new Color(255, 255, 0));
			this.grid.paintPointSet(g2d, this.grid.getPaths().get(0).getArray(), this.getDrawDirection(), 0, 0);
		}
		if (this.getDrawNodes())
		{
			if (!this.getDrawBalencedNodes()) this.grid.paintNodes(g2d);
			g2d.setColor(new Color(255, 0, 255));
			this.grid.paintPointSet(g2d, this.grid.getPathNodeMap().getPointArray(), this.getDrawDirection(), 1, 1);
		}
		if (this.getDrawBalencedNodes())
		{
			this.grid.paintNodes(g2d);
			g2d.setColor(new Color(0, 255, 255));
			this.grid.paintPointSet(g2d, this.grid.getPathNodeMaps().get(0).getPointArray(), this.getDrawDirection(), -1, -1);
		}

		g2d.setColor(new Color(255, 63, 63));
		g2d.drawString(String.format("FPS: %06.2f", 1 / ((double) (System.nanoTime() - startTime) / 1000000000)), 5, this.getHeight() - 5);
		this.repaint();
	}

	public void setDrawBalencedNodes(final boolean drawBalencedNodes)
	{
		this.drawBalencedNodes = drawBalencedNodes;
	}

	public void setDrawNodes(final boolean drawNodes)
	{
		this.drawNodes = drawNodes;
	}

	public void setDrawPaths(final boolean drawPaths)
	{
		this.drawPaths = drawPaths;
	}

	public void setupDisplay()
	{
		this.frame = new JFrame("window");
		this.frame.setBounds(0, 0, 720, 720);
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setBackground(Color.WHITE);
		this.frame.getContentPane().add(this);

		this.help = new JMenu("Help");
		this.file = new JMenu("File");

		this.help1 = new JMenuItem("You can use the arrow keys to select a square to modify.");
		this.help2 = new JMenuItem("Use \"ENTER\" and \"SHIFT + ENTER\" to cycle through possible square contents.");
		this.help3 = new JMenuItem("Pressing \"P\" will toggle calculation and drawing of paths. Be forewarned: this can take a long time on slower hardware!");
		this.help4 = new JMenuItem("To save and load Grids, use the \"S\" and \"L\" keys.");
		this.help5 = new JMenuItem("Press \"R\" to recalculate the paths without toggling them and \"CTRL + R\" to redraw the screen.");
		this.help6 = new JMenuItem("Press \"O\" to optimize the calculated paths.");
		this.help7 = new JMenuItem("Use the \"N\" button to toggle high-speed calculation.");
		this.help8 = new JMenuItem(
				"The green square is start, the blue square is finish, the purple squares are walls, and the numbers are the distance to the start.");
		this.help.add(this.help1);
		this.help.add(this.help2);
		this.help.add(this.help3);
		this.help.add(this.help4);
		this.help.add(this.help5);
		this.help.add(this.help6);
		this.help.add(this.help7);
		this.help.add(this.help8);

		this.menuBar = new JMenuBar();
		this.menuBar.add(this.file);
		this.menuBar.add(this.help);

		this.frame.setJMenuBar(this.menuBar);
	}

	public void setupIOMapping()
	{
		final MouseCycleListener mouseCycleListener = new MouseCycleListener(this.grid, this);

		this.addMouseListener(mouseCycleListener);

		final KeyPressActions upAction = new KeyPressActions(this.grid, this, KeyPressActions.actionSource.UP);
		final KeyPressActions downAction = new KeyPressActions(this.grid, this, KeyPressActions.actionSource.DOWN);
		final KeyPressActions leftAction = new KeyPressActions(this.grid, this, KeyPressActions.actionSource.LEFT);
		final KeyPressActions rightAction = new KeyPressActions(this.grid, this, KeyPressActions.actionSource.RIGHT);
		final KeyPressActions rotateClockWiseAction = new KeyPressActions(this.grid, this, KeyPressActions.actionSource.ENTER);
		final KeyPressActions rotateCounterClockWiseAction = new KeyPressActions(this.grid, this, KeyPressActions.actionSource.SHIFT_ENTER);
		final KeyPressActions togglePathsAction = new KeyPressActions(this.grid, this, KeyPressActions.actionSource.P);
		final KeyPressActions toggleNodesAction = new KeyPressActions(this.grid, this, KeyPressActions.actionSource.N);
		final KeyPressActions toggleBalencedNodesAction = new KeyPressActions(this.grid, this, KeyPressActions.actionSource.B);
		final KeyPressActions calculatePathsAction = new KeyPressActions(this.grid, this, KeyPressActions.actionSource.R);
		final KeyPressActions saveAction = new KeyPressActions(this.grid, this, KeyPressActions.actionSource.S);
		final KeyPressActions loadAction = new KeyPressActions(this.grid, this, KeyPressActions.actionSource.L);
		final KeyPressActions redrawAction = new KeyPressActions(this.grid, this, KeyPressActions.actionSource.CONTROL_R);
		final KeyPressActions optimizeAction = new KeyPressActions(this.grid, this, KeyPressActions.actionSource.O);
		final KeyPressActions toggleDrawDirectionAction = new KeyPressActions(this.grid, this, KeyPressActions.actionSource.C);

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

		this.getInputMap().put(KeyStroke.getKeyStroke("shift ENTER"), "rotateCounterClockWiseAction");
		this.getActionMap().put("rotateCounterClockWiseAction", rotateCounterClockWiseAction);

		this.getInputMap().put(KeyStroke.getKeyStroke("P"), "togglePathsAction");
		this.getActionMap().put("togglePathsAction", togglePathsAction);

		this.getInputMap().put(KeyStroke.getKeyStroke("N"), "toggleNodesAction");
		this.getActionMap().put("toggleNodesAction", toggleNodesAction);

		this.getInputMap().put(KeyStroke.getKeyStroke("B"), "toggleBalencedNodesAction");
		this.getActionMap().put("toggleBalencedNodesAction", toggleBalencedNodesAction);

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

		this.getInputMap().put(KeyStroke.getKeyStroke("C"), "toggleDrawDirectionAction");
		this.getActionMap().put("toggleDrawDirectionAction", toggleDrawDirectionAction);

	}
}

class KeyPressActions extends AbstractAction
{
	public static enum actionSource
	{
		UP, DOWN, LEFT, RIGHT, ENTER, SHIFT_ENTER, P, N, B, R, CONTROL_R, S, L, O, C
	}

	private static final long	serialVersionUID	= 1L;
	private final GridDraw		component;
	private final Grid			grid;

	private final actionSource	source;

	public KeyPressActions(final Grid grid, final GridDraw component, final actionSource source)
	{
		super();
		this.grid = grid;
		this.component = component;
		this.source = source;
	}

	@Override
	public void actionPerformed(final ActionEvent e)
	{
		switch (this.source)
		{
		case UP:
			// System.out.println("UpAction performed!");
			this.grid.moveHighlightedSquare(0);
			// PathfindAI.computeDistance(this.grid, this.grid.getStartPoint());
			this.component.repaint();
			break;
		case DOWN:
			// System.out.println("DownAction performed!");
			this.grid.moveHighlightedSquare(1);
			// PathfindAI.computeDistance(this.grid, this.grid.getStartPoint());
			this.component.repaint();
			break;
		case LEFT:
			// System.out.println("LeftAction performed!");
			this.grid.moveHighlightedSquare(2);
			// PathfindAI.computeDistance(grid, grid.getStartPoint());
			this.component.repaint();
			break;
		case RIGHT:
			// System.out.println("RightAction performed!");
			this.grid.moveHighlightedSquare(3);
			// PathfindAI.computeDistance(grid, grid.getStartPoint());
			this.component.repaint();
			break;
		case ENTER:
			// System.out.println("RotateAction performed!");
			this.grid.rotateHighlightedSquare(true);
			PathfindAI.computeDistance(this.grid, this.grid.getStartPoint());

			if (this.component.getDrawPaths())
			{
				PathfindAI.computePaths(this.grid);
				PathfindAI.optimizePaths(this.grid);
			}
			if (this.component.getDrawNodes())
			{
				PathfindAI.identifyNodes(this.grid);
				PathfindAI.connectNodes(this.grid);
			}
			if (this.component.getDrawBalencedNodes())
			{
				PathfindAI.identifyNodes(this.grid);
				PathfindAI.connectAllNodes(this.grid);
			}
			this.component.repaint();
			break;
		case SHIFT_ENTER:
			// System.out.println("RotateAction performed!");
			this.grid.rotateHighlightedSquare(false);
			PathfindAI.computeDistance(this.grid, this.grid.getStartPoint());

			if (this.component.getDrawPaths())
			{
				PathfindAI.computePaths(this.grid);
				PathfindAI.optimizePaths(this.grid);
			}
			if (this.component.getDrawNodes())
			{
				PathfindAI.identifyNodes(this.grid);
				PathfindAI.connectNodes(this.grid);
			}
			if (this.component.getDrawBalencedNodes())
			{
				PathfindAI.identifyNodes(this.grid);
				PathfindAI.connectAllNodes(this.grid);
			}
			this.component.repaint();
			break;
		case P:
			// System.out.println("TogglePathsAction performed!");
			if (!this.component.getDrawPaths())
			{
				PathfindAI.computeDistance(this.grid, this.grid.getStartPoint());
				PathfindAI.computePaths(this.grid);
				PathfindAI.optimizePaths(this.grid);
			}
			this.component.setDrawPaths(!this.component.getDrawPaths());
			this.component.repaint();
			break;
		case B:
			// System.out.println("ToggleNodesAction performed!");
			if (!this.component.getDrawBalencedNodes())
			{
				PathfindAI.identifyNodes(this.grid);
				PathfindAI.connectAllNodes(this.grid);
			}
			this.component.setDrawBalencedNodes(!this.component.getDrawBalencedNodes());
			this.component.repaint();
			break;
		case N:
			// System.out.println("ToggleBalencedNodesAction performed!");
			if (!this.component.getDrawNodes())
			{
				PathfindAI.identifyNodes(this.grid);
				PathfindAI.connectNodes(this.grid);
			}
			this.component.setDrawNodes(!this.component.getDrawNodes());
			this.component.repaint();
			break;
		case R:
			// System.out.println("CalculatePathsAction performed!");
			PathfindAI.computeDistance(this.grid, this.grid.getStartPoint());
			PathfindAI.computePaths(this.grid);
			this.component.repaint();
			break;
		case CONTROL_R:
			// System.out.println("RedrawAction performed!");
			this.component.repaint();
			break;
		case S:
			// System.out.println("SaveAction performed!");
			this.component.cacheGrid();
			break;
		case L:
			// System.out.println("LoadAction performed!");
			try
			{
				this.component.loadGrid();
			}
			catch (final Exception e1)
			{
				System.out.println("No Grid found!");
			}
			break;
		case O:
			// System.out.println("OptimizeAction performed!");
			PathfindAI.optimizePaths(this.grid);
			this.component.repaint();
			break;
		case C:
			this.component.setDrawDirection(!this.component.getDrawDirection());
			this.component.repaint();
			break;
		default:
			throw new IllegalArgumentException("Bad source received!");
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
			if (this.component.getDrawPaths())
			{
				PathfindAI.computePaths(this.grid);
				PathfindAI.optimizePaths(this.grid);
			}
			if (this.component.getDrawNodes())
			{
				PathfindAI.identifyNodes(this.grid);
				PathfindAI.connectNodes(this.grid);
			}
			if (this.component.getDrawBalencedNodes())
			{
				PathfindAI.identifyNodes(this.grid);
				PathfindAI.connectAllNodes(this.grid);
			}
			this.component.repaint();
		}
	}
}