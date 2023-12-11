package provider.model;

import java.util.List;

import provider.player.PlayerColor;

/**
 * An interface for a Reversi game with only observation methods.
 */
public interface ReadonlyReversiModel {
  /**
   * Get the cell state of a hex cell at a given coordinate.
   *
   * @param coordinate HexCoordinate of the desired cell
   * @return CellState state of the retrieved cell
   * @throws IllegalArgumentException for invalid coordinate
   */
  CellState getCell(HexCoordinate coordinate);

  /**
   * Gets the player whose turn it is.
   *
   * @return PlayerColor
   */
  PlayerColor getCurrentPlayer();

  /**
   * Checks if a move can legally be made at the
   * given coordinate by the given player.
   *
   * @param coordinate  HexCoordinate of the desired cell
   * @param playerColor Player to make the move
   * @return boolean true if the move is allowable
   */
  boolean isMoveAllowable(HexCoordinate coordinate, PlayerColor playerColor);

  /**
   * Checks if no moves are possible for either player, except to pass.
   *
   * @return boolean true if no moves are possible
   */
  boolean noMovesLeft();

  /**
   * Gets the size of the hexagonal board by side-length.
   *
   * @return int size
   */
  int getBoardSize();

  /**
   * Gets a list of all possible moves for a given player.
   *
   * @param p Player to move
   * @return list of possible moves as hex coordinates
   */
  List<HexCoordinate> getLegalMoves(PlayerColor p);

  /**
   * Checks whether the game is over.
   * This will be true if both players have passed in succession.
   *
   * @return true if game is over
   */
  boolean isGameOver();

  /**
   * Get the score for a given player.
   *
   * @param p Player to check
   * @return int score, each tile of player's color counts for one point
   */
  int getScore(PlayerColor p);

  /**
   * Gets a copy of the current game-board.
   *
   * @return copy of board
   */
  CellState[][] getBoardCopy();

  /**
   * Simulates the given move on a board copy and checks
   * what the __SCORE INCREASE__ would be, and returns it.
   * Does not return the player's current score, but the potential increase.
   *
   * @param c Coordinate to check move
   * @param p PlayerColor to move for
   * @return int potential increase in score
   */
  int getSimulatedScore(HexCoordinate c, PlayerColor p);
}
