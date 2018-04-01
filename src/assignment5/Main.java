package assignment5;

import javafx.animation.AnimationTimer;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Parent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.lang.reflect.Modifier;
import java.net.URISyntaxException;
import java.util.*;
import java.io.*;

import assignment5.Critter.CritterShape;

import javax.swing.*;
import javax.xml.soap.Text;

import static javafx.application.Application.launch;

public class Main extends Application {
	//copy/paste preibe hello world example. make bunch of buttons and handlers
	// copy paste different cases into main from main lab 4
	//world
	private static Pane pane = new StackPane();//stays in middle
	private static Pane world = new GridPane();//stays in top left
	//shapes
	private static Circle circle = new Circle();
	private static Polygon square = new Polygon();
	static Polygon triangle = new Polygon();
	static Polygon diamond = new Polygon();
	static Polygon star = new Polygon();
	@Override
	public void start(Stage primarystage){
	    Parent root=null;
	    try {
             root = FXMLLoader.load(getClass().getResource("assignment5.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
		Scene scene = new Scene(root,root.prefWidth(root.getLayoutX()),root.prefHeight(root.getLayoutY()));
		primarystage.setTitle("CRITTER WORLD");
		primarystage.setScene(scene);
		primarystage.show();
		System.out.println("start method");
	}
	public void pressButton(ActionEvent event){
	    System.out.println("HELLO");

    }
	public static void main(String[] args) {
		 Application.launch(args);

	}

    public void makeCritter(ActionEvent actionEvent) {
    }

    public void executeTimeSteps(ActionEvent actionEvent) {
    }

    public void displayWorld(ActionEvent actionEvent) {
    }

    public void displayStats(ActionEvent actionEvent) {
    }

    public void endSimulation(ActionEvent actionEvent) {
    }

    public void startAnimation(ActionEvent actionEvent) {
    }

    public void stopAnimation(ActionEvent actionEvent) {
    }
}
