package view;

import java.util.Objects;

import javax.swing.JPanel;
import javax.swing.JOptionPane;

import controller.ControllerFeatures;
import model.Coordinate;
import model.ReadonlyReversiModel;

/**
 * A GUI view for the game of Reversi, allowing users to click to play the game.
 * Should not be able to affect the model directly.
 */
public abstract class AbstractGUI extends JPanel implements ViewInterface {

  protected ReadonlyReversiModel model;
  protected ControllerFeatures controller;
  protected boolean hintsOn;

  /**
   * Constructor for the GUI view.
   *
   * @param model the model that the view will be using.
   */
  public AbstractGUI(ReadonlyReversiModel model) {
    this.model = Objects.requireNonNull(model);

    //set hints to off when game starts
    hintsOn = false;
  }

  @Override
  public abstract Coordinate findCoordClicked(int mouseX, int mouseY);

  @Override
  public void addFeatures(ControllerFeatures features) {
    this.controller = features;
  }

  @Override
  public void display() {
    repaint();
  }

  @Override
  public void displayMessage(String message) {
    JOptionPane.showMessageDialog(this,
            message,
            "Message", JOptionPane.INFORMATION_MESSAGE);
  }
}
