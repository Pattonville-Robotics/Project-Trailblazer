package org.psdr3.robotics.trailblazer;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * Created by skaggsm on 9/3/15.
 */
public class Designer extends Application {

	public static void main(String[] args) {
		Application.launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		final FlowPane root = new FlowPane(Orientation.VERTICAL);
		final Scene scene = new Scene(root, 500, 500);
		final MenuBar menuBar = new MenuBar();
		final Menu fileMenu = new Menu("File");
		final Menu editMenu = new Menu("Edit");
		final MenuItem newItem = new MenuItem("New map...");
		final MenuItem openItem = new MenuItem("Open map...");
		final MenuItem saveItem = new MenuItem("Save map...");
		final Canvas canvas = new Canvas(400, 400);
		final Grid grid = new Grid(10, 10);

		root.getChildren().addAll(menuBar, canvas);
		menuBar.getMenus().addAll(fileMenu, editMenu);
		menuBar.setUseSystemMenuBar(true);
		menuBar.prefWidthProperty().bind(primaryStage.widthProperty());
		fileMenu.getItems().addAll(newItem, openItem, saveItem);
		editMenu.getItems().addAll();

		canvas.heightProperty().bind(scene.heightProperty());
		canvas.widthProperty().bind(scene.widthProperty());

		final GraphicsContext g = canvas.getGraphicsContext2D();

		primaryStage.setTitle("Map Designer");
		primaryStage.setScene(scene);

		new AnimationTimer() {
			long t = 0;

			@Override
			public void handle(long now) {
				//This is run during the frame.
				//System.out.println("Height: " + canvas.getHeight() + " Width: " + canvas.getWidth());
				g.clearRect(0, 0, g.getCanvas().getWidth(), g.getCanvas().getHeight());

				long dT = now - t;
				t = now;

				g.setStroke(Color.BLUE);
				g.setFill(Color.BLUE);
				g.fillOval(100 * Math.sin(System.currentTimeMillis() / 1000d) + 100, 50, 100, 100);
				g.fillText("Width: " + canvas.getWidth() + " Height: " + canvas.getHeight() + " FPS: " + (10e8f / dT), 10, canvas.getHeight() - 10);
			}
		}.start();

		primaryStage.show();
	}

	private void updateGraphics(GraphicsContext g, Grid grid) {
		g.setFill(Color.WHITE);
		g.setStroke(Color.WHITE);
		g.fillRect(0, 0, g.getCanvas().getWidth(), g.getCanvas().getHeight());

		double gridSideLength = Math.min(g.getCanvas().getHeight() / grid.numRows(), g.getCanvas().getWidth() / grid.numCols());
	}
}
