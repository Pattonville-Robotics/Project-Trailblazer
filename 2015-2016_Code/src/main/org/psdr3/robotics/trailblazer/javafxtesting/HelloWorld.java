package org.psdr3.robotics.trailblazer.javafxtesting;

/**
 * Created by skaggsm on 9/3/15.
 */

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.effect.BlendMode;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.Mnemonic;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class HelloWorld extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {

		Canvas canvas = new Canvas(300, 300);
		final GraphicsContext g = canvas.getGraphicsContext2D();
		g.setFill(Color.YELLOW);
		g.setStroke(Color.YELLOW);
		g.setLineWidth(5);
		g.fillOval(100, 100, 50, 25);

		canvas.blendModeProperty().setValue(BlendMode.HARD_LIGHT);
		canvas.setOnMouseDragged(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				int radius = 5;
				g.fillOval(event.getX() - radius, event.getY() - radius, 2 * radius, 2 * radius);
			}
		});

		MenuBar menuBar = new MenuBar();
		menuBar.setUseSystemMenuBar(true);
		Menu menu = new Menu("Test Menu");
		menuBar.getMenus().add(menu);
		MenuItem menuItem1 = new MenuItem("Test MenuItem 1 (New)");
		MenuItem menuItem2 = new MenuItem("Test MenuItem 2 (Open)");
		menuItem1.acceleratorProperty().setValue(KeyCombination.valueOf("Shortcut+N"));
		menuItem1.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				System.out.println("Shortcut + N");
				g.setFill(Color.BLUE);
				g.setStroke(Color.BLUE);
			}
		});
		menuItem2.acceleratorProperty().setValue(KeyCombination.valueOf("Shortcut+O"));
		menuItem2.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				System.out.println("Shortcut + O");
				g.setFill(Color.YELLOW);
				g.setStroke(Color.YELLOW);
			}
		});
		menu.getItems().addAll(menuItem1, menuItem2);

		Button btn = new Button();
		btn.setText("Say 'Hello World'");
		btn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				if (event.getSource().getClass() == event.getTarget().getClass()) {
					System.out.println("Directed at the button.");
				}
			}
		});

		FlowPane root = new FlowPane(Orientation.VERTICAL);
		root.addEventHandler(ActionEvent.ACTION, new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
				if (event.getSource().getClass() == event.getTarget().getClass()) {
					System.out.println("Directed at the root.");
				}
			}
		});
		root.getChildren().addAll(menuBar, btn, canvas);
		root.setBackground(new Background(new BackgroundFill(Color.GREEN, new CornerRadii(25, true), Insets.EMPTY)));

		Scene scene = new Scene(root, 300, 250);
		scene.addMnemonic(new Mnemonic(btn, KeyCombination.valueOf("Shortcut+C")));
		scene.addMnemonic(new Mnemonic(root, KeyCombination.valueOf("Shortcut+V")));

		primaryStage.setTitle("Hello World!");
		primaryStage.setScene(scene);
		primaryStage.show();
	}
}