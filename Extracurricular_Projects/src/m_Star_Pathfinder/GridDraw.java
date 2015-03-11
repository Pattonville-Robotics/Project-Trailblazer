package m_Star_Pathfinder;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
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

public class GridDraw extends JComponent
{
	private static final long	serialVersionUID	= 1L;

	public static void main(final String[] args)
	{
		final GridDraw gridDraw = new GridDraw();
		gridDraw.init();
		gridDraw.setupIOMapping();
		gridDraw.setupDisplay();
		gridDraw.frame.setVisible(true);
	}

	private boolean	drawBalencedNodes	= false;
	private boolean	drawDirection		= false;
	private boolean	drawNodes			= false;
	private boolean	drawPaths			= false;

	private JMenu	file, help;

	private JFrame	frame;

	private Grid	grid;
	private JMenuItem	help1, help2, help3, help4, help5, help6, help7, help8, help9, newFile, openFile, saveFile, saveAsFile;
	private final Kryo	kryo	= new Kryo();
	private JMenuBar	menuBar;
	File				saveDir	= null;

	public GridDraw()
	{
		super();
	}

	public void cacheGrid(final File file)
	{
		Output output = null;
		try
		{
			output = new Output(new FileOutputStream(file));
			this.kryo.writeObject(output, this.grid);
			System.out.println("Sucessfully cached data.");
		}
		catch (final Exception e1)
		{
			e1.printStackTrace();
			System.out.println("Failed to cache data.");
		}
		finally
		{
			output.close();
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

	public boolean getDrawDirection()
	{
		return this.drawDirection;
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
		this.saveDir = new File(System.getProperty("user.home") + "//grid.data");
		System.out.println(saveDir.getAbsolutePath());

		try
		{
			this.loadGrid(this.saveDir);
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

			PathfindAI.computeDistance(this.grid, this.grid.getStartPoint());
			// PathfindAI.computePaths(this.grid);
			// PathfindAI.optimizePaths(this.grid);

			System.out.println("Finished generating new data. Begin caching.");
			this.cacheGrid(this.saveDir);
		}
		System.out.println("Finished initialization.");
	}

	public void loadGrid(final File file)
	{
		System.out.println("Began reading in data file.");
		Input input = null;
		try
		{
			input = new Input(new FileInputStream(file));
			if (this.grid != null)
				this.replaceGrid(this.kryo.readObject(input, Grid.class));
			else
				this.grid = this.kryo.readObject(input, Grid.class);
		}
		catch (final FileNotFoundException e)
		{
			e.printStackTrace();
		}
		finally
		{
			input.close();
		}
		System.out.println("Finished reading in data file.");
	}

	public void replaceGrid(Grid grid)
	{
		this.grid.setSelf(grid);
	}

	@Override
	public void paintComponent(final Graphics g)
	{
		final long startTime = System.nanoTime();
		final Graphics2D g2d = (Graphics2D) g;
		g2d.setPaint(new GradientPaint(0, 0, new Color(25, 25, 112), this.getWidth(), this.getHeight(), new Color(176, 196, 222)));
		g2d.fillRect(0, 0, this.getWidth(), this.getHeight());

		// System.out.println("Began drawing to the screen.");
		this.grid.paint(g2d);
		// System.out.println("Lowest next to start: " +
		// grid.getLowestAdjacentSquares(grid.getFinishPoint()));

		if (this.getDrawNodes() || this.getDrawBalencedNodes()) this.grid.paintNodes(g2d);

		if (this.getDrawPaths())
		{
			g2d.setColor(new Color(255, 255, 0)); // Yellow
			this.grid.paintPointSet(g2d, this.grid.getPaths().get(0).getArray(), this.getDrawDirection(), 0, 0);
		}
		if (this.getDrawNodes())
		{
			g2d.setColor(new Color(255, 0, 255)); // Magenta
			this.grid.paintPointSet(g2d, this.grid.getPathNodeMap().getPointArray(), this.getDrawDirection(), 1, 1);
		}
		if (this.getDrawBalencedNodes())
		{
			g2d.setColor(new Color(0, 255, 255)); // Cyan
			this.grid.paintPointSet(g2d, this.grid.getPathNodeMaps().get(0).getPointArray(), this.getDrawDirection(), -1, -1);
		}

		g2d.setColor(new Color(255, 193, 63));
		try
		{
			g2d.drawString(String.format("FPS: %06.2f ; Yellow length: %07.3f; Cyan length: %07.3f; Magenta length: %07.3f",
					(1 / ((double) (System.nanoTime() - startTime) / 1000000000)), this.grid.getPaths().get(0).getTotalDistance(), this.grid.getPathNodeMaps()
							.get(0).getTotalDistance(), this.grid.getPathNodeMap().getTotalDistance()), 5, this.getHeight() - 5);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		this.repaint();
	}

	public void setDrawBalencedNodes(final boolean drawBalencedNodes)
	{
		this.drawBalencedNodes = drawBalencedNodes;
	}

	public void setDrawDirection(final boolean drawDirection)
	{
		this.drawDirection = drawDirection;
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
		this.frame = new JFrame("GridViewer V3.1415");
		this.frame.setBounds(0, 0, 720, 720);
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setBackground(Color.WHITE);
		this.frame.setBackground(Color.GRAY);
		this.frame.getContentPane().add(this);

		this.help = new JMenu("Help");
		this.help.setMnemonic('H');
		this.file = new JMenu("File");
		this.file.setMnemonic('F');

		this.newFile = new JMenuItem("New Blank Grid...");
		this.openFile = new JMenuItem("Open...");
		this.saveFile = new JMenuItem("Save...");
		this.saveAsFile = new JMenuItem("Save as...");
		this.newFile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
		this.openFile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
		this.saveFile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
		this.saveAsFile
				.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask() | InputEvent.SHIFT_DOWN_MASK));
		final KeyPressActions newFileAction = new KeyPressActions(this.grid, this, KeyPressActions.actionSource.NEW);
		final KeyPressActions saveFileAction = new KeyPressActions(this.grid, this, KeyPressActions.actionSource.CONTROL_S);
		final KeyPressActions saveAsFileAction = new KeyPressActions(this.grid, this, KeyPressActions.actionSource.SAVE_AS);
		final KeyPressActions openFileAction = new KeyPressActions(this.grid, this, KeyPressActions.actionSource.OPEN);
		this.newFile.addActionListener(newFileAction);
		this.openFile.addActionListener(openFileAction);
		this.saveFile.addActionListener(saveFileAction);
		this.saveAsFile.addActionListener(saveAsFileAction);
		this.file.add(this.newFile);
		this.file.add(this.openFile);
		this.file.add(this.saveFile);
		this.file.add(this.saveAsFile);

		this.help1 = new JMenuItem("You can use the arrow keys to select a square to modify.");
		this.help2 = new JMenuItem("Use \"ENTER\" and \"SHIFT + ENTER\" to cycle through possible square contents.");
		this.help3 = new JMenuItem("Pressing \"P\" will toggle calculation and drawing of paths. Be forewarned: this can take a long time on slower hardware!");
		this.help4 = new JMenuItem("To save and load Grids, use the \"S\" and \"L\" keys.");
		this.help5 = new JMenuItem("Press \"R\" to recalculate the paths without toggling them and \"CTRL + R\" to redraw the screen.");
		this.help6 = new JMenuItem("Press \"O\" to optimize the calculated paths.");
		this.help7 = new JMenuItem("Use the \"N\" button to toggle high-speed calculation.");
		this.help8 = new JMenuItem(
				"The green square is start, the blue square is finish, the purple squares are walls, and the numbers are the distance to the start.");
		this.help9 = new JMenuItem(
				"The yellow line is the maximum optimization, the cyan line is an intermediate level of optimization, and the magenta line is a low level of optimization.");

		this.help.add(this.help1);
		this.help.add(this.help2);
		this.help.add(this.help3);
		this.help.add(this.help4);
		this.help.add(this.help5);
		this.help.add(this.help6);
		this.help.add(this.help7);
		this.help.add(this.help8);
		this.help.add(this.help9);

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
		final KeyPressActions saveAction = new KeyPressActions(this.grid, this, KeyPressActions.actionSource.CONTROL_S);
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

		this.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_R, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()), "redrawAction");
		this.getActionMap().put("redrawAction", redrawAction);

		this.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_S, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()), "saveAction");
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
		B, C, CONTROL_R, DOWN, ENTER, L, LEFT, N, NEW, O, OPEN, P, R, RIGHT, CONTROL_S, SAVE_AS, SHIFT_ENTER, UP
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
		{
			this.grid.moveHighlightedSquare(0);
			this.component.repaint();
			break;
		}
		case DOWN:
		{
			this.grid.moveHighlightedSquare(1);
			this.component.repaint();
			break;
		}
		case LEFT:
		{
			this.grid.moveHighlightedSquare(2);
			this.component.repaint();
			break;
		}
		case RIGHT:
		{
			this.grid.moveHighlightedSquare(3);
			this.component.repaint();
			break;
		}
		case ENTER:
		{
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
		}
		case SHIFT_ENTER:
		{
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
		}
		case P:
		{
			if (!this.component.getDrawPaths())
			{
				PathfindAI.computeDistance(this.grid, this.grid.getStartPoint());
				PathfindAI.computePaths(this.grid);
				PathfindAI.optimizePaths(this.grid);
			}
			this.component.setDrawPaths(!this.component.getDrawPaths());
			this.component.repaint();
			break;
		}
		case B:
		{
			if (!this.component.getDrawBalencedNodes())
			{
				PathfindAI.identifyNodes(this.grid);
				PathfindAI.connectAllNodes(this.grid);
			}
			this.component.setDrawBalencedNodes(!this.component.getDrawBalencedNodes());
			this.component.repaint();
			break;
		}
		case N:
		{
			if (!this.component.getDrawNodes())
			{
				PathfindAI.identifyNodes(this.grid);
				PathfindAI.connectNodes(this.grid);
			}
			this.component.setDrawNodes(!this.component.getDrawNodes());
			this.component.repaint();
			break;
		}
		case R:
		{
			PathfindAI.computeDistance(this.grid, this.grid.getStartPoint());
			PathfindAI.computePaths(this.grid);
			this.component.repaint();
			break;
		}
		case CONTROL_R:
		{
			this.component.repaint();
			break;
		}
		case CONTROL_S:
		{
			this.component.cacheGrid(this.component.saveDir);
			break;
		}
		case L:
		{
			try
			{
				this.component.loadGrid(this.component.saveDir);
			}
			catch (final Exception e1)
			{
				System.out.println("No Grid found!");
			}
			break;
		}
		case O:
		{
			PathfindAI.optimizePaths(this.grid);
			this.component.repaint();
			break;
		}
		case C:
		{
			this.component.setDrawDirection(!this.component.getDrawDirection());
			this.component.repaint();
			break;
		}
		case SAVE_AS:
		{
			final JFileChooser save = new JFileChooser(this.component.saveDir);

			final int returnVal = save.showSaveDialog(this.component);
			if (returnVal == JFileChooser.APPROVE_OPTION)
			{
				this.component.saveDir = save.getSelectedFile();
				this.component.cacheGrid(this.component.saveDir);
			}
			break;
		}
		case OPEN:
		{
			final JFileChooser open = new JFileChooser(this.component.saveDir);

			final int returnVal = open.showOpenDialog(this.component);
			if (returnVal == JFileChooser.APPROVE_OPTION)
			{
				this.component.saveDir = open.getSelectedFile();
				this.component.loadGrid(this.component.saveDir);
			}
			break;
		}
		case NEW:
		{
			final NewFileWindow window = new NewFileWindow(component);
			window.getFrame().setVisible(true);
			break;
		}
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