package model;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Represents a reversi game on a square board.
 */
public class SquareReversi extends AbstractReversi {

  /**
   * This constructs a hexagon reversi game.
   */
  public SquareReversi() {
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
  private SquareReversi(LinkedHashMap<Coordinate, Cell> board,
                         int sideLength, int currentPlayerTurn,
                         boolean gameStarted, int numPasses, int numPlayers) {
    super(new LinkedHashMap<>(board), sideLength, currentPlayerTurn,
            gameStarted, numPasses, numPlayers);
  }

  @Override
  protected ReversiModel cloneChild(LinkedHashMap<Coordinate, Cell> board,
                                    int sideLength, int currentPlayerTurn,
                                    boolean gameStarted,
                                    int numPasses, int numPlayers) {
    return new SquareReversi(board, sideLength, currentPlayerTurn, gameStarted, numPasses,
            numPlayers);
  }

  @Override
  protected void createBoard(int sideLength) {
    //SQUARE REVERSI MUST HAVE AN EVEN SIDELENGTH
    if (sideLength % 2 != 0) {
      throw new IllegalArgumentException("Side length must be even and greater" +
              "than or equal to 4");
    }


    for (int x = 0; x < sideLength; x++) {
      for (int y = 0; y < sideLength; y++) {
        board.put(new CartesianCoordinate(x, y), new BasicCell());
      }
    }
    this.setInitialColors();
  }

  /**
   * This sets the initial colors of the board around the center.
   */
  protected void setInitialColors() {

    int currentPlayer = 1; // Start with player 1
    List<CartesianCoordinate> startingFour = new ArrayList<>();

    startingFour.add(new CartesianCoordinate(sideLength / 2 - 1,sideLength / 2 - 1));
    startingFour.add(new CartesianCoordinate(sideLength / 2,sideLength / 2 - 1));
    startingFour.add(new CartesianCoordinate(sideLength / 2,sideLength / 2));
    startingFour.add(new CartesianCoordinate(sideLength / 2 - 1,sideLength / 2));

    //loop through list
    for (CartesianCoordinate coord : startingFour) {
      board.put(coord, new BasicCell(currentPlayer));
      currentPlayer = 3 - currentPlayer;
    }
  }


}
