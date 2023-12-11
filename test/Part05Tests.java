import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import model.CartesianCoordinate;
import model.Coordinate;
import model.CubicCoordinate;
import model.Player;
import model.PlayerImpl;
import strategy.AvoidCornerNeighborStrategy;
import strategy.CaptureMaxCellsThisMove;
import strategy.CheckCornersFirstStrategy;
import strategy.GoForCornersStrategy;
import strategy.MiniMaxStrategy;
import strategy.RandomValidMoveStrategy;
import view.SquareGUI;

/**
 * Tests for the ReversiModel interface.
 */
public class Part05Tests {

  private model.HexagonReversi game = new model.HexagonReversi();
  private model.SquareReversi squareGame = new model.SquareReversi();

  private List<Integer> players = new ArrayList<>(Arrays.asList(1, 2));

  private Player player1 = new PlayerImpl(null, 1);
  private Player player2 = new PlayerImpl(null, 2);
  private SquareGUI view = new SquareGUI(squareGame);

  //strategies
  private CaptureMaxCellsThisMove captureMaxCellsThisMoveStrategy
          = new CaptureMaxCellsThisMove();
  private AvoidCornerNeighborStrategy avoidCornerNeighborStrategy
          = new AvoidCornerNeighborStrategy();
  private RandomValidMoveStrategy randomValidMoveStrategy
          = new RandomValidMoveStrategy();
  private GoForCornersStrategy goForCornersStrategy
          = new GoForCornersStrategy();
  private CheckCornersFirstStrategy corners = new CheckCornersFirstStrategy();

  @Test
  public void testHints() {
    game.startGame(4, 2);
    Assert.assertEquals(game.howManyCellsDoesThisMoveFlip(new CubicCoordinate(2, -1, -1),
            1), 1);
    game.flipCell(new CubicCoordinate(2, -1, -1), 1);
    Assert.assertEquals(game.howManyCellsDoesThisMoveFlip(new CubicCoordinate(3, -1, -2),
            2), 2);
  }


  // SQUARE REVERSI TESTS

  @Test
  public void testCreateBoard() {
    squareGame.startGame(4, 2);
    Assert.assertEquals(16, squareGame.getBoard().size());
  }

  @Test
  public void testInitialBoard() {
    Assert.assertThrows(IllegalStateException.class, () ->
            game.flipCell(new CubicCoordinate(1, 1, -2), 1));

    squareGame.startGame(4, 2);

    //check ownership of starting cells
    Assert.assertEquals(1, squareGame.getBoard().get(new
            CartesianCoordinate(1, 1)).getOwner());
    Assert.assertEquals(2, squareGame.getBoard().get(new
            CartesianCoordinate(2, 1)).getOwner());
    Assert.assertEquals(1, squareGame.getBoard().get(new
            CartesianCoordinate(2, 2)).getOwner());
    Assert.assertEquals(2, squareGame.getBoard().get(new
            CartesianCoordinate(1, 2)).getOwner());

  }

  @Test
  public void testFlipCell() {
    squareGame.startGame(4, 2);
    squareGame.flipCell(new CartesianCoordinate(3, 1), 1);
    Assert.assertEquals(1, squareGame.getBoard().get(new
            CartesianCoordinate(2, 1)).getOwner());
    Assert.assertEquals(1, squareGame.getBoard().get(new
            CartesianCoordinate(3, 1)).getOwner());
  }

  //flip cell for square board flips this cell and other cells
  @Test
  public void testFlipCellDiagonalManyDirections() {
    squareGame.startGame(6, 2);
    squareGame.flipCell(new CartesianCoordinate(4, 2), 1);
    squareGame.flipCell(new CartesianCoordinate(4, 1), 2);

    Assert.assertEquals(2, squareGame.getBoard().get(new
            CartesianCoordinate(4, 1)).getOwner());
    Assert.assertEquals(2, squareGame.getBoard().get(new
            CartesianCoordinate(3, 2)).getOwner());

    squareGame.flipCell(new CartesianCoordinate(3, 1), 1);
    squareGame.flipCell(new CartesianCoordinate(2, 1), 2);
    Assert.assertEquals(2, squareGame.getBoard().get(new
            CartesianCoordinate(2, 2)).getOwner());
    Assert.assertEquals(2, squareGame.getBoard().get(new
            CartesianCoordinate(3, 1)).getOwner());
  }


  @Test
  public void testPassAndGameOverScenarios() {
    squareGame.startGame(6, 2);
    squareGame.flipCell(new CartesianCoordinate(4, 2), 1);
    squareGame.passMove(2);
    squareGame.flipCell(new CartesianCoordinate(2, 4), 1);

    Assert.assertThrows(IllegalStateException.class, () -> squareGame.passMove(2));
  }

  //CONTROLLER TESTS
  @Test
  public void testNotifyWithMock() {
    StringBuilder log = new StringBuilder();
    MockController mock = new MockController(squareGame, player1, view, log);
    squareGame.startGame(4, 2);
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
    MockController mock1 = new MockController(squareGame, player1, view, log);
    MockController mock2 = new MockController(squareGame, player2, view, log);
    squareGame.subscribe(mock1);
    squareGame.subscribe(mock2);
    squareGame.startGame(4, 2);
    mock1.move(new CartesianCoordinate(3, 1));
    mock1.move(new CartesianCoordinate(1, 3));
    mock1.move(new CartesianCoordinate(1, 1));
    mock1.move(new CartesianCoordinate(-1, 0));

    //the third cord is set to zero for cartesian points
    Assert.assertEquals("NOTIFIED: 1\n" +
            "Player 1 MOVE: (3 1 0) \n" +
            "NOTIFIED: 2\n" +
            "Player 1 MOVE: (1 3 0) \n" +
            "Player 1 MOVE: (1 1 0) \n" +
            "Player 1 MOVE: (-1 0 0) \n", log.toString());
  }

  @Test
  public void testPassWithMock() {
    StringBuilder log = new StringBuilder();
    MockController mock1 = new MockController(squareGame, player1, view, log);
    MockController mock2 = new MockController(squareGame, player2, view, log);

    squareGame.startGame(4, 2);
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
    MockController mock1 = new MockController(squareGame, player1, view, log);
    MockController mock2 = new MockController(squareGame, player2, view, log);

    squareGame.startGame(4, 2);
    mock1.pass();
    mock2.move(new CartesianCoordinate(3, 1));
    mock1.pass();
    mock2.pass();

    Assert.assertEquals("NOTIFIED: 1\n" +
            "PASS: 1\n" +
            "NOTIFIED: 2\n" +
            "Player 2 MOVE: (3 1 0) \n" +
            "PASS: 1\n" +
            "PASS: 2\n" +
            "NOTIFIED: 1\n", log.toString());
  }



  //STRATEGY TESTS
  @Test
  public void captureMaxCellsThisMoveSquare() {

    squareGame.startGame(6, 2);
    squareGame.flipCell(new CartesianCoordinate(4, 2), 1);
    squareGame.flipCell(captureMaxCellsThisMoveStrategy.chooseCoord(squareGame, 2), 2);

    squareGame.flipCell(new CartesianCoordinate(1, 4), 1);
    Assert.assertEquals(new CartesianCoordinate(5,2),
            captureMaxCellsThisMoveStrategy.chooseCoord(squareGame, 2));
  }

  @Test
  public void AvoidCornerNeighborStratCorrectlyAvoidsCorners() {
    squareGame.startGame(6, 2);
    squareGame.passMove(1);
    squareGame.flipCell(new CartesianCoordinate(2,1), 2);

    //THIS SHOULD NOT PICK THE COORDINATE NEXT TO THE CORNER
    Assert.assertNotEquals(new CartesianCoordinate(5,5),
            avoidCornerNeighborStrategy.chooseCoord(squareGame, 1));
  }

  @Test
  public void GoForCornersStratCorrectlyMovesTowardsCorners() {
    squareGame.startGame(6, 2);

    squareGame.passMove(1);
    squareGame.flipCell(new CartesianCoordinate(2,1), 2);

    Assert.assertEquals(new CartesianCoordinate(1,3),
            goForCornersStrategy.chooseCoord(squareGame, 1));
  }

  @Test
  public void miniMaxChoosesBestMove() {
    MiniMaxStrategy miniMax =
            new MiniMaxStrategy(1, new CaptureMaxCellsThisMove());
    squareGame.startGame(6, 2);
    squareGame.flipCell(new CartesianCoordinate(4, 2), 1);

    Assert.assertEquals(new CartesianCoordinate(2, 1),
            miniMax.chooseCoord(squareGame, 2));
  }

  @Test
  public void cornersStrategyPicksCornerIfAvailable() {
    squareGame.startGame(4, 2);
    System.out.println(view.toString());
    squareGame.flipCell(new CartesianCoordinate(3,1), 1);
    //in this scenario player 2 should pick top right corner
    Assert.assertEquals(new CartesianCoordinate(3,0), corners.chooseCoord(squareGame, 2));
  }

  @Test
  public void RandomValidMoveStratWillRandomizeMoves() {
    squareGame.startGame(4, 2);
    boolean flag = true;
    Coordinate firstRandomizedCoord
            = randomValidMoveStrategy.chooseCoord(squareGame, 1);
    while (flag) {
      Coordinate coord = randomValidMoveStrategy.chooseCoord(squareGame, 1);
      if (!coord.equals(firstRandomizedCoord)) {
        flag = false;
      }
    }
    Assert.assertFalse(flag);

  }
}