package provider.model;

import provider.player.PlayerColor;

/**
 * Represents a playable game of Reversi with a hexagonal board.
 */
public interface ReversiModel extends ReadonlyReversiModel {

  /**
   * If allowable, fills in the given cell with a
   * tile of the given player, and completes any side effects.
   *
   * @param coordinate HexCoordinate of the desired cell
   * @param p          Player who asks for the move
   * @throws IllegalStateException on non-allowable move or move out of turn
   */
  void move(HexCoordinate coordinate, PlayerColor p);

  /**
   * Uses the current player's move to give up control to the opposite player.
   */
  void pass();

  /**
   * Registers listeners for any game-related events
   * emitted by this model.
   *
   * @param c color for which this will notify
   * @param g game events
   */
  void addGameEventListeners(PlayerColor c, GameEvents g);

  /**
   * Begins the game of Reversi.
   *
   * @throws IllegalStateException if less than 2 players are registered in the game
   */
  void startGame();

}
