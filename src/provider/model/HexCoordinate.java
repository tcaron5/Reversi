package provider.model;

import java.util.List;
import java.util.Objects;

/**
 * Represents a coordinate of a Reversi hex cell.
 * The coordinate system can be thought of as a rectangular
 * grid, with its origin at the top left. There is some unused
 * space at the top left and bottom right of the grid,
 * as a hexagonal space will have fewer columns as you move
 * from the middle outwards.
 * From a "hexagonal-viewpoint", rows of the hexagon
 * go from top to bottom, starting from zero.
 * Columns go from the bottom-left edge of the hexagon
 * to the top-right edge, starting from zero.
 */
public class HexCoordinate {
  private final int col;
  private final int row;
  public static final int[][] AXIAL_DIRECTIONS = {{1, 0}, {0, 1}, {-1, 0},
      {-1, 1}, {0, -1}, {1, -1}};

  /**
   * Constructs a new HexCoordinate object.
   *
   * @param col axial column
   * @param row axial row
   */
  public HexCoordinate(int row, int col) {
    this.col = col;
    this.row = row;
  }

  /**
   * Get the column of this coordinate.
   *
   * @return int column
   */
  public int getCol() {
    return this.col;
  }

  /**
   * Get the row of this coordinate.
   *
   * @return int row
   */
  public int getRow() {
    return this.row;
  }

  /**
   * Checks if the given coordinate exists on a straight
   * line with this coordinate, in the context of a hexagonal pointy-top grid.
   *
   * @param other HexCoordinate to compare to
   * @return true if they exist on the same straight line
   */
  public boolean onSameLine(HexCoordinate other) {
    if (this.getRow() == other.getRow() || this.getCol() == other.getCol()) {
      return true;
    }
    int rowDistance = other.getRow() - this.getRow();
    return Math.abs(this.getCol() - rowDistance) == other.getCol();
  }

  /**
   * Returns a new instance of the same HexCoordinate.
   *
   * @return duplicated HexCoordinate
   */
  public HexCoordinate duplicate() {
    return new HexCoordinate(this.getRow(), this.getCol());
  }

  @Override
  public String toString() {
    return "(" + this.row + ", " + this.col + ")";
  }

  @Override
  public boolean equals(Object other) {
    if (!(other instanceof HexCoordinate)) {
      return false;
    }
    HexCoordinate o = (HexCoordinate) other;
    return this.row == o.getRow() && this.col == o.getCol();
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.row, this.col);
  }

  /**
   * Finds the uppermost-leftmost coordinate from a list of HexCoordinates.
   *
   * @param coordinates List of HexCoordinate objects
   * @return The uppermost-leftmost HexCoordinate
   * @throws IllegalArgumentException if the coordinates list is empty or null
   */
  public static HexCoordinate findUppermostLeftmost(List<HexCoordinate> coordinates) {
    if (coordinates == null || coordinates.isEmpty()) {
      throw new IllegalArgumentException("The list of coordinates cannot be null or empty.");
    }

    HexCoordinate uppermostLeftmost = coordinates.get(0);
    for (HexCoordinate coordinate : coordinates) {
      if (coordinate.getRow() < uppermostLeftmost.getRow()
              || (coordinate.getRow() == uppermostLeftmost.getRow()
              && coordinate.getCol() < uppermostLeftmost.getCol())) {
        uppermostLeftmost = coordinate;
      }
    }
    return uppermostLeftmost;
  }

}
