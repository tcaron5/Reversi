package model;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import controller.ObserverInterface;

/**
 * Abstract class for the Reversi game model.
 * Implements similar behaviors between Reversi implementations.
 */
public abstract class AbstractReversi implements ReversiModel {

  /**
   * INVARIANT: numPasses <= 2.
   * INTERPRETATION: The number of players must be at most 2.
   */

  protected LinkedHashMap<Coordinate, Cell> board;

  protected int sideLength;

  protected int currentPlayerTurn;
  protected boolean gameStarted;
  protected int numPasses;

  protected int numPlayers;

  protected List<ObserverInterface> controllersToNotify; //for notifying/updating each player

  /**
   * Constructs an AbstractReversi object.
   */
  public AbstractReversi() {
    board = new LinkedHashMap<>();
    gameStarted = false;
    controllersToNotify = new ArrayList<>();
  }

  /**
   * Constructor to pass in fields.
   *
   * @param sideLength the side length of the board.
   */
  protected AbstractReversi(LinkedHashMap<Coordinate, Cell> board, int sideLength,
                            int currentPlayerTurn, boolean gameStarted, int numPasses,
                            int numPlayers) {
    this.board = board;
    this.sideLength = sideLength;
    this.currentPlayerTurn = currentPlayerTurn;
    this.gameStarted = gameStarted;
    this.numPasses = numPasses;
    this.numPlayers = numPlayers;
  }

  protected abstract ReversiModel cloneChild(LinkedHashMap<Coordinate,
          Cell> board, int sideLength, int currentPlayerTurn,
                                             boolean gameStarted, int numPasses, int numPlayers);

  @Override
  public ReversiModel clone() {
    return this.cloneChild(board, sideLength,
            currentPlayerTurn, gameStarted, numPasses, numPlayers);
  }


  @Override
  public LinkedHashMap<Coordinate, Cell> getBoard() {

    return new LinkedHashMap<>(board);
  }


  @Override
  public void startGame(int sideLength, int p) {

    // Check if the game has already started
    if (gameStarted) {
      throw new IllegalStateException("The game has already started.");
    }

    //check that the side length is valid
    if (sideLength < 3) {
      throw new IllegalArgumentException("Side length must be greater than 2 for a valid board");
    }

    if (p != 2) {
      throw new IllegalArgumentException("There must be 2 players in the game");
    }

    currentPlayerTurn = 1;
    numPlayers = p;
    //this.playerList = p;
    this.sideLength = sideLength;
    this.createBoard(sideLength);
    numPasses = 0;
    gameStarted = true;
    //controllersToNotify = new ArrayList<>();

    if (!controllersToNotify.isEmpty()) {
      this.notifyObserverTurn();
    }

  }


  //helper: determines if board can be created, then creates board.
  protected abstract void createBoard(int sideLength);


  @Override
  public void flipCell(Coordinate c, int p) {

    if (p != currentPlayerTurn) {
      throw new IllegalStateException("Not your turn");
    }

    //checks to make sure that game HAS started
    if (!gameStarted) {
      throw new IllegalStateException("The game has not started yet.");
    }
    //checks to make sure that the game isn't over
    if (isGameOver()) {
      throw new IllegalStateException("The game is over.");
    }
    //checks to make sure that the cell is on the board
    if (!isValidCoord(c)) {
      throw new IllegalArgumentException("Not a valid coordinate");
    }

    // if flipping this cell is a valid move
    if (isMoveValid(c, currentPlayerTurn)) {
      //flip the cell of the players turn
      board.put(c, new BasicCell(currentPlayerTurn));
      numPasses = 0;
      //flip all cells between this cell and same player cell,
      // where either x,y,z is equal

      for (Coordinate direction : c.getDirections()) { //gets the 6 directions
        //slightly redundant FIX LATER

        if (hasStraightLine(c, direction, currentPlayerTurn)) {
          //flip all cells in the direction
          flipCellsInDirection(c, direction);
        }
      }
    } else {
      throw new IllegalArgumentException("Not a valid move");
    }


    currentPlayerTurn = 3 - currentPlayerTurn; //the other player
    if (!hasValidMoves(currentPlayerTurn)) {
      if (!isGameOver()) {
        this.passMove(currentPlayerTurn);
      }

    }
    //showValidMoves(currentTurn);


    //might be handled by another method:

    //assume player1 is playing vs player2 and player1 just made a move.
    //check if player2 has any valid moves available, and if they do, it is
    //player2's turn. if player2 doesn't have any moves available, it skips
    //player2's turn and it is now player1's turn again. if player1 doesn't
    //have any more moves, the game is over.

  }

  //helper: determines if the player has any valid moves
  protected boolean hasValidMoves(int playerTurn) {
    //checks to make sure it is passed a valid playerTurn
    if (playerTurn < 0 || playerTurn > numPlayers) {
      throw new IllegalArgumentException("Not a valid player");
    }
    for (Coordinate coordinate : board.keySet()) {
      if (isMoveValid(coordinate, playerTurn)) {
        return true;
      }
    }
    return false;
  }


  //maybe will be used in the future when wanting to show potential moves on the board
  //  protected void showValidMoves(int playerTurn) {
  //    for (Coordinate coordinate : board.keySet()) {
  //      if (isMoveValid(coordinate)) {
  //        //board.put(coordinate, new BasicCell(0, ));
  //      }
  //    }
  //  }

  //helper: flips cells on an axis (x, y, z)
  protected void flipCellsInDirection(Coordinate c, Coordinate direction) {

    Coordinate currentCoord = c.add(direction);

    while (board.get(currentCoord).getOwner() != currentPlayerTurn) {
      board.put(currentCoord, new BasicCell(currentPlayerTurn));
      currentCoord = currentCoord.add(direction);
    }
  }


  @Override
  public void passMove(int p) {
    if (p != currentPlayerTurn) {
      throw new IllegalStateException("Not your turn");
    }

    //checks to make sure that game HAS started
    if (!gameStarted) {
      throw new IllegalStateException("The game has not started yet.");
    }
    if (isGameOver()) {
      throw new IllegalStateException("The game is over.");
    }
    //System.out.println("Player " + currentPlayerTurn + " passed");
    this.currentPlayerTurn = 3 - currentPlayerTurn;
    numPasses++;
    if (!hasValidMoves(currentPlayerTurn)) {
      this.currentPlayerTurn = 3 - currentPlayerTurn;
      numPasses++;
    }

  }


  /**
   * Determines if the game is over.
   *
   * @return true if the game is over, false otherwise.
   * @throws IllegalStateException if the game has not started yet.
   */
  @Override
  public boolean isGameOver() {
    //checks to make sure that game HAS started
    if (!gameStarted) {
      throw new IllegalStateException("The game has not started yet.");
    }
    return (!hasValidMoves(currentPlayerTurn) && !hasValidMoves(3 - currentPlayerTurn))
            || numPasses >= 2;
  }

  @Override
  public int getScore(int p) {
    if (!gameStarted) {
      throw new IllegalStateException("The game has not started yet.");
    }
    if (p != 1 && p != 2) {
      throw new IllegalArgumentException("Player does not exist in game");
    }
    int score = 0;
    for (Coordinate coordinate : board.keySet()) {
      if (board.get(coordinate).getOwner() == p) {
        score++;
      }
    }
    return score;
  }

  //  @Override
  //  public Cell getCellAt(int row, int col) {
  //    return null;
  //  }
  //
  //  @Override
  //  public Color getCellColorAt(int row, int col) {
  //    return null;
  //  }

  @Override
  public int getSideLength() {
    return this.sideLength;
  }

  //helper: determines if a move at the given coordinate is valid

  @Override
  public boolean isMoveValid(Coordinate c, int playerTurn) {
    // Check if the move is within the bounds of the board.
    if (!this.isValidCoord(c) || board.get(c).getOwner() != 0) {
      return false;
    }

    for (Coordinate direction : c.getDirections()) { //gets the 6 directions
      if (hasStraightLine(c, direction, playerTurn)) {
        return true;
      }
    }
    return false;
  }

  //helper: determines if there is potential for a straight line of the same player's cells
  protected boolean hasStraightLine(Coordinate c, Coordinate direction, int currentTurn) {
    Coordinate currentCoord = c.add(direction);

    if (!this.isValidCoord(currentCoord)) {
      return false;
    }

    // if neighbor cell in this direction is owned by other player
    if (board.get(currentCoord).getOwner() != 3 - currentTurn) { //e
      return false;
    }

    // goes in one direction until it finds same player cell
    while (this.isValidCoord(currentCoord) && board.get(currentCoord).getOwner() != 0) {
      currentCoord = currentCoord.add(direction);

      //new check
      if (!this.isValidCoord(currentCoord)) {
        return false;
      }

      if (board.get(currentCoord).getOwner() == currentTurn) {
        return true;
      }
    }
    return false; // No player's disc found at the far end.
  }


  //helper: determines if the coordinate is on the board
  protected boolean isValidCoord(Coordinate c) {
    return board.containsKey(c);
  }

  @Override
  public List<Coordinate> getAvailableMoves(int p) {
    List<Coordinate> available = new ArrayList<>();
    for (Coordinate coord : this.board.keySet()) {
      if (isMoveValid(coord, p)) {
        available.add(coord);
      }
    }
    return available;
  }

  @Override
  public int getCurrentTurn() {
    return currentPlayerTurn;
  }

  @Override
  public void notifyObserverTurn() {
    controllersToNotify.get(currentPlayerTurn - 1).getNotifiedItsYourPlayersMove();
  }

  @Override
  public void subscribe(ObserverInterface observer) {
    controllersToNotify.add(observer);
  }

  @Override
  public int howManyCellsDoesThisMoveFlip(Coordinate coord, int p) {

    int currentScore = this.getScore(p);

    if (this.isMoveValid(coord, p)) {
      ReversiModel clonedModelToTestMove = this.clone();

      clonedModelToTestMove.flipCell(coord, p);
      return clonedModelToTestMove.getScore(p) - currentScore - 1;
    }
    else {
      return 0;
    }

  }


}
