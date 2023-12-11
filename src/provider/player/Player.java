package provider.player;

/**
 * An interface of actions available to a player of a Reversi game.
 */
public interface Player {
  /**
   * Registers the PlayerActions.
   *
   * @param p actions
   */
  void addPlayerActions(PlayerActions p);

  /**
   * Returns the color of this player.
   *
   * @return PlayerColor
   */
  PlayerColor getColor();
}
