import java.util.Random;
import java.util.*;

public class ZombieSlayer extends Creature {

    //for dying
    public int sCount = 0;

    //contructor
    public ZombieSlayer(int r, int c, City cty, Random rnd){

        super(r, c, cty, rnd);
        this.lab = 'p';
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

    //kill zombiecats
    public void takeAction(){ 
        List<Creature> zList = city.getZList(); //get list of zombiecats
        for(Creature c : zList){ //iterate through list 
            GridPoint otherGridPoint = c.getGridPoint();
            GridPoint sGridPoint = this.getGridPoint();
            if(sGridPoint.equals(otherGridPoint) == true){  //if a zombicat is on the same point as the zombieslayer, kill it
                city.queueRmCreature(c);
                city.queueRmZ(c);
            }
        }
    } 

    public boolean die(){ //return true if should die, false otherwise

        //die when count reaches 70
        if(sCount == 70){
            return true;
        }

        return false;
    } 

    public void chase(Creature c){

        this.lab = 'm'; //set color
        
        int sRow = getRow();
        int sColumn = getCol();
        GridPoint sPoint = getPoint();
        City city = getCity();
        int zRow = c.getRow();
        int zColumn = c.getCol();
        GridPoint zPoint = c.getPoint();

        GridPoint southPoint = new GridPoint(sRow + 4, sColumn);
        GridPoint northPoint = new GridPoint(sRow - 4, sColumn);
        GridPoint westPoint = new GridPoint(sRow, sColumn - 4);
        GridPoint eastPoint = new GridPoint(sRow, sColumn + 4);

        //find which way will decrease distance between zombieslayer and zombiecat and move
        if(southPoint.dist(zPoint) < sPoint.dist(zPoint)){
            this.setGridPoint(sRow + 4, sColumn);
        }

        if(northPoint.dist(zPoint) < sPoint.dist(zPoint)){
            this.setGridPoint(sRow - 4, sColumn);
        }

        if(westPoint.dist(zPoint) < sPoint.dist(zPoint)){
            this.setGridPoint(sRow, sColumn - 4);
        }

        if(eastPoint.dist(zPoint) < sPoint.dist(zPoint)){
            this.setGridPoint(sRow, sColumn + 4);
        }

    }

    //find the closest zombiecat
    public Creature findClosest(){

        //make list of all zombiecats within 50 points
        List<Creature> zList = city.getZList();
        List<Creature> in50 = new ArrayList<Creature>();
        for(Creature c: zList){
            GridPoint cPoint = c.getGridPoint();
            GridPoint sGridPoint = this.getGridPoint();
            if(sGridPoint.dist(cPoint) <= 50){
                in50.add(c);
            }
        }

        //find closest zombiecat in the list and return it
        Creature closest = in50.get(0);
        for(Creature c: in50){
            GridPoint closestGridPoint = closest.getGridPoint();
            GridPoint cGridPoint = c.getGridPoint();
            GridPoint sGridPoint = this.getGridPoint();
            if(sGridPoint.dist(cGridPoint) < sGridPoint.dist(closestGridPoint)){
                closest = c;
            }
        }
        return closest;
    }

    //determine if any zombiecats are within 50 points
    public int maybeChase(){

        //get list of all zombiecats within 50 points
        List<Creature> zList = city.getZList();
        List<Creature> in50 = new ArrayList<Creature>();
        for(Creature c: zList){
            GridPoint cPoint = c.getGridPoint();
            GridPoint sGridPoint = this.getGridPoint();
            if(sGridPoint.dist(cPoint) <= 50){
                in50.add(c);
            }
        }

        //if the list is empty return 0, else return 1
        if(in50.isEmpty()){
            return 0;
        }
        else{
            return 1;
        }
    }
    
    public void step(){ //take a step in the given direction in the city

        sCount++;

        int row = getRow();
        int column = getCol();
        int direction = getDir();
        GridPoint point = getPoint();
        City city = getCity();

        //determine if it should chase, if so find the closest zombiecat and chase it
        if(maybeChase() == 1){
            Creature c = findClosest();
            chase(c);
            return;
        }
       
        this.lab = 'p'; //set color

        //move normally
       
        row += sdirRow[direction];
        column += sdirCol[direction];

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
        if(i < 21){
            maybeTurn();
        }

        

    } 

  

}