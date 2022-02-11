import java.util.*;

public class City{


    //Determine the City Grid based on the size of the Plotter
    public static final int WIDTH = 80;
    public static final int HEIGHT = 80;

    //Different names, same result
    public static final int MAX_COL = WIDTH;
    public static final int MAX_ROW = HEIGHT;

    
    // The Grid World for your reference
    //
    //        (x)
    //        columns
    //        0 1 2 3 4 5 ... WIDTH (MAX_COL)
    //       .----------------...
    // (y)r 0|           ,--column (x)
    //    o 1|      * (1,3) 
    //    w 2|         ^    
    //      3|         '-row (y)
    //      .|
    //      .|
    //      .|       
    //HEIGHT :
    //MAX_ROW:
    //


    //IMPORTANT! The grid world is a torus. Whenn a a point goes off
    //an edge, it wrapps around to the other side. So with a width of
    //of 80, a point at (79,5) would move to (0,5) next if it moved
    //one space down. Similarly, with a height of 80, a point
    //at (5,0) would move to (5,79) if it moved one space left.


    //-------------------------------------
    //The simulation's Data Structures
    //
   // private List<Creature> creatures; //list of all creatues
    private List<Creature> creatures = new ArrayList<Creature>(); //list of all creatures
    private List<Creature> allMice = new ArrayList<Creature>(); //list of all mice
    private List<Creature> allCats = new ArrayList<Creature>(); //list of all cats
    private List<Creature> cmList = new ArrayList<Creature>(); //list of mice and cats
    private List<Creature> zList = new ArrayList<Creature>(); //list of all zombiecats
    //Map of GridPoint to a list of cratures whose location is that grid point 
    private HashMap<GridPoint,List<Creature>> creatureGrid = new HashMap<GridPoint,List<Creature>>(); 
    
    private Queue<Creature> rmQueue = new LinkedList<Creature>(); //creatures that are staged for removal
    private Queue<Creature> addQueue = new LinkedList<Creature>(); //creatures that are staged to be added   
    private Queue<Creature> rmQueueMice = new LinkedList<Creature>(); //mice that are staged for removal
    private Queue<Creature> addQueueMice = new LinkedList<Creature>(); //mice that are staged to be added   
    private Queue<Creature> rmQueueCats = new LinkedList<Creature>(); //cats that are staged for removal
    private Queue<Creature> addQueueCats = new LinkedList<Creature>(); //cats that are staged to be added   
    private Queue<Creature> rmQueueCM = new LinkedList<Creature>(); //cats and mice that are staged for removal
    private Queue<Creature> addQueueCM = new LinkedList<Creature>(); //cats and mice that are staged to be added   
    private Queue<Creature> rmQueueZ = new LinkedList<Creature>(); //zombiecats that are staged for removal
    private Queue<Creature> addQueueZ = new LinkedList<Creature>(); //zombiecats that are staged to be added   

    //... YES! you must use all of these collections.
    //... YES! you may add others if you need, but you MUST use these too!
    

    //Random instance
    private Random rand;

    //Note, for Level 4, you may need to change this constructors arguments.
    public City(Random rand, int numMice, int numCats, int numZombieCats, int numZombieSlayers) {
        this.rand = rand;

        //TODO complete this constructor
        //add initial mice to city
        for(int i = 0; i < numMice; i++){
            Creature mice = new Mice(rand.nextInt(HEIGHT), rand.nextInt(WIDTH), this, rand); //create thing of type b
            creatures.add(mice);
            allMice.add(mice);
            cmList.add(mice);
        }
        //add initial cats to city
        for(int i = 0; i < numCats; i++){
            Creature cats = new Cats(rand.nextInt(HEIGHT), rand.nextInt(WIDTH), this, rand); //create thing of type b
            creatures.add(cats);
            allCats.add(cats);
            cmList.add(cats);
        } 
        
        //add initial zombiecats to city
        for(int i = 0; i < numZombieCats; i++){
            Creature zombiecats = new ZombieCats(rand.nextInt(HEIGHT), rand.nextInt(WIDTH), this, rand); //create thing of type b
            creatures.add(zombiecats);
            zList.add(zombiecats);
        } 

        //add initial zombieslayers to city
        for(int i = 0; i < numZombieSlayers; i++){
            Creature zombieslayers = new ZombieSlayer(rand.nextInt(HEIGHT), rand.nextInt(WIDTH), this, rand); //create thing of type b
            creatures.add(zombieslayers);
        } 
        
    }


    //Return the current number of creatures in the simulation
    public int numCreatures(){
        return creatures.size();
    }

    
    // Because we'll be iterating of the Creature List we can't remove
    // items from the list until after that iteration is
    // complete. Instead, we will queue (or stage) removals and
    // additions.
    //
    // I gave yout the two methods for adding, but you'll need to
    // implementing the clearing.

    //stage a create to be removed
    public void queueRmCreature(Creature c){
        //DO NOT EDIT
        rmQueue.add(c);
    }

    //Stage a creature to be added
    public void queueAddCreature(Creature c){
        //DO NOT EDIT
        addQueue.add(c);
    }
    
    //Clear the queue of creatures staged for removal and addition
    public void clearQueue(){
        //TODO

        //Clear the queues by either adding creatures to the
        //simulation or removing them.

        for (Creature addme: addQueue) {
            creatures.add(addme);
        }

        for (Creature rmme: rmQueue) {
            creatures.remove(rmme);
        }

        addQueue.clear();
        rmQueue.clear();

        for (Creature addmice: addQueueMice) {
            allMice.add(addmice);
        }

        for (Creature rmmice: rmQueueMice) {
            allMice.remove(rmmice);
        }

        addQueueMice.clear();
        rmQueueMice.clear();

        for (Creature addcats: addQueueCats) {
            allCats.add(addcats);
        }

        for (Creature rmcats: rmQueueCats) {
            allCats.remove(rmcats);
        }

        addQueueCats.clear();
        rmQueueCats.clear();

        for (Creature addcm: addQueueCM) {
            cmList.add(addcm);
        }

        for (Creature rmcm: rmQueueCM) {
            cmList.remove(rmcm);
        }

        addQueueCM.clear();
        rmQueueCM.clear();

        for (Creature addz: addQueueZ) {
            zList.add(addz);
        }

        for (Creature rmz: rmQueueZ) {
            zList.remove(rmz);
        }

        addQueueZ.clear();
        rmQueueZ.clear();

        //update creatureGrid

        creatureGrid.clear();

        for(Creature c: creatures){
            GridPoint point = c.getPoint();
            if(creatureGrid.containsKey(point)){
                List<Creature> creatureGridList = creatureGrid.get(point);
                creatureGridList.add(c); //add the creature to the list
                creatureGrid.remove(point); //remove the point/list from the hashmap
                creatureGrid.put(point, creatureGridList); //add the new point/list back into the hashmap
            } 
            else{
                List<Creature> creatureGridList = new ArrayList<Creature>();
                creatureGridList.add(c);
                creatureGrid.put(point, creatureGridList);
            }

        }

    }


    //queues for mice

    //stage a mouse to be removed
    public void queueRmMice(Creature c){
        //DO NOT EDIT
        rmQueueMice.add(c);
    }

    //Stage a mouse to be added
    public void queueAddMice(Creature c){
        //DO NOT EDIT
        addQueueMice.add(c);
    }

    //stage a cat to be removed
    public void queueRmCats(Creature c){
        //DO NOT EDIT
        rmQueueCats.add(c);
    }

    //Stage a cat to be added
    public void queueAddCats(Creature c){
        //DO NOT EDIT
        addQueueCats.add(c);
    }

    //stage a cat/mouse to be removed
    public void queueRmCM(Creature c){
        //DO NOT EDIT
        rmQueueCM.add(c);
    }

    //Stage a cat/mouse to be added
    public void queueAddCM(Creature c){
        //DO NOT EDIT
        addQueueCM.add(c);
    }
    
    //stage a zombiecat to be removed
    public void queueRmZ(Creature c){
        //DO NOT EDIT
        rmQueueZ.add(c);
    }

    //Stage a zombiecat to be added
    public void queueAddZ(Creature c){
        //DO NOT EDIT
        addQueueZ.add(c);
    }
   


    //TODO -- there are a number of other member methods you'll want
    //to write here to interact with creatures. This is a good thing
    //to think about when putting together your UML diagram

    //get city
    public City getCity(){
        return this;
    }
    
    //get creature list
    public List<Creature> getCreatureList(){
        return creatures;
    }

    //set creature list
    public List<Creature> setCreatureList(List<Creature> newlist){
        this.creatures = newlist;
        return this.creatures;
    }

    //get creature grid
    public HashMap<GridPoint,List<Creature>> getCreatureGrid(){
        return creatureGrid;
    }

    //set creature grid
    public HashMap<GridPoint,List<Creature>> setCreatureGrid(HashMap<GridPoint,List<Creature>> newgrid){
        this.creatureGrid = newgrid;
        return this.creatureGrid;
    }

    //get mice list
    public List<Creature> getMiceList(){
        return allMice;
    }

    //set mice list
    public List<Creature> setMiceList(List<Creature> newlist){
        this.allMice = newlist;
        return this.allMice;
    }

    //get cats list
    public List<Creature> getCatsList(){
        return allCats;
    }

    //set cats list
    public List<Creature> setCatsList(List<Creature> newlist){
        this.allCats = newlist;
        return this.allCats;
    }

    //get cat/mouse list
    public List<Creature> getCMList(){
        return cmList;
    }

    //set cat/mouse list
    public List<Creature> setCMList(List<Creature> newlist){
        this.cmList = newlist;
        return this.cmList;
    }

    //get zombiecat list
    public List<Creature> getZList(){
        return zList;
    }

    //set zombiecat list
    public List<Creature> setZList(List<Creature> newlist){
        this.zList = newlist;
        return this.zList;
    }

    // This is the simulate method that is called in Simulator.java
    // 
    //You need to realize in your Creature class (and decendents) this
    //functionality so that they work properly. Read through these
    //comments so it's clear you understand.
    public void simulate() {
        //DO NOT EDIT!
        
        //You get this one for free, but you need to review this to
        //understand how to implement your various creatures

        //First, for all creatures ...
        for(Creature c : creatures){
            //check to see if any creature should die
            if(c.die()){
                queueRmCreature(c); //stage that creature for removal
                continue;
            }
            
            //for all remaining creatures take a step
            //this could involve chasing another creature
            //or running away from a creature
            c.step();
        }

        //Clear queue of any removes or adds of creatures due to creature death
        clearQueue(); 

        
        //For every creature determine if an action should be taken
        // such as, procreating (mice), eating (cats, zombiecats), or
        // some new action that you'll add to the system.
        for(Creature c : creatures){
            c.takeAction(); 
        }

        //Clear queue of any removes or adds following actions, such
        //as a mouse that was eaten or a cat that was was removed due
        //to being turned into a zombie cat.
        clearQueue();

        //Output all the locations of the creatures.
        for(Creature c : creatures){
            System.out.println(c);
        }

    }
}
