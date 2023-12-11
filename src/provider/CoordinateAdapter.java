package provider;

import model.Coordinate;
import provider.model.HexCoordinate;
import model.CubicCoordinate;

/**
 * Turns the providers hex coordinate into a cubic coordinate.
 */
public class CoordinateAdapter {

  /**
   * Converts a hex coordinate to a cubic coordinate.
   * @param row is the row of the hex coordinate
   * @param col is the column of the hex coordinate
   * @param sideLength is the side length of the board
   * @return a cubic coordinate
   */
  public CubicCoordinate convertToCubic(int row, int col, int sideLength) {

    int r = row  - (sideLength - 1);
    int q = col  - (sideLength - 1);
    int s = -q - r;

    // Create and return a CubicCoordinates object
    return new CubicCoordinate(q, s, r);
  }


  /**
   * Converts a cubic coordinate to their hex coordinate.
   * @param coord is the cubic coordinate
   * @param sideLength is the side length of the board
   * @return their hex coordinate
   */
  public HexCoordinate convertToHex(Coordinate coord, int sideLength) {
    int row = coord.getZ() + (sideLength - 1);
    int col = coord.getX() + (sideLength - 1);
    return new HexCoordinate(row, col);
  }


}
