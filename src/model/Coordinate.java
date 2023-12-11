package model;

import java.util.List;

/**
 * Interface for a generic coordinate. Where T has to extend Coordinate.
 */
public interface Coordinate {


  /**
   * Gets the neighbors of this coordinate.
   * @return the neighbors of this coordinate.
   */
  List<Coordinate> getNeighbors();

  /**
   * Gets all the possible directions of this coordinate.
   * @return all the possible directions of this coordinate.
   */
  List<Coordinate> getDirections();


  /**
   * Adds the given coordinate to this coordinate.
   * @param other the cubic coordinate to be added.
   * @return the sum of the two cubic coordinates.
   */
  Coordinate add(Coordinate other);

  /**
   * Adds the given coordinate to this coordinate.
   * @param other the cartesian coordinate to be added.
   * @return the sum of the two coordinates.
   */
  CartesianCoordinate addCartesian(CartesianCoordinate other);

  /**
   * Adds the given coordinate to this coordinate.
   * @param other the cubic coordinate to be added.
   * @return the sum of the two coordinates.
   */
  CubicCoordinate addCubic(CubicCoordinate other);

  /**
   * Get the x coord of the Coordinate.
   * @return the x coord.
   */
  int getX();


  /**
   * Get the y coord of the Coordinate.
   * @return the y coord.
   */
  int getY();


  /**
   * Get the z coord of the Coordinate.
   * @return the z coord.
   */
  int getZ();


}
