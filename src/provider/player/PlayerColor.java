package provider.player;

import provider.model.CellState;

/**
 * Represents the possible player colors.
 */
public enum PlayerColor {
  BLACK, WHITE;

  /**
   * Gets the opposite of this player.
   *
   * @return Player opposite player
   */
  public PlayerColor opposite() {
    if (this == PlayerColor.WHITE) {
      return PlayerColor.BLACK;
    } else {
      return PlayerColor.WHITE;
    }
  }

  /**
   * Gets the corresponding CellState of this player.
   *
   * @return CellState state
   */
  public CellState toState() {
    if (this == BLACK) {
      return CellState.BLACK;
    }
    return CellState.WHITE;
  }
}
