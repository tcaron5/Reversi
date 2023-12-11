package provider.view;

import provider.player.PlayerActions;

/**
 * An interface for a menu related to a Reversi game.
 */
public interface Menu {
  /**
   * Registers player action listeners to this menu.
   *
   * @param p Player actions
   */
  void addPlayerActions(PlayerActions p);
}
