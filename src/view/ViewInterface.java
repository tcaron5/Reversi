package view;

import controller.ControllerFeatures;
import model.Coordinate;

/**
 * The interface for the view of a game of reversi.
 */
public interface ViewInterface {

  /**
   * Converts a mouse click XY to the cubic coordinate of the cell.
   * @param mouseX The x value of the click.
   * @param mouseY The y value of the click.
   * @return The CubicCoordinate of the cell clicked.
   */
  Coordinate findCoordClicked(int mouseX, int mouseY);



  //add public methods that controller would call

  /**
   * Allows view to gain access to the features guaranteed by the controller.
   * @param features controller features.
   */
  void addFeatures(ControllerFeatures features);

  /**
   * Displays the up-to-date board in the view.
   */
  void display();

  /**
   * Displays a message to the user.
   * @param message the message to be displayed.
   */
  void displayMessage(String message);
}
