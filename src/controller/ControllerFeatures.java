package controller;

import model.Coordinate;
import model.ReadonlyReversiModel;

/**
 * The actions (features) guaranteed for a controller in the game of Reversi.
 * Allows for a controller to be a listener of a view.
 */
public interface ControllerFeatures {

  /**
   * The action of passing their move.
   */
  void pass();

  /**
   * The action of moving at the given coordinate.
   */
  void move(Coordinate c);

  /**
   * Calls the model and returns the number of possible cells flipped from a move.
   */
  int hintHelper(ReadonlyReversiModel model, Coordinate selectedHexagon);
}
