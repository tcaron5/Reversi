package strategy;

import java.util.ArrayList;
import java.util.List;

import model.Coordinate;
import model.ReadonlyReversiModel;
import model.ReversiModel;

/**
 * This strategy will first play in available corners. If no available corners,
 * then delegate to the AvoidCornerNeighborStrategy.
 */
public class CheckCornersFirstStrategy implements ReversiStrategy {

  @Override
  public Coordinate chooseCoord(ReadonlyReversiModel model, int player) {

    List<Coordinate> allCorners = getAllCorners(model);

    List<Coordinate> corners = new ArrayList<>();
    // if there is a valid move that is a corner, play that
    for (Coordinate coord : model.getBoard().keySet()) {
      if (model.isMoveValid(coord, player) && allCorners.contains(coord)) {
        ReversiModel clone = model.clone();
        clone.flipCell(coord, player);
        corners.add(coord);
      }
    }
    if (!corners.isEmpty()) {
      return corners.get(0);
    }

    // if there are no valid corner moves, default to the AvoidCornerNeighborStrategy
    return new AvoidCornerNeighborStrategy().chooseCoord(model, player);
  }


  private List<Coordinate> getAllCorners(ReadonlyReversiModel model) {
    List<Coordinate> allCorners = new ArrayList<Coordinate>();

    int maxCoordValue = model.getSideLength() - 1;

    for (Coordinate coord : model.getBoard().keySet()) {

      if ((coord.getX() == maxCoordValue
              || coord.getY() == maxCoordValue
              || coord.getZ() == maxCoordValue) && (coord.getX() == 0
              || coord.getY() == 0
              || coord.getZ() == 0)) {
        allCorners.add(coord);
      }

    }
    return allCorners;
  }
}
