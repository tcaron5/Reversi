package strategy;

import model.Coordinate;
import model.ReadonlyReversiModel;

/**
 * A Strategy interface for choosing where to play next for the given player for Reversi.
 */
public interface ReversiStrategy {

  /**
   * Choosing a coordinate based off a strategy.
   * @param model the model for the game that is immutable.
   * @param player the player choosing the coord.
   * @return
   */
  Coordinate chooseCoord(ReadonlyReversiModel model, int player);
}
