package provider.player;

import provider.model.HexCoordinate;

/**
 * A features interface describing actions available to players (computer or human).
 */
public interface PlayerActions {

  /**
   * Plays a move on the board for this player at the given coordinate.
   *
   * @param coordinate - cell to capture
   */
  void playMove(HexCoordinate coordinate);

  /**
   * Gives up this player's turn.
   */
  void pass();
}
