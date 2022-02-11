import java.util.Random;
import java.util.*;

public class Mice extends Creature {

    //count for dying/multiplying
    public int count = 0;

    //contructor
    public Mice(int r, int c, City cty, Random rnd){
        
        super(r, c, cty, rnd);
        this.lab = 'b';

    }

    //take a step
     public void step(){ 


        int row = getRow();
        int column = getCol();
        int direction = getDir();
        GridPoint point = getPoint();
        City city = getCity();
        GridPoint oldpoint = point;


        row += dirRow[direction];
        column += dirCol[direction];

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

        //decides turning
        int i = rand.nextInt(101);
        if(i < 21){
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

    //which way to turn
    public void maybeTurn(){ //randomnly turn if suppose to
        int i = rand.nextInt(3);
         if (i == 1) {
            rightTurn();
        }
        
        if (i == 2) {
            leftTurn();
        }
    } 


    //procreate
    public void takeAction(){ 

        count++;

        if(count == 20){
            int row = getRow();
            int column = getCol();
            int direction = getDir();
            GridPoint point = getPoint();
            City city = getCity();

            //make new mouse and add to appropriate lists
            Creature mice = new Mice(row, column, city, rand); 
            this.city.queueAddCreature(mice);
            this.city.queueAddMice(mice);
            this.city.queueAddCM(mice);
        }

    } 

    public boolean die(){ //return true if should die, false otherwise
    
        //dies when count gets to thirty, removes from appropriate lists
        if(count == 30){
            this.city.queueRmMice(this);
            this.city.queueRmCM(this);
            return true;
        }

        return false;
    } 



}