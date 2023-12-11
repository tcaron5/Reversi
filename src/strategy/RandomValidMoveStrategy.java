package strategy;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import model.Coordinate;
import model.ReadonlyReversiModel;

/**
 * This strategy randomly chooses a valid move to make.
 */
public class RandomValidMoveStrategy implements ReversiStrategy {
  @Override
  public Coordinate chooseCoord(ReadonlyReversiModel model, int player) {
    List<Coordinate> keysAsArray = new ArrayList<Coordinate>(model.getBoard().keySet());

    Random r = new Random();
    Coordinate randomCoord = keysAsArray.get(r.nextInt(keysAsArray.size()));
    boolean validMoveExists = false;
    //if no valid moves
    for (Coordinate coord : keysAsArray) {
      if (model.isMoveValid(coord, player)) {
        validMoveExists = true;
      }
    }

    while (!model.isMoveValid(randomCoord, player) && validMoveExists) {
      r = new Random();
      randomCoord = keysAsArray.get(r.nextInt(keysAsArray.size()));
    }

    return randomCoord;
  }
}
