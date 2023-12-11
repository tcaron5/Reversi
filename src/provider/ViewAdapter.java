package provider;

import java.awt.Graphics;
import java.util.Objects;

import javax.swing.JPanel;

import provider.view.ReversiView;
import controller.ControllerFeatures;
import model.CubicCoordinate;
import view.ViewInterface;

/**
 * Takes in the provider view implementation and adapts it to our view interface.
 */
public class ViewAdapter extends JPanel implements ViewInterface {
  private final ReversiView view;
  private final int sideLength;


  /**
   * Adapts their view to ours.
   *
   * @param view       Our Providers view of the game.
   * @param sideLength length of one side of the board.
   */
  public ViewAdapter(ReversiView view, int sideLength) {
    Objects.requireNonNull(view);
    this.view = view;
    this.sideLength = sideLength;
  }

  @Override
  public CubicCoordinate findCoordClicked(int mouseX, int mouseY) {
    return null;
  }

  @Override
  public void addFeatures(ControllerFeatures features) {
    view.addPlayerActions(new FeaturesToPlayerActionsAdapter(features, sideLength));
  }

  @Override
  public void display() {
    view.refreshView();
  }

  @Override
  public void displayMessage(String message) {
    view.showMessage(message);
  }

  @Override
  protected void paintComponent(Graphics g) {
    view.refreshView();
  }

}
