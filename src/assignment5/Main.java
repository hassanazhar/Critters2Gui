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
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.paint.*;
import javafx.scene.shape.*;

import java.awt.*;
import java.awt.Rectangle;
import java.awt.Shape;
import java.lang.reflect.Modifier;
import java.net.URISyntaxException;
import java.util.*;
import java.io.*;

import assignment5.Critter.CritterShape;
import sun.awt.image.ImageWatched;

import javax.swing.*;
import javax.xml.soap.Text;

import static javafx.application.Application.launch;

public class Main extends Application {
	//copy/paste preibe hello world example. make bunch of buttons and handlers
	// copy paste different cases into main from main lab 4
	//world
	private static Pane pane = new StackPane();//stays in middle
	private static Pane crittergrid = new GridPane();//stays in top left
	//shapes
	private static Circle circle = new Circle();
	private static Polygon square = new Polygon();
	static Polygon triangle = new Polygon();
	static Polygon diamond = new Polygon();
	static Polygon star = new Polygon();
	//static final ComboBox lol = new ComboBox();


	@FXML
	private TextField numCritters;
	@FXML
	private TextField numSteps;
	@FXML
	private TextField seedValue;
	@FXML
	private ComboBox selectbox;
	public void initialize(){
		selectbox.getItems().addAll(
				"Algae","Ali","Tank","Boss","BigBoss","Craig"
		);
	}
	@FXML
	private TextField numAni;

	@Override
	public void start(Stage primarystage){
	//	selectbox.getItems().addAll(
	//			"Algae","Ali","Tank","Boss","BigBoss","Craig"
	//	);
	    Parent root=null;
	    try {
             root = FXMLLoader.load(getClass().getResource("assignment5.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
		Scene scene = new Scene(root,root.prefWidth(root.getLayoutX()),root.prefHeight(root.getLayoutY()));
		primarystage.setTitle("Controls");
		primarystage.setScene(scene);
		primarystage.show();

		Stage secondStage = new Stage();
		Scene scene2 = new Scene(crittergrid,Params.world_width,Params.world_height);
		secondStage.setTitle("Critter Grid");
		secondStage.setScene(scene2);
		secondStage.show();
		//Need to paint items on grid now.


		System.out.println("start method");
	}
	public static void main(String[] args) {
		 Application.launch(args);

	}
	//done
	public void numCritter(ActionEvent event) throws InvalidCritterException{
		int numcrits = Integer.parseInt(numCritters.getText());
		String critname= selectbox.getValue().toString();
		//String critname = selectbox.getSelectionModel().getSelectedItem().toString();
		try {
			for (int i = 0; i < numcrits; i++) {
				Critter.makeCritter(critname);
			}
		}catch(InvalidCritterException e){
			throw new InvalidCritterException(critname);
		}
    }
	//done
    public void numTimeSteps(ActionEvent event) {
		int numts = Integer.parseInt(numSteps.getText());
		for(int i=0; i<numts;i++) {
			Critter.worldTimeStep();
		}
    }
	//done
    public void showWorld(ActionEvent event) {
		Critter.displayWorld();

    }
	//done
    public void stats(ActionEvent event) {
		LinkedList<Critter> new2= Critter.getalivecritters();
		Critter.runStats(new2);
    }
	//done
    public void end(ActionEvent event) {
		System.exit(0);
    }

    public  void makeGridWorld(GridPane grid){
		for(int r=0;r<Params.world_width;r++){
			for(int c = 0; c<Params.world_height; c++){
				Shape r1 = new Rectangle(1,1);
				r1.setFill(Color.BLUE);
				r1.setStroke(Color.BLACK);
				grid.add(r1,c,r);
			}
		}
	}

    public void startAni(ActionEvent actionEvent) {
    }

    public void stopAni(ActionEvent actionEvent) {
    }
}
