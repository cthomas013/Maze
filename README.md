This repository contains code, written in Java, for a maze application game. The game allows the user to choose a level ranging from 0 to f,
with 0 being the easiest and f being the hardest. The game also allows the user to input what type of random generation algorithm he or 
she would like the program to use to randomly generate a maze with the given level. After the user has given all of this information the game
will generate the specified maze and allow the user to try to solve the maze. A zoomed out view of the entire maze showing both the walls and the
best solution to solve the maze can be portrayed on the screen if the user decides to do this. If not, the game will allow the user to play until 
either he or she quits or he or she reaches the exit of the maze and subsequently wins the game. If the user does not want to play the maze,
he or she can call for one of two different robot drivers that will work through the maze. The first type of driver is the wallfollower, which will 
follow the wall on its right to go through and solve the maze. Unfortunately, the robot only has a limited amount of energy and each movement 
consumes a given amount of energy, so the robot may run out of energy and stop before reaching the exit on some of the higher level mazes.
The second type of robot driver is the wizard which will follow the solution path to solve the maze. Because it follows the solution, the wizard
should be able to solve any maze without running out of energy.

I hope you enjoy playing my maze game!
