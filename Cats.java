import java.util.Random;
import java.util.*;

public class Cats extends Creature {

    //for dying
    public int catCount = 0;

    //constructor
    public Cats(int r, int c, City cty, Random rnd){

        super(r, c, cty, rnd);
        this.lab = 'y';
    }

     //right turn
    public void rightTurn() { 
        int dir = getDir();
        dir = (dir + 1) % 4;
        setDir(dir);
    }
    
    //left turn
    public void leftTurn() { 
        int dir = getDir();
        dir = (dir + 3) % 4;
        setDir(dir);
    }

    //which way for turning
    public void maybeTurn(){ //randomnly turn if suppose to
        int i = rand.nextInt(3);
         if (i == 1) {
            rightTurn();
        }
        
        if (i == 2) {
            leftTurn();
        }
    } 

    //eat
    public void takeAction(){ 
        List<Creature> miceList = city.getMiceList(); //make linked list for things
        for(Creature c : miceList){ //iterate through list 
            GridPoint otherGridPoint = c.getGridPoint();
            GridPoint catGridPoint = this.getGridPoint();
            if(catGridPoint.equals(otherGridPoint) == true){ //if a mouse is on the same point as the zombiecat, eat it
                city.queueRmCreature(c);
                city.queueRmMice(c);
                city.queueRmCM(c);
                catCount = 0;
            }
        }
    } 

    public boolean die(){ //return true if should die, false otherwise

        int catRow = getRow();
        int catColumn = getCol();
        City city = getCity();

        //turn into a zombiecat if count reaches 50
        if(catCount == 50){
            this.city.queueRmCats(this);
            this.city.queueRmCM(this);
            Creature zombieCat = new ZombieCats(catRow, catColumn, city, rand); //create thing of type b
            this.city.queueAddCreature(zombieCat);
            this.city.queueAddZ(zombieCat);
            return true;
        }


        return false;
    } 

    //for chasing
    public void chase(Creature c){

        this.lab = 'c'; //set color
        
        int catRow = getRow();
        int catColumn = getCol();
        int catDirection = getDir();
        GridPoint catPoint = getPoint();
        City city = getCity();
        int mouseRow = c.getRow();
        int mouseColumn = c.getCol();
        int mouseDirection = c.getDir();
        GridPoint mousePoint = c.getPoint();

        GridPoint southPoint = new GridPoint(catRow + 2, catColumn);
        GridPoint northPoint = new GridPoint(catRow - 2, catColumn);
        GridPoint westPoint = new GridPoint(catRow, catColumn - 2);
        GridPoint eastPoint = new GridPoint(catRow, catColumn + 2);

        //find which way will decrease distance between cat and mouse and move
        if(southPoint.dist(mousePoint) < catPoint.dist(mousePoint)){
            this.setGridPoint(catRow + 2, catColumn);
        }

        if(northPoint.dist(mousePoint) < catPoint.dist(mousePoint)){
            this.setGridPoint(catRow - 2, catColumn);
        }

        if(westPoint.dist(mousePoint) < catPoint.dist(mousePoint)){
            this.setGridPoint(catRow, catColumn - 2);
        }

        if(eastPoint.dist(mousePoint) < catPoint.dist(mousePoint)){
            this.setGridPoint(catRow, catColumn + 2);
        }

    }

    //find the closest mouse/cat
    public Creature findClosest(){

        //make list of all mice within 20 points
        List<Creature> miceList = city.getMiceList(); //make linked list for things
        List<Creature> in20 = new ArrayList<Creature>();
        for(Creature c: miceList){
            GridPoint cPoint = c.getGridPoint();
            GridPoint catGridPoint = this.getGridPoint();
            if(catGridPoint.dist(cPoint) <= 20){
                in20.add(c);
            }
        }

        //find closest creature in the list and return it
        Creature closest = in20.get(0);
        for(Creature c: in20){
            GridPoint closestGridPoint = closest.getGridPoint();
            GridPoint cGridPoint = c.getGridPoint();
            GridPoint catGridPoint = this.getGridPoint();
            if(catGridPoint.dist(cGridPoint) < catGridPoint.dist(closestGridPoint)){
                closest = c;
            }
        }
        return closest;
    }

    //determine if any mice are within 20 points
    public int maybeChase(){

        //get list of all mice within 20 points
        List<Creature> miceList = city.getMiceList(); 
        List<Creature> in20 = new ArrayList<Creature>();
        for(Creature c: miceList){
            GridPoint cPoint = c.getGridPoint();
            GridPoint catGridPoint = this.getGridPoint();
            if(catGridPoint.dist(cPoint) <= 20){
                in20.add(c);
            }
        }

        //if the list is empty return 0, else return 1
        if(in20.isEmpty()){
            return 0;
        }
        else{
            return 1;
        }
    }
    
    public void step(){ //take a step in the given direction in the city

        catCount++;

    
        int row = getRow();
        int column = getCol();
        int direction = getDir();
        GridPoint point = getPoint();
        City city = getCity();

        //determine if it should chase, if so find the closest mouse and chase it
        if(maybeChase() == 1){
            Creature c = findClosest();
            chase(c);
            return;
        }
       
        this.lab = 'y'; //set color
       
        row += catdirRow[direction];
        column += catdirCol[direction];

        //takes care of edge issues
        if(row > 79){
            row = 0;
        }
        if(row < 0){
            row = 79;
        }
        if(column > 79){
            column = 0;
        }
        if(column < 0){
            column = 79;
        }

        //set new point
        this.setGridPoint(row, column);

        //handles turning
        int i = rand.nextInt(101);
        if(i < 6){
            maybeTurn();
        }

        

    } 

  

}