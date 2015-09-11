package org.psdr3.robotics.trailblazer;

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
		FlowPane root = new FlowPane(Orientation.VERTICAL);
		Scene scene = new Scene(root, 500, 500);
		MenuBar menuBar = new MenuBar();
		Menu fileMenu = new Menu("File");
		Menu editMenu = new Menu("Edit");
		MenuItem newItem = new MenuItem("New map...");
		MenuItem openItem = new MenuItem("Open map...");
		MenuItem saveItem = new MenuItem("Save map...");
		Canvas canvas = new Canvas();

		root.getChildren().addAll(menuBar, canvas);
		menuBar.getMenus().addAll(fileMenu, editMenu);
		menuBar.setUseSystemMenuBar(true);
		menuBar.prefWidthProperty().bind(primaryStage.widthProperty());
		fileMenu.getItems().addAll(newItem, openItem, saveItem);
		editMenu.getItems().addAll();

		GraphicsContext g = canvas.getGraphicsContext2D();

		primaryStage.setTitle("Map Designer");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	private void updateGraphics(GraphicsContext g, Grid grid) {
		g.setFill(Color.WHITE);
		g.setStroke(Color.WHITE);
		g.fillRect(0, 0, g.getCanvas().getWidth(), g.getCanvas().getHeight());

		double gridSideLength = Math.min(g.getCanvas().getHeight() / grid.numRows(), g.getCanvas().getWidth() / grid.numCols());
	}
}
