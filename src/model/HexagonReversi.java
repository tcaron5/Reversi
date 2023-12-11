package model;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * This class represents a hexagon reversi game and extends the AbstractReversi class.
 */
public class HexagonReversi extends AbstractReversi {

  /**
   * This constructs a hexagon reversi game.
   */
  public HexagonReversi() {
    super();
  }

  /**
   * Constructor for passing in the fields.
   * @param board the game board.
   * @param sideLength the length of one edge of the board.
   * @param currentPlayerTurn the current players turn.
   * @param gameStarted if the game has started.
   * @param numPasses number of passes in a row so far.
   * @param numPlayers number of players in the game.
   */
  private HexagonReversi(LinkedHashMap<Coordinate, Cell> board,
                         int sideLength, int currentPlayerTurn,
                         boolean gameStarted, int numPasses, int numPlayers) {
    super(new LinkedHashMap<>(board), sideLength, currentPlayerTurn,
            gameStarted, numPasses, numPlayers);
  }

  /**
   * This creates a board used for the game.
   * @param sideLength the length of one edge of the board.
   */
  @Override
  protected void createBoard(int sideLength) {
    for (int r = -sideLength + 1; r < sideLength; r++) {
      for (int q = -sideLength + 1; q < sideLength; q++) {
        for (int s = -sideLength + 1; s < sideLength; s++) {
          if (r + q + s == 0) {
            board.put(new CubicCoordinate(q, s, r), new BasicCell());
          }
        }
      }
    }
    this.setInitialColors();
  }

  /**
   * This sets the initial colors of the board around the center.
   */
  protected void setInitialColors() {

    int currentPlayer = 1; // Start with player 1
    List<Coordinate> neighbors = new CubicCoordinate(0,0,0).getNeighbors();

    //loop through list
    for (Coordinate coord : neighbors) {
      board.put(coord, new BasicCell(currentPlayer));
      currentPlayer = 3 - currentPlayer;
    }
  }


  protected ReversiModel cloneChild(LinkedHashMap<Coordinate, Cell> board,
                                    int sideLength, int currentPlayerTurn,
                                    boolean gameStarted, int numPasses, int numPlayers) {

    return new HexagonReversi(board, sideLength, currentPlayerTurn, gameStarted, numPasses,
            numPlayers);
  }
}
