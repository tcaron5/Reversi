package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Represents a coordinate in a x,y Cartesian plane.
 */
public class CartesianCoordinate implements Coordinate {

  private final int x;
  private final int y;

  public CartesianCoordinate(int x, int y) {
    this.x = x;
    this.y = y;
  }

  @Override
  public List<Coordinate> getNeighbors() {
    List<Coordinate> neighbors = new ArrayList<>();
    // Add adjacent coordinates
    neighbors.add(new CartesianCoordinate(x + 1, y));   // Right
    neighbors.add(new CartesianCoordinate(x - 1, y));   // Left
    neighbors.add(new CartesianCoordinate(x, y + 1));   // Down
    neighbors.add(new CartesianCoordinate(x, y - 1));   // Up

    // Add diagonally adjacent coordinates
    neighbors.add(new CartesianCoordinate(x + 1, y + 1)); // Bottom-Right
    neighbors.add(new CartesianCoordinate(x - 1, y + 1)); // Bottom-Left
    neighbors.add(new CartesianCoordinate(x + 1, y - 1)); // Top-Right
    neighbors.add(new CartesianCoordinate(x - 1, y - 1)); // Top-Left

    return neighbors;
  }

  @Override
  public List<Coordinate> getDirections() {
    List<Coordinate> directions = new ArrayList<>();

    directions.add(new CartesianCoordinate(-1,1));
    directions.add(new CartesianCoordinate(0,1));
    directions.add(new CartesianCoordinate(1,1));
    directions.add(new CartesianCoordinate(1,0));
    directions.add(new CartesianCoordinate(1, -1));
    directions.add(new CartesianCoordinate(0, -1));
    directions.add(new CartesianCoordinate(-1, -1));
    directions.add(new CartesianCoordinate(-1, 0));

    return directions;
  }

  @Override
  public Coordinate add(Coordinate other) {
    return other.addCartesian(this);
  }

  @Override
  public CartesianCoordinate addCartesian(CartesianCoordinate other) {

    int newX = this.x + other.x;
    int newY = this.y + other.y;
    return new CartesianCoordinate(newX, newY);
  }

  @Override
  public CubicCoordinate addCubic(CubicCoordinate other) {
    throw new UnsupportedOperationException("Cannot add a " +
            "cubic coordinate to a cartesian coordinate");
  }

  @Override
  public int getX() {
    return x;
  }

  @Override
  public int getY() {
    return y;
  }

  @Override
  public int getZ() {
    // Cartesian coordinates don't have a Z coordinate, returning 0
    return 0;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }
    CartesianCoordinate that = (CartesianCoordinate) obj;
    return x == that.x && y == that.y;
  }

  @Override
  public int hashCode() {
    return Objects.hash(x, y);
  }
}