import java.util.Random;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

public class Simulator {
	
    public static void main(String[] args) {

        //This is the arguments for running your program so you can test with different settings
        final String USAGE = "java Simulator numMice numCats numZombieCats numZombieSlayers rounds [randSeed] [--DEBUG]";
        //note that [ ] is to indicate an optional argument. You do not include [ ] when using this argument.
        //When using the --DEBUG flag, you must set a random seed.
        
        boolean DEBUG = false;
        
        //parse arguments
        if(args.length < 5){
            System.out.println("ERROR: missing arguments");
            System.out.println(USAGE);
            System.exit(1);
        }
        int numMice = Integer.parseInt(args[0]);
        int numCats = Integer.parseInt(args[1]);
        int numZombieCats = Integer.parseInt(args[2]);
        int numZombieSlayers = Integer.parseInt(args[3]);
        int rounds = Integer.parseInt(args[4]);

        Random rand;
        if(args.length > 5)
            rand = new Random(Integer.parseInt(args[5]));
        else
            rand = new Random(100);

        if(args.length > 6 && args[6].equals("--DEBUG")){
            DEBUG=true;
        }

        //Initialize a city with mice, cats, and zombie cats
        City city= new City(rand,numMice,numCats,numZombieCats,numZombieSlayers);
        //Note for Level 4 you may need to change your constructors arguments.
        
        int count = 0;


        while (count < rounds) {
            count++;

            //TODO: You'll eventually need to complete functionality
            //for adding mice and cats per round, but start small and
            //build up.

            //add mouse every 100 rounds
            if(count % 100 == 0){
                City cit = city.getCity();
                List<Creature> newcreature = city.getCreatureList(); //make linked list for things
                Creature mice = new Mice(rand.nextInt(80), rand.nextInt(80), cit, rand); //create thing of type b
                newcreature.add(mice);
                city.setCreatureList(newcreature);
                List<Creature> newmice = city.getMiceList(); //make linked list for things
                newmice.add(mice);
                city.setMiceList(newmice);
                List<Creature> newCM = city.getCMList(); //make linked list for things
                newCM.add(mice);
                city.setCMList(newCM);
            }

            //add cat every 25 rounds
            if(count % 25 == 0){
                City cit = city.getCity();
                List<Creature> newcreature = city.getCreatureList(); //make linked list for things
                Creature cat = new Cats(rand.nextInt(80), rand.nextInt(80), cit, rand); //create thing of type b
                newcreature.add(cat);
                city.setCreatureList(newcreature);
                List<Creature> newCM = city.getCMList(); //make linked list for things
                newCM.add(cat);
                city.setCMList(newCM);
            }

            //add zombie slayer every 200 rounds
            if(count % 200 == 0){
                City cit = city.getCity();
                List<Creature> newcreature = city.getCreatureList(); //make linked list for things
                Creature slayer = new ZombieSlayer(rand.nextInt(80), rand.nextInt(80), cit, rand); //create thing of type b
                newcreature.add(slayer);
                city.setCreatureList(newcreature);
            }



            city.simulate();
            System.out.println("done "+count);
            System.out.flush();

            if(DEBUG){
                System.err.print("Enter anything to continue: ");
                try{
                    (new BufferedReader(new InputStreamReader(System.in))).readLine();
                }catch(Exception e){
                    System.exit(1);
                }
            }
            
        }
    }
}
