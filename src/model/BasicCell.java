package model;

import java.util.Objects;

/**
 * This class represents a basic cell.
 */
public class BasicCell implements Cell {

  //owner as 0 represents untouched hexagon cell.
  private final int owner;

  /**
   * Constructs a basic cell with owner as 0.
   */
  public BasicCell() {

    this.owner = 0;
  }

  /**
   * Constructs a basic cell with owner as the given owner.
   * @param owner the owner of the cell
   */
  public BasicCell(int owner) {

    this.owner = owner;
  }

  /**
   * Gets the owner of the cell.
   * @return the owner of the cell
   */
  public int getOwner() {
    return owner;
  }

  //equal operations
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    BasicCell otherCell = (BasicCell) o;
    return owner == otherCell.owner;
  }

  @Override
  public int hashCode() {
    return Objects.hash(owner);
  }

}
