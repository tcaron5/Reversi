import java.util.LinkedHashMap;
import java.util.Objects;

import model.Cell;
import model.Coordinate;
import model.HexagonReversi;
import model.ReversiModel;

/**
 * This mock model of Reversi prints out the coord that is trying to be flipped.
 * Cloning this mock, passes the stringbuilder log in order to see which coords
 * are attempted on all the clones of a particular game.
 */
public class StrategyMockModel extends HexagonReversi {

  private StringBuilder log;

  /**
   * Main constructor for the mock.
   *
   * @param log the log of the game
   */
  public StrategyMockModel(StringBuilder log) {
    super();
    this.log = Objects.requireNonNull(log);
  }

  /**
   * Constructor for mock takes in board.
   *
   * @param log   the log of the game
   * @param board the board of the game
   */
  public StrategyMockModel(StringBuilder log, LinkedHashMap<Coordinate, Cell> board) {
    super();
    this.log = Objects.requireNonNull(log);
    this.board = board;

  }

  /**
   * Takes in a state of the game.
   * @param board             the game board.
   * @param sideLength        the length of one edge of the board.
   * @param currentPlayerTurn the current players turn.
   * @param gameStarted       if the game has started.
   * @param numPasses         number of passes in a row so far.
   * @param numPlayers        number of players in the game.
   * @param log               the log of the game.
   */
  public StrategyMockModel(LinkedHashMap<Coordinate, Cell> board,
                           int sideLength, int currentPlayerTurn,
                           boolean gameStarted, int numPasses, int numPlayers, StringBuilder log) {
    this.board = board;
    this.sideLength = sideLength;
    this.currentPlayerTurn = currentPlayerTurn;
    this.gameStarted = gameStarted;
    this.numPasses = numPasses;
    this.numPlayers = numPlayers;
    this.log = log;
  }

  // will print out the coord trying to be flipped
  @Override
  public void flipCell(Coordinate c, int p) {
    log.append("FLIPCELL: (" + c.getX() + " " + c.getY() + " " + c.getZ() + ") \n");
  }

  protected ReversiModel cloneChild(LinkedHashMap<Coordinate, Cell> board,
                                    int sideLength, int currentPlayerTurn,
                                    boolean gameStarted, int numPasses, int numPlayers) {
    return new StrategyMockModel(board, sideLength, currentPlayerTurn,
            gameStarted, numPasses, numPlayers, this.log);
  }
}
