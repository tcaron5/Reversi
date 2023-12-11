package provider.model;

/**
 * Events that can be emitted from the model of a Reversi game.
 */
public interface GameEvents {
  /**
   * This will be called when it is the recipient's turn to play.
   */
  void yourTurn();

  /**
   * This will be called when both players have passed, and the game has ended.
   */
  void gameOver();
}
