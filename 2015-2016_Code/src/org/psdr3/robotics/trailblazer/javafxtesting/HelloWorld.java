package org.psdr3.robotics.trailblazer.javafxtesting;

/**
 * Created by skaggsm on 9/3/15.
 */

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.Mnemonic;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class HelloWorld extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {

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
			}
		});
		menuItem2.acceleratorProperty().setValue(KeyCombination.valueOf("Shortcut+O"));
		menuItem2.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				System.out.println("Shortcut + O");
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

		Canvas canvas = new Canvas(300, 300);
		GraphicsContext g = canvas.getGraphicsContext2D();
		g.setFill(Color.BLUE);
		g.setStroke(Color.BLUE);
		g.setLineWidth(5);
		g.fillOval(100, 100, 50, 25);

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

		Scene scene = new Scene(root, 300, 250);
		scene.addMnemonic(new Mnemonic(btn, KeyCombination.valueOf("Shortcut+C")));
		scene.addMnemonic(new Mnemonic(root, KeyCombination.valueOf("Shortcut+V")));

		primaryStage.setTitle("Hello World!");
		primaryStage.setScene(scene);
		primaryStage.show();
	}
}