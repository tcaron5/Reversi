package model;


import controller.ObserverInterface;

/**
 * Represents the primary model interface for playing a game of Reversi.
 * Gives us the guaranteed mutator methods.
 */
public interface ReversiModel extends ReadonlyReversiModel {

  /**
   * Starts the game with the given parameters, creates a board.
   * @param sideLength the length of one edge of the board.
   * @param p is the number of players in the game.
   * @throws IllegalStateException if the game has already started.
   * @throws IllegalArgumentException if the side length is less than 3.
   * @throws IllegalArgumentException if the list of players is empty.
   */
  void startGame(int sideLength, int p);


  /**
   * Flips the cell and (maybe) other cells at the given coordinate.
   * @param c the coordinate of the cell to be flipped.
   * @param p the player id that is attempting to make the move.
   * @throws IllegalStateException if the game has not started yet.
   * @throws IllegalStateException if the game is over.
   * @throws IllegalStateException if player attempts to make move when it's not their turn.
   * @throws IllegalArgumentException if the coordinate is not on the board.
   * @throws IllegalArgumentException if the move is invalid.
   */

  void flipCell(Coordinate c, int p);

  /**
   * Passes the turn to the next player.
   * @param p the player id that is attempting to make the move.
   * @throws IllegalStateException if the game has not started yet.
   * @throws IllegalStateException if the game is over.
   * @throws IllegalStateException if player attempts to pass when it's not their turn.
   */
  void passMove(int p);

  /**
   * Notifies the observer of the game model that it's their turn.
   */
  void notifyObserverTurn();

  /**
   * Subscribes an observer to the game model.
   */
  void subscribe(ObserverInterface observer);

}
