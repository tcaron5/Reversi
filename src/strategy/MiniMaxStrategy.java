package strategy;

import java.util.ArrayList;
import java.util.List;

import model.Coordinate;
import model.Pair;
import model.ReadonlyReversiModel;
import model.ReversiModel;

/**
 * This Reversi strategy is built to be unbeatable. That is, it uses the mini max
 * algorithm to determine its best possible move, based on opponent's strategy and possible moves.
 * This strategy recursively looks down all possible game outcomes (up to a given depth)
 * for a given move, and it determines which one leads to maximizing ones own score.
 *
 */
public class MiniMaxStrategy implements ReversiStrategy {
  private int player; // the player whose score you are trying to maximize

  private final int depth;

  private ReversiStrategy opponentStrategy;

  /**
   * MiniMax strategy for reversi takes in a depth.
   * @param depth the max number of moves it looks down for a particular possible move. Once
   *              the max depth is reached, the mini max will delegate to another strategy.
   * @param opponentStrategy the strategy of the opponent, used to determine our minimax move.
   */
  public MiniMaxStrategy(int depth, ReversiStrategy opponentStrategy) {
    // throw if bad depth
    this.depth = depth;
    this.opponentStrategy = opponentStrategy;
  }

  /**
   * MiniMax strategy for reversi takes in a depth.
   * @param depth the max number of moves it looks down for a particular possible move. Once
   *              the max depth is reached, the mini max will delegate to another strategy.
   */
  public MiniMaxStrategy(int depth) {
    // throw if bad depth
    this.depth = depth;
    this.opponentStrategy = new GoForCornersStrategy();
  }

  @Override
  public Coordinate chooseCoord(ReadonlyReversiModel model, int player) {
    if (depth < 0) {
      throw new IllegalArgumentException("Depth should be 0 or more.");
    }
    this.player = player;

    //return choice;
    return miniMax(model, player, 0).first();
  }

  protected Pair<Coordinate, Integer> miniMax(ReadonlyReversiModel model,
                                                   int player, int depth) {

    // get the list of possible moves for each player
    List<Coordinate> availablePlayer = model.getAvailableMoves(player);
    List<Coordinate> availableOpponent = model.getAvailableMoves(3 - player);

    //max depth reached
    if (depth >= this.depth) {
      // if ME has no moves
      // return null pair with score eval, use global PLAYER - other player for score calc
      if (availablePlayer.isEmpty()) {
        return new Pair<>(null, miniMaxScore(model, player));
      }
      // we are at our turn, and cant look any further, so we play a move and return its result
      // return pair of move and score after that move, clone before u mutate model
      else if (player == this.player) {
        ReversiModel possibleGame = model.clone();
        Coordinate move = new AvoidCornerNeighborStrategy().chooseCoord(possibleGame, player);
        possibleGame.flipCell(move, player);
        return new Pair<>(move, miniMaxScore(possibleGame, player));
      }
      // we are at other turn, play opponents return pair of coord and score,
      // clone the model then mutate it and evaluate the score
      else {
        ReversiModel possibleGame = model.clone();
        Coordinate move = this.opponentStrategy.chooseCoord(possibleGame, player);
        possibleGame.flipCell(move, player);
        return new Pair<>(move, miniMaxScore(possibleGame, player));
      }
    }

    // case: both lists are empty, then game is over
    // return null pair with score eval, use global PLAYER - other player
    if (availablePlayer.isEmpty() && availableOpponent.isEmpty()) {
      return new Pair<>(null, miniMaxScore(model, player));
    }
    // case: you have no moves
    // pass the turn as the other player
    // return minimax(model, other player, depth + 1)
    else if (availablePlayer.isEmpty() && !availableOpponent.isEmpty()) {

      return miniMax(model, 3 - player, depth + 1);
    }

    List<Coordinate> available = model.getAvailableMoves(player);
    //which possible move is best
    List<Pair<Coordinate, Integer>> coordScorePairs = new ArrayList<>();

    //    System.out.println("Began iterating over moves at depth " + depth);
    //populate lists(turn into helper)?
    //    System.out.println(available);
    for (Coordinate coord : available) {
      //      System.out.println("Moving for " + coord);
      //      System.out.println("Before: \n" + new TextualView(model));
      //      System.out.println(model.getCurrentTurn());
      ReversiModel possibleGame = model.clone();
      possibleGame.flipCell(coord, player);
      //      System.out.println("After: \n" + new TextualView(possibleGame));
      //      System.out.println(possibleGame.getCurrentTurn());

      int nextPlayer = 3 - player;
      coordScorePairs.add(new Pair<>(coord, miniMax(possibleGame, nextPlayer,
              depth + 1).second())); //recursion down each available move
    }
    //    System.out.println("Finished iterating over moves at detpth " + depth);

    // Now Actually choose the move out of the available moves
    // Do the min or the max calculation
    if (model.getCurrentTurn() == this.player) {
      return this.getMaxPair(coordScorePairs);
    } else {
      return this.getMinPair(coordScorePairs);
    }
  }

  private Pair<Coordinate, Integer> getMaxPair(List<Pair<Coordinate,
          Integer>> coordScorePairs) {
    if (coordScorePairs.isEmpty()) {
      throw new RuntimeException("bad");
    }
    Pair<Coordinate, Integer> bestPair = coordScorePairs.get(0);

    for (Pair<Coordinate, Integer> pair : coordScorePairs) {
      if (pair.second() > bestPair.second()) {
        bestPair = pair;
      }
    }

    return bestPair;
  }

  private Pair<Coordinate, Integer> getMinPair(List<Pair<Coordinate,
          Integer>> coordScorePairs) {
    if (coordScorePairs.isEmpty()) {
      throw new RuntimeException("bad");
    }
    Pair<Coordinate, Integer> bestPair = coordScorePairs.get(0);

    for (Pair<Coordinate, Integer> pair : coordScorePairs) {
      if (pair.second() < bestPair.second()) {
        bestPair = pair;
      }
    }

    return bestPair;
  }

  //score is called when a base case is hit
  //
  protected int miniMaxScore(ReadonlyReversiModel model, int player) {
    if (player == this.player) {
      return model.getScore(player) - model.getScore(3 - player);
    }
    else {
      return model.getScore(3 - player) - model.getScore(player);
    }
  }

}
