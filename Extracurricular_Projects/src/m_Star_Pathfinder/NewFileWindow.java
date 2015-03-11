package m_Star_Pathfinder;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JSpinner;
import javax.swing.JSplitPane;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class NewFileWindow
{

	/**
	 * Launch the application.
	 */
	public static void main(final String[] args)
	{
		final NewFileWindow window = new NewFileWindow(null);
		window.frame.setVisible(true);
	}

	private JFrame				frame;
	private GridDraw			superGridDraw;
	private GridPreviewPanel	previewPanel;

	/**
	 * Create the application.
	 */
	public NewFileWindow(GridDraw superGridDraw)
	{
		this.initialize();
		this.superGridDraw = superGridDraw;
	}

	public JFrame getFrame()
	{
		return frame;
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize()
	{
		this.frame = new JFrame();
		this.frame.setBounds(100, 100, 450, 275);
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		final JSplitPane splitPane = new JSplitPane();
		this.frame.getContentPane().add(splitPane, BorderLayout.CENTER);

		previewPanel = new GridPreviewPanel(2, 2, 40, 40);
		splitPane.setRightComponent(previewPanel);

		final JPanel settingsPanel = new JPanel();
		settingsPanel.setMinimumSize(new Dimension(100, 100));
		splitPane.setLeftComponent(settingsPanel);
		settingsPanel.setLayout(new BoxLayout(settingsPanel, BoxLayout.Y_AXIS));

		final JLabel lblNewLabel = new JLabel("Columns");
		lblNewLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		settingsPanel.add(lblNewLabel);

		final JSpinner widthSpinner = new JSpinner();
		widthSpinner.addChangeListener(new ChangeListener()
		{
			public void stateChanged(ChangeEvent e)
			{
				previewPanel.setNumRows((Integer) widthSpinner.getValue());
				frame.repaint();
			}
		});
		widthSpinner.setModel(new SpinnerNumberModel(new Integer(4), new Integer(4), null, new Integer(1)));
		widthSpinner.setMaximumSize(new Dimension(76, 28));
		settingsPanel.add(widthSpinner);

		final JLabel lblNewLabel_1 = new JLabel("Rows");
		lblNewLabel_1.setAlignmentX(Component.CENTER_ALIGNMENT);
		settingsPanel.add(lblNewLabel_1);

		final JSpinner heightSpinner = new JSpinner();
		heightSpinner.addChangeListener(new ChangeListener()
		{
			public void stateChanged(ChangeEvent e)
			{
				previewPanel.setNumColumns((Integer) heightSpinner.getValue());
				frame.repaint();
			}
		});
		heightSpinner.setModel(new SpinnerNumberModel(new Integer(4), new Integer(4), null, new Integer(1)));
		heightSpinner.setMaximumSize(new Dimension(76, 28));
		heightSpinner.setToolTipText("Width");
		settingsPanel.add(heightSpinner);

		final JLabel lblNewLabel_2 = new JLabel("Square Width");
		lblNewLabel_2.setAlignmentX(Component.CENTER_ALIGNMENT);
		settingsPanel.add(lblNewLabel_2);

		final JSpinner squareWidthSpinner = new JSpinner();
		squareWidthSpinner.addChangeListener(new ChangeListener()
		{
			public void stateChanged(ChangeEvent e)
			{
				previewPanel.setSquareWidth((Integer) squareWidthSpinner.getValue());
				frame.repaint();
			}
		});
		squareWidthSpinner.setModel(new SpinnerNumberModel(new Integer(40), null, null, new Integer(1)));
		squareWidthSpinner.setMaximumSize(new Dimension(76, 28));
		settingsPanel.add(squareWidthSpinner);

		final JLabel lblNewLabel_3 = new JLabel("Square Height");
		lblNewLabel_3.setAlignmentX(Component.CENTER_ALIGNMENT);
		settingsPanel.add(lblNewLabel_3);

		final JSpinner squareHeightSpinner = new JSpinner();
		squareHeightSpinner.addChangeListener(new ChangeListener()
		{
			public void stateChanged(ChangeEvent e)
			{
				previewPanel.setSquareHeight((Integer) squareHeightSpinner.getValue());
				frame.repaint();
			}
		});
		squareHeightSpinner.setModel(new SpinnerNumberModel(new Integer(40), null, null, new Integer(1)));
		squareHeightSpinner.setMaximumSize(new Dimension(76, 28));
		settingsPanel.add(squareHeightSpinner);

		final JSeparator separator = new JSeparator();
		settingsPanel.add(separator);

		final JButton btnCreate = new JButton("Create");
		btnCreate.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				Grid grid = new Grid((Integer) widthSpinner.getValue(), (Integer) heightSpinner.getValue(), (Integer) squareWidthSpinner.getValue(),
						(Integer) squareHeightSpinner.getValue());
				grid.setSquareContents(new Point(0, 0), SquareType.START);
				grid.setSquareContents(new Point(3, 3), SquareType.FINISH);
				PathfindAI.computeDistance(grid, grid.getStartPoint());
				PathfindAI.computePaths(grid);
				PathfindAI.optimizePaths(grid);
				superGridDraw.replaceGrid(grid);
				frame.dispose();
			}
		});
		btnCreate.setAlignmentX(Component.CENTER_ALIGNMENT);
		settingsPanel.add(btnCreate);

		final JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				frame.dispose();
			}
		});
		btnCancel.setAlignmentX(Component.CENTER_ALIGNMENT);
		settingsPanel.add(btnCancel);
	}
}
