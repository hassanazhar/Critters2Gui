package assignment5;

import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.geometry.Pos;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import static assignment5.Critter.CritterShape.CIRCLE;
import static assignment5.Critter.CritterShape.SQUARE;

public abstract class Critter {
	/* NEW FOR PROJECT 5 */
	public enum CritterShape {
		CIRCLE,
		SQUARE,
		TRIANGLE,
		DIAMOND,
		STAR
	}

	/* the default color is white, which I hope makes critters invisible by default
	 * If you change the background color of your View component, then update the default
	 * color to be the same as you background
	 *
	 * critters must override at least one of the following three methods, it is not
	 * proper for critters to remain invisible in the view
	 *
	 * If a critter only overrides the outline color, then it will look like a non-filled
	 * shape, at least, that's the intent. You can edit these default methods however you
	 * need to, but please preserve that intent as you implement them.
	 */
	public javafx.scene.paint.Color viewColor() {
		return javafx.scene.paint.Color.WHITE;
	}

	public javafx.scene.paint.Color viewOutlineColor() { return viewColor(); }
	public javafx.scene.paint.Color viewFillColor() { return viewColor(); }

	public abstract CritterShape viewShape();

	private static String myPackage;
	private	static List<Critter> population = new java.util.ArrayList<Critter>();
	private static List<Critter> babies = new java.util.ArrayList<Critter>();
	private static int[][] map = new int[Params.world_width][Params.world_height];
	private static String [] realCritters = {"assignment5.Algae","assignment5.TragicCritter","assignment5.Craig","assignment5.Boss",
			"assignment5.Ali", "assignment5.Tank","assignment5.BigBoss"};
	private final int torusx(int moves, int xcoord){
		if ((xcoord+moves)>(Params.world_width-1)){
			return(moves-1);

		}
		else if((xcoord+moves)<0){
			return(Params.world_width+moves);
		}
		else{
			xcoord+=moves;
			x_coord+=moves;//need to update the variable as well
			return(xcoord);
		}
	}
	private final int torusy(int moves, int ycoord){
		if((ycoord+moves)<0){
			return(Params.world_height+moves);
		}
		else if((Params.world_height-1)<(ycoord+moves)){
			return(moves-1);

		}
		else{
			ycoord+=moves;
			y_coord+=moves; //need to update y_coord as well
			return(ycoord);
		}
	}
	private boolean isAlive;
	private boolean moved;//flag
	private static int getx_coord(Critter i){
		return i.x_coord;
	}
	private static int gety_coord(Critter i){
		return i.y_coord;
	}
	// Gets the package name.  This assumes that Critter and its subclasses are all in the same package.
	static {
		myPackage = Critter.class.getPackage().toString().split(" ")[1];
	}

	protected final String look(int direction, boolean steps) {
		this.energy = getEnergy()-Params.look_energy_cost;
		int step = 1;
		if(!steps){
			step = 1;
		}else {
			step = 2;
		}
		int x = this.x_coord;
		int y = this.y_coord;
		int xCheck = x;
		int yCheck = y;
		switch(direction){
			case (0)://E
				xCheck=torus2x(step,x);
				break;
			case(1)://NE
				xCheck=torus2x(step,x);
				yCheck=torus2y(step,y);
				break;
			case(2)://N
				yCheck=torus2y(step,y);
				break;
			case(3)://NW
				xCheck=torus2x(step,x);
				yCheck=torus2y(step,y);
				break;
			case(4)://W
				xCheck=torus2x(step,x);
				break;
			case(5)://SW
				xCheck=torus2x(step,x);
				yCheck=torus2y(step,y);
				break;
			case(6)://S
				yCheck=torus2y(step,y);
				break;
			case(7)://SE
				xCheck=torus2x(step,x);
				yCheck=torus2y(step,y);
				break;
		}

		if(map[xCheck][yCheck]>=1){
			for(Critter a : population){
				if(a.x_coord==xCheck && a.y_coord==yCheck){
					return a.toString();
				}
			}
		}
		return null;
	}

	/* rest is unchanged from Project 4 */


	private static java.util.Random rand = new java.util.Random();
	public static int getRandomInt(int max) {
		return rand.nextInt(max);
	}

	public static void setSeed(long new_seed) {
		rand = new java.util.Random(new_seed);
	}


	/* a one-character long string that visually depicts your critter in the ASCII interface */
	public String toString() { return ""; }

	private int energy = 0;
	protected int getEnergy() { return energy; }

	private int x_coord;
	private int y_coord;
	//done
	protected final void walk(int direction) {
		energy=energy-Params.walk_energy_cost;
		int x = x_coord;
		int y = y_coord;
		map[x][y] = map[x][y] -1;		// removing critter from previous spot in map
		if(x_coord >= 160 || y_coord >= 80 || x_coord < 0 || y_coord < 0)
			return;
		switch(direction){


			case (0)://E
				x_coord=torusx(1,x);
				break;

			case(1)://NE
				x_coord=torusx(1,x);
				y_coord=torusy(-1,y);
				break;
			case(2)://N
				y_coord=torusy(-1,y);
				break;
			case(3)://NW
				x_coord=torusx(-1,x);
				y_coord=torusy(-1,y);
				break;
			case(4)://W
				x_coord=torusx(-1,x);
				break;
			case(5)://SW
				x_coord=torusx(-1,x);
				y_coord=torusy(1,y);
				break;
			case(6)://S
				y_coord=torusy(1,y);
				break;
			case(7)://SE
				x_coord=torusx(1,x);
				y_coord=torusy(1,y);
				break;
		}

		if(x_coord >= 160 || y_coord >= 80 || x_coord < 0 || y_coord < 0)
			return;
		map[x_coord][y_coord] = map[x_coord][y_coord] + 1; // adding critter to new spot in map

		this.moved=true;
	}
	//done
	protected final void run(int direction) {
		energy = energy-Params.run_energy_cost;
		int x= x_coord;
		int y = y_coord;
		map[x][y] += -1;		// removing critter from previous spot in map
		switch(direction){
			case(0):
				x_coord=torusx(2,x);
				break;
			case(1):
				x_coord=torusx(2,x);
				y_coord=torusy(-2,y);
				break;
			case(2):
				y_coord=torusy(-2,y);
				break;
			case(3):
				x_coord=torusx(-2,x);
				y_coord=torusy(-2,y);
				break;
			case(4):
				x_coord=torusx(-2,x);
				break;
			case(5):
				x_coord=torusx(-2,x);
				y_coord=torusy(2,y);
			case(6):
				y_coord=torusy(2,y);
				break;
			case(7):
				x_coord=torusx(2,x);
				y_coord=torusy(2,y);
				break;

		}
		map[x_coord][y_coord] += 1;		// adding critter to new spot in map
		this.moved=true;

	}
	//done
	protected final void reproduce(Critter offspring, int direction) {
		if (this.energy<Params.min_reproduce_energy||!this.isAlive){
			return;
		}
		else{
			offspring.energy= this.energy/2;
			double energyroundup=Math.ceil(this.energy/2);
			this.energy=(int)energyroundup;
			int x = this.x_coord;
			int y = this.y_coord;
			switch(direction){
				case(0):
					offspring.x_coord=torusx(1, x);
					offspring.y_coord=y;
					break;
				case(1)://NE
					offspring.x_coord=torusx(1,x);
					offspring.y_coord=torusy(-1,y);
					break;
				case(2)://N
					offspring.x_coord=x;
					offspring.y_coord=torusy(-1,y);
					break;
				case(3)://NW
					offspring.x_coord=torusx(-1,x);
					offspring.y_coord=torusy(-1,y);
					break;
				case(4)://W
					offspring.x_coord=torusx(-1,x);
					offspring.y_coord=y;
					break;
				case(5)://SW
					offspring.x_coord=torusx(-1,x);
					offspring.y_coord=torusy(1,y);
					break;
				case(6)://S
					offspring.x_coord=x;
					offspring.y_coord=torusy(1,y);
					break;
				case(7)://SE
					offspring.x_coord=torusx(1,x);
					offspring.y_coord=torusy(1,y);
					break;

			}
			babies.add(offspring);
		}
	}

	public abstract void doTimeStep();
	public abstract boolean fight(String oponent);
	public static void worldTimeStep() {
		for (Critter A : Critter.population){
			if(A.energy<=0){
				A.isAlive = false;
			}
			else if(A.energy>0){
				A.isAlive=true;
				A.doTimeStep();
				if(A.energy<0){
					A.isAlive = false;
				}
			}

		}
		for (Iterator<Critter> iterator = Critter.population.iterator(); iterator.hasNext();){
			Critter tmp = iterator.next();
			if (tmp.isAlive == false){
				iterator.remove();
			}
		}

		encounters2();

		for (Iterator<Critter> iterator = Critter.population.iterator(); iterator.hasNext();){
			Critter tmp = iterator.next();
			if (tmp.isAlive == false){
				iterator.remove();
			}
		}

		mapCheck();
		// we have add reproduction

		// we have to add algae
		for (int i = 0; i < Params.refresh_algae_count; i = i + 1){
			//Algae child = new Algae();
			//child.setEnergy(Params.start_energy);
			//makecritter takes care of anything
			try{
				makeCritter("Algae");
			}
			catch(InvalidCritterException e){
				e.printStackTrace();
			}
		}

		//deduct rest energy
		for (Critter A : Critter.population){
			A.energy = A.getEnergy() - Params.rest_energy_cost;
			if(A.getEnergy()<=0){
				A.isAlive=false;
			}
		}
		for (Iterator<Critter> iterator = Critter.population.iterator(); iterator.hasNext();){
			Critter tmp = iterator.next();
			if (tmp.isAlive == false){
				iterator.remove();
			}
		}
		//babies add
		for(Critter b: babies){
			b.isAlive=true;
			population.add(b);
		}
		//not sure if this removes everything
		babies.clear();

	}
	// HELPER FOR WORLDTIMESTEP
	private static void rollDice(Critter A, Critter B){
		if (A.getEnergy()<=0 || B.getEnergy()<=0){
			System.out.print("ERORROORORR");
			if(A.getEnergy()<=0){
				A.isAlive=false;
				map[A.x_coord][A.y_coord]-=1;
			}
			if(B.getEnergy()<=0){
				B.isAlive=false;
				map[B.x_coord][B.y_coord]-=1;
			}
			return;
		}
		int rollA = getRandomInt(A.getEnergy());
		int rollB = getRandomInt(B.getEnergy());

		if(rollA>=rollB){
			A.isAlive=true;
			A.energy+= B.getEnergy()/2;
			B.isAlive=false;
		}
		else{
			B.isAlive=true;
			B.energy+= A.getEnergy()/2;
			A.isAlive=false;
		}
	}
	private static boolean canMove(Critter foo, int direction) {
		int x = foo.x_coord;
		int y = foo.y_coord;
		int xCheck = 0;
		int yCheck = 0;
		switch (direction) {
			case (0)://E
				xCheck = torus2x(1, x);
				break;
			case (1)://NE
				xCheck = torus2x(1, x);
				yCheck = torus2y(-1, y);
				break;
			case (2)://N
				yCheck = torus2y(-1, y);
				break;
			case (3)://NW
				xCheck = torus2x(-1, x);
				yCheck = torus2y(-1, y);
				break;
			case (4)://W
				xCheck = torus2x(-1, x);
				break;
			case (5)://SW
				xCheck = torus2x(-1, x);
				yCheck = torus2y(1, y);
				break;
			case (6)://S
				yCheck = torus2y(1, y);
				break;
			case (7)://SE
				xCheck = torus2x(1, x);
				yCheck = torus2y(1, y);
				break;
		}
		/*if(xCheck > Params.world_width-1){
			System.out.println("we messed up");
		}
		if(yCheck > Params.world_height-1){
			System.out.print("we messed up y");
		}*/

		if(map[xCheck][yCheck]>0){
			return false;
		} else {
			return true;
		}
	}
	private static int torus2x(int moves, int xcoord){
		if ((xcoord+moves)>(Params.world_width-1)){
			return (moves-1);

		}
		else if((xcoord+moves)<0){
			return(Params.world_width+moves);
		}
		else{
			xcoord+=moves;
			return(xcoord);
		}
	}
	private static int torus2y(int moves, int ycoord){
		if((ycoord+moves)<0){
			return(Params.world_height+moves);
		}
		else if((Params.world_height-1)<(ycoord+moves)){
			return (moves-1);

		}
		else{
			ycoord+=moves;
			return(ycoord);
		}
	}
	private static boolean runAway(Critter foo){
		//if A has moved
		if(foo.moved) {
			foo.energy = foo.getEnergy() - Params.walk_energy_cost;
			if (foo.getEnergy() <= 0) {
				foo.isAlive=false;
			}
			return false;
		} else {
			int dir = getRandomInt(8);
			if(canMove(foo, dir)) {
				foo.walk(dir);
				return true;
			}else{
				foo.energy = foo.getEnergy() - Params.walk_energy_cost;
				return false;
			}
		}
	}
	private static boolean sameLocation(Critter A, Critter B){
		if((A.x_coord==B.x_coord) && (A.y_coord == B.y_coord)){
			return true;
		}else {
			return false;
		}
	}
	private static void encounters2(){
		for(Critter aCrit : Critter.population){
			if(aCrit.isAlive == false){
				continue;
			}
			for(Critter bCrit : Critter.population){
				if(aCrit.isAlive == false){
					break;
				} else if(bCrit.isAlive == false){
					continue;
				}else if(aCrit == bCrit){
					continue;
				} else if(aCrit.isAlive && bCrit.isAlive){
					if(sameLocation(aCrit, bCrit)){
						boolean aFight = aCrit.fight(bCrit.toString());
						boolean bFight = bCrit.fight(aCrit.toString());

						if(aFight && bFight){
							rollDice(aCrit,bCrit);
						} else if(aFight && !bFight){
							//we want B to try and walk
							if(!runAway(bCrit)){
								aCrit.isAlive=true;
								aCrit.energy += bCrit.getEnergy()/2;
								bCrit.isAlive=false;
							}
						} else if(!aFight && bFight){
							if(!runAway(aCrit)) {
								bCrit.isAlive = true;
								bCrit.energy += aCrit.getEnergy()/2;
								aCrit.isAlive = false;
							}
						} else if(!aFight && !bFight){
							runAway(aCrit);
							if(aCrit.getEnergy()<=0){
								aCrit.isAlive = false;
							}
							runAway(bCrit);
							if(bCrit.getEnergy()<=0){
								bCrit.isAlive=false;
							}
							if(sameLocation(aCrit,bCrit)){
								rollDice(aCrit, bCrit);
							}
						}
					}
				}
			}
		}
	}

	private static void mapCheck(){
		for(int x = 0; x < Params.world_width; x = x + 1){
			for(int y = 0; y < Params.world_height; y = y + 1) {
				if (map[x][y] == 1) {
				}
			}
		}
	}
	//NOT SURE IF I CAN MAKE PUBLIC METHOD
	public static LinkedList<Critter> getalivecritters(){
		LinkedList<Critter> newlist=new LinkedList<>();
		for(Critter a: population){
			if(a.isAlive){
				newlist.add(a);
			}
		}
		return newlist;
	}
	//DONE HELPER
	//NEED TO DO DISPLAYWORLD
	public static void displayWorld(GridPane grid) {
		for(Critter a: population){
			int width = Params.world_width;
			int height = Params.world_height;


			if(a.isAlive){
				 CritterShape shape = a.viewShape();
				 int i=CritterShape.valueOf(shape.toString()).ordinal();
				Shape s;
				//CIRCLE,
				//SQUARE,
				//TRIANGLE,
				//DIAMOND,
				//STAR
				 switch(i){
					 case(0):
					 	s = new Circle(5/2);
					 	s.setStroke(a.viewOutlineColor());
					 	grid.add(s,a.x_coord,a.y_coord);
					 	break;
					 case(1):
					 	s=new Rectangle(5,5);
					 	s.setStroke(a.viewOutlineColor());
					 	grid.add(s,a.x_coord,a.y_coord);
					 	break;
					 case(2):
					 	Polygon p = new Polygon();
						p.getPoints().addAll(new Double[]{
								0.0,0.0,
								5.0,10.0,
								0.0,10.0}
						);
						p.setStroke(a.viewOutlineColor());
						grid.add(p,a.x_coord,a.y_coord);
						break;
					 case(3):
						Polygon d = new Polygon();
						 d.getPoints().addAll(new Double[]{
								 0.0,5.0,
								 5.0,10.0,
								 10.0,5.0,
								 5.0,10.0}
						 );
						 d.setStroke(a.viewOutlineColor());
						 grid.add(d,a.x_coord,a.y_coord);
						 break;
					 case(4):
						 Polygon star = new Polygon();
						 star.getPoints().addAll(new Double[]{
								 0.0,0.0,

								 5.0,10.0,
								 10.0,5.0,
								 5.0,10.0}
						 );
						 star.setStroke(a.viewOutlineColor());
						 grid.add(star,a.x_coord,a.y_coord);
						 break;
					 default:
					 		s=new Rectangle(5,5);
					 		s.setFill(Color.RED);
					 		grid.add(s,a.x_coord,a.y_coord);

				 }



				}

			}

	}

	//Alternate displayWorld, where you use Main.<pane> to reach into your
	//display component.
	//NEED TO MAKE THIS APPEAR IN GRID ON FX
	public static void displayWorld() {
		int width = Params.world_width+2;
		int height = Params.world_height+2;
		char[][]worldmap=new char[width][height];
		for(int i=0;i<width;i++){
			for(int j=0;j<height;j++){
				if((i==0&&j==0)||(i==width-1&&j==height-1)||(i==0&&j==height-1)||(i==width-1)&&(j==0)){
					worldmap[i][j]='+';
				}
				else if (i==0&&j!=0){
					worldmap[i][j]='-';
				}
				else if(j==0&&i!=0){
					worldmap[i][j]='|';
				}
				else if(i==width-1){
					worldmap[i][j]='-';
				}
				else if(j==height-1){
					worldmap[i][j]='|';
				}
				else{
					worldmap[i][j]=' ';
				}
			}
		}
		//Add critters to display
		for(Critter a : population){
			worldmap[a.x_coord+1][a.y_coord+1]=a.toString().charAt(0);
		}
		for(int i=0;i<width;i++){
			for(int j=0;j<height;j++){
				System.out.print(worldmap[i][j]);
			}
			System.out.println();
		}

	}


	/* create and initialize a Critter subclass
	 * critter_class_name must be the name of a concrete subclass of Critter, if not
	 * an InvalidCritterException must be thrown
	 */
	//done
	public static void makeCritter(String critter_class_name) throws InvalidCritterException {
		Class<?> myCritClass = null;
		Object obj;
		Constructor<?> consttr;
		boolean real = false;
		//critter_class_name = critter_class_name.substring(0,1).toUpperCase()+critter_class_name.substring(1);
		critter_class_name="assignment5."+critter_class_name;
		for(String A: realCritters){
			//String blah = "assignment4."+critter_class_name;
			if (A.equals(critter_class_name)){
				real = true;
			}
		}
		if(!real){
			throw new InvalidCritterException(critter_class_name);
		}
		try{
			myCritClass =Class.forName(critter_class_name);
			consttr=myCritClass.getConstructor();
			obj= consttr.newInstance();
			Critter c = (Critter) obj; //IS THIS OK?
			c.energy=Params.start_energy;
			c.x_coord= getRandomInt(Params.world_width);
			c.y_coord=getRandomInt(Params.world_height);
			map[c.x_coord][c.y_coord] += 1;
			c.isAlive= true;
			//add more stuff as necessary
			population.add(c);

		}catch(ClassNotFoundException e) {
			System.out.println(critter_class_name);//
			System.out.println(myCritClass);//
			System.out.println("ERROR 1");

			throw new InvalidCritterException(critter_class_name);
		}
		catch(NoSuchMethodException e){
			System.out.println("ERROR 2");
			throw new InvalidCritterException(critter_class_name);
		}
		catch (IllegalAccessException e){
			System.out.println("ERROR 3");
			throw new InvalidCritterException(critter_class_name);
		}
		catch(InvocationTargetException e){
			System.out.println("ERROR 4");
			throw new InvalidCritterException(critter_class_name);

		}
		catch(InstantiationException e){
			System.out.println("ERROR 5");
			throw new InvalidCritterException(critter_class_name);
		}

	}
	//done
	public static List<Critter> getInstances(String critter_class_name) throws InvalidCritterException {
		List<Critter> result = new java.util.ArrayList<Critter>();
		Class<?> crit = null;
		try{
			crit=Class.forName(critter_class_name);
		}
		catch (ClassNotFoundException e){
			throw new InvalidCritterException(critter_class_name);
		}
		for(Critter i: population){
			if(crit.isInstance(i)){
				result.add(i);
			}
		}

		return result;
	}
	//done
	public static void runStats(List<Critter> critters) {
		System.out.print("" + critters.size() + " critters as follows -- ");
		java.util.Map<String, Integer> critter_count = new java.util.HashMap<String, Integer>();
		for (Critter crit : critters) {
			String crit_string = crit.toString();
			Integer old_count = critter_count.get(crit_string);
			if (old_count == null) {
				critter_count.put(crit_string,  1);
			} else {
				critter_count.put(crit_string, old_count.intValue() + 1);
			}
		}
		String prefix = "";
		for (String s : critter_count.keySet()) {
			System.out.print(prefix + s + ":" + critter_count.get(s));
			prefix = ", ";
		}
		System.out.println();
	}

	/* the TestCritter class allows some critters to "cheat". If you want to
	 * create tests of your Critter model, you can create subclasses of this class
	 * and then use the setter functions contained here.
	 *
	 * NOTE: you must make sure thath the setter functions work with your implementation
	 * of Critter. That means, if you're recording the positions of your critters
	 * using some sort of external grid or some other data structure in addition
	 * to the x_coord and y_coord functions, then you MUST update these setter functions
	 * so that they correctup update your grid/data structure.
	 */
	static abstract class TestCritter extends Critter {
		protected void setEnergy(int new_energy_value) {
			super.energy = new_energy_value;
		}

		protected void setX_coord(int new_x_coord) {
			super.x_coord = new_x_coord;
		}

		protected void setY_coord(int new_y_coord) {
			super.y_coord = new_y_coord;
		}

		protected int getX_coord() {
			return super.x_coord;
		}

		protected int getY_coord() {
			return super.y_coord;
		}


		/*
		 * This method getPopulation has to be modified by you if you are not using the population
		 * ArrayList that has been provided in the starter code.  In any case, it has to be
		 * implemented for grading tests to work.
		 */
		protected static List<Critter> getPopulation() {
			return population;
		}

		/*
		 * This method getBabies has to be modified by you if you are not using the babies
		 * ArrayList that has been provided in the starter code.  In any case, it has to be
		 * implemented for grading tests to work.  Babies should be added to the general population
		 * at either the beginning OR the end of every timestep.
		 */
		protected static List<Critter> getBabies() {
			return babies;
		}
	}

	/**
	 * Clear the world of all critters, dead and alive
	 */
	//done
	public static void clearWorld() {
		for(Critter i: population){
			population.remove(i);
		}
		for(Critter j: babies){
			babies.remove(j);
		}
		population.clear();
		babies.clear();

	}


}