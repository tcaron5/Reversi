package view;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

import model.Cell;
import model.Coordinate;

/**
 * A textual view for the game Reversi that uses the ReversiModel.
 */
public class TextualView {

  private final model.ReadonlyReversiModel model;

  //private Appendable ap;


  public TextualView(model.ReadonlyReversiModel model) {
    this.model = model;
  }

  public TextualView(model.ReadonlyReversiModel model, Appendable ap) {
    this.model = model;
    //this.ap = ap;
  }

  @Override
  public String toString() {

    StringBuilder sb = new StringBuilder();

    Map<Coordinate, Cell> board = model.getBoard();
    Collection<Cell> values = board.values();
    Set<Coordinate> keys = board.keySet();


    // Display the board
    int lineLength = 0;
    int lengthLimit = model.getSideLength();

    //counts what row we are on
    int rowCount = 0;


    for (Coordinate coordinate : board.keySet()) {

      if (lineLength == 0) {
        // Add spaces at the beginning of the line
        for (int i = 0; i < Math.abs(coordinate.getZ()); i++) {
          sb.append(" ");
        }
      }

      Cell cell = board.get(coordinate);
      if (cell.getOwner() == 0) {
        sb.append("_");
      } else if (cell.getOwner() == 1) {
        sb.append("X");
      } else if (cell.getOwner() == 2) {
        sb.append("O");
      } else if (cell.getOwner() == -1) {
        sb.append("!");
      } else {
        throw new IllegalArgumentException("Invalid owner");
      }
      sb.append(" "); // adds a space between each cell

      lineLength++;
      // Check if it's time to start a new row
      if (lineLength == lengthLimit) {
        sb.append("\n");

        lineLength = 0;
        rowCount++;
        if (rowCount < model.getSideLength()) {
          lengthLimit++;
        } else {
          lengthLimit--;
        }
      }

    }
    return sb.toString();
  }


}
