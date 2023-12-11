package provider.view;

import java.util.List;

import provider.model.HexCoordinate;
import provider.player.PlayerActions;

/**
 * An interface for a game of Reversi.
 * Empty for now, but will likely have methods to add.
 */
public interface ReversiView {

  /**
   * Registers the PlayerActions to the view.
   *
   * @param p actions
   */
  void addPlayerActions(PlayerActions p);

  /**
   * Repaints the game view.
   */
  void refreshView();

  /**
   * Prints a dialog to the view.
   *
   * @param message string to show
   */
  void showMessage(String message);

  /**
   * Highlights the possible moves for the current player.
   *
   * @param cells cells to highlight
   */
  void highlightMoves(List<HexCoordinate> cells);

  /**
   * Adjusts the title of the window.
   *
   * @param newTitle new title to set
   */
  void updateWindowTitle(String newTitle);
}
