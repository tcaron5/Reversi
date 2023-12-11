import java.util.LinkedHashMap;

import model.AbstractReversi;
import model.Cell;
import model.Coordinate;
import controller.ObserverInterface;
import model.ReversiModel;

/**
 * Mock of the model to determine of observation pattern behaves as expected.
 */
public class MockReversiObservation extends AbstractReversi {
  private StringBuilder log;

  /**
   * Constructor takes in a current turn and a log.
   *
   * @param currentTurn the current turn for the scenario being tested.
   * @param log         the game log which tells us if the behavior is as expected.
   */
  public MockReversiObservation(int currentTurn, StringBuilder log) {
    this.log = log;
    this.currentPlayerTurn = currentTurn;
    gameStarted = true;
    numPlayers = 2;
  }

  @Override
  protected ReversiModel cloneChild(LinkedHashMap<Coordinate, Cell> board,
                                    int sideLength, int currentPlayerTurn, boolean gameStarted,
                                    int numPasses, int numPlayers) {
    return null;
  }

  @Override
  protected void createBoard(int sideLength) {
    log.append("");
  }

  @Override
  public void notifyObserverTurn() {
    controllersToNotify.get(currentPlayerTurn - 1).getNotifiedItsYourPlayersMove();

  }

  @Override
  public void subscribe(ObserverInterface observer) {
    controllersToNotify.add(observer);
    log.append(controllersToNotify.size() + "\n");
  }

}
