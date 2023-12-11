package model;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * Gives us the guaranteed observer methods in a game of reversi.
 */
public interface ReadonlyReversiModel {

  /**
   * Gets a copy of the board of cells in the game.
   * @return a map of cells in the game.
   */
  LinkedHashMap<Coordinate, Cell> getBoard();

  /**
   * Determines if the game is over.
   * @return true if the game is over, false otherwise.
   * @throws IllegalStateException if the game has not started yet.
   */
  boolean isGameOver();

  /**
   * Gets the score of a player.
   * @param p the player to get the score.
   * @return the score of the player.
   * @throws IllegalStateException if game has not started.
   * @throws IllegalArgumentException if player does not exist in the game.
   */
  int getScore(int p);


  /**
   * Gets the length of one edge of the board (number of cells long one edge is).
   * @return the length of one edge of the board.
   */
  int getSideLength();


  //legal to play at given coordinate?
  /**
   * Determine if a cubic coordinate is a valid move for the players turn.
   * @return true if the move is valid.
   */
  boolean isMoveValid(Coordinate c, int playerTurn);

  // Does the current player have any legal moves? (protected method)

  // What are the contents of a cell at a given coordinate? (in cell class)

  /**
   * Clones the game of Reversi.
   * @return a clone of this game.
   */
  ReversiModel clone();

  /**
   * Get a list of available moves for the given player.
   * @param p the player whose moves we want.
   * @return a list of available moves for the given player.
   */
  List<Coordinate> getAvailableMoves(int p);

  /**
   * Get the current turn.
   */
  int getCurrentTurn();


  /**
   * Returns the number of cells that would be flipped if the given move were made.
   * @param coord the coordinate of the cell to be flipped.
   * @param p the player id that is attempting to make the move.
   * @return the number of cells that would be flipped if the given move were made.
   */
  int howManyCellsDoesThisMoveFlip(Coordinate coord, int p);
}