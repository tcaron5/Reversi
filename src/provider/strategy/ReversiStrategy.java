package provider.strategy;

import provider.model.ReadonlyReversiModel;
import provider.model.HexCoordinate;
import provider.player.PlayerColor;

/**
 * An interface for strategies for capturing tiles
 * on a Reversi board.
 */
public interface ReversiStrategy {
  /**
   * According to the rules of this strategy,
   * selects a move (represented by a HexCoordinate)
   * which the player should make.
   *
   * @param m       the current game state
   * @param forWhom the PlayerColor the move is for
   * @return a HexCoordinate of the advised move
   */
  HexCoordinate chooseMove(ReadonlyReversiModel m, PlayerColor forWhom);
}
