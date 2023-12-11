package provider.model;

import java.awt.Color;

import provider.player.PlayerColor;

/**
 * Represents the state of a Reversi hex cell.
 */
public enum CellState {
  BLACK, WHITE, EMPTY, OUTSIDE;

  /**
   * Checks if a CellState is equivalent to a matching PlayerColor.
   *
   * @param p given PlayerColor
   * @return true if this CellState is the same color
   */
  public boolean equalsPlayer(PlayerColor p) {
    switch (p) {
      case BLACK:
        return this.equals(CellState.BLACK);
      case WHITE:
        return this.equals(CellState.WHITE);
      default:
        return false;
    }
  }

  /**
   * Converts this state to a matching color.
   *
   * @return Color
   */
  public Color toColor() {
    switch (this) {
      case BLACK:
        return Color.BLACK;
      case WHITE:
        return Color.WHITE;
      default:
        return null;
    }
  }
}
