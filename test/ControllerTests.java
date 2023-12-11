import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import model.CubicCoordinate;
import model.HexagonReversi;
import model.Player;
import model.PlayerImpl;
import strategy.CaptureMaxCellsThisMove;
import strategy.CheckCornersFirstStrategy;
import view.HexagonGUI;

/**
 * The tests for the controller that makes sure all controller/listener behaviors work as expected.
 */
public class ControllerTests {

  private HexagonReversi game;

  private Player player1;
  private Player player2;

  private Player computerPlayer1;
  private Player computerPlayer2;

  private HexagonGUI view;


  @Before
  public void setUp() {
    game = new HexagonReversi();
    player1 = new PlayerImpl(null, 1);
    player2 = new PlayerImpl(null, 2);

    computerPlayer1 = new PlayerImpl(new CheckCornersFirstStrategy(), 1);
    computerPlayer2 = new PlayerImpl(new CaptureMaxCellsThisMove(), 2);

    view = new HexagonGUI(game);
  }

  //OBSERVER INTERFACE TESTS
  @Test
  public void testNotifyWithMock() {
    StringBuilder log = new StringBuilder();
    MockController mock = new MockController(game, player1, view, log);
    game.startGame(3, 2);
    mock.getNotifiedItsYourPlayersMove();
    mock.getNotifiedItsYourPlayersMove();
    mock.getNotifiedItsYourPlayersMove();
    mock.getNotifiedItsYourPlayersMove();

    Assert.assertEquals("NOTIFIED: 1\n" +
            "NOTIFIED: 1\n" +
            "NOTIFIED: 1\n" +
            "NOTIFIED: 1\n" +
            "NOTIFIED: 1\n", log.toString());
  }

  //CONTROLLER FEATURES TESTS
  @Test
  public void testMoveWithMock() {
    StringBuilder log = new StringBuilder();
    MockController mock1 = new MockController(game, player1, view, log);
    MockController mock2 = new MockController(game, player2, view, log);
    game.subscribe(mock1);
    game.subscribe(mock2);
    game.startGame(3, 2);
    mock1.move(new CubicCoordinate(2, -1, -1));
    mock1.move(new CubicCoordinate(1, 2, 1));
    mock1.move(new CubicCoordinate(1, 1, 2));
    mock1.move(new CubicCoordinate(-1, 0, 4));

    Assert.assertEquals("NOTIFIED: 1\n" +
            "Player 1 MOVE: (2 -1 -1) \n" +
            "NOTIFIED: 2\n" +
            "Player 1 MOVE: (1 2 1) \n" +
            "Player 1 MOVE: (1 1 2) \n" +
            "Player 1 MOVE: (-1 0 4) \n", log.toString());
  }

  @Test
  public void testPassWithMock() {
    StringBuilder log = new StringBuilder();
    MockController mock1 = new MockController(game, player1, view, log);
    MockController mock2 = new MockController(game, player2, view, log);

    game.startGame(3, 2);
    mock1.pass();
    mock2.pass();

    Assert.assertEquals("NOTIFIED: 1\n" +
            "PASS: 1\n" +
            "NOTIFIED: 2\n" +
            "PASS: 2\n" +
            "NOTIFIED: 1\n", log.toString());
  }

  @Test
  public void testMoveAndPass() {
    StringBuilder log = new StringBuilder();
    MockController mock1 = new MockController(game, player1, view, log);
    MockController mock2 = new MockController(game, player2, view, log);

    game.startGame(3, 2);
    mock1.pass();
    mock2.move(new CubicCoordinate(2, -1, -1));
    mock1.pass();
    mock2.pass();

    Assert.assertEquals("NOTIFIED: 1\n" +
            "PASS: 1\n" +
            "NOTIFIED: 2\n" +
            "Player 2 MOVE: (2 -1 -1) \n" +
            "NOTIFIED: 1\n" +
            "PASS: 1\n" +
            "NOTIFIED: 2\n" +
            "PASS: 2\n" +
            "NOTIFIED: 1\n", log.toString());
  }


  //computer tests
  @Test
  public void testComputersPlayGame() {
    StringBuilder log = new StringBuilder();
    MockController mock1 = new MockController(game, computerPlayer1, view, log);
    MockController mock2 = new MockController(game, computerPlayer2, view, log);
    game.startGame(3, 2);

    Assert.assertEquals("Player 1 MOVE: (1 1 -2) \n" +
            "Player 2 MOVE: (2 -1 -1) \n" +
            "Player 1 MOVE: (1 -2 1) \n" +
            "Player 2 MOVE: (-1 -1 2) \n" +
            "Player 1 MOVE: (-2 1 1) \n" +
            "Player 2 MOVE: (-1 2 -1) \n" +
            "Player 1 MOVE: (0 0 0) \n" +
            "NOTIFIED: 1\n" +
            "NOTIFIED: 2\n" +
            "NOTIFIED: 1\n" +
            "NOTIFIED: 2\n" +
            "NOTIFIED: 1\n" +
            "NOTIFIED: 2\n" +
            "NOTIFIED: 1\n", log.toString());
  }

}