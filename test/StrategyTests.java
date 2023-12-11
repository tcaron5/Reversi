import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import model.Coordinate;
import model.CubicCoordinate;
import model.Player;
import model.PlayerImpl;
import strategy.CaptureMaxCellsThisMove;
import strategy.CheckCornersFirstStrategy;
import strategy.MiniMaxStrategy;
import strategy.RandomValidMoveStrategy;
import strategy.GoForCornersStrategy;
import strategy.AvoidCornerNeighborStrategy;

import view.TextualView;

/**
 * Test strategies for the game of Reversi in different game scenarios.
 */

public class StrategyTests {

  private model.HexagonReversi game = new model.HexagonReversi();

  private List<Integer> players = new ArrayList<>(Arrays.asList(1,2)) ;

  private TextualView view = new TextualView(game);

  //strategies
  private CaptureMaxCellsThisMove captureMaxCellsThisMoveStrategy
          = new CaptureMaxCellsThisMove();
  private AvoidCornerNeighborStrategy avoidCornerNeighborStrategy
          = new AvoidCornerNeighborStrategy();
  private GoForCornersStrategy goForCornersStrategy
          = new GoForCornersStrategy();
  private RandomValidMoveStrategy randomValidMoveStrategy
          = new RandomValidMoveStrategy();
  private CheckCornersFirstStrategy corners = new CheckCornersFirstStrategy();


  //CaptureMaxCellsThisMoveStrategy tests

  // start of the game tie
  @Test
  public void CaptureMaxCellsStratTiePicksUpperLeftMost() {
    game.startGame(3, 2);
    System.out.println(view.toString());
    game.flipCell(captureMaxCellsThisMoveStrategy.chooseCoord(game, 1), 1);
    Assert.assertEquals("  _ X _ \n" +
            " _ X X _ \n" +
            "_ O _ X _ \n" +
            " _ X O _ \n" +
            "  _ _ _ \n", view.toString());
  }


  @Test
  public void CaptureMaxCellsStratPicksMoveToGiveBestScore() {
    game.startGame(3, 2);
    System.out.println(view.toString());
    game.flipCell(captureMaxCellsThisMoveStrategy.chooseCoord(game, 1), 1);
    Assert.assertEquals("  _ X _ \n" +
            " _ X X _ \n" +
            "_ O _ X _ \n" +
            " _ X O _ \n" +
            "  _ _ _ \n", view.toString());
    game.flipCell(captureMaxCellsThisMoveStrategy.chooseCoord(game, 2), 2);
    Assert.assertEquals("  _ X _ \n" +
            " _ X X O \n" +
            "_ O _ O _ \n" +
            " _ X O _ \n" +
            "  _ _ _ \n", view.toString());
    game.flipCell(captureMaxCellsThisMoveStrategy.chooseCoord(game, 1), 1);
    Assert.assertEquals("  _ X _ \n" +
            " _ X X O \n" +
            "_ O _ X _ \n" +
            " _ X X X \n" +
            "  _ _ _ \n", view.toString());
    game.flipCell(captureMaxCellsThisMoveStrategy.chooseCoord(game, 2), 2);
    Assert.assertEquals("  _ X _ \n" +
            " _ X X O \n" +
            "_ O _ O _ \n" +
            " _ O O X \n" +
            "  _ O _ \n", view.toString());
    game.flipCell(captureMaxCellsThisMoveStrategy.chooseCoord(game, 1), 1);
    Assert.assertEquals("  _ X _ \n" +
            " _ X X O \n" +
            "_ X _ O _ \n" +
            " X X X X \n" +
            "  _ O _ \n", view.toString());
    game.flipCell(captureMaxCellsThisMoveStrategy.chooseCoord(game, 2), 2);
    Assert.assertEquals("  _ X _ \n" +
            " O O O O \n" +
            "_ O _ O _ \n" +
            " X O X X \n" +
            "  _ O _ \n", view.toString());
    System.out.println(view.toString());
    Assert.assertTrue(game.isGameOver());

  }



  //AvoidCornerNeighborStrategy tests
  //start of game tie:
  @Test
  public void AvoidCornerTiePicksUpperLeftMost() {
    game.startGame(3, 2);
    System.out.println(view.toString());
    game.flipCell(avoidCornerNeighborStrategy.chooseCoord(game, 1), 1);
    Assert.assertEquals("  _ X _ \n" +
            " _ X X _ \n" +
            "_ O _ X _ \n" +
            " _ X O _ \n" +
            "  _ _ _ \n", view.toString());
  }

  @Test
  public void AvoidCornerNeighborStratCorrectlyAvoidsCorners() {
    game.startGame(4, 2);
    System.out.println(view.toString());
    game.flipCell(new CubicCoordinate(1,1,-2), 1);
    game.flipCell(new CubicCoordinate(2,1,-3), 2);
    System.out.println(view.toString());

    //opportunity to move into neighbor of corner in top left
    // -> should not move there.
    Assert.assertNotEquals(new CubicCoordinate(1,2,-3),
            avoidCornerNeighborStrategy.chooseCoord(game, 1));
    game.flipCell(avoidCornerNeighborStrategy.chooseCoord(game, 1), 1);
    Assert.assertEquals("   _ _ O _ \n" +
            "  _ _ O _ _ \n" +
            " _ X X X _ _ \n" +
            "_ _ X _ X _ _ \n" +
            " _ _ X O _ _ \n" +
            "  _ _ _ _ _ \n" +
            "   _ _ _ _ \n", view.toString());
    System.out.println(view.toString());

  }

  @Test
  public void AvoidCornerNeighborStratHandlesTies() {
    game.startGame(4, 2);
    game.flipCell(new CubicCoordinate(1,1,-2), 1);
    game.flipCell(new CubicCoordinate(2,1,-3), 2);
    game.flipCell(avoidCornerNeighborStrategy.chooseCoord(game, 1), 1);

    //sets up game state
    game.flipCell(avoidCornerNeighborStrategy.chooseCoord(game, 2), 2);
    System.out.println(view.toString());
    Assert.assertEquals("   _ _ O _ \n" +
            "  _ _ O _ _ \n" +
            " _ X X X O _ \n" +
            "_ _ X _ O _ _ \n" +
            " _ _ X O _ _ \n" +
            "  _ _ _ _ _ \n" +
            "   _ _ _ _ \n", view.toString());

    // strategy should avoid opportunities in top left and middle right corners
    game.flipCell(avoidCornerNeighborStrategy.chooseCoord(game, 1), 1);
    System.out.println(view.toString());
    Assert.assertEquals("   _ _ O _ \n" +
            "  _ _ O _ _ \n" +
            " _ X X X O _ \n" +
            "_ _ X _ X _ _ \n" +
            " _ _ X X X _ \n" +
            "  _ _ _ _ _ \n" +
            "   _ _ _ _ \n", view.toString());
    game.flipCell(new CubicCoordinate(-2,1,1),2);
    System.out.println(view.toString());
    game.flipCell(new CubicCoordinate(3,-2,-1),1);
    System.out.println(view.toString());
    game.flipCell(new CubicCoordinate(-1,-1,2),2);
    game.passMove(1);
    System.out.println(view.toString());
    
    // at this point in the game, player2 has no moves available that avoid
    // the neighbors of a corner.
    // therefore, the strategy defaults to picking the top-leftmost valid move,
    // which is a move that is a neighbor of a corner.

    //picks between 3 different moves that are all neighbors of a corner
    //delegate to MaxCells Strategy
    Assert.assertEquals(new CubicCoordinate(3,-1,-2),
            avoidCornerNeighborStrategy.chooseCoord(game, 2));
    game.flipCell(avoidCornerNeighborStrategy.chooseCoord(game, 2), 2);
    System.out.println(view.toString());
    Assert.assertEquals("   _ _ O _ \n" +
            "  _ _ O _ O \n" +
            " _ X O X O X \n" +
            "_ _ O _ O _ _ \n" +
            " _ O O O X _ \n" +
            "  _ _ O _ _ \n" +
            "   _ _ _ _ \n", view.toString());
  }



  //GoForCornersStrategy tests

  @Test
  public void GoForCornersStratCorrectlyMovesTowardsCorners() {
    game.startGame(4, 2);
    System.out.println(view.toString());

    //picks top-leftmost valid move
    game.flipCell(goForCornersStrategy.chooseCoord(game, 1), 1);
    Assert.assertEquals("   _ _ _ _ \n" +
            "  _ _ X _ _ \n" +
            " _ _ X X _ _ \n" +
            "_ _ O _ X _ _ \n" +
            " _ _ X O _ _ \n" +
            "  _ _ _ _ _ \n" +
            "   _ _ _ _ \n", view.toString());
    System.out.println(view.toString());

    // player2 (O) should pick the cell that moves furthest out
    // towards the outer ring of the board

    Assert.assertEquals(new CubicCoordinate(2,1,-3),
            goForCornersStrategy.chooseCoord(game, 2));
    game.flipCell(goForCornersStrategy.chooseCoord(game, 2), 2);
    System.out.println(view.toString());

    // player1 (X) should do the same
    Assert.assertEquals(new CubicCoordinate(1,2,-3),
            goForCornersStrategy.chooseCoord(game, 1));
    game.flipCell(goForCornersStrategy.chooseCoord(game, 1), 1);
    System.out.println(view.toString());

    // here player2 (O) has the opportunity to move into the
    // top left corner, which it should do

    Assert.assertEquals(new CubicCoordinate(0,3,-3),
            goForCornersStrategy.chooseCoord(game, 2));
    game.flipCell(goForCornersStrategy.chooseCoord(game, 2), 2);
    System.out.println(view.toString());

    // the current game state results in a tie for strategy GoForCorners
    // for player1 (X). player1 has three options to move into the
    // second outermost ring of the board, and it should pick the
    // top-leftmost move (-1, 2, -1)

    Assert.assertEquals(new CubicCoordinate(-1,2,-1),
            goForCornersStrategy.chooseCoord(game, 1));
    game.flipCell(goForCornersStrategy.chooseCoord(game, 1), 1);
    System.out.println(view.toString());
  }


  //CornersFirst tests
  @Test
  public void cornersStrategyPicksCornerIfAvailable() {
    game.startGame(4, 2);

    System.out.println(view.toString());

    game.flipCell(new CubicCoordinate(1,1,-2), 1);
    game.flipCell(new CubicCoordinate(2,1,-3), 2);

    game.flipCell(new CubicCoordinate(1,2,-3), 1);
    System.out.println(view.toString());

    game.flipCell(new CubicCoordinate(-1,-1,2), 2);
    game.flipCell(new CubicCoordinate(-2,-1,3), 1);

    game.flipCell(new CubicCoordinate(-1,-2,3), 2);
    System.out.println(view.toString());
    game.passMove(1);

    //in this scenario player 2 should pick top left corner
    Assert.assertEquals(new CubicCoordinate(0,3,-3), corners.chooseCoord(game, 2));

  }

  //RandomValidMoveStrategy tests


  // will continuously loop until the strategy picks a different move,
  // proving it is randomizing the moves.
  @Test
  public void RandomValidMoveStratWillRandomizeMoves() {
    game.startGame(4, 2);
    boolean flag = true;
    Coordinate firstRandomizedCoord
            = randomValidMoveStrategy.chooseCoord(game, 1);
    while (flag) {
      Coordinate coord = randomValidMoveStrategy.chooseCoord(game, 1);
      if (!coord.equals(firstRandomizedCoord)) {
        flag = false;
      }
    }
    Assert.assertFalse(flag);

  }


  @Test
  public void miniMaxChoosesBestMove() {
    MiniMaxStrategy miniMax =
            new MiniMaxStrategy(1, new CaptureMaxCellsThisMove());
    game.startGame(4, 2);
    System.out.println(view.toString());
    game.flipCell(captureMaxCellsThisMoveStrategy.chooseCoord(game, 1), 1);
    System.out.println(view.toString());

    Assert.assertEquals(new CubicCoordinate(2,1,-3),miniMax.chooseCoord(game, 2));
    game.flipCell(miniMax.chooseCoord(game, 2), 2);
    System.out.println(view.toString());
  }

  @Test
  public void miniMaxWinsAtDepth5() {
    game.startGame(5, 2);
    MiniMaxStrategy miniMaxAgainstAvoid =
            new MiniMaxStrategy(5, new AvoidCornerNeighborStrategy());
    Player playerAvoidCorners = new PlayerImpl(avoidCornerNeighborStrategy, 1);
    Player playerMiniMax = new PlayerImpl(miniMaxAgainstAvoid, 2);


    List<Player> players = new ArrayList<Player>();
    players.add(playerAvoidCorners);
    players.add(playerMiniMax);
    int playerIndex = 0;

    System.out.println(view.toString());
    while (!game.isGameOver()) {
      Coordinate coord = players.get(playerIndex).play(game);
      try {
        game.flipCell(coord, players.get(playerIndex).getPlayerNumber());
        System.out.println("Player " + players.get(playerIndex).getPlayerNumber() + " move:");
        System.out.println(view.toString());

      }
      catch (IllegalStateException ise) {
        System.out.println(players.get(playerIndex).getPlayerNumber() + " cannot make move.");
      }

      playerIndex = (playerIndex + 1) % players.size();
    }

    System.out.println("Player1 Score: " + game.getScore(1));
    System.out.println("Player2 Score: " + game.getScore(2));

    Assert.assertTrue(game.getScore(2) > game.getScore(1));
  }

  @Test
  public void miniMaxUsesAnotherStrategyWhenMaxDepthReached() {
    MiniMaxStrategy miniMax =
            new MiniMaxStrategy(0, new CaptureMaxCellsThisMove());

    game.startGame(4, 2);

    System.out.println(view.toString());
    game.flipCell(captureMaxCellsThisMoveStrategy.chooseCoord(game, 1), 1);
    System.out.println(view.toString());
    //game.flipCell(miniMax.chooseCoord(game, 2), 2);
    //game.flipCell(AvoidCornerNeighborStrategy.chooseCoord(game, 2), 2);

    Assert.assertEquals(miniMax.chooseCoord(game, 2),
            avoidCornerNeighborStrategy.chooseCoord(game, 2));
  }


  @Test
  public void miniMaxChoosesBestMoveInEndGameScenario() {
    MiniMaxStrategy miniMax =
            new MiniMaxStrategy(3, new CaptureMaxCellsThisMove());

    game.startGame(3, 2);

    System.out.println(view.toString());

    game.flipCell(captureMaxCellsThisMoveStrategy.chooseCoord(game, 1), 1);
    System.out.println(view.toString());

    game.flipCell(miniMax.chooseCoord(game,2), 2);
    System.out.println(view.toString());

    game.flipCell(captureMaxCellsThisMoveStrategy.chooseCoord(game, 1), 1);
    System.out.println(view.toString());
    Assert.assertEquals(new CubicCoordinate(-1,2,-1), miniMax.chooseCoord(game,2));
    game.flipCell(miniMax.chooseCoord(game,2), 2);
    System.out.println(view.toString());

    game.flipCell(captureMaxCellsThisMoveStrategy.chooseCoord(game, 1), 1);
    System.out.println(view.toString());

    game.flipCell(miniMax.chooseCoord(game,2), 2);
    System.out.println(view.toString());

    Assert.assertTrue(game.getScore(2) > game.getScore(1));
    Assert.assertEquals(game.getScore(2), 8);
  }


  //mock tests

  @Test
  public void captureMaxCellsStrategyChecksAllPossibleMoves() {
    StringBuilder logMoves = new StringBuilder();
    StrategyMockModel mock = new StrategyMockModel(logMoves);
    mock.startGame(3, 2);
    captureMaxCellsThisMoveStrategy.chooseCoord(mock, 1);

    Assert.assertTrue(logMoves.toString().contains("(1 1 -2)"));
    Assert.assertTrue(logMoves.toString().contains("(2 -1 -1)"));
    Assert.assertTrue(logMoves.toString().contains("(-2 1 1)"));
    Assert.assertTrue(logMoves.toString().contains("(-1 -1 2)"));

    Assert.assertEquals("FLIPCELL: (1 1 -2) \n" +
            "FLIPCELL: (-1 2 -1) \n" +
            "FLIPCELL: (2 -1 -1) \n" +
            "FLIPCELL: (-2 1 1) \n" +
            "FLIPCELL: (1 -2 1) \n" +
            "FLIPCELL: (-1 -1 2) \n", logMoves.toString());
  }

  @Test
  public void avoidCornerNeighborStrategyChecksAllPossibleMoves() {
    StringBuilder logMoves = new StringBuilder();
    StrategyMockModel mock = new StrategyMockModel(logMoves);
    mock.startGame(3, 2);
    avoidCornerNeighborStrategy.chooseCoord(mock, 1);

    Assert.assertTrue(logMoves.toString().contains("(1 1 -2)"));
    Assert.assertTrue(logMoves.toString().contains("(2 -1 -1)"));
    Assert.assertTrue(logMoves.toString().contains("(-2 1 1)"));
    Assert.assertTrue(logMoves.toString().contains("(-1 -1 2)"));
  }

  //miniMax will check all possible moves it can make for each possible move opponent can make
  @Test
  public void miniMaxStrategyChecksAllPossibleMoves() {
    StringBuilder logMoves = new StringBuilder();
    StrategyMockModel mock = new StrategyMockModel(logMoves);
    mock.startGame(4, 2);

    MiniMaxStrategy miniMax = new MiniMaxStrategy(2, captureMaxCellsThisMoveStrategy);
    miniMax.chooseCoord(mock, 1);
    //Assert.assertEquals("", logMoves.toString());

    Assert.assertTrue(logMoves.toString().contains("(1 1 -2)"));
    Assert.assertTrue(logMoves.toString().contains("(2 -1 -1)"));
    Assert.assertTrue(logMoves.toString().contains("(-2 1 1)"));
    Assert.assertTrue(logMoves.toString().contains("(-1 -1 2)"));

    //Assert.assertTrue(logMoves.toString().contains("(0 3 -3)"));


  }

  @Test
  public void captureMaxCellsStrategyChecksAllPossibleMovesLateGame() {
    StringBuilder logMoves = new StringBuilder();
    StrategyMockModel mock = new StrategyMockModel(logMoves);
    mock.startGame(3, 2);
    Assert.assertEquals(new CubicCoordinate(1,1,-2),
            captureMaxCellsThisMoveStrategy.chooseCoord(mock, 1));

  }

  @Test
  public void GoForCornersStrategyChecksAllAvailableCorners() {
    game.startGame(4, 2);
    System.out.println(view.toString());

    game.flipCell(new CubicCoordinate(1,1,-2), 1);
    game.flipCell(new CubicCoordinate(2,1,-3), 2);

    game.flipCell(new CubicCoordinate(1,2,-3), 1);
    System.out.println(view.toString());

    game.flipCell(new CubicCoordinate(-1,-1,2), 2);
    game.flipCell(new CubicCoordinate(-2,-1,3), 1);

    game.flipCell(new CubicCoordinate(-1,-2,3), 2);
    System.out.println(view.toString());
    game.passMove(1);

    //in this scenario player 2 should pick top left corner

    StringBuilder logMoves = new StringBuilder();
    //    StrategyMockModel mock = new StrategyMockModel(logMoves, game.getBoard());
    StrategyMockModel mock = new StrategyMockModel(game.getBoard(),
            4, 2, true, 1, 2, logMoves);
    //    mock.startGame(3, 2);

    TextualView mockView = new TextualView(mock);
    System.out.println(mockView.toString() + "MOCK");
    corners.chooseCoord(mock, 1);

    //Assert.assertEquals("", logMoves.toString());

    //tries to make all corner moves available for player 1
    Assert.assertTrue(logMoves.toString().contains("(3 0 -3)"));
    Assert.assertTrue(logMoves.toString().contains("(0 -3 3)"));

    Assert.assertFalse(logMoves.toString().contains("(-1 -1 2)"));

    //tries to make all corner moves available for player 2
    corners.chooseCoord(mock, 2);
    Assert.assertTrue(logMoves.toString().contains("(0 3 -3)"));
    Assert.assertTrue(logMoves.toString().contains("(-3 0 3)"));
    Assert.assertFalse(logMoves.toString().contains("(-1 -1 2)"));

  }


}
