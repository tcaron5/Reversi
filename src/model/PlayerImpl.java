package model;

import strategy.ReversiStrategy;

/**
 * Represents a player.
 */
public class PlayerImpl implements Player {

  private ReversiStrategy strategy;
  private int playerNumber;

  public PlayerImpl(ReversiStrategy strategy, int playerNumber) {
    this.strategy = strategy;
    this.playerNumber = playerNumber;
  }

  //strategy: ask the player for a move, and then play
  @Override
  public Coordinate play(ReadonlyReversiModel model) {
    return strategy.chooseCoord(model, playerNumber);
  }

  @Override
  public int getPlayerNumber() {
    return this.playerNumber;
  }

  @Override
  public ReversiStrategy getPlayerStrategy() {
    return strategy;
  }
}
