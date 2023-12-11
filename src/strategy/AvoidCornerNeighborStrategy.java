package strategy;

import java.util.ArrayList;
import java.util.List;

import model.Coordinate;
import model.ReadonlyReversiModel;

/**
 * This Reversi strategy will avoid playing next to corners.
 * If it must play next to a corner, capture max cells.
 */
public class AvoidCornerNeighborStrategy implements ReversiStrategy {

  @Override
  public Coordinate chooseCoord(ReadonlyReversiModel model, int player) {

    List<Coordinate> allNeighborsOfCorners = getAllNeighborsOfCorners(model);

    // if there is a valid move that is not a neighbor of a corner, play that
    for (Coordinate coord : model.getBoard().keySet()) {
      if (model.isMoveValid(coord, player) && !allNeighborsOfCorners.contains(coord)) {
        return coord;
      }
    }

    //then it must choose a neighbor:
    return new CaptureMaxCellsThisMove().chooseCoord(model, player);


  }

  private List<Coordinate> getAllNeighborsOfCorners(ReadonlyReversiModel model) {
    List<Coordinate> allNeighborsOfCorners = new ArrayList<Coordinate>();

    int maxCoordValue = model.getSideLength() - 1;

    for (Coordinate coord : model.getBoard().keySet()) {

      if ((coord.getX() == maxCoordValue
              || coord.getY() == maxCoordValue
              || coord.getZ() == maxCoordValue) && (coord.getX() == 0
              || coord.getY() == 0
              || coord.getZ() == 0)) {
        allNeighborsOfCorners.addAll(coord.getNeighbors());
      }

    }
    return allNeighborsOfCorners;
  }


}
