package model;

import org.junit.Assert;
import org.junit.Test;

import view.TextualView;


/**
 * Tests the helper and protected methods in the model package for abstract reversi.
 */
public class AbstractReversiPackageTests {

  private model.HexagonReversi game = new model.HexagonReversi();

  //private List<Integer> players = new ArrayList<>(Arrays.asList(1,2)) ;

  private TextualView view = new TextualView(game);

  @Test
  public void testHasValidMoves() {
    game.startGame(3, 2);
    Assert.assertTrue(game.hasValidMoves(1));
    Assert.assertTrue(game.hasValidMoves(2));
    Assert.assertThrows(IllegalArgumentException.class, () -> game.hasValidMoves(3));
  }

  @Test
  public void testFlipCellsInDirection() {
    game.startGame(4, 2);
    Assert.assertEquals("   _ _ _ _ \n" +
            "  _ _ _ _ _ \n" +
            " _ _ X O _ _ \n" +
            "_ _ O _ X _ _ \n" +
            " _ _ X O _ _ \n" +
            "  _ _ _ _ _ \n" +
            "   _ _ _ _ \n", view.toString());
    game.flipCellsInDirection(new CubicCoordinate(-3,0,3), new CubicCoordinate(1,0,-1));
    //assumes the selected cell is already owned by the player
    Assert.assertTrue(game.getBoard().get(new CubicCoordinate(-3,0,3)).getOwner() == 0);
    Assert.assertTrue(game.getBoard().get(new CubicCoordinate(-2,0,2)).getOwner() == 1);
    Assert.assertTrue(game.getBoard().get(new CubicCoordinate(-1,0,1)).getOwner() == 1);
    Assert.assertEquals("   _ _ _ _ \n" +
            "  _ _ _ _ _ \n" +
            " _ _ X O _ _ \n" +
            "_ _ O _ X _ _ \n" +
            " _ _ X O _ _ \n" +
            "  _ X _ _ _ \n" +
            "   _ _ _ _ \n", view.toString());
  }

  @Test
  public void testIsMoveValid() {
    game.startGame(3,2);
    //empty, but invalid cell (center of board)
    Assert.assertFalse(game.isMoveValid(new CubicCoordinate(0,0,0), 1));
    //already owned, invalid cell
    Assert.assertFalse(game.isMoveValid(new CubicCoordinate(-1,0,1), 1));
    //unowned, invalid cell, on the board
    Assert.assertFalse(game.isMoveValid(new CubicCoordinate(2,0,-2), 1));
    //invalid cell, off the board
    Assert.assertFalse(game.isMoveValid(new CubicCoordinate(5,0,-5), 1));
    //valid cell
    Assert.assertTrue(game.isMoveValid(new CubicCoordinate(2,-1,-1), 1));
  }

  @Test
  public void testHasStraightLine() {
    game.startGame(3, 2);
    //coordinate on the board, but when direction is added goes off the board
    Assert.assertFalse(game.hasStraightLine(new CubicCoordinate(2,0,-2),
            new CubicCoordinate(1,0,-1), 1));
    //test neighbor in that direction is owned by same player
    Assert.assertFalse(game.hasStraightLine(new CubicCoordinate(-1,2,-1),
            new CubicCoordinate(1,-1,-0), 1));
    //correctly finds new line
    Assert.assertTrue(game.hasStraightLine(new CubicCoordinate(2,-1,-1),
            new CubicCoordinate(-1,1,-0), 1));
  }

  @Test
  public void testIsValidCoord() {
    game.startGame(3, 2);
    Assert.assertTrue(game.isValidCoord(new CubicCoordinate(2,-1,-1)));
    Assert.assertTrue(game.isValidCoord(new CubicCoordinate(0,0,0)));
    Assert.assertFalse(game.isValidCoord(new CubicCoordinate(5,0,-5)));
  }

}