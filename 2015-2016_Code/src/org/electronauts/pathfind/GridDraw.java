package org.electronauts.pathfind;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
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
import javax.swing.WindowConstants;

// TODO: Auto-generated Javadoc
// TODO Reduce source code size; only include needed things
// TODO Design easier interface for code and movement

/**
 * The Class GridDraw.
 */
public class GridDraw extends JComponent {

	/**
	 * The Constant serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * The kryo.
	 */
	private final Kryo kryo = new Kryo();
	/**
	 * The save dir.
	 */
	File saveDir = null;
	/**
	 * The draw balenced nodes.
	 */
	private boolean drawBalencedNodes = false;
	/**
	 * The draw direction.
	 */
	private boolean drawDirection = false;
	/**
	 * The draw nodes.
	 */
	private boolean drawNodes = false;
	/**
	 * The draw paths.
	 */
	private boolean drawPaths = false;
	/**
	 * The frame.
	 */
	private JFrame frame;
	/**
	 * The grid.
	 */
	private Grid grid;

	/**
	 * Instantiates a new grid draw.
	 */
	public GridDraw() {
		super();
	}

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(final String[] args) {
		final GridDraw gridDraw = new GridDraw();
		gridDraw.init();
		gridDraw.setupIOMapping();
		gridDraw.setupDisplay();
		gridDraw.frame.setVisible(true);
	}

	/**
	 * Inits the.
	 */
	public void init() {
		this.saveDir = new File(System.getProperty("user.home") + "//grid.data");
		System.out.println(this.saveDir.getAbsolutePath());

		try {
			this.loadGrid(this.saveDir);
		} catch (final Exception e) {
			e.printStackTrace();
			System.out.println("File not found. Began generating new data.");
			this.setGrid(new Grid(16, 16, 40, 40));
			this.getGrid().setSquareContents(new Point(1, 2), SquareType.START);
			this.getGrid().setSquareContents(new Point(5, 11), SquareType.FINISH);

			this.getGrid().setSquareContents(new Point(3, 2), SquareType.HAZARD);
			this.getGrid().setSquareContents(new Point(3, 3), SquareType.HAZARD);
			this.getGrid().setSquareContents(new Point(3, 4), SquareType.HAZARD);
			this.getGrid().setSquareContents(new Point(2, 5), SquareType.HAZARD);
			this.getGrid().setSquareContents(new Point(1, 5), SquareType.HAZARD);
			this.getGrid().setSquareContents(new Point(0, 5), SquareType.HAZARD);
			this.getGrid().setSquareContents(new Point(4, 6), SquareType.HAZARD);
			this.getGrid().setSquareContents(new Point(5, 7), SquareType.HAZARD);
			this.getGrid().setSquareContents(new Point(6, 8), SquareType.HAZARD);

			PathfindAI.computeDistance(this.getGrid(), this.getGrid().getStartPoint());

			System.out.println("Finished generating new data. Begin caching.");
			this.cacheGrid(this.saveDir);
		}
		System.out.println("Finished initialization.");
	}

	/**
	 * Setup io mapping.
	 */
	public void setupIOMapping() {
		final MouseCycleListener mouseCycleListener = new MouseCycleListener(this.getGrid(), this);

		this.addMouseListener(mouseCycleListener);

		final KeyPressActions upAction = new KeyPressActions(this.getGrid(), this, KeyPressActions.actionSource.UP);
		final KeyPressActions downAction = new KeyPressActions(this.getGrid(), this, KeyPressActions.actionSource.DOWN);
		final KeyPressActions leftAction = new KeyPressActions(this.getGrid(), this, KeyPressActions.actionSource.LEFT);
		final KeyPressActions rightAction = new KeyPressActions(this.getGrid(), this, KeyPressActions.actionSource.RIGHT);
		final KeyPressActions rotateClockWiseAction = new KeyPressActions(this.getGrid(), this, KeyPressActions.actionSource.ENTER);
		final KeyPressActions rotateCounterClockWiseAction = new KeyPressActions(this.getGrid(), this, KeyPressActions.actionSource.SHIFT_ENTER);
		final KeyPressActions togglePathsAction = new KeyPressActions(this.getGrid(), this, KeyPressActions.actionSource.P);
		final KeyPressActions toggleNodesAction = new KeyPressActions(this.getGrid(), this, KeyPressActions.actionSource.N);
		final KeyPressActions toggleBalencedNodesAction = new KeyPressActions(this.getGrid(), this, KeyPressActions.actionSource.B);
		final KeyPressActions calculatePathsAction = new KeyPressActions(this.getGrid(), this, KeyPressActions.actionSource.R);
		final KeyPressActions saveAction = new KeyPressActions(this.getGrid(), this, KeyPressActions.actionSource.CONTROL_S);
		final KeyPressActions loadAction = new KeyPressActions(this.getGrid(), this, KeyPressActions.actionSource.L);
		final KeyPressActions redrawAction = new KeyPressActions(this.getGrid(), this, KeyPressActions.actionSource.CONTROL_R);
		final KeyPressActions optimizeAction = new KeyPressActions(this.getGrid(), this, KeyPressActions.actionSource.O);
		final KeyPressActions toggleDrawDirectionAction = new KeyPressActions(this.getGrid(), this, KeyPressActions.actionSource.C);

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

	/**
	 * Setup display.
	 */
	public void setupDisplay() {
		this.frame = new JFrame("GridViewer V3.14159");
		this.frame.setBounds(0, 0, 720, 720);
		this.frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		this.setBackground(Color.WHITE);
		this.frame.setBackground(Color.GRAY);
		this.frame.getContentPane().add(this);

		JMenu help = new JMenu("Help");
		help.setMnemonic('H');
		/*
	  The help.
	 */
		JMenu file = new JMenu("File");
		file.setMnemonic('F');

		JMenuItem newFile = new JMenuItem("New Blank Grid...");
		JMenuItem openFile = new JMenuItem("Open...");
		JMenuItem saveFile = new JMenuItem("Save...");
		JMenuItem saveAsFile = new JMenuItem("Save as...");
		newFile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
		openFile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
		saveFile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
		saveAsFile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask() | InputEvent.SHIFT_DOWN_MASK));
		final KeyPressActions newFileAction = new KeyPressActions(this.getGrid(), this, KeyPressActions.actionSource.NEW);
		final KeyPressActions saveFileAction = new KeyPressActions(this.getGrid(), this, KeyPressActions.actionSource.CONTROL_S);
		final KeyPressActions saveAsFileAction = new KeyPressActions(this.getGrid(), this, KeyPressActions.actionSource.SAVE_AS);
		final KeyPressActions openFileAction = new KeyPressActions(this.getGrid(), this, KeyPressActions.actionSource.OPEN);
		newFile.addActionListener(newFileAction);
		openFile.addActionListener(openFileAction);
		saveFile.addActionListener(saveFileAction);
		saveAsFile.addActionListener(saveAsFileAction);
		file.add(newFile);
		file.add(openFile);
		file.add(saveFile);
		file.add(saveAsFile);

		/*
	  The save as file.
	 */
		JMenuItem help1 = new JMenuItem("You can use the arrow keys to select a square to modify.");
		JMenuItem help2 = new JMenuItem("Use \"ENTER\" and \"SHIFT + ENTER\" to cycle through possible square contents.");
		JMenuItem help3 = new JMenuItem("Pressing \"P\" will toggle calculation and drawing of paths. Be forewarned: this can take a long time on slower hardware!");
		JMenuItem help4 = new JMenuItem("To save and load Grids, use the \"S\" and \"L\" keys.");
		JMenuItem help5 = new JMenuItem("Press \"R\" to recalculate the paths without toggling them and \"CTRL + R\" to redraw the screen.");
		JMenuItem help6 = new JMenuItem("Press \"O\" to optimize the calculated paths.");
		JMenuItem help7 = new JMenuItem("Use the \"N\" button to toggle high-speed calculation.");
		JMenuItem help8 = new JMenuItem("The green square is start, the blue square is finish, the purple squares are walls, and the numbers are the distance to the start.");
		JMenuItem help9 = new JMenuItem("The yellow line is the maximum optimization, the cyan line is an intermediate level of optimization, and the magenta line is a low level of optimization.");

		help.add(help1);
		help.add(help2);
		help.add(help3);
		help.add(help4);
		help.add(help5);
		help.add(help6);
		help.add(help7);
		help.add(help8);
		help.add(help9);

		/*
	  The menu bar.
	 */
		JMenuBar menuBar = new JMenuBar();
		menuBar.add(file);
		menuBar.add(help);

		this.frame.setJMenuBar(menuBar);
	}

	/**
	 * Load grid.
	 *
	 * @param file the file
	 */
	public void loadGrid(final File file) {
		System.out.println("Began reading in data file.");
		Input input;
		try {
			input = new Input(new FileInputStream(file));
			if (this.getGrid() != null)
				this.replaceGrid(this.kryo.readObject(input, Grid.class));
			else
				this.setGrid(this.kryo.readObject(input, Grid.class));
		} catch (final FileNotFoundException e) {
			e.printStackTrace();
		}
		System.out.println("Finished reading in data file.");
	}

	/**
	 * Gets the grid.
	 *
	 * @return the grid
	 */
	public Grid getGrid() {
		return this.grid;
	}

	/**
	 * Cache grid.
	 *
	 * @param file the file
	 */
	public void cacheGrid(final File file) {
		Output output;
		try {
			output = new Output(new FileOutputStream(file));
			this.kryo.writeObject(output, this.getGrid());
			System.out.println("Sucessfully cached data.");
		} catch (final Exception e1) {
			e1.printStackTrace();
			System.out.println("Failed to cache data.");
		}
	}

	/**
	 * Replace grid.
	 *
	 * @param grid the grid
	 */
	public void replaceGrid(final Grid grid) {
		this.getGrid().setSelf(grid);
	}

	/**
	 * Sets the grid.
	 *
	 * @param grid the new grid
	 */
	public void setGrid(final Grid grid) {
		this.grid = grid;
	}

	/**
	 * Disable nodes.
	 */
	public void disableNodes() {
		this.drawNodes = false;
	}

	/**
	 * Disable paths.
	 */
	public void disablePaths() {
		this.drawPaths = false;
	}

	/**
	 * Enable nodes.
	 */
	public void enableNodes() {
		this.drawNodes = true;
	}

	/**
	 * Enable paths.
	 */
	public void enablePaths() {
		this.drawPaths = true;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
	 */
	@Override
	public void paintComponent(final Graphics g) {
		final long startTime = System.nanoTime();
		final Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g2d.setPaint(new GradientPaint(0, 0, new Color(25, 25, 112), this.getWidth(), this.getHeight(), new Color(176, 196, 222)));
		g2d.fillRect(0, 0, this.getWidth(), this.getHeight());

		this.getGrid().paint(g2d);

		if (this.getDrawNodes() || this.getDrawBalencedNodes())
			this.getGrid().paintNodes(g2d);

		if (this.getDrawPaths()) {
			g2d.setColor(new Color(255, 255, 0)); // Yellow
			g2d.setStroke(new BasicStroke(2, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
			final double max = this.getGrid().getPaths().get(0).getTotalDistance();
			for (int i = 0; i < this.getGrid().getPaths().size(); i++) {
				if (this.getGrid().getPaths().get(i).getTotalDistance() > max)
					break;
				this.getGrid().paintPointSet(g2d, this.getGrid().getPaths().get(i).getArray(), this.getDrawDirection(), 0, 0);
			}
		}
		if (this.getDrawNodes()) {
			g2d.setColor(new Color(255, 0, 255)); // Magenta
			g2d.setStroke(new BasicStroke(2, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
			this.getGrid().paintPointSet(g2d, this.getGrid().getPathNodeMap().getPointArray(), this.getDrawDirection(), 2, 2);
		}
		if (this.getDrawBalencedNodes()) {
			g2d.setColor(new Color(0, 255, 255)); // Cyan
			g2d.setStroke(new BasicStroke(2, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
			final double max = this.getGrid().getPathNodeMaps().get(0).getTotalDistance();
			for (int i = 0; i < this.getGrid().getPathNodeMaps().size(); i++) {
				if (this.getGrid().getPathNodeMaps().get(i).getTotalDistance() > max)
					break;
				this.getGrid().paintPointSet(g2d, this.getGrid().getPathNodeMaps().get(i).getPointArray(), this.getDrawDirection(), -2, -2);
			}
		}

		g2d.setColor(new Color(255, 193, 63));
		try {
			g2d.drawString(String.format("FPS: %06.2f ; Yellow length: %07.3f; Cyan length: %07.3f; Magenta length: %07.3f", 1 / ((double) (System.nanoTime() - startTime) / 1000000000), this.getGrid().getPaths().get(0).getTotalDistance(), this.getGrid().getPathNodeMaps().get(0).getTotalDistance(), this.getGrid().getPathNodeMap().getTotalDistance()), 5, this.getHeight() - 5);
		} catch (final Exception e) {
			e.printStackTrace();
		}
		this.repaint();
	}

	/**
	 * Gets the draw nodes.
	 *
	 * @return the draw nodes
	 */
	public boolean getDrawNodes() {
		return this.drawNodes;
	}

	/**
	 * Gets the draw balenced nodes.
	 *
	 * @return the draw balenced nodes
	 */
	public boolean getDrawBalencedNodes() {
		return this.drawBalencedNodes;
	}

	/**
	 * Gets the draw paths.
	 *
	 * @return the draw paths
	 */
	public boolean getDrawPaths() {
		return this.drawPaths;
	}

	/**
	 * Gets the draw direction.
	 *
	 * @return the draw direction
	 */
	public boolean getDrawDirection() {
		return this.drawDirection;
	}

	/**
	 * Sets the draw direction.
	 *
	 * @param drawDirection the new draw direction
	 */
	public void setDrawDirection(final boolean drawDirection) {
		this.drawDirection = drawDirection;
	}

	/**
	 * Sets the draw paths.
	 *
	 * @param drawPaths the new draw paths
	 */
	public void setDrawPaths(final boolean drawPaths) {
		this.drawPaths = drawPaths;
	}

	/**
	 * Sets the draw balenced nodes.
	 *
	 * @param drawBalencedNodes the new draw balenced nodes
	 */
	public void setDrawBalencedNodes(final boolean drawBalencedNodes) {
		this.drawBalencedNodes = drawBalencedNodes;
	}

	/**
	 * Sets the draw nodes.
	 *
	 * @param drawNodes the new draw nodes
	 */
	public void setDrawNodes(final boolean drawNodes) {
		this.drawNodes = drawNodes;
	}
}

class KeyPressActions extends AbstractAction {
	private static final long serialVersionUID = 1L;
	private final GridDraw component;
	private final Grid grid;
	private final actionSource source;

	public KeyPressActions(final Grid grid, final GridDraw component, final actionSource source) {
		super();
		this.grid = grid;
		this.component = component;
		this.source = source;
	}

	@Override
	public void actionPerformed(final ActionEvent e) {
		switch (this.source) {
			case UP: {
				this.grid.moveHighlightedSquare(0);
				this.component.repaint();
				break;
			}
			case DOWN: {
				this.grid.moveHighlightedSquare(1);
				this.component.repaint();
				break;
			}
			case LEFT: {
				this.grid.moveHighlightedSquare(2);
				this.component.repaint();
				break;
			}
			case RIGHT: {
				this.grid.moveHighlightedSquare(3);
				this.component.repaint();
				break;
			}
			case ENTER: {
				this.grid.rotateHighlightedSquare(true);
				PathfindAI.computeDistance(this.grid, this.grid.getStartPoint());

				if (this.component.getDrawPaths()) {
					PathfindAI.computePaths(this.grid);
					PathfindAI.optimizePaths(this.grid);
				}
				if (this.component.getDrawNodes()) {
					PathfindAI.identifyNodes(this.grid);
					PathfindAI.connectNodes(this.grid);
				}
				if (this.component.getDrawBalencedNodes()) {
					PathfindAI.identifyNodes(this.grid);
					PathfindAI.connectAllNodes(this.grid);
				}
				this.component.repaint();
				break;
			}
			case SHIFT_ENTER: {
				this.grid.rotateHighlightedSquare(false);
				PathfindAI.computeDistance(this.grid, this.grid.getStartPoint());

				if (this.component.getDrawPaths()) {
					PathfindAI.computePaths(this.grid);
					PathfindAI.optimizePaths(this.grid);
				}
				if (this.component.getDrawNodes()) {
					PathfindAI.identifyNodes(this.grid);
					PathfindAI.connectNodes(this.grid);
				}
				if (this.component.getDrawBalencedNodes()) {
					PathfindAI.identifyNodes(this.grid);
					PathfindAI.connectAllNodes(this.grid);
				}
				this.component.repaint();
				break;
			}
			case P: {
				if (!this.component.getDrawPaths()) {
					PathfindAI.computeDistance(this.grid, this.grid.getStartPoint());
					PathfindAI.computePaths(this.grid);
					PathfindAI.optimizePaths(this.grid);
				}
				this.component.setDrawPaths(!this.component.getDrawPaths());
				this.component.repaint();
				break;
			}
			case B: {
				if (!this.component.getDrawBalencedNodes()) {
					PathfindAI.identifyNodes(this.grid);
					PathfindAI.connectAllNodes(this.grid);
				}
				this.component.setDrawBalencedNodes(!this.component.getDrawBalencedNodes());
				this.component.repaint();
				break;
			}
			case N: {
				if (!this.component.getDrawNodes()) {
					PathfindAI.identifyNodes(this.grid);
					PathfindAI.connectNodes(this.grid);
				}
				this.component.setDrawNodes(!this.component.getDrawNodes());
				this.component.repaint();
				break;
			}
			case R: {
				PathfindAI.computeDistance(this.grid, this.grid.getStartPoint());
				PathfindAI.computePaths(this.grid);
				this.component.repaint();
				break;
			}
			case CONTROL_R: {
				this.component.repaint();
				break;
			}
			case CONTROL_S: {
				this.component.cacheGrid(this.component.saveDir);
				break;
			}
			case L: {
				try {
					this.component.loadGrid(this.component.saveDir);
				} catch (final Exception e1) {
					System.out.println("No Grid found!");
				}
				break;
			}
			case O: {
				PathfindAI.optimizePaths(this.grid);
				this.component.repaint();
				break;
			}
			case C: {
				this.component.setDrawDirection(!this.component.getDrawDirection());
				this.component.repaint();
				break;
			}
			case SAVE_AS: {
				final JFileChooser save = new JFileChooser(this.component.saveDir);

				final int returnVal = save.showSaveDialog(this.component);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					this.component.saveDir = save.getSelectedFile();
					this.component.cacheGrid(this.component.saveDir);
				}
				break;
			}
			case OPEN: {
				final JFileChooser open = new JFileChooser(this.component.saveDir);

				final int returnVal = open.showOpenDialog(this.component);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					this.component.saveDir = open.getSelectedFile();
					this.component.loadGrid(this.component.saveDir);
				}
				break;
			}
			case NEW: {
				final NewFileWindow window = new NewFileWindow(this.component);
				window.getFrame().setVisible(true);
				break;
			}
			default:
				throw new IllegalArgumentException("Bad source received!");
		}
	}

	public enum actionSource {
		B, C, CONTROL_R, CONTROL_S, DOWN, ENTER, L, LEFT, N, NEW, O, OPEN, P, R, RIGHT, SAVE_AS, SHIFT_ENTER, UP
	}
}

class MouseCycleListener implements MouseListener {
	private final GridDraw component;
	private final Grid grid;

	public MouseCycleListener(final Grid grid, final GridDraw component) {
		super();
		this.grid = grid;
		this.component = component;
	}

	@Override
	public void mouseClicked(final MouseEvent e) {
	}

	@Override
	public void mousePressed(final MouseEvent e) {
	}

	@Override
	public void mouseReleased(final MouseEvent e) {
		this.grid.setHighlightedSquare(e.getX() / this.grid.getSquareWidth(), e.getY() / this.grid.getSquareHeight());
		if (this.grid.testSetHighlightedSquare(e.getX() / this.grid.getSquareWidth(), e.getY() / this.grid.getSquareHeight())) {
			if (e.getButton() == MouseEvent.BUTTON1)
				this.grid.rotateHighlightedSquare(true);
			else if (e.getButton() == MouseEvent.BUTTON3)
				this.grid.rotateHighlightedSquare(false);

			PathfindAI.computeDistance(this.grid, this.grid.getStartPoint());
			if (this.component.getDrawPaths()) {
				PathfindAI.computePaths(this.grid);
				PathfindAI.optimizePaths(this.grid);
			}
			if (this.component.getDrawNodes()) {
				PathfindAI.identifyNodes(this.grid);
				PathfindAI.connectNodes(this.grid);
			}
			if (this.component.getDrawBalencedNodes()) {
				PathfindAI.identifyNodes(this.grid);
				PathfindAI.connectAllNodes(this.grid);
			}
			this.component.repaint();
		}
	}

	@Override
	public void mouseEntered(final MouseEvent e) {
	}

	@Override
	public void mouseExited(final MouseEvent e) {
	}
}