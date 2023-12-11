package model;

import strategy.ReversiStrategy;

/**
 * Represents a player in Reversi.
 */
public interface Player {
  /**
   * Play at a coordinate depending on the player's strategy.
   * @param model the model for the game of reversi.
   * @return the cubic coordinate the player plays at.
   */
  Coordinate play(ReadonlyReversiModel model);

  /**
   * Get the player number for this player.
   * @return the player number.
   */
  int getPlayerNumber();

  /**
   * Get the strategy for this player.
   * @return the player number.
   */
  ReversiStrategy getPlayerStrategy();

}
