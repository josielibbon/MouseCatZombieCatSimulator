# OOP Design Document

[Update the path below so your UML diagram appears in the rendered markdown, once you do, remove this text!]

![](https://github.com/cs2113-f21/project-2-josielibbon/blob/main/UML.png)

## Overview

The Mice.java, zombiecats.java, Cats.java and extra.java classes inherit from the Creature.java class, because they all need their generic methods such as getGridPoint() and dist(), but also need more specific methods that act in accordance with the different creatures. GridPoint's functionality is realized in City.java and inherits from Object.java. City.java's functionality is then realized in Simulator.java when the simulate method is called.

[For each of the classes in your OOP design, including ones provided
for you, offer a brief description of their functionality and how they
interact. Be sure to highlight good OOP like encapsulation,
inheritance, and polymorphism. Once you do, remove this text! Also be
sure the headers below match the class names, and not Class 1 and
Class 2 and etc.]

## Creature.java

This class has the general methods that we want to work for all creatures, no matter what type they are. These methods include the constructor for Creatures, getRow(), getCol(), getGridPoint(), dist() and toString(). The class also holds protected and private variables such as the direction, label and point in the grid that the creature holds.

## Mice.java

This class is specifically for the Mice creatures in the city. It inherits from Creature.java, and also includes more methods that include commands that are specific to the Mice such as takeAction() and die(). Other methods in this class include the constructor for Mice, maybeTurn(), and step().

## Cats.java

This class is specifically for the Cats creatures in the city. It inherits from Creature.java, and also includes more methods that include commands that are specific to the Cats such as takeAction() and die(). Other methods in this class include the constructor for Cats, maybeTurn(), and step().

## zombiecats.java

This class is specifically for the zombie cat creatures in the city. It inherits from Creature.java, and also includes more methods that include commands that are specific to the zombie cats such as takeAction() and die(). Other methods in this class include the constructor for zombie cats, maybeTurn(), and step().

## extra.java

This class is specifically for the extra creatures in the city. It inherits from Creature.java, and also includes more methods that include commands that are specific to the extra such as takeAction() and die(). Other methods in this class include the constructor for extra creatures, maybeTurn(), and step().

## City.java

This class deals more with the managing of the number of different creatures in the city. It's functionality is realized in the Creature.java class and its descendant classes (mice, cats, zombie cats and extra). It includes methods such as the City constructor, numCreatures(), queueRMCreatures(), queueAddCreature(), etc. I also added a zombify() method in the UML Diagram that I think I may need to use to more efficiently remove a cat from the city and add a zombie cat in its place.

## GridPoint.java

This class deals completely with the grid the creatures move around on. It inherits from the Object class, and its functionality is realized in the City.java class. It includes methods such as the GridPoint constructor, equals(), hashCode(), toString() and dist().

## Simulator.java

This class deals with running the actual program, as it is called from the command line. It takes in the arguments for the simulation, including the number of initial mice, cats and zombie cats.

