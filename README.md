# Project 2


## Help Received

Please document any help you received in completing this lab. Note that the what you submit should be your own work. Refer to the syllabus for more details. 

[ANSWER HERE]

## Describe your work


## Part 1: UML Diagram

Note that you must do two tasks here:

1. Add to your repo a document `UML.png` that is a image of your UML diagram
2. Update the document `OOP-design.md` that describes your OOP design, referencing your document.
3. You will receive feedback on your design in a github issue

For your final submission, please update `UML.png` with the final UML diagram and `OOP-design.md` with your final description. Below describe the major changes you made.

[ANSWER HERE]

## Part 2: Implementation

What level simulation did you achieve

Level : [4] <-- choose one!

If you completed Level 4, describe the additional creature you added to the simulation.

I created a ZombieSlayer, whose purpose is to kill the ZombieCats. The ZombieSlayer is pink when not chasing ZombieCats, and red when it is ZombieCats. It chases ZombieCats, and kills them when they are on the same gridpoint. It jumps 4 steps at a time, and dies every 70 rounds. A new ZombieSlayer comes in to the simulation every 200 rounds. It can search up to 50 gridpoints at a time. The ZombieSlayer class extends from the Creature class.

