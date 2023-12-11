package provider.strategy;

import java.util.List;

import provider.model.ReadonlyReversiModel;
import provider.model.HexCoordinate;
import provider.player.PlayerColor;

/**
 * Represents a ReversiStrategy that will choose a move that
 * will gain this player the move tiles possible.
 * In the event of a tie, this strategy will choose a move
 * with the uppermost-leftmost coordinate.
 */
public class AsManyAsPossible implements ReversiStrategy {
  @Override
  public HexCoordinate chooseMove(ReadonlyReversiModel m, PlayerColor forWhom) {
    List<HexCoordinate> availableMoves = m.getLegalMoves(forWhom);
    if (availableMoves.isEmpty()) {
      throw new IllegalStateException("No legal moves. You must pass.");
    }
    int maxScore = 0;
    HexCoordinate bestMove = null;
    for (HexCoordinate c : availableMoves) {
      int score = m.getSimulatedScore(c, forWhom);
      if (score > maxScore) {
        bestMove = c;
        maxScore = score;
      } else if (score == maxScore) {
        if (bestMove == null) {
          bestMove = c;
        } else {
          bestMove = HexCoordinate.findUppermostLeftmost(List.of(bestMove, c));
        }
      }
    }
    return bestMove;
  }
}
