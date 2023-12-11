import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

import javax.swing.JFrame;

import controller.ReversiController;
import model.BasicCell;
import model.Cell;
import model.Coordinate;
import model.CubicCoordinate;
import model.HexagonReversi;
import model.PlayerImpl;
import model.ReversiModel;
import view.HexagonGUI;
import view.TextualView;

/**
 * Tests the implementation of model.HexagonReversi.
 */
public class HexagonReversiTests {

  private model.HexagonReversi game = new model.HexagonReversi();

  private List<Integer> players = new ArrayList<>(Arrays.asList(1, 2));

  private TextualView view = new TextualView(game);

  @Test
  public void testCreateBoard() {
    game.startGame(3, 2);
    Assert.assertEquals(19, game.getBoard().size());
  }


  @Test
  public void testInitialColors() {
    game.startGame(3, 2);

    Assert.assertEquals(new BasicCell(1).getOwner(),
            game.getBoard().get(new model.CubicCoordinate(0, 1, -1)).getOwner());

    Assert.assertEquals(new BasicCell(2).getOwner(),
            game.getBoard().get(new model.CubicCoordinate(1, 0, -1)).getOwner());

    Assert.assertEquals(new BasicCell(1).getOwner(),
            game.getBoard().get(new model.CubicCoordinate(1, -1, 0)).getOwner());
  }


  //FLIP CELL TESTS

  //flip cell invalid move (cannot flip if no straight line with same player cell exists)
  @Test
  public void testFlipCellNotAValidMove() {
    game.startGame(3, 2);
    Assert.assertThrows(IllegalArgumentException.class, () ->
            game.flipCell(new CubicCoordinate(2, -2, 0), 1));
  }

  //flip cell invalid move (cannot flip if has no neighbors)????
  @Test
  public void testFlipCellNoNeighbors() {
    game.startGame(3, 2);
    Assert.assertThrows(IllegalArgumentException.class, () ->
            game.flipCell(new CubicCoordinate(2, 0, -2), 1));
  }

  //flip cell invalid coord (not on board), throws
  @Test
  public void testFlipCellNotOnBoard() {
    game.startGame(3, 2);

    Assert.assertThrows(IllegalArgumentException.class, () ->
            game.flipCell(new CubicCoordinate(5, 0, -5), 1));
  }

  //flip cell invalid coord (already owned player 1 and player 2)
  @Test
  public void testFlipCellAlreadyOwned() {
    game.startGame(3, 2);

    Assert.assertThrows(IllegalArgumentException.class, () ->
            game.flipCell(new CubicCoordinate(1, 0, -1), 1));
  }


  //flip cell throws because game has not started yet
  @Test
  public void testFlipCellGameNotStarted() {
    Assert.assertThrows(IllegalStateException.class, () ->
            game.flipCell(new CubicCoordinate(2, -1, -1), 1));
  }

  //flip cell one direction, changes ownership
  @Test
  public void testFlipCellOneDirection() {
    game.startGame(3, 2);
    Assert.assertEquals(2,
            game.getBoard().get(new CubicCoordinate(1, 0, -1)).getOwner());

    game.flipCell(new CubicCoordinate(2, -1, -1), 1);
    Assert.assertEquals(1,
            game.getBoard().get(new CubicCoordinate(1, 0, -1)).getOwner());
  }

  //flip cell multiple directions, make sure cells were flipped in multiple directions
  @Test
  public void testFlipCellMultipleDirections() {
    game.startGame(3, 2);
    game.flipCell(new CubicCoordinate(2, -1, -1), 1);
    //Assert.assertEquals("", view.toString());
    game.flipCell(new CubicCoordinate(1, 1, -2), 2);
    //Assert.assertEquals("", view.toString());
    Assert.assertEquals(2,
            game.getBoard().get(new CubicCoordinate(0, 1, -1)).getOwner());
    Assert.assertEquals(2,
            game.getBoard().get(new CubicCoordinate(-1, 1, 0)).getOwner());


    //flip cell, flips cells in multiple directions
    game.flipCell(new CubicCoordinate(-1, 2, -1), 1);


    Assert.assertEquals(1,
            game.getBoard().get(new CubicCoordinate(0, 1, -1)).getOwner());
    Assert.assertEquals(1,
            game.getBoard().get(new CubicCoordinate(-1, 1, 0)).getOwner());

    //Assert.assertEquals("", view.toString());

  }

  //flip cell should flip multiple cells in multiple directions
  @Test
  public void testFlipMultipleCellsInMultipleDirections() {
    game.startGame(3, 2);
    game.flipCell(new CubicCoordinate(2, -1, -1), 1);
    game.flipCell(new CubicCoordinate(1, 1, -2), 2);
    game.flipCell(new CubicCoordinate(-1, 2, -1), 1);
    game.flipCell(new CubicCoordinate(-2, 1, 1), 2);
    game.flipCell(new CubicCoordinate(-1, -1, 2), 1);

    //    Assert.assertEquals("", view.toString());
    Assert.assertEquals(1,
            game.getBoard().get(new CubicCoordinate(1, 0, -1)).getOwner());
    Assert.assertEquals(1,
            game.getBoard().get(new CubicCoordinate(1, -1, 0)).getOwner());

    Assert.assertEquals(1,
            game.getBoard().get(new CubicCoordinate(-1, 0, 1)).getOwner());
    Assert.assertEquals(1,
            game.getBoard().get(new CubicCoordinate(0, -1, 1)).getOwner());

    //flip cell multiple cells in multiple directions
    game.flipCell(new CubicCoordinate(1, -2, 1), 2);

    Assert.assertEquals(2,
            game.getBoard().get(new CubicCoordinate(1, 0, -1)).getOwner());
    Assert.assertEquals(2,
            game.getBoard().get(new CubicCoordinate(1, -1, 0)).getOwner());

    Assert.assertEquals(2,
            game.getBoard().get(new CubicCoordinate(-1, 0, 1)).getOwner());
    Assert.assertEquals(2,
            game.getBoard().get(new CubicCoordinate(0, -1, 1)).getOwner());
    //Assert.assertEquals("", view.toString());

  }

  //flip cell that is at the edge of the board, valid
  @Test
  public void testFlipCellEdgeOfBoardValid() {
    game.startGame(3, 2);
    game.flipCell(new CubicCoordinate(2, -1, -1), 1);
    //Assert.assertEquals("", view.toString());
    Assert.assertEquals(0,
            game.getBoard().get(new CubicCoordinate(1, 1, -2)).getOwner());

    //flip cell edge of board
    game.flipCell(new CubicCoordinate(1, 1, -2), 2);

    Assert.assertEquals(2,
            game.getBoard().get(new CubicCoordinate(1, 1, -2)).getOwner());

  }

  //flip middle cell invalid
  @Test
  public void testFlipMiddleCell() {
    game.startGame(3, 2);
    Assert.assertThrows(IllegalArgumentException.class, () ->
            game.flipCell(new CubicCoordinate(0, 0, 0), 1));
  }

  //flip cell can result in game over
  //also makes sure flip cell and pass should throw if game over is already over
  @Test
  public void testFlipCellsMultipleDirectionsUntilGameOver() {
    game.startGame(3, 2);
    game.flipCell(new CubicCoordinate(2, -1, -1), 1);
    game.flipCell(new CubicCoordinate(1, 1, -2), 2);
    game.flipCell(new CubicCoordinate(-1, 2, -1), 1);

    game.flipCell(new CubicCoordinate(-2, 1, 1), 2);
    game.flipCell(new CubicCoordinate(-1, -1, 2), 1);

    Assert.assertFalse(game.isGameOver());
    game.flipCell(new CubicCoordinate(1, -2, 1), 2);

    Assert.assertTrue(game.isGameOver());
    //Assert.assertEquals("", view.toString());

    Assert.assertThrows(IllegalStateException.class, () ->
            game.flipCell(new CubicCoordinate(1, 1, -2), 1));

    Assert.assertThrows(IllegalStateException.class, () ->
            game.passMove(2));
  }


  @Test
  public void testCreateBoardSideLengthFiveInitialColors() {
    game.startGame(5, 2);
    Assert.assertEquals(61, game.getBoard().size());
    Assert.assertEquals(new BasicCell(1).getOwner(),
            game.getBoard().get(new model.CubicCoordinate(0, 1, -1)).getOwner());

    Assert.assertEquals(new BasicCell(2).getOwner(),
            game.getBoard().get(new model.CubicCoordinate(1, 0, -1)).getOwner());

    Assert.assertEquals(new BasicCell(1).getOwner(),
            game.getBoard().get(new model.CubicCoordinate(1, -1, 0)).getOwner());
  }

  @Test
  public void testGetBoard() {
    game.startGame(3, 2);
    LinkedHashMap<Coordinate, Cell> expectedBoard = new LinkedHashMap<>();
    for (int r = -3 + 1; r < 3; r++) {
      for (int q = -3 + 1; q < 3; q++) {
        for (int s = -3 + 1; s < 3; s++) {
          if (r + q + s == 0) {
            expectedBoard.put(new CubicCoordinate(q, s, r), new BasicCell());
          }
        }
      }
    }

    int currentPlayer = 1; // Start with player 1
    List<Coordinate> neighbors = new CubicCoordinate(0, 0, 0).getNeighbors();
    //loop through list
    for (Coordinate coord : neighbors) {
      expectedBoard.put(coord, new BasicCell(currentPlayer));
      currentPlayer = 3 - currentPlayer;
    }

    Assert.assertEquals(expectedBoard, game.getBoard());
  }

  //PASS MOVES TESTS

  @Test
  public void testPassMoveLetsOtherPlayerMakeAMove() {
    game.startGame(3, 2);
    game.passMove(1);

    game.flipCell(new CubicCoordinate(-1, 2, -1), 2);

    Assert.assertEquals(new BasicCell(2),
            game.getBoard().get(new CubicCoordinate(-1, 2, -1)));

    Assert.assertEquals(new BasicCell(2),
            game.getBoard().get(new CubicCoordinate(0, 1, -1)));

  }

  //If a player has no legal moves, they are required to pass.
  @Test
  public void testMoveIsAutomaticallyPassedIfCannotMakeMove() {
    game.startGame(3, 2);
    game.flipCell(new CubicCoordinate(-1, 2, -1), 1); // x moves
    game.passMove(2); // O passes
    game.flipCell(new CubicCoordinate(1, -2, 1), 1); // x moves
    // should automatically pass player 2


    //so flipping this cell should be player x move
    game.flipCell(new CubicCoordinate(1, 1, -2), 1); // x moves

    Assert.assertEquals(new BasicCell(1),
            game.getBoard().get(new CubicCoordinate(1, 1, -2)));
    //Assert.assertEquals("", view.toString());

  }

  @Test
  public void testTwoPassesInARowEndsGame() { //2 passes in a row ends the game
    game.startGame(3, 2);
    game.passMove(1); // x passes
    Assert.assertFalse(game.isGameOver());
    game.passMove(2); // O passes

    Assert.assertTrue(game.isGameOver());
    Assert.assertEquals(3, game.getScore(1));
  }

  //one person is forced to pass, the other passes, then the game is over
  @Test
  public void testPassingMoveWhenOpponentCannotMakeAMoveEndsGame() {
    game.startGame(3, 2);
    game.flipCell(new CubicCoordinate(-1, 2, -1), 1); // x moves
    game.passMove(2); // O passes
    game.flipCell(new CubicCoordinate(1, -2, 1), 1); // x moves
    // should automatically pass player 2

    game.passMove(1); //should go to 2 and then back to 1, and then game is over

    Assert.assertTrue(game.isGameOver());

  }

  @Test
  public void testCannotMakeMoveOrPassWhenItsNotYourTurn() {
    game.startGame(3, 2);

    // player 2 tries valid move before player 1
    Assert.assertThrows(IllegalStateException.class, () ->
            game.flipCell(new CubicCoordinate(1, 1, -2), 2));

  }

  //making an invalid move does not end the game
  @Test
  public void testOneCannotMoveOtherMakesInvalidMove() {
    game.startGame(3, 2);
    game.flipCell(new CubicCoordinate(-1, 2, -1), 1); // x moves
    game.passMove(2); // O passes
    game.flipCell(new CubicCoordinate(1, -2, 1), 1); // x moves
    // should automatically pass player 2

    Assert.assertFalse(game.isGameOver());
    //player 1 makes invalid move
    Assert.assertThrows(IllegalArgumentException.class, () ->
            game.flipCell(new CubicCoordinate(0, 0, 0), 1));
    Assert.assertFalse(game.isGameOver());
  }

  @Test
  public void testFullGameWithMultipleRuleBreakAttempts() {
    game.startGame(3, 2);
    game.flipCell(new CubicCoordinate(2, -1, -1), 1);

    //player 1 cannot move or pass out of turn
    Assert.assertThrows(IllegalStateException.class, () ->
            game.flipCell(new CubicCoordinate(1, 1, -2), 1));
    Assert.assertThrows(IllegalStateException.class, () ->
            game.passMove(1));

    //player 2 cannot place cell where there is no valid straight line to make
    //no straight and next to other players cells
    Assert.assertThrows(IllegalArgumentException.class, () ->
            game.flipCell(new CubicCoordinate(2, 0, -2), 2));

    //next to own cell
    Assert.assertThrows(IllegalArgumentException.class, () ->
            game.flipCell(new CubicCoordinate(-1, 2, -1), 2));

    game.flipCell(new CubicCoordinate(1, 1, -2), 2);
    game.flipCell(new CubicCoordinate(-1, 2, -1), 1);
    System.out.println(view.toString());

    game.flipCell(new CubicCoordinate(-2, 1, 1), 2);
    game.flipCell(new CubicCoordinate(-1, -1, 2), 1);

    Assert.assertFalse(game.isGameOver());
    game.flipCell(new CubicCoordinate(1, -2, 1), 2);

    Assert.assertTrue(game.isGameOver());
    //Assert.assertEquals("", view.toString());

    Assert.assertThrows(IllegalStateException.class, () ->
            game.flipCell(new CubicCoordinate(1, 1, -2), 1));

    Assert.assertThrows(IllegalStateException.class, () ->
            game.passMove(2));
  }


  //TEXTUAL VIEW

  //test big game with textual view and score
  @Test
  public void testTextualView() {
    Assert.assertThrows(IllegalStateException.class, () ->
            game.flipCell(new CubicCoordinate(1, 1, -2), 1));

    game.startGame(3, 2);

    ArrayList<Integer> ownerList = new ArrayList<>();

    Assert.assertEquals("  _ _ _ \n" +
            " _ X O _ \n" +
            "_ O _ X _ \n" +
            " _ X O _ \n" +
            "  _ _ _ \n", view.toString());

    game.flipCell(new CubicCoordinate(2, -1, -1), 1);
    Assert.assertEquals("  _ _ _ \n" +
            " _ X X X \n" +
            "_ O _ X _ \n" +
            " _ X O _ \n" +
            "  _ _ _ \n", view.toString());
    game.flipCell(new CubicCoordinate(1, 1, -2), 2);
    Assert.assertEquals(
            "  _ O _ \n" +
                    " _ O X X \n" +
                    "_ O _ X _ \n" +
                    " _ X O _ \n" +
                    "  _ _ _ \n", view.toString());
    game.flipCell(new CubicCoordinate(-1, 2, -1), 1);
    Assert.assertEquals(
            "  _ O _ \n" +
                    " X X X X \n" +
                    "_ X _ X _ \n" +
                    " _ X O _ \n" +
                    "  _ _ _ \n", view.toString());
    game.flipCell(new CubicCoordinate(-2, 1, 1), 2);
    Assert.assertEquals(
            "  _ O _ \n" +
                    " X O X X \n" +
                    "_ O _ X _ \n" +
                    " O O O _ \n" +
                    "  _ _ _ \n", view.toString());
    game.flipCell(new CubicCoordinate(-1, -1, 2), 1);
    Assert.assertEquals(
            "  _ O _ \n" +
                    " X O X X \n" +
                    "_ X _ X _ \n" +
                    " O X X _ \n" +
                    "  _ X _ \n", view.toString());

    //tests not valid move
    Assert.assertThrows(IllegalArgumentException.class, () ->
            game.flipCell(new CubicCoordinate(0, 0, 0), 2));

    //test coord not valid
    Assert.assertThrows(IllegalArgumentException.class, () ->
            game.flipCell(new CubicCoordinate(-5, 5, 100), 2));

    //valid move that ends the game
    game.flipCell(new CubicCoordinate(1, -2, 1), 2);
    Assert.assertEquals(
            "  _ O _ \n" +
                    " X O O X \n" +
                    "_ X _ O _ \n" +
                    " O O O O \n" +
                    "  _ X _ \n", view.toString());

    Assert.assertTrue(game.isGameOver());
  }

  //different board size, keep track of score
  @Test
  public void biggerTextualViewWithScore() {
    game.startGame(5, 2);

    Assert.assertEquals("    _ _ _ _ _ \n" +
            "   _ _ _ _ _ _ \n" +
            "  _ _ _ _ _ _ _ \n" +
            " _ _ _ X O _ _ _ \n" +
            "_ _ _ O _ X _ _ _ \n" +
            " _ _ _ X O _ _ _ \n" +
            "  _ _ _ _ _ _ _ \n" +
            "   _ _ _ _ _ _ \n" +
            "    _ _ _ _ _ \n", view.toString());
    //get score at start of game
    Assert.assertEquals(3, game.getScore(1));
    Assert.assertEquals(3, game.getScore(2));

    game.flipCell(new CubicCoordinate(2, -1, -1), 1);
    Assert.assertEquals("    _ _ _ _ _ \n" +
            "   _ _ _ _ _ _ \n" +
            "  _ _ _ _ _ _ _ \n" +
            " _ _ _ X X X _ _ \n" +
            "_ _ _ O _ X _ _ _ \n" +
            " _ _ _ X O _ _ _ \n" +
            "  _ _ _ _ _ _ _ \n" +
            "   _ _ _ _ _ _ \n" +
            "    _ _ _ _ _ \n", view.toString());
    Assert.assertEquals(5, game.getScore(1));
    Assert.assertEquals(2, game.getScore(2));
    game.passMove(2);
    Assert.assertEquals("    _ _ _ _ _ \n" +
            "   _ _ _ _ _ _ \n" +
            "  _ _ _ _ _ _ _ \n" +
            " _ _ _ X X X _ _ \n" +
            "_ _ _ O _ X _ _ _ \n" +
            " _ _ _ X O _ _ _ \n" +
            "  _ _ _ _ _ _ _ \n" +
            "   _ _ _ _ _ _ \n" +
            "    _ _ _ _ _ \n", view.toString());
    Assert.assertEquals(5, game.getScore(1));
    Assert.assertEquals(2, game.getScore(2));
    //trying to move out of turn
    Assert.assertThrows(IllegalStateException.class, () ->
            game.flipCell(new CubicCoordinate(1, 1, -2), 2));
    game.flipCell(new CubicCoordinate(-1, -1, 2), 1);
    Assert.assertEquals("    _ _ _ _ _ \n" +
            "   _ _ _ _ _ _ \n" +
            "  _ _ _ _ _ _ _ \n" +
            " _ _ _ X X X _ _ \n" +
            "_ _ _ O _ X _ _ _ \n" +
            " _ _ _ X X _ _ _ \n" +
            "  _ _ _ X _ _ _ \n" +
            "   _ _ _ _ _ _ \n" +
            "    _ _ _ _ _ \n", view.toString());
    Assert.assertEquals(7, game.getScore(1));
    Assert.assertEquals(1, game.getScore(2));
    game.flipCell(new CubicCoordinate(1, 1, -2), 2);
    Assert.assertEquals("    _ _ _ _ _ \n" +
            "   _ _ _ _ _ _ \n" +
            "  _ _ _ O _ _ _ \n" +
            " _ _ _ O X X _ _ \n" +
            "_ _ _ O _ X _ _ _ \n" +
            " _ _ _ X X _ _ _ \n" +
            "  _ _ _ X _ _ _ \n" +
            "   _ _ _ _ _ _ \n" +
            "    _ _ _ _ _ \n", view.toString());
    Assert.assertEquals(6, game.getScore(1));
    Assert.assertEquals(3, game.getScore(2));
    game.flipCell(new CubicCoordinate(-1, 2, -1), 1);
    Assert.assertEquals("    _ _ _ _ _ \n" +
            "   _ _ _ _ _ _ \n" +
            "  _ _ _ O _ _ _ \n" +
            " _ _ X X X X _ _ \n" +
            "_ _ _ X _ X _ _ _ \n" +
            " _ _ _ X X _ _ _ \n" +
            "  _ _ _ X _ _ _ \n" +
            "   _ _ _ _ _ _ \n" +
            "    _ _ _ _ _ \n", view.toString());
    Assert.assertEquals(9, game.getScore(1));
    Assert.assertEquals(1, game.getScore(2));
    game.passMove(2);
    game.flipCell(new CubicCoordinate(2, 1, -3), 1);
    Assert.assertEquals("    _ _ _ _ _ \n" +
            "   _ _ _ X _ _ \n" +
            "  _ _ _ X _ _ _ \n" +
            " _ _ X X X X _ _ \n" +
            "_ _ _ X _ X _ _ _ \n" +
            " _ _ _ X X _ _ _ \n" +
            "  _ _ _ X _ _ _ \n" +
            "   _ _ _ _ _ _ \n" +
            "    _ _ _ _ _ \n", view.toString());
    Assert.assertEquals(11, game.getScore(1));
    Assert.assertEquals(0, game.getScore(2));
    Assert.assertTrue(game.isGameOver());
  }

  @Test
  public void twoOptionalPassesIsGameOver() {
    game.startGame(3, 2);
    game.passMove(1);
    game.passMove(2);
    Assert.assertTrue(game.isGameOver());
  }

  //TEST THROWS:
  @Test
  public void startGameThrows() {
    Assert.assertThrows(IllegalArgumentException.class, () ->
            game.startGame(2, 2));

    List<Integer> invalid2 = new ArrayList<>(Arrays.asList(1, 2, 3));
    Assert.assertThrows(IllegalArgumentException.class, () ->
            game.startGame(3, 0));

    game.startGame(3, 2);
    Assert.assertThrows(IllegalStateException.class, () ->
            game.startGame(3, 2));
  }

  @Test
  public void flipCellThrows() {

    //trying to flip valid cell before game has started
    Assert.assertThrows(IllegalStateException.class, () ->
            game.flipCell(new CubicCoordinate(2, -1, -1), 1));

    game.startGame(3, 2);

    //trying to flip cell thats not valid (not on the board)
    Assert.assertThrows(IllegalArgumentException.class, () ->
            game.flipCell(new CubicCoordinate(5, 0, -5), 1));

    //trying to flip cell that is valid but isn't a valid move
    Assert.assertThrows(IllegalArgumentException.class, () ->
            game.flipCell(new CubicCoordinate(0, 0, 0), 1));
  }

  @Test
  public void passMoveThrows() {
    //game hasn't started yet
    Assert.assertThrows(IllegalStateException.class, () ->
            game.passMove(1));
  }

  @Test
  public void isGameOverThrows() {
    //game hasn't started yet
    Assert.assertThrows(IllegalStateException.class, () ->
            game.isGameOver());
  }

  @Test
  public void getScoreThrows() {
    //game hasn't started yet
    Assert.assertThrows(IllegalStateException.class, () ->
            game.getScore(1));

    //getting score of invalid player
    game.startGame(3, 2);
    Assert.assertThrows(IllegalArgumentException.class, () -> game.getScore(3));
  }

  //tests the cases of placing cells between opponent cells
  @Test
  public void testPlaceCellInbetweenOpponentCells() {
    game.startGame(5, 2);
    game.flipCell(new CubicCoordinate(2, -1, -1), 1);

    game.flipCell(new CubicCoordinate(1, 1, -2), 2);

    game.flipCell(new CubicCoordinate(-1, 2, -1), 1);


    game.flipCell(new CubicCoordinate(3, -1, -2), 2);

    game.flipCell(new CubicCoordinate(2, 1, -3), 1);

    game.passMove(2);
    game.flipCell(new CubicCoordinate(3, -2, -1), 1);

    game.flipCell(new CubicCoordinate(3, -3, 0), 2);

    game.flipCell(new CubicCoordinate(4, -1, -3), 1);
    //System.out.println(view.toString());

    //places cell in between two of opponents cells
    game.flipCell(new CubicCoordinate(3, 0, -3), 2);
    //System.out.println(view.toString());
    Assert.assertEquals(2,
            game.getBoard().get(new CubicCoordinate(3, 0, -3)).getOwner());

  }

  // Test clone
  @Test
  public void testCloneGame() {
    game.startGame(3, 2);
    ReversiModel clone = game.clone();

    Assert.assertEquals(game.getBoard(), clone.getBoard());

    clone.flipCell(new CubicCoordinate(2, -1, -1), 1);

    Assert.assertNotEquals(game.getBoard(), clone.getBoard());
  }


  //test isMoveValid
  @Test
  public void testisMoveValid() {
    game.startGame(3, 2);

    Assert.assertTrue(game.isMoveValid(new CubicCoordinate(-1, 2, -1), 1));
    Assert.assertTrue(game.isMoveValid(new CubicCoordinate(-1, -1, 2), 1));
    Assert.assertFalse(game.isMoveValid(new CubicCoordinate(3, 0, -3), 1));

  }

  //test getAvailableMoves
  @Test
  public void getAvailableMoves() {
    game.startGame(3, 2);
    List<CubicCoordinate> available =
            new ArrayList<>(Arrays.asList(new CubicCoordinate(1, 1, -2),
                    new CubicCoordinate(-1, 2, -1), new CubicCoordinate(2, -1, -1),
                    new CubicCoordinate(-2, 1, 1), new CubicCoordinate(1, -2, 1),
                    new CubicCoordinate(-1, -1, 2)));

    Assert.assertEquals(available, game.getAvailableMoves(1));

  }


  @Test
  public void getCurrentTurn() {
    game.startGame(3, 2);
    Assert.assertEquals(1, game.getCurrentTurn());
    game.passMove(1);
    Assert.assertEquals(2, game.getCurrentTurn());
  }

  @Test
  public void testFindCubicCoord() {
    game.startGame(3, 2);
    HexagonGUI g = new HexagonGUI(game);
    // Create the main JFrame to display the game
    JFrame frame = new JFrame("Reversi Game");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(500, 500);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.add(g);
    frame.setVisible(true);

    //Assert.assertEquals(500, g.getWidth());
    Coordinate coordClicked = g.findCoordClicked(g.getHeight() / 2, g.getWidth() / 2);

    Assert.assertEquals(new CubicCoordinate(0, 0, 0), coordClicked);
  }

  @Test
  public void testSubscribe() {
    StringBuilder log = new StringBuilder();
    MockReversiObservation mock = new MockReversiObservation(1, log);
    mock.subscribe(new ReversiController(new HexagonReversi(),
            new PlayerImpl(null, 1), new HexagonGUI(new HexagonReversi())));
    mock.subscribe(new ReversiController(new HexagonReversi(),
            new PlayerImpl(null, 1), new HexagonGUI(new HexagonReversi())));

    Assert.assertEquals("1\n2\n", log.toString());
  }

  @Test
  public void testNotifyObserverTurn() {
    StringBuilder log = new StringBuilder();
    MockReversiObservation mock = new MockReversiObservation(1, log);
    mock.subscribe(new MockController(new MockReversiObservation(1, new StringBuilder()),
            new PlayerImpl(null, 1), new HexagonGUI(new HexagonReversi()), log));
    mock.subscribe(new MockController(new MockReversiObservation(1, new StringBuilder()),
            new PlayerImpl(null, 1), new HexagonGUI(new HexagonReversi()), log));

    mock.notifyObserverTurn();

    Assert.assertEquals("1\n" +
            "2\n" +
            "NOTIFIED: 1\n", log.toString());

  }

}
