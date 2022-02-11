import java.util.Random;
import java.util.*;

public class ZombieCats extends Creature {

    //count for dying
    public int zcCount = 0;

    //constructor
    public ZombieCats(int r, int c, City cty, Random rnd){
        
        super(r, c, cty, rnd);
        this.lab = 'r';

    }

    //for chasing
    public void chase(Creature c){
        
        int zcRow = getRow();
        int zcColumn = getCol();
        GridPoint zcPoint = getPoint();
        City city = getCity();
        GridPoint cPoint = c.getPoint();

        GridPoint southPoint = new GridPoint(zcRow + 3, zcColumn);
        GridPoint northPoint = new GridPoint(zcRow - 3, zcColumn);
        GridPoint westPoint = new GridPoint(zcRow, zcColumn - 3);
        GridPoint eastPoint = new GridPoint(zcRow, zcColumn + 3);

        //find which way will decrease distance between zombiecat and creature and move
        if(southPoint.dist(cPoint) < zcPoint.dist(cPoint)){
            this.setGridPoint(zcRow + 3, zcColumn);
        }

        if(northPoint.dist(cPoint) < zcPoint.dist(cPoint)){
            this.setGridPoint(zcRow - 3, zcColumn);
        }

        if(westPoint.dist(cPoint) < zcPoint.dist(cPoint)){
            this.setGridPoint(zcRow, zcColumn - 3);
        }

        if(eastPoint.dist(cPoint) < zcPoint.dist(cPoint)){
            this.setGridPoint(zcRow, zcColumn + 3);
        }

    

    }

     //find the closest mouse/cat
    public Creature findClosest(){

        //make list of all cats and mice within 40 points
        List<Creature> cmList = city.getCMList(); //make linked list for things
        List<Creature> in40 = new ArrayList<Creature>();
        for(Creature c: cmList){
            GridPoint cPoint = c.getGridPoint();
            GridPoint zcGridPoint = this.getGridPoint();
            if(zcGridPoint.dist(cPoint) <= 40){
                in40.add(c);
            }
        }

        //find closest creature in the list and return it
        Creature closest = in40.get(0);
        for(Creature c: in40){
            GridPoint closestGridPoint = closest.getGridPoint();
            GridPoint cGridPoint = c.getGridPoint();
            GridPoint zcGridPoint = this.getGridPoint();
            if(zcGridPoint.dist(cGridPoint) < zcGridPoint.dist(closestGridPoint)){
                closest = c;
            }
        }
        return closest;
    }

    //determine if any zombiecats are within 40 points
    public int maybeChase(){

        //get list of all mice/cats within 50 points
        List<Creature> cmList = city.getCMList(); 
        List<Creature> in40 = new ArrayList<Creature>();
        in40.clear();
        for(Creature c: cmList){
            GridPoint cPoint = c.getGridPoint();
            GridPoint zcGridPoint = this.getGridPoint();
            if(zcGridPoint.dist(cPoint) <= 40){
                in40.add(c);
            }
        }

        //if the list is empty return 0, else return 1
        if(in40.isEmpty()){
            return 0;
        }
        else{
            return 1;
        }
    }

     public void step(){ //take a step in the given direction in the city
        int row = getRow();
        int column = getCol();
        int direction = getDir();
        GridPoint point = getPoint();
        City city = getCity();

        //determine if it should chase, if so find the closest mouse/cat and chase it
        if(maybeChase() == 1){
            this.lab = 'k';
            Creature c = findClosest();
            chase(c);
            return;
        }

        this.lab = 'r'; //set color

        row += zombiedirRow[direction];
        column += zombiedirCol[direction];

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
    public void maybeTurn(){ 
        int i = rand.nextInt(3);
         if (i == 1) {
            rightTurn();
        }
        
        if (i == 2) {
            leftTurn();
        }
    } 


    //eat cat/mouse
    public void takeAction(){ 

        zcCount++;


        List<Creature> miceList = city.getMiceList(); //get list of mice
        for(Creature c : miceList){ //iterate through list 
            GridPoint miceGridPoint = c.getGridPoint();
            GridPoint zcGridPoint = this.getGridPoint();
            if(zcGridPoint.equals(miceGridPoint) == true){ //if a mouse is on the same point as the zombiecat, eat it
                city.queueRmCreature(c);
                city.queueRmMice(c);
                city.queueRmCM(c);
                zcCount = 0;
            }
        }

        List<Creature> catList = city.getCatsList(); //get list of cats
        for(Creature c : catList){ //iterate through list 
            GridPoint catGridPoint = c.getGridPoint();
            GridPoint zcGridPoint = this.getGridPoint();
            if(zcGridPoint.equals(catGridPoint) == true){ //if a cat is on the same point as the zombiecat, turn it into a zombie/eat it
                city.queueRmCreature(c);
                city.queueRmCats(c);
                city.queueRmCM(c);
                zcCount = 0;
            }
        }

    } 

    public boolean die(){ //return true if should die, false otherwise
    
        //die if count reaches 100 rounds
        if(zcCount == 100){
            this.city.queueRmZ(this);
            return true;
        }

        return false;
    } 



}