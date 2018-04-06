package assignment5;

import javafx.animation.*;
import javafx.application.Application;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.paint.*;
import javafx.scene.shape.*;

import java.lang.reflect.Modifier;
import java.net.URISyntaxException;
import java.util.*;
import java.io.*;

import javafx.scene.text.*;


import javafx.util.Duration;

public class Main extends Application {
	//copy/paste preibe hello world example. make bunch of buttons and handlers
	// copy paste different cases into main from main lab 4
	//world
	private static Pane pane = new StackPane();//stays in middle
	private Stage secondStage = new Stage();
	private GridPane rootGrid = new GridPane();

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
				"Algae","Tank","Boss","BigBoss","Craig","TragicCritter"
		);
	}
	@FXML
	private TextField numAni;
	@FXML
	private TextField reflectionCritter;


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

		//makeGridWorld(crittergrid);
		//scene2 = new Scene(crittergrid,Params.world_width,Params.world_height);
		//secondStage.setScene(scene2);
		//secondStage.show();
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
		System.out.println("DONE");
	}
	//done
	public void showWorld(ActionEvent event) {
		GridPane crittergrid = new GridPane();
		makeGridWorld(crittergrid);

		try {
			crittergrid.getChildren().clear();
			makeGridWorld(crittergrid);
			Critter.displayWorld(crittergrid);
			Critter.displayWorld();

			Scene scene = new Scene(crittergrid, Params.world_width, Params.world_height);
			//makeGridWorld(crittergrid);
			secondStage.setScene(scene);
			secondStage.show();
		}
		catch(Exception e){
		}

	}
	//done
	public void stats(ActionEvent event) {
		LinkedList<Critter> new2= Critter.getalivecritters();
		Critter.runStats(new2);

		Text text = new Text();
		text.setText("Life is Good");
		text.setX(100);
		text.setY(100);
		text.setFont(new Font(90));

		Pane pane = new Pane(text);


		Scene scene = new Scene(pane, 600, 300);

		Stage statStage = new Stage();
		statStage.setScene(scene);
		statStage.setTitle("Critter Statistics");
		statStage.show();

	}
	//done
	public void end(ActionEvent event) {
		System.exit(0);
	}

	public  void makeGridWorld(GridPane grid){
		for(int r=0;r<Params.world_width;r++){
			for(int c = 0; c<Params.world_height; c++){
				// Explicit Fix
				//javafx.scene.shape.Shape rect = new javafx.scene.shape.Rectangle();
				//rect.setFill(Color.GREEN);
				//rect.setFill(Color.RED);

				// Fix where i remove the unnecessary import statements
				// Use 'alt-enter' to select the correct class declarations
				Shape r1 = new Rectangle(10,10);
				r1.setFill(null);
				r1.setStroke(Color.BLACK);
				grid.add(r1,c,r);
			}
		}
	}
	//WORK ON BELOW. ADD ANIMATION
	int count = 0;
	boolean work=true;
	public void reflection(ActionEvent actionEvent) throws InvalidCritterException {
		String s = reflectionCritter.getText();
		System.out.println(s);
		try {
			Critter.makeCritter(s);
			selectbox.getItems().add(s);

		}
		catch(InvalidCritterException e){
			e.printStackTrace();
		}

	}
	public void startAni(ActionEvent actionEvent) {
		//work =true;

			int doSteps = Integer.parseInt(numSteps.getText());
			int numani = Integer.parseInt(numAni.getText());
			animate(doSteps,numani, work);
	}
	private Timeline animation;
	public void animate(int steps, int numAni, boolean work) {
		if(work) {
			int steps1 = 0;
			int numAni1 = 0;
			if (steps > 0) {
				steps1 = steps;
			}
			if (numAni > 0) {
				numAni1 = numAni;
			}
			int numDisplayFrame = (steps1 / numAni1);
			animation = new Timeline(new KeyFrame(Duration.seconds(1), e -> critterstep(numDisplayFrame)));
			animation.setCycleCount(numDisplayFrame);
			animation.play();

			Scene scene = new Scene(rootGrid, 10 * Params.world_width, 10 * Params.world_height);
			secondStage.setTitle("Critter Grid");
			secondStage.setScene(scene);
			secondStage.resizableProperty();
			secondStage.show();

		}

	}
	public void critterstep(int steps){
		for(int i = 0; i < steps; i ++){
			Critter.worldTimeStep();
		}
		rootGrid.getChildren().clear();
		makeGridWorld(rootGrid);
		Critter.displayWorld(rootGrid);
		Critter.displayWorld();
		count = count + 1;
		System.out.println("This is run: " + count);
	}
	public void stopAni(ActionEvent actionEvent) {
		animation.stop();
	}
}