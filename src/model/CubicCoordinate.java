package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a cubic coordinate.
 * We chose to use cubic coordinates for our game board for maximum flexibility
 * so that our model can create different board shapes easily without having to change
 * any game logic.
 */
public class CubicCoordinate implements Coordinate {
  private final int x;
  private final int y;
  private final int z;

  private final     // Define the six possible neighbor directions
  int[][] directions = {
          {1, -1, 0}, {1, 0, -1}, {0, 1, -1},
          {-1, 1, 0}, {-1, 0, 1}, {0, -1, 1}
  };

  /**
   * Constructs a cubic coordinate with the given x, y, z.
   * @param q the x coordinate
   * @param s the y coordinate
   * @param r the z coordinate
   */
  public CubicCoordinate(int q, int s, int r) {
    this.x = q;
    this.y = s;
    this.z = r;
  }

  //getters
  public int getX() {
    return x;
  }

  public int getY() {
    return y;
  }

  public int getZ() {
    return z;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }
    CubicCoordinate other = (CubicCoordinate) obj;
    return x == other.x && y == other.y && z == other.z;
  }

  @Override
  public int hashCode() {
    int result = x;
    result = 31 * result + y;
    result = 31 * result + z;
    return result;
  }

  @Override
  public String toString() {
    return "(" + x + ", " + y + ", " + z + ")";
  }

  /**
   * Gets the neighbors of this cubic coordinate.
   * @return the neighbors of this cubic coordinate
   */
  public List<Coordinate> getNeighbors() {
    List<Coordinate> neighbors = new ArrayList<>();
    int x = this.getX();
    int y = this.getY();
    int z = this.getZ();

    for (int[] dir : directions) {
      int dx = dir[0];
      int dy = dir[1];
      int dz = dir[2];

      neighbors.add(new CubicCoordinate(x + dx, y + dy, z + dz));
    }

    return neighbors;
  }

  /**
   * Gets all the possible directions of this cubic coordinate.
   * @return all the possible directions of this cubic coordinate
   */
  public List<Coordinate> getDirections() {
    List<Coordinate> neighbors = new ArrayList<>();

    for (int[] dir : directions) {
      int dx = dir[0];
      int dy = dir[1];
      int dz = dir[2];

      neighbors.add(new CubicCoordinate(dx, dy, dz));
    }
    return neighbors;
  }


  @Override
  public Coordinate add(Coordinate other) {
    return other.addCubic(this);
  }

  @Override
  public CartesianCoordinate addCartesian(CartesianCoordinate other) {
    throw new UnsupportedOperationException("Cannot add a " +
            "cartesian coordinate to a cubic coordinate");
  }


  /**
   * Adds the given cubic coordinate to this cubic coordinate.
   * @param other the cubic coordinate to be added
   * @return the sum of the two cubic coordinates
   */
  @Override
  public CubicCoordinate addCubic(CubicCoordinate other) {
    int newX = this.getX() + other.getX();
    int newY = this.getY() + other.getY();
    int newZ = this.getZ() + other.getZ();
    return new CubicCoordinate(newX, newY, newZ);
  }

}