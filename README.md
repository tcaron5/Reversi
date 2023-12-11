# Reversi Game

## Overview

Welcome to the Reversi game project! This code provides the interface and abstraction
for a Reversi game. Extending it is a Reversi implementation with a hexagon-shaped playing board.
Our Reversi game was designed with the intent of being easily extended
and customized to meet various gameplay and feature requirements. This 
includes the ability to easily change the board size and the shape of the board without
changing any game logic.

## Quick Start For Part 1

To get started with this project, follow these simple steps:

1. Download the project files to your local machine.
2. Review the project structure and key components.
3. Explore the codebase to see how the game logic, board representation, and player interactions are implemented.
4. Refer to the `HexagonReversiTests` class for examples of testing various aspects of the game.

An example of starting a game and playing a move is shown here: <br>

public void startSimpleGame() { <br>
&nbsp;&nbsp; game.startGame(3, 2); <br>
&nbsp;&nbsp; game.flipCell(new CubicCoordinate(2, -1, -1), 1); <br>
}
<br>
<br>

This results in the current game board looking like this:

&nbsp;&nbsp;&nbsp;&nbsp;_ _ _ <br>
&nbsp;&nbsp;_ X X X <br>
&nbsp;_ O _ X _ <br>
&nbsp;&nbsp;_ X O _ <br>
&nbsp;&nbsp;&nbsp;&nbsp;_ _ _


## Key Components

In this project, we have several key components:

1. **Model**: Represents the game board, its contents, and enforces the rules of the game. This includes managing the game state, validating moves, and determining the winner.

2. **View**: While a GUI is currently not implemented in this assignment, we have implemented a textual view for a simpler representation of the game board. It will use '_' for empty cells, 'X' for one player, and 'O' for the other player.

3. **Controller**: Currently, we do not implement a controller. However, we will design one in future assignments to manage user interactions and gameplay.

4. **Players**: We envision a player interface that allows both human and AI players to interact with the model.

## Key Subcomponents

Within each of the key components, the following subcomponents and features are included:

**Model:**
- AbstractReversi: contains majority of game logic that we believe should be shared in between
  Reversi implementations. We chose the createBoard method to be abstract because we expect
  implementations to want to create their board in their own ways. And if clients need to override
  other helper game logic such as isValidMove, they can do so without having to override
  logic in the public methods.
    - Drives the control flow: when a game is started, all fields are instantiated and set, and a board is created for the game.
    - Driven by the control flow: flipCell and passMove are called by the controller, and will change the state of the game
      if the move is valid.
- HexagonReversi: extends AbstractReversi and implements the createBoard method to create a hexagon-shaped board.
- BasicCell: represents a cell on the game board. It can be empty, or contain a player's piece.
- CubicCoordinate: represents the coordinates of a cell on the game board.
- BasicPlayer: Currently not implemented.
  
**View:**
- GUI: displays the game board with graphics.
- TextualView: displays the game board in a textual format.


**Controller:**
- Currently not implemented.


## Source Organization

To navigate, here's a brief guide to where you can find different components and modules within the codebase:

- **Model**: The main logic for the game, including the game board representation, move validation, and game state management, can be found in the model package. Includes all game objects.

- **View**: The textual view implementation for representing the game board can be found in the view package.

- **Controller**: While not implemented in this assignment, the controller will be found in the controller package in future assignments.




## Changes for Part 2

### Refactoring Models
1. Introduced `ReadonlyReversiModel` interface to separate read-only and mutable functionalities.
2. We included the following methods to the `ReadonlyReversiModel` interface: isMoveValid, getCurrentTurn, getAvailableMoves, clone (these functionalities already existed but were refactored into the interface)


### Visualizing the Game
1. Implemented a GUI using Java Swing.
2. Developed a constructor for the view that takes a `ReadonlyReversiModel` to ensure immutability.
3. Implemented basic rendering with the ability to handle mouse clicks and keyboard input for moves and passes.
4. Created a placeholder class `Reversi` for testing the view.

### Testing the View
Three screenshots of the view:
1. A screenshot depicting the start of a game.
   ![startOfGame.png](screenshotsOfGUI%2FstartOfGame.png)
2. A screenshot depicting an intermediate point in the game.
   ![intermediatePointIngame.png](screenshotsOfGUI%2FintermediatePointIngame.png)
3. A screenshot depicting a cell being selected.
   ![cellSelected.png](screenshotsOfGUI%2FcellSelected.png)

### Strategic Computer Players
1. Implemented a strategy that randomly selects a move.
2. Implemented a basic computer player strategy focusing on capturing as many pieces as possible each turn.
3. Implemented a strategy that focuses on avoiding the neighboring cells of corners.
4. Implemented a strategy that focuses on capturing and moving towards corners.
5. Implemented a strategy that minimizes the maximum move the opponent can make.

### Testing Strategies
Utilized mocks for model testing, recording transcripts of inspected coordinates.

## Changes for Part 3
New Interfaces:
ControllerFeatures - Allows for a controller to be a listener of a view. The actions (features) guaranteed for a controller in the game of Reversi.
ObserverInterface - Allowing one to be a listener of a game model. This is the interface for an observer of the Reversi model.
New Classes:
ReversiController - this is the controller for our game of Reversi that subscribes itself as a listener to the model and view, in order for a player to play the game.
It handles exceptions by displaying an alert to the player. It can be given either a human player or computer player. Its behaviors are defined by the new feature interfaces.

MockController - used to test that the controller listener behavior works as expected
MockReversiObservation - used to test that the model notifying behavior works as expected

Updating main - creates two views, one for each player.

## Part 4

We were able to get all of the required features of the provided view working.
They did include code for the hints in the next assignment, so we did not implement that feature.
Additionally, our providers never included a highlighting feature in their code, so we were not able to include that in their view. 

